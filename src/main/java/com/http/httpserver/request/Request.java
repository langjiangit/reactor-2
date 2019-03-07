package com.http.httpserver.request;

import java.util.Map;
import java.util.Set;

public interface Request {

    public static final String POST = "POST";

    public static final String GET = "GET";
    /**
     * 得到参数
     * @param:  @return
     * @return: Map<String,Object>

     */
    public Map<String, Object> getAttribute();

    /**
     * 得到请求方式
     * @param:  @return
     * @return: String

     */
    public String getMethod();

    /**
     * 得到URI
     * @param:  @return
     * @return: String

     */
    public String getUri();

    /**
     * 版本协议
     * @param:  @return
     * @return: String

     */
    public String getProtocol();

    /**
     * 得到请求头Map
     * @param:  @return
     * @return: String

     */
    public Map<String, Object> getHeaders();

    /**
     * 得到请求头参数集合
     * @param:  @return
     * @return: String

     */
    public Set<String> getHeaderNames();

    /**
     * 根据请求头名得到对应的请求头
     * @param:  @return
     * @return: String

     */
    public Object getHeader(String key);
}
