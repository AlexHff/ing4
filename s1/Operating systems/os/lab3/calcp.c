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

void calc_process(int a, int b, int c, int d, int e, int f);
clock_t times(struct tms *buf);

int main(int argc, char* argv[])
{
  int a = 2;
  int b = 2;
  int c = 2;
  int d = 1;
  int e = 5;
  int f = 5;
  calc_process(a, b, c, d, e, f);
  return 0;
}

void calc_process(int a, int b, int c, int d, int e, int f) {
  struct tms start, end;
  struct rusage rstart, rend;
  times(&start);
  getrusage(RUSAGE_SELF, &rstart);
  int res;
  int shm_id, shm_id_flag;
  int *ptr, *flag;
  shm_id = shmget(IPC_PRIVATE, 10*sizeof(int), IPC_CREAT | 0666);
  shm_id_flag = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0666);
  ptr = (int *) shmat(shm_id, NULL, 0);
  flag = (int *) shmat(shm_id, NULL, 0);
  ptr[0] = a;
  ptr[1] = b;
  ptr[2] = c;
  ptr[3] = d;
  ptr[4] = e;
  ptr[5] = f;
  if (fork() == 0) {
    ptr[6] = ptr[0] + ptr[1];
    if (fork() == 0) {
      ptr[8] = ptr[4] + ptr[5];
      *flag = 1;
    }
  } else {
    ptr[7] = ptr[2] - ptr[3];
    while (*flag != 1);
    ptr[9] = ptr[6] * ptr[7];
    res = ptr[9] + ptr[8];
    printf("%d\n", res);
    shmctl(shm_id, IPC_RMID, NULL);
    shmctl(shm_id_flag, IPC_RMID, NULL);
    times(&end);
    getrusage(RUSAGE_SELF, &rend);
    printf("%lf usec\n", (end.tms_utime+end.tms_stime-start.tms_utime-start.tms_stime)*1000000.0/sysconf(_SC_CLK_TCK));
    printf("%ld usec\n", (rend.ru_utime.tv_sec-rstart.ru_utime.tv_sec)*1000000 +(rend.ru_utime.tv_usec-rstart.ru_utime.tv_usec)+(rend.ru_stime.tv_sec-rstart.ru_stime.tv_sec)*1000000 +(rend.ru_stime.tv_usec-rstart.ru_stime.tv_usec));
  }
}
