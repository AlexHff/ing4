#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <unistd.h>
#include <semaphore.h>
#include <pthread.h>

sem_t mut1, mut2, mut3;

void * fThread1(void *arg)
{
	sem_wait(&mut1);
	printf("lock 1 acquired\n");
	sleep(2);
	sem_wait(&mut2);
	printf("lock 2 acquired\n");
	sleep(2);
	sem_post(&mut2);
	printf("lock 2 released\n");
	sem_post(&mut1);
	printf("lock 1 released\n");
}

void * fThread2(void *arg)
{
	sem_wait(&mut2);
	printf("lock 2 acquired\n");
	sleep(2);
	sem_wait(&mut3);
	printf("lock 3 acquired\n");
	sleep(2);
	sem_post(&mut3);
	printf("lock 3 released\n");
	sem_post(&mut2);
	printf("lock 2 released\n");
}

void * fThread3(void *arg)
{
	sem_wait(&mut3);
	printf("lock 3 acquired\n");
	sleep(2);
	sem_wait(&mut1);
	printf("lock 1 acquired\n");
	sleep(2);
	sem_post(&mut1);
	printf("lock 1 released\n");
	sem_post(&mut3);
	printf("lock 3 released\n");
}

int main(int argc, char **argv)
{
	sem_init(&mut1,0,1);
	sem_init(&mut2,0,1);
	sem_init(&mut3,0,1);
	pthread_t t1, t2, t3;
        pthread_create(&t1, NULL, fThread1, NULL);
        pthread_create(&t2, NULL, fThread2, NULL);
        pthread_create(&t3, NULL, fThread3, NULL);
	pthread_join(t1,NULL);
	pthread_join(t2,NULL);
	pthread_join(t3,NULL);

	sem_destroy(&mut1);
	sem_destroy(&mut2);
	sem_destroy(&mut3);
}
