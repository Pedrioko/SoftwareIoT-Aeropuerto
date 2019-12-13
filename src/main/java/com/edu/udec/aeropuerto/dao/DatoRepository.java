package com.edu.udec.aeropuerto.dao;

import com.edu.udec.aeropuerto.domain.Dato;
import com.edu.udec.aeropuerto.domain.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DatoRepository extends MongoRepository<Dato, String> {

}