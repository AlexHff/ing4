#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <fcntl.h>

int main() 
{
	int i;
	char buf[100];

	// ouvrir un le stdin en lecture non bloquante
	//fcntl(STDIN_FILENO, F_SETFL, O_NONBLOCK);
	
	for (i = 0; i < 10; i++) 
	{
		int nb;
		//try to read something from the standard input
		//and store it in the buffer array
		nb = read(STDIN_FILENO, buf, 100);
		//Then, print 
		//the number of characters in the string (including the \0)
		//and if there was an error or not (0 if not)
		printf("nwrites = %d\terror = %d\n", nb, errno);
	}
}
