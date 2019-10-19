#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

int main(int argc, char **argv)
{
  int pipefd[2];
  pid_t cpid;
  char buf;
  if (pipe(pipefd) == -1) {
    perror("pipe");
    exit(EXIT_FAILURE);
  }
  cpid = fork();
  if (cpid == -1) {
    perror("fork");
    exit(EXIT_FAILURE);
  }
  if (cpid == 0) {
    close(pipefd[1]);
    dup2(pipefd[0], 0);
    close(pipefd[1]);
    char *args[] = {"more", (char *) NULL};
    execvp(args[0], args);
    _exit(EXIT_SUCCESS);
  } else {
    close(pipefd[0]);
    dup2(pipefd[1], 1);
    close(pipefd[1]);
    char *args[] = {"ps", "aux", (char *) NULL};
    execvp(args[0], args);
    wait(NULL);
    exit(EXIT_SUCCESS);
  }
  return 0;
}
