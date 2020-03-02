#!/usr/bin/python3

import numpy as np
import matplotlib.pyplot as plt
import random

class NeuralNetwork():
  def __init__(self):
    np.random.seed(1)
    self.weights = {}
    self.num_layers = 1

  def add_layer(self, shape):
    self.weights[self.num_layers] = np.vstack((2 * np.random.random(shape) - 1, 2 * np.random.random((1, shape[1])) - 1))
    self.num_layers += 1

  def sigmoid(self, x):
    return 1.0 / (1.0 + np.exp(-x))

  def predict(self, data):
    for layer in range(1, self.num_layers+1):
      data = np.dot(data, self.weights[layer-1][:, :-1]) + self.weights[layer-1][:, -1]
      data = self.sigmoid(data)
    return data

  def forward_propagate(self, data):
    for layer in range(2, self.num_layers+1):
      data = np.dot(data.T, self.weights[layer-1][:-1, :]) + self.weights[layer-1][-1, :].T
      data = self.sigmoid(data).T
    return data

if __name__ == '__main__':
  # Load data from file
  fic = open("data_ffnn_3classes.txt", "r")
  data = np.loadtxt(fic)
  fic.close()

  # Input data matrix
  X = np.array([np.ones(len(data)), data[:, 0], data[:, 1]]).transpose()
  plt.scatter(X[:,1],X[:,2])

  # Actual output
  Y = np.array(data[:,2])

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
