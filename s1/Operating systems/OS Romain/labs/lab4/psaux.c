#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main(int argc, char **argv)
{
	//system("ps aux | more");
	

	int pipefd[2];
	char buf;
	char command[13];

	if(argc != 1){fprintf(stderr,"not the right number of arguments");}

	//creation of the pipe + checking for an error
	if(pipe(pipefd)==-1){perror("pipe"); exit(EXIT_FAILURE);}

	if(fork()==0) //Child process
	{
		//We will run the more operation from here
		
		//First, close the write end of the pipe
		close(pipefd[1]);
		//then make the standard input to be the rend end of the pipe
		dup2(pipefd[0],STDIN_FILENO);
		//finally execute the more function
		system("more");
		//close the read end of the pipe
		close(pipefd[0]);

		exit(EXIT_SUCCESS);
	}		
	else //Parent process
	{
		//We will run the ps aux operation from here
		
		//first, we close the pipe read end
		close(pipefd[0]);
		//change standard output
		dup2(pipefd[1],STDOUT_FILENO);
		//then, execute the ps aux function
		system("ps aux");
		//close the write end of the pipe
		close(pipefd[1]);
		//wait for the child to finish his operation
		wait(NULL);
		
		exit(EXIT_SUCCESS);
	}


}
