#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char* argv[])
{
    if (argc != 2) {
        int i = 5;
        int pid = fork();
        if (fork() == 0) {
            fprintf(stdout, "I am child and my PID is %d\n", pid);
            i++;
        } else {
            fprintf(stdout, "I am parent and my PID is %d\n", pid);
        }
    } else {
        int const n = atoi(argv[1]);
        for (int i = 0; i < n; i++) {
            int pid = fork();
            if (pid == 0) {
                fprintf(stdout, "I am child %d and my PID is %d\n", i + 1, pid);
            }
            else {
                fprintf(stdout, "I am parent %d and my PID is %d\n", i + 1, pid);
            }
        }
    }
    return 0;
}
