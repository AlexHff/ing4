# -*- coding: utf-8 -*-
"""
Created on Fri Oct  4 11:44:38 2019

@author: thoma
"""

import numpy as np 
import matplotlib.pyplot as plt 
from numpy.linalg import inv 

fichier= open("data_ffnn_3classes.txt")
data = np.loadtxt(fichier)
"""chargement du fichier"""
X1 = data [:, 0]
X2 = data [:, 1]
Y = data [:, 2]

X = np.array([X1,X2])
X = np.transpose(X)


fichier.close()






"""_____________FONCTIONS !!!!!!_____________"""



def layer1(F, V):
  for j in range(0,5):
    for i in range(0,2):        
      F = np.array([1]*51, F)
      F = F*V
      F = inv(1 + np.exp(F))

def graph():    
    plt.scatter(X1,X2)  
    print("__________courbe train___________")
    plt.show()
    
def erreur(Ye):
    x =100
    E = 0
    for i in range (x):
      E=(1/2)*sum((Ye - Y)**2)
    print("erreur de train :")
    print(E/x)
  
graph()
