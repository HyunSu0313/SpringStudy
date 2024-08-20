package hello.boot;

import hello.servlet.HelloServlet;
import hello.spring.HelloConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class MySpringApplication {

    public static void run(Class configClass, String[] args) {
        System.out.println("MySpringApplication.main args=" + List.of(args));
        //��Ĺ ����
        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        //������ �����̳� ����
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(configClass);

        //������ MVC ����ó ���� ����, ������ �����̳� ����
        DispatcherServlet dispatcher = new DispatcherServlet(appContext);

        String docBase = null;
        try {
            docBase = Files.createTempDirectory("tomcat-basedir").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //����ó ���� ���
        Context context = tomcat.addContext("", docBase);
        tomcat.addServlet("","dispatcher",dispatcher);
        context.addServletMappingDecoded("/", "dispatcher");
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
