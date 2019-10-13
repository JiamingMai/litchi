import py.optimize.target_function as tf
import py.optimize.steepest_descent_optimizer as sdo
import numpy as np


class TestTargetFunction(tf.TargetFunction):

    def fun(self, params, args):
        x1 = params[0]
        x2 = params[1]
        x3 = params[2]
        x4 = params[3]
        a1 = args[0]
        a2 = args[1]
        a3 = args[2]
        a4 = args[3]
        res = a1 * np.power(x1, 3) + a2 * np.power(x2, 2) + a3 * x3 + a4 * x4
        return res

def test_calc_partial_derivative():
    params = np.array([7.0, 5.0, -1.0, 16.0])
    args = np.array([4.0, 2.0, 3.0, 1.0])
    target_function = TestTargetFunction()
    optimizer = sdo.SteepestDescentOptimizer(epoch_num = 10000)
    partial_derivative_of_x1 = optimizer.calc_partial_derivative(target_function, params, 0, args)
    partial_derivative_of_x2 = optimizer.calc_partial_derivative(target_function, params, 1, args)
    partial_derivative_of_x3 = optimizer.calc_partial_derivative(target_function, params, 2, args)
    partial_derivative_of_x4 = optimizer.calc_partial_derivative(target_function, params, 3, args)
    print(partial_derivative_of_x1)
    print(partial_derivative_of_x2)
    print(partial_derivative_of_x3)
    print(partial_derivative_of_x4)

if __name__ == "__main__":
    test_calc_partial_derivative()