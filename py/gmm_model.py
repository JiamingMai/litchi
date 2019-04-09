import os
import numpy as np


def cluster(x, k, epoch_num):
    random_labels = np.random.randint(0, k, x.shape[0])
    u = []
    sigma = []
    for i in range(k):
        xi = x[random_labels == i, :]
        ui = np.mean(xi, 0)
        sigma_i = np.cov(np.transpose(xi))
        u.append(ui)
        sigma.append(sigma_i)

    for e in range(epoch_num):
        print("epoch #%d" %(e+1))
        new_u = []
        new_sigma = []
        for i in range(k):
            new_ui = 0
            new_sigma_i = np.zeros([x.shape[1], x.shape[1]])
            ni = 0
            for j in range(x.shape[0]):
                pj = 0
                for s in range(k):
                    pj = pj + gaussian_fun(x[j], u[s], sigma[s])
                pji = gaussian_fun(x[j], u[i], sigma[i]) / pj
                new_ui = new_ui + pji * x[j]
                ni = ni + pji
                new_sigma_i = new_sigma_i + pji * np.array(np.transpose(np.mat(x[j] - u[i])).dot(np.mat(x[j] - u[i])))
            print("cluster #%d have %d samples" %(i, ni))
            new_ui = new_ui / ni
            new_sigma_i = new_sigma_i / ni
            new_u.append(new_ui)
            new_sigma.append(new_sigma_i)
        u = new_u
        sigma = new_sigma
        print(u)
        print()
    return u, sigma


def gaussian_fun(x, u, sigma):
    d = len(x)
    left_term = 1 / (np.power(2*np.pi, d/2) * np.power(np.linalg.det(sigma), 0.5))
    right_term = np.exp(-0.5 * np.dot(np.dot(np.transpose(x - u), np.linalg.inv(sigma)), x - u))
    p = left_term * right_term
    return p