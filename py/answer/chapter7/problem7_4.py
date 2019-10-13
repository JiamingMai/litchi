import numpy as np
import py.algorithm.linear_model
import py.algorithm.logarithm_model
import py.algorithm.exponential_model
import py.algorithm.polynomial_model
import py.algorithm.power_model

def test_linear_model(x, y):
    print("Linear Model:")
    params = py.algorithm.linear_model.optimize(x, y)
    y_hat = py.algorithm.linear_model.calc_value(np.mat([[5]]), params)
    print(y_hat)
    rmse = calc_rmse(py.algorithm.linear_model.calc_value(x, params), y)
    print("RMSE: %.2f" %rmse)
    print()

def test_logarithm_model(x, y):
    print("Logarithm Model:")
    params = py.algorithm.logarithm_model.optimize(x, y)
    y_hat = py.algorithm.logarithm_model.calc_value(np.mat([[5]]), params)
    print(y_hat)
    rmse = calc_rmse(py.algorithm.logarithm_model.calc_value(x, params), y)
    print("RMSE: %.2f" %rmse)
    print()

def test_exponential_model(x, y):
    print("Exponential Model:")
    params = py.algorithm.exponential_model.optimize(x, y)
    y_hat = py.algorithm.exponential_model.calc_value(np.mat([[5]]), params)
    print(y_hat)
    rmse = calc_rmse(py.algorithm.exponential_model.calc_value(x, params), y)
    print("RMSE: %.2f" %rmse)
    print()

def test_polynomial_model(x, y):
    print("Polynomial Model:")
    params = py.algorithm.polynomial_model.optimize(x, y)
    y_hat = py.algorithm.polynomial_model.calc_value(np.mat([[5]]), params)
    print(y_hat)
    rmse = calc_rmse(py.algorithm.polynomial_model.calc_value(x, params), y)
    print("RMSE: %.2f" %rmse)
    print()

def test_power_model(x, y):
    print("Power Model:")
    params = py.algorithm.power_model.optimize(x, y)
    y_hat = py.algorithm.power_model.calc_value(np.mat([[5]]), params)
    print(y_hat)
    rmse = calc_rmse(py.algorithm.power_model.calc_value(x, params), y)
    print("RMSE: %.2f" %rmse)
    print()

def calc_rmse(y_hat, y):
    rmse = np.sqrt(np.divide(np.sum(np.power(y_hat - y, 2)), y_hat.shape[0]))
    return rmse

if __name__ == "__main__":
    x = np.mat([[4], [8], [9], [8], [7], [12], [6], [10], [6], [9]])
    y = np.mat([[9], [20], [22], [15], [17], [23], [18], [25], [10], [20]])
    test_linear_model(x, y)
    test_logarithm_model(x, y)
    test_exponential_model(x, y)
    test_polynomial_model(x, y)
    test_power_model(x, y)