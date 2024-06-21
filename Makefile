all:
	@make Instructions.class
	@make run.class
	@make emulator.class
	@make Programcounter.class
	@make run
Instructions.class run.class: ./Instructions/Instructions.java ./RunMode/run.java
	@javac ./Instructions/Instructions.java
	@javac ./RunMode/run.java
Programcounter.class: ./Programcounter/Programcounter.java
	@javac ./Programcounter/Programcounter.java
emulator.class: emulator.java
	@javac emulator.java
branch.class : ./Branch/branch.java
	@javac ./Branch/branch.java
run:
	@java emulator
clean:
	@rm -f ./Instructions/*.class
	@rm -f ./RunMode/*.class
	@rm -f *.class
	@rm -f ./Programcounter/*.class
	@rm -f ./Branch/*.class
	@rm -f *.class
