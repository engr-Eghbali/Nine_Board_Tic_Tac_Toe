# Nine_Board_Tic_Tac_Toe
a Nine-board Tic-Tac-Toe AI with H-Minimax algorithm and Alpha-Beta Pruning

Baisc Tic-Tac-Toe

This program uses the MiniMax search algorithm to search the complete state space for the AI to make the best move. In this program the search algorithm works perfectly. It can search the entire state space and return the next move immediately. The AI can guarantee that it will never lose a game.

This program involves the state-space search paradigm that we have covered in class. In this game, the state of the board is represented using a single integer array of size 9. An empty space has value 0; The space occupied by the AI has value 1 and the space occupied by the player has value 2, which can also be used to keep track of whose turn it is to move. The applicable actions are calculates using the method getAvailableMove(), which returns cells that have value 0(empty). The method makeMove(int Move, int turn) can be used to update the board with mark, whose first parameter indicates which cell to mark and the second parameter indicates whose turn it is(1 or 2). The initial state is an empty board, in another word, a integer array of size 9 with value 0. The method isGameOver() checks the terminal states. There are three situations that satisfy the terminal condition: A board with 3 co-linear containing all 1s’(AI wins); A board with 3 co-linear containing all 2s’(player wins); or the board is full (draw).

The AI applies the MiniMax algorithm to search for the best move. The method minimax() will recursively call itself. At each depth, it checks whose turn it is, make all the possible moves and repeat until the terminal nodes. At the terminal nodes, if AI loses, it returns -1; if AI wins, it returns 1, otherwise it returns 0(no empty space, draw). Then the score will be recursively computed upwards. At each depth, if it is AI’s turn, it will return the maximum score; if it is player’s move, it will return the minimum score. The calculated scores for the all the possible moves will be printed and the AI chooses the best next move that has the highest score.

The program reads player’s move through standard input. Player should enter number 1 – 9 indicating which cell they want to play. If that cell is full (the move is not legal), the program will ask player to input again. The AI’s move (a single integer) is printed to standard output. All other information (board, scores, text) are printed to standard error. If AI plays first, it will default to play position 5.




Advanced Tic-Tac-Toe

This program uses the H-MiniMax algorithm with depth-limited of 7 and with alpha-beta pruning. A self-defined heuristic function (describe below) is used to evaluate the score if it reaches the depth limit. The AI takes about 3~4 seconds to calculate the move in the beginning but as the game develops it will be faster. The AI has a pretty passive (not aggressive) strategy, but plays ok against human. However, it does not guarantee never lose.

The whole program structure is similar to the basic version. But in this advanced version, since they are 9 boards, it uses an arraylist of integer arrays to represent the whole board. The arraylist keeps track of which board it is and the integer array represents a specific board. If AI plays first, it plays [1,5] as default.

The MiniMax search will be cutoff at depth 7, because this is the maximum depth that could make AI return a move in reasonable time. With depth of 8, it will take more than 10 seconds to finish searching so 8 is not a reasonable cutoff. With depth of 7, AI needs 3~4 seconds to make the move. As the game develops and the number of possible moves decreases, the search will be faster and faster. If it reaches this cutoff depth, it will use the heuristic function to calculate the score.

For each board, the heuristic function returns a value = (the number of possible row/column/diagonal the AI can win - the number of possible row/column/diagonal the player can win) (e.g, if for a certain row, there is one or two X but zero O, then X is possible to win that row). Then total score will be the sum of all the heuristic values of all 9 boards. If there are several moves with identical highest score, the AI will randomly pick one. Alpha-Beta pruning is also used in the search algorithm in order to improve the searching time and depth. During max’s turn, if the score is greater than alpha, update alpha; if the score is greater than beta, break and prune the tree. During min’s turn, if the score is lower than beta, update beta; if the score is lower than alpha, break and prune the tree. Using this method, the search can be more efficient and go to deeper levels.

Something that could improve significantly is the heuristic function. With current approach, the AI strategy is really passive and does not really know what to do in the beginning (lots of identical maximum scores). Also, it would be great if the structure and search algorithm could be simplified so that it could set a bigger depth limit to make the AI stronger.
