import numpy as np
import py.algorithm.linear_model


def optimize(x, y):
    lnx = np.log(x)
    lny = np.log(y)
    params = py.algorithm.linear_model.optimize(lnx, lny)
    lna = params[0][0]
    a = np.exp(lna)
    params[0][0] = a
    return params


def calc_value(x, params):
    lnx = np.log(x)
    a = params[0][0]
    lna = np.log(a)
    params[0][0] = lna
    lny = py.algorithm.linear_model.calc_value(lnx, params)
    y = np.exp(lny)
    return y