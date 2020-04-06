/* Program resolving the question 2.2 of lab 2
 * Authors are: Romain Brisse & Hyunjae Lee
 * All rights of diffusion are reserved to the authors.
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <string.h>
#include <unistd.h>

#define KEY 4567
#define PERMS 0660
#define KEYBIS 1234

int main(int argc, char **argv)
{
	int id;
	int *a;

	//creation of the shared memory space regarding the calculus
	id = shmget(KEY,6*sizeof(int),IPC_CREAT | PERMS);

	//relocating the a array in the shared memory space
	a = (int*) shmat(id,NULL,0);
	//initialize the values in my array
	a[0]=1;	
	a[1]=2;	
	a[2]=3;	
	a[3]=4;	
	a[4]=5;	
	a[5]=6;

	int flag;
	int *b;

	//creation of the shared memory sapce regarding the flags
	flag = shmget(KEYBIS,2*sizeof(int),IPC_CREAT | PERMS);
	//reloacting the b array in the shared memory space
	b = (int*) shmat(flag,NULL,0);

	//initialize the values in the array
	b[0]=0;
	b[1]=0;

	//fork a first time to create process p2
	if(fork()==0)
	{
		//fork a second time to create process p3
		if(fork()==0)
		{	
			//execute the first step of calculus
			a[0]=a[0]+a[1];
			//change the value of the first boolean to true
			b[0]=1;
		}
		else
		{
			//execute the second part of the calculus
			a[2]=a[2]-a[3];
			//wait for the first boolean to change its value indicating that the first step of the calculus has ended
			while(!b[0]){}
			//then execute the fourth part of the calculus
			a[0]=a[0]*a[2];
			//and change the value of the second boolean
			b[1]=1;
		}
	}	
	//this is process p1
	else
	{
		//execute third part of process
		a[4]=a[4]+a[5];
		//wait for the second boolean to change its value indicating that the fourth part of the calculus has ended
		while(!b[1]){}
		//then execute the fifth part of the calculus
		a[0]=a[0]+a[4];
		//print the result
		printf("result: %d\n",a[0]);
	}
}
