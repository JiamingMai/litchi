import os
import numpy as np
import py.algorithm.kmeans_model


def test_kmeans_model():
    file_path = os.getcwd() + "/../../src/main/resources/cluster_test_data.csv"
    print(file_path)
    x = np.loadtxt(file_path, delimiter=",",usecols=(1, 2), unpack=True)
    x = np.transpose(x)
    k = 3
    epoch_num = 100
    u, classes = py.algorithm.kmeans_model.cluster(x, k, epoch_num)
    print(u)
    print(classes)

if __name__ == "__main__":
    test_kmeans_model()