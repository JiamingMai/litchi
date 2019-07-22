import numpy as np

def test_max(a):
    max_mat1 = np.max(a, 0)
    print(max_mat1)
    max_mat2 = np.max(a, 1)
    print(max_mat2)

if __name__ == "__main__":
    a = np.array([[5, 4, 8], [1, 5, 6]])
    test_max(a)