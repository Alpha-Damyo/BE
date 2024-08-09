//package com.damyo.alpha.global.config;
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import jakarta.annotation.PreDestroy;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.annotation.Validated;
//
//@Component
//@ConfigurationProperties(prefix="ssh")
//@Validated
//@Setter
//public class SshTunnelingInitializer {
//    private String sshHost;
//    private int sshPort;
//    private String sshUser;
//    private String privateKeyPath;
//    private int localPort;
//
//    private Session session;
//
//    @PreDestroy
//    public void destroy() {
//        if(session.isConnected()) {
//            session.disconnect();
//        }
//    }
//
//    public Integer buildSshConnection() {
//        Integer forwardPort = null;
//
//        try {
//            JSch jsch = new JSch();
//
//            jsch.addIdentity(privateKeyPath);
//            session = jsch.getSession(sshUser, sshHost, sshPort);
//            session.setConfig("StrictHostKeyChecking", "no");
//
//            session.connect();
//
//            forwardPort = session.setPortForwardingL(0, "localhost", localPort);
//        }
//        catch (JSchException e) {
//            this.destroy();
//            throw new RuntimeException(e);
//        }
//
//        return forwardPort;
//    }
//}
