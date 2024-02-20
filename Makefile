all:
	make Instructions.class
	make run.class
	make emulator.class
	make run
Instructions.class run.class: ./Instructions/Instructions.java ./RunMode/run.java
	@javac ./Instructions/Instructions.java
	@javac ./RunMode/run.java
emulator.class: emulator.java
	@javac emulator.java
run:
	@java emulator
clean:
	@rm -f ./Instructions/*.class
	@rm -f ./RunMode/*.class
	@rm -f *.class
