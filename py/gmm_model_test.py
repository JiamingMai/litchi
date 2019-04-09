import os
import numpy as np
import py.gmm_model

def test_gmm_model():
    file_path = os.getcwd() + "/../src/main/resources/cluster_test_data.csv"
    x = np.loadtxt(file_path, delimiter=",",usecols=(1, 2), unpack=True)
    x = np.transpose(x)
    k = 3
    epoch_num = 50
    u, sigma = py.gmm_model.cluster(x, k, epoch_num)


if __name__ == "__main__":
    test_gmm_model()