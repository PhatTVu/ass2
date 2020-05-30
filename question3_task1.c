#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(){

	/*Take input*/
	printf("Enter number of fork call: ");
	int n;
	scanf("%d", &n);

	/*FIle pointer*/
	FILE *fp;
	fp = fopen("output", "a");

	/*PID*/
	pid_t prc_id;
	pid_t p_prc_id;
	pid_t id;

	/*Do fork*/
	for (int i = 0; i < n; i++){
		fflush(fp);
		id = fork();

		//Child process
		if(id == 0){
			/*Print output*/
			prc_id = getpid();
			p_prc_id = getppid();
			printf("Process ID: %d\n", prc_id);
			printf("Parent Process ID: %d\n", p_prc_id);

			/*Write output to file*/
			fprintf(fp, "%s%d\n", "Process ID: ", prc_id);
			fprintf(fp, "%s%d\n", "Parent process ID: ", p_prc_id);
			exit(0);
		}else if(id > 0){
			wait(NULL);
		}
	}
	
	/*Close file to save data*/
	fclose(fp);

	/*Sleep for 1 second*/
	sleep(1);

	return 0;
}