# Sabre
[![license](https://img.shields.io/github/license/Project-Cepi/Sabre?style=for-the-badge&color=b2204c)](../LICENSE)
[![downloads](https://img.shields.io/github/downloads/Project-Cepi/Sabre/latest/total?color=%236ac&label=Downloads&style=for-the-badge)](https://github.com/Project-Cepi/Sabre/releases)
[![discord-banner](https://img.shields.io/discord/706185253441634317?label=discord&style=for-the-badge&color=7289da)](https://discord.cepi.world/8K8WMGV)

Sabre is a server JAR wrapper using the Minestom API as a community-maintained groundwork library.

## Features

* Fully featured configuration
* Stop, update, and operator commands.
* Colored terminal
* Built-in flat generator for testing
* [Import map](https://github.com/Project-Cepi/import-map) for easy setup

## Installation

### Automatic

`curl -fsSL https://raw.githubusercontent.com/Project-Cepi/Sabre/master/install.sh | sh`

### Manual

Download the JAR from [Sabre releases](https://github.com/Project-Cepi/Sabre/releases)
 [or compile it yourself](#Compile).

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
