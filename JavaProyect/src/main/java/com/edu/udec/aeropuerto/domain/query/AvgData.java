package com.edu.udec.aeropuerto.domain.query;

public class AvgData {
    private Double avghumedad;

    public AvgData(Double avghumedad) {
        this.avghumedad = avghumedad;
    }

    public AvgData() {
    }

    public Double getAvghumedad() {
        return avghumedad;
    }

    public void setAvghumedad(Double avghumedad) {
        this.avghumedad = avghumedad;
    }
}
