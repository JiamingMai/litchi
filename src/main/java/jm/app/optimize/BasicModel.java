package jm.app.optimize;

import jm.app.algebra.Matrix;

public interface BasicModel {

    Matrix optimize(Matrix x, Matrix y);

    Matrix calcValue(Matrix x, Matrix params);

}
