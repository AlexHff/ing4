#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>
#include <sys/shm.h>
#include <sys/times.h>
#include <sys/types.h>
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
void calc_process(int a, int b, int c, int d, int e, int f);

int main(int argc, char* argv[])
{
  //(a+b)*(c-d)+(e+f)
  int a = 2;
  int b = 2;
  int c = 2;
  int d = 1;
  int e = 5;
  int f = 5;
  clock_t start_thread, end_thread, start_process, end_process;
  double cpu_time_thread, cpu_time_process;
  start_thread = clock();
  calc_thread(a, b, c, d, e, f);
  end_thread = clock();
  cpu_time_thread = ((double) (end_thread - start_thread));
  start_process = clock();
  calc_process(a, b, c, d, e, f);
  end_process = clock();
  cpu_time_process = ((double) (end_process - start_process));
  printf("%lf, %lf\n", cpu_time_thread, cpu_time_process);
  return 0;
}

void *sum(void *ptr) {
  struct arg_struct *args = (struct arg_struct *) ptr;
  int ret = args->arg1 + args->arg2;
  return (void *) ret;
}

void *dif(void *ptr) {
  struct arg_struct *args = (struct arg_struct *) ptr;
  int ret = args->arg1 - args->arg2;
  return (void *) ret;
}

void calc_thread(int a, int b, int c, int d, int e, int f) {
  pthread_t t1, t2, t3;
  int rt1, rt2, rt3, res;
  void *a_b, *c_d, *e_f;
  struct arg_struct args1;
  args1.arg1 = a;
  args1.arg2 = b;
  struct arg_struct args2;
  args2.arg1 = c;
  args2.arg2 = d;
  struct arg_struct args3;
  args3.arg1 = e;
  args3.arg2 = f;
  rt1 = pthread_create(&t1, NULL, &sum, (void *) &args1);
  rt2 = pthread_create(&t2, NULL, &dif, (void *) &args2);
  rt3 = pthread_create(&t3, NULL, &sum, (void *) &args3);
  pthread_join(t1, &a_b);
  pthread_join(t2, &c_d);
  pthread_join(t3, &e_f);
  res = (int) a_b * (int) c_d + (int) e_f;
  printf("%d\n", res);
}

void calc_process(int a, int b, int c, int d, int e, int f) {
  int res;
  int shm_id;
  int *ptr;
  shm_id = shmget(IPC_PRIVATE, 10*sizeof(int), IPC_CREAT | 0666);
  ptr = (int *) shmat(shm_id, NULL, 0);
  ptr[0] = a;
  ptr[1] = b;
  ptr[2] = c;
  ptr[3] = d;
  ptr[4] = e;
  ptr[5] = f;
  if (fork() == 0) {
    ptr[6] = ptr[0] + ptr[1];
    //kill(getpid(), SIGTERM);
    if (fork() == 0) {
      ptr[8] = ptr[4] + ptr[5];
    //kill(getpid(), SIGTERM);
    }
  } else {
    ptr[7] = ptr[2] - ptr[3];
    wait(NULL);
    ptr[9] = ptr[6] * ptr[7];
    res = ptr[9] + ptr[8];
    printf("%d\n", res);
    shmctl(shm_id, IPC_RMID, NULL);
  }
}
