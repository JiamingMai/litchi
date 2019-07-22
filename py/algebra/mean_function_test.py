import numpy as np

def test_mean(a):
    mean_mat1 = np.mean(a, 0)
    print(mean_mat1)
    mean_mat2 = np.mean(a, 1)
    print(mean_mat2)

if __name__ == "__main__":
    a = np.array([[5, 4, 8], [1, 5, 6]])
    test_mean(a)