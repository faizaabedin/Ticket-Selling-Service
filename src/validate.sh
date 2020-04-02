#!/bin/bash
FILES=./outputsExpected/*
for file in $FILES
do
  #take test name with out extension
  FILE=$(echo FILE =`echo $file | cut -d \/ -f 3 | cut -d \. -f 1`) 

  #this is to print the number of output file to be checked 
  echo "Checking outputs of test $FILE"

  #this is the command to be executed with every test file
  diff ./outputsExpected/$FILE.atf ./outputsActual/$FILE.etf
done

