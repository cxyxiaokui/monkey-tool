package cn.zqmy.monkeytool.http;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.util.Map;
import java.util.Objects;

/**
 * Rest 请求基础功能抽取类
 * <p>
 * HttpConfig 使用示例：
 * String url = "https://github.com/Arronlong/httpclientutil";
 * <p>
 * //最简单的使用：
 * String html = HttpClientUtil.get(HttpConfig.custom().url(url));
 * System.out.println(html);
 * <p>
 * //---------------------------------
 * //			【详细说明】
 * //--------------------------------
 * <p>
 * //插件式配置Header（各种header信息、自定义header）
 * Header[] headers = HttpHeader.custom()
 * .userAgent("javacl")
 * .other("customer", "自定义")
 * .build();
 * <p>
 * //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
 * HCB hcb = HCB.custom()
 * .timeout(1000) //超时
 * .pool(100, 10) //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
 * .sslpv(SSLProtocolVersion.TLSv1_2) 	//设置ssl版本号，默认SSLv3，也可以调用sslpv("TLSv1.2")
 * .ssl()  	  	//https，支持自定义ssl证书路径和密码，ssl(String keyStorePath, String keyStorepass)
 * .retry(5)		//重试5次
 * ;
 * <p>
 * HttpClient client = hcb.build();
 * //插件式配置请求参数（网址、请求参数、编码、client）
 * HttpConfig config = HttpConfig.custom()
 * .headers(headers)	//设置headers，不需要时则无需设置
 * .url(url)	          //设置请求的url
 * .map(map)	          //设置请求参数，没有则无需设置 不会处理GET传递的Map
 * .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
 * .client(client)    //如果只是简单使用，无需设置，会自动获取默认的一个client对象
 * //.inenc("utf-8")  //设置请求编码，如果请求返回一直，不需要再单独设置
 * //.inenc("utf-8")	//设置返回编码，如果请求返回一直，不需要再单独设置
 * //.json("json字符串")  //json方式请求的话，就不用设置map方法，当然二者可以共用。不支持 delete
 * //.context(HttpCookies.custom().getContext()) //设置cookie，用于完成携带cookie的操作
 * //.out(new FileOutputStream("保存地址"))       //下载的话，设置这个方法,否则不要设置
 * //.files(new String[]{"d:/1.txt","d:/2.txt"}) //上传的话，传递文件路径，一般还需map配置，设置服务器保存路径;
 * //使用方式：
 * String result1 = HttpClientUtil.get(config);     //get请求
 * String result2 = HttpClientUtil.post(config);    //post请求
 * //同时返回常用的几类对象：result、header、StatusLine、StatusCode
 * HttpResult respResult = HttpClientUtil.sendAndGetResp(config);
 * System.out.println("返回结果：\n"+respResult.getResult());
 * System.out.println("返回resp-header："+respResult.getRespHeaders());//可以遍历
 * System.out.println("返回具体resp-header："+respResult.getHeaders("Date"));
 * System.out.println("返回StatusLine对象："+respResult.getStatusLine());
 * System.out.println("返回StatusCode："+respResult.getStatusCode());
 * System.out.println("返回HttpResponse对象）（可自行处理）："+respResult.getResp());
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/11/11
 **/
public abstract class BaseRestClient {

    /**
     * 自定义HttpClient 一般采用HttpClient 自带线程池中的对象。
     */
    private HttpClient customHttpClient;

    public BaseRestClient() {
    }

    public BaseRestClient(HttpClient customHttpClient) {
        this.customHttpClient = customHttpClient;
    }


    public HttpClient getCustomHttpClient() {
        return customHttpClient;
    }

    protected void setCustomHttpClient(HttpClient customHttpClient) {
        this.customHttpClient = customHttpClient;
    }

    // ---------------------------------------------------------------- GET start

    /**
     * GET 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param headerMap 请求头Map
     * @param type      响应数据要转换类类型
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T get(HttpConfig config, Map<String, String> headerMap, TypeReference<T> type) throws HttpProcessException {
        return get(config(config, headerMap), type);
    }

    /**
     * GET 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T get(HttpConfig config, TypeReference<T> type) throws HttpProcessException {
        String result = HttpClientUtil.get(config);
        if (type == null) {
            return (T) result;
        }
        return parseObject(result, type);
    }
    // ---------------------------------------------------------------- GET end

    // ---------------------------------------------------------------- POST start

    /**
     * POST 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param headerMap 请求头Map
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException  Http进程异常
     */
    protected <T> T post(HttpConfig config, Map<String, String> headerMap, TypeReference<T> type) throws HttpProcessException {
        return post(config(config, headerMap), type);
    }

    /**
     * POST 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T post(HttpConfig config ,TypeReference<T> type) throws HttpProcessException {
        String result = HttpClientUtil.post(config);
        if (type == null) {
            return (T) result;
        }

        return parseObject(result, type);
    }

    // ---------------------------------------------------------------- POST end

    // ---------------------------------------------------------------- PUT start

    /**
     * PUT 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param headerMap 请求头Map
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T put(HttpConfig config, Map<String, String> headerMap, TypeReference<T> type) throws HttpProcessException {
        config = config(config, headerMap);
        return put(config, type);
    }

    /**
     * PUT 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T put(HttpConfig config, TypeReference<T> type) throws HttpProcessException {
        String result = HttpClientUtil.put(config);
        if (type == null) {
            return (T) result;
        }
        return parseObject(result, type);
    }

    // ---------------------------------------------------------------- PUT end

    // ---------------------------------------------------------------- DELETE start

    /**
     * DELETE 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param headerMap 请求头Map
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T delete(HttpConfig config, Map<String, String> headerMap, TypeReference<T> type) throws HttpProcessException {
        config = config(config, headerMap);
        return delete(config, type);
    }

    /**
     * DELETE 请求 默认采用自定义线程池 HttpClient
     * 调用方请自行异常处理
     *
     * @param config    请求配置类
     * @param type      响应数据要转换类
     * @return 请求响应的对象
     * @throws HttpProcessException Http进程异常
     */
    protected <T> T delete(HttpConfig config, TypeReference<T> type) throws HttpProcessException {
        String result = HttpClientUtil.delete(config);
        if (type == null) {
            return (T) result;
        }
        return parseObject(result, type);
    }

    // ---------------------------------------------------------------- DELETE end

    /**
     * 解析响应数据
     *
     * @param result 响应字符串
     * @param clazz
     * @return
     */
    protected <T> T parseObject(String result, Class<T> clazz) {
        return JSON.parseObject(result, clazz);
    }

    /**
     * 解析响应数据
     *
     * @param result 响应字符串
     * @param type
     * @return  请求响应的对象
     */
    protected <T> T parseObject(String result, TypeReference<T> type) {
        return JSON.parseObject(result, type);
    }

    /**
     * 初始化请求头信息 如果请求头Map 有 Content-Type 则覆盖
     *
     * @param headerMap
     * @return
     */
    protected Header[] mapToHeads(Map<String, String> headerMap) {
        HttpHeader httpHeader = HttpHeader.custom();
        if (CollectionUtil.isEmpty(headerMap)) {
            return null;
        }

        String contentType = headerMap.get("Content-Type");
        if (StrUtil.isNotBlank(contentType)) {
            httpHeader.contentType(contentType);
        }

        headerMap.forEach((k, v) -> {
            httpHeader.other(k, v);
        });
        return httpHeader.build();
    }

    // ---------------------------------------------------------------- Private method end

    /**
     * @param config             请求配置类
     * @param headerMap          请求头参数Map
     * @return
     */
    private HttpConfig config(HttpConfig config, Map<String, String> headerMap) {

        Header[] headers = mapToHeads(headerMap);
        if (headers != null) {
            config.headers(headers);
        }

        HttpClient client = config.client();
        //是否启动线程池
        if (Objects.isNull(client)) {
            if (customHttpClient == null) {
                throw new RuntimeException("customHttpClient is null");
            }
            config.client(customHttpClient);
        }

        return config;
    }

    // ---------------------------------------------------------------- Private method end
}
