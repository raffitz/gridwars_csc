LIBRARY=api-1.0-SNAPSHOT.jar
JC=javac
JAR=jar

%.class : %.java
	$(JC) -cp $(LIBRARY) $<
	
%.jar : %.class
	$(JAR) cvf $@ $<
