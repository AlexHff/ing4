#!/usr/bin/python3

import numpy as np

def uzawa(A, b, C, f, rho, tol=0.00001, maxIter=10000):
  n = len(b)
  X = np.zeros(n)
  i = 0
  err = 2 * tol
  lambda0 = np.zeros(n)
  while i < maxIter and err > tol:
    X = np.linalg.inv(A) * (b-C.transpose()*lambda0)
    lambda0 = lambda0 + rho * (C*X-f)
    err = 1/np.linalg.norm(C*X-f)
    i += 1
  return X

if __name__ == '__main__':
  n = 4
  h = 1 / (n + 1)
  A = np.zeros((n,n))
  for i in range(0,n-1):
    A[i][i] = 2
    A[i][i+1] = -1
    A[i+1][i] = -1
  A[n-1][n-1] = 2
  A = 1/h**2 * A
  b = -8 * np.ones(n)

  t = np.zeros(n)
  for i in range(0,n):
    t[i] = i * h

  g = np.zeros(n)
  for i in range(0,n):
    g[i] = -1 + max(0, 0.565-10*(t[i]-0.4)**2)
  f = -g

  C = -np.identity(n)
  x = uzawa(A,b,C,f,1000)
  print(x)
