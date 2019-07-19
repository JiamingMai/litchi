import numpy as np

def test_identity_mat(n):
    c = np.identity(n)
    print(c)

if __name__ == "__main__":
    test_identity_mat(5)