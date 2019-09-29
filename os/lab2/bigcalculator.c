#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>

int main(int argc, char* argv[])
{
  int a = 2;
  int b = 2;
  int c = 2;
  int d = 1;
  int e = 5;
  int f = 5;
  int res;
  int shm_id, shm_id_flag;
  int *ptr, *flag;
  shm_id = shmget(IPC_PRIVATE, 4*sizeof(int), IPC_CREAT | 0666);
  if (shm_id < 0) {
    printf("shmget array error\n");
    exit(1);
  }
  shm_id_flag = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0666);
  if (shm_id_flag < 0) {
    printf("shmget flag error\n");
    exit(1);
  }
  ptr = (int *) shmat(shm_id, NULL, 0);
  flag = (int *) shmat(shm_id_flag, NULL, 0);
  if (fork() == 0) {
    ptr[0] = a + b;
    if (fork() == 0) {
      ptr[2] = e + f;
      *flag = 1;
    }
  } else {
    ptr[1] = c - d;
    while (*flag != 1);
    //wait(NULL);
    ptr[3] = ptr[0] * ptr[1];
    res = ptr[3] + ptr[2];
    printf("%d\n", res);
    shmctl(shm_id, IPC_RMID, NULL);
  }
  return 0;
}
