#include <stdio.h> 
#include <sys/types.h> 
#include <unistd.h>  
#include <stdlib.h> 
#include <sys/wait.h> 
#include <string.h>

void mysystem(const char *command) {
  if (command == NULL) return;
  if (fork() == 0) {
    execl("/bin/sh", "sh", "-c", command, (char *) NULL);
    exit(0);
  }
}

int main(){
  char command[50];
  strcpy(command, "./helloworld");
  mysystem(command);
  return 0;
}
