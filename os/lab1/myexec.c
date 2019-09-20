#include <stdio.h> 
#include <sys/types.h> 
#include <unistd.h>  
#include <stdlib.h> 
#include <errno.h>   
#include <sys/wait.h> 

int main(){
  pid_t  pid; 
  int status; 
  pid = fork(); 
  if (pid == 0){ 
    printf("child process, pid = %u\n",getpid()); 
    char * argv_list[] = {NULL}; 
    execv("helloworld",argv_list); 
  }
  else{
    printf("parent process, pid = %u\n",getpid()); 
  } 
  return 0; 
} 
