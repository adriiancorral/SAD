/**
 * read ints from stdin and write to stdout
 * in binary form
 */

#include <stdio.h>

int main() {
    int i;
    while (scanf("%d", &i) != EOF) {
        fwrite(&i, sizeof(i), 1, stdout);
    }
}