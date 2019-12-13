package com.edu.udec.aeropuerto.math;

import java.util.List;
import java.util.stream.Collectors;

public class Stats {

    public static double getStdDev(List<Double> list) {
        List<Double> listdiff = getDiffs(list);
        return Math.sqrt(getAvg(listdiff));
    }

    public static List<Double> getDiffs(List<Double> list) {
        double avg = getAvg(list);
        return list.stream().map((e -> {
                double diff = e - avg;
                return diff * diff;
            })).collect(Collectors.toList());
    }

    public static double getAvg(List<Double> list) {
        Double result;
        result = list.stream().reduce(0D, (t, i) -> t + i);
        return result / list.size();
    }

}