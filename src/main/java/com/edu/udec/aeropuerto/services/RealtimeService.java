package com.edu.udec.aeropuerto.services;

import com.edu.udec.aeropuerto.domain.enums.RangoRuido;
import com.fazecast.jSerialComm.SerialPort;
import com.edu.udec.aeropuerto.dao.SensorRepository;
import com.edu.udec.aeropuerto.domain.Dato;
import com.edu.udec.aeropuerto.domain.Sensor;
import com.edu.udec.aeropuerto.events.EventAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RealtimeService {
    private SensorRepository sensorRepository;

    private Map<String, JLabel> humedadLabel = new HashMap<>();
    private Map<String, JLabel> tempLabel = new HashMap<>();
    private Map<String, Thread> threadMap = new HashMap<>();
    private Map<String, EventAction> actionMap = new HashMap<>();

    @Autowired
    public RealtimeService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @PostConstruct
    public void init() {
        List<Sensor> listall = sensorRepository.findAll();

        listall.forEach(sensor -> {
            Thread thread = threadMap.get(sensor.getPuerto());
            if (thread != null) thread.stop();
            Thread hilo = new Thread(() -> {
                try {
                    SerialPort sp = SerialPort.getCommPort(sensor.getPuerto().trim());
                    sp.setComPortParameters(9600, 8, 1, 0);
                    sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
                    if (sp.openPort()) {
                        while (true) {
                            if (sp.bytesAvailable() > 0) {
                                Sensor sensorrefesh = sensorRepository.findByPuerto(sensor.getPuerto()).get(0);
                                BufferedReader br = new BufferedReader(new InputStreamReader(sp.getInputStream()));
                                String value = br.readLine();
                                List<Dato> historial = sensorrefesh.getHistorial();
                                if (historial == null)
                                    historial = new ArrayList<>();

                                Double valor = Double.valueOf(value);
                                Dato dato = new Dato(valor, new Date());
                                if (valor >= 0.0 && valor <= 50.0) {
                                    dato.setClasificacionRuido(RangoRuido.CONFORTABLE);
                                }
                                if (valor >= 51.0 && valor <= 65.0) {
                                    dato.setClasificacionRuido(RangoRuido.RUIDO_NO_PELIGROSO);
                                }
                                if (valor >= 66.0 && valor <= 85.0) {
                                    dato.setClasificacionRuido(RangoRuido.RUIDO_DANO_AUDITIVO);
                                }
                                if (valor >= 86.0 && valor <= 100.0) {
                                    dato.setClasificacionRuido(RangoRuido.RUIDO_EXTREMO);
                                }
                                if (valor >= 101.0 && valor <= 120.0) {
                                    dato.setClasificacionRuido(RangoRuido.SEVERA_IRRITACION_AL_ODIO);
                                }
                                if (valor >= 121.0 && valor <= 130.0) {
                                    dato.setClasificacionRuido(RangoRuido.UMBRAL_DEL_DOLOR_INMEDIATO);
                                }
                                if (valor >= 131.0 && valor <= 160.0) {
                                    dato.setClasificacionRuido(RangoRuido.DANO_FISICO_INMEDIATO);
                                }

                                historial.add(dato);


                                sensorrefesh.setHistorial(historial);
                                sensorRepository.save(sensorrefesh);
                                EventAction action = actionMap.get(sensorrefesh.getPuerto());
                                if (action != null) action.doIt(sensorrefesh);
                            }
                            Thread.currentThread().sleep(2000);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            threadMap.put(sensor.getPuerto(), hilo);
            hilo.start();

        });
    }

    public JLabel putHumedadLabel(String key, JLabel value) {
        return humedadLabel.put(key, value);
    }

    public JLabel putTempLabel(String key, JLabel value) {
        return tempLabel.put(key, value);
    }

    public EventAction putActionMap(String key, EventAction value) {
        return actionMap.put(key, value);
    }
}
