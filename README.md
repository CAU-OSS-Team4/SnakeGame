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


### Basic user interfaces
- The main menu of the game includes
  1. **PLAY** : starts a new game
  2. **LOAD** : loads a saved game
  3. **RANKING** : displays the top-ranked players' name and score.
  4. **EXIT** : terminates the game

- Once the game is started, the player controls the snake using arrow keys.
- The player can pause the game by pressing a special key (e.g., the ESC key)
- Once paused, the in-game menu is popped up with the following options:
  1. **RESUME** : continues to play the paused game
  2. **RESTART** : starts a new game, instead of the paused game
  3. **SAVE** : stores the current game status and returns to the main menu
     - The status includes the information about the snake and apple.
  4. **EXIT** : returns to the main menu without saving the current game status



  
