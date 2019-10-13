import numpy as np
import py.algebra.albebra_util as au

a = np.array([[6, 7, 1], [2, 2, 4]])
b = np.array([[8, 1, 3], [4, 4, 1]])
print(np.concatenate(a, b, 1))