import java.util.Random;
import java.util.Scanner;

/*
 * Game class handles the simulation of Duck Duck Goose using a circular doubly linked list
 * Each round the game selects a picker, randomly generates a duck count and a direction
 * Advances the pointer to choose a goose (excluding the picker) and then simulates the chase
 * If the goose is a super goose (20% chance) it chases a random number (1-3) of additional players
 * In every round one player is eliminated:
 *   - If the picker wins, the goose is the loser and the picker is moved from their original index
 *   - If the goose wins, the picker is eliminated
 * The winning player is reinserted at the index from which the goose was removed
 * This update ensures that the picker is never the same as the goose and avoids duplicate entries
 */
public class Game {
    private MyCircularDoublyLinkedList<Player> players; // circular list of players
    private int totalRounds; // total rounds to simulate
    private Scanner scanner; // for user input during simulation
    private Random random; // for randomness during simulation
    private int currentPickerIndex; // index for the current picker

    public Game(MyCircularDoublyLinkedList<Player> players, int totalRounds) {
        this.players = players;
        this.totalRounds = totalRounds;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        // Start with the first player in the list
        this.currentPickerIndex = 0;
    }

    public void startSimulation() {
        System.out.println("\nInitial circle:");
        displayPlayers();

        for (int round = 1; round <= totalRounds; round++) {
            if (players.size() < 2) {
                System.out.println("Not enough players to continue the game");
                break;
            }
            System.out.println("\n--- Round " + round + " ---");

            // Save the picker's original index and get the picker
            int originalPickerIndex = currentPickerIndex;
            Player picker = players.get(originalPickerIndex);
            System.out.println("Picker is: " + picker.getName());

            // Generate a random duck count (value between 1 and 5)
            int moveCount = random.nextInt(5) + 1;
            System.out.println("Move count: " + moveCount);

            // Randomly choose direction (true means clockwise; false means counterclockwise)
            boolean clockwise = random.nextBoolean();
            String directionStr = "";
            if (clockwise) {
                directionStr = "clockwise";
            } else {
                directionStr = "counterclockwise";
            }
            System.out.println("Direction: " + directionStr);

            // Compute goose index from the list excluding the picker
            int effectiveSize = players.size() - 1; // number of eligible players
            int offset = (moveCount - 1) % effectiveSize;
            int gooseIndex = (originalPickerIndex + 1 + offset) % players.size();
            // This guarantees the picker is not chosen as goose

            Player goose = players.get(gooseIndex);
            System.out.println("Goose selected: " + goose.getName());

            // Determine if the goose is super goose (20% chance)
            boolean isSuperGoose = false;
            if (random.nextDouble() < 0.2) {
                isSuperGoose = true;
            }
            if (isSuperGoose) {
                System.out.println(goose.getName() + " is a super goose!");
            }

            // Remove the goose from the circle and save its removal index
            players.remove(gooseIndex);
            // At this point the list size has decreased by one

            // Determine chase outcome
            boolean pickerWinsMain = random.nextBoolean();
            if (pickerWinsMain) {
                System.out.println("Picker " + picker.getName() + " wins the chase against " + goose.getName());
            } else {
                System.out.println("Goose " + goose.getName() + " wins the chase against " + picker.getName());
            }

            // In both cases, we need to remove the picker from the list to avoid duplication when reinserting the winner
            // Adjust the picker's index if necessary (if the goose was removed from before the picker, shift left by one)
            int adjustedPickerIndex = originalPickerIndex;
            if (originalPickerIndex > gooseIndex) {
                adjustedPickerIndex = originalPickerIndex - 1;
            }

            Player winner = null;
            if (pickerWinsMain) {
                winner = picker;
                players.remove(adjustedPickerIndex);
            } else {
                winner = goose;
                players.remove(adjustedPickerIndex);
            }

            // Compute the insertion index based on the original goose removal index
            int insertionIndex = gooseIndex;
            if (adjustedPickerIndex < gooseIndex) {
                insertionIndex = gooseIndex - 1;
            }

            // Reinsert the winning player at the removal index
            players.add(insertionIndex, winner);
            System.out.println("Reinserting " + winner.getName() + " at index " + insertionIndex);

            displayPlayers();

            if (round < totalRounds) {
                System.out.println("Do you want to add or remove a player? (add / remove / none)");
                String choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("add")) {
                    System.out.println("Enter new player's name");
                    String newName = scanner.nextLine().trim();
                    players.add(new Player(newName));
                    System.out.println(newName + " added");
                    displayPlayers();
                } else if (choice.equals("remove")) {
                    System.out.println("Enter player's name to remove");
                    String removeName = scanner.nextLine().trim();
                    boolean removed = false;
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).getName().equalsIgnoreCase(removeName)) {
                            players.remove(i);
                            System.out.println(removeName + " removed");
                            removed = true;
                            break;
                        }
                    }
                    if (removed == false) {
                        System.out.println("Player " + removeName + " not found");
                    }
                    displayPlayers();
                }
            }
            // Set the current picker index to the player immediately after the newly inserted winner
            currentPickerIndex = (insertionIndex + 1) % players.size();
        }
    }

    private void displayPlayers() {
        System.out.println("Current circle:");
        players.walkForward();
    }
}