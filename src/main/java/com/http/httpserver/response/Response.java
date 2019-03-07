package com.http.httpserver.response;

import com.xml.XmlUtils;

import java.nio.channels.SelectionKey;

public interface Response {

    //服务器名字
    public static final String SERVER_NAME = XmlUtils.getRootElement("server.xml").element("serverName").getText();

    public String getContentType();

    public int getStatuCode();

    public String getStatuCodeStr();

    public String getHtmlFile();

    public void setHtmlFile(String htmlFile);

    public SelectionKey getKey();

    public void setContentType(String contentType);

    public void setStatuCode(int statuCode);

    public void setStatuCodeStr(String statuCodeStr);
}
