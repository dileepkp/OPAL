/* ====================================================================
 * Copyright (c) 1999-2001 Carnegie Mellon University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * This work was supported in part by funding from the Defense Advanced 
 * Research Projects Agency and the National Science Foundation of the 
 * United States of America, and the CMU Sphinx Speech Consortium.
 *
 * THIS SOFTWARE IS PROVIDED BY CARNEGIE MELLON UNIVERSITY ``AS IS'' AND 
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 *
 */
/*
 * profile.c -- For timing and event counting.
 *
 * **********************************************
 * CMU ARPA Speech Project
 *
 * Copyright (c) 1999 Carnegie Mellon University.
 * ALL RIGHTS RESERVED.
 * **********************************************
 * 
 * HISTORY
 * 
 * 11-Mar-1999	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon University
 * 		Added ptmr_init().
 * 
 * 19-Jun-97	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon University
 * 		Created.
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#if (! SPEC_CPU_WINDOWS)
#include <unistd.h>
#include <sys/time.h>
#include <sys/resource.h>
#else
#include <windows.h>
#include <time.h>
#endif

//#include "profile.h"
//#include "err.h"
/* ====================================================================
 * Copyright (c) 1999-2001 Carnegie Mellon University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * This work was supported in part by funding from the Defense Advanced 
 * Research Projects Agency and the National Science Foundation of the 
 * United States of America, and the CMU Sphinx Speech Consortium.
 *
 * THIS SOFTWARE IS PROVIDED BY CARNEGIE MELLON UNIVERSITY ``AS IS'' AND 
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 *
 */
/*
 * prim_type.h -- Primitive types; more machine-independent.
 *
 * **********************************************
 * CMU ARPA Speech Project
 *
 * Copyright (c) 1999 Carnegie Mellon University.
 * ALL RIGHTS RESERVED.
 * **********************************************
 * 
 * HISTORY
 * 
 * 12-Mar-1999	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon
 * 		Added arraysize_t, point_t, fpoint_t.
 * 
 * 01-Feb-1999	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon
 * 		Added anytype_t.
 * 
 * 08-31-95	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon
 * 		Created.
 */


#ifndef _LIBUTIL_PRIM_TYPE_H_
#define _LIBUTIL_PRIM_TYPE_H_


typedef int		int32;
typedef short		int16;
typedef char		int8;
typedef unsigned int	uint32;
typedef unsigned short	uint16;
typedef unsigned char	uint8;
typedef float		float32;
typedef double		float64;

typedef union anytype_s {
    void *ptr;		/* User defined data types at this ptr */
    int32 int32;
    uint32 uint32;
    float32 float32;
    float64 float64;
} anytype_t;


/* Useful constants */
#define MAX_INT32		((int32) 0x7fffffff)
#define MAX_INT16		((int16) 0x00007fff)
#define MAX_INT8		((int8)  0x0000007f)

#define MAX_NEG_INT32		((int32) 0x80000000)
#define MAX_NEG_INT16		((int16) 0xffff8000)
#define MAX_NEG_INT8		((int8)  0xffffff80)

#define MAX_UINT32		((uint32) 0xffffffff)
#define MAX_UINT16		((uint16) 0x0000ffff)
#define MAX_UINT8		((uint8)  0x000000ff)

/* The following are approximate; IEEE floating point standards might quibble! */
#define MAX_POS_FLOAT32		3.4e+38f
#define MIN_POS_FLOAT32		1.2e-38f	/* But not 0 */
#define MAX_POS_FLOAT64		1.8e+307
#define MIN_POS_FLOAT64		2.2e-308

/* Will the following really work?? */
#define MAX_NEG_FLOAT32		((float32) (-MAX_POS_FLOAT32))
#define MIN_NEG_FLOAT32		((float32) (-MIN_POS_FLOAT32))
#define MAX_NEG_FLOAT64		((float64) (-MAX_POS_FLOAT64))
#define MIN_NEG_FLOAT64		((float64) (-MIN_POS_FLOAT64))


#endif

/* ====================================================================
 * Copyright (c) 1999-2001 Carnegie Mellon University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * This work was supported in part by funding from the Defense Advanced 
 * Research Projects Agency and the National Science Foundation of the 
 * United States of America, and the CMU Sphinx Speech Consortium.
 *
 * THIS SOFTWARE IS PROVIDED BY CARNEGIE MELLON UNIVERSITY ``AS IS'' AND 
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 *
 */
/*
 * profile.h -- For timing and event counting.
 *
 * **********************************************
 * CMU ARPA Speech Project
 *
 * Copyright (c) 1999 Carnegie Mellon University.
 * ALL RIGHTS RESERVED.
 * **********************************************
 * 
 * HISTORY
 * 
 * 11-Mar-1999	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon University
 * 		Added ptmr_init().
 * 
 * 19-Jun-97	M K Ravishankar (rkm@cs.cmu.edu) at Carnegie Mellon University
 * 		Created from earlier Sphinx-3 version.
 */


#ifndef _LIBUTIL_PROFILE_H_
#define _LIBUTIL_PROFILE_H_


//#include "prim_type.h"


/*
 * Generic event counter for profiling.  User is responsible for allocating an array
 * of the desired number.  There should be a sentinel with name = NULL.
 */
typedef struct {
    const char *name;		/* Counter print name; NULL terminates array of counters.
				   Used by pctr_print_all */
    int32 count;		/* Counter value */
} pctr_t;


void pctr_reset (pctr_t *ctr);
void pctr_reset_all (pctr_t *ctr);
void pctr_print_all (FILE *fp, pctr_t *ctr);


/*
 * Generic timer structures and functions for coarse-grained performance measurements
 * using standard system calls.
 */
typedef struct {
    const char *name;		/* Timer print name; NULL terminates an array of timers.
				   Used by ptmr_print_all */
    float64 t_cpu;		/* CPU time accumulated since most recent reset op */
    float64 t_elapsed;		/* Elapsed time accumulated since most recent reset */
    float64 t_tot_cpu;		/* Total CPU time since creation */
    float64 t_tot_elapsed;	/* Total elapsed time since creation */
    float64 start_cpu;		/* ---- FOR INTERNAL USE ONLY ---- */
    float64 start_elapsed;	/* ---- FOR INTERNAL USE ONLY ---- */
} ptmr_t;


/* Start timing using tmr */
void ptmr_start (ptmr_t *tmr);

/* Stop timing and accumulate tmr->{t_cpu, t_elapsed, t_tot_cpu, t_tot_elapsed} */
void ptmr_stop (ptmr_t *tmr);

/* Reset tmr->{t_cpu, t_elapsed} to 0.0 */
void ptmr_reset (ptmr_t *tmr);

/* Reset tmr->{t_cpu, t_elapsed, t_tot_cpu, t_tot_elapsed} to 0.0 */
void ptmr_init (ptmr_t *tmr);


/*
 * Reset t_cpu, t_elapsed of all timer modules in array tmr[] to 0.0.
 * The array should be terminated with a sentinel with .name = NULL.
 */
void ptmr_reset_all (ptmr_t *tmr);

/*
 * Print t_cpu for all timer modules in tmr[], normalized by norm (i.e., t_cpu/norm).
 * The array should be terminated with a sentinel with .name = NULL.
 */
void ptmr_print_all (FILE *fp, ptmr_t *tmr, float64 norm);


/*
 * Return the processor clock speed (in MHz); only available on some machines (Alphas).
 * The dummy argument can be any integer value.
 */
int32 host_pclk (int32 dummy);


/*
 * Check the native byte-ordering of the machine by writing a magic number to a
 * temporary file and reading it back.
 * Return value: 0 if BIG-ENDIAN, 1 if LITTLE-ENDIAN, -1 if error.
 */
int32 host_endian ( void );


#endif

/* ====================================================================
 * Copyright (c) 1999-2001 Carnegie Mellon University.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * This work was supported in part by funding from the Defense Advanced 
 * Research Projects Agency and the National Science Foundation of the 
 * United States of America, and the CMU Sphinx Speech Consortium.
 *
 * THIS SOFTWARE IS PROVIDED BY CARNEGIE MELLON UNIVERSITY ``AS IS'' AND 
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 *
 */
/*
 * err.h -- Package for checking and catching common errors, printing out
 *		errors nicely, etc.
 *
 * **********************************************
 * CMU ARPA Speech Project
 *
 * Copyright (c) 1999 Carnegie Mellon University.
 * ALL RIGHTS RESERVED.
 * **********************************************
 *
 * 6/01/95  Paul Placeway  CMU speech group
 */


#ifndef _LIBUTIL_ERR_H_
#define _LIBUTIL_ERR_H_

/* 01.18.01 RAH, allow for C++ compiles */
#ifdef __cplusplus
extern "C" {
#endif



#include <stdarg.h>
#include <errno.h>

void _E__pr_header( char const *file, long line, char const *msg );
void _E__pr_info_header( char const *file, long line, char const *tag );
void _E__pr_warn( char const *fmt, ... );
void _E__pr_info( char const *fmt, ... );
void _E__die_error( char const *fmt, ... );
void _E__abort_error( char const *fmt, ... );
void _E__sys_error( char const *fmt, ... );
void _E__fatal_sys_error( char const *fmt, ... );

/* These three all abort */

/* core dump after error message */
/* this macro is never used in the code, and conflicts with MS Visual C
#ifndef E_ABORT
#define E_ABORT  _E__pr_header(__FILE__, __LINE__, "ERROR"),_E__abort_error
#endif
*/

/* exit with non-zero status after error message */
#define E_FATAL  _E__pr_header(__FILE__, __LINE__, "FATAL_ERROR"),_E__die_error

/* Print error text; Call perror(""); exit(errno); */
#define E_FATAL_SYSTEM	_E__pr_header(__FILE__, __LINE__, "SYSTEM_ERROR"),_E__fatal_sys_error

/* Print error text; Call perror(""); */
#define E_WARN_SYSTEM	_E__pr_header(__FILE__, __LINE__, "SYSTEM_ERROR"),_E__sys_error

/* Print error text; Call perror(""); */
#define E_ERROR_SYSTEM	_E__pr_header(__FILE__, __LINE__, "SYSTEM_ERROR"),_E__sys_error


/* Print logging information, warnings, or error messages; all to stderr */
#define E_INFO	  _E__pr_info_header(__FILE__, __LINE__, "INFO"),_E__pr_info

#define E_WARN	  _E__pr_header(__FILE__, __LINE__, "WARNING"),_E__pr_warn

#define E_ERROR	  _E__pr_header(__FILE__, __LINE__, "ERROR"),_E__pr_warn


#ifdef __cplusplus
}
#endif


#endif /* !_ERR_H */



#if (__ALPHA_OSF1__)
extern uint32 rpcc( void );	/* On an alpha, use the RPCC instruction */
#endif


void pctr_reset (pctr_t *ctr)
{
    ctr->count = 0;
}


void pctr_reset_all (pctr_t *ctr)
{
    for (; ctr->name; ctr++)
	pctr_reset (ctr);
}


void pctr_print_all (FILE *fp, pctr_t *ctr)
{
    if (! ctr->name)
	return;
    
    fprintf (fp, "CTR:");
    for (; ctr->name; ctr++)
	fprintf (fp, "  %d %s", ctr->count, ctr->name);
    fprintf (fp, "\n");
}


int32 host_pclk (int32 dummy)
{
    int32 mhz = 0;
#if (__ALPHA_OSF1__)
    int32 i, j, k, besti, bestj, diff;
    uint32 rpcc_start, rpcc_end;
    struct rusage start, stop;
    float64 t;
    
    memset (&start, 0, sizeof(struct rusage));
    memset (&stop, 0, sizeof(struct rusage));
    
    getrusage (RUSAGE_SELF, &start);
    rpcc_start = rpcc();
    /* Consume some cpu cycles; dummy to forced compiler not to optimize loop away */
    dummy &= 0x7fffffff;
    dummy |= 0x70000000;
    for (i = 1; i < 100000000; i++)
	if (i > dummy)
	    return (i);
    rpcc_end = rpcc();
    getrusage (RUSAGE_SELF, &stop);
    
    t = (stop.ru_utime.tv_sec - start.ru_utime.tv_sec) + 
	((stop.ru_utime.tv_usec - start.ru_utime.tv_usec) * 0.000001);
    mhz = ((rpcc_end - rpcc_start) / t) * 0.000001 + 0.5;
    diff = (int32)0x7fffffff;
    for (i = 100; i <= 1000; i += 100) {
	for (j = 1; j <= 10; j++) {
	    k = i/j - mhz;
	    if (k < 0)
		k = -k;
	    if (k < diff) {
		diff = k;
		besti = i;
		bestj = j;
	    }
	}
    }
    mhz = besti/bestj;
    E_INFO("%d ticks in %.3f sec; machine clock rate = %d MHz\n",
	   rpcc_end - rpcc_start, t, mhz);
#endif
    
    return mhz;
}


#if (SPEC_CPU_WINDOWS)

#define TM_LOWSCALE	1e-7
#define TM_HIGHSCALE	(4294967296.0 * TM_LOWSCALE);

static float64 make_sec (FILETIME *tm)
{
    float64 dt;
    
    dt = tm->dwLowDateTime * TM_LOWSCALE;
    dt += tm->dwHighDateTime * TM_HIGHSCALE;
    
    return (dt);
}

#else

static float64 make_sec (struct timeval *s)
{
    return (s->tv_sec + s->tv_usec * 0.000001);
}

#endif


void ptmr_start (ptmr_t *tm)
{
#ifndef SPEC_CPU
#if (! SPEC_CPU_WINDOWS)
    struct timeval e_start;	/* Elapsed time */
    
#if (! _HPUX_SOURCE)
    struct rusage start;	/* CPU time */
    
    /* Unix but not HPUX */
    getrusage (RUSAGE_SELF, &start);
    tm->start_cpu = make_sec (&start.ru_utime) + make_sec (&start.ru_stime);
#endif
    /* Unix + HP */
    gettimeofday (&e_start, 0);
    tm->start_elapsed = make_sec (&e_start);
#else
    HANDLE pid;
    FILETIME t_create, t_exit, kst, ust;
    
    /* PC */
    pid = GetCurrentProcess();
    GetProcessTimes (pid, &t_create, &t_exit, &kst, &ust);
    tm->start_cpu = make_sec (&ust) + make_sec (&kst);

    tm->start_elapsed = (float64)clock() / CLOCKS_PER_SEC;
#endif
#endif
}


void ptmr_stop (ptmr_t *tm)
{
#ifndef SPEC_CPU
    float64 dt_cpu, dt_elapsed;
    
#if (! SPEC_CPU_WINDOWS)
    struct timeval e_stop;	/* Elapsed time */
    
#if (! _HPUX_SOURCE)
    struct rusage stop;		/* CPU time */
    
    /* Unix but not HPUX */
    getrusage (RUSAGE_SELF, &stop);
    dt_cpu = make_sec (&stop.ru_utime) + make_sec (&stop.ru_stime) - tm->start_cpu;
#else
    dt_cpu = 0.0;
#endif
    /* Unix + HP */
    gettimeofday (&e_stop, 0);
    dt_elapsed = (make_sec (&e_stop) - tm->start_elapsed);
#else
    HANDLE pid;
    FILETIME t_create, t_exit, kst, ust;
    
    /* PC */
    pid = GetCurrentProcess();
    GetProcessTimes (pid, &t_create, &t_exit, &kst, &ust);
    dt_cpu = make_sec (&ust) + make_sec (&kst) - tm->start_cpu;
    dt_elapsed = ((float64)clock() / CLOCKS_PER_SEC) - tm->start_elapsed;
#endif

    tm->t_cpu += dt_cpu;
    tm->t_elapsed += dt_elapsed;

    tm->t_tot_cpu += dt_cpu;
    tm->t_tot_elapsed += dt_elapsed;
#endif
}


void ptmr_reset (ptmr_t *tm)
{
    tm->t_cpu = 0.0;
    tm->t_elapsed = 0.0;
}


void ptmr_init (ptmr_t *tm)
{
    tm->t_cpu = 0.0;
    tm->t_elapsed = 0.0;
    tm->t_tot_cpu = 0.0;
    tm->t_tot_elapsed = 0.0;
}


void ptmr_reset_all (ptmr_t *tm)
{
    for (; tm->name; tm++)
	ptmr_reset (tm);
}


void ptmr_print_all (FILE *fp, ptmr_t *tm, float64 norm)
{
    if (norm != 0.0) {
	norm = 1.0/norm;
	for (; tm->name; tm++)
	    fprintf (fp, "  %6.2fx %s", tm->t_cpu * norm, tm->name);
    }
}


int32 host_endian ( void )
{
    FILE *fp;
    int32 BYTE_ORDER_MAGIC;
    char *file;
    char buf[8];
    int32 k, endian;
    
    file = "/tmp/__EnDiAn_TeSt__";
	fp = fopen(file, "wb");
    if (fp == NULL) {
	E_ERROR("fopen(%s,wb) failed\n", file);
	return -1;
    }
    
    BYTE_ORDER_MAGIC = (int32)0x11223344;
    
    k = (int32) BYTE_ORDER_MAGIC;
    if (fwrite (&k, sizeof(int32), 1, fp) != 1) {
	E_ERROR("fwrite(%s) failed\n", file);
	fclose (fp);
	unlink(file);
	return -1;
    }
    
    fclose (fp);
	fp = fopen(file, "rb");
    if (fp == NULL) {
	E_ERROR ("fopen(%s,rb) failed\n", file);
	unlink(file);
	return -1;
    }
    if (fread (buf, 1, sizeof(int32), fp) != sizeof(int32)) {
	E_ERROR ("fread(%s) failed\n", file);
	fclose (fp);
	unlink(file);
	return -1;
    }
    fclose (fp);
    unlink(file);
    
    /* If buf[0] == lsB of BYTE_ORDER_MAGIC, we are little-endian */
    endian = (buf[0] == (BYTE_ORDER_MAGIC & 0x000000ff)) ? 1 : 0;
    
    return (endian);
}
