import numpy as np
import py.algorithm.neural_network_model as nn

def train_and_operator():
    input = []
    input.append(np.zeros((2, 1)))
    input.append(np.zeros((2, 1)))
    input.append(np.zeros((2, 1)))
    input.append(np.zeros((2, 1)))
    input[0][0][0] = 0
    input[0][1][0] = 0
    input[1][0][0] = 0
    input[1][1][0] = 1
    input[2][0][0] = 1
    input[2][1][0] = 0
    input[3][0][0] = 1
    input[3][1][0] = 1
    label = []
    label.append(np.zeros((1, 1)))
    label.append(np.zeros((1, 1)))
    label.append(np.zeros((1, 1)))
    label.append(np.zeros((1, 1)))
    label[0][0][0] = 0
    label[1][0][0] = 0
    label[2][0][0] = 0
    label[3][0][0] = 1
    neural_network_model = nn.NeuralNetworkModel(np.array([2, 3, 1]))
    neural_network_model.train(input, label)
    res1 = neural_network_model.forward(input[0])
    res2 = neural_network_model.forward(input[1])
    res3 = neural_network_model.forward(input[2])
    res4 = neural_network_model.forward(input[3])
    print(res1)
    print(res2)
    print(res3)
    print(res4)
    print()

def train_or_operator():
    input = []
    input.append(np.zeros((2, 1)))
    input.append(np.zeros((2, 1)))
    input.append(np.zeros((2, 1)))
    input.append(np.zeros((2, 1)))
    input[0][0][0] = 0
    input[0][1][0] = 0
    input[1][0][0] = 0
    input[1][1][0] = 1
    input[2][0][0] = 1
    input[2][1][0] = 0
    input[3][0][0] = 1
    input[3][1][0] = 1
    label = []
    label.append(np.zeros((1, 1)))
    label.append(np.zeros((1, 1)))
    label.append(np.zeros((1, 1)))
    label.append(np.zeros((1, 1)))
    label[0][0][0] = 1
    label[1][0][0] = 1
    label[2][0][0] = 1
    label[3][0][0] = 0
    or_operator_network = nn.NeuralNetworkModel(np.array([2, 3, 1]))
    or_operator_network.train(input, label)
    res1 = or_operator_network.forward(input[0])
    res2 = or_operator_network.forward(input[1])
    res3 = or_operator_network.forward(input[2])
    res4 = or_operator_network.forward(input[3])
    print(res1)
    print(res2)
    print(res3)
    print(res4)
    print()

if __name__ == "__main__":
    train_and_operator()