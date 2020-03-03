#!/usr/bin/python3

import numpy as np
import matplotlib.pyplot as plt
import random

class NeuralNetwork:
  def __init__(self):
    self.w1 = np.random.randn()
    self.w2 = np.random.randn()
    self.b1 = np.random.randn()
    self.b2 = np.random.randn()

  def sigmoid(self,x):
    return 1.0 / (1.0 + np.exp(-x))

  def grad_sigmoid(self, x):
    return np.exp(-x) / np.power(1.0 + np.exp(-1), 2)

if __name__ == '__main__':
  # Load data from file
  fic = open("data_ffnn_3classes.txt", "r")
  data = np.loadtxt(fic)
  fic.close()

  # Input data matrix
  X = np.array([np.ones(len(data)), data[:, 0], data[:, 1]]).transpose()
  plt.scatter(X[:,1],X[:,2])

  # Actual output
  Y = data[:,2]

  nn = NeuralNetwork()

  plt.show()

  """
  # Initialize parameters
  v = np.random.rand(3,3)

  # Multiply matrices
  X = x.dot(v)

  # Apply activation function
  f = sigmoid(X)

  # Add columns of ones
  F = np.array([np.ones(len(f)), f[:, 0], f[:, 1], f[:, 2]]).transpose()

  # Init params
  w = np.random.rand(4,3)

  # Multiply matrices
  FF = F.dot(w)

  # Apply activation function
  G = sigmoid(FF)
  """
