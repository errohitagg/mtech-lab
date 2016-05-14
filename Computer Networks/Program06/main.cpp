/**
 * @file		main.cpp
 * @brief 		Program to implement Floyd Warshall's Algorithm
 * @author		Rohit Aggarwal
 * @date		14/10/2015
 */

#include <stdio.h>
#include <limits.h>

int ** floydWarshall(int vertices, int edges[7][7]) {
	
	int **distance = new int*[7];
	int processed[vertices];
	int i, j, k;
	
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			distance[i][j] = edges[i][j];
		}
	}
	
	for (k = 0; k < vertices; k++) {
		for (i = 0; i < vertices; i++) {
			for (j = 0; j < vertices; j++) {
				if (distance[i][j] > (distance[i][k] + distance[k][j])) {
					distance[i][j] = distance[i][k] + distance[k][j];
				}
			}
		}
	}
	
	return distance;
}

int main(int argc, char **argv)
{
	int vertices, startVertex;
	int edges[7][7];
	int **shortestDistance;
	int i, j;
	
	printf("Enter number of vertices ( <=7 ): ");
	scanf("%d", &vertices);
	
	if (vertices > 7) {
		printf("\nMaxmium 7 vertices were allowed");
		return 1;
	}
	
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			edges[i][j] = INT_MIN;
		}
	}
	
	printf("\nEnter distance between pair of vertices (0 if edge doesn't exists)\n");
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			if (i == j) {
				edges[i][j] = 0;
				printf("(%d, %d) = %d\n", i + 1, j + 1, edges[i][j]);
			} else {
				printf("(%d, %d) = ", i + 1, j + 1);
				scanf("%d", &edges[i][j]);
				if (edges[i][j] == 0) {
					edges[i][j] = INT_MAX;
				}
			}
		}
	}
	
	shortestDistance = floydWarshall(vertices, edges);
	printf("Shortest distance\n");
	printf("From\tTo\tDistance\n");
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			printf("%d\t%d\t%d\n", i, j, shortestDistance[i][j]);
		}
	}
		
	return 0;
}