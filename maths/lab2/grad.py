#!/usr/bin/python3

import numpy as np

def grad_f(X):
  return np.array([2*(X[0]-1)+ 40*X[0]*(X[0]**2-X[1]),20*(X[1]-X[0]**2)])

def proj(X):
  return np.array([min(0.5, X[0]), min(0.5, X[1])])

def gradient_descent(X0, step, epsilon=0.00001, maxIter = 10000 ):
  i = 0
  err = 2*epsilon
  X1 = proj(X0 - step * grad_f(X0))
  while err >= epsilon and i < maxIter:
    X1 = proj(X1 - grad_f(X1) * step)
    err = np.abs((X1[0]-X0[0])**2+(X1[1]-X0[1])**2)
    i += 1
  return X1

if __name__ == '__main__':
  res = gradient_descent(np.array([-1,-1]), 0.00001)
  print(res)
