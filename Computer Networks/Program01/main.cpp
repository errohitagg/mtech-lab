/**
 * @file		main.cpp
 * @brief 		Program to validate an Internet Protocol Address
 * @author		Rohit Aggarwal
 * @date		07/10/2015
 */

#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char **argv) {
	
	char ipAddr[16], execute, *token;
	int firstOctet, count, temp;
	
	do {
		
		printf ("\nEnter an IP Address: ");
		scanf("%s", ipAddr);
		
		count = 0;
		token = strtok(ipAddr, ".");
		if (token == NULL) {
			printf("Invalid IP Address. Must contain 4 integers (0-255) separated via dot (.)");
		
		} else {
			
			firstOctet = atoi(token);
			if (firstOctet > 255) {
				printf("Invalid IP Address. Must contain 4 integers (0-255) separated via dot (.)");
				
			} else {
				
				count++;
			
				while (token != NULL) {
				
					token = strtok(NULL, ".");
					if (token == NULL) {
						break;
					}
					
					temp = atoi(token);
					if (temp > 255) {
						break;
					}
					count++;
				}
				
				if (count != 4) {
					printf("Invalid IP Address. Must contain 4 integers (0-255) separated via dot (.)");
				} else if (firstOctet == 127) {
					printf("Valid IP Address (Loopback).");
				} else {
					if (firstOctet < 127) {
						printf("Valid IP Address (Class A");
					} else if (firstOctet < 192)  {
						printf("Valid IP Address (Class B)");
					} else if (firstOctet < 224)  {
						printf("Valid IP Address (Class C)");
					} else if (firstOctet < 240) {
						printf("Valid IP Address (Class D)");
					} else {
						printf("Valid IP Address (Class E)");
					}
				}
			}
		}
		
		printf ("\nDo you want to continue (y/n): ");
		scanf("%s", &execute);
		execute = tolower(execute);
		
	} while (execute != 'n');
	
	return 0;
}