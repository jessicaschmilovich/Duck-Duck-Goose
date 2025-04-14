import java.util.Scanner;

public class DDGRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for number of players; must be at least 3
        System.out.println("Enter number of players:");
        int numPlayers = Integer.parseInt(scanner.nextLine().trim());
        while (numPlayers < 3) {
            System.out.println("Please enter a number of players that is at least 3:");
            numPlayers = Integer.parseInt(scanner.nextLine().trim());
        }

        // Create the circular list and add each player
        MyCircularDoublyLinkedList<Player> playerList = new MyCircularDoublyLinkedList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Enter name for player " + (i + 1) + ":");
            String name = scanner.nextLine().trim();
            playerList.add(new Player(name));
        }
        
        // Ask for total number of simulation rounds
        System.out.println("Enter total number of simulation rounds:");
        int rounds = Integer.parseInt(scanner.nextLine().trim());
        
        // Instantiate Game and run the simulation
        Game game = new Game(playerList, rounds);
        game.startSimulation();
    }
}