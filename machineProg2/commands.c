#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <stdlib.h>
#include "structs_util.c"

int ld(struct Board*);
int sw(struct Board*);
int si(struct Board*);
int sr(struct Board*);
int sd(struct Board*);
int p(struct Board*);
int q(struct Board*);
int move(struct Board*);
int u(struct Board*);
int r(struct Board*);
int s(struct Board*);
int l(struct Board*);

//If agumet is given, loads the deck in the file of the agiment name else loads defult deck
//If a deck is loaded returns 1 else returns error info
int ld(struct Board *board){
    FILE *filePointer;
    int i = 0;

    // Define buffer to store card representation
    char cards[3];

    if (board->aguement[0] == '\0') {
        setDeckToDefoult(board->deck);
        return 1;

    } else {
        // Construct filename based on argument
        char filename[50];
        snprintf(filename, sizeof(filename), "mp2_CLionProject/SaveDeck/%s.txt", board->aguement);
        filePointer = fopen(filename, "r");
    }

    if (filePointer == NULL) {
        return 2; // Return error code
    }

    bool checkDeck[52];

    while(i < 52){
        checkDeck[i] = false;
        i++;
    }
    i = 0;
    int j = 0;

    // Read each card from the file
    while (fgets(cards, sizeof(cards), filePointer) != NULL && i < 52) {
        // Skip newline characters
        if (cards[0] == '\n') {
            continue;
        }
        int num = cardCharToNum(cards[0]);
        int suit;
        switch(cards[1]){
            case 'H':
                suit = -1;
                break;
            case 'D':
                suit = 12;
                break;
            case 'C':
                suit = 25;
                break;
            case 'S':
                suit = 38;
                break;
            default:
                j = 0;
                while(j<52){
                    board->deck[j].created = false;
                    j++;
                }
                return 500+i+1;
        }
        if(num<1||num>13){
            j = 0;
            while(j<52){
                board->deck[j].created = false;
                j++;
            }
            return 500+i+1;
        }
        if(checkDeck[suit+num]){
            j = 0;
            while(j<52){
                board->deck[j].created = false;
                j++;
            }
            return 600+i+1;
        }else{
            checkDeck[suit+num] = true;
        }

        // Store the card information in board->deck[i]
        board->deck[i].created = true;
        board->deck[i].hidden = true;
        board->deck[i].num = num;
        board->deck[i].suit = cards[1];
        i++;
    }

    fclose(filePointer);

    return 1;
}
//If deck is loaded flips all cards face up and returns 1 else returns 3
int sw(struct Board *board){
    bool valid = false;
    int i = 0;
    while(i<52) {
        if(board->deck[i].created) {
            board->deck[i].hidden = false;
            valid = true;
        }
        i++;
    }
    if(!valid){
        return 3;
    }
    return 1;
}
//If agument is given splits deck acordingly and returns 1 if agument is invalid returns 0
//If agument isnt given uses 26 as agument and returns 1
//If deck isnt loaded returns 3
int si(struct Board *board){
    int i = 0;
    while(i<52) {
        if(!board->deck[i].created) {
            return 3;
        }
        i++;
    }
    i = 0;
    int split = 26;
    if(board->aguement[0] == ' '){
        i = 1;
    }
    if(board->aguement[0] == '\0'){
        srand(time(0));
        split = (rand()%50) + 1;
    }else if(board->aguement[i+1] != '\0' && board->aguement[i+2] != '\0'){
        return 0;
    }else if(board->aguement[i+1] == '\0'){
        split = board->aguement[i]-'0';
    }else{
        split = (board->aguement[i+1]-'0')+(10*(board->aguement[i]-'0'));
    }
    if(split < 1 || split > 51){
        return 0;
    }

    i = 0;
    struct Card pile1[split];
    struct Card pile2[52-split];
    while(i < 52){
        if(i > 51-split){
            pile2[i] = board->deck[i];
        }else{
            pile1[i-(52-split)] = board->deck[i];
        }
        i++;
    }

    int p1 = 0;
    int p2 = 0;
    i = 0;
    while(i < 52){
        if(i < split*2 && i < ((52-split)*2)+1){
            if(i%2 == 0){
                board->deck[i] = pile1[i/2];
                p1 = i/2;
            }else{
                board->deck[i] = pile2[(i-1)/2];
                p2 = (i-1)/2;
            }
        }else if(split>52-split){
            p1++;
            board->deck[i] = pile1[p1];
        }else{
            p2++;
            board->deck[i] = pile2[p2];
        }
        i++;
    }
    return 1;
}
//If deck isnt loaded returns 3 else shuffels deck and returns 1
int sr(struct Board *board){

    struct Card b;
    struct Card t;
    b.prevCard = NULL;
    b.nextCard = &board->deck[51];
    t.prevCard = &board->deck[51];
    t.nextCard = NULL;
    board->deck[51].nextCard = &t;
    board->deck[51].prevCard = &b;

    int i = 0;
    while(i<52) {
        if(!board->deck[i].created) {
            return 3;
        }
        i++;
    }

    i = 50;

    while(i >= 0){
        srand(time(0));
        int r = rand()%(-i+52);
        struct Card* card = cardPointerAt(&b, r);
        board->deck[i].prevCard = card;
        board->deck[i].nextCard = card->nextCard;
        card->nextCard->prevCard = &board->deck[i];
        card->nextCard = &board->deck[i];

        i--;
    }

    struct Card temp[52];
    i = 0;
    while(i < 52){
        temp[i] = *cardPointerAt(b.nextCard, i);
        i++;
    }
    i = 0;
    while(i < 52){
        board->deck[i] = temp[i];
        i++;
    }
    return 1;
}
//Saves deck to a file and returns 0 or returns 2 if unable to create file
int sd(struct Board *board){

    FILE *filePointer;
    int i = 0;
    char suit[52];
    char num[52];

    while(i<52){
        suit[i] = board->deck[i].suit;
        num[i] = cardNumToChar(board->deck[i].num);
        i++;
    }

// fopen opens the file: syntax fopen(fileName, editMode)
    if(board->aguement[0] == '\0') {
        filePointer = fopen("mp2_CLionProject/SaveDeck/cards.txt", "w");
    }else{
        char filename[50];
        snprintf(filename, sizeof(filename), "mp2_CLionProject/SaveDeck/%s.txt", board->aguement);
        filePointer = fopen(filename, "w");
    }

// Check if the file opened successfully
    if (filePointer == NULL) {
        return 2; // Return error code
    }

// Write each suit and number to the file
    for (i = 0; i < 52; i++) {
        fprintf(filePointer, "%c%c\n", num[i], suit[i]);
    }

// Close the file
    fclose(filePointer);

    return 1;
}
//If deck isnt loaded returns 3 else setups game and returns 1
int p(struct Board *board){

    int k = 0;

    while(k < 52){
        if(board->deck[k].created != true){
            return 3;
        }
        k++;
    }

    board->c[0] = &board->deck[0];
    board->c[1] = &board->deck[1];
    board->c[2] = &board->deck[2];
    board->c[3] = &board->deck[3];
    board->c[4] = &board->deck[4];
    board->c[5] = &board->deck[5];
    board->c[6] = &board->deck[6];

    board->f[0] = NULL;
    board->f[1] = NULL;
    board->f[2] = NULL;
    board->f[3] = NULL;

    board->deck[0].hidden = false;
    board->deck[0].nextCard = NULL;
    board->deck[0].prevCard = NULL;

    int i;
    int pile = 1;

    while(pile < 7){
        i = 0;
        while(i < 5+pile){
            int j = 0;
            int p = 0;
            int t = 0;
            int n = 0;
            while(j < i-4){
                j++;
                p = t;
                t = n;
                n = n + j;
            }
            int preOffset = ((i-1) * 6) + pile - p;
            int offset = (i * 6) + pile - t;
            int nextOffset = ((i+1) * 6) + pile - n;

            if(i > 0 && i < 4+pile) {
                board->deck[offset].prevCard = &board->deck[preOffset];
                board->deck[offset].nextCard = &board->deck[nextOffset];
            }else if(i == 0) {
                board->deck[offset].prevCard = NULL;
                board->deck[offset].nextCard = &board->deck[nextOffset];
            }else if(i == 4+pile){
                board->deck[offset].prevCard = &board->deck[preOffset];
                board->deck[offset].nextCard = NULL;
            }

            if(i < pile){
                board->deck[offset].hidden = true;
            }else{
                board->deck[offset].hidden = false;
            }
            i++;
        }
        pile++;
    }

    board->playPhase = true;
    return 1;
}
//stops game and retrns 1
int q(struct Board *board){
    int i = 0;
    while(i < 52){
        board->deck[i].hidden = true;
        i++;
    }
    board->playPhase = false;
    freeAllLogs(board->uLog);
    board->uLog = NULL;
    board->rLog = NULL;
    return 1;
}
//If move is legal executes move and returns 1 else returns 4
int move(struct Board *board){
    struct Card *from;
    struct Card *to;
    bool fromF = false;
    int pileNr = 0;
    char dc = '\0';
    int dn = 0;
    char suit = '\0';
    int num = 0;

    if(board->aguement[0] == 'C' && board->aguement[2] == ':' && board->aguement[5] == '-' && board->aguement[6] == '>'){
        pileNr = board->aguement[1]-'0';
        pileNr--;
        num = cardCharToNum(board->aguement[3]);
        suit = board->aguement[4];
        dc = board->aguement[7];
        dn = board->aguement[8]-'0';

        if(num < 1 || num > 13 || (suit != 'H' && suit != 'D' && suit != 'C' && suit != 'S')){
            return 4;
        }

        int i = 0;
        while(true){
            from = cardPointerAt(board->c[pileNr], i);
            if(from == NULL){
                return 4;
            }else if(from->num == num && from->suit == suit){
                break;
            }
            i++;
        }

    }else if(board->aguement[0] == 'F' && board->aguement[2] == '-' && board->aguement[3] == '>'){
        pileNr = board->aguement[1]-'0';
        pileNr--;
        dc = board->aguement[4];
        dn = board->aguement[5]-'0';
        fromF = true;

        from = cardPointerAtTop(board->f[pileNr]);

        if(from == NULL){
            return 4;
        }
    }else{
        return 4;
    }

    dn--;

    if(dn == pileNr && ((dc == 'C' && !fromF)||(dc == 'F' && fromF))){
        return 4;
    }

    if(dc == 'C'){
        if((board->c[dn] == NULL && from->num != 13) || dn < 0 || dn > 6){
            return 4;
        }else if(board->c[dn] == NULL){
            struct Log thisMove;
            thisMove.moved = from;
            thisMove.from = from->prevCard;
            thisMove.to = NULL;
            if(fromF){
                thisMove.cfFrom = 'F';
            }else{
                thisMove.cfFrom = 'C';
            }
            thisMove.pileFrom = pileNr;
            thisMove.cfTo = dc;
            thisMove.pileTo = dn;
            thisMove.hidden = false;
            if(from->prevCard != NULL){
                thisMove.hidden = from->prevCard->hidden;
                from->prevCard->hidden = false;
            }
            saveLog(board, thisMove);
            board->c[dn] = from;
            if(fromF && from->prevCard == NULL){
                board->f[pileNr] = NULL;
            }else if(!fromF && from->prevCard == NULL){
                board->c[pileNr] = NULL;
            }else{
                from->prevCard->nextCard = NULL;
                from->prevCard = NULL;
            }
            return 1;
        }else{
            to = cardPointerAtTop(board->c[dn]);
            if(from->suit != to->suit && from->num+1 == to->num){
                struct Log thisMove;
                thisMove.moved = from;
                thisMove.from = from->prevCard;
                thisMove.to = to;
                if(fromF){
                    thisMove.cfFrom = 'F';
                }else{
                    thisMove.cfFrom = 'C';
                }
                thisMove.pileFrom = pileNr;
                thisMove.cfTo = dc;
                thisMove.pileTo = dn;
                thisMove.hidden = false;
                if(from->prevCard != NULL){
                    thisMove.hidden = from->prevCard->hidden;
                }
                saveLog(board, thisMove);
                if(from->prevCard == NULL && fromF) board->f[pileNr] = NULL;
                if(from->prevCard == NULL && !fromF) board->c[pileNr] = NULL;
                return moveAontopofB(from, to);
            }else{
                return 4;
            }
        }
    }else if(dc == 'F'){
        if(from->nextCard != NULL) return 4;
        if((board->f[dn] == NULL && from->num != 1)|| dn < 0 || dn > 3){
            return 4;
        }else if(board->f[dn] == NULL) {
            struct Log thisMove;
            thisMove.moved = from;
            thisMove.from = from->prevCard;
            thisMove.to = NULL;
            if(fromF){
                thisMove.cfFrom = 'F';
            }else{
                thisMove.cfFrom = 'C';
            }
            thisMove.pileFrom = pileNr;
            thisMove.cfTo = dc;
            thisMove.pileTo = dn;
            thisMove.hidden = false;
            if(from->prevCard != NULL){
                thisMove.hidden = from->prevCard->hidden;
                from->prevCard->hidden = false;
            }
            saveLog(board, thisMove);
            board->f[dn] = from;
            if (fromF && from->prevCard == NULL) {
                board->f[pileNr] = NULL;
            } else if (!fromF && from->prevCard == NULL) {
                board->c[pileNr] = NULL;
            }else{
                from->prevCard->nextCard = NULL;
                from->prevCard = NULL;
            }
            return 1;
        }else{
            to = cardPointerAtTop(board->f[dn]);
            if(from->suit == to->suit && from->num == to->num+1){
                struct Log thisMove;
                thisMove.moved = from;
                thisMove.from = from->prevCard;
                thisMove.to = to;
                if(fromF){
                    thisMove.cfFrom = 'F';
                }else{
                    thisMove.cfFrom = 'C';
                }
                thisMove.pileFrom = pileNr;
                thisMove.cfTo = dc;
                thisMove.pileTo = dn;
                thisMove.hidden = false;
                if(from->prevCard != NULL){
                    thisMove.hidden = from->prevCard->hidden;
                }
                saveLog(board, thisMove);
                if(from->prevCard == NULL && fromF) board->f[pileNr] = NULL;
                if(from->prevCard == NULL && !fromF) board->c[pileNr] = NULL;
                return moveAontopofB(from, to);
            }else{
                return 4;
            }
        }
    }else{
        return 4;
    }
}
//Undo last move and returns 1 or returns 5 if no move is saved
int u(struct Board *board){
    if(board->uLog == NULL){
        return 5;
    }
    struct Card* card = board->uLog->moved;
    if(board->uLog->to == NULL){
        if(board->uLog->cfTo == 'C'){
            board->c[board->uLog->pileTo] = NULL;
        }else{
            board->f[board->uLog->pileTo] = NULL;
        }
    }else{
        board->uLog->to->nextCard = NULL;
    }
    if(board->uLog->from == NULL){
        card->prevCard = NULL;
        if(board->uLog->cfFrom == 'C'){
            board->c[board->uLog->pileFrom] = card;
        }else{
            board->f[board->uLog->pileFrom] = card;
        }
    }else{
        if(board->uLog->hidden){
            board->uLog->from->hidden = true;
        }
        card->prevCard = board->uLog->from;
        card->prevCard->nextCard = card;
    }
    board->rLog = board->uLog;
    board->uLog = board->uLog->prevLog;
    return 1;
}
//Redo last undo and returns 1 or returns 6 if no undo is saved
int r(struct Board *board){
    if(board->rLog == NULL){
        return 6;
    }
    struct Card* card = board->rLog->moved;
    if(board->rLog->from == NULL){
        if(board->rLog->cfFrom == 'C'){
            board->c[board->rLog->pileFrom] = NULL;
        }else{
            board->f[board->rLog->pileFrom] = NULL;
        }
    }else{
        board->rLog->from->hidden = false;
        board->rLog->from->nextCard = NULL;
    }
    if(board->rLog->to == NULL){
        card->prevCard = NULL;
        if(board->rLog->cfTo == 'C'){
            board->c[board->rLog->pileTo] = card;
        }else{
            board->f[board->rLog->pileTo] = card;
        }
    }else{
        card->prevCard = board->rLog->to;
        card->prevCard->nextCard = card;
    }
    board->uLog = board->rLog;
    board->rLog = board->rLog->nextLog;
    return 1;
}
int s(struct Board *board){

    FILE *filePointer;

    if(board->aguement[0] == '\0') {
        filePointer = fopen("mp2_CLionProject/SaveDeck/cards.txt", "w");
    }else{
        char filename[50];
        snprintf(filename, sizeof(filename), "mp2_CLionProject/SaveDeck/%s.txt", board->aguement);
        filePointer = fopen(filename, "w");
    }

    if (filePointer == NULL) {
        return 2;
    }
    int i = 0;
    char suit[52];
    char num[52];
    char rl = '\0';
    char ul = '\0';

    while(i<52){
        suit[i] = board->deck[i].suit;
        num[i] = cardNumToChar(board->deck[i].num);
        i++;
    }
    i = 0;
    while(i<52){
        fprintf(filePointer, "%c%c\n", num[i], suit[i]);
        i++;
    }
    int j = 0;
    while(j<7) {
        fprintf(filePointer, "\n");
        struct Card *baseCard = board->c[j];
        struct Card *card = cardPointerAt(baseCard, 1);
        if (baseCard != NULL) {
            fprintf(filePointer, "%c%c%u\n", cardNumToChar(baseCard->num), baseCard->suit, baseCard->hidden);
            i = 2;
            while (card != NULL) {
                fprintf(filePointer, "%c%c%u\n", cardNumToChar(card->num), card->suit, card->hidden);
                card = cardPointerAt(baseCard, i);
                i++;
            }
        }
        j++;
    }
    j = 0;
    while(j<4) {
        fprintf(filePointer, "\n");
        struct Card *baseCard = board->f[j];
        struct Card *card = cardPointerAt(baseCard, 1);
        if (baseCard != NULL) {
            fprintf(filePointer, "%c%c%u\n", cardNumToChar(baseCard->num), baseCard->suit, baseCard->hidden);
            i = 2;
            while (card != NULL) {
                fprintf(filePointer, "%c%c%u\n", cardNumToChar(card->num), card->suit, card->hidden);
                card = cardPointerAt(baseCard, i);
                i++;
            }
        }
        j++;
    }
    struct Log* log = firstLog(board->uLog);
    if(log == NULL){
        log = firstLog(board->rLog);
    }
    i = 0;
    while(true){
        i++;
        if(log == NULL){
            break;
        }
        fprintf(filePointer, "\n");
        if(log == board->uLog){
            ul = i+'0';
        }
        if(log == board->rLog){
            rl = i+'0';
        }
        fprintf(filePointer, "%c%c\n", cardNumToChar(log->moved->num), log->moved->suit);
        if(log->from == NULL){
            fprintf(filePointer, "NULL\n");
        }else{
            fprintf(filePointer, "%c%c\n", cardNumToChar(log->from->num), log->from->suit);
        }
        fprintf(filePointer, "%c%c\n", log->cfFrom, log->pileFrom+'0');
        if(log->to == NULL){
            fprintf(filePointer, "NULL\n");
        }else{
            fprintf(filePointer, "%c%c\n", cardNumToChar(log->to->num), log->to->suit);
        }
        fprintf(filePointer, "%c%c\n", log->cfTo, log->pileTo+'0');
        if(log->hidden){
            fprintf(filePointer, "H\n");
        }else{
            fprintf(filePointer, "V\n");
        }
        log = log->nextLog;
    }
    fprintf(filePointer, "\n");
    fprintf(filePointer, "U%c\n", ul);
    fprintf(filePointer, "R%c", rl);
    fclose(filePointer);
    return 1;
}
int l(struct Board *board){
    return -1;
}