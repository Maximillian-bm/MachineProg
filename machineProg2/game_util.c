#include <stdbool.h>

void findAgument(char*, char*, int);
int findMethod(char*, bool);
void message(char*, int);

//given the nummber of the command called and the input string, stores parts of the input string in the given rtnStr
//so that the first chars that represent the command the user wants to be called
void findAgument(char *cmdStr, char *rtnStr, int aguNum){
// in the "input" find and return the agument.
// in the case that the agunum is 1, 5, 12 or 13, return the filename in "input"
// in the case that agunum is 3 return the number in "input"
// in the case of 9 return the whole string of "input"
// any returns should be a char* to a new char array
    int i = 0;
    while(true){
        if(cmdStr[i] == '\0'){
            break;
        }
        i++;
    }

    if(aguNum == 1 || aguNum == 3 || aguNum == 5){
        int j = 0;
        i--;
        while(j<i){
            rtnStr[j] = cmdStr[j+2];
            j++;
        }
    }else if(aguNum == 12 || aguNum == 13){
        int j = 0;
        while(j<i){
            rtnStr[j] = cmdStr[j+2];
            j++;
        }
    }else if(aguNum == 9){
        int j = 0;
        i++;
        while(j<i){
            rtnStr[j] = cmdStr[j];
            j++;
        }
    }
}
//Given the input string and bool represnting playphase returns the int that represents the command to be called
//if input is invalid returns 0;
int findMethod(char *input, bool playPhase){
// return the number assotiated with the command saved in "input"
// if input is not valid return 0
    if(playPhase){
        switch(input[0]){
            case 'Q':
                if(input[1] == '\0'){
                    return 8;
                }else{
                    return 0;
                }
            case 'C':
                return 9;
            case 'F':
                return 9;
            case 'U':
                if(input[1] == '\0'){
                    return 10;
                }else{
                    return 0;
                }
            case 'R':
                if(input[1] == '\0'){
                    return 11;
                }else{
                    return 0;
                }
            case 'S':
                if(input[1] != '\0'){
                    return 12;
                }else{
                    return 0;
                }
            case 'L':
                if(input[1] != '\0'){
                    return 13;
                }else{
                    return 0;
                }
        }
    }else{
        switch (input[1]) {
            case 'D':
                if(input[0] == 'L'){
                    return 1;
                }else if(input[0] == 'S'){
                    return 5;
                }else{
                    return 0;
                }
            case 'W':
                if(input[0] == 'S' && input[2] == '\0'){
                    return 2;
                }else{
                    return 0;
                }
            case 'I':
                if(input[0] == 'S'){
                    return 3;
                }else{
                    return 0;
                }
            case 'R':
                if(input[0] == 'S' && input[2] == '\0'){
                    return 4;
                }else{
                    return 0;
                }
            case 'Q':
                if(input[0] == 'Q' && input[2] == '\0'){
                    return 6;
                }else{
                    return 0;
                }
            case '\0':
                if(input[0] == 'P'){
                    return 7;
                }else{
                    return 0;
                }
        }
    }
    return 0;
}
//Given an int, stores the apropriate message in the given string
void message(char* str, int i){
    if(i>=500 && i<600){
        int line = i - 500;
        int ones = line%10;
        int tens = (line-ones)/10;
        str[0] = 'F';
        str[1] = 'I';
        str[2] = 'L';
        str[3] = 'E';
        str[4] = ' ';
        str[5] = 'U';
        str[6] = 'N';
        str[7] = 'V';
        str[8] = 'A';
        str[9] = 'L';
        str[10] = 'I';
        str[11] = 'D';
        str[12] = ' ';
        str[13] = 'A';
        str[14] = 'T';
        str[15] = ' ';
        str[16] = tens+'0';
        str[17] = ones+'0';
        str[18] = '\0';
    }else if(i>=600 && i<700){
        int line = i - 600;
        int ones = line%10;
        int tens = (line-ones)/10;
        str[0] = 'F';
        str[1] = 'I';
        str[2] = 'L';
        str[3] = 'E';
        str[4] = ' ';
        str[5] = 'D';
        str[6] = 'O';
        str[7] = 'U';
        str[8] = 'B';
        str[9] = 'L';
        str[10] = 'I';
        str[11] = 'C';
        str[12] = 'A';
        str[13] = 'T';
        str[14] = 'E';
        str[15] = ' ';
        str[16] = 'A';
        str[17] = 'T';
        str[18] = ' ';
        str[19] = tens+'0';
        str[20] = ones+'0';
        str[21] = '\0';
    }
    switch (i) {
        case -1:
            str[0] = 'N';
            str[1] = 'O';
            str[2] = 'T';
            str[3] = ' ';
            str[4] = 'I';
            str[5] = 'M';
            str[6] = 'P';
            str[7] = 'L';
            str[8] = 'E';
            str[9] = 'M';
            str[10] = 'E';
            str[11] = 'N';
            str[12] = 'T';
            str[13] = 'E';
            str[14] = 'D';
            str[15] = '\0';
            break;
        case 0:
            str[0] = 'N';
            str[1] = 'O';
            str[2] = 'T';
            str[3] = ' ';
            str[4] = 'V';
            str[5] = 'A';
            str[6] = 'L';
            str[7] = 'I';
            str[8] = 'D';
            str[9] = '\0';
            break;
        case 1:
            str[0] = 'O';
            str[1] = 'K';
            str[2] = '\0';
            break;
        case 2:
            str[0] = 'U';
            str[1] = 'N';
            str[2] = 'A';
            str[3] = 'B';
            str[4] = 'L';
            str[5] = 'E';
            str[6] = ' ';
            str[7] = 'T';
            str[8] = 'O';
            str[9] = ' ';
            str[10] = 'F';
            str[11] = 'I';
            str[12] = 'N';
            str[13] = 'D';
            str[14] = ' ';
            str[15] = 'F';
            str[16] = 'I';
            str[17] = 'L';
            str[18] = 'E';
            str[19] = '\0';
            break;
        case 3:
            str[0] = 'N';
            str[1] = 'O';
            str[2] = ' ';
            str[3] = 'D';
            str[4] = 'E';
            str[5] = 'C';
            str[6] = 'K';
            str[7] = ' ';
            str[8] = 'L';
            str[9] = 'O';
            str[10] = 'A';
            str[11] = 'D';
            str[12] = 'E';
            str[13] = 'D';
            str[14] = '\0';
            break;
        case 4:
            str[0] = 'N';
            str[1] = 'O';
            str[2] = 'T';
            str[3] = ' ';
            str[4] = 'A';
            str[5] = ' ';
            str[6] = 'V';
            str[7] = 'A';
            str[8] = 'L';
            str[9] = 'I';
            str[10] = 'D';
            str[11] = ' ';
            str[12] = 'M';
            str[13] = 'O';
            str[14] = 'V';
            str[15] = 'E';
            str[16] = '\0';
            break;
        case 5:
            str[0] = 'N';
            str[1] = 'O';
            str[2] = ' ';
            str[3] = 'M';
            str[4] = 'O';
            str[5] = 'V';
            str[6] = 'E';
            str[7] = 'S';
            str[8] = ' ';
            str[9] = 'T';
            str[10] = 'O';
            str[11] = ' ';
            str[12] = 'U';
            str[13] = 'N';
            str[14] = 'D';
            str[15] = 'O';
            str[16] = '\0';
            break;
        case 6:
            str[0] = 'N';
            str[1] = 'O';
            str[2] = ' ';
            str[3] = 'M';
            str[4] = 'O';
            str[5] = 'V';
            str[6] = 'E';
            str[7] = 'S';
            str[8] = ' ';
            str[9] = 'T';
            str[10] = 'O';
            str[11] = ' ';
            str[12] = 'R';
            str[13] = 'E';
            str[14] = 'D';
            str[15] = 'O';
            str[16] = '\0';
            break;
    }
}