#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

void *inc(void *arg);
void *dec(void *arg);

int i;
sem_t mutex;

int main(int argc, char* argv[])
{
  i = 65;
  // Why 1?
  sem_init(&mutex, 0, 1);
  pthread_t t1, t2;

  pthread_create(&t1, NULL, &inc, NULL);
  pthread_create(&t2, NULL, &dec, NULL);

  pthread_join(t1, NULL);
  pthread_join(t2, NULL);

  printf("%d\n", i);

  return 0;
}

void *inc(void *arg) {
  sem_wait(&mutex);
  i++;
  sem_post(&mutex);
}

void *dec(void *arg) {
  sem_wait(&mutex);
  i--;
  sem_post(&mutex);
}

