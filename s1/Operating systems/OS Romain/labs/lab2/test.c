#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/wait.h>

#define KEY 4567
#define PERMS 0660

int main(int argc, char **argv)
{
	int id; int i; int *ptr;

	id =shmget(KEY,sizeof(int),IPC_CREAT | PERMS);
	ptr = (int*) shmat(id,NULL,0);
	int *ptr2; ptr2 = (int*) shmat(id,NULL,0); *ptr2= 100;
	*ptr=54;
	i=54;

	if(fork()==0)
	{	
		(*ptr2)++;
		(*ptr)++;
		i++;
		printf("values : %d %d %d\n",(*ptr),i,(*ptr2));		
	}
	else
	{
		wait(NULL);

		printf("values : %d %d %d\n",(*ptr),i,(*ptr2));
		shmctl(id,IPC_RMID,NULL);
	}

}
