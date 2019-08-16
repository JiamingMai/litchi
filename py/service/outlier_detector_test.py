import os
import numpy as np
import py.service.outlier_detector as detector
import py.algebra.albebra_util as au

def read_records():
    file_path = os.getcwd() + "/../../src/main/resources/cc_general_pended.csv"
    x = np.loadtxt(file_path, delimiter=",", skiprows=(1), usecols=(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17), unpack=True)
    x = np.transpose(x)
    x = au.normalize(x, 0)
    inputs = {}
    for i in range(x.shape[0]):
        inputs[i] = x[i, :]
    return inputs

def test_detect_outlier():
    inputs = read_records()
    outlier_detector = detector.OutlierDetector()
    outputs = outlier_detector.detect_outlier(inputs)
    print(outputs)

if __name__ == "__main__":
    test_detect_outlier()