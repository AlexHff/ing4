#!/usr/bin/python3

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from random import *

# show plots in the notebook

def sigmoid(x):
  return 1/(1 + np.exp(-x))

def sigmoid_prime(x):
  return np.exp(-x) / np.power(1 + np.exp(-x), 2)

def predict(X):
  H1 = sigmoid(np.dot(X, W1) + B1)
  return sigmoid(np.dot(H1, W2) + B2)


def to_vect(y):
  a = np.zeros(3)
  a[y] = 1
  return a

fic = open("data_ffnn_3classes.txt","r")
a = np.loadtxt(fic)
y_train = a[:,2].T #on transpose la matrice y
y_train = y_train.astype(int)
y_train = np.array([to_vect(y) for y in y_train])
x_train = a[:,:2]
x_train = x_train.astype(float)
x_train = [np.reshape(x, (1, 2)) for x in x_train]
print(x_train)

W1 = np.random.rand(2, 5)
B1 = np.random.rand(1, 5)
W2 = np.random.rand(5, 3)
B2 = np.random.rand(1, 3)
lr = 0.1

for i in range(1000):
  error = 0
  for X, Y_true in zip(x_train, y_train):
    H1 = sigmoid(np.dot(X, W1) + B1)
    Y = sigmoid(np.dot(H1, W2) + B2)
    J = 0.5 * np.sum(np.power(Y_true - Y, 2))
    dB2 = (Y - Y_true) * sigmoid_prime(np.dot(H1, W2) + B2)
    dW2 = np.dot(H1.T, dB2)
    dB1 = np.dot(dB2, W2.T * sigmoid_prime(np.dot(X, W1) + B1))
    dW1 = np.dot(X.T, dB1)

    B2 -= lr * dB2
    W2 -= lr * dW2
    B1 -= lr * dB1
    W1 -= lr * dW1

    error += J
  error /= len(x_train)

x1 = 2
x2 = 2
test = np.array([x1,x2])
print(predict(test))

x1 = 4
x2 = 4
test = np.array([x1,x2])
print(predict(test))

x1 = 4.5
x2 = 1.5
test = np.array([x1,x2])
print(predict(test))

