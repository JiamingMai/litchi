import numpy as np
import py.logarithm_model


def test_logarithm_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    params = py.logarithm_model.optimize(x, y)
    print(params)
    y_hat = py.logarithm_model.calc_value(x, params)
    print(y_hat)


if __name__ == "__main__":
    test_logarithm_model()