# Duck Duck Goose
Duck Duck Goose is a simulation of the classic game where players sit in a circle and one player is chosen as the goose to be chased each round. It uses a circular doubly linked list to manage players and rotate roles automatically. The program allows a random picker to select the goose using a random move count and random direction (clockwise or counterclockwise), ensuring the picker is never chosen as the goose. In some rounds, the goose randomly becomes a super goose that chases one to three extra players. Additionally, users can choose to add or remove players between rounds.

## Features:


**Random Role Selection**: Automatically rotates the picker and selects a goose based on a random move count and direction.

**Super Goose Mode**: Occasionally, the goose becomes a super goose that chases extra players.

**Dynamic Player Management**: Users can add or remove players between rounds.

## Project Structure:


**DDGRunner.java**: The main entry point that collects user input and starts the simulation.

**Game.java**: Contains the simulation logic for picker rotation, goose selection, chase outcomes, and super goose behavior.

**MyCircularDoublyLinkedList.java**: Implements a circular doubly linked list to maintain player order.

**MyCircularList.java**: Defines the interface for circular list operations.

**Player.java**: Represents individual players and stores their attributes.
