#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void *inc(void *ptr);
void *dec(void *ptr);
int i = 65;

int main(int argc, char* argv[])
{
  pthread_t t1, t2;
  pthread_create(&t1, NULL, &inc, NULL);
  pthread_create(&t2, NULL, &dec, NULL);
  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
  if (i != 65)
    printf("%d\n", i);
  return 0;
}

void *inc(void *arg) {
  i++;
}

void *dec(void *arg) {
  i--;
}

