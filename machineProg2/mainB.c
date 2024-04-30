#include "game_util.c"
#include "commands.c"
#include "client.c"

int main() {

    static char send[1024];
    static char recive[1024];
    connectToServer(1312);
    struct Board board;

    for(int i = 0; i < 52; i++){
        board.deck[i].created = false;
    }

    board.input[0] = '\0';

    board.output[0] = '\0';

    board.playPhase = false;

    board.uLog = NULL;

    board.rLog = NULL;

    bool exit = false;

    while(!exit) {

        while(true){
            int temp = readFromServer(recive);
            if(recive[0] != '\0') break;
            if(temp < 1){
                exit = true;
                break;
            }
            //printf("empty");
        }

        for(int i = 0; i < 21; i++){
            board.input[i] = recive[i];
        }
        board.input[21] = '\0';
        int method = findMethod(&board.input, board.playPhase);

        switch (method) {
            case 1:
                findAgument(&board.input, &board.aguement,1);
                message(board.output, ld(&board));
                break;
            case 2:
                message(board.output, sw(&board));
                break;
            case 3:
                findAgument(&board.input, &board.aguement,3);
                message(board.output, si(&board));
                break;
            case 4:
                message(board.output, sr(&board));
                break;
            case 5:
                findAgument(&board.input, &board.aguement,5);
                message(board.output, sd(&board));
                break;
            case 6:
                exit = true;
                break;
            case 7:
                message(board.output, p(&board));
                break;
            case 8:
                message(board.output, q(&board));
                break;
            case 9:
                findAgument(&board.input, &board.aguement,9);
                message(board.output, move(&board));
                break;
            case 10:
                message(board.output, u(&board));
                break;
            case 11:
                message(board.output, r(&board));
                break;
            case 12:
                findAgument(&board.input, &board.aguement,12);
                message(board.output, s(&board));
                break;
            case 13:
                findAgument(&board.input, &board.aguement,13);
                message(board.output, l(&board));
                break;
            default:
                message(&board.output, method);
                break;
        }
        formatForServer(&board, send);
        writeToServer(send);
        //printf("\nSending %c command to server!\n", send[0]);
        printBord(&board);
    }
    abort();
    return 0;
}