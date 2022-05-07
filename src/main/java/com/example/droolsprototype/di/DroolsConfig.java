package com.example.droolsprototype.di;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config file stolen from https://github.com/Java-Techie-jt/spring-drools/blob/master/src/main/java/com/javatechie/spring/drools/api/DroolConfig.java
 * Modified to better utilize dependency injection
 */
@Configuration
public class DroolsConfig {

    @Bean
    public KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieFileSystem getKieFileSystem(KieServices kieServices) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("static/rules.drl"));
        return kieFileSystem;
    }

    private void getKieRepository(KieServices kieServices) {
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
    }

    @Bean
    public KieContainer getKieContainer(KieServices kieServices, KieFileSystem kieFileSystem) {
        getKieRepository(kieServices);
        KieModule kieModule = kieServices.newKieBuilder(kieFileSystem)
                .buildAll()
                .getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Bean
    public KieSession getKieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }

}
