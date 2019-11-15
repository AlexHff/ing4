#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/shm.h>
#include <sys/times.h>
#include <sys/types.h>
#include <sys/resource.h>
#include <sys/wait.h>
#include <pthread.h>
#include <signal.h>

struct arg_struct {
  int arg1;
  int arg2;
};

void *sum(void *ptr);
void *dif(void *ptr);
clock_t times(struct tms *buf);

int main(int argc, char* argv[])
{
  int a = 2;
  int b = 2;
  int c = 2;
  int d = 1;
  int e = 5;
  int f = 5;
  pthread_t t1, t2;
  int rt1, rt2, res;
  int *a_b, *c_d, *e_f;
  struct arg_struct args1;
  args1.arg1 = a;
  args1.arg2 = b;
  struct arg_struct args2;
  args2.arg1 = c;
  args2.arg2 = d;
  rt1 = pthread_create(&t1, NULL, &sum, (void *) &args1);
  rt2 = pthread_create(&t2, NULL, &dif, (void *) &args2);
  pthread_join(t1, (void **) &a_b);
  pthread_join(t2, (void **) &c_d);
  *e_f = e + f;
  res = *a_b * *c_d;
  res += *e_f;
  printf("%d\n", res);
  return 0;
}

void *sum(void *ptr) {
  struct arg_struct *args = (struct arg_struct *) ptr;
  int *ret = malloc(sizeof(int));
  *ret = args->arg1 + args->arg2;
  pthread_exit(ret);
}

void *dif(void *ptr) {
  struct arg_struct *args = (struct arg_struct *) ptr;
  int *ret = malloc(sizeof(int));
  *ret = args->arg1 - args->arg2;
  pthread_exit(ret);
}

