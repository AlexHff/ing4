#!/usr/bin/python3

import matplotlib.pyplot as plt
import numpy as np
import random
from mpl_toolkits.mplot3d import axes3d, Axes3D

def gradient_descent(grad_rosen, x, y, alpha = 0.00125, epsilon=0.0001, nMax = 10000 ):
  """
  Perform gradient descent on given samples.

  Parameters
  ----------
  Grad : {callable}, function
  x : float
  y : float
  alpha : float
          Learning rate
  epsilon : float
            Max allowed difference
  nMax : int
         Maximum number of iterations

  Returns
  -------
  X : {array-like}, matrice containing results
  x_i : {float}, last iteration of the algorithm for x
  y_i : {float}, last iteration of the algorithm for y
  """
  i = 0
  x_i = np.empty(0)
  error = 2*epsilon

  while error > epsilon and i < nMax:
    i += 1
    x_i = np.append(x_i,x)
    x_prev = x
    x = x - alpha * grad(x)
    error = np.linalg.norm(x - x_prev)
    x, y = X[0], X[1]
  print(X)
  return X, x_i, y_i

if __name__ == '__main__':
  x_0 = random.uniform(-2.0, 2.0)
  y_0 = random.uniform(-2.0, 2.0)
  root,iter_x,iter_y = gradient_descent(grad_rosen,x_0,y_0)

  # Code below is for plotting
  x = np.linspace(-2,2,250)
  y = np.linspace(-2,3,250)
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
  ax.contour(X,Y,Z, 50, cmap = 'jet')
  ax.scatter(iter_x,iter_y,color = 'r', marker = '*')
  ax.quiver(iter_x[:-1], iter_y[:-1], anglesx, anglesy, scale_units = 'xy', angles = 'xy', scale = 1, color = 'r', alpha = .3)

  plt.show()
