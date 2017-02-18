/*
 * Author: Zhishen Pan
 * Date: 2017-02-15
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Advanced_Minimax {
	
	Scanner input = new Scanner(System.in); 

	List<Advanced_Point> availableMoves;
	List<Advanced_Node> finalScore;
	ArrayList<int[]> board;
	int preMove; 

	//Constructor; create a new 9x9 board 
	public Advanced_Minimax() {
		board = new ArrayList<int[]>();

		for(int i=0;i<9;i++){
			int[] matrix = new int[9];
			board.add(matrix);
		}
	}

	//return available moves
	public List<Advanced_Point> getAvailableMove() {
		availableMoves = new ArrayList<Advanced_Point>();
		int[] matrix = board.get(preMove); 
		boolean full = true; 

		//check if the previous matrix is full 
		for (int i = 0; i < 9; i++) {
			if (matrix[i] == 0){
				full = false; 
				availableMoves.add(new Advanced_Point(preMove, i));
			}
		}

		//if that board is not full, can only play in that matrix 
		if(full == false)
			return availableMoves; 
		
		//otherwise, can play in any board; 
		else{
			availableMoves = new ArrayList<Advanced_Point>();
			for(int i = 0;i < 9 ;i++){
				int[] matrix2 = board.get(i); 
				for(int j = 0; j < 9; j++){
					if(matrix2[j] == 0)
						availableMoves.add(new Advanced_Point(i,j)); 
				}
			}
		}
		return availableMoves;
	}


	//make move 
	//matrix[i] == 2, player move
	//matrix[i] == 1, computer move
	public void makeMove(Advanced_Point point, int player) {
		
		int[] matrix = board.get(point.matrix); 
		matrix[point.position] = player; 
		board.set(point.matrix, matrix); 
		
		//update preMove variable 
		preMove = point.position; 
	}

	//select the next move with highest score 
	public Advanced_Point bestMove() {
		int max = Integer.MIN_VALUE;
		int best = 0;

		//get the highest score 
		for (int i = 0; i < finalScore.size(); i++) { 
			if (finalScore.get(i).score > max) {
				max = finalScore.get(i).score; 
				best = i;
			}
		}

		//check if there are moves with identical highest scores; 
		List<Integer> index = new ArrayList<>();

		for(int i=0;i<finalScore.size();i++){
			if(finalScore.get(i).score == finalScore.get(best).score)
				index.add(i);
		}

		//if there are several moves with identical highest score, randomly pick one 
		Random ran = new Random(); 
		best = ran.nextInt(index.size());

		return finalScore.get(index.get(best)).point; 
	}

	//initial call to minimax algorithm 
	public void callMinimax(int depth, int turn){
		finalScore = new ArrayList<Advanced_Node>();
		minimax(depth, turn, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	//minimax with depth-limit search (cutoff at depth 7) and alpha beta pruning 
	public int minimax(int depth, int turn, int alpha, int beta) {

		List<Advanced_Point> availableMove = getAvailableMove();

		//if someone wins the game or there is no available move 
		if (comWon()) 
			return Integer.MAX_VALUE;
		if (playerWon()) 
			return Integer.MIN_VALUE;
		if (availableMove.isEmpty()) 
			return 0; 

		//if it reaches the cutoff depth, call the heuristic function to evaluate the score 
		if(depth == 7){
			int totalvalue = 0;
			for(int i=0;i<9;i++){
				int[] a = board.get(i);
				totalvalue = totalvalue + Heuristic.heuristic_score(a);
			}

			if(turn == 1)
				return totalvalue; 
			else 
				return -totalvalue; 
		}

		//Computer's turn, get maximum point 
		if (turn == 1) {
			int newBound = alpha; 

			//for each possible move
			for (int i = 0; i < availableMove.size(); ++i) {
				Advanced_Point point = availableMove.get(i);  

				//make the move 
				makeMove(point, 1); 
				//recursive call 
				int currentScore = minimax(depth + 1, 2, alpha, beta);

				//if it comes back to the top, add score to the final list 
				if (depth == 0) 
					finalScore.add(new Advanced_Node(currentScore, point));

				//update the alpha(lower bound) if currentScore is bigger
				newBound = Math.max(currentScore, newBound);

				//reset the board 
				int[] m = board.get(point.matrix); 
				m[point.position] = 0; 
				board.set(point.matrix, m);

				//if the score bigger than beta, prune tree
				if(newBound > beta)
					return beta;
			} 
			return newBound; 
		}

		//player's turn, get minimum point 
		else{
			int newBound = beta; 

			//for each possible move 
			for (int i = 0; i < availableMove.size(); ++i) {
				Advanced_Point point = availableMove.get(i);
				//make the move 
				makeMove(point, 2); 
				//recursive call 
				int currentScore = minimax(depth + 1, 1, alpha, beta);

				//update the upper bound if it's lower 
				newBound = Math.min(currentScore,newBound);

				//reset the board 
				int[] m = board.get(point.matrix); 
				m[point.position] = 0; 
				board.set(point.matrix, m);

				//if the score is bigger than alpha, prune tree
				if(newBound < alpha)
					return alpha;
			}
			return newBound; 
		}
	}


	//Game is over is someone wins, or whole board is full (draw)
	public boolean isGameOver() {
		return (comWon() || playerWon() || getAvailableMove().isEmpty());
	}

	//check if the computer wins the game (by winning any of the nine boards) 
	public boolean comWon(){
		for(int i=0;i<9;i++){
			int[] single = board.get(i); 
			boolean terminate = xWonSingle(single); 

			if(terminate == true)
				return true; 
		}
		return false; 
	}

	//check if player wins the game (by winning any of the nine boards) 
	public boolean playerWon(){
		for(int i=0;i<9;i++){
			int[] single = board.get(i); 
			boolean terminate = oWonSingle(single); 

			if(terminate == true)
				return true; 
		}
		return false; 
	}

	//check if the computer wins a board
	public boolean xWonSingle(int[] single) {
		if(single[0] == 1 && single[1] == 1 && single[2] == 1)
			return true; 
		else if (single[3] == 1 && single[4] == 1 && single[5] == 1)
			return true; 
		else if (single[6] == 1 && single[7] == 1 && single[8] == 1)
			return true; 
		else if (single[0] == 1 && single[3] == 1 && single[6] == 1)
			return true; 
		else if (single[1] == 1 && single[4] == 1 && single[7] == 1)
			return true; 
		else if (single[2] == 1 && single[5] == 1 && single[8] == 1)
			return true; 
		else if (single[0] == 1 && single[4] == 1 && single[8] == 1)
			return true; 
		else if (single[2] == 1 && single[4] == 1 && single[6] == 1)
			return true; 
		else
			return false; 
	}

	//check if player wins a board
	public boolean oWonSingle(int[] single) {
		if(single[0] == 2 && single[1] == 2 && single[2] == 2)
			return true; 
		else if (single[3] == 2 && single[4] == 2 && single[5] == 2)
			return true; 
		else if (single[6] == 2 && single[7] == 2 && single[8] == 2)
			return true; 
		else if (single[0] == 2 && single[3] == 2 && single[6] == 2)
			return true; 
		else if (single[1] == 2 && single[4] == 2 && single[7] == 2)
			return true; 
		else if (single[2] == 2 && single[5] == 2 && single[8] == 2)
			return true; 
		else if (single[0] == 2 && single[4] == 2 && single[8] == 2)
			return true; 
		else if (single[2] == 2 && single[4] == 2 && single[6] == 2)
			return true; 
		else
			return false; 
	}

	//display the board 
	public void printBoard(boolean userFirst) {
		ArrayList<String[]> complete = new ArrayList<String[]>(); 

		for(int i=0;i<9;i++){
			int[] matrix = board.get(i); 
			String[] p = new String[9]; 

			for(int j = 0;j<9;j++){
				if(matrix[j] == 0)
					p[j] = Integer.toString(j+1); 
				else if(matrix[j] == 1 && userFirst == false)
					p[j] = "X"; 
				else if(matrix[j] == 2 && userFirst == false)
					p[j] = "O";
				else if(matrix[j] == 1 && userFirst == true)
					p[j] = "O"; 
				else
					p[j] = "X"; 
			}
			complete.add(p); 
		}

		//first row 
		System.err.print(complete.get(0)[0] + " "+complete.get(0)[1] +" "+ complete.get(0)[2]
				+" | "+complete.get(1)[0] + " "+complete.get(1)[1] +" "+ complete.get(1)[2]
						+" | "+complete.get(2)[0] + " "+complete.get(2)[1] +" "+ complete.get(2)[2]+"\n");
		//second row 
		System.err.print(complete.get(0)[3] + " "+complete.get(0)[4] +" "+ complete.get(0)[5]
				+" | "+complete.get(1)[3] + " "+complete.get(1)[4] +" "+ complete.get(1)[5]
						+" | "+complete.get(2)[3] + " "+complete.get(2)[4] +" "+ complete.get(2)[5]+"\n");
		//third row 
		System.err.print(complete.get(0)[6] + " "+complete.get(0)[7] +" "+ complete.get(0)[8]
				+" | "+complete.get(1)[6] + " "+complete.get(1)[7] +" "+ complete.get(1)[8]
						+" | "+complete.get(2)[6] + " "+complete.get(2)[7] +" "+ complete.get(2)[8]+"\n");
		System.err.println("- - - - - - - - - - - ");
		//fourth row 
		System.err.print(complete.get(3)[0] + " "+complete.get(3)[1] +" "+ complete.get(3)[2]
				+" | "+complete.get(4)[0] + " "+complete.get(4)[1] +" "+ complete.get(4)[2]
						+" | "+complete.get(5)[0] + " "+complete.get(5)[1] +" "+ complete.get(5)[2]+"\n");
		//fifth row
		System.err.print(complete.get(3)[3] + " "+complete.get(3)[4] +" "+ complete.get(3)[5]
				+" | "+complete.get(4)[3] + " "+complete.get(4)[4] +" "+ complete.get(4)[5]
						+" | "+complete.get(5)[3] + " "+complete.get(5)[4] +" "+ complete.get(5)[5]+"\n");
		//six row 
		System.err.print(complete.get(3)[6] + " "+complete.get(3)[7] +" "+ complete.get(3)[8]
				+" | "+complete.get(4)[6] + " "+complete.get(4)[7] +" "+ complete.get(4)[8]
						+" | "+complete.get(5)[6] + " "+complete.get(5)[7] +" "+ complete.get(5)[8]+"\n");
		System.err.println("- - - - - - - - - - - ");
		//seventh row 
		System.err.print(complete.get(6)[0] + " "+complete.get(6)[1] +" "+ complete.get(6)[2]
				+" | "+complete.get(7)[0] + " "+complete.get(7)[1] +" "+ complete.get(7)[2]
						+" | "+complete.get(8)[0] + " "+complete.get(8)[1] +" "+ complete.get(8)[2]+"\n");
		//eighth row 
		System.err.print(complete.get(6)[3] + " "+complete.get(6)[4] +" "+ complete.get(6)[5]
				+" | "+complete.get(7)[3] + " "+complete.get(7)[4] +" "+ complete.get(7)[5]
						+" | "+complete.get(8)[3] + " "+complete.get(8)[4] +" "+ complete.get(8)[5]+"\n");
		//ninth row 
		System.err.print(complete.get(6)[6] + " "+complete.get(6)[7] +" "+ complete.get(6)[8]
				+" | "+complete.get(7)[6] + " "+complete.get(7)[7] +" "+ complete.get(7)[8]
						+" | "+complete.get(8)[6] + " "+complete.get(8)[7] +" "+ complete.get(8)[8]+"\n");		
	}
}
