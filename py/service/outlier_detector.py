import numpy as np
import py.algorithm.gmm_model as gmm_model
import py.algorithm.pca_model as pca
import py.algebra.albebra_util as au

class OutlierDetector():

    def __init__(self):
        self.max_feature_num = 2
        self.pca_model = pca.PcaModel()
        self.epsilon = 0.01

    def detect_outlier(self, records):
        key_set = list(records.keys())
        feature_num = len(records[key_set[0]])
        record_num = len(key_set)
        # Step 1. Convert Map to Matrix
        x = np.zeros((record_num, feature_num))
        for i in range(record_num):
            key = key_set[i]
            x[i, :] = records[key]

        # reduce dimension with PCA model if there are too many features
        if feature_num > self.max_feature_num:
            normalized_x = au.normalize(x, 0)
            x = np.zeros((record_num, self.max_feature_num))
            inputs = []
            for i in range(record_num):
                input = np.zeros((feature_num, 1))
                input[:, 0] = np.transpose(normalized_x[i, :])
                inputs.append(input)
            self.pca_model.train(inputs, self.max_feature_num)
            for i in range(record_num):
                xi = inputs[i]
                x[i, :] = np.transpose(self.pca_model.encode(xi))

        # Step 2. Calculate the mean vector u and the covariance matrix sigma
        x = au.normalize(x, 0)
        u = au.mean(x, 0)
        sigma = np.cov(np.transpose(x))

        # Step 3. Calculate the result of Gaussian Function and detect the outliers
        outputs = {}
        for i in range(record_num):
            xi = np.transpose(x[i, :])
            p = gmm_model.gaussian_fun(xi, u, sigma)
            if (p < self.epsilon):
                outputs[i] = xi
        return outputs


