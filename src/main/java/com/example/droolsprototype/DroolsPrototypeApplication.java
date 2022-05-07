package com.example.droolsprototype;

import com.example.droolsprototype.demo.DemoTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Timer;

/**
 * Application entry point
 */
@SpringBootApplication
public class DroolsPrototypeApplication {

    public final static double CPU_THRESHOLD = -999;

    private final DemoTask demoTask;

    public static void main(String[] args) {
        //create an instance of this class and all its dependencies
        ConfigurableApplicationContext context = SpringApplication.run(DroolsPrototypeApplication.class, args);
        //get the instance and invoke the runExample() method
        context.getBean(DroolsPrototypeApplication.class).runExample();
    }

    public DroolsPrototypeApplication(DemoTask demoTask) {
        this.demoTask = demoTask;
    }

    public void runExample() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(demoTask, 0, 10000); //run demo every 10 seconds
    }


}
