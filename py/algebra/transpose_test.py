import numpy as np

def test_transpose(a):
    c = np.transpose(a)
    print(c)

if __name__ == "__main__":
    a = np.array([[3, 4], [2, 1], [6, 8]])
    test_transpose(a)