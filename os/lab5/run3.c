#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

void *runThread1(void *arg);
void *runThread2(void *arg);
void *runThread3(void *arg);

sem_t mutex1, mutex2;

int main(int argc, char* argv[])
{
  sem_init(&mutex1, 0, 1);
  sem_init(&mutex2, 0, 1);
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
  system("firefox");
  sem_post(&mutex1);
}

void *runThread2(void *arg) {
  sem_wait(&mutex1);
  system("htop");
  sem_post(&mutex1);
}

void *runThread3(void *arg) {
  sem_wait(&mutex1);
  sem_wait(&mutex2);
  system("vi");
  sem_post(&mutex1);
  sem_post(&mutex2);
}

