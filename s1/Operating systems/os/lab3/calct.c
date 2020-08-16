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
void calc_thread(int a, int b, int c, int d, int e, int f);
clock_t times(struct tms *buf);

int main(int argc, char* argv[])
{
  int a = 2;
  int b = 2;
  int c = 2;
  int d = 1;
  int e = 5;
  int f = 5;
  struct tms start, end;
  struct rusage rstart, rend;
  times(&start);
  getrusage(RUSAGE_SELF, &rstart);
  calc_thread(a, b, c, d, e, f);
  times(&end);
  getrusage(RUSAGE_SELF, &rend);
  printf("%lf usec\n", (end.tms_utime+end.tms_stime-start.tms_utime-start.tms_stime)*1000000.0/sysconf(_SC_CLK_TCK));
  printf("%ld usec\n", (rend.ru_utime.tv_sec-rstart.ru_utime.tv_sec)*1000000 +(rend.ru_utime.tv_usec-rstart.ru_utime.tv_usec)+(rend.ru_stime.tv_sec-rstart.ru_stime.tv_sec)*1000000 +(rend.ru_stime.tv_usec-rstart.ru_stime.tv_usec));
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

void calc_thread(int a, int b, int c, int d, int e, int f) {
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
}
