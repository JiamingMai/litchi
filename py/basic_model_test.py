import numpy as np
import py.linear_model
import py.logarithm_model
import py.power_model
import py.exponential_model
import py.polynomial_model

def calc_rmse(y_hat, y):
    n = y_hat.shape[0]
    rmse = np.power(np.sum(np.power(y_hat - y, 2)) / n, 0.5)
    return rmse

def compare_basic_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    linear_params = py.linear_model.optimize(x, y)
    linear_y_hat = py.linear_model.calc_value(x, linear_params)
    linear_rmse = calc_rmse(linear_y_hat, y)
    print(linear_rmse)

if __name__ == "__main__":
    compare_basic_model()


