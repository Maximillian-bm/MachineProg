#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#include <ws2tcpip.h>

int connectToServer(int);
int readFromServer(char*);
int writeToServer(char*);
void abort();

SOCKET sockfd;

int connectToServer(int port){
    WSADATA wsa;
    struct sockaddr_in server_addr;

    if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0) {
        printf("WSAStartup failed\n");
        return -1;
    }

    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == INVALID_SOCKET) {
        printf("Socket creation failed\n");
        WSACleanup();
        return -1;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);
    inet_pton(AF_INET, "127.0.0.1", &server_addr.sin_addr);

    if (connect(sockfd, (struct sockaddr *)&server_addr, sizeof(server_addr)) == SOCKET_ERROR) {
        printf("Connection failed\n");
        closesocket(sockfd);
        WSACleanup();
        return -1;
    }

    return 1;
}
int readFromServer(char* msg){
    memset(msg, 0, 1024);

    if (recv(sockfd, msg, 1024, 0) == SOCKET_ERROR) {
        printf("Read from server failed\n");
        return -1;
    }
    return 1;
}
int writeToServer(char* msg){
    if (send(sockfd, msg, strlen(msg), 0) == SOCKET_ERROR) {
        printf("Write to server failed\n");
        return -1;
    }

    return 1;
}

void abort(){
    closesocket(sockfd);
    WSACleanup();
}