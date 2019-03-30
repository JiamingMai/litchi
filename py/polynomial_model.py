import numpy as np
import py.linear_model


def optimize(x, y):
    x = np.c_[x, np.power(x, 2), np.power(x, 3)]
    params = py.linear_model.optimize(x, y)
    return params


def calc_value(x, params):
    x = np.c_[x, np.power(x, 2), np.power(x, 3)]
    y = py.linear_model.calc_value(x, params)
    return y