#ifndef SERVEURCHECKIN_H
#define SERVEURCHECKIN_H

#include <stdio.h>
#include <stdlib.h> /* pour exit */
#include <string.h> /* pour memcpy */
#include <fcntl.h>
#include <signal.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h> /* pour les types de socket */
#include <netdb.h> /* pour la structure hostent */
#include <errno.h>
#include <netinet/in.h> /* pour la conversion adresse reseau->format dot ainsi que le conversion format local/format reseau */
#include <netinet/tcp.h> /* pour la conversion adresse reseau->format dot */
#include <arpa/inet.h> /* pour la conversion adresse reseau->format dot */
#include <time.h> /* pour select et timeval */
#include <pthread.h>
//#include "SocketsUtilities.c"
#define NB_MAX_CLIENTS 5 /* Nombre maximum de clients connectes */
#define EOC "END_OF_CONNEXION"
#define DOC "DENY_OF_CONNEXION"
#define MAXSTRING 160 /* Longueur des messages */
#define affThread(num, msg) printf("th_%s> %s\n", num, msg);

#define LOGIN_OFFICER 1
#define LOGOUT_OFFICER 2
#define CHECK_TICKET 3
#define PAYMENT_DONE 4

typedef struct
{
  char nomUtilisateur[80];
  char password[80];
} userLogin;
typedef struct
{
  char id_Ticket[80];
  int nbrPassagers;
} ticket;
void HandlerQuit(int);
void * fctThread(void * param);
char * getThreadIdentity();
void Config();
void chargeVecLogin();
int checkLuggage(int);
//void chargeVecTicket();
int checkTicket(char*,int);
int login(char *,char*);
#endif