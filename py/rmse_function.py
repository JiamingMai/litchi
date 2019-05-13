import py.target_function as tf
import numpy as np


class RmseFunction(tf.TargetFunction):

    def __init__(self, target_function):
        self.target_function = target_function

    def fun(self, params, args):
        truth_output = args[:, -1]
        train_input = args[:, :-1]
        res_array = []
        for i in range(train_input.shape[0]):
            input = np.array(train_input[i, :])
            res = self.target_function.fun(params, input)
            res_array.append(res)
        res = np.array(res_array)
        rmse = np.sqrt(np.divide(np.sum(np.power(res - truth_output, 2)), res.shape[0]))
        return rmse