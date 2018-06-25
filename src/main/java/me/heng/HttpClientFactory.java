package me.heng;

/**
 * AUTHOR: wangdi
 * DATE: 20/06/2018
 * TIME: 5:12 PM
 */
public class HttpClientFactory {
    private static HttpAsyncClient httpAsyncClient = new HttpAsyncClient();

    private static HttpSyncClient httpSyncClient = new HttpSyncClient();

    private HttpClientFactory() {
    }

    private static HttpClientFactory httpClientFactory = new HttpClientFactory();



    public static HttpClientFactory getInstance() {

        return httpClientFactory;

    }

    protected HttpAsyncClient getHttpAsyncClientPool() {
        return httpAsyncClient;
    }

    protected HttpSyncClient getHttpSyncClientPool() {
        return httpSyncClient;
    }
}
