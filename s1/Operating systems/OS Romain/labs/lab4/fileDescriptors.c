#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char **argv)
{
	FILE *fptr;
	FILE *fptr2;
	char string[20] = "";

	//ptr = fopen("fileopen","mode");
	fptr = fopen("text1","r");
	fptr2 = fopen("text2","w");

	//redirect standard output to text2
	dup2(fileno(fptr2),STDOUT_FILENO);	
			
	if(fptr!=NULL)	
	{
		while(fgets(string,20,fptr) != NULL)
		{
			printf("%s",string);
		}
	}
	else
	{
		printf("error");
	}


	fclose(fptr);
	return 0;
}
