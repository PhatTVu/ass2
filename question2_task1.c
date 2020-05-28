/*input
2
*/

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[])
{
    /*Get input*/
    int n;
    int i;
    pid_t prc_id;
    pid_t p_prc_id;
    scanf("%d", &n);

    /*File pointer*/
    FILE * fp;

    /*Do fork()*/
    for(i = 0; i < n; i++){  
        fork();
    }

    /*Output*/
    prc_id = getpid();
    p_prc_id = getppid();
    printf("Process id: %d\n", prc_id);
    printf("Parent process id: %d\n", p_prc_id);

    /*Write output to file*/
    fp = fopen ("output", "a");
    fprintf (fp, "%s%d\n", "Process id: ",prc_id);
    fprintf (fp, "%s%d\n", "Parent process id: ",p_prc_id);

    /*Close file to save data*/
    fclose(fp);

    /*Sleep for 1 second*/
    sleep(1);

    return 0;
}
