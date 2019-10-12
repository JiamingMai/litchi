import numpy as np
import py.service.trend_line_enum as tle
import py.algorithm.linear_model as linear_model
import py.algorithm.logarithm_model as logarithm_model
import py.algorithm.power_model as power_model
import py.algorithm.exponential_model as exponential_model
import py.algorithm.polynomial_model as polynomail_model

class TrendLineServiceWithPrediction():

    def __init__(self, prediction = 3):
        self.prediction_len = prediction

    def wrap_mat(self, values, predition_len = 0):
        x_mat = np.mat(np.zeros((len(values) + predition_len, 1)))
        for i in range(x_mat.shape[0]):
            x_mat[i][0] = i + 1
        y_mat = np.mat(np.zeros((len(values), 1)))
        for i in range(y_mat.shape[0]):
            y_mat[i][0] = values[i]
        matrices = []
        matrices.append(x_mat)
        matrices.append(y_mat)
        return matrices

    def unwrap_mat(self, y_hat):
        trend_line_values = []
        for i in range(y_hat.shape[0]):
            trend_line_values.append(y_hat[i, 0])
        return trend_line_values

    def calc_rmse(self, model, x, y):
        params = model.optimize(x, y)
        y_hat = model.calc_value(x, params)
        rmse = np.sqrt(np.dot(np.transpose(np.subtract(y_hat, y)), np.subtract(y_hat, y)) / y.shape[0])
        return rmse

    def select_best_model(self, x, y):
        models = []
        models.append(linear_model)
        models.append(logarithm_model)
        models.append(exponential_model)
        models.append(polynomail_model)
        models.append(power_model)
        best_model_index = -1
        min_rmse = 9999999
        for i in range(len(models)):
            model = models[i]
            rmse = self.calc_rmse(model, x, y)
            print("Model: %s, RMSE: %f" %(model, rmse))
            if rmse < min_rmse:
                min_rmse = rmse
                best_model_index = i
        best_model = models[best_model_index]
        print("Best Model: %s" %(best_model))
        return best_model


    def estimate_values(self, values, trend_line_type):
        matrices = self.wrap_mat(values)
        x = matrices[0]
        y = matrices[1]
        basic_model = linear_model
        if trend_line_type == tle.TrendLineEnum.AUTO:
            basic_model = self.select_best_model(x, y)
        elif trend_line_type == tle.TrendLineEnum.LINEAR:
            basic_model = linear_model
        elif trend_line_type == tle.TrendLineEnum.LOGRITHM:
            basic_model = logarithm_model
        elif trend_line_type == tle.TrendLineEnum.EXPONENTIAL:
            basic_model = exponential_model
        elif trend_line_type == tle.TrendLineEnum.POLYNOMIAL:
            basic_model = polynomail_model
        elif trend_line_type == tle.TrendLineEnum.POWER:
            basic_model = power_model
        else:
            # can't happen
            return
        params = basic_model.optimize(x, y)
        if (self.prediction_len > 0):
            matrices = self.wrap_mat(values, self.prediction_len)
            x = matrices[0]
        y_hat = basic_model.calc_value(x, params)
        trend_line_values = self.unwrap_mat(y_hat)
        return trend_line_values