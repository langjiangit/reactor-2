package com.http.server.simpleserver.impl;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import sun.misc.BASE64Encoder;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



public class DefaultHttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultHttpRequestHandler.class);
//
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(true);
//

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        LOGGER.error(e.getMessage(),e);
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {

        String uri = fullHttpRequest.uri();
        System.out.println("uri111111:"+uri);
        try{
            //拿到请求的头信息
            HttpHeaders httpHeaders = fullHttpRequest.headers();
            for(String header:httpHeaders.names()){
                //判断是否是文件提交表单类型
                if(httpHeaders.get(header).contains("multipart/form-data")||httpHeaders.get(header).equals("application/octet-stream")){
                    HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, fullHttpRequest,
                            Charset.forName("UTF-8"));
                    List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
                    for (InterfaceHttpData data : datas) {
                        //取文件流,把文件转化成base64加密的字符串
                        if (data.getHttpDataType() == HttpDataType.FileUpload) {
                            FileUpload fileUpload = (FileUpload) data;
                            String fileName = fileUpload.getFilename();
                            LOGGER.info("fileName:"+fileName);
                            //拿到文件流
                            byte[]b = fileUpload.get();
                            BASE64Encoder encoder = new BASE64Encoder();
                            //把加密后的文件流传进接口，输入参数为file,可自定义，一定要在请求的接口的请求参数里配置上改字段
                            uri = uri+"&file="+encoder.encode(b);
                        }else{
                            //除文件流的其它参数
                            Attribute attribute = (Attribute) data;
                            LOGGER.info(attribute.getName()+":"+attribute.getValue());
                            uri = uri+"&"+attribute.getName()+"="+attribute.getValue();
                        }
                    }

//                    LOGGER.info("uri:"+uri);



                    fullHttpRequest = new DefaultFullHttpRequest(fullHttpRequest.protocolVersion(), HttpMethod.GET, uri);
                    break;
                }



            }

            Class clazz = Class.forName("com.http.handle.BaseHandle");
            Constructor constructor = clazz.getConstructor();
            Method method= clazz.getMethod("run",FullHttpRequest.class);

            method.invoke(constructor.newInstance(),fullHttpRequest);


        }catch(Exception e){
            LOGGER.info("文件流获取异常",e);
        }

    }


    /**
     * 获取post请求、get请求的参数保存到map中
     */
    private Map<String, String> getRequestParams(ChannelHandlerContext ctx, HttpRequest req){
        Map<String, String>requestParams=new HashMap<String, String>();
        // 处理get请求
        if (req.method() == HttpMethod.GET) {
            QueryStringDecoder decoder = new QueryStringDecoder(req.getUri());
            Map<String, List<String>> parame = decoder.parameters();
            Iterator<Entry<String, List<String>>> iterator = parame.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String, List<String>> next = iterator.next();
                requestParams.put(next.getKey(), next.getValue().get(0));
            }
        }
        // 处理POST请求
        if (req.getMethod() == HttpMethod.POST) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false), req);
            List<InterfaceHttpData> postData = decoder.getBodyHttpDatas(); //
            for(InterfaceHttpData data:postData){
                if (data.getHttpDataType() == HttpDataType.Attribute) {
                    MemoryAttribute attribute = (MemoryAttribute) data;
                    requestParams.put(attribute.getName(), attribute.getValue());
                }
            }
        }
        return requestParams;
    }



}
