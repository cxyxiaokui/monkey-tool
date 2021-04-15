package cn.zqmy.monkeytool.http;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import cn.zqmy.monkeytool.http.properties.RestClientProperties;
import com.alibaba.fastjson.TypeReference;
import com.arronlong.httpclientutil.common.HttpConfig;
import org.apache.http.client.HttpClient;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 抱石云 HTTP API Client 基础封装类 含 链路Id传递和AKSK集成
 *
 * @Author: zhuoqianmingyue
 * @Date: 2021/03/05
 * @Description:
 **/
public abstract class AbstractMonkeyApiClient extends BaseRestClient {

    @Resource
    private RestClientProperties restClientProperties;

    @Resource
    private HttpClient httpClient;

    @PostConstruct
    public void setHttpClient() {
        super.setCustomHttpClient(httpClient);
    }

    // ---------------------------------------------------------------- static Http GET start

    /**
     * Get 请求
     *
     * @param url 请求 URL
     * @return 响应结果
     */
    public String get(String url) throws Exception {
        return getByParamMap(url, null, null);
    }

    /**
     * Get 请求
     *
     * @param url 请求 URL
     * @return 响应结果
     */
    public <T> T get(String url, TypeReference<T> type) throws Exception {
        return getByParamMap(url, null, type);
    }

    /**
     * Get 请求
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @return 响应结果
     */
    public String get(String url, Object param) throws Exception {
        Map<String, Object> paramMap = objectToMap(param);
        return getByParamMap(url, paramMap, null);
    }

    /**
     * Get 请求
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @param type  响应数据要转换类类型
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T get(String url, Object param, TypeReference<T> type) throws Exception {
        Map<String, Object> paramMap = objectToMap(param);
        return getByParamMap(url, paramMap, type);
    }

    /**
     * Get 请求
     *
     * @param url     请求 URL
     * @param param   请求Object参数
     * @param exclude AK/SK 排除字段 多个字段以逗号分隔
     * @param type    响应数据要转换类类型
     * @param <T>     响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T get(String url, Object param, String exclude, TypeReference<T> type) throws Exception {
        Map<String, Object> paramMap = objectToMap(param);
        return getByParamMap(url, paramMap, exclude, type);
    }

    /**
     * Get 请求
     *
     * @param url      请求 URL
     * @param paramMap 请求 Map 参数
     * @param type     响应数据要转换类类型
     * @param <T>      响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T getByParamMap(String url, Map<String, Object> paramMap, TypeReference<T> type) throws Exception {
        HttpConfig httpConfig = buildGetOrDeleteConfig(url, paramMap);
        return get(httpConfig, null, type);
    }


    /**
     * Get 请求
     *
     * @param url      请求 URL
     * @param paramMap 请求 Map 参数
     * @param exclude  AK/SK 排除字段 多个字段以逗号分隔
     * @param type     响应数据要转换类类型
     * @param <T>      响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T getByParamMap(String url, Map<String, Object> paramMap, String exclude, TypeReference<T> type) throws Exception {
        HttpConfig httpConfig = buildGetOrDeleteConfig(url, paramMap);
        return get(httpConfig, null, type);
    }

    /**
     * httpUrl 转换成 Map
     *
     * @param httpUrl URL字符串
     * @return URL字符串参数 Map
     */
    private Map<String, Object> httpUrlToMap(String httpUrl) {
        Map<String, Object> queryStringMap = MapUtil.newHashMap(16);
        if (StrUtil.isNotBlank(httpUrl)) {
            UrlBuilder builder = UrlBuilder.ofHttp(httpUrl, CharsetUtil.CHARSET_UTF_8);
            Map<CharSequence, CharSequence> map = builder.getQuery().getQueryMap();

            map.forEach((k, v) -> {
                String key = (String) k;
                String value = (String) v;
                queryStringMap.put(key, value);
            });
        }

        return queryStringMap;
    }

    // ---------------------------------------------------------------- static Http GET end

    // ---------------------------------------------------------------- static Http POST start

    /**
     * Post 请求 json参数
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @return 响应结果
     */
    public String post(String url, Object param) throws Exception {
        return post(url, param, null);
    }

    /**
     * Post 请求 json参数
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @param type  响应数据要转换类类型
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T post(String url, Object param, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildJsonHttpConfig(url, param);
        return post(httpConfig, null, type);
    }


    /**
     * POST 请求 json参数
     *
     * @param url     请求 URL
     * @param param   请求Object参数
     * @param exclude AK/SK 排除字段 多个字段以逗号分隔
     * @param type    响应数据要转换类类型
     * @param <T>     响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T post(String url, Object param, String exclude, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildJsonHttpConfig(url, param);
        return post(httpConfig, null, type);
    }

    /**
     * Post Form表单请求
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @return 响应结果
     */
    public String postForm(String url, Object param) throws Exception {
        return postForm(url, param, null);
    }

    /**
     * Post Form表单请求
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @param type  响应数据要转换类类型
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T postForm(String url, Object param, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildFormHttpConfig(url, param);
        return post(httpConfig, null, type);
    }


    /**
     * POST Form表单请求
     *
     * @param url     请求 URL
     * @param param   请求Object参数
     * @param exclude AK/SK 排除字段 多个字段以逗号分隔
     * @param type    响应数据要转换类类型
     * @param <T>     响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T postForm(String url, Object param, String exclude, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildFormHttpConfig(url, param);
        return post(httpConfig, null, type);
    }

    /**
     * 构建以json格式字符串作为的请求配置类
     *
     * @param url   请求 URL
     * @param param HTTP 请求 Object 参数
     * @return 请求配置类
     */
    protected HttpConfig buildJsonHttpConfig(String url, Object param) {
        return genJsonRequestHttpConfig(url, JSONUtil.toJsonStr(param));
    }


    /**
     * 构建以Form表单请求配置类
     *
     * @param url   请求 URL
     * @param param HTTP 请求 Object 参数
     * @return 请求配置类
     */
    protected HttpConfig buildFormHttpConfig(String url, Object param) {
        Map<String, Object> paramMap = objectToMap(param);
        Map<String, Object> noNullMap = MapUtil.filter(paramMap, (Filter<Map.Entry<String, Object>>) t -> t.getValue() != null);

        return genFormRequestHttpConfig(url, noNullMap);
    }

    // ---------------------------------------------------------------- static Http POST end

    // ---------------------------------------------------------------- static Http PUT start

    /**
     * PUT 请求 json参数
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T put(String url, Object param) throws Exception {
        return put(url, param, null);
    }

    /**
     * PUT 请求 json参数
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @param type  响应数据要转换类类型
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T put(String url, Object param, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildJsonHttpConfig(url, param);
        return put(httpConfig, null, type);
    }

    /**
     * put 请求 json参数
     *
     * @param url     请求 URL
     * @param param   请求Object参数
     * @param exclude AK/SK 排除字段 多个字段以逗号分隔
     * @param type    响应数据要转换类类型
     * @param <T>     响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T put(String url, Object param, String exclude, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildJsonHttpConfig(url, param);
        return put(httpConfig, null, type);
    }

    // ---------------------------------------------------------------- static Http PUT end

    // ---------------------------------------------------------------- static Http DELETE start

    /**
     * DELETE 请求
     *
     * @param url   请求 URL
     * @param param 请求Object参数
     * @param type  响应数据要转换类类型
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T delete(String url, Object param, TypeReference<T> type) throws Exception {

        Map<String, Object> paramMap = objectToMap(param);
        return deleteByParamMap(url, paramMap, type);
    }

    /**
     * delete 请求
     *
     * @param param 请求Object参数
     * @param url   请求 URL
     * @param type  响应数据要转换类类型
     * @param <T>   响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T delete(String url, Object param, String exclude, TypeReference<T> type) throws Exception {
        Map<String, Object> paramMap = objectToMap(param);
        return deleteByParamMap(url, paramMap, exclude, type);
    }

    /**
     * delete 请求参数方式和 get 一样
     *
     * @param url  请求 URL
     * @param type 响应数据要转换类类型
     * @param <T>  响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T deleteByParamMap(String url, Map<String, Object> paramMap, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildGetOrDeleteConfig(url, paramMap);
        return delete(httpConfig, null, type);
    }

    /**
     * delete 请求参数方式和 get 一样
     *
     * @param url  请求 URL
     * @param type 响应数据要转换类类型
     * @param <T>  响应数据要转换类类型泛型
     * @return 响应结果
     */
    public <T> T deleteByParamMap(String url, Map<String, Object> paramMap, String exclude, TypeReference<T> type) throws Exception {

        HttpConfig httpConfig = buildGetOrDeleteConfig(url, paramMap);
        return delete(httpConfig, null, type);
    }

    // ---------------------------------------------------------------- static Http DELETE end

    /**
     * 构建 POST 请求 默认 HttpConfig json 参数形式
     *
     * @param url  请求 URL
     * @param json 以json格式字符串作为参数
     * @return HttpConfig 请求配置类
     */
    public HttpConfig genJsonRequestHttpConfig(String url, String json) {

        return HttpConfig
                .custom()
                .url(url)
                .json(json)
                .timeout(restClientProperties.getTimeout());
    }

    /**
     * 构建 POST 请求 Form 表单参数形式 HttpConfig multipart/form-data x-www-form-urlencoded
     *
     * @param url      请求 URL
     * @param formMaps 表单数据
     * @return HttpConfig 请求配置类
     */
    public HttpConfig genFormRequestHttpConfig(String url, Map<String, Object> formMaps) {

        return HttpConfig
                .custom()
                .url(url)
                .map(formMaps)
                .timeout(restClientProperties.getTimeout());
    }

    /**
     * 构建 GET 获取 删除的 HTTPConfig
     *
     * @param url      请求 URL
     * @param paramMap Map参数集合
     * @return 请求配置类
     */
    protected HttpConfig buildGetOrDeleteConfig(String url, Map<String, Object> paramMap) {
        String requestUrl = url + buildQueryString(paramMap);

        return HttpConfig
                .custom()
                .url(requestUrl)
                .timeout(restClientProperties.getTimeout());
    }

    /**
     * Object 请求参数转换为Map
     *
     * @param param Object 请求参数
     * @return Map参数集合
     */
    protected Map<String, Object> objectToMap(Object param) {
        Map<String, Object> paramMap = null;
        if (param == null) {
            paramMap = MapUtil.newHashMap(16);
        }

        if (param != null) {
            if (!(param instanceof Map)) {
                paramMap = BeanUtil.beanToMap(param);
            }

            if ((param instanceof Map)) {
                paramMap = (Map<String, Object>) param;
            }

        }
        return paramMap;
    }

    /**
     * 将Map形式的Form表单数据转换为Url参数形式
     *
     * @param param 表单数据
     * @return url参数
     */
    protected String buildQueryString(Map<String, Object> param) {
        String query = "";
        if (CollectionUtil.isNotEmpty(param)) {
            query = URLUtil.buildQuery(param, Charset.defaultCharset());
            query = StrUtil.isEmpty(query) ? "" : "?" + query;
        }
        return query;
    }
}
