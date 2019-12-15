package com.edu.udec.aeropuerto.dao;

import java.util.List;
import java.util.Optional;

import com.edu.udec.aeropuerto.dao.api.SensorRepositoryCustom;
import com.edu.udec.aeropuerto.domain.Sensor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorRepository extends MongoRepository<Sensor, Long> {

    public List<Sensor> findByPuerto(String puerto);

    public List<Sensor> findByNombre(String nombre);

}