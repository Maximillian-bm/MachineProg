#include "commands.c"
#include "game_util.c"

int main() {

    int c;
    struct Board board;
    int i = 0;
    while(i<52){
        board.deck[i].created = false;
        i++;
    }

    board.input[0] = '\0';

    board.output[0] = '\0';

    board.playPhase = false;

    board.uLog = NULL;

    board.rLog = NULL;

    bool exit = false;

    while(!exit) {
        printBord(&board);

        scanf("%20[^\n]", board.input);

        while ((c = getchar()) != '\n' && c != EOF);

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
                message(&board.input, method);
                break;
        }
    }
    return 0;
}