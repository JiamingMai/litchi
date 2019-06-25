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

package jm.app;

import jm.app.algebra.Matrix;
import jm.app.algorithm.NeuralNetworkModel;
import org.junit.jupiter.api.Test;

public class NeuralNetworkModelTest {

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
        
        NeuralNetworkModel network = new NeuralNetworkModel(2, 3, 1);
        network.setActivationFunction(NeuralNetworkModel.ActivationFunction.SIGMOID);
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
}
