#include <stdio.h>
#define SIZE 100

void fileFunctions() {

	FILE *f2,*f3;
	int n, i; float x; char name[50],*name2;
	double b[100];
	size_t ret_code;
	FILE *f = fopen("mylog.txt","r");
	f2 = fopen("mylog.txt","r");
	//After this last statement f2 >= 0

	
	n = fscanf(stdin, "%d%f%s", &i, &x, name);
	//The fscanf function returns the value of the macro EOF if an input failure occurs
	//before the first conversion (if any) has completed. Otherwise, the function returns the
	//number of input items assigned, which can be fewer than provided for, or even zero, in
	//the event of an early matching failure.
	fscanf(stdin, "%2d%f%*d %[0123456789]", &i, &x, name);

	int n2 = fgetc(f);
	fgetc(f);

	fgets(name,n,f);
	name2 = fgets(name,n,f);

	ret_code = fread(b, sizeof *b, SIZE, f);
	fread(b, sizeof *b, SIZE, f);
	return;
}