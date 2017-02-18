/*
 * Author: Zhishen Pan
 * Date: 2017-02-15
 */

import java.util.List;

public class Advanced_Main {
	public static void main(String[] args) throws InterruptedException {

		//infinite loop 
		while(true){
			Advanced_Minimax game = new Advanced_Minimax();
			boolean userFirst = true; 

			System.err.println("New Game! Would you like to play X or O (X plays first)?");
			String user = game.input.next(); 

			//check who will play first 
			//if computer plays first, default at [1,5] 
			if((user.equalsIgnoreCase("o"))){
				userFirst = false; 
				game.makeMove(new Advanced_Point(0,4), 1);
				game.printBoard(userFirst);
			}

			else
				game.printBoard(userFirst);

			boolean first = true;

			while (!game.isGameOver()) {

				//print out possible move for players (except the first time)
				List<Advanced_Point> legalmove = game.getAvailableMove(); 
				if(first == false){
					System.err.print("Possible Move:");
					for(int i = 0;i<legalmove.size();i++)
						System.err.print(legalmove.get(i)+",");
					System.err.println();
				}

				boolean legal = false; 
				int userMatrix = -1;
				int userPosition = -1; 

				//if it's the first move,ask for a move  
				if(first){
					System.err.println("Your move: ");
					userMatrix = game.input.nextInt() - 1; 
					userPosition = game.input.nextInt() - 1; 
					first = false; 
				}
				else{
					//check if the input move is legal 
					while(legal == false){
						System.err.println("Your move: ");
						userMatrix = game.input.nextInt() - 1; 
						userPosition = game.input.nextInt() - 1; 

						for(int i=0;i<legalmove.size();i++){
							if(userMatrix == legalmove.get(i).matrix && userPosition == legalmove.get(i).position){
								legal = true; 
								break; 
							}
						}
					}
				}

				//make the move and print the board 
				game.makeMove(new Advanced_Point(userMatrix, userPosition), 2);
				game.printBoard(userFirst);

				//check if the game is over
				if (game.isGameOver()) 
					break;

				//calculate the scores for computer's next move 
				game.callMinimax(0, 1);

				//print out the scores 
				for(int i=0;i<game.finalScore.size();i++){
					System.err.println("Point:" + (game.finalScore.get(i).point) +" -> Score: "+game.finalScore.get(i).score); 
				}
				System.err.println();

				//make the moves with highest score 
				Advanced_Point comMove = game.bestMove(); 
				game.makeMove(comMove, 1);

				//print out the computer's move 
				Thread.sleep(5); 
				System.out.println(comMove.matrix + 1);
				System.out.println(comMove.position + 1);
				Thread.sleep(5); 
				System.err.println();
				
				//print out the board after the move 
				game.printBoard(userFirst);

				//check if the game is over 
				if (game.isGameOver()) 
					break;
			}

			//If the game is over, check win/lost/draw
			if (game.playerWon())
				System.err.println("Congradulations, you win!.");

			else if (game.comWon())
				System.err.println("Unfortunately, you lost!");
			else
				System.err.println("It's a draw!"); 
		}
	}
}
