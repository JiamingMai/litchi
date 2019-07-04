import numpy as np
import py.algorithm.linear_model


def optimize(x, y):
    params = py.algorithm.linear_model.optimize(np.log(x), y)
    return params


def calc_value(x, params):
    y_hat = py.algorithm.linear_model.calc_value(np.log(x), params)
    return y_hat