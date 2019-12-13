package com.edu.udec.aeropuerto.dao;

import com.edu.udec.aeropuerto.dao.api.SensorRepositoryCustom;
import com.edu.udec.aeropuerto.domain.Sensor;
import com.edu.udec.aeropuerto.domain.query.AvgData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class SensorRepositoryCustomImpl implements SensorRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SensorRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<AvgData> avghum(String puerto) {
        Criteria p = Criteria.where("puerto").is(puerto);
        Aggregation avg_humedad = Aggregation.newAggregation(Aggregation.match(p), (Aggregation.group().avg("historial.humedad").as("avghumedad")));
        return mongoTemplate.aggregate(avg_humedad, Sensor.class, AvgData.class).getMappedResults();
    }

    @Override
    public List<AvgData> avgtemp(String puerto) {
        return null;
    }
}