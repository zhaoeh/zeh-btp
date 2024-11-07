package jp.co.futech.module.insight.utils;

import com.alibaba.fastjson2.JSONObject;
import jp.co.futech.module.insight.entity.ms.EventTaskChartInfoEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static jp.co.futech.framework.common.exception.util.ServiceExceptionUtil.exception;
import static jp.co.futech.module.insight.exception.ErrorCodeConstants.*;

public class SupersetUtils {
    public static String getAccessToken(String spsetUrl, String spsetUserName, String spsetPassword, RestTemplate restTemplate){
        String spsetSecurityUrl=spsetUrl+"/api/v1/security/login";
        Map<String, String> headerMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", spsetUserName);
        jsonObject.put("password", spsetPassword);
        jsonObject.put("provider", "db");
        jsonObject.put("refresh", true);
        ResponseEntity<JSONObject> reponse = HttpUtils.sendPostRequest(spsetSecurityUrl, headerMap, jsonObject, restTemplate);
        HttpStatusCode statusCode = reponse.getStatusCode();
        JSONObject reponseBody = reponse.getBody();
        if(statusCode.value() != 200 || reponseBody == null || reponseBody.isEmpty()) {
            throw exception(GETSPSET_ACCESS_TOKEN_FAILED);
        }
        String accessToken = reponseBody.getString("access_token");
        return accessToken;
    }


    public static int createDataset(String spsetUrl, int spsetDatabaseId, String spsetSchema, String accessToken, String sql, String tableName, RestTemplate restTemplate) {
        String spsetCreateDatasetUrl=spsetUrl+"/api/v1/dataset/";
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Bearer " + accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("database", spsetDatabaseId);
        jsonObject.put("schema",spsetSchema);
        jsonObject.put("sql", sql);
        jsonObject.put("table_name", tableName);
        ResponseEntity<JSONObject> reponse = HttpUtils.sendPostRequest(spsetCreateDatasetUrl, headerMap, jsonObject, restTemplate);
        HttpStatusCode statusCode = reponse.getStatusCode(); //返回码201
        JSONObject reponseBody = reponse.getBody();
        if(statusCode.value() != 201 || reponseBody == null || reponseBody.isEmpty()) {
            throw exception(CREATE_DATASET_FAILED);
        }
        return reponseBody.getInteger("id");
    }

    public static int createChart(String spsetUrl, String accessToken, String datasourceType, String sliceName, int datasourceId, RestTemplate restTemplate){
        String spsetCreateChartUrl = spsetUrl+"/api/v1/chart/";
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Bearer " + accessToken);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("datasource_id", datasourceId);
        jsonObject.put("datasource_type",datasourceType);
        jsonObject.put("slice_name", sliceName);
        ResponseEntity<JSONObject> response = HttpUtils.sendPostRequest(spsetCreateChartUrl, headerMap, jsonObject, restTemplate);
        HttpStatusCode statusCode = response.getStatusCode();//返回码201
        JSONObject responseBody = response.getBody();
        if(statusCode.value() != 201 || responseBody == null || responseBody.isEmpty()) {
            throw exception(CREATE_CHART_FAILED);
        }
        Integer sliceId = responseBody.getInteger("id");
        return sliceId;
    }
    public static void refreshDataset(String spsetUrl, String accessToken, int datasourceId, RestTemplate restTemplate){
        String spsetCreateChartUrl = spsetUrl+"/api/v1/dataset/" + datasourceId + "/refresh";
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Bearer " + accessToken);
        JSONObject jsonObject = new JSONObject();
        ResponseEntity<JSONObject> response = HttpUtils.sendPutRequest(spsetCreateChartUrl, headerMap, jsonObject, restTemplate);
        HttpStatusCode statusCode = response.getStatusCode();//返回码201
        JSONObject responseBody = response.getBody();
        if(statusCode.value() != 200 || responseBody == null || responseBody.isEmpty()) {
            throw exception(REFRESH_DATASET_FAILED);
        }

    }
    public static void deleteDataset(String spsetUrl, String accessToken, int datasourceId, RestTemplate restTemplate){
        String spsetCreateChartUrl = spsetUrl+"/api/v1/dataset/" + datasourceId;
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("Authorization","Bearer " + accessToken);
        ResponseEntity<JSONObject> response = HttpUtils.sendDeleteRequest(spsetCreateChartUrl, headerMap, restTemplate);
        HttpStatusCode statusCode = response.getStatusCode();//返回码201
        JSONObject responseBody = response.getBody();
        if(statusCode.value() != 200 || responseBody == null || responseBody.isEmpty()) {
            throw exception(DELETE_DATASET_FAILED);
        }
    }
}
