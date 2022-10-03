import java.util.*;

public class Multiplayer extends Game {

    public Capital capital;
    public Player multiPlayer;
    public final LeaderBoard leaderboard;

    Scanner scanner = new Scanner(System.in);

    ArrayList<String> playerArrayList = new ArrayList<>();
    private String currentPlayer = null;


    /**
     * Constructor of the game
     */
    public Multiplayer() {
        this.capital = CapitalReader.getRandomCapital();
        this.multiPlayer = new Player();
        this.leaderboard = new LeaderBoard();
    }

    public void GameMenu() {

        boolean running;
        running = true;

        while (running) {

            System.out.println("1. Start singel player game!");
            System.out.println("2. Multiplayer");
            System.out.println("3. Quit");
            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    Game game = new Game();
                    game.start();
                    break;
                case 2:
                    System.out.println("Multiplayer game selected");
                    addNewPlayer();
                    multiplayerGame();
                    break;
                case 3:
                    running = false;
                    break;

            }
        }
    }

    public void multiplayerGame() {
        final String WORD = "w";
        final String LETTER = "l";
        final int LAST_LIFE = 1;
        boolean gameIsOver = false;
        double timeInSeconds;
        String userOption = "";
        int index = 0;

        Collections.shuffle(playerArrayList);
        CapitalReader.getRandomCapital();

        while (!gameIsOver) {

            showGameHintsMultiplayer();
            if (multiPlayer.getLifePoints() == LAST_LIFE) {
                GameView.displayCountryName(capital.getCountry());
            }
            userOption = getOption();
            if (userOption.toLowerCase().equals(WORD)) {
                gameIsOver = checkGuessedWord();  /////fix this
            } else if (userOption.toLowerCase().equals(LETTER)) {
                gameIsOver = checkGuessedLetterMultiplayer();
                currentPlayer = playerArrayList.get(index);
                index++;
                System.out.println("\n Player right now: " + currentPlayer);
                if (index == playerArrayList.size()) {
                    index = 0;

                }
            }
            if (multiPlayer.getLifePoints() <= 0) {
                gameIsOver = true;
            }
            if (gameIsOver) {
                multiPlayer.calculateTime();
                showGameHints();
                if (multiPlayer.getLifePoints() <= 0) {
                    GameView.displayLoseMessage();
                } else {
                    GameView.displayWinMessage();
                    this.leaderboard.addScore(GameView.getUserName(), new Date(), multiPlayer.getGuessingTime(),
                            multiPlayer.getGuessingCount(), capital.getName());
                }
                GameView.displayGuessingCountAndTime(multiPlayer.getGuessingCount(), multiPlayer.getGuessingTime());
                this.leaderboard.displayScores();
                gameIsOver = askToPlayAgain();
            }
        }
    }


    /**
     * Adds new player to Multiplayer game
     *
     * @return
     */
    public ArrayList<String> addNewPlayer() {

        System.out.println("Choose players: 2-4");
        String playerChoice = scanner.next();



        while (true) {
            switch (playerChoice) {

                case "2":
                    System.out.println("Player 1: ");
                    String player1 = scanner.next();
                    playerArrayList.add(player1);
                    Player Multiplayer = new Player();
                    System.out.println("Player 2: ");
                    String player2 = scanner.next();
                    playerArrayList.add(player2);
                    Player Multiplayer2 = new Player();


                    System.out.println("Players selected:  " + playerArrayList);
                    break;


                case "3":
                    System.out.println("Player 1: ");
                    String player3 = scanner.next();
                    playerArrayList.add(player3);
                    System.out.println("Player 2: ");
                    String player4 = scanner.next();
                    playerArrayList.add(player4);
                    System.out.println("Player 3: ");
                    String player5 = scanner.next();
                    playerArrayList.add(player5);
                    System.out.println("Players selected:  " + playerArrayList);
                    break;
                case "4":
                    System.out.println("Player 1: ");
                    String player6 = scanner.next();
                    playerArrayList.add(player6);
                    System.out.println("Player 2: ");
                    String player7 = scanner.next();
                    playerArrayList.add(player7);
                    System.out.println("Player 3: ");
                    String player8 = scanner.next();
                    playerArrayList.add(player8);
                    System.out.println("Player 4: ");
                    String player9 = scanner.next();
                    playerArrayList.add(player9);
                    System.out.println("Players selected:  " + playerArrayList);
                    break;
            }
            return playerArrayList;
        }
    }

    /**
     * Show all information for the user needed to play, eg. hints, lives, ascii art
     */
    public void showGameHintsMultiplayer () {
        clearConsole();
        GameView.displayHintAndLives(capital.getHint(), multiPlayer.getLifePoints());
        if (capital.getNotInWord().size() > 0) {
            GameView.displayNotInWord(capital.getNotInWordAsString());
        }
        GameView.displayAsciiArt(multiPlayer.getLifePoints());
    }

    boolean checkGuessedLetterMultiplayer() {

        final int LETTER_GUESSING_DECREMENT = 1;
        boolean gameIsOver = false;
        char letter = getGuessedLetter();

        multiPlayer.incrementGuessingCount();
        if (capital.getName().toUpperCase().contains(Character.toString(letter).toUpperCase())) {
            capital.unhideLetter(letter);

            if (capital.getHiddenWordAsString().equals(capital.getName())) {
                gameIsOver = true;
            }
        } else {
            GameView.displayLetterNotInWordMessage();
            capital.addLetterToNotInWord(Character.toString(letter).toUpperCase());
            multiPlayer.decrementLifePointsByValue(LETTER_GUESSING_DECREMENT);
        }

        return gameIsOver;
    }
}
