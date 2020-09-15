# Makefile for starlibj

include $(BMRBMAKEHEADER)

#
# uncomment (and edit) these if your $BMRBMAKEHEADER does not
# define them (or you don't have $BMRBMAKEHEADER)
#
#GLOBALCLASSES = /bmrb/javaclasses

INSTDIR = $(GLOBALCLASSES)/EDU/bmrb/starlibj

all: starlibj

starlibj:
	javac *.java

install:
	- mkdir -p $(INSTDIR)
	cp *.class $(INSTDIR)

clean:
	- rm *.class *\~

# eof Makefile