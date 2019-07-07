import numpy as np

class NeuralNetworkModel():

    biases = []
    weights = []
    output_mat = []

    def __init__(self, num_of_each_layer):
        self.epoch_num = 100000
        self.activation_function_type = "sigmoid"
        self.learning_rate = 0.1
        for i in range(len(num_of_each_layer) - 1):
            self.biases.append(np.random.rand(num_of_each_layer[i+1], 1))
            self.weights.append(np.random.rand(num_of_each_layer[i+1], num_of_each_layer[i]))
        for i in range(len(num_of_each_layer)):
            self.output_mat.append(np.random.rand(num_of_each_layer[i], 1))

    def train(self, input, label):
        for i in range(self.epoch_num):
            avg_mse = 0.0
            for sample_no in range(len(input)):
                sensitivity = self.est_sensitivity(input, label, sample_no)
                self.update_weights(sensitivity)
                self.update_biases(sensitivity)
                avg_mse = avg_mse + self.calc_mse(input, label)
            avg_mse = avg_mse / len(input)
            print("Epoch %d/%d, MSE: %.5f" %(i+1, self.epoch_num, avg_mse))

    def activation_function_for_mat(self, mat):
        new_mat = np.random.rand(mat.shape[0], mat.shape[1])
        for i in range(mat.shape[0]):
            for j in range(mat.shape[1]):
                new_mat[i][j] = self.activation_function(mat[i][j])
        return new_mat

    def activation_function(self, x):
        if self.activation_function_type == "sigmoid":
            return 1 / (1 + np.exp(-x))
        else:
            return x

    def forward(self, input):
        self.output_mat[0] = np.copy(input)
        for i in range(len(self.weights)):
            self.output_mat[i+1] = self.activation_function_for_mat(np.add(np.dot(self.weights[i], self.output_mat[i]), self.biases[i]))
        return np.copy(self.output_mat[len(self.output_mat)-1])

    def update_biases(self, sensitiviate):
        for layer in range(len(self.biases)):
            self.biases[layer] = np.subtract(self.biases[layer], np.multiply(sensitiviate[layer], self.learning_rate))

    def update_weights(self, sensitiviate):
        for layer in range(len(self.weights)):
            for i in range(self.weights[layer].shape[0]):
                for j in range(self.weights[layer].shape[1]):
                    self.weights[layer][i][j] = self.weights[layer][i][j] - self.learning_rate * sensitiviate[layer][i][0] * self.output_mat[layer][j][0]

    def derivative_of_act_fun_for_mat(self, output):
        active_output = np.random.rand(output.shape[0], output.shape[1])
        for i in range(output.shape[0]):
            for j in range(output.shape[1]):
                active_output[i][j] = self.derivative_of_act_fun(output[i][j])
        return active_output

    def derivative_of_act_fun(self, output):
        if self.activation_function_type == "sigmoid":
            return output * (1 - output)
        else:
            return 1.0

    def est_sensitivity(self, input, label, sample_no):
        sensitivity = []
        for i in range(len(self.biases)):
            sensitivity.append(np.zeros((self.biases[i].shape[0], 1)))
        output = self.forward(input[sample_no])
        sensitivity[len(sensitivity)-1] = np.multiply(np.multiply(self.derivative_of_act_fun_for_mat(output), np.subtract(label[sample_no], output)), -2)
        for layer in range(len(sensitivity)-2, -1, -1):
            for i in range(sensitivity[layer].shape[0]):
                sensitivity[layer][i][0] = self.derivative_of_act_fun(self.output_mat[layer+1][i][0]) * np.inner(self.weights[layer+1][:, i], sensitivity[layer+1])
        return sensitivity

    def calc_mse(self, input, label):
        sum = 0.0
        for i in range(len(input)):
            res = self.forward(input[i])
            for j in range(label[i].shape[0]):
                sum = sum + np.power(res[j][0] - label[i][j][0], 2)
        mse = sum / len(label)
        return mse