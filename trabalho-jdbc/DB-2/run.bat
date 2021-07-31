print "> Compilando os Arquivos...\n"
javac *.java -Xlint:deprecation
cls
print "> Compilação completa!\n"
print "> Executando o programa\n\n"
java -classpath .;hsql.jar Mainwindow
cls
@pause
