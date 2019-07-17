import numpy as np

def test_add(a, b):
    c = np.add(a, b)
    print(c)

def test_sub(a, b):
    c = np.subtract(a, b)
    print(c)

if __name__ == "__main__":
    a = np.array([[1, 2], [3, 4], [6, 4]])
    b = np.array([[4, 2], [1, 5], [3, 3]])
    test_add(a, b)
    test_sub(a, b)