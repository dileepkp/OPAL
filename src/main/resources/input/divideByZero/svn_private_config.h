/* subversion/svn_private_config.h.in.  Generated from configure.ac by autoheader.  */

/* The fs type to use by default */
#undef DEFAULT_FS_TYPE

/* The http library to use by default */
#undef DEFAULT_HTTP_LIBRARY

/* Define to 1 if Ev2 implementations should be used. */
#undef ENABLE_EV2_IMPL

/* Define to 1 if translation of program messages to the user's native
   language is requested. */
#undef ENABLE_NLS

/* Define to 1 if you have the `bind_textdomain_codeset' function. */
#undef HAVE_BIND_TEXTDOMAIN_CODESET

/* Define to 1 if you have the <dlfcn.h> header file. */
#undef HAVE_DLFCN_H

/* Define to 1 if you don't have `vprintf' but do have `_doprnt.' */
#undef HAVE_DOPRNT

/* Define to 1 if you have the <inttypes.h> header file. */
#undef HAVE_INTTYPES_H

/* Define to 1 if you have the `iconv' library (-liconv). */
#undef HAVE_LIBICONV

/* Define to 1 if you have the `socket' library (-lsocket). */
#undef HAVE_LIBSOCKET

/* Define to 1 if you have the <magic.h> header file. */
#undef HAVE_MAGIC_H

/* Define to 1 if you have the <memory.h> header file. */
#undef HAVE_MEMORY_H

/* Define to 1 if you have the `rb_errinfo' function. */
#undef HAVE_RB_ERRINFO

/* Define to 1 if you have the `readlink' function. */
#undef HAVE_READLINK

/* Define to 1 if you have the <serf.h> header file. */
#undef HAVE_SERF_H

/* Define to 1 if you have the <stdint.h> header file. */
#undef HAVE_STDINT_H

/* Define to 1 if you have the <stdlib.h> header file. */
#undef HAVE_STDLIB_H

/* Define to 1 if you have the <strings.h> header file. */
#undef HAVE_STRINGS_H

/* Define to 1 if you have the <string.h> header file. */
#undef HAVE_STRING_H

/* Define to 1 if you have the `symlink' function. */
#undef HAVE_SYMLINK

/* Define to 1 if you have the <sys/stat.h> header file. */
#undef HAVE_SYS_STAT_H

/* Define to 1 if you have the <sys/types.h> header file. */
#undef HAVE_SYS_TYPES_H

/* Define to 1 if you have the <sys/utsname.h> header file. */
#undef HAVE_SYS_UTSNAME_H

/* Define to 1 if you have the `tcgetattr' function. */
#undef HAVE_TCGETATTR

/* Define to 1 if you have the `tcsetattr' function. */
#undef HAVE_TCSETATTR

/* Defined if we have a usable termios library. */
#undef HAVE_TERMIOS_H

/* Define to 1 if you have the `uname' function. */
#undef HAVE_UNAME

/* Define to 1 if you have the <unistd.h> header file. */
#undef HAVE_UNISTD_H

/* Define to 1 if you have the `vprintf' function. */
#undef HAVE_VPRINTF

/* Define to 1 if you have the <zlib.h> header file. */
#undef HAVE_ZLIB_H

/* Define to the sub-directory in which libtool stores uninstalled libraries.
   */
#undef LT_OBJDIR

/* Define to the address where bug reports for this package should be sent. */
#undef PACKAGE_BUGREPORT

/* Define to the full name of this package. */
#undef PACKAGE_NAME

/* Define to the full name and version of this package. */
#undef PACKAGE_STRING

/* Define to the one symbol short name of this package. */
#undef PACKAGE_TARNAME

/* Define to the home page for this package. */
#undef PACKAGE_URL

/* Define to the version of this package. */
#undef PACKAGE_VERSION

/* Define to 1 if you have the ANSI C header files. */
#undef STDC_HEADERS

/* Define to the Python/C API format character suitable for apr_int64_t */
#undef SVN_APR_INT64_T_PYCFMT

/* Define if circular linkage is not possible on this platform. */
#undef SVN_AVOID_CIRCULAR_LINKAGE_AT_ALL_COSTS_HACK

/* Defined to be the path to the installed binaries */
#undef SVN_BINDIR

/* Defined to the config.guess name of the build system */
#undef SVN_BUILD_HOST

/* Defined to the config.guess name of the build target */
#undef SVN_BUILD_TARGET

/* The path of a default editor for the client. */
#undef SVN_CLIENT_EDITOR

/* Defined if the full version matching rules are disabled */
#undef SVN_DISABLE_FULL_VERSION_MATCH

/* Defined if plaintext password/passphrase storage is disabled */
#undef SVN_DISABLE_PLAINTEXT_PASSWORD_STORAGE

/* The desired major version for the Berkeley DB */
#undef SVN_FS_WANT_DB_MAJOR

/* The desired minor version for the Berkeley DB */
#undef SVN_FS_WANT_DB_MINOR

/* The desired patch version for the Berkeley DB */
#undef SVN_FS_WANT_DB_PATCH

/* Define if compiler provides atomic builtins */
#undef SVN_HAS_ATOMIC_BUILTINS

/* Is GNOME Keyring support enabled? */
#undef SVN_HAVE_GNOME_KEYRING

/* Is GPG Agent support enabled? */
#undef SVN_HAVE_GPG_AGENT

/* Is Mac OS KeyChain support enabled? */
#undef SVN_HAVE_KEYCHAIN_SERVICES

/* Defined if KWallet support is enabled */
#undef SVN_HAVE_KWALLET

/* Defined if libmagic support is enabled */
#undef SVN_HAVE_LIBMAGIC

/* Is Mach-O low-level _dyld API available? */
#undef SVN_HAVE_MACHO_ITERATE

/* Is Mac OS property list API available? */
#undef SVN_HAVE_MACOS_PLIST

/* Defined if apr_memcache (standalone or in apr-util) is present */
#undef SVN_HAVE_MEMCACHE

/* Defined if Expat 1.0 or 1.1 was found */
#undef SVN_HAVE_OLD_EXPAT

/* Defined if Cyrus SASL v2 is present on the system */
#undef SVN_HAVE_SASL

/* Defined if support for Serf is enabled */
#undef SVN_HAVE_SERF

/* Defined if libsvn_client should link against libsvn_ra_local */
#undef SVN_LIBSVN_CLIENT_LINKS_RA_LOCAL

/* Defined if libsvn_client should link against libsvn_ra_serf */
#undef SVN_LIBSVN_CLIENT_LINKS_RA_SERF

/* Defined if libsvn_client should link against libsvn_ra_svn */
#undef SVN_LIBSVN_CLIENT_LINKS_RA_SVN

/* Defined if libsvn_fs should link against libsvn_fs_base */
#undef SVN_LIBSVN_FS_LINKS_FS_BASE

/* Defined if libsvn_fs should link against libsvn_fs_fs */
#undef SVN_LIBSVN_FS_LINKS_FS_FS

/* Defined to be the path to the installed locale dirs */
#undef SVN_LOCALE_DIR

/* Defined to be the null device for the system */
#undef SVN_NULL_DEVICE_NAME

/* Defined to be the path separator used on your local filesystem */
#undef SVN_PATH_LOCAL_SEPARATOR

/* Subversion library major verson */
#undef SVN_SOVERSION

/* Defined if svn should use the amalgamated version of sqlite */
#undef SVN_SQLITE_INLINE

/* Defined if svn should try to load DSOs */
#undef SVN_USE_DSO

/* Define to empty if `const' does not conform to ANSI C. */
#undef const

/* Define to `unsigned int' if <sys/types.h> does not define. */
#undef size_t

#ifdef SVN_WANT_BDB
#define APU_WANT_DB
@SVN_DB_HEADER@
#endif



/* Indicate to translators that string X should be translated.  Do not look
   up the translation at run time; just expand to X.  This macro is suitable
   for use where a constant string is required at compile time. */
#define N_(x) x
/* Indicate to translators that we have decided the string X should not be
   translated.  Expand to X. */
#define U_(x) x
#ifdef ENABLE_NLS
#include <locale.h>
#include <libintl.h>
/* Indicate to translators that string X should be translated.  At run time,
   look up and return the translation of X. */
#define _(x) dgettext(PACKAGE_NAME, x)
/* Indicate to translators that strings X1 and X2 are singular and plural
   forms of the same message, and should be translated.  At run time, return
   an appropriate translation depending on the number N. */
#define Q_(x1, x2, n) dngettext(PACKAGE_NAME, x1, x2, n)
#else
#define _(x) (x)
#define Q_(x1, x2, n) (((n) == 1) ? x1 : x2)
#define gettext(x) (x)
#define dgettext(domain, x) (x)
#endif

