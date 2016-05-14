/**
 * @file		main.cpp
 * @brief 		Program to implement Bellman Ford's Algorithm
 * @author		Rohit Aggarwal
 * @date		21/10/2015
 */

#include <stdio.h>
#include <limits.h>

int *bellmanFord(int vertices, int edges[7][7], int startVertex) {
	
	static int distance[7];
	int processed[vertices];
	int i, j, k;
	
	for (i = 0; i < vertices; i++) {
		distance[i] = INT_MAX;
		processed[i] = 0;
	}
	
	distance[startVertex] = 0;
	
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			for (k = 0; k < vertices; k++) {
				if (edges[j][k] != -1) {
					if ((distance[j] + edges[j][k]) < distance[k]) {
						distance[k] = distance[j] + edges[j][k];
					}
				}
			}
		}
	}
	
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			if ((distance[i] + edges[i][j]) < distance[j]) {
				printf("Graph contains a negetive-weight cycle");
			}
		}
	}
	
	return distance;
}

int main(int argc, char **argv)
{
	int vertices, startVertex;
	int edges[7][7];
	int *shortestDistance;
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
	
	printf("\nEnter distance between pair of vertices (-1 if edge doesn't exists)\n");
	for (i = 0; i < vertices; i++) {
		for (j = 0; j < vertices; j++) {
			if (i == j) {
				edges[i][j] = 0;
				printf("(%d, %d) = %d\n", i + 1, j + 1, edges[i][j]);
			} else if (edges[i][j] != INT_MIN) {
				printf ("(%d, %d) = %d\n", i + 1, j + 1, edges[i][j]);
			} else {
				printf("(%d, %d) = ", i + 1, j + 1);
				scanf("%d", &edges[i][j]);
				edges[j][i] = edges[i][j];
			}
		}
	}
	
	printf ("\nRun Bellman Ford algorithm from which vertex ( <=%d): ", vertices);
	scanf("%d", &startVertex);
	
	shortestDistance = bellmanFord(vertices, edges, startVertex - 1);
	printf("Shortest distance to all nodes from %d\n", startVertex);
	printf("Node\tDistance\n");
	for (i = 0; i < vertices; i++) {
		printf("%d\t%d\n", i, shortestDistance[i]);
	}
		
	return 0;
}
