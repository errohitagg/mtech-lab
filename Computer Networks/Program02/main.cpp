/**
 * @file 		main.cpp
 * @brief 		Program to Simulate Distance Vector Routing
 * @author 		Rohit Aggarwal
 * @date 		14/10/2015
 */
 
#include<stdio.h>

struct node {
   
	int dist[10];
	int frm[10];
      
} rt[10];

int main()  {
	
    int cst[10][10];
    int n, i, j, k, count;
    
	printf("\nEnter the number of nodes: ");
    scanf("%d", &n);
    printf("\nEnter the cost matrix: \n");
    
	for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            
			scanf("%d", &cst[i][j]);
            if (i == j) {
                cst[i][j] = 0;
			}
            rt[i].dist[j] = cst[i][j];
            rt[i].frm[j] = j;
        }
    }
    
    do {
		
        count = 0;
        for (i = 0; i < n; i++) {
            for(j = 0; j < n; j++) {
                for(k = 0; k < n; k++) {
					
                    if (rt[i].dist[j] > cst[i][k] + rt[k].dist[j]) {
                         rt[i].dist[j] = rt[i].dist[k] + rt[k].dist[j];
                         rt[i].frm[j] = k;
                         count++;
                    }                
                }
            }
        }
		
    } while (count != 0);
    
    for (i = 0; i < n; i++) {
		
       printf("\nState value for router: %d", i + 1);
       
	   for (j = 0; j < n; j++) {
           printf("\n\tNode %d via %d distance %d", j + 1, rt[i].frm[j] + 1, rt[i].dist[j]);
       }
    }
    
    return 0;
}