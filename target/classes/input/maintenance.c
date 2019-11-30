#include <stdio.h>

int test1 () {
  int i,sum;
  sum = 0;                       
	
	for (i = 0; i < 100; i++)
  	sum += i; 
	return sum;
}


int test2 () {
  int i,sum;
	
	for (i = 0, sum = 0; i < 100; i++)    
  	sum += i; 
	return sum;
}


int foo1 (int qX ) {
  int a, c, d;

  d = (qX-1);
  while ( d != 0 ) {
    d = c - (c/d) * d;
  }
  return (a % (qX-1)); 
}

int foo2 (int qX ) {
  int a, c, d;

  d = (qX-1);
  while ( qX-1 != 0 ) {
    d = c - (c/d) * d;
  }
  return (a % (qX-1)); 
}
