#include <stdio.h>

#define PI 3.14159

struct sds {
    int len;
    int alloc;
    unsigned char flags;
    char buf[];
};

float area(int arg) {
    return PI * arg * arg;
}

void loop(int n) {
    while (n) {
        int i;
        for (i = 0; i < n; i++) {
            switch (i % 5) {
            case 0:
                printf("YES!!!");
                break;
            default:
                break;
            }
        }
        if (sizeof i > 0) {
            goto label;
        }
    }
label:
    n > 0 ? n = 1 : n = -1;
}

int main() {
    // Inline comment
    extern static char ch = 'a';
    auto int integer = 123;
    int float_1 = 1.23;
    int float_2 = 123f;
    int _long = 123l;
    int _long_long = 123ll;
    int _unsigned = 123u;
    int oct = 0123;
    int hex = 0x1a;
    if (integer >= float_1 + float_2 && _long == _long_long) {
        _unsigned++;
    }
    /**
     * Block comment
     */
    double a = (double) area(integer);
    printf("\"area is %f\"", a);
    return 0;
}
