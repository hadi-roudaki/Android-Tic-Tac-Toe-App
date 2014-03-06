package org.roudaki.tictadtoe;




public class TicTadToeModel {
	public enum Piece{X,O,_};
	private final int WIDTH=3;
	private final int HEIGHT=3;
	private Piece[][] board = new Piece[WIDTH][HEIGHT];
	private Piece currentPlayer = Piece.X;
	
	
	public TicTadToeModel(){
		reset();
		
	}
	public void  reset(){
		for (int i=0; i<WIDTH;i++){
			for (int j=0;j<HEIGHT;j++){
				board[i][j]=Piece._;
			}
		}
		
	}
	private void togglePlayer() {
		if (currentPlayer == Piece.X){
			currentPlayer= Piece.O;
		}else{
			currentPlayer= Piece.X;
		}
	}

	public Piece getCurrentPlayer(){
		return currentPlayer;
	}	
	
	public void setValue(int row, int col, Piece p){
		board[row][col]= p;
		togglePlayer();
	}
	
 public Piece getValue(int row, int col){
	 return board[row][col];
	 
 }

 
public Piece checkWinner(){
	Piece winner = Piece._;
	
	
	//rows
	for (int i=0; i<HEIGHT;i++){
		if (winner == Piece._ && board[i][0] == board[i][1] && board[i][1]== board[i][2])
			winner = board[i][0];
	}
	///cols
	for (int i=0; i<HEIGHT;i++){
		if (winner == Piece._ &&  board[0][i] == board[1][i] && board[1][i]== board[2][i])
			winner = board[0][i];	
	}
	//diag

	if (winner == Piece._ && board[0][0] == board[1][1] && board[1][1]== board[2][2])
		winner = board[0][0];
	if (winner ==Piece._ && board[0][2] == board[1][1] && board[1][1]== board[2][0])
		winner = board[1][1];	
	return winner;
	
 }
}
