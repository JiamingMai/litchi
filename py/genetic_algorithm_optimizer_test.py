import py.target_function as tf
import py.genetic_algorithm_optimizer as gao
import numpy as np


class TestTargetFunction(tf.TargetFunction):

    # f(x) = a * x + b
    def fun(self, params, args):
        x = args[0]
        a = params[0]
        b = params[1]
        res = a * x + b
        return res


def test_optimizer():
    bounaries = np.array([[-5, 5], [-5, 5]])
    optimizer = gao.GeneticAlgorithmOptimizer(bounaries)
    params = np.array([0.1, 0.1])
    train_input = np.array([[-1], [0], [1]])
    truth_output = np.array([[-1], [1], [3]])
    target_function = TestTargetFunction()
    optimized_params = optimizer.optimize(target_function, params, train_input, truth_output)
    print(optimized_params)


if __name__ == "__main__":
    test_optimizer()