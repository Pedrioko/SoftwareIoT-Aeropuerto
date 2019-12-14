package com.edu.udec.aeropuerto.math;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Estudiante
 */
public class FastV {
    FastVector fv;
    Instances data;

     public FastV(int cantidad) {
        this.fv = new FastVector();
        this.fv.addElement(new Attribute("X"));
        this.fv.addElement(new Attribute("Y"));
                
        data = new Instances("datos", fv, cantidad);
        data.setClassIndex(data.numAttributes() - 1);
    }
    
    public void addPair(int x, int y) {
        Instance temp;

        temp = new Instance(2);
        temp.setValue((Attribute) fv.elementAt(0), x);
        temp.setValue((Attribute) fv.elementAt(1), y);
        data.add(temp);
    }
    
    public double [] getFunction() throws Exception {
        
        LinearRegression lr = new LinearRegression();
        
        lr.buildClassifier(data);
        
        return lr.coefficients();        
    }
    
}
