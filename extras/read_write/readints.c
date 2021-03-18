#include <stdio.h>

int main() {
    // Read binary ints from System.in and
    // write them in textual from to System.out
    int i;
    while (fread(&i, sizeof(i), 1, stdin) != 0) {
        printf("%d\n", i);
    }
}