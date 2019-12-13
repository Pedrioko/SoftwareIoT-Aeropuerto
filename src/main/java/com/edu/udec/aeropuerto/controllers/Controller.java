package com.edu.udec.aeropuerto.controllers;

import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.edu.udec.aeropuerto.App;
import com.edu.udec.aeropuerto.controllers.domain.StandardResponse;
import com.edu.udec.aeropuerto.controllers.domain.StatusResponse;
import com.edu.udec.aeropuerto.dao.SensorRepository;
import com.edu.udec.aeropuerto.domain.Sensor;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import static spark.Spark.*;

public class Controller {
    private final SensorRepository sensorRepo;
    StringWriter stringWriter;
    Configuration configuration = new Configuration(new Version(2, 3, 0));

    public Controller() {
        configuration.setClassForTemplateLoading(Controller.class, "/templates/");
        sensorRepo = App.Ctx.getBean(SensorRepository.class);
        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate("index.html");
            template.process(null, writer);
            return writer;
        });

        get("/api/sensor/:id", (req, res) -> {
            String params = req.params(":id");
            Optional<Sensor> byId = sensorRepo.findById(Long.valueOf(params));
            Gson gson = new Gson();
            return gson.toJson(byId.get());
        });

        get("/api/sensors", (req, res) -> {
            String params = req.params(":id");
            List<Sensor> all = sensorRepo.findAll();
            Gson gson = new Gson();
            return gson.toJson(all);
        });

        post("/api/sensor", (req, res) -> {
            Sensor sensor = new Gson().fromJson(req.body(), Sensor.class);
            sensorRepo.save(sensor);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        put("/api/sensor", (req, res) -> {
            Sensor sensor = new Gson().fromJson(req.body(), Sensor.class);
            sensorRepo.save(sensor);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });
    }
}
