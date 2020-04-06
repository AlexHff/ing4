#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/mman.h>
#include <unistd.h>
#include <semaphore.h>
#include <pthread.h>

sem_t mutex;
int a;

void *fThread1(void *arg)
{
	a--;
	printf("-: %d\n", a);
	sem_post(&mutex);
}

void *fThread2(void *arg)
{
	sem_wait(&mutex);
	a++;
	printf("+: %d\n", a);
}

int main(int argc, char **argv)
{
	sem_init(&mutex,0,0);
	a=65;

	pthread_t t1, t2;
	pthread_create(&t1, NULL, fThread1, NULL);
	pthread_create(&t2, NULL, fThread2, NULL);

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);

	printf("=: %d\n",a);
}

