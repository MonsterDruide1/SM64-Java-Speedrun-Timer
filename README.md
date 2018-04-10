# SM64-Java-Speedrun-Timer
This is a Java-Based Speedrun-Timer for Super Mario 64. It doesn't need input to go on to the next Split/Checkpoint, it does automatically from the output of the game.

## Requirements
* A PC with Windows
* Java JDK
* MUPEN64 0.5-rerecording (others hopefully coming soon)
* SM64 - US Version (others hopefully coming soon)
* Cheat Engine (hopefully removed soon)

## Building
The directory "Project" is a Maven project. You can import it into Eclipse (other IDE's possible) and work with it. 

Until now, SM64-Java-Speedrun-Timer is a Project, that needs to be recompiled and edited every time, the emulator is closed. 
To make it work, you have to start your emulator. Once SM64 started, you need to edit "Run.java" in "src/code". From the Task Manager, you have to read out the PID of the emulator process. In the code, it's in the run(Checkpoints) Method. The PID goes into the variable named "pid". After that, you have to open Cheat Engine and add an Address manually. The Address is **"mupen64-rerecording.exe"+826BFC**. After clicking on OK the real Address is displayed in the table at the bottom. You have to copy it into the "actionAddress" variable. 

Now you're ready to set up your speedrun!

## Setting up
The timer needs to know, which checkpoints you want to have. Thus you need to create a file with all those checkpoints. An example is the file "31-Star-Speedrun-example.txt" in the root of the Git Project. 
The syntax is equal every time: 
*Name of your Checkpoint*||*type of Checkpoint*||*hex code of Checkpoint* (<--><--> *alternative of checkpoint* (<--><--> *alternative to alternative of checkpoint* (...)))
Lastly, you have to set the path of your speedrun data created in the last step. In the "Main.java" in "src/code" you can set the variable "saved" to the path of your choice.

## Starting
After Building and Setting up, you can start the jar without attributes and it will give you a countdown from 3 to 0 and then the first checkpoint starts. After completing all checkpoints the timer will calculate your times and print it out to console. Til today there is no graphical out- or input. The output is in German; I will translate it soon.

## License
You can use these files under CC BY-NC 4.0 (creative commons 4.0 non-commercial).
