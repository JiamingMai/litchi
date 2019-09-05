import numpy as np
import py.optimize.genetic_algorithm_optimizer as gao
import py.optimize.holt_winters_rmse_function as hwrf

class HoltWintersModel():

    def optimize(self, y, cycle_len, epoch_num=10000):
        boundaries = np.zeros((3, 2))
        for i in range(boundaries.shape[0]):
            boundaries[i][0] = 0.0
            boundaries[i][1] = 1.0
        optimizer = gao.GeneticAlgorithmOptimizer(boundaries, epoch_num)
        # initalize parameters that are going to be optimized
        params = np.zeros((1, 3))
        for i in range(params.shape[0]):
            params[i][0] = 0.001
        params = optimizer.optimize(hwrf.HoltWintersRmseFunction(cycle_len), params, y, y, False)
        return params

    def fittedValue(self, y, params, cycle_len, prediction_len):
        alpha = params[0]
        beta = params[1]
        gama = params[2]
        s = []
        b = []
        c = []
        yHat = []

        # Initialize s0
        sum = 0.0
        for i in range(cycle_len):
            sum = sum + y[i][0]
        s0 = sum / cycle_len
        s.append(s0)

        # Initialize b0
        sum = 0.0
        for i in range(cycle_len, 2 * cycle_len):
            sum = sum + (y[i][0] - y[i - cycle_len][0])
        b0 = sum / (cycle_len * cycle_len)
        b.append(b0)

        # Initialize c
        for i in range(cycle_len):
            ci = y[i][0] - s[0]
            c.append(ci)

        # Initialize yHat
        y0 = s[0] + b[0] + c[0]
        yHat.append(y0)

        # Estimate yHat
        for i in range(y.shape[0]):
            # estimate si, bi, ci, yHat
            s.append(alpha * (y[i][0] - c[i]) + (1 - alpha) * (s[i] + b[i]))
            b.append(beta * (s[i+1] - s[i]) + (1 - beta) * b[i])
            c.append(gama * (y[i][0] - s[i]) - b[i] + (1 - gama) * c[i])
            yHat.append(s[i + 1] + b[i + 1] + c[i + 1])
            if i == y.shape[0] - 1:
                for j in range(2, prediction_len + 2):
                    yHat.append(s[i + 1] + (b[i + 1] * j) + c[i + 1] + (j % prediction_len))

        yHat = yHat[1:]
        yHatMat = np.zeros((len(yHat), 1))
        for i in range(yHatMat.shape[0]):
            yHatMat[i][0] = yHat[i]
        return yHatMat



