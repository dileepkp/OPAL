#include <stdio.h>

void foo(int *Data) {
  FILE *F = fopen("myfile.txt", "w");
  if (F != 0) {
    fputs ("fopen example", F);
    if (!Data)
      fclose(F);
    else
      fputc(*Data, F);
    fclose(F); // expected-warning {{Closing a previously closed file stream}}
  }
  return;
}


void bar()
{    
  unsigned long AvgRate, TotalRate, NumberLeakyBuckets, jumpd;
unsigned long total_frame_buffer = 0;
  long *buffer_frame, minB;
  unsigned long iBucket, iFrame, framerate, FrameIndex = 0;
  long maxBuffer, actualBuffer, InitFullness, iChannelRate;
  unsigned long *Rmin, *Bmin, *Fmin;
long Bit_Buffer[10000];
   
  fprintf(stdout,"-------------------------------------------------------------------------------\n");
  printf(" Total Frames:  %ld  \n", total_frame_buffer);
  NumberLeakyBuckets = (unsigned long) NumberLeakyBuckets;
  buffer_frame = calloc(total_frame_buffer+1, sizeof(long));
  if(!buffer_frame)
    no_mem_exit("init_buffer: buffer_frame");
  Rmin = calloc(NumberLeakyBuckets, sizeof(unsigned long));
  if(!Rmin)
    no_mem_exit("init_buffer: Rmin");    
  Bmin = calloc(NumberLeakyBuckets, sizeof(unsigned long));
  if(!Bmin)
    no_mem_exit("init_buffer: Bmin");
  Fmin = calloc(NumberLeakyBuckets, sizeof(unsigned long));
  if(!Fmin)
    no_mem_exit("init_buffer: Fmin");

  TotalRate = 0;
  for(iFrame=0; iFrame < total_frame_buffer; iFrame++) 
  {
    TotalRate += (unsigned long) Bit_Buffer[iFrame];
  }
  AvgRate = (unsigned long) ((float) TotalRate/ total_frame_buffer);
  
  if(1 != get_LeakyBucketRate(NumberLeakyBuckets, Rmin))
  { /* if rate file is not present, use default calculated from avg.rate */
    for(iBucket=0; iBucket < NumberLeakyBuckets; iBucket++) 
    {
      if(iBucket == 0)
        Rmin[iBucket] = (unsigned long)((float) AvgRate * framerate)/(jumpd+1); /* convert bits/frame to bits/second */
      else
        Rmin[iBucket] = (unsigned long) ((float) Rmin[iBucket-1] + (AvgRate/4) * (framerate) / (jumpd+1));    
    }
  }
  Sort(NumberLeakyBuckets, Rmin);   

  maxBuffer = AvgRate * 20; /* any initialization is good. */        
  for(iBucket=0; iBucket< NumberLeakyBuckets; iBucket++) 
  {           
    iChannelRate = (long) (Rmin[iBucket] * (jumpd+1)/(framerate)); /* converts bits/second to bits/frame */
    /* To calculate initial buffer size */
    InitFullness = maxBuffer; /* set Initial Fullness to be buffer size */
    buffer_frame[0] = InitFullness;
    minB = maxBuffer; 
    
    for(iFrame=0; iFrame<total_frame_buffer ; iFrame++) 
    {        
      buffer_frame[iFrame] = buffer_frame[iFrame] - Bit_Buffer[iFrame];
      if(buffer_frame[iFrame] < minB) 
      {
        minB = buffer_frame[iFrame];
        FrameIndex = iFrame;
      }
      
      buffer_frame[iFrame+1] = buffer_frame[iFrame] + iChannelRate;
      if(buffer_frame[iFrame+1] > maxBuffer)
        buffer_frame[iFrame+1] = maxBuffer;
    }
    actualBuffer = (maxBuffer - minB);

    /* To calculate initial buffer Fullness */
    InitFullness = Bit_Buffer[0];
    buffer_frame[0] = InitFullness;
    for(iFrame=0; iFrame < FrameIndex+1; iFrame++) 
    {
      buffer_frame[iFrame] = buffer_frame[iFrame] - Bit_Buffer[iFrame];
      if(buffer_frame[iFrame] < 0) {
        InitFullness -= buffer_frame[iFrame];
        buffer_frame[iFrame] = 0;
      }
      buffer_frame[iFrame+1] = buffer_frame[iFrame] + iChannelRate;
      if(buffer_frame[iFrame+1] > actualBuffer)
        break;
    }       
    Bmin[iBucket] = (unsigned long) actualBuffer;
    Fmin[iBucket] = (unsigned long) InitFullness;
  }

  write_buffer(NumberLeakyBuckets, Rmin, Bmin, Fmin);

  free(buffer_frame);
  free(Rmin);
  free(Bmin);
  free(Fmin);
  return;
}
