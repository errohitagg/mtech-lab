/**
 * @file		main.cpp
 * @brief 		Program to implement Dijkstra's Algorithm
 * @author		Rohit Aggarwal
 * @date		07/10/2015
 */

#include <stdio.h>
#include <limits.h>

struct Queue {
	
	int elements[7];
	int begin = 0;
	int end = 0;
	int count = 0;
};

void enqueue(Queue &queue, int element) {
	
	if (queue.count == 7) {
		return;
	}
	
	queue.elements[queue.end] = element;
	queue.end = (queue.end + 1) % 7;
	queue.count++;
}

int dequeue(Queue &queue) {
	
	if (queue.count == 0) {
		return INT_MIN;
	}
	
	int element = queue.elements[queue.begin];
	queue.begin = (queue.begin + 1) % 7;
	queue.count--;
	return element;
}

int empty(Queue queue) {
	
	if (queue.count == 0) {
		return 1;
	} else {
		return 0;
	}
}

int in_queue(Queue queue, int element) {
	
	int i = queue.begin, count = 0;
	while (count < queue.count) {
		if (queue.elements[i] == element) {
			return 1;
		}
		i = (i + 1) % 7;
		count++;
	}
	return 0;
}

int *dijkstra(int vertices, int edges[7][7], int startVertex) {
	
	static int distance[7];
	int processed[vertices];
	int i, j, vertex;
	Queue queue;
	
	for (i = 0; i < vertices; i++) {
		distance[i] = INT_MAX;
		processed[i] = 0;
	}
	
	distance[startVertex] = 0;
	enqueue(queue, startVertex);
	
	while (empty(queue) == 0) {
		
		vertex = dequeue(queue);
		processed[vertex] = 1;
		
		for (i = 0; i < vertices; i++) {
			
			if (edges[vertex][i] != -1 && processed[i] == 0) {
			
				if (in_queue(queue, i) == 0) {
					enqueue(queue, i);
				}
				
				if ((edges[vertex][i] + distance[vertex]) < distance[i]) {
					distance[i] = distance[vertex] + edges[vertex][i];
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
	
	printf ("\nRun Dijkstra's algorithm from which vertex ( <=%d): ", vertices);
	scanf("%d", &startVertex);
	
	shortestDistance = dijkstra(vertices, edges, startVertex - 1);
	printf("Shortest distance to all nodes from %d\n", startVertex);
	printf("Node\tDistance\n");
	for (i = 0; i < vertices; i++) {
		printf("%d\t%d\n", i, shortestDistance[i]);
	}
		
	return 0;
}
