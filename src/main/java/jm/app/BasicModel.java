package jm.app;

public interface BasicModel {

    Matrix optimize(Matrix x, Matrix y);

    Matrix calcValue(Matrix x, Matrix params);

}
