import py.optimize.target_function as tf
import py.optimize.steepest_descent_optimizer as sdo
import numpy as np

class TestTargetFunction(tf.TargetFunction):

    # f(x1, x2) = x1^2 + x2
    def fun(self, params, args):
        x1 = args[0]
        x2 = args[1]
        res = np.add(np.multiply(params[0], np.power(x1, 2)), np.multiply(params[1], x2))
        return res

def test_calc_partial_derivative():
    params = np.array([1.0, 2.0])
    args = np.array([4.0, 3.0])
    target_function = TestTargetFunction()
    optimizer = sdo.SteepestDescentOptimizer()
    partial_derivative = optimizer.calc_partial_derivative(target_function, params, 0, args)
    print(partial_derivative)

if __name__ == "__main__":
    test_calc_partial_derivative()