#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char *argv[]) 
{
	int pipefd[2]; //used to create a pipe --> [0] is the fd for read end and [1] is the fd for write end
	pid_t cpid; //return type of a getpid()
	char buf;

	if (argc != 2) //there can only be 2 parameters to the program call
	{
		fprintf(stderr, "Usage: %s <string>\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	if (pipe(pipefd) == -1) //an error when creating the pipe ?
	{
		perror("pipe");
		exit(EXIT_FAILURE);
	}

	cpid = fork(); //then fork the shit outta this program
	
	if (cpid == -1) //error ?
	{
		perror("fork");
		exit(EXIT_FAILURE);
	}

	if (cpid == 0) //CHILD process
	{    /* Child reads from pipe */
		close(pipefd[1]);          /* Close unused write end */
		while (read(pipefd[0], &buf, 1) > 0) //then read from from the pipe char by char while there is something to read
			write(STDOUT_FILENO, &buf, 1); //write that in the standard output
		
		write(STDOUT_FILENO, "\n", 1); //esthetic reasons
		close(pipefd[0]); //close read end of the pipe cuz we don't need it anymore
		_exit(EXIT_SUCCESS); //exit the chile
	} 
	else 
	{            /* Parent writes argv[1] to pipe */
		close(pipefd[0]);          /* Close unused read end */
		write(pipefd[1], argv[1], strlen(argv[1])); //write the argv[1] to the pipe
		close(pipefd[1]);          /* Reader will see EOF */
		wait(NULL);                /* Wait for child */
		exit(EXIT_SUCCESS); //exit when finished
	}
}
