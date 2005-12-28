This is a Meta/Z test project to get started
with the maven build and project management tool.
This readme contains a step by step introduction
to maven.

1. install maven
	Maven can be obtained from
	http://maven.apache.org.
	Download version 2.0.1 and install conforming
	to the instructions found on the maven site.
	
2. check out the helloworld project from metaz.berlios.de
	The helloworld project is contained in the helloworld
	directory.

3.	open a command window and go to the location where you
	checked out helloworld (this is your subversion sandbox).
	from the command line, run mvn compile.
	Possibly maven starts retrieving jars it needs, and next it
	will compile the HelloWorld.java file.
	from the command line, run mvn test.
	Tests will be run.
	from the command line, run mvn package
	A jar file will be created and placed in the target dir.
	
4	Modify HelloWorld.java and add a system.out.println statement
	adding your name.
	run the above commands again.
	
	from the command line, run mvn site
	A site will be created in the target/site folder.
	Find your name in the Project Info / Project team page
	
5.	Commit the changed HelloWorld.java file

If things go wrong, drop me an email!
Erik-Jan (E.J.Spaans@inter.NL.net). 