# shipwrecked

# Shipwrecked – Island of Wonders


### Prepared by: 
Joel Erulu

### Organization: 
Software Development 

### Draft: 
2.0

### Date: 
April 21, 2024

---

## Contents:

1. Introduction:
    - Purpose
    - Scope
    - Definitions, Acronyms, and Abbreviations
    - References
    - Overview

2. Overall Description:
    - Product Perspective
    - Product Functions
    - User Characteristics
    - Constraints
    - Assumptions and Dependencies
    - Apportioning of Requirements

3. Specific Requirements:
    - External Interface Requirements
    - Software Product Features
    - Performance Requirements
    - Design Constraints
    - Software System Attributes
    - Logical Database Requirements
    - Other Requirements

---

## Screenshots

![Screenshot 1](/shipwrecked/screenshot1.JPG)
![Screenshot 2](/shipwrecked/screenshot2.JPG)
![Screenshot 2](/shipwrecked/screenshot3.JPG)

## 1: Introduction

### 1.1 Purpose:

This SRS document is for our text-based adventure game: Shipwrecked - Island of Wonders, version 2.0. The purpose is to build a text-based adventure game, based on Sinbad the Sailor stories. The intended audience is for young adults and adults who enjoy a nice sense of exploration. It might take them 20 minutes to complete the game and after it is up to the users what they want to do.

### 1.2 Scope:

Players must prepare to journey through an island filled with mysteries, treasures, and danger at every turn. In this immersive adventure game, players will encounter multiple puzzles, hidden treasures, formidable enemies, and loyal allies as they chart their course across these exotic lands and treacherous seas. The goal is to explore the vast expanse of the game island, solve intricate puzzles, and complete daring quests that await at every corner. What sets this game apart is the freedom the players have to explore and tackle challenges in any order they desire, fostering a sense of discovery and excitement with each new encounter. With each decision they make, players will shape their own destiny by making friends, collecting treasures, and facing dangerous encounters that promise to keep them on the edge of their seats.

### 1.3 Definitions, Acronyms, and Abbreviations:

- IDE – Integrated Development Environment.
- Player/User – A person playing the game.
- SDLC – Software Development Life Cycle.
- UI – User Interface.
- SRS – Software Requirements Specification.
- RPGs – Role Playing Games.
- TBD – To be determined.
- N/A – Not applicable

### 1.4 References:

Based on ITEC 3860 class PowerPoints lecture slides, word document about code standards is used in section 3.5 of this document and first assignment mini game. Other references include Sinbad: the sailor stories, the Jumanji movies, and Final Fantasy/ RPGs.

### 1.5 Overview:

Section 2 of this document provides an overview of the system. We begin giving a perspective of the product, followed by giving the interfaces that the system will interact with, and a summarized list of the functionality that will be implemented, and potential users who will use the system. Lastly, it lists off constraints and assumptions that limit the system. Section 3 contains all the requirements of the game platform fleshed out in detail, beginning with the requirements needed to make the system run at all, then explaining the requirements which will improve user experience.

---

## 2: Overall Description

### 2.1 Product Perspective:

This product will be independent and self – contained.

#### 2.1.1 System Interfaces:

This game will operate from IntelliJ IDE console.

#### 2.1.2 User interfaces:

The user will operate a computer with a screen, keyboard, mouse, and console to interact with this system. The first display the user will see is the name, description, and the rules of the game. All of these are described in section 3 of this document.

#### 2.1.3 Hardware Interfaces:

N/A.

#### 2.1.4 Software Interfaces:

- SQLite Version 3.45.1
- IntelliJ Version 2023.3.3

#### 2.1.5 Communication Interfaces:

The user, the database, and the console are the main communication interfaces.

#### 2.1.6 Memory Constraints:

TBD.

#### 2.1.7 Operations:

Each room will have the option to examine, eat, help, and talk. Certain rooms can fight, run, hide, save, build, fight. When users save the game, they can reload data in the save room. You will restart from your previous save point. Available commands for rooms include directional commands, eat, sleep (when there are no enemies), examine, gather, help, talk. Commands when enemies are present include run, hide, and fight. Valid commands if typed properly will work, but invalid will throw a message saying “Not a valid command. Type help for command list.”

### 2.2 Product Functions:

- Begin the game.
- Load saved data.
- Save data.
- Exit the game.
- Game prompts
- Encounter text and possible choices.

### 2.3 User Characteristics:

Users of this game should be familiar with basic game mechanics such as typing commands and making choices. They should have patience and enjoy exploration and puzzle-solving.

### 2.4 Constraints:

- Only one player can play the game at a time.
- The game must be run on a computer with Java installed.
- The game is text-based, so players must be able to read and type commands.
- Limited graphics capabilities.

### 2.5 Assumptions and Dependencies:

- Assumes players have basic typing and reading abilities.
- Assumes players are interested in an adventure-based game with puzzle-solving elements.
- Relies on the availability of Java and the specified IDE (IntelliJ) to run.

### 2.6 Apportioning of Requirements:

Future updates may include graphics, multiplayer functionality, and expanded storyline options.

---

## 3: Specific Requirements

### 3.1 External Interface Requirements:

#### 3.1.1 User Interfaces:

- The game will be played via a console interface.
- Users will type commands to interact with the game.
- The game will provide text-based feedback and prompts.

#### 3.1.2 Software Interfaces:

- The game will interact with a SQLite database to manage saved game data.
- The game will be developed using the IntelliJ IDE.
- The game will be written in Java.

### 3.2 Software Product Features:

#### 3.2.1 Game Initialization:

- Users can start a new game.
- Users can load a previously saved game.
- Users can exit the game.

#### 3.2.2 Player Actions:

- Users can move between rooms.
- Users can examine their surroundings.
- Users can interact with objects in the environment.
- Users can talk to NPCs.
- Users can save their progress.
- Users can exit the game.

### 3.3 Performance Requirements:

- The game should respond to user input within a reasonable time frame (less than 1 second).
- The game should run smoothly on standard hardware configurations.

### 3.4 Design Constraints:

- The game must be text-based.
- The game must be playable on a console interface.
- The game must be developed using Java.

### 3.5 Software System Attributes:

#### 3.5.1 Reliability:

- The game should handle user input errors gracefully.
- The game should save progress reliably.

#### 3.5.2 Availability:

- The game should be available to play whenever the user desires.
- The game should not crash or freeze during normal operation.

#### 3.5.3 Security:

- The game should not require any sensitive user information.
- The game should not introduce any security vulnerabilities to the user's system.

### 3.6 Logical Database Requirements:

- The game will use a SQLite database to store player progress and game state.

