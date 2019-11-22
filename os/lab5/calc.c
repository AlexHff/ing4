#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

struct arg_struct {
  int arg1;
  int arg2;
};

void *sum(void *arg);

sem_t mutex;
int n = 3;
int result[n];
int spot = 0;

int main(int argc, char* argv[])
{
  int i;
  int a = 2, b = 2, c = 2, d = 1, e = 5, f = 5;
  struct arg_struct args;
  pthread_t t1, t2, t3;

  sem_init(&mutex, 0, 1);

  args.arg1 = a;
  args.arg2 = b;
  pthread_create(&t1, NULL, &sum, (void *) &args);

  args.arg1 = c;
  args.arg2 = d;
  pthread_create(&t2, NULL, &sum, (void *) &args);

  args.arg1 = e;
  args.arg2 = f;
  pthread_create(&t3, NULL, &sum, (void *) &args);

  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
  pthread_join(t3, NULL);

  for (i = 0; i < n; i++) {
    printf("%d\n", result[i]);
  }

  return 0;
}

void *sum(void *arg) {
  struct arg_struct *args = (struct arg_struct *) arg;
  sem_wait(&mutex);
  if (spot < n) {
    result[n] = args->arg1 + args->arg2;
    n++;
  }
  sem_post(&mutex);
}

