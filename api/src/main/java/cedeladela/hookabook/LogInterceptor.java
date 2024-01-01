//package cedeladela.hookabook;
//
//import org.apache.logging.log4j.ThreadContext;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Aspect
//@Component
//public class LogInterceptor {
//
//    @Value("${spring.application.name}")
//    private String serviceName;
//
//    @Value("${spring.application.version}")
//    private String serviceVersion;
//
//    @Value("${spring.profiles.active}")
//    private String environment;
//
//    @Before("@within(org.springframework.stereotype.Controller) || @within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository) || @within(org.springframework.stereotype.Component)")
//    public void beforeMethodExecution(JoinPoint joinPoint) {
//        // Dodajte kontekstne podatke
//        ThreadContext.put("serviceName", serviceName);
//        ThreadContext.put("serviceVersion", serviceVersion);
//        ThreadContext.put("environment", environment);
//
//        // Dodajte unikaten identifikator zahtevka
//        ThreadContext.put("requestId", UUID.randomUUID().toString());
//    }
//}
