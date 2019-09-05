import numpy as np
import py.algorithm.holt_winters_model as hwm


def test_holt_winters_model():
    cycle_len = 7
    y = np.zeros((17, 1))
    y[0][0] = 1.0
    y[1][0] = 2.0
    y[2][0] = 4.0
    y[3][0] = 8.0
    y[4][0] = 16.0
    y[5][0] = 32.0
    y[6][0] = 64.0
    y[7][0] = 128.0
    y[8][0] = 256.0
    y[9][0] = 512.0
    y[10][0] = 1024.0
    y[11][0] = 2048.0
    y[12][0] = 4096.0
    y[13][0] = 8192.0
    y[14][0] = 16384.0
    y[15][0] = 32768.0
    y[16][0] = 65536.0
    holt_winters_model = hwm.HoltWintersModel()
    best_params = holt_winters_model.optimize(y, cycle_len, 20)
    forecast_values = holt_winters_model.fittedValue(y, best_params, cycle_len, 3)
    print(list(forecast_values[:, 0]))

if __name__ == "__main__":
    test_holt_winters_model()