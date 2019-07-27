import numpy as np
import py.algorithm.neural_network_model as nn

class AutoEncoder():

    def train(self, samples):
        dimension = samples[0].shape[0]
        compressed_dimension = int(np.ceil(dimension / 2))
        if compressed_dimension <= 0:
            compressed_dimension = 1
        self.neural_network_model = nn.NeuralNetworkModel(np.array([dimension, compressed_dimension, dimension]))
        self.neural_network_model.train(samples, samples)

    def encode(self, x):
        self.neural_network_model.forward(x)
        output_of_all_layers = self.neural_network_model.output_mat
        output = output_of_all_layers[1]
        return output