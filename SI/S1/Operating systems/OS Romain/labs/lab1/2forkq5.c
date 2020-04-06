#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
	if(fork()==0)
	{
		printf("I am a first generation child!\n");
		if(fork()==0)
		{
			printf("I am a second generation child!\n");
		}
	}
	else
	{
		printf("I am a first generation parent!\n");
		if(fork()==0)
		{
			printf("I am a first generation child!\n");
		}
	
	}

return 0;
}
