#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>
#include <fcntl.h>

int main(int argc, char** argv)
{
  int nread, i;
  char buf[128];
  //fcntl(STDIN_FILENO, F_SETFL, O_NONBLOCK);
  fcntl(STDIN_FILENO, F_SETFL, 0);
  for (i = 0; i < 10; i++) {
    nread = read(0, buf, 128);
    if(nread == -1)
      perror("read");
    else
      printf("ok");
    printf("nwrites = %d\terror = %d\n", nread, errno);
  }
  return 0;
}
