package com.edu.udec.aeropuerto.listener;


import com.edu.udec.aeropuerto.domain.Dato;
import com.edu.udec.aeropuerto.domain.Sensor;
import com.edu.udec.aeropuerto.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class DatoModelListener extends AbstractMongoEventListener<Dato> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public DatoModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Dato> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Dato.SEQUENCE_NAME));
        }
    }


}