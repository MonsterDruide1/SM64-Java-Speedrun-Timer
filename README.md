# SM64-Java-Speedrun-Timer
This is a Java-Based Speedrun-Timer for Super Mario 64. It doesn't need input to go on to the next Split/Checkpoint, it does automatically from the output of the game.

## Requirements
* A PC with Windows
* Java JDK
* MUPEN64 0.5-rerecording (others hopefully coming soon)
* SM64 - US Version (others hopefully coming soon)

## Building
The directory "Project" is a Maven project. You can import it into Eclipse (other IDE's may work) and work with it.

If you want to have it as .jar, then you have to run it from the command line. But before compiling it into a jar, you have to set the variable "saved" (look into "Setting up") to your path.

## Setting up
The timer needs to know, which checkpoints you want to have. Thus you need to create a file with all those checkpoints. An example is the file "31-Star-Speedrun-example.txt" in the root of the Git Project. 
The syntax is equal every time: 
*Name of your Checkpoint*||*type of Checkpoint*||*hex code of Checkpoint* (<--><--> *alternative of checkpoint* (<--><--> *alternative to alternative of checkpoint* (...)))
Lastly, you have to set the path of your speedrun data created in the last step. In the "Main.java" in "src/code" you can set the variable "saved" to the path of your choice.

## Starting
After Building and Setting up, you can start the jar without attributes and it will give you a countdown from 3 to 0 and then the first checkpoint starts. After completing all checkpoints the timer will calculate your times and print it out to console. Til today there is no graphical out- or input. The output is in German or English, depending on your system language.

## License
You can use these files under CC BY-NC 4.0 (creative commons 4.0 non-commercial).
