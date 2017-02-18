/*
 * Author: Zhishen Pan
 * Date: 2017-02-15
 */

import java.util.List;

public class Basic_Main {
	public static void main(String[] args) throws InterruptedException {

		while(true){
			Basic_Minimax game = new Basic_Minimax();
			boolean userFirst = true; 
			boolean legal = false; 
			
			//decide who plays first 
			System.err.println("New Game! Would you like to play X or O (X plays first)?");
			String user = game.input.next(); 

			//if computer plays first, default at position[5] 
			if((user.equalsIgnoreCase("o"))){
				userFirst = false; 
				game.makeMove(4, 1);
				game.printBoard(userFirst);
			}
			
			//if human plays first, print the board
			else
				game.printBoard(userFirst);

			while (!game.isGameOver()) {
				
				List<Integer> available = game.getAvailableMove();
				int userMove = 1; 
				
				//ask human for the next move; if valid, make the move
				while(legal == false){
					System.err.println("Your next move: ");
					userMove = game.input.nextInt() -1; 
					
					for(int i=0;i<available.size();i++){
						if(userMove == available.get(i)){
							legal = true; 
							break; 
						}
					}
				}
				game.makeMove(userMove, 2);
				game.printBoard(userFirst);
				legal = false; 

				//check if the game is over
				if (game.isGameOver()) 
					break;

				//calculate the best move 
				game.callMinimax(0, 1);

				//print the score of the each move 
				for(int i=0;i<game.finalScore.size();i++){
					System.err.println("Point:" + (game.finalScore.get(i).move+1) +" -> Score: "+game.finalScore.get(i).score); 
				}
				System.err.println();

				//pick and make the best move
				int comMove = game.bestMove(); 
				game.makeMove(comMove, 1);
				Thread.sleep(5); 
				System.out.println(comMove+1);
				Thread.sleep(5); 
				game.printBoard(userFirst);
			}
			
			//If the game is over, check win/lost/draw
			if (game.playerWon())
				System.err.println("Unfortunately, you lost!");
			else if (game.comWon())
				System.err.println("Congradulations, you win!.");
			else
				System.err.println("It's a draw!");
		}
	}

}
