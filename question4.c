#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>


/*HashMap Builder*/
struct node{
    int key;
    int val;
    struct node *next;
};
struct table{
    int size;
    struct node **list;
};
struct table *createTable(int size){
    struct table *t = (struct table*)malloc(sizeof(struct table));
    t->size = size;
    t->list = (struct node**)malloc(sizeof(struct node*)*size);
    int i;
    for(i=0;i<size;i++)
        t->list[i] = NULL;
    return t;
}
int hashCode(struct table *t,int key){
    if(key<0)
        return -(key%t->size);
    return key%t->size;
}
void insert(struct table *t,int key,int val){
    int pos = hashCode(t,key);
    struct node *list = t->list[pos];
    struct node *newNode = (struct node*)malloc(sizeof(struct node));
    struct node *temp = list;
    while(temp){
        if(temp->key==key){
            temp->val = val;
            return;
        }
        temp = temp->next;
    }
    newNode->key = key;
    newNode->val = val;
    newNode->next = list;
    t->list[pos] = newNode;
}
int lookup(struct table *t,int key){
    int pos = hashCode(t,key);
    struct node *list = t->list[pos];
    struct node *temp = list;
    while(temp){
        if(temp->key==key){
            return temp->val;
        }
        temp = temp->next;
    }
    return -1;
}


/*MAIN*/
int main(){

	/*Take input*/
	printf("Enter number of fork call: ");
	int n;
	scanf("%d", &n);

	/*Create table to store level of each process*/
	int buf = 2;
	int tableSize;
	for(int i = 0; i < n; i++){
		tableSize = buf << i;
	}
	struct table *t = createTable(tableSize);

	/*FIle pointer*/
	FILE *fp;
	fp = fopen("output", "a");

	/*PID*/
	pid_t prc_id;
	pid_t p_prc_id;
	pid_t id;

	/*Record the first PID with level 0*/
	prc_id = getpid();
	printf("Process ID: %d , Level: %d\n", prc_id, 0);
	insert(t, prc_id, 0);

	/*Do fork*/
	for (int i = 0; i < n; i++){
		fflush(fp);
		id = fork();

		//Child process
		if(id == 0){
			
			//Get process and its parent PID
			prc_id = getpid();
			p_prc_id = getppid();

			//Get its parent level and increase by 1
			int parentLevel = lookup(t, p_prc_id);
			int childLevel = parentLevel + 1;

			//Store the child process level
			insert(t, prc_id, childLevel);
			
			/*Print output*/
			printf("Process ID: %d , Level: %d , Parent ID: %d\n", prc_id, childLevel, p_prc_id);

			/*Write output to file*/
			fprintf(fp, "%s%d\n", "Process ID: ", prc_id);
			fprintf(fp, "%s%d\n", "Level: ", i+1);
			fprintf(fp, "%s%d\n", "Parent process ID: ", p_prc_id);

		}
	}
	
	/*Close file to save data*/
	fclose(fp);

	/*Sleep for 1 second*/
	sleep(1);

	return 0;
}