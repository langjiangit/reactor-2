package com.http.handle;

import io.netty.handler.codec.http.FullHttpRequest;



import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public  class BaseHandle extends  Handle{

    public BaseHandle(){

    }

//    public void run(String string) {
//        System.out.println(string);
//    }
    @Override
    public void run(FullHttpRequest fullHttpRequest){
        String uri = fullHttpRequest.uri();
        System.out.println("uri2222:"+uri);
//        fullHttpRequest.

    }

//    public static void main(String[] args) throws DocumentException, IOException {
//
//        SAXReader saxReader = new SAXReader();
//        File file = new File("src/main/resources/api.xml");
//        Document document = saxReader.read(file);
//        Element rootElement = document.getRootElement();
//        System.out.println(rootElement.getName());
////        Element rootElement1 = rootElement.get
////        System.out.println(rootElement1.getName());
//        List <Element>elements = rootElement.elements();
//        List<Element> apis = null;
//        System.out.println(elements.size());
//        for (Element element:elements){
//            if (element.getName().equals("apis")){
//                apis = element.elements();
//                break;
//            }
//        }
//        Document doc = DocumentHelper.createDocument();
//        Element element = doc.addElement("api");
////        element.se
//        Element apiName = element.addElement("apiName");
//        apiName.setText("BaseHandle");
//        Element apiPath = element.addElement("apiPath");
//        apiPath.setText(BaseHandle.class.getPackage().getName());
//        apis.add(element);
//        OutputFormat format = OutputFormat.createPrettyPrint();
//        //设置输出编码
//        format.setEncoding("UTF-8");
//        //创建需要写入的File对象
////        File file = new File("D:" + File.separator + "books.xml");
//        //生成XMLWriter对象，构造函数中的参数为需要输出的文件流和格式
//        XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
//        //开始写入，write方法中包含上面创建的Document对象
//        writer.write(document);
//    }


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName("com.http.handle.BaseHandle");
        Constructor constructor = clazz.getConstructor();
        Method method= clazz.getMethod("run",String.class);
//        BaseHandle baseHandle = (BaseHandle)constructor.newInstance();
        method.invoke(constructor.newInstance(),"sss");

    }
}
