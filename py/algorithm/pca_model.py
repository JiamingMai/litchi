import numpy as np
import py.algebra.albebra_util as au

class PcaModel():

    def train(self, samples, dimension):
        n = len(samples)
        m = samples[0].shape[0]
        input = np.zeros((n, m))
        for i in range(n):
            for j in range(m):
                input[i][j] = samples[i][j, 0]
        # Step 1. Calculate the mean vector u
        self.u = au.mean(input, 0)

        # Step 2. Calculate the scattering matrix S
        self.s = np.zeros((m, m))
        for i in range(n):
            x = input[i]
            temp = np.dot(np.subtract(x, self.u), np.transpose(np.subtract(x, self.u)))
            self.s = np.add(self.s, temp)

        # Step 3. Calculate the mapping matrix A
        self.a = np.zeros((m, dimension))
        eigenvalues, eigenvectors = au.eigen(self.s)
        for i in range(dimension):
            self.a[:, i] = eigenvectors[:, i]

    def encode(self, x):
        z = np.dot(np.transpose(self.a), x)
        return z