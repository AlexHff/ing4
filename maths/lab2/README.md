# DM 2

## 1.
N = data

h = 1/(N+1)

A = 1/h^2 * [[2,-1,0,...,0]
            [-1,2,-1,0,...,0]
            [0,-1,2,-1,0,...,0]
            ...
            [0,...,0,-1,2]]

b = [-8,...,-8]

i = 1,...,N

t = (t\_i)

t\_i = i * h

g = (g\_i)

g\_i = -1 + max(0,-10(t\_i-0.4)^2+0.625)

J(X = 1/2 * \<AX,X> - \<b,X>

C1 = {X = (x\_i) in RN : x\_i >= g\_i}

Data: A, b, g, t, eps, X\_0

X = X\_0

while ||X\_k+1 - X\_k|| >= eps

X\_k+1 = X\_k - f grad(J(X\_k))

X\_k+1 = X\_k - f(AX\_k-b)

X\_k+1 = max(X\_k+1,g)

### Uzawa

Data: k = 0, X\_0 in RN, lambda\_0 in RN

while test >= eps
