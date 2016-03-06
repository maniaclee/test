#!/bin/bash

java -Xmx500M -cp "/usr/local/lib/antlr-4.5-complete.jar:$CLASSPATH" org.antlr.v4.Tool JavaBean.g4
javac JavaBean*.java
java org.antlr.v4.runtime.misc.TestRig  JavaBean file -gui input
