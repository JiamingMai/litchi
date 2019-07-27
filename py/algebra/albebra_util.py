import numpy as np

def mean(a, direction):
    return np.mean(a, direction)

def covariance(a, b):
    return np.cov(a, b)

def eigen(a):
    eigenvalue, eigenvector = np.linalg.eig(a)
    # eigenvalue 按特征值由大到小排序
    # eigenvector 的第i列对应eigenvalue第i个特征值的特征向量
    return eigenvalue, eigenvector

def max(a, direction):
    return np.max(a, direction)

def min(a, direction):
    return np.min(a, direction)

def determinant(a):
    return np.linalg.det(a)

def inverse(a):
    return np.linalg.inv(a)

def normalize(a, direction):
    if direction == 0:
        normalized_a = np.copy(a)
        for i in range(a.shape[1]):
            ai = a[:, i]
            mx = max(ai, 0)
            mn = min(ai, 0)
            normalized_ai = np.copy(ai)
            normalized_ai = normalized_ai - mn
            normalized_ai = normalized_ai / (mx - mn)
            normalized_a[:, i] = normalized_ai
        return normalized_a
    elif direction == 1:
        normalized_a = np.copy(a)
        for i in range(a.shape[0]):
            ai = a[i, :]
            mx = max(ai, 1)
            mn = min(ai, 1)
            normalized_ai = np.copy(ai)
            normalized_ai = normalized_ai - mn
            normalized_ai = normalized_ai / (mx - mn)
            normalized_a[i, :] = normalized_ai
        return normalized_a
    else:
        return a

def unitize(a):
    unitized_a = np.copy(a)
    if len(a.shape) == 1:
        denominator = np.linalg.norm(a)
        for i in range(len(a)):
            unitized_a[i] = a[i] / denominator
    elif a.shape[0] == 1:
        denominator = np.linalg.norm(a)
        for i in range(a.shape[1]):
            unitized_a[0][i] = a[0][i] / denominator
    elif a.shape[1] == 1:
        denominator = np.linalg.norm(a)
        for i in range(a.shape[0]):
            unitized_a[i][0] = a[i][0] / denominator
    else:
        print("Can only unitize a matrix with single row/column.")
        return unitized_a

def orthogonalize(a):
    dimension = a.shape[0]
    vectorNum = a.shape[1]
    b = np.random.rand(dimension, vectorNum)
    for i in range(vectorNum):
        ai = a[:, i]
        bi = np.copy(ai)
        for j in range(i):
            bj = b[:, j]
            coefficient = np.multiply(np.inner(bj, ai), 1.0 / np.inner(bj, bj))
            coefficient = coefficient[0][0]
        b[:, i] = bi[:, 0]
        # unitization
        for i in range(vectorNum):
            b[:, i] = unitize(b[:, i])
    return b
#此处省略其它方法

if __name__ == "__main__":
    a = np.array([[-1,1,0],
                  [-4,3,0],
                  [1,0,2]])
    eigenvalue, featurevector = eigen(a)
    print(eigenvalue)
    print(featurevector)