import py.optimize.rmse_function as rf
import py.optimize.optimizer as opt
import numpy as np

class SteepestDescentOptimizer(opt.Optimizer):

    def __init__(self, epoch_num = 100000, learning_rate = 0.0001):
        self.epoch_num = epoch_num
        self.learning_rate = learning_rate

    def calc_partial_derivative(self, target_function, params, partial_variable_index, args):
        epsilon = 0.00000001
        param = params[partial_variable_index]
        left_offset = param - epsilon
        right_offset = param + epsilon
        left_offset_params = np.array(params)
        left_offset_params[partial_variable_index] = left_offset
        right_offset_params = np.array(params)
        right_offset_params[partial_variable_index] = right_offset
        fx = target_function.fun(params, args)
        left_derivative = np.divide(target_function.fun(left_offset_params, args) - fx, -epsilon)
        right_derivative = np.divide(target_function.fun(right_offset_params, args) - fx, epsilon)
        partial_derivative = np.multiply(left_derivative + right_derivative, 0.5)
        return partial_derivative

    def optimize(self, target_function, params, train_input, truth_output, to_wrap_rmse_function=True):
        if to_wrap_rmse_function == True:
            target_function = rf.RmseFunction(target_function)
        args = np.concatenate((train_input, truth_output), 1)
        last_params = np.array(params)
        new_params = np.array(params)
        for e in range(self.epoch_num):
            print("Epoch #%d" %e, new_params)
            for i in range(new_params.shape[0]):
                partial_derivative = self.calc_partial_derivative(target_function, last_params, i, args)
                param = np.subtract(new_params[i], self.learning_rate * partial_derivative)
                new_params[i] = param
            last_params = np.array(new_params)
        return new_params

