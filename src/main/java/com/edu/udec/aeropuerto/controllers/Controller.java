package com.edu.udec.aeropuerto.controllers;

import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edu.udec.aeropuerto.App;
import com.edu.udec.aeropuerto.controllers.domain.StandardResponse;
import com.edu.udec.aeropuerto.controllers.domain.StatusResponse;
import com.edu.udec.aeropuerto.dao.SensorRepository;
import com.edu.udec.aeropuerto.domain.Dato;
import com.edu.udec.aeropuerto.domain.Sensor;
import com.edu.udec.aeropuerto.domain.enums.RangoRuido;
import com.edu.udec.aeropuerto.math.FastV;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import weka.associations.Apriori;
import weka.classifiers.functions.LinearRegression;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;

import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

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

        get("/api/sensor/:id/clusters", (req, res) -> {
            String params = req.params(":id");
            Optional<Sensor> byId = sensorRepo.findById(Long.valueOf(params));
            List<Dato> historial = byId.get().getHistorial();
            if (historial == null || historial.isEmpty())
                return "{}";
            else {
                FastVector atts = new FastVector();
                atts.addElement(new Attribute("Decibeles", 0));
                atts.addElement(new Attribute("Clasificacion", 1));

                Instances dataset = new Instances("Dataset", atts, historial.size());

                historial.forEach(e -> {
                    dataset.add(new Instance(1.0, new double[]{e.getDecibeles(), e.getClasificacionRuido().getValue()}));
                });
                SimpleKMeans skm = new SimpleKMeans();
                skm.buildClusterer(dataset);
                String cad = "[";

                Instances cent = skm.getClusterCentroids();
                for (int i = 0; i < cent.numInstances(); i++) {
                    Instance e = cent.instance(i);
                    int w = e.numValues();
                    cad += "{\"nombre\": \"" + e.attribute(i).name() + "\", \"values\":[";
                    for (int j = 0; j < w; j++) {
                        cad += e.toString(j) + (j != (w - 1) ? "," : "");
                    }
                    cad += "]},";
                }


                cad = cad + "]";
                System.out.println("Skm " + skm);
                cad = cad.replace("},]", "}]");
                return cad;
            }
        });
        get("/api/sensor/:id/regresion", (req, res) -> {
            String params = req.params(":id");
            Optional<Sensor> byId = sensorRepo.findById(Long.valueOf(params));

            List<Dato> historial = byId.get().getHistorial();
            if (historial == null || historial.isEmpty())
                return "{}";
            else {
                FastV fv = new FastV(historial.size());

                historial.forEach(item -> {
                    fv.addPair(historial.indexOf(item), (int) item.getDecibeles());
                });
                double[] coefficients = fv.getFunction();
                return "{\"m\":" + coefficients[0] + ",\"c\":" + coefficients[2] + "}";
            }
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
