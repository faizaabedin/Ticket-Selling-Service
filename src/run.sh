#!/bin/bash
FILES=./inputs/*
for file in $FILES
do
  #take test name with out extension
  FILE=$(echo $file | cut -d \/ -f 3 | cut -d \. -f 1) 

  #this is to print test number 
  echo "Processing $FILE"

  javac main.java

  #this is the command to be executed with every test file
  java main currentUserFile.inp availableTicketFile.inp $FILE.inp ./outputsActual/$FILE.atf | tee ./outputsActual/$FILE.bto
  #  > ./outputsActual/$file.out
done

