package jm.app;

import java.math.BigDecimal;
import java.util.Random;

public class NeuralNetworkModel {

    private Matrix[] weights;

    private Matrix[] biases;

    private Matrix[] outputMat;

    private BigDecimal lambda = new BigDecimal(0.1);

    private int epochNum = 100000;

    private final BigDecimal RANDOM_COEFFIENCE = new BigDecimal(1.0);

    ActivationFunction activationFunction = ActivationFunction.SIGMOID;

    public BigDecimal getLambda() {
        return lambda;
    }

    public void setLambda(BigDecimal lambda) {
        this.lambda = lambda;
    }

    public int getEpochNum() {
        return epochNum;
    }

    public void setEpochNum(int epochNum) {
        this.epochNum = epochNum;
    }

    public Matrix[] getWeights() {
        return weights;
    }

    public void setWeights(Matrix[] weights) {
        this.weights = weights;
    }

    public Matrix[] getBiases() {
        return biases;
    }

    public void setBiases(Matrix[] biases) {
        this.biases = biases;
    }

    public NeuralNetworkModel(int... numOfEachLayer) {
        biases = new Matrix[numOfEachLayer.length - 1];
        for (int i = 0; i < biases.length; i++) {
            biases[i] = new Matrix(numOfEachLayer[i + 1], 1);
        }
        weights = new Matrix[numOfEachLayer.length - 1];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new Matrix(numOfEachLayer[i + 1], numOfEachLayer[i]);
        }
        outputMat = new Matrix[numOfEachLayer.length];
        for (int i = 0; i < outputMat.length; i++) {
            outputMat[i] = new Matrix(numOfEachLayer[i], 1);
        }
        init();
    }

    private void init() {
        initWeights();
        initBiases();
    }

    private void initWeights() {
        Random random = new Random();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].getRowNum(); j++) {
                for (int k = 0; k < weights[i].getColNum(); k++) {
                    weights[i].setValue(j, k, RANDOM_COEFFIENCE.multiply(new BigDecimal(random.nextDouble())));
                }
            }
        }
    }

    private void initBiases() {
        Random random = new Random();
        for (int i = 0; i < biases.length; i++) {
            for (int j = 0; j < biases[i].getRowNum(); j++) {
                biases[i].setValue(j, 0, RANDOM_COEFFIENCE.multiply(new BigDecimal(random.nextDouble())));
            }
        }
    }

    public void train(Matrix[] input, Matrix[] label) {
        for (int i = 0; i < epochNum; i++) {
            BigDecimal avgMse = new BigDecimal(0.0);
            for (int sampleNo = 0; sampleNo < input.length; sampleNo++) {
                Matrix[] sensitivity = estSensitivity(input, label, sampleNo);
                updateWeights(sensitivity);
                updateBiases(sensitivity);
                avgMse = avgMse.add(calcMse(input, label));
            }
            avgMse = new BigDecimal(avgMse.doubleValue() / input.length);
            System.out.println(String.format("Epoch #%d/%d, MSE: %.5f", i+1, epochNum, avgMse.doubleValue()));
        }
    }

    private BigDecimal calcMse(Matrix[] input, Matrix[] label) {
        BigDecimal sum = new BigDecimal(0.0);
        for (int i = 0; i < input.length; i++) {
            Matrix res = forward(input[i]);
            for (int j = 0; j < label[i].getRowNum(); j++) {
                sum = sum.add((res.getValue(j, 0).subtract(label[i].getValue(j, 0))).pow(2));
            }
        }
        double mse = sum.doubleValue() / label.length;
        //System.out.println("MSE: " + mse);
        return new BigDecimal(mse);
    }

    private void updateWeights(Matrix[] sensitivity) {
        for (int layer = 0; layer < weights.length; layer++) {
            for (int i = 0; i < weights[layer].getRowNum(); i++) {
                for (int j = 0; j < weights[layer].getColNum(); j++) {
                    weights[layer].setValue(i, j, weights[layer].getValue(i, j).subtract(lambda.multiply(sensitivity[layer].getValue(i, 0).multiply(outputMat[layer].getValue(j, 0)))));
                }
            }
        }
    }

    private void updateBiases(Matrix[] sensitivity) {
        for (int layer = 0; layer < biases.length; layer++) {
            biases[layer] = AlgebraUtil.subtract(biases[layer], AlgebraUtil.dot(sensitivity[layer], lambda));
        }
    }

    private Matrix[] estSensitivity(Matrix[] input, Matrix[] label, int sampleNo) {
        Matrix[] sensitivity = new Matrix[biases.length];
        for (int i = 0; i < biases.length; i++) {
            sensitivity[i] = new Matrix(biases[i].getRowNum(), 1);
        }

        Matrix output = forward(input[sampleNo]);

        sensitivity[sensitivity.length - 1] = AlgebraUtil.dot(AlgebraUtil.dot(derivativeOfActFun(output), AlgebraUtil.subtract(label[sampleNo], output)), new BigDecimal(-2));

        for (int layer = sensitivity.length - 2; layer >= 0; layer--) {
            for (int i = 0; i < sensitivity[layer].getRowNum(); i++) {
                sensitivity[layer].setValue(i, 0, derivativeOfActFun(outputMat[layer + 1].getValue(i, 0)).multiply(
                        AlgebraUtil.inner(AlgebraUtil.getColumnVector(weights[layer + 1], i), sensitivity[layer + 1])));
            }
        }
        return sensitivity;
    }

    private int generateSampleNo(double[][] input, int batchSize) {
        Random random = new Random();
        int sampleNo = random.nextInt(input.length);
        return sampleNo;
    }

    private double[] selectColumn(Double[][] mat, int col) {
        double[] vec = new double[mat.length];
        for (int i = 0; i < mat.length; i++) {
            vec[i] = mat[i][col];
        }
        return vec;
    }

    public Matrix forward(Matrix input) {
        outputMat[0] = AlgebraUtil.copy(input);
        for (int i = 0; i < weights.length; i++) {
            outputMat[i + 1] = activationFunction(AlgebraUtil.add(AlgebraUtil.multiply(weights[i], outputMat[i]), biases[i]));
        }
        return AlgebraUtil.copy(outputMat[outputMat.length - 1]);
    }

    private Matrix activationFunction(Matrix mat) {
        Matrix newMat = new Matrix(mat.getRowNum(), mat.getColNum());
        for (int i = 0; i < mat.getRowNum(); i++) {
            for (int j = 0; j < mat.getColNum(); j++) {
                newMat.setValue(i, j, activationFunction(mat.getValue(i, j)));
            }
        }
        return newMat;
    }

    private BigDecimal activationFunction(BigDecimal x) {
        switch (activationFunction){
            case SIGMOID: return new BigDecimal(1 / (1 + Math.exp(x.negate().doubleValue())));
            case LINEAR: return x;
            default: return x;
        }
    }

    private Double innerProduct(Double[] vec1, Double[] vec2) {
        if (vec1.length != vec2.length) {
            return null;
        }
        return innerProduct(convertDoubleArray(vec1), convertDoubleArray(vec2));
    }

    private Double innerProduct(double[] vec1, double[] vec2) {
        if (vec1.length != vec2.length) {
            return null;
        }
        double res = 0;
        for (int i = 0; i < vec1.length; i++) {
            res += vec1[i] * vec2[i];
        }
        return res;
    }

    private Double[] convertDoubleArray(double[] inputArr) {
        Double[] outputArr = new Double[inputArr.length];
        for (int i = 0; i < outputArr.length; i++) {
            outputArr[i] = inputArr[i];
        }
        return outputArr;
    }

    private double[] convertDoubleArray(Double[] inputArr) {
        double[] outputArr = new double[inputArr.length];
        for (int i = 0; i < outputArr.length; i++) {
            outputArr[i] = inputArr[i];
        }
        return outputArr;
    }

    private Matrix derivativeOfActFun(Matrix output) {
        Matrix activeOutput = new Matrix(output.getRowNum(), output.getColNum());
        for (int i = 0; i < output.getRowNum(); i++) {
            for (int j = 0; j < output.getColNum(); j++) {
                activeOutput.setValue(i, j, derivativeOfActFun(output.getValue(i, j)));
            }
        }
        return activeOutput;
    }

    private BigDecimal derivativeOfActFun(BigDecimal output){
        switch (activationFunction){
            case SIGMOID: return output.multiply(new BigDecimal(1).subtract(output));
            case LINEAR: return new BigDecimal(1.0);
            default: return new BigDecimal(1.0);
        }
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public enum ActivationFunction {
        SIGMOID, LINEAR;
    }
}