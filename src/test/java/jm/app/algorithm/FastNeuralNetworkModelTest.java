/*
 * This is an example of the neural network toolbox. The main method trys to train a neural network with three layers to
 * match the XOR operation. The network works successfully. The inputs and the corresponding correct outputs are as
 * follows:
 *   -----------------
 *  | input | output |
 *   -----------------
 *  |  00   |   0    |
 *  |  01   |   1    |
 *  |  10   |   1    |
 *  |  11   |   0    |
 *  ------------------
 */

package jm.app.algorithm;

import jm.app.algebra.Matrix;
import jm.app.algorithm.FastNeuralNetworkModel;
import org.junit.jupiter.api.Test;

public class FastNeuralNetworkModelTest {

    @Test
    public void testNeuralNetwork(){
        Matrix[] input = new Matrix[4];
        input[0] = new Matrix(2, 1);
        input[1] = new Matrix(2, 1);
        input[2] = new Matrix(2, 1);
        input[3] = new Matrix(2, 1);
        input[0].setValue(0, 0, 0);
        input[0].setValue(1, 0, 0);
        input[1].setValue(0, 0, 0);
        input[1].setValue(1, 0, 1);
        input[2].setValue(0, 0, 1);
        input[2].setValue(1, 0, 0);
        input[3].setValue(0, 0, 1);
        input[3].setValue(1, 0, 1);
        Matrix[] label = new Matrix[4];
        label[0] = new Matrix(1, 1);
        label[1] = new Matrix(1, 1);
        label[2] = new Matrix(1, 1);
        label[3] = new Matrix(1, 1);
        label[0].setValue(0, 0, 0);
        label[1].setValue(0, 0, 1);
        label[2].setValue(0, 0, 1);
        label[3].setValue(0, 0, 0);

        FastNeuralNetworkModel network = new FastNeuralNetworkModel(2, 3, 1);
        network.setActivationFunction(FastNeuralNetworkModel.ActivationFunction.SIGMOID);
        /*
        Double[][][] weights = new Double[2][][];
        weights[0] = new Double[3][2];
        weights[0][0][0] = 6.70482641156543;
        weights[0][0][1] = -4.18128384406268;
        weights[0][1][0] = 5.38360500391982;
        weights[0][1][1] = -7.52967197673607;
        weights[0][2][0] = 4.16230095022815;
        weights[0][2][1] = 5.95317354348635;
        weights[1] = new Double[1][3];
        weights[1][0][0] = -9.46783598265045;
        weights[1][0][1] = 9.57546144367196;
        weights[1][0][2] = 6.14236729717791;

        Double[][] biases = new Double[2][];
        biases[0] = new Double[3];
        biases[0][0] = 1.30745916070485;
        biases[0][1] = -2.19334229747328;
        biases[0][2] = -0.525310872754804;
        biases[1] = new Double[1];
        biases[1][0] = -1.19737302691328;

        network.setWeitghts(weights);
        network.setBiases(biases);
        */

        network.train(input, label);
        Matrix res1 = network.forward(input[0]);
        Matrix res2 = network.forward(input[1]);
        Matrix res3 = network.forward(input[2]);
        Matrix res4 = network.forward(input[3]);
        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);
    }

    private static void printRes(double[] res){
        for(int i = 0; i < res.length; i++){
            System.out.println(res[i]);
        }
    }
}
