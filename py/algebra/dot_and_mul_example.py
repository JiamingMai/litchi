import numpy as np

def test_dot(a, b):
    c = np.dot(a, b)
    print(c)

def test_mul(a, b):
    c = np.multiply(a, b)
    print(c)

if __name__ == "__main__":
    a1 = np.array([[1, 2], [3, 4], [6, 4]])
    b1 = np.array([[4, 2], [1, 5], [3, 3]])
    test_dot(a1, b1)

    a2 = np.array([[1, 2], [3, 4], [6, 4]])
    b2 = np.array([[4, 2], [1, 5], [3, 3]])
    test_mul(a2, b2)