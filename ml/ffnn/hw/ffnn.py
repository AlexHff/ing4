#!/usr/bin/python3

import numpy as np
import matplotlib.pyplot as plt
import random

class NeuralNetwork:
  def __init__(self):
    """
      Learning rate (alpha), weights and biases for the neural network.
    """
    self.w1 = np.random.rand(2, 5)
    self.b1 = np.random.rand(1, 5)
    self.w2 = np.random.rand(5, 3)
    self.b2 = np.random.rand(1, 3)
    self.alpha = 0.1

  def evaluate(self, x):
    h = sigmoid(np.dot(x, self.w1) + self.b1)
    return sigmoid(np.dot(h, self.w2) + self.b2)

  def train(self, training_input, training_output, alpha, iterations, plt):
    for i in range(iterations):
      err = 0
      for x, y in zip(training_input, training_output):
        h1 = sigmoid(np.dot(x, self.w1) + self.b1)
        y_train = sigmoid(np.dot(h1, self.w2) + self.b2)
        j = 1 / 2 * np.sum((y - y_train)**2)
        delta_b2 = (y_train - y) * grad_sigmoid(np.dot(h1, self.w2) + self.b2)
        delta_w2 = np.dot(h1.T, delta_b2)
        delta_b1 = np.dot(delta_b2, self.w2.T * grad_sigmoid(np.dot(x, self.w1) + self.b1))
        delta_w1 = np.dot(x.T, delta_b1)
        self.w1 = self.w1 - delta_w1 * alpha
        self.b1 = self.b1 - delta_b1 * alpha
        self.b2 = self.b2 - delta_b2 * alpha
        self.w2 = self.w2 - delta_w2 * alpha
        err = err + j
        err = err / len(training_input)
      plt.scatter(i, err)

  def test(self, x1, x2):
    arr = np.array([x1, x2])
    return self.evaluate(arr)

def sigmoid(x):
	return 1.0 / (1.0 + np.exp(-x))

def grad_sigmoid(x):
	return sigmoid(x) * (1.0 - sigmoid(x))

def convert_to_int(x):
  if x[0][0] > 0.66:
    return 0
  if x[0][1] > 0.66:
    return 1
  return 2

def to_vect(y):
	a = np.zeros(3)
	a[y] = 1
	return a

if __name__ == '__main__':
  # Load data from file
  fic = open("data_ffnn_3classes.txt", "r")
  data = np.loadtxt(fic)
  fic.close()

  # Input data matrix
  training_input = [np.reshape(x, (1, 2)) for x in data[:,:2].astype(float)]

  # Output matrix
  training_output = np.array([to_vect(y) for y in np.array(data[:,2]).T.astype(int)])

  # Create and train neural network
  net = NeuralNetwork()
  net.train(training_input, training_output, 0.1, 1000, plt)

  # Verify neural network for input data
  sucess = 0
  for i in range(0, len(training_input)):
    x = net.evaluate(training_input[i])
    print(x, " == ", str(convert_to_int([training_output[i]])))
    if convert_to_int(x) == convert_to_int([training_output[i]]):
      sucess+=1
  print(sucess // len(training_input))

  # Test network with new data
  tmp = net.test(2, 2)
  print(tmp, " = ", str(convert_to_int(tmp)))
  tmp = net.test(4, 4)
  print(tmp, " = ", str(convert_to_int(tmp)))
  tmp = net.test(4.5, 1.5)
  print(tmp, " = ", str(convert_to_int(tmp)))

  plt.show()
