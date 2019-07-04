import numpy as np
import py.algorithm.exponential_model


def test_exponential_model():
    x = np.mat([[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]])
    y = np.mat([[22.16716829679195], [163.7944500994327], [1210.2863804782053], [8942.873961125184], [66079.39738442015],
                [488264.37425701175], [3607812.85249433], [26658331.561523616], [196979907.41199154], [1455495586.2293708]])
    params = py.algorithm.exponential_model.optimize(x, y)
    print(params)
    y_hat = py.algorithm.exponential_model.calc_value(x, params)
    print(y_hat)


if __name__ == "__main__":
    test_exponential_model()