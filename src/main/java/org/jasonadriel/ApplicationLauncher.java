package org.jasonadriel;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.jasonadriel.web.TransactionServlet;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {
        int port = System.getProperty("server.port") == null ? 8080 :
                Integer.parseInt(System.getProperty("server.port"));
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", null);
        Wrapper servlet = Tomcat.addServlet(ctx, "bankServlet", new TransactionServlet());
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }
}