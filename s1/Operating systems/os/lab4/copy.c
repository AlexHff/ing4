#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>              /* Obtain O_* constant definitions */
#include <sys/types.h>
#include <sys/stat.h>

int main(int argc, char *argv[])
{
  if (argc != 3) {
    fprintf(stderr, "Too few or too many arguments.\nUse ./copy src tgt\n");
    exit(0);
  }
  int src, tgt;
  char *args[] = {argv[2], NULL};
  src = open(argv[1], O_RDONLY);
  tgt = open(argv[2], O_WRONLY | O_TRUNC | O_CREAT, S_IRUSR | S_IRGRP | S_IWGRP | S_IWUSR);
  dup2(src, 0);
  dup2(tgt, 1);
  close(src);
  close(tgt);
  execvp("cat", args);
  return 0;
}
