#include <stdbool.h>

struct Card{
    bool hidden;
    char suit;
    int num;
    struct Card* prevCard;
    struct Card* nextCard;
    bool created;
};

struct Log{

    struct Log* prevLog;
    struct Log* nextLog;

    struct Card* moved;
    struct Card* from;
    struct Card* to;
    char cfFrom;
    int pileFrom;
    char cfTo;
    int pileTo;
    bool hidden;
};

struct Board{

    struct Card deck[52];

    char aguement[21];

    char input[21];

    char output[21];

    bool playPhase;

    struct Card* c[7];

    struct Card* f[4];

    struct Log* uLog;

    struct Log* rLog;
};