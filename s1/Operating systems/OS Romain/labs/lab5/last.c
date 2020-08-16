#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/mman.h>
#include <unistd.h>
#include <semaphore.h>
#include <pthread.h>

sem_t mutex1, mutex2, muti;
int a=1, b=2, c=3, d=4, e=5, f=6, i=-1;
int results[3];

void *fThread1(void *arg)
{
	sem_wait(&muti);
	i++;
	results[i]=a+b;
	printf("result : %d, i: %d\n",results[i],i);
	sem_post(&muti);
	if(i==1)
	{
		sem_post(&mutex1);
		printf("free 1st mutex\n");
	}
	else if(i==2)
	{
		sem_post(&mutex2);
		printf("free 2nd mutex\n");
	}
}

void *fThread2(void *arg)
{
	sem_wait(&muti);
	i++;
	results[i]=c-d;
	printf("result : %d, i: %d\n",results[i],i);
	if(i==1)
	{
		sem_post(&mutex1);
		printf("free 1st mutex\n");
	}
	else if(i==2)
	{
		sem_post(&mutex2);
		printf("free 2nd mutex\n");
	}
	sem_post(&muti);
}

void *fThread3(void *arg)
{
	sem_wait(&muti);
	i++;
	results[i]=e+f;
	printf("result : %d, i: %d\n",results[i],i);
	if(i==1)
	{
		sem_post(&mutex1);
		printf("free 1st mutex\n");
	}
	else if(i==2)
	{
		sem_post(&mutex2);
		printf("free 2nd mutex\n");
	}
	sem_post(&muti);
}

void *fThread4(void *arg)
{
	sem_wait(&mutex1);
	printf("get first mutex\n");

	results[0]=results[0]*results[1];

	sem_wait(&mutex2);
	printf("get second mutex\n");

	results[0]=results[0]*results[2];
	printf("result: %d\n",results[0]);
}

int main(int argc, char **argv)
{
	sem_init(&mutex1,0,0);
	sem_init(&mutex2,0,0);
	sem_init(&muti,0,1);
	pthread_t t1, t2, t3, t4;

	pthread_create(&t1, NULL, fThread1, NULL);
	pthread_create(&t2, NULL, fThread2, NULL);
	pthread_create(&t3, NULL, fThread3, NULL);
	pthread_create(&t4, NULL, fThread4, NULL);
	printf("threads created\n");

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	pthread_join(t3, NULL);
	pthread_join(t4, NULL);
	printf("threads joined\n");
}
