package com.example.ojbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

@SpringBootTest
class OjBotApplicationTests {

//    private RestTemplateConfig restTemplateConfig = new RestTemplateConfig();

//    @Autowired
//    private RestTemplate restTemplate;
    @Test
    void contextLoads() {
//        RestTemplate restTemplate = restTemplateConfig.restTemplate();

        String url = "http://code.sipcoj.com/api/profile?username=22-何智博";
//        Object forObject = restTemplate.getForObject(url, String.class);
//        System.out.println(forObject.toString());

//        HttpHeaders httpHeaders = new HttpHeaders();
////       httpHeaders.set("Cookie", "_ga=GA1.2.2048795898.1663412801; csrftoken=v7W9wLfNgUFB9wgVlsdhHnHSrWZLxBhThEVGi13tzG6g2pUGEp4Pd6e5eeVJ8OW3; sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal; _gid=GA1.2.2056050097.1664683520");
//        httpHeaders.set("Cookie" ,"sessionid=uf9rll17uh6de5gyk95vygc0ymycgmal");
//        HttpEntity<?> requestEntity = new HttpEntity<>(httpHeaders);

//        System.out.println(requestEntity);
        URL url1;
        try {
             url1 = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 5; ++i) {
            long t1 = new Date().getTime();
//        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
//                JSONObject.class);
            try {
//                ParameterizedTypeReference

//                StringUtil

                URLConnection urlConnection = url1.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long t2 = new Date().getTime();
            System.out.println(t2-t1);
        }

//        System.out.println(exchange);
//        System.out.println(exchange.getBody());

    }

}
