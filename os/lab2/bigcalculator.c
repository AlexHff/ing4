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
  int shm_id;
  int *ptr;
  shm_id = shmget(IPC_PRIVATE, 11*sizeof(int), IPC_CREAT | 0666);
  ptr = (int *) shmat(shm_id, NULL, 0);
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
    }
  } else {
    wait(NULL);
    ptr[7] = ptr[2] - ptr[3];
    ptr[9] = ptr[6] * ptr[7];
    res = ptr[9] + ptr[8];
    printf("%d\n", res);
    shmctl(shm_id, IPC_RMID, NULL);
  }
  return 0;
}
