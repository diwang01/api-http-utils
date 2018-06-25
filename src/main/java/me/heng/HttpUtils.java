package me.heng;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import me.heng.model.HttpDeleteWithBody;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;


/**
 * AUTHOR: wangdi
 * DATE: 20/06/2018
 * TIME: 5:33 PM
 */
public class HttpUtils {

    private static Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    private static String utf8Charset = "UTF-8";

    private static Joiner joiner = Joiner.on("&").skipNulls();


    /**
     * map转化为NameValuePair
     *
     * @param kvs
     */
    private static List<NameValuePair> getParamters(Map<String, Object> kvs) {
        List<NameValuePair> nameValuePairs = Lists.newArrayList();
        if (kvs != null) {
            for (Map.Entry<String, Object> entry : kvs.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            return nameValuePairs;
        }
        return null;
    }

    /**
     * HttpPut
     *
     * @param baseUrl
     * @param kvs
     * @param headers
     * @return
     */
    public static String httpSyncPut(String baseUrl, Map<String, Object> kvs, List<Header> headers) {
        CloseableHttpClient httpClient = HttpClientFactory.getInstance().getHttpSyncClientPool().getHttpClient();
        List<NameValuePair> params = getParamters(kvs);
        HttpPut httpPut = new HttpPut(baseUrl);
        Header[] hs = new Header[headers.size()];
        httpPut.setHeaders(hs);

        LOG.warn("==== Parameters ======" + params);
        CloseableHttpResponse response = null;
        try {
            httpPut.setEntity(new UrlEncodedFormEntity(params));
//            httpPut.setHeader("Connection","close");
            response = httpClient.execute(httpPut);
            LOG.warn("========HttpResponseProxy：========" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, utf8Charset);
                LOG.warn("========Response=======" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * DELETE METHOD
     *
     * @param baseUrl
     * @param headers
     * @return
     */
    public static String httpSyncDelete(String baseUrl, List<Header> headers) {
        CloseableHttpClient httpClient = HttpClientFactory.getInstance().getHttpSyncClientPool().getHttpClient();
        HttpDelete httpDelete = new HttpDelete(baseUrl);
        Header[] hs = new Header[headers.size()];
        httpDelete.setHeaders(hs);

//        LOG.warn("==== Parameters ======" + body);
        CloseableHttpResponse response = null;
        try {
//            httpDelete.setHeader("Connection","close");
            response = httpClient.execute(httpDelete);
            LOG.warn("========HttpResponseProxy：========" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, utf8Charset);
                LOG.warn("========Response=======" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * DELETE WITH JSON BODY METHOD
     *
     * @param baseUrl
     * @param kvs
     * @param headers
     * @return
     */
    public static String httpSyncDeleteWithBody(String baseUrl, Map<String, Object> kvs, List<Header> headers) {
        CloseableHttpClient httpClient = HttpClientFactory.getInstance().getHttpSyncClientPool().getHttpClient();
//        List<NameValuePair> params = Lists.newArrayList();
        String body = StringUtils.EMPTY;
        if (kvs != null) {
            body = JSONObject.toJSONString(kvs);
        }
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(baseUrl);
        Header[] hs = new Header[headers.size()];
        httpDelete.setHeaders(hs);

        LOG.warn("==== Parameters ======" + body);
        CloseableHttpResponse response = null;
        try {
            httpDelete.setEntity(new StringEntity(body));
//            httpDelete.
//            httpPost.setHeader("Connection","close");
            response = httpClient.execute(httpDelete);
            LOG.warn("========HttpResponseProxy：========" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, utf8Charset);
                LOG.warn("========Response=======" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static String httpSyncGet(String baseUrl, Map<String, Object> kvs, List<Header> headers) {
        CloseableHttpClient httpClient = HttpClientFactory.getInstance().getHttpSyncClientPool().getHttpClient();
        StringBuilder sb = new StringBuilder(baseUrl).append("?");
        //构造baseUrl
        sb.append(joiner.withKeyValueSeparator("=").join(kvs));
        HttpGet httpGet = new HttpGet(sb.toString());
        //构造headers
        Header[] hs = new Header[headers.size()];
        httpGet.setHeaders(hs);
//        LOG.warn("==== Parameters ======" + params);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            LOG.warn("========HttpResponseProxy：========" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, utf8Charset);
                LOG.warn("========Response=======" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            LOG.error(e.getCause().getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 向指定的url发送一次post请求,参数是List<NameValuePair>
     *
     * @param baseUrl 请求地址
     * @param kvs     请求参数,格式是Map<String, Object>
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public static String httpSyncPost(String baseUrl, Map<String, Object> kvs, List<Header> headers) {

        CloseableHttpClient httpClient = HttpClientFactory.getInstance().getHttpSyncClientPool().getHttpClient();
        List<NameValuePair> params = getParamters(kvs);
        HttpPost httpPost = new HttpPost(baseUrl);
        Header[] hs = new Header[headers.size()];
        httpPost.setHeaders(hs);
        //Parameters
        LOG.warn("==== Parameters ======" + params);
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
//            httpPost.setHeader("Connection","close");
            response = httpClient.execute(httpPost);
            LOG.warn("========HttpResponseProxy：========" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, utf8Charset);
                LOG.warn("========Response=======" + result);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 向指定的url发送一次异步post请求,参数是字符串
     *
     * @param baseUrl   请求地址
     * @param urlParams 请求参数,格式是List<BasicNameValuePair>
     * @param callback  回调方法,格式是FutureCallback
     * @return 返回结果, 请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public static void httpAsyncPost(String baseUrl, List<BasicNameValuePair> postBody,
                                     List<BasicNameValuePair> urlParams, FutureCallback callback) throws Exception {
        if (baseUrl == null || "".equals(baseUrl)) {
            throw new Exception("missing base url");
        }

        try {
            CloseableHttpAsyncClient hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                    .getAsyncHttpClient();
            hc.start();
            HttpPost httpPost = new HttpPost(baseUrl);
//            httpPost.setHeader("Connection","close");
            if (null != postBody) {
                LOG.debug("exeAsyncReq post postBody={}", postBody);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                        postBody, utf8Charset);
                httpPost.setEntity(entity);
            }
            if (null != urlParams) {
                String getUrl = EntityUtils
                        .toString(new UrlEncodedFormEntity(urlParams));
                httpPost.setURI(new URI(httpPost.getURI().toString()
                        + "?" + getUrl));
            }
            LOG.warn("exeAsyncReq getparams:" + httpPost.getURI());
            hc.execute(httpPost, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
