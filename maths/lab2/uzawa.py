#!/usr/bin/python3

import numpy as np

def uzawa(A, b, h, X0, lambda0):
  i = 0
  eps = 10
  while np.all(eps > 0.0000001) and i < 1000000:
    lambda1 = np.maximum(lambda0 + 4 * (h @ X0), np.zeros(3))
    X1 = np.linalg.inv(A) @ (b.transpose() - (h.transpose() @ lambda1).transpose()).transpose()
    lamdba0 = lambda1.transpose()
    eps = abs(X1.transpose() - X0)
    X0 = X1.transpose()
    i+=1
  return X1

if __name__ == '__main__':
  N = np.array([2,2,2])
  h = 1 / (N + 1)
  A = np.array([[2,-1,0],
                [-1,2,-1],
                [0,-1,2]])
  b = np.array([-8,-8,-8])
  r = uzawa(A, b, h, np.array([1,1,1]), np.array([1,1,1]))
  print(r)
