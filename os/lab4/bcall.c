#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <errno.h>
#include <fcntl.h>

int main(int argc, char *argv[])
{
  int i;
  char buf[100];
  fcntl(STDIN_FILENO, F_SETFL, O_NONBLOCK); // Manipulates the STDIN_FILENO file descriptor 
  for (i = 0; i < 10; i++) {
    int nb;
    nb = read(STDIN_FILENO, buf, sizeof(buf));
    printf("nwrites = %d\terror = %d\n", nb, errno);
  }
  return 0;
}
