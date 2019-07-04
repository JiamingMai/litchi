import numpy as np


def optimize(x, y):
    x = convert_to_mat_with_x0(x)
    params = np.dot(np.dot(np.dot(np.transpose(x), x).I, np.transpose(x)), y)
    return params


def calc_value(x, params):
    x = convert_to_mat_with_x0(x)
    y_hat = np.dot(x, params)
    return y_hat


def convert_to_mat_with_x0(x):
    return np.c_[np.ones(x.shape[0]), x]