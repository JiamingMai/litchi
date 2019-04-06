import os
import numpy as np


def cluster(x, k, epoch_num):
    indices = np.random.randint(0, x.shape[0], k)
    u = []
    for i in indices:
        u.append(x[i, :])

    for e in range(epoch_num):
        classes = []
        for i in range(x.shape[0]):
            dist = []
            for j in range(k):
                dist.append(np.linalg.norm(x[i, :] - u[j]))
            for j in range(k):
                if (dist[j] == np.min(dist)):
                    classes.append(j)
                    break
        for j in range(k):
            xj = x[np.array(classes) == j, :]
            u[j] = np.mean(xj, 0)
    return u


if __name__ == "__main__":
    file_path = os.getcwd() + "/../src/main/resources/cluster_test_data.csv"
    x = np.loadtxt(file_path, delimiter=",",usecols=(1, 2), unpack=True)
    x = np.transpose(x)
    u = cluster(x, 3, 100)
    print(u)