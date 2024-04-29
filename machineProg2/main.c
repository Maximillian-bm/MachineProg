#include "commands.c"
#include "game_util.c"
#include <string.h>

int main() {

    //tests for findAgument
    printf("findAgument tests: \n");
    char *cmdStr = "LD1";
    char rtnStr[21];
    findAgument(cmdStr, rtnStr, 1);
    if (rtnStr[0] == '1' ){
        printf("Load deck test passed\n");
    } else {
        printf("Load deck test failed\n");
    }

    cmdStr = "SI20";
    findAgument(cmdStr, rtnStr, 3);
    if (rtnStr[0] == '2' && rtnStr[1] == '0') {
        printf("Split test passed\n");
    }else {
        printf("Split test failed\n");
    }

    cmdStr = "SD1";
    findAgument(cmdStr, rtnStr, 5);
    if (rtnStr[0] == '1'){
        printf("Safe file test passed\n");
    } else {
        printf("Safe file test failed\n");
    }

    cmdStr = "C1:AH->F1";
    findAgument(cmdStr, rtnStr, 9);
    if (rtnStr[0] == 'C' && rtnStr[1] == '1' && rtnStr[2] == ':' && rtnStr[3] == 'A' && rtnStr[4] == 'H' && rtnStr[5] == '-' && rtnStr[6] == '>' && rtnStr[7] == 'F' && rtnStr[8] == '1'){
        printf("Move test passed\n\n");
    } else {
        printf("Move test failed\n\n");
    }


    // Tests for findMethod
    printf("findMethod test: \n");
    char *input = "LD";
    bool playPhase = false;
    if (findMethod(input, playPhase) == 1){
        printf("Load deck test passed\n");
    } else {
        printf("Load deck test failed\n");
    }

    input = "SW";
    if (findMethod(input, playPhase) == 2){
        printf("Display cards test passed\n");
    } else {
        printf("Display cards test failed\n");
    }

    input = "SI";
    if (findMethod(input, playPhase) == 3){
        printf("Split test passed\n");
    } else{
        printf("Split test failed\n");
    }

    input = "SR";
    if (findMethod(input, playPhase) == 4){
        printf("Shuffle test passed\n");
    } else {
        printf("Shuffle test failed\n");
    }

    input = "SD";
    if (findMethod(input, playPhase) == 5){
        printf("Safe deck test passed\n");
    } else {
        printf("Safe deck test failed\n");
    }

    input = "P";
    if (findMethod(input, playPhase) == 7){
        printf("Play Phase test passed\n");
    } else {
        printf("Play phase test failed\n");
    }

    input = "QQ";
    if (findMethod(input, playPhase) == 6){
        printf("Exit game test passed\n");
    } else {
        printf("Exit game test failed\n");
    }

    playPhase = true;
    input = "Q";
    if (findMethod(input, playPhase) == 8) {
        printf("Exit play phase test passed\n");
    } else {
        printf("Exit play phase test failed\n");
    }

    input = "C";
    if (findMethod(input, playPhase) == 9) {
        printf("Move test passed\n");
    } else {
        printf("Move test failed\n");
    }

    input = "F";
    if (findMethod(input, playPhase) == 9) {
        printf("Move test passed\n");
    } else {
        printf("Move test failed\n");
    }

    input = "U";
    if (findMethod(input, playPhase) == 10) {
        printf("Undo test passed\n");
    } else {
        printf("Undo test failed\n");
    }

    input = "R";
    if (findMethod(input, playPhase) == 11) {
        printf("Redo test passed\n");
    } else {
        printf("Redo test failed\n");
    }

    input = "S";
    if (findMethod(input, playPhase) == 12) {
        printf("Safe state test passed\n");
    } else {
        printf("Safe state test failed\n");
    }

    input = "L";
    if (findMethod(input, playPhase) == 13) {
        printf("Load state test passed\n");
    } else {
        printf("Load state test failed\n");
    }

    input = "KT";
    if (findMethod(input, playPhase) == 0) {
        printf("Playphase wrong input test passed\n\n");
    } else {
        printf("Playphase wrong input test failed\n\n");
    }

    // Tests for structs_util
    printf("structs_util tests: \n");
    struct Card card = {.num = 7, .suit = 'H', .hidden = false, .nextCard = NULL};
    char cardPointer[3];
    char expectedCard[] = "7H";

    cardAt(&card, 0, cardPointer);
    if (strcmp(cardPointer, expectedCard) == 0) {
        printf("CardAt test passed\n");
    } else {
        printf("CardAt test failed\n");
    }

    card.hidden = true;
    char expectedHiddenCard[] = "[]";

    cardAt(&card, 0, cardPointer);
    if (strcmp(cardPointer, expectedHiddenCard) == 0) {
        printf("CardAt test passed\n");
    } else {
        printf("CardAt test failed\n");
    }

    //negative test, should give an error
    cardAt(&card, -1, cardPointer);

    cardAtTop(&card, cardPointer);
    if (strcmp(cardPointer, expectedCard) == 0) {
        printf("CardAtTop test passed\n");
    } else {
        printf("CardAtTop test failed\n");
    }


    bool test_passed = true;

    for (int i = 1; i <= 13; i++) {
        char result = cardNumToChar(i);
        switch (result) {
            case 'A':
            case 'T':
            case 'J':
            case 'Q':
            case 'K':
                break;
            default:
                if (result != (char)(i + '0')) {
                    test_passed = false;
                    printf("Test failed for value %d\n", i);
                }
        }
    }

    if (test_passed) {
        printf("cardNumtoChar test passed\n");
    } else {
        printf("cardNumtoChar test failed\n");
    }

    for (int i = 1; i <= 13; i++) {
        int result;
        switch (i) {
            case 1:
                result = cardCharToNum('A');
                break;
            case 10:
                result = cardCharToNum('T');
                break;
            case 11:
                result = cardCharToNum('J');
                break;
            case 12:
                result = cardCharToNum('Q');
                break;
            case 13:
                result = cardCharToNum('K');
                break;
            default:
                result = cardCharToNum(cardNumToChar(i));
        }

        if (result != i) {
            test_passed = false;
            printf("cardCharToNum test failed for value %d\n", i);
        }
    }

    if (test_passed) {
        printf("cardChartoNum test passed\n");
    } else {
        printf("cardChartoNum test failed\n");
    }

    struct Card card1 = {.num = 1, .suit = 'C', .hidden = false, .nextCard = NULL};
    struct Card card2 = {.num = 2, .suit = 'D', .hidden = false, .nextCard = &card1};
    struct Card card3 = {.num = 3, .suit = 'S', .hidden = false, .nextCard = &card2};
    struct Card* result1 = cardPointerAt(&card3, 2);
    struct Card* result2 = cardPointerAt(&card3, -1);
    struct Card* result3 = cardPointerAt(NULL, 2);
    if (result1 == &card1 && result2 == &card3 && result3 == NULL) {
        printf("cardPointerAt test passed\n");
    } else {
        printf("cardPointerAt test failed\n");
    }

    struct Card* result4 = cardPointerAtTop(&card3);
    struct Card* result5 = cardPointerAtTop(&card1);
    struct Card* result6 = cardPointerAtTop(NULL);
    if (result4 == &card1 && result5 == &card1 && result6 == NULL){
        printf("cardPointerAtTop test passed\n");
    } else {
        printf("cardPointerAtTop test failed\n");
    }

    struct Card deck[52];

    setDeckToDefoult(deck);

    test_passed = true;
    for (int i = 0; i < 52; i++) {
        card = deck[i];
        if (!card.created || !card.hidden) {
            test_passed = false;
            break;
        }
        if (i < 13 && card.suit != 'C') {
            test_passed = false;
            break;
        }
        if (i >= 13 && i < 26 && card.suit != 'D') {
            test_passed = false;
            break;
        }
        if (i >= 26 && i < 39 && card.suit != 'H') {
            test_passed = false;
            break;
        }
        if (i >= 39 && card.suit != 'S') {
            test_passed = false;
            break;
        }
        if (card.num != ((i % 13) + 1)) {
            test_passed = false;
            break;
        }
    }

    if (test_passed) {
        printf("setDeckToDefoult test passed\n");
    } else {
        printf("setDeckToDefoult test failed\n");
    }

    struct Card A = {.num = 10, .suit = 'C', .hidden = false, .prevCard = &card1, .nextCard = NULL};
    struct Card B = {.num = 11, .suit = 'D', .hidden = false, .prevCard = NULL, .nextCard = NULL};
    if (moveAontopofB(&A, &B)) {
        printf("moveAontopofB test passed\n");
    } else {
        printf("moveAontopofB test failed\n");
    }

    return 1;
}