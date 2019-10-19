#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char **argv)
{
  if (argc != 2) {
    fprintf(stderr, "Usage: %s <string>\n", argv[0]);
    exit(EXIT_FAILURE);
  }
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
    while (read(pipefd[0], &buf, 1) > 0)
      write(STDOUT_FILENO, &buf, 1);
    write(STDOUT_FILENO, "\n", 1);
    close(pipefd[0]);
    char *args[] = {"more", (char *) NULL};
    execvp(args[0], args);
    //execlp("more", "more", NULL);
    _exit(EXIT_SUCCESS);
  } else {
    close(pipefd[0]);
    dup2(pipefd[1], 1);
    write(pipefd[1], argv[1], strlen(argv[1]));
    close(pipefd[1]);
    //execlp(argv[1], argv[1], NULL);
    wait(NULL);
    char *args[] = {argv[1], (char *) NULL};
    execvp(args[0], args);
    exit(EXIT_SUCCESS);
  }
}
