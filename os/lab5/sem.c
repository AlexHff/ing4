#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

void *inc(void *ptr);
void *dec(void *ptr);

int i = 65;
sem_t mutex;

int main(int argc, char* argv[])
{
  sem_init(&mutex, 0, 1);
  pthread_t t1, t2;
  pthread_create(&t1, NULL, &inc, NULL);
  pthread_create(&t2, NULL, &dec, NULL);
  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
  if (i != 65)
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

