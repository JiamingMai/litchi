import numpy as np
import py.algorithm.linear_model


def optimize(x, y):
    params = py.algorithm.linear_model.optimize(x, np.log(y))
    lna = params[0][0]
    a = np.exp(lna)
    params[0][0] = a
    return params


def calc_value(x, params):
    a = params[0][0]
    params[0][0] = np.log(a)
    lny = py.algorithm.linear_model.calc_value(x, params)
    y = np.exp(lny)
    return y