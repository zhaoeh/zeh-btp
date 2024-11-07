package jp.co.futech.module.system.util;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.quickbi_public20220101.AsyncClient;
import com.aliyun.sdk.service.quickbi_public20220101.models.CreateTicketRequest;
import com.aliyun.sdk.service.quickbi_public20220101.models.CreateTicketResponse;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AliQuickBiTool {
    private static final String accessKeyId = "LTAI5tLrLeDz3x2j9kwha2Xv";
    private static final String accessKeySecret = "8CO8Y4vndtBcQJdjzI0hcpCrHCztie";

    private static final String BI_HOST = "https://bi.aliyun.com/";
    private static final String BI_HOST_HOME_INTL = "https://bi-ap-southeast-1.data.aliyun.com/";

    public enum BiType {
        DASHBOARD("token3rd/dashboard/view/pc.htm"), REPORT("token3rd/report/view.htm"), OFFLINE("token3rd/offline/view/pc.htm"), SCREEN("token3rd/screen/view/pc.htm");

        private final String path;

        BiType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

    public static String getUrl(String workId, String ticket, BiType biType) {
        return String.format("%s%s?pageId=%s&accessTicket=%s", BI_HOST_HOME_INTL, biType.getPath(), workId, ticket);
    }

    public static String buildTicket(String worksId) throws ExecutionException, InterruptedException {

        // HttpClient Configuration
        /*HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
                .connectionTimeout(Duration.ofSeconds(10)) // Set the connection timeout time, the default is 10 seconds
                .responseTimeout(Duration.ofSeconds(10)) // Set the response timeout time, the default is 20 seconds
                .maxConnections(128) // Set the connection pool size
                .maxIdleTimeOut(Duration.ofSeconds(50)) // Set the connection pool timeout, the default is 30 seconds
                // Configure the proxy
                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
                // If it is an https connection, you need to configure the certificate, or ignore the certificate(.ignoreSSL(true))
                .x509TrustManagers(new X509TrustManager[]{})
                .keyManagers(new KeyManager[]{})
                .ignoreSSL(false)
                .build();*/

        // Configure Credentials authentication information, including ak, secret, token
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
//                .accessKeyId(System.getenv("ACCESS_KEY_ID"))
                .accessKeyId(accessKeyId)
//                .accessKeySecret(System.getenv("ACCESS_KEY_SECRET"))
                .accessKeySecret(accessKeySecret)
//                .securityToken(System.getenv("SECURITY_TOKEN")) // use STS token
                .build());

        // Configure the Client
        AsyncClient client = AsyncClient.builder().region("ap-southeast-1") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/quickbi-public
                                .setEndpointOverride("quickbi-public.ap-southeast-1.aliyuncs.com")
                        //.setConnectTimeout(Duration.ofSeconds(30))
                ).build();

        // Parameter settings for API request
        CreateTicketRequest createTicketRequest = CreateTicketRequest.builder().worksId(worksId)
                // Request-level configuration rewrite, can set Http request parameters, etc.
                // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                .build();

        // Asynchronously get the return value of the API request
        CompletableFuture<CreateTicketResponse> response = client.createTicket(createTicketRequest);
        // Synchronously get the return value of the API request
        CreateTicketResponse resp = response.get();

//        System.out.println();
        // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

        // Finally, close the client
        client.close();
        return resp.getBody().getResult();
    }
}
