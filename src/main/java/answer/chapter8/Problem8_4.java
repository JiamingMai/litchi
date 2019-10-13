package answer.chapter8;

import jm.app.algebra.Matrix;
import jm.app.algorithm.NeuralNetworkModel;

import java.math.BigDecimal;

/**
 * 参考8.3节的抑或运算器的示例，尝试使用8.2节中的多层神经网络模型，
 * 分别编写训练与运算器以及训练或运算器的示例。
 */
public class Problem8_4 {

    public static void trainAndOperator() {
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
        label[1].setValue(0, 0, 0);
        label[2].setValue(0, 0, 0);
        label[3].setValue(0, 0, 1);

        NeuralNetworkModel network = new NeuralNetworkModel(2, 3, 1);
        network.setEpochNum(1000);
        network.setLambda(new BigDecimal(0.0001));
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

    public static void trainOrOperator() {
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
        label[3].setValue(0, 0, 1);

        NeuralNetworkModel network = new NeuralNetworkModel(2, 3, 1);
        network.setEpochNum(200);
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

    public static void main(String[] args) {
        trainAndOperator();
        trainOrOperator();
    }

}
