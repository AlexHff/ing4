#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h> 
#include <unistd.h>
#include <time.h>

int a=1, b=2, c=3, d=4, e=5, f=6;

void * fThread1(void *arg) {
  int *result = malloc(sizeof(int));
  *result = a+b;
  
  pthread_exit(result);
}

void * fThread2(void *arg) {
  int *result = malloc(sizeof(int));
  *result = c-d;

  pthread_exit(result);
}

void * fThread3(void *arg) {
  int *result = malloc(sizeof(int));
  *result = e+f;

  pthread_exit(result);
}

void * fThread4(void *arg) {
	int *result = malloc(sizeof(int));
	*result = a*b;
	pthread_exit(result);
}


int main(int argc, char **argv) {

  int result = 0;
  int * result1;
  int * result2;
  int * result3;	
  pthread_t t1;
  pthread_t t2;
  pthread_t t3;
  pthread_t t4;
    
	clock_t tt1, tt2;

	tt1=clock();

  pthread_create(&t1, NULL, fThread1, NULL); 
  pthread_create(&t2, NULL, fThread2, NULL); 
  pthread_create(&t3, NULL, fThread3, NULL); 
  
  pthread_join(t1, (void **) &result1);
  pthread_join(t2, (void **) &result2);
  pthread_join(t3, (void **) &result3);


  printf ("a+b: %d\n", *result1);
  printf ("c-d: %d\n", *result2);
  printf ("e-f: %d\n", *result3);

  a=*result1; b=*result2;

  pthread_create(&t4, NULL, fThread4, NULL);
  pthread_join(t4, (void **) &result1);

  printf("(a+b)*(c-d): %d\n",*result1);

  sleep(1);
  result = *result1 + *result3;

  printf("(a+b)*(c-d)+(e+f): %d\n",result);
	
}
