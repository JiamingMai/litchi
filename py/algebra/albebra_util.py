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

def unitize(a):
    if len(a.shape) == 1:
        denominator = np.linalg.norm(a)
        for i in range(len(a)):
            a[i] = a[i] / denominator
    elif a.shape[0] == 1:
        denominator = np.linalg.norm(a)
        for i in range(a.shape[1]):
            a[0][i] = a[0][i] / denominator
    elif a.shape[1] == 1:
        denominator = np.linalg.norm(a)
        for i in range(a.shape[0]):
            a[i][0] = a[i][0] / denominator
    else:
        print("Can only unitize a matrix with single row/column.")
        return a

#此处省略其它方法

if __name__ == "__main__":
    a = np.array([[-1,1,0],
                  [-4,3,0],
                  [1,0,2]])
    eigenvalue, featurevector = eigen(a)
    print(eigenvalue)
    print(featurevector)