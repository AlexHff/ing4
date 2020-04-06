#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

void *runThread1(void *arg);
void *runThread2(void *arg);
void *runThread3(void *arg);

sem_t mutex1, mutex2, mutex3;

int main(int argc, char* argv[])
{
  sem_init(&mutex1, 0, 1);
  sem_init(&mutex2, 0, 1);
  sem_init(&mutex3, 0, 1);
  pthread_t t1, t2, t3;

  pthread_create(&t1, NULL, &runThread1, NULL);
  pthread_create(&t2, NULL, &runThread2, NULL);
  pthread_create(&t3, NULL, &runThread3, NULL);

  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
  pthread_join(t3, NULL);

  return 0;
}

void *runThread1(void *arg) {
  sem_wait(&mutex1);
  printf("T1 lock 1\n");
  sleep(5);
  sem_wait(&mutex2);
  printf("T1 lock 2\n");
  sleep(5);
  sem_post(&mutex2);
  sem_post(&mutex1);
}

void *runThread2(void *arg) {
  sem_wait(&mutex2);
  printf("T2 lock 2\n");
  sleep(5);
  sem_wait(&mutex3);
  printf("T2 lock 3\n");
  sleep(5);
  sem_post(&mutex3);
  sem_post(&mutex2);
}

void *runThread3(void *arg) {
  sem_wait(&mutex3);
  printf("T3 lock 3\n");
  sleep(5);
  sem_wait(&mutex1);
  printf("T3 lock 1\n");
  sleep(5);
  sem_post(&mutex1);
  sem_post(&mutex3);
}

