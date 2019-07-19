import numpy as np

def test_mat_basic():
    mat1 = np.array([[1, 2, 3], [4, 5, 6]])
    print(mat1)
    mat2 = np.zeros((3, 2))
    print(mat2)
    mat3 = np.ones((3, 2))
    print(mat3)
    mat4 = np.random.rand(3, 2)
    print(mat4)

if __name__ == "__main__":
    test_mat_basic()