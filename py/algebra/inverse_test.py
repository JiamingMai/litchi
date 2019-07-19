import numpy as np

def test_inverse(a):
    c = np.linalg.inv(a)
    print(c)
    print(np.dot(a, c))


if __name__ == "__main__":
    a = np.array([[1, 2, 3], [3, 2, 1], [1, 4, 2]])
    test_inverse(a)