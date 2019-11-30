#include <stdio.h>
int main(){

int x = 2;
int *p = &x;// p hasValue A_x, p pointsTo x

int **q;
q = &p;// q hasValue A_p, q points to p

int *r;
r= *q; // r hasValue A_x, r points to x

int *s;
s = p; // s hasValue A_x, s points to x

int w = **q;

return 0;
}
