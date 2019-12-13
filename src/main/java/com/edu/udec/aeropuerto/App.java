package com.edu.udec.aeropuerto;

import com.edu.udec.aeropuerto.controllers.Controller;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class App {


    public static ConfigurableApplicationContext Ctx;

    public static void main(String[] args) {
        Ctx = new SpringApplicationBuilder(App.class)
                .headless(false)
                .run(args);
        new Controller();
    }

}