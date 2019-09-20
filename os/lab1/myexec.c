#include <stdio.h> 
#include <sys/types.h> 
#include <unistd.h>  
#include <stdlib.h> 
#include <errno.h>   
#include <sys/wait.h> 
#include <string.h>

int main(){
  pid_t  pid; 
  int status; 
  pid = fork(); 
  if (pid == 0){ 
    printf("child process, pid = %u\n",getpid()); 
    char command[50];
    strcpy(command, "firefox");
    execl("/bin/sh", "sh", "-c", command, (char *) NULL);
    //char * argv_list[] = {NULL}; 
    //execv("helloworld",argv_list); 
    printf("child process, pid = %u\n",getpid()); 
  }
  else{
    printf("parent process, pid = %u\n",getpid()); 
  } 
  return 0; 
} 
