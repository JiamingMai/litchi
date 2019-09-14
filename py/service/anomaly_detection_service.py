import numpy as np
import py.algebra.albebra_util as au
import py.algorithm.holt_winters_model as hwm

class AnomalyDetionService():

    def __init__(self):
        self.epoch_num = 20
        self.cycle_len = 7

    def detect_anomaly(self, y, y_next):
        # wrap mat
        y_mat = np.zeros((len(y), 1))
        for i in range(y_mat.shape[0]):
            y_mat[i][0] = y[i]
        # predict value of next timestamp
        holt_winters_model = hwm.HoltWintersModel()
        optimized_params = holt_winters_model.optimize(y_mat, self.cycle_len, self.epoch_num)
        y_hat = holt_winters_model.fittedValue(y_mat, optimized_params, self.cycle_len, 1)
        predicted_value = y_hat[-1]
        # calculate 3 sigma
        sigma = np.var(y_mat, 0)
        upperBound = predicted_value + 3 * sigma
        lowerBound = predicted_value - 3 * sigma
        if (y_next > upperBound or y_next < lowerBound):
            return True
        else:
            return False