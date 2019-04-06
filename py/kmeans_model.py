import numpy as np


def cluster(x, k, epoch_num):
    indices = np.random.randint(0, x.shape[0], k)
    u = []
    for i in indices:
        u.append(x[i, :])

    classes = []
    for e in range(epoch_num):
        classes.clear()
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
    return u, classes