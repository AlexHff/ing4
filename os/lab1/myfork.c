#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char* argv[])
{
  int i = 5;
  fork();
  if (fork() == 0) {
    fprintf(stdout, "I am child i=%d.\n", ++i);
    if (fork() == 0) {
      fprintf(stdout, "I am child's child i=%d.\n", i);
    }
  } else {
    fprintf(stdout, "I am parent and my PID is %d i=%d.\n", getpid(), --i);
  }
  return 0;
}
