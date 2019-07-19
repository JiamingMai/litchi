import numpy as np

def test_merge_mat(a, b):
    c1 = np.concatenate([a, b], 0)
    print(c1)
    c2 = np.concatenate([a, b], 1)
    print(c2)

if __name__ == "__main__":
    a = np.array([[1, 2, 3], [4, 5, 6]])
    b = np.array([[7, 8, 9], [10, 11, 12]])
    test_merge_mat(a, b)