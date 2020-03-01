#!/usr/bin/python3

import matplotlib.pyplot as plt
import numpy as np
import random
from mpl_toolkits.mplot3d import axes3d, Axes3D

def rosen(x,y):
	return (1 - x)**2 + 100 * (y - x**2)**2

def grad(x,y):
  g1 = -400*x*y + 400*x**3 + 2*x -2
  g2 = 200*y -200*x**2
  return np.array([g1,g2])

def hess(x,y):
  h11 = -400*y + 1200*x**2 + 2
  h12 = -400 * x
  h21 = -400 * x
  h22 = 200
  return np.array([[h11,h12],[h21,h22]])

def Newton_Raphson_Optimize(Grad, Hess, x,y, epsilon=0.000001, nMax = 200):
  i = 0
  iter_x, iter_y, iter_count = np.empty(0),np.empty(0), np.empty(0)
  error = 10
  X = np.array([x,y])

  while np.linalg.norm(error) > epsilon and i < nMax:
    i +=1
    iter_x = np.append(iter_x,x)
    iter_y = np.append(iter_y,y)
    iter_count = np.append(iter_count ,i)   
    print(X) 

    X_prev = X
    X = X - np.linalg.inv(Hess(x,y)) @ Grad(x,y)
    error = X - X_prev
    x,y = X[0], X[1]
  return X, iter_x,iter_y, iter_count

if __name__ == '__main__':
  x_0 = random.uniform(-2.0, 2.0)
  y_0 = random.uniform(-2.0, 2.0)
  root,iter_x,iter_y, iter_count = Newton_Raphson_Optimize(grad,hess,x_0,y_0)

  x = np.linspace(-3,3,250)
  y = np.linspace(-9,8,350)
  X, Y = np.meshgrid(x, y)
  Z = rosen(X, Y)

  anglesx = iter_x[1:] - iter_x[:-1]
  anglesy = iter_y[1:] - iter_y[:-1]

  fig = plt.figure(figsize = (16,8))

  ax = fig.add_subplot(1, 2, 1, projection='3d')
  ax.plot_surface(X,Y,Z,rstride = 5, cstride = 5, cmap = 'jet', alpha = .4, edgecolor = 'none' )
  ax.plot(iter_x,iter_y, rosen(iter_x,iter_y),color = 'r', marker = '*', alpha = .4)

  ax.view_init(45, 280)
  ax.set_xlabel('x')
  ax.set_ylabel('y')

  ax = fig.add_subplot(1, 2, 2)
  ax.contour(X,Y,Z, 60, cmap = 'jet')
  ax.scatter(iter_x,iter_y,color = 'r', marker = '*')
  ax.quiver(iter_x[:-1], iter_y[:-1], anglesx, anglesy, scale_units = 'xy', angles = 'xy', scale = 1, color = 'r', alpha = .3)

  plt.show()
