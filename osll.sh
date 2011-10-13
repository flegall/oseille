#! /bin/sh

command="java -jar osll.jar"

# Parsing parameters (for wildcard support)
while [ $# -ne 0 ]
do
   command="$command \"$1\""
   shift
done  

eval ${command}

exit $?
