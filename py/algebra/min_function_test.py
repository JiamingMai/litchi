import numpy as np

def test_min(a):
    min_mat1 = np.min(a, 0)
    print(min_mat1)
    min_mat2 = np.min(a, 1)
    print(min_mat2)

if __name__ == "__main__":
    a = np.array([[5, 4, 8], [1, 5, 6]])
    test_min(a)