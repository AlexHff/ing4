#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <unistd.h>
#include <semaphore.h>
#include <pthread.h>


sem_t mutex1, mutex2;

void *fThread1(void *arg)
{
	system("opera");
	sem_post(&mutex1);
}
void *fThread2(void *arg)
{
	sem_wait(&mutex1);
	system("vi");
	sem_post(&mutex2);
}
void *fThread3(void *arg)
{
	sem_wait(&mutex2);
	system("gnumeric");
}

int main(int argc, char **argv)
{
	pthread_t t1, t2, t3;

	sem_init(&mutex1,0,0);
	sem_init(&mutex2,0,0);

	pthread_create(&t1, NULL, fThread1, NULL);
	pthread_create(&t2, NULL, fThread2, NULL);
	pthread_create(&t3, NULL, fThread3, NULL);

	pthread_join(t1, NULL);
	pthread_join(t2, NULL);
	pthread_join(t3, NULL);


	sem_destroy(&mutex1);
	sem_destroy(&mutex2);
}
