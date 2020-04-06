#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

void forkThis()
{
	if(fork()==0)
		printf("this is the child!\n");
	else
		printf("this is the parent!\n");
}

int main()
{
	forkThis();
	printf("%d\n",getpid());
	printf("%d\n",getppid());
return 0;
}
