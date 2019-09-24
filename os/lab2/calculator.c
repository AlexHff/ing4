#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/ipc.h>
#include <sys/shm.h>

int main(int argc, char* argv[])
{
  int res;
  int shm_id;
  int *ptr;
  shm_id = shmget(IPC_PRIVATE, 6*sizeof(int), IPC_CREAT | 0666);
  ptr = (int *) shmat(shm_id, NULL, 0);
  ptr[0] = 2;
  ptr[1] = 2;
  ptr[2] = 1;
  ptr[3] = 1;
  if (fork() == 0) {
    ptr[4] = ptr[0] + ptr[1];
  } else {
    wait(NULL);
    ptr[5] = ptr[2] + ptr[3];
    res = ptr[4] - ptr[5];
    printf("%d\n", res);
    shmctl(ShmID, IPC_RMID, NULL);
  }
  return 0;
}
