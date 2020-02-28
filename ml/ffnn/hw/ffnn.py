import numpy as np
import matplotlib.pyplot as plt
import random

def sigmoid(x):
  return 1.0 / (1.0 + np.exp(-x))

def labels(x):
  labels = [len(x)]
  for i in range (0, len(x)):
    labels[i] = max(x[i][0], 3.0)
  return labels

def sse(Y, y):
  return 1.0 / 2.0 * sum((Y - y)**2)

# Load data from file
fic = open("data_ffnn_3classes.txt", "r")
data = np.loadtxt(fic)
fic.close()

# Input data matrix
x = np.array([np.ones(len(data)), data[:, 0], data[:, 1]]).transpose()

# Actual output
y = data[:, 2]

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
print(G)
