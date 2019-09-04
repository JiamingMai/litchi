import py.optimize.target_function as tf
import numpy as np


class HoltWintersRmseFunction(tf.TargetFunction):

    def __init__(self, cycle_len):
        self.cycle_len = cycle_len

    def fun(self, params, args):
        y = args[:, -1]
        alpha = params[0][0]
        beta = params[0][1]
        gama = params[0][2]
        s = []
        b = []
        c = []
        yHat = []

        # Initialize s0
        sum = 0.0
        for i in range(self.cycle_len):
            sum = sum + y[i][0]
        s0 = sum / self.cycle_len
        s.append(s0)

        # Initialize b0
        sum = 0.0
        for i in range(self.cycle_len, 2 * self.cycle_len):
            sum = sum + (y[i][0] - y[i - self.cycle_len][0])
        b0 = sum / (self.cycle_len * self.cycle_len)
        b.append(b0)

        # Initialize c
        for i in range(self.cycle_len):
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

        # Calculate RMSE
        sum = 0.0
        for i in range(y.shape[0]):
            sum = sum + np.power(y[i][0] - yHat[i], 2)
        rmse = np.sqrt(sum / y.shape[0])
        return rmse