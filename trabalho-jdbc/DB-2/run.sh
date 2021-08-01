#!/bin/sh
printf "> Compilando os arquivos...\n"
javac *.java -Xlint:deprecation
clear
printf "> Compilação completa\n"
printf "> Executando Programa\n\n"
java -classpath hsql.jar:. Mainwindow
clear
exit
