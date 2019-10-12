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
        res = np.power(a1 * x1 + a2 * x2 + a3 * x3 + a4 * x4, 2)
        return res

def test_optimizer():
    params = np.array([0.3, 0.3, 0.5, 0.2])
    args = np.array([5, -2, 3, -6])
    target_function = TestTargetFunction()
    optimizer = sdo.SteepestDescentOptimizer(epoch_num= 10000)
    optimized_params = optimizer.optimize_with_args(target_function, params, args)
    print(optimized_params)

if __name__ == "__main__":
    test_optimizer()