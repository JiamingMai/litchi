import numpy as np
import py.algorithm.linear_model

def test_linear_model():
    x = np.mat([[4], [8], [9], [8], [7], [12], [6], [10], [6], [9]])
    y = np.mat([[9], [20], [22], [15], [17], [23], [18], [25], [10], [20]])
    params = py.algorithm.linear_model.optimize(x, y)
    y_hat = py.algorithm.linear_model.calc_value(np.mat([[5]]), params)
    print(y_hat)

if __name__ == "__main__":
    test_linear_model()