#!/usr/bin/python3

import numpy as np
import matplotlib.pyplot as plt

data = np.random.randint(0, 2, (40, 8))
print(data)

out = [sum(row) for row in data]
print(out)

training = out[:30]
test = out[30:40]
print(training, test)

f = open("data_kmeans.txt", "r")
data_kmeans = np.loadtxt(f)
f.close()
plt.scatter(data_kmeans[:,0], data_kmeans[:,1])
plt.show()

f = open("data_pca.txt", "r")
data_pca = np.loadtxt(f)
f.close()
plt.scatter(data_pca[:,0], data_pca[:,1])
plt.show()
