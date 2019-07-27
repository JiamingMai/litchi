import os
import numpy as np
import py.algorithm.auto_encoder_model as ae
import py.algebra.albebra_util as au

def read_records():
    file_path = os.getcwd() + "/../../src/main/resources/cc_general_pended.csv"
    x = np.loadtxt(file_path, delimiter=",", skiprows=(1), usecols=(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17), unpack=True)
    x = np.transpose(x)
    x = au.normalize(x, 0)
    inputs = []
    for i in range(x.shape[0]):
        input = np.zeros((x.shape[1], 1))
        input[:, 0] = np.transpose(x[i, :])
        inputs.append(input)
    return inputs

def test_auto_encoder_model():
    inputs = read_records()
    auto_encoder = ae.AutoEncoder()
    auto_encoder.train(inputs)
    for i in range(len(inputs)):
        encoded_input = auto_encoder.encode(inputs[i])
        print(encoded_input)



if __name__ == "__main__":
    test_auto_encoder_model()