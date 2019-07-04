import numpy as np
import py.algorithm.linear_model
import py.algorithm.logarithm_model
import py.algorithm.power_model
import py.algorithm.exponential_model
import py.algorithm.polynomial_model

def calc_rmse(y_hat, y):
    n = y_hat.shape[0]
    rmse = np.power(np.sum(np.power(y_hat - y, 2)) / n, 0.5)
    return rmse

def compare_basic_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782],
                [6.218875824868201],[6.58351893845611], [6.891820298110627],
                [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    linear_params = py.algorithm.linear_model.optimize(x, y)
    linear_y_hat = py.algorithm.linear_model.calc_value(x, linear_params)
    linear_rmse = calc_rmse(linear_y_hat, y)
    print("Linear Model RMSE: %.2f" %linear_rmse)

    logarithm_params = py.algorithm.logarithm_model.optimize(x, y)
    logarithm_y_hat = py.algorithm.logarithm_model.calc_value(x, logarithm_params)
    logarithm_rmse = calc_rmse(logarithm_y_hat, y)
    print("Logarithm Model RMSE: %.2f" %logarithm_rmse)

    power_params = py.algorithm.power_model.optimize(x, y)
    power_y_hat = py.algorithm.power_model.calc_value(x, power_params)
    power_rmse = calc_rmse(power_y_hat, y)
    print("Power Model RMSE: %.2f" %power_rmse)

    exponential_params = py.algorithm.exponential_model.optimize(x, y)
    exponential_y_hat = py.algorithm.exponential_model.calc_value(x, exponential_params)
    exponential_rmse = calc_rmse(exponential_y_hat, y)
    print("Exponential Model RMSE: %.2f" %exponential_rmse)

    polynomial_params = py.algorithm.polynomial_model.optimize(x, y)
    polynomial_y_hat = py.algorithm.polynomial_model.calc_value(x, polynomial_params)
    polynomial_rmse = calc_rmse(polynomial_y_hat, y)
    print("Polynomial Model RMSE: %.2f" %polynomial_rmse)

if __name__ == "__main__":
    compare_basic_model()


