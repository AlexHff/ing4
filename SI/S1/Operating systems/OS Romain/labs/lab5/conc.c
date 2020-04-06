#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <unistd.h>

#define KEY 1234
#define PERMS 0660

int main(int argc, char **argv)
{
	int id, a;
	id = shmget(KEY,sizeof(int),IPC_CREAT | PERMS);

	a = (int) shmat(id,NULL,0);
	a = 65;

	if(fork()==0)
	{
		sleep(2);
		a--;
		printf("-: %d\n",a);
	}
	else
	{
		sleep(2);
		a++;
		printf("+: %d\n",a);
		sleep(2);
		printf("=: %d\n",a);
	}


	
	return 0;
}
