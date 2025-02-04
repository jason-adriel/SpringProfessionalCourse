package org.jasonadriel;

import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.jasonadriel.context.BankApplicationConfiguration;
import org.jasonadriel.web.TransactionServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        int port = System.getProperty("server.port") == null ? 8080 :
                Integer.parseInt(System.getProperty("server.port"));
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        Context tomcatCtx = tomcat.addContext("", null);
        WebApplicationContext webCtx = getWebApplicationContext(tomcatCtx.getServletContext());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webCtx);
        Wrapper servlet = Tomcat.addServlet(tomcatCtx, "bankServlet", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    public static WebApplicationContext getWebApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(BankApplicationConfiguration.class);
        context.setServletContext(servletContext);
        context.refresh();
        context.registerShutdownHook();
        return context;
    }
}