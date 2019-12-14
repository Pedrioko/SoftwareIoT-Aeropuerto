package com.edu.udec.aeropuerto.domain.enums;

public enum RangoRuido {
    CONFORTABLE(0),
    RUIDO_NO_PELIGROSO(1),
    RUIDO_DANO_AUDITIVO(2),
    RUIDO_EXTREMO(2),
    SEVERA_IRRITACION_AL_ODIO(3),
    UMBRAL_DEL_DOLOR_INMEDIATO(4),
    DANO_FISICO_INMEDIATO(5);
    private final double value;

    private RangoRuido(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
