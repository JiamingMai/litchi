import numpy as np
import py.algorithm.linear_model
import py.algorithm.logarithm_model
import py.algorithm.exponential_model
import py.algorithm.polynomial_model
import py.algorithm.power_model

def test_linear_model():
    x = np.mat([[-1], [0], [1], [2], [3], [4], [5], [6], [7], [8]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    params = py.algorithm.linear_model.optimize(x, y)
    y_hat = py.algorithm.linear_model.calc_value(x, params)
    print(y_hat)

def test_exponential_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    params = py.algorithm.exponential_model.optimize(x, y)
    y_hat = py.algorithm.exponential_model.calc_value(x, params)
    print(y_hat)

def test_polynomial_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    params = py.algorithm.polynomial_model.optimize(x, y)
    y_hat = py.algorithm.polynomial_model.calc_value(x, params)
    print(y_hat)

def test_power_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    params = py.algorithm.power_model.optimize(x, y)
    y_hat = py.algorithm.power_model.calc_value(x, params)
    print(y_hat)


if __name__ == "__main__":
    test_linear_model()
    test_exponential_model()
    test_polynomial_model()
    test_power_model()
