/*
 * Author: Zhishen Pan
 * Date: 2017-02-15
 */

import java.util.*;

public class Basic_Minimax {

	Scanner input = new Scanner(System.in);
	
	List<Integer> availableMoves;
	List<Basic_Node> finalScore;
	int[] board;

	//Constructor
	public Basic_Minimax() {
		board = new int[9];
	}

	//check which position is empty (== 0)
	public List<Integer> getAvailableMove() {
		availableMoves = new ArrayList<>();
		for (int i = 0; i < 9; ++i) {
			if (board[i] == 0)
				availableMoves.add(i);
		}
		return availableMoves;
	}

	public void makeMove(int point, int player) {
		//board[i] == 2, player move
		//board[i] == 1, computer move
		board[point] = player;   
	}

	//best move = the move with highest score
	public int bestMove() {
		int max = -10;
		int best = -1;

		for (int i = 0; i < finalScore.size(); ++i) { 
			if (finalScore.get(i).score > max) {
				max = finalScore.get(i).score; 
				best = i;
			}
		}

		return finalScore.get(best).move;
	}

	//initial call to Minimax 
	public void callMinimax(int depth, int turn){
		finalScore = new ArrayList<Basic_Node>();
		minimax(depth, turn);
	}

	//Minimax algorithm 
	public int minimax(int depth, int turn) {

		List<Integer> availableMove = getAvailableMove();
		List<Integer> scores = new ArrayList<>(); 
		
		//if it reaches the roots 
		if (playerWon()) 
			return 1;
		if (comWon()) 
			return -1;
		if (availableMove.isEmpty()) 
			return 0; 

		//Recursive call for each possible move  
		for (int i = 0; i < availableMove.size(); ++i) {
			int point = availableMove.get(i);  

			//Computer's move 
			if (turn == 1) { 
				makeMove(point, 1); 
				int currentScore = minimax(depth + 1, 2);
				scores.add(currentScore);

				//if it reaches back to the top, store the score
				if (depth == 0) 
					finalScore.add(new Basic_Node(currentScore, point));
			} 
			
			//Player's turn
			else if (turn == 2) {
				makeMove(point, 2); 
				int currentScore = minimax(depth + 1, 1);
				scores.add(currentScore);
			}
			
			//reset the board
			board[point] = 0;
		}

		//Computers's turn, get maximum point 
		if(turn == 1)
			return getMax(scores); 
		//Player's turn, get maximum point 
		else
			return getMin(scores);
	}
	
	//Game over if someone wins, or board is full (draw)
	public boolean isGameOver() {
		return (playerWon() || comWon() || getAvailableMove().isEmpty());
	}

	// combinations that player will win (2)
	public boolean playerWon() {
		if(board[0] == 1 && board[1] == 1 && board[2] == 1)
			return true; 
		else if (board[3] == 1 && board[4] == 1 && board[5] == 1)
			return true; 
		else if (board[6] == 1 && board[7] == 1 && board[8] == 1)
			return true; 
		else if (board[0] == 1 && board[3] == 1 && board[6] == 1)
			return true; 
		else if (board[1] == 1 && board[4] == 1 && board[7] == 1)
			return true; 
		else if (board[2] == 1 && board[5] == 1 && board[8] == 1)
			return true; 
		else if (board[0] == 1 && board[4] == 1 && board[8] == 1)
			return true; 
		else if (board[2] == 1 && board[4] == 1 && board[6] == 1)
			return true; 
		else
			return false; 
	}

	// combinations that computer will win (1)
	public boolean comWon() {
		if(board[0] == 2 && board[1] == 2 && board[2] == 2)
			return true; 
		else if (board[3] == 2 && board[4] == 2 && board[5] == 2)
			return true; 
		else if (board[6] == 2 && board[7] == 2 && board[8] == 2)
			return true; 
		else if (board[0] == 2 && board[3] == 2 && board[6] == 2)
			return true; 
		else if (board[1] == 2 && board[4] == 2 && board[7] == 2)
			return true; 
		else if (board[2] == 2 && board[5] == 2 && board[8] == 2)
			return true; 
		else if (board[0] == 2 && board[4] == 2 && board[8] == 2)
			return true; 
		else if (board[2] == 2 && board[4] == 2 && board[6] == 2)
			return true; 
		else
			return false; 
	}

	//print the board, change integers into X&O
	public void printBoard(boolean userFirst) {

		String[] p = new String[9]; 

		for(int i=0;i<9;i++){
			if(board[i] == 0)
				p[i] = Integer.toString(i+1); 
			else if(board[i] == 1 && userFirst == false)
				p[i] = "X"; 
			else if(board[i] == 2 && userFirst == false)
				p[i] = "O";
			else if(board[i] == 1 && userFirst == true)
				p[i] = "O"; 
			else
				p[i] = "X"; 
		}

		System.err.print(p[0] + " | "+p[1] +" | "+ p[2]+"\n");
		System.err.print(p[3] + " | "+p[4] +" | "+ p[5]+"\n");
		System.err.print(p[6] + " | "+p[7] +" | "+ p[8]+"\n");
	}

	//get the minimum number from a list 
	public int getMin(List<Integer> list) {
		int min = 1;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i) < min)
				min = list.get(i);
		}
		return min;
	}

	//get the maximum number from a list
	public int getMax(List<Integer> list) {
		int max = -1;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i) > max)
				max = list.get(i);
		}
		return max;
	}

}

