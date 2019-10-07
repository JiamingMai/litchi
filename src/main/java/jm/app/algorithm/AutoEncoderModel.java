package jm.app.algorithm;

import jm.app.algebra.Matrix;

public class AutoEncoderModel {

    private FastNeuralNetworkModel neuralNetworkModel;

    private Integer compressedDimension;

    public AutoEncoderModel() {
    }

    public AutoEncoderModel(int compressedDimension) {
        this.compressedDimension = compressedDimension;
    }

    public void train(Matrix[] samples) {
        int dimension = samples[0].getRowNum();
        if (null == compressedDimension) {
            compressedDimension = dimension / 2 > 0 ? dimension / 2 : 1;
        }
        neuralNetworkModel = new FastNeuralNetworkModel(dimension, compressedDimension, dimension);
        neuralNetworkModel.train(samples, samples);
    }

    public Matrix encode(Matrix x) {
        neuralNetworkModel.forward(x);
        Matrix[] outputOfAllLayers = neuralNetworkModel.getOutputMat();
        Matrix output = outputOfAllLayers[1];
        return output;
    }

    public Integer getCompressedDimension() {
        return compressedDimension;
    }

    public void setCompressedDimension(Integer compressedDimension) {
        this.compressedDimension = compressedDimension;
    }
}
