package com.http.httpserver.handle.impl;

import com.http.httpserver.context.Context;
import com.http.httpserver.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundHandler extends AbstractHandler{
    private Logger logger = LoggerFactory.getLogger(NotFoundHandler.class);
    private Response response;

    @Override
    public void doGet(Context context) {
        logger.info("进入了404Handler");
        response = context.getResponse();

        response.setStatuCode(404);
        response.setStatuCodeStr("Not Found");
        response.setHtmlFile("404.html");
    }
}
