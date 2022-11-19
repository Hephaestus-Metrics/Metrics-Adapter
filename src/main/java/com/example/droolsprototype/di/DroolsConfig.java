package com.example.droolsprototype.di;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config file stolen from <a href="https://github.com/Java-Techie-jt/spring-drools/blob/master/src/main/java/com/javatechie/spring/drools/api/DroolConfig.java">github</a>
 * Modified to better utilize dependency injection
 */
@Configuration
public class DroolsConfig {

    @Value("${mode}")
    private String mode;

    private static final String DEMO_RULES_PATH = "static/rules.drl";
    private static final String TESTS_RULES_PATH = "static/performance-test-rules.drl";
    private static final String BUSINESS_DEMO_RULES_PATH = "static/business-demo-rules.drl";

    @Bean
    public KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieFileSystem getKieFileSystem(KieServices kieServices) {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(ResourceFactory.newClassPathResource(getRulesPath()));
        return kieFileSystem;
    }

    private String getRulesPath() {
        if(mode.equals("TIME_TEST") || mode.equals("NUMBER_TEST") || mode.equals("MOCK_METRICS_TEST")) {
            return TESTS_RULES_PATH;
        } else if (mode.equals("BUSINESS_DEMO_TEST")) {
            return BUSINESS_DEMO_RULES_PATH;
        }
        return DEMO_RULES_PATH;
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
    public StatelessKieSession getKieSession(KieContainer kieContainer) {
        return kieContainer.newStatelessKieSession();
    }

}
