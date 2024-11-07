package jp.co.futech.module.system.service.permission;

import com.alibaba.ttl.threadpool.agent.internal.javassist.tools.rmi.Sample;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.gateway.pop.Configuration;
import com.aliyun.sdk.service.quickbi_public20220101.models.CreateTicketRequest;
import com.aliyun.sdk.service.quickbi_public20220101.models.CreateTicketResponse;
import com.aliyuncs.profile.DefaultProfile;
import darabonba.core.client.ClientOverrideConfiguration;
import jp.co.futech.module.system.util.AliQuickBiTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import com.aliyun.sdk.service.quickbi_public20220101.*;

/**
 * @author MYK-Heng.zhang
 */
@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

    String WORK_ID="1cdabc13-09c0-41c6-ab98-fa40d6a1f23c";
    @Override
    public Map<String,String> getTicket() throws ExecutionException, InterruptedException {
        Map<String,String> result=new HashMap<>();
        String ticket = AliQuickBiTool.buildTicket(WORK_ID);
        result.put("title","ticket");
        result.put("url",AliQuickBiTool.getUrl(WORK_ID, ticket, AliQuickBiTool.BiType.DASHBOARD));
        return result;
    }


}
