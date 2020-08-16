#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char* argv[])
{
  int i = 5;
  if (fork() == 0) {
    wait(NULL);
    fprintf(stdout, "I am child and my PID is %d i=%d .\n", getpid(), ++i);
  } else {
    fprintf(stdout, "I am parent and my PID is %d i=%d.\n", getpid(), --i);
  }
  return 0;
}
