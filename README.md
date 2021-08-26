# Sabre
Sabre is a server JAR wrapper using the Minestom API as a community-maintained groundwork library.

## Installation

Download the JAR from [Sabre releases](https://github.com/Project-Cepi/Sabre/releases)
or compile it yourself. Instructions to do so are in Compile header.

Create a run script or run it from the terminal using 

`java -Xms2000m -Xmx2000m -jar Sabre.jar`

With `-Xms` being the minimum amount of RAM you want to use,

and `-Xmx` being the max amount of RAM you want to use.

## Compile

Create a folder, then
Clone the repository using:

`git clone https://github.com/Project-Cepi/Sabre.git`

Once it is cloned, make sure you have gradle installed, and run

`./gradlew shadowJar` on Mac or Linux, and

`gradlew shadowJar` on Windows.

This will output the jar to `build/libs` in the project directory.
