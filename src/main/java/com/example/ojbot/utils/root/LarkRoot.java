package com.example.ojbot.utils.root;

import com.example.ojbot.pojo.dto.param.RootUrl;
import com.example.ojbot.utils.request.RequestUtils;
import com.example.ojbot.utils.request.dto.Param;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author tzih
 * @date 2022.10.02
 */

@Data
@Component
@ConfigurationProperties(prefix = "lark-robot")
public class LarkRoot {

    private Boolean dev;

    private String url;
    
    private String profile;

//    @Autowired
//    private RestTemplate restTemplate;
    private static RestTemplate restTemplate = new RestTemplate();

    @Resource
    private RootUrl rootUrl;
    

    public void setDev(Boolean dev) {
        this.dev = dev;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;

    }


    public void sendMessage(Object message, Integer id) {
        this.url = rootUrl.getUrl().get(id);
        if ( !dev) {

            System.out.println("ok");
            System.out.println(url);
            String[] split = message.toString().split("n");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{\"post\":");
            stringBuilder.append("{\"zh_cn\":{\"title\":\"完成排行榜");
            stringBuilder.append("\",\"content\":[");
//            stringBuilder.append("[{\"tag\":\"text\",\"text\":\""+"asdsa" +"\"}]");
            int i =0;

            for (i = 0; i < split.length; ++i) {
                if (i != split.length -1) {
                    stringBuilder.append("[{\"tag\":\"text\",\"text\":\""+split[i] +"\"}],");
                }
                else {
                    stringBuilder.append("[{\"tag\":\"text\",\"text\":\""+split[i] +"\"}]");
                }
            }
            stringBuilder.append("]}}");
            stringBuilder.append("}");
            System.out.println(stringBuilder);
//            Object post = RequestUtils.POST(
//                    getUrl(),
//                    new Param(
//                            "content", "{\"post\":\"" + stringBuilder.toString() + "\"}",
//                            "msg_type", "post"
//                    ));
            Object post = RequestUtils.POST(
                    getUrl(),
                    new Param(
                            "content", stringBuilder.toString(),
                            "msg_type", "post"
                    ));
//            System.out.println(text);
            System.out.println(post);
            System.out.println("post ok");
        }

    }

}
