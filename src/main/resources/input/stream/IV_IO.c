/*  IO.c  */

//#include "../IV.h" aidb
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
/*  IV.h  */

//#include "../cfiles.h"

/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------------
   IV -- integer vector object
 
   size    -- size of the vector
   maxsize -- maximum size of the vector
   owned   -- owner flag
      when == 1, storage pointed to by entries
      has been allocated here and can be free'd.
      when == 0, storage pointed to by entries
      has not been allocated here and cannot be free'd.
   vec -- pointer to base address
   ---------------------------------------------------------
*/

/*  IV.h  */

/*--------------------------------------------------------------------*/
/*
   -------------------------------------------------------
   purpose -- given the pair of arrays (x1[],y1[]), 
              create a pair of arrays (x2[],y2[]) whose
              entries are pairwise chosen from (x1[],y1[])
              and whose distribution is an approximation.

   return value -- the size of the (x2[],y2[]) arrays
   -------------------------------------------------------
*/
int
IVcompress ( 
   int   size1, 
   int   x1[], 
   int   y1[],
   int   size2,  
   int   x2[],  
   int   y2[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -------------------------------
   purpose -- to copy y[*] := x[*]
   -------------------------------
*/
void
IVcopy ( 
   int   size, 
   int   y[], 
   int   x[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -----------------------------------------------
   purpose -- to fill a int vector with a value
   -----------------------------------------------
*/
void
IVfill ( 
   int   size, 
   int   y[], 
   int   ival 
) ;
/*--------------------------------------------------------------------*/
/*
   ------------------------------------
   purpose -- to print out a int vector
   ------------------------------------
*/
void
IVfprintf ( 
   FILE   *fp, 
   int    size, 
   int    y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -------------------------------------------------------------------
   purpose -- to write out an integer vector with eighty column lines
 
   input --
 
      fp     -- file pointer, must be formatted and write access
      size   -- length of the vector
      y      -- integer vector
      column -- present column
      pierr  -- pointer to int to hold return value, 
                should be 1 if any print was successful,
                if fprintf() failed, then ierr = -1
  
   return value -- present column
 
   created -- 95sep22, cca
   mods    -- 95sep29, cca
      added ierr argument to handle error returns from fprintf()
   -------------------------------------------------------------------
*/
int
IVfp80 ( 
   FILE   *fp, 
   int    size, 
   int    y[], 
   int    column,
   int    *pierr
) ;
/*--------------------------------------------------------------------*/
/*
   ----------------------------------------------------------
   purpose -- to free the storage taken by an integer vector.
              note : ivec must have been returned by IVinit
   ----------------------------------------------------------
*/
void
IVfree ( 
   int y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -----------------------------------
   purpose -- to read in an int vector
   -----------------------------------
*/
int
IVfscanf ( 
   FILE   *fp, 
   int    size, 
   int    y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   ---------------------------------------
   purpose -- to gather y[*] = x[index[*]]
   ---------------------------------------
*/
void
IVgather ( 
   int   size, 
   int   y[], 
   int   x[], 
   int   index[] 
) ;
/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------
   purpose -- allocate a int array with size entries
              and fill with value dval
   
   return value -- a pointer to the start of the array
 
   created : 95sep22, cca
   ---------------------------------------------------
*/
int *
IVinit ( 
   int   size, 
   int   ival 
) ;
/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------
   purpose -- allocate a int array with size entries
   
   return value -- a pointer to the start of the array
 
   created : 95sep22, cca
   ---------------------------------------------------
*/
int *
IVinit2 ( 
   int   size 
) ;
/*--------------------------------------------------------------------*/
/*
   -------------------------------------------------------------
   purpose -- allocate an int array and fill with the inverse of
              y[]. note, y[] must be a permutation vector.

   created : 95sep22, cca
   -------------------------------------------------------------
*/
int *
IVinverse ( 
   int   size, 
   int   y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   ------------------------------
   purpose -- to permute a vector
              y[index[*]] := y[*]
   ------------------------------
*/
void
IVinvPerm ( 
   int   size, 
   int   y[], 
   int   index[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -----------------------------------------------------
   purpose -- to return the first entry of maximum size,
              *ploc contains its index 
   -----------------------------------------------------
*/
int
IVmax ( 
   int   size, 
   int   y[], 
   int   *ploc 
) ;
/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------------------
   purpose -- to return the first entry of maximum absolute value,
              *ploc contains its index
 
   created -- 95sep22, cca
   ---------------------------------------------------------------
*/
int
IVmaxabs (
   int   size,
   int   y[],
   int   *ploc
) ;
/*--------------------------------------------------------------------*/
/*
   ------------------------------------------------------
   purpose -- to return the first entry of minimum value,
              *ploc contains its index
 
   created -- 95sep22, cca
   ------------------------------------------------------
*/
int
IVmin (
   int   size,
   int   y[],
   int   *ploc
) ;
/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------------------
   purpose -- to return the first entry of minimum absolute value,
              *ploc contains its index
 
   created -- 95sep22, cca
   ---------------------------------------------------------------
*/
int
IVminabs (
   int   size,
   int   y[],
   int   *ploc
) ;
/*--------------------------------------------------------------------*/
/*
   ------------------------------
   purpose -- to permute a vector
              y[*] := y[index[*]]
   ------------------------------
*/
void
IVperm ( 
   int   size, 
   int   y[], 
   int   index[] 
) ;
/*--------------------------------------------------------------------*/
/*
   ----------------------------------------------------
   purpose -- to fill a int vector with a ramp function
   ----------------------------------------------------
*/
void
IVramp ( 
   int   size, 
   int   y[], 
   int   start, 
   int   inc 
) ;
/*--------------------------------------------------------------------*/
/*
   ----------------------------------------
   purpose -- to scatter y[index[*]] = x[*]
   ----------------------------------------
*/
void
IVscatter ( 
   int   size, 
   int   y[], 
   int   index[], 
   int   x[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -----------------------------------------------
   purpose -- to return the sum of a int vector
   -----------------------------------------------
*/
int
IVsum ( 
   int   size, 
   int   y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------
   purpose -- to return the sum of the absolute values 
              of the entries in an int vector
   ---------------------------------------------------
*/
int
IVsumabs ( 
   int   size, 
   int   y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   --------------------------------
   purpose -- to swap y[*] and x[*]
   --------------------------------
*/
void
IVswap ( 
   int   size, 
   int   y[], 
   int   x[] 
) ;
/*--------------------------------------------------------------------*/
/*
   ----------------------------------
   purpose -- to zero a int vector
   ----------------------------------
*/
void
IVzero ( 
   int   size, 
   int   y[] 
) ;
/*--------------------------------------------------------------------*/
/*
   -------------------------------------------------
   purpose -- to permute an integer vector,
              procedure uses srand48() and drand48()

   input --

      size -- size of the vector
      ivec -- vector to be permuted
      seed -- seed for the random number generator
              if seed <= 0, simple return
   -------------------------------------------------
*/
void
IVshuffle ( 
   int   size, 
   int   y[], 
   int   seed 
) ;
/*--------------------------------------------------------------------*/
/*
   ------------------------------------------------------
   locate an instance of target in the vector ivec[size].
   we assume that ivec[] is sorted in ascending order
   so we can use binary search to locate target.
 
   return value --
      -1  -- if target not found in ivec[]
      loc -- where target = ivec[loc]
 
   created -- 96may27, cca
   ------------------------------------------------------
*/
int
IVlocateViaBinarySearch (
   int   size,
   int   ivec[],
   int   target
) ;
/*--------------------------------------------------------------------*/


typedef struct _IV   IV ;
struct _IV {
   int   size    ;
   int   maxsize ;
   int   owned   ;
   int   *vec    ;
} ;
/*--------------------------------------------------------------------*/
/*
------------------------------------------------------------------------
----- methods found in basics.c ----------------------------------------
------------------------------------------------------------------------
*/
/*
   -----------------------
   constructor method

   created -- 95oct06, cca
   -----------------------
*/
IV *
IV_new ( 
   void 
) ;
/*
   -----------------------
   set the default fields

   created -- 95oct06, cca
   -----------------------
*/
void
IV_setDefaultFields ( 
   IV   *iv
) ;
/*
   -----------------------
   clear the data fields

   created -- 95oct06, cca
   -----------------------
*/
void
IV_clearData ( 
   IV   *iv
) ;
/*
   -----------------------
   destructor

   created -- 95oct06, cca
   -----------------------
*/
void
IV_free ( 
   IV   *iv
) ;
/*--------------------------------------------------------------------*/
/*
------------------------------------------------------------------------
----- methods found in init.c ----------------------------------------
------------------------------------------------------------------------
*/
/*
   ---------------------------------------------
   simplest initialization method
 
   if entries != NULL
      the object does not own the entries,
      it just points to the entries base address
   else if size > 0
      the object will own the entries,
      it allocates a vector of size int's.
   else
      nothing happens
   endif
 
   created -- 96aug28, cca
   ---------------------------------------------
*/
void
IV_init (
   IV    *iv,
   int   size,
   int   *entries 
) ;
/*
   -------------------------
   basic initializion method
 
   created -- 95oct06, cca
   -------------------------
*/
void
IV_init1 ( 
   IV    *iv,
   int   Maxsize
) ;
/*
   -------------------------
   total initializion method
 
   created -- 95oct06, cca
   -------------------------
*/
void
IV_init2 ( 
   IV    *iv,
   int   size, 
   int   maxsize, 
   int   owned, 
   int   *vec 
) ;
/*
   ----------------------------------
   set the maximum size of the vector
 
   created -- 96dec08, cca
   ----------------------------------
*/
void
IV_setMaxsize (
   IV    *iv,
   int   newmaxsize
) ;
/*
   --------------------------
   set the size of the vector
 
   created -- 96dec08, cca
   --------------------------
*/
void
IV_setSize (
   IV    *iv,
   int   newsize
) ;
/*--------------------------------------------------------------------*/
/*
------------------------------------------------------------------------
----- methods found in instance.c --------------------------------------
------------------------------------------------------------------------
*/
/*
   ---------------------------------------------------------------
   return value is 0 if the entries are not owned by the object
   otherwise the return value is the number of entries at the base
   storage of the vector
 
   created  -- 96jun22, cca
   modified -- 96aug28, cca
   ---------------------------------------------------------------
*/
int
IV_owned (
   IV   *iv
) ;
/*
   ----------------------------------
   return the vector size and entries

   created -- 95oct06, cca
   ----------------------------------
*/
int
IV_size (
   IV   *iv
) ;
/*
   -------------------------------------
   return the maximum size of the vector
 
   created -- 95dec08, cca
   -------------------------------------
*/
int
IV_maxsize (
   IV   *iv
) ;
/*
   ------------------------------------------------
   return the loc'th entry of a vector.
   note: if loc is out of range then -1 is returned

   created -- 96jun29, cca
   ------------------------------------------------
*/
int 
IV_entry (
   IV    *iv,
   int   loc
) ;
/*
   ----------------------------------------------
   return a pointer to the object's entries array
 
   created -- 95oct06, cca
   ----------------------------------------------
*/
int *
IV_entries (
   IV   *iv
) ;
/*
   --------------------------------------------
   fill *psize with the vector's size
   and *pentries with the address of the vector
 
   created -- 95oct06, cca
   --------------------------------------------
*/
void
IV_sizeAndEntries (
   IV    *iv,
   int   *psize,
   int   **pentries
) ;
/*
   --------------------------
   set the size of the vector
 
   created -- 96jun22, cca
   --------------------------
*/
void
IV_setSize ( 
   IV    *iv,
   int   Size
) ;
/*
   ---------------------------
   set and entry in the vector
 
   created -- 96jul14, cca
   ---------------------------
*/
void
IV_setEntry ( 
   IV    *iv,
   int   location,
   int   value
) ;
/*--------------------------------------------------------------------*/
/*
------------------------------------------------------------------------
----- methods found in util.c ----------------------------------------
------------------------------------------------------------------------
*/
/*
   -----------------------------------------------------------
   shift the base of the entries and adjust the dimensions
   note: this is a dangerous operation because the iv->vec
   does not point to the base of the entries any longer,
   and thus if the object owns its entries and it is called
   to resize them or to free them, malloc and free will choke.
 
   USE WITH CAUTION!
 
   created -- 96aug25, cca
   modified -- 96aug28, cca
      structure changed
   -----------------------------------------------------------
*/
void
IV_shiftBase (
   IV    *iv,
   int    offset
) ;
/*
   ---------------------------
   push an entry onto the list

   created -- 95oct06, cca
   modified -- 95aug28, cca
      structure has changed
   ---------------------------
*/
void
IV_push (
   IV    *iv,
   int   val
) ;
/*
   ---------------------------
   minimum and maximum entries

   created -- 95oct06, cca
   ---------------------------
*/
int 
IV_min ( 
   IV   *iv
) ;
int 
IV_max ( 
   IV   *iv
) ;
int 
IV_sum ( 
   IV   *iv
) ;
/*
   -------------------------------------------------------
   sort each index list into ascending or descending order

   created -- 95oct06, cca
   -------------------------------------------------------
*/
void
IV_sortUp ( 
   IV   *iv
) ;
void
IV_sortDown ( 
   IV   *iv
) ;
/*
   -----------------------
   ramp the entries

   created -- 95oct06, cca
   -----------------------
*/
void
IV_ramp (
   IV    *iv,
   int   base,
   int   incr
) ;
/*
   -----------------------
   shuffle the list

   created -- 95oct06, cca
   -----------------------
*/
void
IV_shuffle (
   IV    *iv, 
   int   seed
) ;
/*
   ----------------------------------------------
   return the number of bytes taken by the object

   created -- 95oct06, cca
   ----------------------------------------------
*/
int
IV_sizeOf ( 
   IV   *iv
) ;
/*
   ---------------------------------------------------------------- 
   keep entries that are tagged, move others to end and adjust size

   created -- 95oct06, cca
   ---------------------------------------------------------------- 
*/
void
IV_filterKeep (
   IV    *iv,
   int   tags[],
   int   keepTag
) ;
/*
   --------------------------------------------------------- 
   move purge entries that are tagged to end and adjust size

   created -- 95oct06, cca
   --------------------------------------------------------- 
*/
void
IV_filterPurge (
   IV    *iv,
   int   tags[],
   int   purgeTag
) ;
/*
   --------------------------------------------
   iterator :
   return the pointer to the head of the vector

   created -- 95oct06, cca
   --------------------------------------------
*/
int *
IV_first (
   IV   *iv
) ;
/*
   -----------------------------------------------------
   iterator :
   return the pointer to the next location in the vector

   created -- 95oct06, cca
   -----------------------------------------------------
*/
int *
IV_next (
   IV    *iv,
   int   *pi
) ;
/*
   --------------------------
   fill a vector with a value
 
   created -- 96jun22, cca
   --------------------------
*/
void
IV_fill (
   IV    *iv,
   int   value
) ;
/*
   --------------------------------------
   copy entries from iv2 into iv1.
   note: this is a "mapped" copy, 
   iv1 and iv2 need not be the same size.
 
   created -- 96aug31, cca
   --------------------------------------
*/
void
IV_copy (
   IV   *iv1,
   IV   *iv2
) ;
/*
   --------------------------------------------------
   increment the loc'th location of the vector by one
   and return the new value
 
   created -- 96dec18, cca
   --------------------------------------------------
*/
int
IV_increment (
   IV    *iv,
   int   loc
) ;
/*
   --------------------------------------------------
   decrement the loc'th location of the vector by one
   and return the new value
 
   created -- 96dec18, cca
   --------------------------------------------------
*/
int
IV_decrement (
   IV    *iv,
   int   loc
) ;
/*
   ------------------------------------------------------------
   return the first location in the vector that contains value.
   if value is not present, -1 is returned. cost is linear in 
   the size of the vector

   created -- 96jan15, cca
   ------------------------------------------------------------
*/
int
IV_findValue (
   IV   *iv,
   int  value
) ;
/*
   ---------------------------------------------------------------
   return a location in the vector that contains value.
   the entries in the vector are assumed to be in ascending order.
   if value is not present, -1 is returned. cost is logarithmic in 
   the size of the vector

   created -- 96jan15, cca
   ---------------------------------------------------------------
*/
int
IV_findValueAscending (
   IV   *iv,
   int  value
) ;
/*
   ----------------------------------------------------------------
   return a location in the vector that contains value.
   the entries in the vector are assumed to be in descending order.
   if value is not present, -1 is returned. cost is logarithmic in 
   the size of the vector

   created -- 96jan15, cca
   ----------------------------------------------------------------
*/
int
IV_findValueDescending (
   IV   *iv,
   int  value
) ;
/*
   ---------------------------------------------------
   purpose -- return invlistIV, an IV object
              that contains the inverse map,
              i.e., invlist[list[ii]] = ii.
              other entries of invlist[] are -1.
              all entris in listIV must be nonnegative
 
   created -- 98aug12, cca
   ---------------------------------------------------
*/
IV *
IV_inverseMap (
   IV   *listIV
) ;
/*
   -----------------------------------------------------------
   purpose -- return an IV object that contains the locations 
              of all instances of target in listIV. this is 
              used when listIV is a map from [0,n) to a finite
              set (like processors) and we want to find all
              entries that are mapped to a specific processor.
 
   created -- 98aug12, cca
   -----------------------------------------------------------
*/
IV *
IV_targetEntries (
   IV   *listIV,
   int  target
) ;
/*--------------------------------------------------------------------*/
/*
------------------------------------------------------------------------
----- methods found in IO.c --------------------------------------------
------------------------------------------------------------------------
*/
/*
   ----------------------------------------------
   purpose -- to read in an IV object from a file

   input --

      fn -- filename, must be *.ivb or *.ivf

   return value -- 1 if success, 0 if failure

   created -- 95oct06, cca
   ----------------------------------------------
*/
int
IV_readFromFile ( 
   IV    *iv, 
   char   *fn 
) ;
/*
   -----------------------------------------------------
   purpose -- to read an IV object from a formatted file

   return value -- 1 if success, 0 if failure

   created -- 95oct06, cca
   -----------------------------------------------------
*/
int
IV_readFromFormattedFile ( 
   IV    *iv, 
   FILE   *fp 
) ;
/*
   ---------------------------------------------------
   purpose -- to read an IV object from a binary file

   return value -- 1 if success, 0  if failure

   created -- 95oct06, cca
   ---------------------------------------------------
*/
int
IV_readFromBinaryFile ( 
   IV    *iv, 
   FILE   *fp 
) ;
/*
   -------------------------------------------
   purpose -- to write an IV object to a file

   input --

      fn -- filename
        *.ivb -- binary
        *.ivf -- formatted
        anything else -- for human eye

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   -------------------------------------------
*/
int
IV_writeToFile ( 
   IV    *iv, 
   char   *fn 
) ;
/*
   -----------------------------------------------------
   purpose -- to write an IV object to a formatted file

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   -----------------------------------------------------
*/
int
IV_writeToFormattedFile ( 
   IV    *iv, 
   FILE   *fp 
) ;
/*
   --------------------------------------------------
   purpose -- to write an IV object to a binary file

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   --------------------------------------------------
*/
int
IV_writeToBinaryFile ( 
   IV    *iv, 
   FILE   *fp 
) ;
/*
   -------------------------------------------------
   purpose -- to write an IV object for a human eye

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   -------------------------------------------------
*/
int
IV_writeForHumanEye ( 
   IV    *iv, 
   FILE   *fp 
) ;
/*
   ---------------------------------------------------------
   purpose -- to write out the statistics for the IV object

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   ---------------------------------------------------------
*/
int
IV_writeStats ( 
   IV    *iv, 
   FILE   *fp 
) ;
/*
   -------------------------------------------------------------------
   purpose -- to write out an integer vector with eighty column lines

   input --
 
      fp     -- file pointer, must be formatted and write access
      column -- present column
      pierr  -- pointer to int to hold return value, 
                should be 1 if any print was successful,
                if fprintf() failed, then ierr = -1
  
   return value -- present column
 
   created -- 96jun22, cca
   -------------------------------------------------------------------
*/
int
IV_fp80 (
   IV     *iv,
   FILE   *fp,
   int    column,
   int    *pierr
) ;
/*
   ---------------------------------------------------
   purpose -- to write the IV object for a matlab file
 
   return value -- 1 if success, 0 otherwise
 
   created -- 98oct22, cca
   ---------------------------------------------------
*/
int
IV_writeForMatlab ( 
   IV     *iv, 
   char   *name,
   FILE   *fp 
) ;
/*--------------------------------------------------------------------*/
static const char *suffixb = ".ivb" ;
static const char *suffixf = ".ivf" ;

/*--------------------------------------------------------------------*/
/*
   ----------------------------------------------
   purpose -- to read in an IV object from a file

   input --

      fn -- filename, must be *.ivb or *.ivf

   return value -- 1 if success, 0 if failure

   created -- 95oct06, cca
   ----------------------------------------------
*/
int
IV_readFromFile ( 
   IV    *iv, 
   char   *fn 
) {
FILE   *fp ;
int    fnlength, rc, sulength ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fn == NULL ) {
   fprintf(stderr, 
    "\n error in IV_readFromFile(%p,%s), file %s, line %d"
    "\n bad input\n", iv, fn, __FILE__, __LINE__) ;
   return(0) ;
}
/*
   -------------
   read the file
   -------------
*/
fnlength = strlen(fn) ;
sulength = strlen(suffixb) ;
if ( fnlength > sulength ) {
   if ( strcmp(&fn[fnlength-sulength], suffixb) == 0 ) {
	fp = fopen(fn, "rb");
      if ( fp == NULL ) {
         fprintf(stderr, "\n error in IV_readFromFile(%p,%s)"
                 "\n unable to open file %s", iv, fn, fn) ;
         rc = 0 ;
      } else {
         rc = IV_readFromBinaryFile(iv, fp) ;
         fclose(fp) ;
      }
   } else if ( strcmp(&fn[fnlength-sulength], suffixf) == 0 ) {
	fp = fopen(fn, "r");
      if ( fp == NULL ) {
         fprintf(stderr, "\n error in IV_readFromFile(%p,%s)"
                 "\n unable to open file %s", iv, fn, fn) ;
         rc = 0 ;
      } else {
         rc = IV_readFromFormattedFile(iv, fp) ;
         fclose(fp) ;
      }
   } else {
      fprintf(stderr, "\n error in IV_readFromFile(%p,%s)"
              "\n bad IV file name %s,"
              "\n must end in %s (binary) or %s (formatted)\n",
              iv, fn, fn, suffixb, suffixf) ;
      rc = 0 ;
   }
} else {
   fprintf(stderr, "\n error in IV_readFromFile(%p,%s)"
       "\n bad IV file name %s,"
       "\n must end in %s (binary) or %s (formatted)\n",
       iv, fn, fn, suffixb, suffixf) ;
   rc = 0 ;
}
return(rc) ; }

/*--------------------------------------------------------------------*/
/*
   -----------------------------------------------------
   purpose -- to read an IV object from a formatted file

   return value -- 1 if success, 0 if failure

   created -- 95oct06, cca
   -----------------------------------------------------
*/
int
IV_readFromFormattedFile ( 
   IV    *iv, 
   FILE   *fp 
) {
int   rc, size ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL ) {
   fprintf(stderr, "\n error in IV_readFromFormattedFile(%p,%p)"
           "\n bad input\n", iv, fp) ;
   return(0) ;
}
IV_clearData(iv) ;
/*
   ------------------------------
   read in the size of the vector
   ------------------------------
*/
if ( (rc = fscanf(fp, "%d", &size)) != 1 ) {
   fprintf(stderr, "\n error in IV_readFromFormattedFile(%p,%p)"
           "\n %d items of %d read\n", iv, fp, rc, 1) ;
   return(0) ;
}
/*
   ---------------------
   initialize the object
   ---------------------
*/
IV_init(iv, size, NULL) ;
iv->size = size ;
/*
   ------------------------
   read in the vec[] vector
   ------------------------
*/
if ( (rc = IVfscanf(fp, size, iv->vec)) != size ) {
   fprintf(stderr, "\n error in IV_readFromFormattedFile(%p,%p)"
           "\n %d items of %d read\n", iv, fp, rc, size) ;
   return(0) ;
}
return(1) ; }

/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------
   purpose -- to read an IV object from a binary file

   return value -- 1 if success, 0  if failure

   created -- 95oct06, cca
   ---------------------------------------------------
*/
int
IV_readFromBinaryFile ( 
   IV    *iv, 
   FILE   *fp 
) {
int   rc, size ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL ) {
   fprintf(stderr, "\n fatal error in IV_readFromBinaryFile(%p,%p)"
           "\n bad input\n", iv, fp) ;
   return(0) ;
}
IV_clearData(iv) ;
/*
   ------------------------------
   read in the size of the vector
   ------------------------------
*/
if ( (rc = fread((void *) &size, sizeof(int), 1, fp)) != 1 ) {
   fprintf(stderr, "\n error in IV_readFromBinaryFile(%p,%p)"
           "\n itemp(3) : %d items of %d read\n", iv, fp, rc, 1) ;
   return(0) ;
}
/*
   ---------------------
   initialize the object
   ---------------------
*/
IV_init(iv, size, NULL) ;
iv->size = size ;
/*
   ------------------------
   read in the vec[] vector
   ------------------------
*/
if ( (rc = fread((void *) iv->vec, sizeof(int), size, fp)) != size ) {
   fprintf(stderr, "\n error in IV_readFromBinaryFile(%p,%p)"
           "\n sizes(%d) : %d items of %d read\n", 
           iv, fp, size, rc, size) ;
   return(0) ;
}
return(1) ; }

/*--------------------------------------------------------------------*/
/*
   -------------------------------------------
   purpose -- to write an IV object to a file

   input --

      fn -- filename
        *.ivb -- binary
        *.ivf -- formatted
        anything else -- for human eye

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   -------------------------------------------
*/
int
IV_writeToFile ( 
   IV    *iv, 
   char   *fn 
) {
FILE   *fp ;
int    fnlength, rc, sulength ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fn == NULL ) {
   fprintf(stderr, "\n fatal error in IV_writeToFile(%p,%s)"
    "\n bad input\n", iv, fn) ; 
}
/*
   ------------------
   write out the file
   ------------------
*/
fnlength = strlen(fn) ;
sulength = strlen(suffixb) ;
if ( fnlength > sulength ) {
   if ( strcmp(&fn[fnlength-sulength], suffixb) == 0 ) {
	fp = fopen(fn, "wb");
      if ( fp == NULL ) {
         fprintf(stderr, "\n error in IV_writeToFile(%p,%s)"
                 "\n unable to open file %s", iv, fn, fn) ;
         rc = 0 ;
      } else {
         rc = IV_writeToBinaryFile(iv, fp) ;
         fclose(fp) ;
      }
   } else if ( strcmp(&fn[fnlength-sulength], suffixf) == 0 ) {
	fp = fopen(fn, "w");
      if ( fp == NULL ) {
         fprintf(stderr, "\n error in IV_writeToFile(%p,%s)"
                 "\n unable to open file %s", iv, fn, fn) ;
         rc = 0 ;
      } else {
         rc = IV_writeToFormattedFile(iv, fp) ;
         fclose(fp) ;
      }
   } else {
	fp = fopen(fn, "a");
      if ( fp == NULL ) {
         fprintf(stderr, "\n error in IV_writeToFile(%p,%s)"
                 "\n unable to open file %s", iv, fn, fn) ;
         rc = 0 ;
      } else {
         rc = IV_writeForHumanEye(iv, fp) ;
         fclose(fp) ;
      }
   }
} else {
	fp = fopen(fn, "a");
   if ( fp == NULL ) {
      fprintf(stderr, "\n error in IV_writeToFile(%p,%s)"
              "\n unable to open file %s", iv, fn, fn) ;
      rc = 0 ;
   } else {
      rc = IV_writeForHumanEye(iv, fp) ;
      fclose(fp) ;
   }
}
return(rc) ; }

/*--------------------------------------------------------------------*/
/*
   -----------------------------------------------------
   purpose -- to write an IV object to a formatted file

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   -----------------------------------------------------
*/
int
IV_writeToFormattedFile ( 
   IV    *iv, 
   FILE   *fp 
) {
int   ierr, rc ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL || iv->size <= 0 ) {
   fprintf(stderr, "\n fatal error in IV_writeToFormattedFile(%p,%p)"
           "\n bad input\n", iv, fp) ;
   fprintf(stderr, "\n iv->size = %d", iv->size) ;
   exit(-1) ;
}
/*
   -------------------------------------
   write out the size of the vector
   -------------------------------------
*/
rc = fprintf(fp, "\n %d", iv->size) ;
if ( rc < 0 ) {
   fprintf(stderr, "\n fatal error in IV_writeToFormattedFile(%p,%p)"
           "\n rc = %d, return from first fprintf\n", iv, fp, rc) ;
   return(0) ;
}
if ( iv->size > 0 ) {
   IVfp80(fp, iv->size, iv->vec, 80, &ierr) ;
   if ( ierr < 0 ) {
      fprintf(stderr, 
              "\n fatal error in IV_writeToFormattedFile(%p,%p)"
              "\n ierr = %d, return from sizes[] IVfp80\n", 
              iv, fp, ierr) ;
      return(0) ;
   }
}

return(1) ; }

/*--------------------------------------------------------------------*/
/*
   --------------------------------------------------
   purpose -- to write an IV object to a binary file

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   --------------------------------------------------
*/
int
IV_writeToBinaryFile ( 
   IV    *iv, 
   FILE   *fp 
) {
int   rc ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL || iv->size <= 0 ) {
   fprintf(stderr, "\n fatal error in IV_writeToBinaryFile(%p,%p)"
           "\n bad input\n", iv, fp) ;
   exit(-1) ;
}
rc = fwrite((void *) &iv->size, sizeof(int), 1, fp) ;
if ( rc != 1 ) {
   fprintf(stderr, "\n error in IV_writeToBinaryFile(%p,%p)"
           "\n %d of %d scalar items written\n", iv, fp, rc, 1) ;
   return(0) ;
}
rc = fwrite((void *) iv->vec, sizeof(int), iv->size, fp) ;
if ( rc != iv->size ) {
   fprintf(stderr, "\n error in IV_writeToBinaryFile(%p,%p)"
           "\n iv->sizes, %d of %d items written\n",
           iv, fp, rc, iv->size) ;
   return(0) ;
}
return(1) ; }

/*--------------------------------------------------------------------*/
/*
   -------------------------------------------------
   purpose -- to write an IV object for a human eye

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   -------------------------------------------------
*/
int
IV_writeForHumanEye ( 
   IV    *iv, 
   FILE   *fp 
) {
int   ierr, rc ;

if ( iv == NULL || fp == NULL ) {
   fprintf(stderr, "\n fatal error in IV_writeForHumanEye(%p,%p)"
           "\n bad input\n", iv, fp) ;
   exit(-1) ;
}
if ( (rc = IV_writeStats(iv, fp)) == 0 ) {
   fprintf(stderr, "\n fatal error in IV_writeForHumanEye(%p,%p)"
           "\n rc = %d, return from IV_writeStats(%p,%p)\n",
           iv, fp, rc, iv, fp) ;
   return(0) ;
}
IVfp80(fp, iv->size, iv->vec, 80, &ierr) ;

return(1) ; }

/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------------
   purpose -- to write out the statistics for the IV object

   return value -- 1 if success, 0 otherwise

   created -- 95oct06, cca
   ---------------------------------------------------------
*/
int
IV_writeStats ( 
   IV    *iv, 
   FILE   *fp 
) {
int   rc ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL ) {
   fprintf(stderr, "\n error in IV_writeStats(%p,%p)"
           "\n bad input\n", iv, fp) ;
   exit(-1) ;
}
rc = fprintf(fp, "\n IV : integer vector object : ") ;
if ( rc < 0 ) { goto IO_error ; }
rc = fprintf(fp, " size = %d, maxsize = %d, owned = %d", 
             iv->size, iv->maxsize, iv->owned) ;
if ( rc < 0 ) { goto IO_error ; }
return(1) ;

IO_error :
   fprintf(stderr, "\n fatal error in IV_writeStats(%p,%p)"
           "\n rc = %d, return from fprintf\n", iv, fp, rc) ;
   return(0) ;
}

/*--------------------------------------------------------------------*/
/*
   -------------------------------------------------------------------
  purpose -- to write out an integer vector with eighty column lines
 
   input --
 
      fp     -- file pointer, must be formatted and write access
      column -- present column
      pierr  -- pointer to int to hold return value, 
                should be 1 if any print was successful,
                if fprintf() failed, then ierr = -1
  
   return value -- present column
 
   created -- 96jun22, cca
   -------------------------------------------------------------------
*/
int
IV_fp80 (
   IV     *iv,
   FILE   *fp,
   int    column,
   int    *pierr
) {
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL || pierr == NULL ) {
   fprintf(stderr, "\n fatal error in IV_fp80(%p,%p,%p)"
           "\n bad input\n", iv, fp, pierr) ;
   exit(-1) ;
}
if ( iv->size > 0 && iv->vec != NULL ) {
   column = IVfp80(fp, iv->size, iv->vec, column, pierr) ;
}

return(column) ; }

/*--------------------------------------------------------------------*/
/*
   ---------------------------------------------------
   purpose -- to write the IV object for a matlab file
 
   return value -- 1 if success, 0 otherwise
 
   created -- 98oct22, cca
   ---------------------------------------------------
*/
int
IV_writeForMatlab ( 
   IV     *iv, 
   char   *name,
   FILE   *fp 
) {
int   ii, rc, size ;
int   *entries ;
/*
   ---------------
   check the input
   ---------------
*/
if ( iv == NULL || fp == NULL ) {
   fprintf(stderr, "\n error in IV_writeForMatlab(%p,%p,%p)"
           "\n bad input\n", iv, name, fp) ;
   exit(-1) ;
}
IV_sizeAndEntries(iv, &size, &entries) ;
fprintf(fp, "\n %s = zeros(%d,%d) ;", name, size, size) ;
for ( ii = 0 ; ii < size ; ii++ ) {
   fprintf(fp, "\n %s(%d) = %d ;", name, ii+1, entries[ii]+1) ;
}
return(1) ; }
 
/*--------------------------------------------------------------------*/
int main(){
return 0;
}
