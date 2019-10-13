default_windows_size = 3

def optimize(x, y):
    # no need to optimize the parameters
    params = []
    return params


def calc_value(x, params):
    y_hat = []
    for i in range(len(x)):
        if (i + 1 < default_windows_size):
            sum = 0.0
            for j in range(i + 1):
                sum = sum + x[j]
            val = sum / (i + 1)
            y_hat.append(val)
        else:
            sum = 0.0
            for j in range(i - default_windows_size + 1, i):
                sum = sum + x[j]
            val = sum / default_windows_size
            y_hat.append(val)
    return y_hat