/*
 * Program resolving the question 1.2 of lab 2
 * Author is: Mr. KHOURY
 * comments written by: Romain Brisse & Hyunjae Lee
 */

//inclusion of all necessary libraries
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/shm.h>
#include <sys/wait.h>

//definition of the access key for the future shared memory space
#define KEY 4567
//definition of access permissions for the shared memory space (in octal)
#define PERMS 0660

int main(int argc, char **argv) 
{
	//definition of necessary variables
	int id;
	int i;
	int *ptr;

	//this command is used to display the state of the segments of shared memory
	system("ipcs -m");
	/*
	 * creation of the shared memory space:
	 * 	- Key is it's identifier
	 * 	- sizeof(int) represents the size of the memory space
	 * 	- IPC_CREAT is the keyword indicating we are creating the shared memory space
	 * 	- PERMS represents the permissions defined earlier
	 */
	id = shmget(KEY, sizeof(int), IPC_CREAT | PERMS);

	system("ipcs -m");

	/*
	 * then, we relocate the pointer into the share memory space:
	 * 	- id is the name of the shared memory space in which we relocate
	 * 	- NULL  means the system will choose himself the address in the shared space
	 * 	- 0 means there are no flags attached to this declaration
	 */
	ptr = (int *) shmat(id, NULL, 0);
	//then, intialize the values of ptr and i
	*ptr = 54;
	i = 54;

	//fork, creating a child process
	if (fork() == 0) 
	{
		//in the child process: increment the values of i and ptr
		(*ptr)++;
		i++;
		printf("Value of *ptr = %d\nValue of i = %d\n", *ptr, i);
		exit(0);

	}
       	else 
	{
		//in the parent process, wait for the child :wq
		//and print tha values of i and ptr.
		wait(NULL);
		printf("Value of *ptr = %d\nValue of i = %d\n", *ptr, i);
		shmctl(id, IPC_RMID, NULL);
	}
}
