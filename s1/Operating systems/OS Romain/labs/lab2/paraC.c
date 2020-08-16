/* Program resolving the question 2.1 of lab 2
 * Authors are: Romain Brisse & Hyunjae Lee
 * All rights of diffusion are reserved to the Authors.
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <string.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/resource.h>

#define KEY 4567
#define PERMS 0660

int main(int argc, char **argv)
{
	int id; int *a;
	struct rusage t1,t2;

	getrusage(RUSAGE_CHILDREN,&t1);
	//creation of the shared memory space
	id = shmget(KEY,6*sizeof(int),IPC_CREAT | PERMS);
	
	//reloacating the array a in the shared memory space
	a = (int*) shmat(id,NULL,0);
	//initialize the values in the array
	a[0]=1;
	a[1]=2;
	a[2]=3;
	a[3]=4;
	a[4]=5;
	a[5]=6;

	//fork a first time to create process p2
	if(fork()==0)
	{
		//fork a second time to create process p3
		if(fork()==0)
		{
			//execute first step of calculus
			a[0]=a[0]+a[1];
			printf("a+b = %d\n",a[0]);
		}
		else
		{
			//execute second part of calculus
			a[2]=a[2]-a[3];
			printf("c-d = %d\n",a[2]);
			//wait for the first part of calculus to have ended
			wait(NULL);
			//then do the fourth part of calculus
			a[0]=a[0]*a[2];
			printf("a*c = %d\n",a[0]);
		}
	}
	//this is process p1
	else
	{
		//execute third par of calculus
		a[4]=a[4]+a[5];
		printf("e+f = %d\n",a[4]);
		//wait for the fourth part of calculus to have ended
		wait(NULL);
		//then do the fifth of calculus
		a[0]=a[0]+a[4];
		getrusage(RUSAGE_CHILDREN,&t2);
		printf("result: %d\n",a[0]); 
		printf("%ld usec\n", (t2.ru_utime.tv_sec-t1.ru_utime.tv_sec)*1000000 +(t2.ru_utime.tv_usec-t1.ru_utime.tv_usec)+(t2.ru_stime.tv_sec-t1.ru_stime.tv_sec)*1000000 +(t2.ru_stime.tv_usec-t1.ru_stime.tv_usec));
		printf ("Context switch : voluntary = %ld , involuntary = %ld\n",t2.ru_nvcsw,t2.ru_nivcsw);
	}
}
