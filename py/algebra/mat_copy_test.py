import numpy as np

def test_copy_mat(a):
    c = np.copy(a)
    a[0][0] = 9
    print(a)
    print(c)

if __name__ == "__main__":
    a = np.array([[1, 2, 3], [4, 5, 6]])
    test_copy_mat(a)