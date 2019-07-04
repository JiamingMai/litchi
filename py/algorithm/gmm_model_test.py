import os
import numpy as np
import py.algorithm.gmm_model


def test_gmm_model():
    file_path = os.getcwd() + "/../../src/main/resources/cluster_test_data.csv"
    x = np.loadtxt(file_path, delimiter=",",usecols=(1, 2), unpack=True)
    x = np.transpose(x)
    k = 3
    epoch_num = 50
    u, sigma = py.algorithm.gmm_model.cluster(x, k, epoch_num)
    x_classes = py.algorithm.gmm_model.cluster_with_u_and_sigma(x, k, u, sigma)
    print(x_classes)


if __name__ == "__main__":
    test_gmm_model()