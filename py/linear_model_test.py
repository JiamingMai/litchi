import numpy as np
import py.linear_model

def test_linear_model():
    x = np.mat([[-1], [0], [1], [2], [3], [4], [5], [6], [7], [8]])
    y = np.mat([[-1], [1], [3], [5], [7], [9], [11], [13], [15], [17]])
    params = py.linear_model.optimize(x, y)
    print(params)
    y_hat = py.linear_model.calc_value(np.mat([[15]]), params)
    print(y_hat)

if __name__ == "__main__":
    test_linear_model()