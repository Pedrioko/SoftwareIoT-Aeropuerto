package com.edu.udec.aeropuerto.dao.api;

import com.edu.udec.aeropuerto.domain.query.AvgData;

import java.util.List;

public interface SensorRepositoryCustom {

    List<AvgData> avgtemp(String puerto);

    List<AvgData> avghum(String puerto);
}