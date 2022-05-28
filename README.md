# SnakeGame 


### Basic rules of the game
- The size of the board is 40 x 40 or larger.
- The snake starts at the center of the board, moving north (upward).
- The snake moves at a constant speed.
- The snake moves only north, south, east, or west.
- An apple appears at a random location (but the location where the snake can reach).
  - There is always exactly one apple visible at any given time.
- When the snake "eats" (runs into) an apple, it gets longer.
- The game continues until the snake "dies".
  - The snake dies by either
    1. running into the edge of the board.
    2. running into its own body.
- Once the snake dies, the final score is calculated based on the number of apples eaten by the snake.

### Basic rule of the dual player mode
- Two players (player 1 and 2) concurrently play the Snake game on a single board
- The size of the board is 40 x 80 or larger. 
- The player 1’s snake starts at the left-top corner of the board, moving south (downward).
- The player 2’s snake starts at the right-bottom corner of the board, moving north (upward).
- Apples appear at a random location (but the location where one of the snakes can reach).
    - There are always exactly two apples visible at any given time.
- The game continues until one of the snakes dies.
    - The snakes die by either (1) running into the edge of the board, (2) running into its own 
      body, or (3) running into another snake’s body.
- Once one snake dies, the alive snake wins the game (score is not calculated).
- Other rules including the movement of snakes are same with the single player mode

### Basic rule of auto player mode
- A bot automatically plays the single player Snake game.
-  The final score is calculated, but not recorded in the ranking board.
       - The score is just displayed



### Basic user interfaces
- The main menu of the game includes
  1. **SINGLE PLAY** : starts a new single-player game 
  2. **DUAL PLAY** : starts a new dual-player game 
  3. **AUTO PLAY** : starts a new auto play game
  4. **LOAD** : loads a saved single-player game
  5. **RANKING** : displays the top-ranked players’ name and score
  6. **EXIT** : terminates the game

### UI for the dual player game
- Once the game is started, two players control their snake using arrow keys and w-a-s-d 
keys, respectively.
- The players can pause the game by pressing a special key (e.g., the ESC key)
- Once paused, the in-game menu is popped up with the following options: 
1. RESUME continues to play the paused game
2. RESTART starts a new game, instead of the paused game
3. EXIT returns to the main menu without saving the current game status(the save functionality is not supported in the dual player mode)

### UI for the auto player mode
- Once the game is started, a bot (not a human player) automatically plays the game.
- The user can pause the game by pressing a special key (e.g., the ESC key)
- Once paused, the in-game menu is popped up with the following options: 
1. RESUME continues to play the paused game
2. RESTART starts a new game, instead of the paused game
3. EXIT returns to the main menu without saving the current game status(the save functionality is not supported in the auto play mode)

### UI for all games
- Once a game is finished, a simple pop-up message, which contains the final results (e.g., 
the final score or the winner) is displayed

### Guildeline to install and run the game
- Dowload the Github desktop
- Open our Snake game in Github desktop, then the project including file soure code, file images and the others will save in your local disk that you choose
- Run the project with any IDE or platform which support to Java language


  
