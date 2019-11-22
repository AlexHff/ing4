#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

void *runThread1(void *arg);
void *runThread2(void *arg);
void *runThread3(void *arg);

sem_t mutex;

int main(int argc, char* argv[])
{
  sem_init(&mutex, 0, 1);
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
  sem_wait(&mutex);
  system("firefox");
  sem_post(&mutex);
}

void *runThread2(void *arg) {
  sem_wait(&mutex);
  system("htop");
  sem_post(&mutex);
}

void *runThread3(void *arg) {
  sem_wait(&mutex);
  system("vi");
  sem_post(&mutex);
}

