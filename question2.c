#include<stdio.h>
#include<string.h>
#include<sys/types.h>
#include<stdlib.h>


int main(){

	printf("enter a value: ");

	pid_t pid;
	FILE *fp;
	int chars;
	fp = fopen ("question2.txt", "w");
	int n;
	scanf("%d", &n);
	for (int i =0; i < n; i++){
		fflush(fp);
		pid = fork();
		
		if(pid==0){
			
        		chars =fprintf(fp, "pid in child=%d pid in parent=%d\n",getpid(), getppid());
			printf("pid in child=%d pid in parent=%d\n",getpid(), getppid());
		
			
    		}
		waitpid(pid, NULL, 0);
	}
	
	fclose(fp);
}
