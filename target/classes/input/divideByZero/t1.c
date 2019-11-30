/* crypto/evp/e_bf.c */
/* Copyright (C) 1995-1998 Eric Young (eay@cryptsoft.com)
 * All rights reserved.
 *
 * This package is an SSL implementation written
 * by Eric Young (eay@cryptsoft.com).
 * The implementation was written so as to conform with Netscapes SSL.
 * 
 * This library is free for commercial and non-commercial use as long as
 * the following conditions are aheared to.  The following conditions
 * apply to all code found in this distribution, be it the RC4, RSA,
 * lhash, DES, etc., code; not just the SSL code.  The SSL documentation
 * included with this distribution is covered by the same copyright terms
 * except that the holder is Tim Hudson (tjh@cryptsoft.com).
 * 
 * Copyright remains Eric Young's, and as such any Copyright notices in
 * the code are not to be removed.
 * If this package is used in a product, Eric Young should be given attribution
 * as the author of the parts of the library used.
 * This can be in the form of a textual message at program startup or
 * in documentation (online or textual) provided with the package.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes cryptographic software written by
 *     Eric Young (eay@cryptsoft.com)"
 *    The word 'cryptographic' can be left out if the rouines from the library
 *    being used are not cryptographic related :-).
 * 4. If you include any Windows specific code (or a derivative thereof) from 
 *    the apps directory (application code) you must include an acknowledgement:
 *    "This product includes software written by Tim Hudson (tjh@cryptsoft.com)"
 * 
 * THIS SOFTWARE IS PROVIDED BY ERIC YOUNG ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * 
 * The licence and distribution terms for any publically available version or
 * derivative of this code cannot be changed.  i.e. this code cannot simply be
 * copied and put under another distribution licence
 * [including the GNU Public Licence.]
 */
#include <stdio.h>
#include "cryptlib.h"
#ifndef OPENSSL_NO_BF
#include "openssl/evp.h"
#include "evp_locl.h"
#include "openssl/objects.h"
#include "openssl/blowfish.h"
//#include <mongoose.h> 
#include <setjmp.h> 
//#include <stonesoup/stonesoup_trace.h> 
#include <sys/stat.h> 
static int bf_init_key(EVP_CIPHER_CTX *ctx,const unsigned char *key,const unsigned char *iv,int enc);
typedef struct {
BF_KEY ks;}EVP_BF_KEY;
#define data(ctx)	EVP_C_DATA(EVP_BF_KEY,ctx)
int singleton_consound = 0;

union plowrightia_vervain 
{
  char *kendrick_nubilous;
  double suits_chittered;
  char *tenderably_autocarpian;
  char antiempiricist_scarletberry;
  int noropianic_message;
}
;
int stonesoup_global_variable;
void stonesoup_handle_taint(char *pectinately_graphotype);
void* stonesoup_printf_context;
void stonesoup_setup_printf_context() {
}
void stonesoup_printf(char * format, ...) {
    va_list argptr;
    // mg_send_header(stonesoup_printf_context, "Content-Type", "text/plain");
    va_start(argptr, format);
    mg_vprintf_data((struct mg_connection*) stonesoup_printf_context, format, argptr);
    va_end(argptr);
}
void stonesoup_close_printf_context() {
}
static int stonesoup_exit_flag = 0;
static int stonesoup_ev_handler(struct mg_connection *conn, int ev) {
  char * ifmatch_header;
  char* stonesoup_tainted_buff;
  int buffer_size = 1000;
  int data_size = 0;
  if (ev == 1) {
    ifmatch_header = (char*) mg_get_header(conn, "if-match");
    if (strcmp(ifmatch_header, "weak_taint_source_value") == 0) {
        while (1) {
            stonesoup_tainted_buff = (char*) malloc(buffer_size * sizeof(char));
            /* STONESOUP: SOURCE-TAINT (Socket Variable) */
            data_size = mg_get_var(conn, "data", stonesoup_tainted_buff, buffer_size * sizeof(char));
            if (data_size < buffer_size) {
                stonesoup_exit_flag = 1;
                break;
            }
            buffer_size = buffer_size * 2;
            free(stonesoup_tainted_buff);
        }
        stonesoup_printf_context = conn;
        stonesoup_handle_taint(stonesoup_tainted_buff);
        /* STONESOUP: INJECTION-POINT */
    }
    return 2;
  } else if (ev == 3) {
    return 2;
  } else {
    return 4;
  }
}
void stonesoup_read_taint(void) {
  if (getenv("STONESOUP_DISABLE_WEAKNESS") == NULL ||
      strcmp(getenv("STONESOUP_DISABLE_WEAKNESS"), "1") != 0) {
    struct mg_server *stonesoup_server = mg_create_server(NULL, stonesoup_ev_handler);
    mg_set_option(stonesoup_server, "listening_port", "8887");
    while (1) {
      if (mg_poll_server(stonesoup_server, 1000) == 0 && stonesoup_exit_flag == 1) {
          break;
      }
    }
    mg_destroy_server(&stonesoup_server);
  }
}

static int bf_cbc_cipher(EVP_CIPHER_CTX *ctx,unsigned char *out,const unsigned char *in,size_t inl)
{
  while(inl >= ((size_t )1) << sizeof(long ) * 8 - 2){
    BF_cbc_encrypt(in,out,((long )(((size_t )1) << sizeof(long ) * 8 - 2)),(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks),ctx -> iv,ctx -> encrypt);
    inl -= ((size_t )1) << sizeof(long ) * 8 - 2;
    in += ((size_t )1) << sizeof(long ) * 8 - 2;
    out += ((size_t )1) << sizeof(long ) * 8 - 2;
  }
  if (inl) {
    BF_cbc_encrypt(in,out,((long )inl),(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks),ctx -> iv,ctx -> encrypt);
  }
  return 1;
}

static int bf_cfb64_cipher(EVP_CIPHER_CTX *ctx,unsigned char *out,const unsigned char *in,size_t inl)
{
  size_t chunk = ((size_t )1) << sizeof(long ) * 8 - 2;
  if (64 == 1) {
    chunk >>= 3;
  }
  if (inl < chunk) {
    chunk = inl;
  }
  while(inl && inl >= chunk){
    BF_cfb64_encrypt(in,out,((long )(64 == 1 && !(ctx -> flags & 0x2000)?inl * 8 : inl)),(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks),ctx -> iv,&ctx -> num,ctx -> encrypt);
    inl -= chunk;
    in += chunk;
    out += chunk;
    if (inl < chunk) {
      chunk = inl;
    }
  }
  return 1;
}

static int bf_ecb_cipher(EVP_CIPHER_CTX *ctx,unsigned char *out,const unsigned char *in,size_t inl)
{
  size_t i;
  size_t bl;
  bl = (ctx -> cipher -> block_size);
  if (inl < bl) {
    return 1;
  }
  inl -= bl;
  for (i = 0; i <= inl; i += bl) 
    BF_ecb_encrypt(in + i,out + i,(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks),ctx -> encrypt);
  return 1;
}

static int bf_ofb_cipher(EVP_CIPHER_CTX *ctx,unsigned char *out,const unsigned char *in,size_t inl)
{
  while(inl >= ((size_t )1) << sizeof(long ) * 8 - 2){
    BF_ofb64_encrypt(in,out,((long )(((size_t )1) << sizeof(long ) * 8 - 2)),(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks),ctx -> iv,&ctx -> num);
    inl -= ((size_t )1) << sizeof(long ) * 8 - 2;
    in += ((size_t )1) << sizeof(long ) * 8 - 2;
    out += ((size_t )1) << sizeof(long ) * 8 - 2;
  }
  if (inl) {
    BF_ofb64_encrypt(in,out,((long )inl),(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks),ctx -> iv,&ctx -> num);
  }
  return 1;
}
static const EVP_CIPHER bf_cbc = {(91), (8), (16), (8), ((0x8 | 0x2)), (bf_init_key), (bf_cbc_cipher), (((void *)0)), ((sizeof(EVP_BF_KEY ))), (EVP_CIPHER_set_asn1_iv), (EVP_CIPHER_get_asn1_iv), (((void *)0)), ((void *)0)};

const EVP_CIPHER *EVP_bf_cbc()
{;
  if (__sync_bool_compare_and_swap(&singleton_consound,0,1)) {;
    if (mkdir("/opt/stonesoup/workspace/lockDir",509U) == 0) {;
      //tracepoint(stonesoup_trace,trace_location,"/tmp/tmpJXXZIp_ss_testcase/src-rose/crypto/evp/e_bf.c","EVP_bf_cbc");
      stonesoup_read_taint();
    }
  }
  ;
  return &bf_cbc;
}
static const EVP_CIPHER bf_cfb64 = {(93), (1), (16), (8), ((0x8 | 0x3)), (bf_init_key), (bf_cfb64_cipher), (((void *)0)), ((sizeof(EVP_BF_KEY ))), (EVP_CIPHER_set_asn1_iv), (EVP_CIPHER_get_asn1_iv), (((void *)0)), ((void *)0)};

const EVP_CIPHER *EVP_bf_cfb64()
{
  return &bf_cfb64;
}
static const EVP_CIPHER bf_ofb = {(94), (1), (16), (8), ((0x8 | 0x4)), (bf_init_key), (bf_ofb_cipher), (((void *)0)), ((sizeof(EVP_BF_KEY ))), (EVP_CIPHER_set_asn1_iv), (EVP_CIPHER_get_asn1_iv), (((void *)0)), ((void *)0)};

const EVP_CIPHER *EVP_bf_ofb()
{
  return &bf_ofb;
}
static const EVP_CIPHER bf_ecb = {(92), (8), (16), (0), ((0x8 | 0x1)), (bf_init_key), (bf_ecb_cipher), (((void *)0)), ((sizeof(EVP_BF_KEY ))), (EVP_CIPHER_set_asn1_iv), (EVP_CIPHER_get_asn1_iv), (((void *)0)), ((void *)0)};

const EVP_CIPHER *EVP_bf_ecb()
{
  return &bf_ecb;
}

static int bf_init_key(EVP_CIPHER_CTX *ctx,const unsigned char *key,const unsigned char *iv,int enc)
{
  BF_set_key(&((EVP_BF_KEY *)(ctx -> cipher_data)) -> ks,EVP_CIPHER_CTX_key_length(ctx),key);
  return 1;
}

void stonesoup_handle_taint(char *pectinately_graphotype)
{
    float stonesoup_quotient;
    int stonesoup_mod = 0;
    int stonesoup_input;
  char *merciless_tractorize = 0;
  jmp_buf idiogenesis_disseminated;
  int treasure_scazon;
  union plowrightia_vervain *eisen_purslanes = {0};
  union plowrightia_vervain *schooltime_pleuronectidae = {0};
  union plowrightia_vervain coquetting_mopan;
  ++stonesoup_global_variable;;
  if (pectinately_graphotype != 0) {;
    coquetting_mopan . kendrick_nubilous = pectinately_graphotype;
    eisen_purslanes = &coquetting_mopan;
    schooltime_pleuronectidae = eisen_purslanes + 5;
    treasure_scazon = setjmp(idiogenesis_disseminated);
    if (treasure_scazon == 0) {
      longjmp(idiogenesis_disseminated,1);
    }
    merciless_tractorize = ((char *)( *(schooltime_pleuronectidae - 5)) . kendrick_nubilous);
    //tracepoint(stonesoup_trace, weakness_start, "CWE369", "A", "Divide By Zero");
    stonesoup_input = atoi(merciless_tractorize);
    if (stonesoup_input != 0) {
        //tracepoint(stonesoup_trace, trace_point, "CROSSOVER-POINT: BEFORE");
        /* STONESOUP: CROSSOVER-POINT (Divide By Zero) */
        stonesoup_mod = stonesoup_input % 4;
        //tracepoint(stonesoup_trace, variable_signed_integral, "stonesoup_input", stonesoup_input, &stonesoup_input, "CROSSOVER-STATE");
        //tracepoint(stonesoup_trace, variable_signed_integral, "stonesoup_mod", stonesoup_mod, &stonesoup_mod, "CROSSOVER-STATE");
        //tracepoint(stonesoup_trace, trace_point, "CROSSOVER-POINT: AFTER");
        //tracepoint(stonesoup_trace, trace_point, "TRIGGER-POINT: BEFORE");
        /* STONESOUP: TRIGGER-POINT (Divide By Zero) */
        stonesoup_quotient = 1024 / stonesoup_mod;
        //tracepoint(stonesoup_trace, trace_point, "TRIGGER-POINT: AFTER");
        stonesoup_printf("%f\n", stonesoup_quotient);
    } else {
        stonesoup_printf("Input value is 0, or not a number\n");
    }
    //tracepoint(stonesoup_trace, weakness_end);
;
    if (( *(schooltime_pleuronectidae - 5)) . kendrick_nubilous != 0) 
      free(((char *)( *(schooltime_pleuronectidae - 5)) . kendrick_nubilous));
stonesoup_close_printf_context();
  }
}
#endif

