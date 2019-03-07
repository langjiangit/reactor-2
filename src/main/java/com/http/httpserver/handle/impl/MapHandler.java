package com.http.httpserver.handle.impl;

import com.http.httpserver.handle.Handler;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import com.xml.XmlUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHandler {

    //访问路径对应控制类
    private static Map<String, Handler> handlerMap = new HashMap<String, Handler> ();

    private static MapHandler instance = null;

    //将构造器私有化
    private MapHandler(){}

    //得到HandlerMap对象实例
    public static MapHandler getContextMapInstance() {

        if(instance == null) {
            synchronized (MapHandler.class) {
                if(instance == null) {
                    instance = new MapHandler();
                    //得到web.xml的根路径
                    Element rootElement = XmlUtils.getRootElement("web.xml");
                    //得到handler的集合
                    List<Element> handlers = XmlUtils.getElements(rootElement);
                    for (Element element : handlers) {
                        Element urlPattenEle = XmlUtils.getElement(element, "url-patten");
                        //得到urlPatten(uri)
                        String urlPatten = XmlUtils.getElementText(urlPattenEle);
                        Element handlerClazzEle = XmlUtils.getElement(element, "handler-class");
                        //得到handler 的class文件路径
                        String clazzPath = XmlUtils.getElementText(handlerClazzEle);
                        Class<?> clazz = null;
                        try {
                            //通过反射得到handler实例化对象，然后以键值对的形式存储
                            clazz = Class.forName(clazzPath);
                            Handler handler = (Handler)clazz.newInstance();
                            instance.getHandlerMap().put(urlPatten, handler);
                            Logger.getLogger(MapHandler.class).info("成功添加Handler " + clazzPath);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return instance;
    }

    public Map<String, Handler> getHandlerMap() {
        return handlerMap;
    }
}