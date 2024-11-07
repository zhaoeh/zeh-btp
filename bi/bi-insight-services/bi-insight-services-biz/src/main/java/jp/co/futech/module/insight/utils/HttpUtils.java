package jp.co.futech.module.insight.utils;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.module.insight.constant.CommonConstant.*;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.EVENT_TASK_AIRFLOW_EXCEPTION;


/**
 * @author tao.ma@futech.co.jp
 * @date 2024/4/17
 */
public class HttpUtils {

    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static final String HTTP_PROTOCOL = "http://";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_ALL = "*/*";


    /**
     * Http Get Task Status
     *
     * @param queryId       Query ID
     * @param baseUrl       Base Url
     * @param authorization Authorization
     * @param restTemplate  Restful Template
     * @return Response Entity
     */
    public static ResponseEntity<JSONObject> getTaskStatus(String queryId,
                                                           String baseUrl,
                                                           String authorization,
                                                           RestTemplate restTemplate) {
        String url = baseUrl + SLASH + queryId + SLASH + STATUS;
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);
    }

    /**
     * Http Post Query Task
     *
     * @param taskSql       Task SQL
     * @param baseUrl       Base Url
     * @param mode          Query Mode
     * @param authorization Authorization
     * @param restTemplate  Restful Template
     * @return Response Entity
     */
    public static ResponseEntity<JSONObject> postQueryTask(String taskSql,
                                                           String baseUrl,
                                                           String mode,
                                                           String authorization,
                                                           RestTemplate restTemplate) {
        JSONObject requestBody = new JSONObject();
        requestBody.put(QUERY, taskSql);
        requestBody.put(MODE, mode);
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, authorization);
        return restTemplate.postForEntity(baseUrl, new HttpEntity<>(requestBody.toJSONString(), headers), JSONObject.class);
    }

    public static ResponseEntity<JSONObject> sentAirflowPost(String url, JSONObject json, RestTemplate restTemplate){
        logger.info("Access airFlow URL: " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
        headers.add(ACCEPT, ACCEPT_ALL);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(json.toJSONString(), headers), JSONObject.class);
    }


    // GET 请求
    public static ResponseEntity<JSONObject> sendGetRequest(String url, Map<String, String> headers, RestTemplate restTemplate) {
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, ACCEPT_ALL);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
        return response;
    }

    // POST 请求
    public static ResponseEntity<JSONObject> sendPostRequest(String url, Map<String, String> headers, JSONObject body, RestTemplate restTemplate) {
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, ACCEPT_ALL);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        System.out.println("spturl:" + url);
        System.out.println("header:"+headers);
        System.out.println("boady:" + body);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.POST, entity, JSONObject.class);
        return response;
    }

    // PUT 请求
    public static ResponseEntity<JSONObject> sendPutRequest(String url, Map<String, String> headers, JSONObject body, RestTemplate restTemplate) {
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, ACCEPT_ALL);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.PUT, entity, JSONObject.class);
        return response;
    }

    // DELETE 请求
    public static ResponseEntity<JSONObject> sendDeleteRequest(String url, Map<String, String> headers, RestTemplate restTemplate) {
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        headers.put(ACCEPT, ACCEPT_ALL);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<JSONObject> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, JSONObject.class);
        return response;
    }


}
