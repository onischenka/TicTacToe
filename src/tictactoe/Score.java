package tictactoe;

public class Score {
	private int score;
	private int position;
		
	
	Score(int score, int position) {
		this.score = score;
		this.position = position;
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}	
	
	int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


}
