#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/times.h>
#include <sys/time.h>
#include <sys/resource.h>

int a=1, b=2, c=3, d=4, e=5, f=6;


void * fThread2(void *arg)
{
        int *result = malloc(sizeof(int));
        *result = c-d;
        printf("c-d: %d\n",*result);
        pthread_exit(result);
}

void * fThread1(void *arg)
{
        int *result = malloc(sizeof(int));
        int * resultt2;

        pthread_t t2;
        pthread_create(&t2, NULL, fThread2, NULL);
        *result = a+b;
        printf("a+b: %d\n",*result);

        pthread_join(t2, (void **) &resultt2);

        *result = (*result) * (*resultt2);
        printf("(a+b)*(c-d): %d\n",*result);

        pthread_exit(result);
}

int main(int argc, char **argv)
{
        int result1;
        int * result2;
        pthread_t t1;
	struct rusage rstart, rend;

	getrusage(RUSAGE_SELF,&rstart);
        pthread_create(&t1, NULL, fThread1, NULL);

        result1 = e+f;
        printf("e+f: %d\n",result1);

        pthread_join(t1, (void **) &result2);

        result1 = (*result2) + result1;
	getrusage(RUSAGE_SELF,&rend);
        printf("(a+b)*(c-d)+(e+f): %d\n",result1);
	
	printf("%ld usec\n", (rend.ru_utime.tv_sec-rstart.ru_utime.tv_sec)*1000000 +(rend.ru_utime.tv_usec-rstart.ru_utime.tv_usec)+(rend.ru_stime.tv_sec-rstart.ru_stime.tv_sec)*1000000 +(rend.ru_stime.tv_usec-rstart.ru_stime.tv_usec));
	
	printf ("Context switch : voluntary = %ld , involuntary = %ld\n",rend.ru_nvcsw,rend.ru_nivcsw);
}
