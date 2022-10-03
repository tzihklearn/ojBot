package com.example.ojbot.myConfig;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author tzih
 * @date 2022.10.02
 */
@Component
@Data
public class HttpClientProperties {
    /**
     * 是否使用httpclient连接池
     */
    private boolean useHttpClientPool = true;
    /**
     * 从连接池中获得一个connection的超时时间
     */
    private int connectionRequestTimeout = 3000;
    /**
     * 建立连接超时时间
     */
    private int connectTimeout = 3000;
    /**
     * 建立连接后读取返回数据的超时时间
     */
    private int readTimeout = 5000;
    /**
     * 连接池的最大连接数，0代表不限
     */
    private int maxTotalConnect = 128;
    /**
     * 每个路由的最大连接数
     */
    private int maxConnectPerRoute = 32;


    private int keepAliveTime = 1000000000;
    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }
    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
    public int getConnectTimeout() {
        return connectTimeout;
    }
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
    public int getReadTimeout() {
        return readTimeout;
    }
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
    public int getMaxTotalConnect() {
        return maxTotalConnect;
    }
    public void setMaxTotalConnect(int maxTotalConnect) {
        this.maxTotalConnect = maxTotalConnect;
    }
    public int getMaxConnectPerRoute() {
        return maxConnectPerRoute;
    }
    public void setMaxConnectPerRoute(int maxConnectPerRoute) {
        this.maxConnectPerRoute = maxConnectPerRoute;
    }
    public boolean isUseHttpClientPool() {
        return useHttpClientPool;
    }
    public void setUseHttpClientPool(boolean useHttpClientPool) {
        this.useHttpClientPool = useHttpClientPool;
    }
}

