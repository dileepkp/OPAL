/* TEMPLATE GENERATED TESTCASE FILE 
Filename: CWE369_Divide_by_Zero__int_rand_modulo_31.c 
Label Definition File: CWE369_Divide_by_Zero__int.label.xml 
Template File: sources-sinks-31.tmpl.c 
*/
/* 
 * @description 
 * CWE: 369 Divide by Zero 
 * BadSource: rand Set data to result of rand(), which may be zero 
 * GoodSource: Non-zero 
 * Sinks: modulo 
 *    GoodSink: Check for zero before modulo 
 *    BadSink : Modulo a constant with data 
 * Flow Variant: 31 Data flow using a copy of data within the same function 
 * 
 * */
  
#include <stdio.h>
#include <stdlib.h>
  
#ifndef OMITBAD 
  
void CWE369_Divide_by_Zero__int_rand_modulo_31_bad() 
{ 
    int data,dataCopy,data2; 
    /* Initialize data */
    data = -1; 
    /* POTENTIAL FLAW: Set data to a random value */
    data = RAND32(); 
    { 
        dataCopy = data; 
        data2 = dataCopy; 
        /* POTENTIAL FLAW: Possibly divide by zero */
        printIntLine(100 % data2); 
    } 
} 
  
#endif /* OMITBAD */ 
  
#ifndef OMITGOOD 
  
/* goodG2B() uses the GoodSource with the BadSink */
static void goodG2B() 
{ 
    int data,dataCopy,data2;
    /* Initialize data */
    data = -1; 
    /* FIX: Use a value not equal to zero */
    data = 7; 
    { 
        dataCopy = data; 
        data2 = dataCopy; 
        /* POTENTIAL FLAW: Possibly divide by zero */
        printIntLine(100 % data2); 
    } 
} 
  
/* goodB2G() uses the BadSource with the GoodSink */
static void goodB2G() 
{ 
    int data,dataCopy,data2; 
    /* Initialize data */
    data = -1; 
    /* POTENTIAL FLAW: Set data to a random value */
    data = RAND32(); 
    { 
        dataCopy = data; 
        data2 = dataCopy; 
        /* FIX: test for a zero denominator */
        if( data2 != 0 ) 
        { 
            printIntLine(100 % data2); 
        } 
        else
        { 
            printLine("This would result in a divide by zero"); 
        } 
    } 
} 
  
void CWE369_Divide_by_Zero__int_rand_modulo_31_good() 
{ 
    goodG2B(); 
    goodB2G(); 
} 
  
#endif /* OMITGOOD */ 
  
/* Below is the main(). It is only used when building this testcase on 
   its own for testing or for building a binary to use in testing binary 
   analysis tools. It is not used when compiling all the testcases as one 
   application, which is how source code analysis tools are tested. */
#ifdef INCLUDEMAIN 
  
int main(int argc, char * argv[]) 
{ 
    /* seed randomness */
    srand( (unsigned)time(NULL) ); 
#ifndef OMITGOOD 
    printLine("Calling good()..."); 
    CWE369_Divide_by_Zero__int_rand_modulo_31_good(); 
    printLine("Finished good()"); 
#endif /* OMITGOOD */ 
#ifndef OMITBAD 
    printLine("Calling bad()..."); 
    CWE369_Divide_by_Zero__int_rand_modulo_31_bad(); 
    printLine("Finished bad()"); 
#endif /* OMITBAD */ 
    return 0; 
} 
  
#endif 
