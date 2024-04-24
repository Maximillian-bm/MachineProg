#include "commands.c"
#include "game_util.c"

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

    return 1;
}