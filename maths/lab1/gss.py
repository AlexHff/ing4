#!/usr/bin/python3

import math
from scipy.optimize import minimize_scalar

invphi = (math.sqrt(5) - 1) / 2  # 1 / phi
invphi2 = (3 - math.sqrt(5)) / 2  # 1 / phi^2

def fct(x):
	return (x-2)**2

def gss(f, a, b, tol=1e-5):
	(a, b) = (min(a, b), max(a, b))
	h = b - a
	if h <= tol:
		return (a, b)

	n = int(math.ceil(math.log(tol / h) / math.log(invphi)))

	c = a + invphi2 * h
	d = a + invphi * h
	yc = f(c)
	yd = f(d)

	for k in range(n-1):
		if yc < yd:
			b = d
			d = c
			yd = yc
			h = invphi * h
			c = a + invphi2 * h
			yc = f(c)
		else:
			a = c
			c = d
			yc = yd
			h = invphi * h
			d = a + invphi * h
			yd = f(d)

	if yc < yd:
		return (a, d)
	else:
		return (c, b)

if __name__ == '__main__':
  print(gss(fct,1,5))
  print(minimize_scalar(fct,bounds=(1,5),method='golden').x)
