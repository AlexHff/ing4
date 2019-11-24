#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define n 3

struct arg_struct {
  int arg1;
  int arg2;
};

void *sum(void *arg);
void *multiply(void *arg);

sem_t mutex;
int result[n];
int spot = 0;
int res = 1;

int main(int argc, char* argv[])
{
  int i;
  int a = 2, b = 2, c = 2, d = 1, e = 5, f = 5;
  struct arg_struct args1, args2, args3;
  pthread_t t1, t2, t3, t4;

  sem_init(&mutex, 0, 1);

  args1.arg1 = a;
  args1.arg2 = b;
  pthread_create(&t1, NULL, &sum, (void *) &args1);

  args2.arg1 = c;
  args2.arg2 = d;
  pthread_create(&t2, NULL, &sum, (void *) &args2);

  args3.arg1 = e;
  args3.arg2 = f;
  pthread_create(&t3, NULL, &sum, (void *) &args3);

  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
  pthread_join(t3, NULL);

  spot = 0;

  for (i = 0; i < n; i++) {
    pthread_create(&t4, NULL, &multiply, (void *) NULL);
    pthread_join(t4, NULL);
  }

  for (i = 0; i < n; i++) {
    printf("%d\n", result[i]);
  }

  printf("%d\n", res);

  return 0;
}

void *sum(void *arg) {
  struct arg_struct *args = (struct arg_struct *) arg;
  sem_wait(&mutex);
  if (spot < n) {
    result[spot] = args->arg1 + args->arg2;
    spot++;
  }
  sem_post(&mutex);
}

void *multiply(void *arg) {
  sem_wait(&mutex);
  if (spot < n) {
    res *= result[spot];
    spot++;
  }
  sem_post(&mutex);
}

