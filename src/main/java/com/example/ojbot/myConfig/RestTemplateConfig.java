package com.example.ojbot.myConfig;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Configuration
@Data
@Slf4j
public class RestTemplateConfig {

    private static RestTemplate restTemplate;


//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//    private static RestTemplate restTemplate = new RestTemplate();

//    @Bean
//    public RestTemplate restTemplate() {
//        return restTemplate();
//    }

//    @Bean
//    RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
//        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
//        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        for (HttpMessageConverter c : messageConverters) {
//            if (c instanceof StringHttpMessageConverter) {
//                ((StringHttpMessageConverter) c).setDefaultCharset(Charset.forName("utf-8"));
//            }
//        }
//        return restTemplate;
//    }
////    @Bean
//    @ConfigurationProperties(prefix = "spring.resttemplate")
//    HttpClientProperties httpClientProperties() {
//        return new HttpClientProperties();
//    }
//    @Bean
//    ClientHttpRequestFactory clientHttpRequestFactory(HttpClientProperties httpClientProperties) {
//        //如果不使用HttpClient的连接池，则使用restTemplate默认的SimpleClientHttpRequestFactory,底层基于HttpURLConnection
//        if (!httpClientProperties.isUseHttpClientPool()) {
//            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//            factory.setConnectTimeout(httpClientProperties.getConnectTimeout());
//            factory.setReadTimeout(httpClientProperties.getReadTimeout());
//            return factory;
//        }
//        //HttpClient4.3及以上版本不手动设置HttpClientConnectionManager,默认就会使用连接池PoolingHttpClientConnectionManager
//        HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(httpClientProperties.getMaxTotalConnect())
//                .setMaxConnPerRoute(httpClientProperties.getMaxConnectPerRoute()).evictExpiredConnections()
//                .evictIdleConnections(5000, TimeUnit.MILLISECONDS).setKeepAliveStrategy(connectionKeepAliveStrategy()).build();
//        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        factory.setConnectTimeout(httpClientProperties.getConnectTimeout());
//        factory.setReadTimeout(httpClientProperties.getReadTimeout());
//        factory.setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout());
//
//        return factory;
//    }
//
////    private static final RestTemplate restTemplate = new RestTemplate();
////
////    public static RestTemplate getR() {
////        return restTemplate;
////    }
//
//    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy(){
//        return (response, context) -> {
//            // Honor 'keep-alive' header
//            HeaderElementIterator it = new BasicHeaderElementIterator(
//                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
//            while (it.hasNext()) {
//                HeaderElement he = it.nextElement();
//                String param = he.getName();
//                String value = he.getValue();
//                if (value != null && "timeout".equalsIgnoreCase(param)) {
//                    try {
//                        return Long.parseLong(value) * 1000;
//                    } catch(NumberFormatException ignore) {
//                        log.error("解析长连接过期时间异常",ignore);
//                    }
//                }
//            }
//            HttpHost target = (HttpHost) context.getAttribute(
//                    HttpClientContext.HTTP_TARGET_HOST);
//            //如果请求目标地址,单独配置了长连接保持时间,使用该配置
//            Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(httpClientPoolConfig.getKeepAliveTargetHost()).orElseGet(HashMap::new)
//                    .entrySet().stream().filter(
//                            e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();
//            //否则使用默认长连接保持时间
//            return any.map(en -> en.getValue() * 1000L).orElse(httpClientPoolConfig.getKeepAliveTime() * 1000L);
//        };
//    }


//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpClientPoolConfig httpClientPoolConfig;


//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate(clientHttpRequestFactory());
//    }


    /**
     * 创建HTTP客户端工厂
     */
    //name = "clientHttpRequestFactory"
    @Bean()
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        /**
         *  maxTotalConnection 和 maxConnectionPerRoute 必须要配
         */
        if (httpClientPoolConfig.getMaxTotalConnect() <= 0) {
            throw new IllegalArgumentException("invalid maxTotalConnection: " + httpClientPoolConfig.getMaxTotalConnect());
        }
        if (httpClientPoolConfig.getMaxConnectPerRoute() <= 0) {
            throw new IllegalArgumentException("invalid maxConnectionPerRoute: " + httpClientPoolConfig.getMaxConnectPerRoute());
        }
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(httpClientPoolConfig.getConnectTimeout());
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(httpClientPoolConfig.getReadTimeout());
        // 从连接池获取请求连接的超时时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(httpClientPoolConfig.getConnectionRequestTimout());
        return clientHttpRequestFactory;
    }

    /**
     * 初始化RestTemplate,并加入spring的Bean工厂，由spring统一管理
     */

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //name = "httpClientTemplate"


    @Bean(name = "httpClientTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        System.out.println(factory.toString());
        restTemplate = createRestTemplate(factory);
        System.out.println("asdsa");
//        return createRestTemplate(factory);
        return restTemplate;
    }
    /**
     * 配置httpClient
     *
     * @return
     */
    @Bean
    public HttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        try {
            //设置信任ssl访问
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();

            httpClientBuilder.setSSLContext(sslContext);
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    // 注册http和https请求
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();

            //使用Httpclient连接池的方式配置(推荐)，同时支持netty，okHttp以及其他http框架
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 最大连接数
            poolingHttpClientConnectionManager.setMaxTotal(httpClientPoolConfig.getMaxTotalConnect());
            // 同路由并发数
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpClientPoolConfig.getMaxConnectPerRoute());
            //配置连接池
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
            // 重试次数
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(httpClientPoolConfig.getRetryTimes(), true));

            //设置默认请求头
            List<Header> headers = getDefaultHeaders();
            httpClientBuilder.setDefaultHeaders(headers);
            //设置长连接保持策略
            httpClientBuilder.setKeepAliveStrategy(connectionKeepAliveStrategy());
            return httpClientBuilder.build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("初始化HTTP连接池出错", e);
        }
        return null;
    }


    /**
     * 配置长连接保持策略
     * @return
     */
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy(){
        return (response, context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch(NumberFormatException ignore) {
                        log.error("解析长连接过期时间异常",ignore);
                    }
                }
            }
            HttpHost target = (HttpHost) context.getAttribute(
                    HttpClientContext.HTTP_TARGET_HOST);
            //如果请求目标地址,单独配置了长连接保持时间,使用该配置
            Optional<Map.Entry<String, Integer>> any = Optional.ofNullable(httpClientPoolConfig.getKeepAliveTargetHost()).orElseGet(HashMap::new)
                    .entrySet().stream().filter(
                            e -> e.getKey().equalsIgnoreCase(target.getHostName())).findAny();

            //否则使用默认长连接保持时间
            System.out.println("ok");
            return any.map(en -> en.getValue() * 1000L).orElse(httpClientPoolConfig.getKeepAliveTime() * 1000L);
        };
    }




    /**
     * 设置请求头
     *
     * @return
     */
    private List<Header> getDefaultHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        headers.add(new BasicHeader("Cookie" ,"sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal"));
        return headers;
    }


    private RestTemplate createRestTemplate(ClientHttpRequestFactory factory) {

        System.out.println("createRestTemplate");

        RestTemplate restTemplate = new RestTemplate(factory);

        //我们采用RestTemplate内部的MessageConverter
        //重新设置StringHttpMessageConverter字符集，解决中文乱码问题
        modifyDefaultCharset(restTemplate);

        //设置错误处理器
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        return restTemplate;
    }

    /**
     * 修改默认的字符集类型为utf-8
     *
     * @param restTemplate
     */
    private void modifyDefaultCharset(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (StringHttpMessageConverter.class == item.getClass()) {
                converterTarget = item;
                break;
            }
        }
        if (null != converterTarget) {
            converterList.remove(converterTarget);
        }
        Charset defaultCharset = Charset.forName(httpClientPoolConfig.getCharset());
        converterList.add(1, new StringHttpMessageConverter(defaultCharset));
    }


}
