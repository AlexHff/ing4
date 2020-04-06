#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <unistd.h>

#define KEY 4567
#define PERMS 0660

int main(int argc, char **argv) {
  int id, i;
  int *ptr;
  // Show information on IPC facilities about active shared memory segments.
  system("ipcs -m");
  // Allocates a System V shared memory segment.
  id = shmget(KEY, sizeof(int), IPC_CREAT | PERMS);
  system("ipcs -m");
  // Shared memory operations
  ptr = (int*) shmat(id, NULL, 0);
  *ptr = 54;
  i = 54;
  if (fork() == 0) {
    (*ptr)++;
    i++;
    printf("Value of *ptr = %d\nValue of i = %d\n", *ptr, i);
  } else {
    wait(NULL);
    printf("Value of *ptr = %d\nValue of i = %d\n", *ptr, i);
    // System V shared memory control
    shmctl(id, IPC_RMID, NULL);
  }
}
