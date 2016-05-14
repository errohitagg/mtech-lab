/**
 * @file		main.cpp
 * @brief 		Program to simulate Sliding Window Protcol - Go Back-N 
 * @author		Rohit Aggarwal
 * @date		21/10/2015
 */
 
#include<stdio.h>
#include<conio.h>

int reciver(int pac) {
	
	int r;
	char c;
	
	printf("Receving packet: %d", pac);
	printf("\nSend Positive Acknowledgement (y/n)? ");
	
	fflush(stdin);
	scanf("%c", &c);
	if(c == 'y') {
		r = pac;
	} else {
		r = -1;
	}
	
	return r;
}

int sender(int pac) {
	
	int r;
	char c;
	
	printf("\nWant succesful transmit of packet (y/n)? ");
	fflush(stdin);
	scanf("%c", &c);
	
	if (c == 'y') {
		r = reciver(pac);
	} else {
		
		printf("\nPacket lost: %d\n", pac);
		r = -1;
	}
	
	return r;
}

int main() {
	
	char ack[50];
	int i, j, ws, n;
	
	printf("\nEnter the window size: ");
	scanf("%d", &ws);
	printf("\nEnter the number of packets to be sent: ");
	scanf("%d", &n);
	
	for (i = 0; i < ws; i++) {   
		
		printf("\nSending packet: %d", i);
		ack[i] = sender(i);
	}
	
	while (i < n) {
		
		if (ack[i % ws] == -1) {
			
			printf("\nNeagative acknowledgement for packet %d", i - ws);
			printf("\nRe-Sending packet: %d", i - ws);
			ack[i % ws] = sender(i - ws);
			
			for (j = i - ws + 1; j < i; j++) {
				
				printf("\nRe-Sending packet: %d", j);
				ack[i % ws] = sender(j);
			}
			
		} else {
			
			printf("\nSending packet: %d", i);
			ack[i % ws] = sender(i);
			i++;
	   }
	}
	return 0;
}