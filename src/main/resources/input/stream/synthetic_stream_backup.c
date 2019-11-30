void foo(int *Data) {
  FILE *F = fopen("myfile.txt", "w");
  if (F != 0) {
		if(!Data){
			fputc(*Data, F);
			fclose(F);
  }
	}
  fclose(F);
	return;
}


