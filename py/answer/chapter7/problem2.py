import numpy as np
import py.optimize.target_function as tf
import py.optimize.steepest_descent_optimizer as sdo

class TestTargetFunction(tf.TargetFunction):

    def fun(self, params, args):
        res = np.dot(params, np.transpose(args))
        return res


class SteepestDecentLinearModel():

    def optimize(self, x, y):
        params = np.ones(x.shape[1] + 1) * 0.1
        x = self.convert_to_mat_with_x0(x)
        target_function = TestTargetFunction()
        optimizer = sdo.SteepestDescentOptimizer( epoch_num = 1000, learning_rate = 0.0001)
        optimized_params = optimizer.optimize(target_function, params, x, y)
        return optimized_params


    def calc_value(self, x, params):
        x = self.convert_to_mat_with_x0(x)
        y_hat = np.dot(x, params) 
        return y_hat


    def convert_to_mat_with_x0(self, x):
        return np.c_[np.ones(x.shape[0]), x]

if __name__ == '__main__':
    x = np.mat([[-1], [0], [1], [2], [3], [4], [5], [6], [7], [8]])
    y = np.mat([[3], [4.38629436119891], [5.19722457733622], [5.772588722239782], [6.218875824868201],
                [6.58351893845611], [6.891820298110627], [7.1588830833596715], [7.394449154672439], [7.605170185988092]])
    linear_model = SteepestDecentLinearModel()
    params = linear_model.optimize(x, y)
    y_hat = linear_model.calc_value(x, params)
    print(y_hat)