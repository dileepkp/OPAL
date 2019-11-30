#include <stdio.h>

void writeCharToLog(char *Data) {
	FILE *f = fopen("mylog.txt","w");
	if(f != NULL) {
		
		if(!Data)
			return;

		fputc(*Data,f);
		fclose(f);
	}
	
	return;
}
