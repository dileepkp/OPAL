
int get_LeakyBucketRate(unsigned long NumberLeakyBuckets, unsigned long *Rmin)
{
  FILE *f;
  unsigned long i, buf;

  f = fopen("file", "r");
  if(f == NULL)
  {
    printf(" LeakyBucketRate File does not exist; using rate calculated from avg. rate \n");
    return 0;
  }
  
  for(i=0; i<NumberLeakyBuckets; i++) 
  {
    if(1 != fscanf(f, "%ld", &buf)) 
    {
      printf(" Leaky BucketRateFile does not have valid entries;\n using rate calculated from avg. rate \n");
      fclose (f);
      return 0;
    }
    Rmin[i] = buf;
  }
  fclose (f);
  return 1;
}
