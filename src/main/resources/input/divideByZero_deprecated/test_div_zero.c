#include <stdio.h>

void test(int z) {
	int x;
  if (z == 0)
    x = 1 / z; // warn
}

void test2() {
  int x = 1;
  int y = x % 0; // warn
}

int f(unsigned int a) {
  if (a <= 0) return 1 / a;
  return a;
}


int fooPR10616 (int qX ) {
  int a, c, d;

  d = (qX-1);
  while ( d != 0 ) {
    d = c - (c/d) * d;
  }

  return (a % (qX-1)); // expected-warning {{Division by zero}} 

}
