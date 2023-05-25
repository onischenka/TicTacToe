package tictactoe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TicTacToe implements ActionListener {

	private	boolean playerTurn, isComputer = true;
	private static final int DEFAULT_BOARDSIZE = 9; 
	private static final int[][] winCombination = setWinCombination(); 
	
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] buttons = new JButton[DEFAULT_BOARDSIZE];


	TicTacToe() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
//	frame.getContentPane().setBackground(new Color(99,57,116));
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		textfield.setBackground(new Color(60, 60, 60));
		textfield.setForeground(new Color(25, 250, 0));
		textfield.setFont(new Font("Calibri", Font.BOLD, 22));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Хрестики-Нолики");
		textfield.setOpaque(true);
		title_panel.setLayout(new BorderLayout());
//		title_panel.setBounds(0,0,800,100);

		button_panel.setLayout(new GridLayout(3, 0));
		// button_panel.setBackground(new Color(150,150,150));

		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
//			buttons[i].setFont(new Font("Microsoft Yi Baiti", Font.CENTER_BASELINE, 100));
			buttons[i].setFocusable(false);
			buttons[i].setBackground(new Color(99, 57, 116));
			buttons[i].setEnabled(false);
			buttons[i].addActionListener(this);
						
		}

		title_panel.add(textfield);
		frame.add(title_panel, BorderLayout.NORTH);
		frame.add(button_panel);

		firstTurn();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			if (e.getSource() == buttons[i]) {
				if (buttons[i].getName() == null) {
					if (playerTurn) {
						turnx(i);
					} else {
						turn0(i);
					}
//Якщо людина натиснула на порожню клітину то грає комп'ютер	
					if (isComputer) {
						comuterPlaying();
					}
				
				}
	
			}
		}
	}

	public void firstTurn() {

//виводимо назву та починаємо гру
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * активуємо кнопки для початку гри, якщо цього не робити, то гравець може
		 * почати гру до того як згенерується рандомно хто перший починає гру
		 */
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i].setEnabled(true);
		}

		if (random.nextInt(2) == 0) {
			playerTurn = false;
			textfield.setText("Хід 0");
		} else {
			playerTurn = true;
			textfield.setText("Хід X");
		}

	}

//	хід за хрестики
	public void turnx(int i) {
		File file = new File("src\\tictactoe\\img\\cross.png");
		Icon icon = new ImageIcon(file.getAbsolutePath());
		buttons[i].setForeground(new Color(0, 0, 225));
//		buttons[i].setText("X");
		buttons[i].setIcon(icon);
		buttons[i].setName("X");
		textfield.setText("Хід 0");
		checkWinner();
		playerTurn = false;	
	}

//	хід за нулики
	public void turn0(int i) {		
		File file = new File("src\\tictactoe\\img\\zero.png");
		Icon icon = new ImageIcon(file.getAbsolutePath());
		buttons[i].setForeground(new Color(225, 0, 0));
//		buttons[i].setText("0");
		buttons[i].setIcon(icon);
		buttons[i].setName("0");
		textfield.setText("Хід X");		
		checkWinner();
		playerTurn = true;		
	}

	public void checkWinner() {
		int fillButtons = 0;

		/*
		 * нічия може бути коли всі кнопки заповнені та відсутня переможна комбінація
		 */
		for (fillButtons = 0; fillButtons < DEFAULT_BOARDSIZE; fillButtons++) {
			if (buttons[fillButtons].getName() == null) {
				break;
			}
		}
//	якщо всі клітинки заповнені то може бути нічия
		if (fillButtons == DEFAULT_BOARDSIZE) {
			Draw();
		}
//перевіряємо що група кнопок має переможну комбінацію			
		for(int i = 0; i < winCombination.length; i++) {
			checkWinnerCombination(winCombination[i][0], winCombination[i][1], winCombination[i][2]);
//			System.out.println(winCombination[i][0] + "_" + winCombination[i][1] + "_" + winCombination[i][2]);
		}
			
	}


	public void checkWinnerCombination(int first, int second, int third) {

		if (buttons[first].getName() == buttons[second].getName()  
				&& buttons[first].getName() == buttons[third].getName()
				&& buttons[first].getName() != null) {
			Wins(first, second, third);
		}
	}

	public void Wins(int first, int second, int third) {
		buttons[first].setBackground(new Color(173,254,47));
		buttons[second].setBackground(new Color(127,255,0));
		buttons[third].setBackground(new Color(0,255,0));

		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i].setEnabled(false);
		}
		if (playerTurn) {
			textfield.setText("Перемогли X");
		} else {
			textfield.setText("Перемогли 0");
		}
		isComputer = false;
	}

	public void Draw() {
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText("Нічия");
	}

//Комп'ютер виконує хід
	public void comuterPlaying() {

//Для кожного можливого кроку знаходимо кінцевий стан		
		String[] copyButtons = convertToString();
		System.out.println("******new click*****");
		
//test 	
//		copyButtons[0] = "0";
//		copyButtons[1] = "0";
//		copyButtons[2] = "X";
//		copyButtons[3] = "X";
//		copyButtons[4] = null;
//		copyButtons[5] = "0";
//		copyButtons[6] = null;
//		copyButtons[7] = null;
//		copyButtons[8] = "X";
//		playerTurn = false;
//		true - X , false  - 0
//		!playerTurn - повертаємо значення ходу людини;
		Score bestScore = minimax(copyButtons, !playerTurn, 0);
		System.out.println(bestScore.getPosition());
		if (playerTurn) {
			turnx(bestScore.getPosition());
		} else {
			turn0(bestScore.getPosition());
		}

	}

	public String[] convertToString() {
		String[] copyButtons = new String[buttons.length];
		for(int i = 0; i < buttons.length; i++) {			
			if (buttons[i].getName() != null) {
				copyButtons[i] = buttons[i].getName();

			}
		}

		return copyButtons;
	}

	public void printArray(String[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println("[" + i + "]" + " = " + array[i]);
		}
		System.out.println("-------");

	}

	public Score minimax(String[] moves, boolean oppTurn, int deep) {
				
		String[] copyMoves = moves.clone();
		int bestScore, bestPosition = 0;
		oppTurn = oppTurn ? false : true;	
		
		if(playerTurn) {
			bestScore = (oppTurn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		} else {
			bestScore = (!oppTurn) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}    
	    

//		System.out.println("--->>Deep  " + deep);
//		printArray(moves);
		deep++;

		boolean isOver = isOver(moves);
		if (isOver) {
			
			bestScore = valuation(moves, oppTurn);
		} else {
		
			for (int i = 0; i < moves.length; i++) {			
				if (moves[i] == null) {
					if(oppTurn) copyMoves[i] = "X"; else copyMoves[i] = "0"; 
//					System.out.println(i);
//					if(deep == 1) {
//						System.out.println("best>>>" + bestScore);
//					}
					Score score = minimax(copyMoves, oppTurn , deep);	
					copyMoves[i] = null;
//					if(deep == 1) {
//						System.out.println("Score: " + score.getScore());
//						System.out.println("Posit: " + score.getPosition());	
//					}		
										
					
					if(!playerTurn == oppTurn) {
						if (score.getScore() < bestScore) {
							bestScore = score.getScore();
							bestPosition = i;									
						}
					} else {						
						if (score.getScore() > bestScore) {
							bestScore = score.getScore();
							bestPosition = i;									
						}
					}
			
					
//					if(deep == 1) {
//						System.out.println("BestScore:" +  bestScore);	
//						System.out.println("BestPosition:" +  bestPosition);	
//					}

					
				}
			}
		}	
	
		return new Score(bestScore, bestPosition);
	}
	
	public int valuation(String[] moves, boolean oppTurn) {
		int sign = 1;
		int score = 0;
		
		
		if(oppTurn == playerTurn ) {
			sign *=-1;
		}
		
		//перевіряємо на переможну комбінацію			
		for(int i=0; i < winCombination.length; i++) {
			if ( moves[winCombination[i][0]] == moves[winCombination[i][1]] 
			  && moves[winCombination[i][0]] == moves[winCombination[i][2]] 
			  && moves[winCombination[i][0]] != null) {				
				score = 1;				
			}			
		}	
		
		return score * sign;
	}
	
	public boolean isOver(String[] moves) {
		
		//перевіряємо на переможну комбінацію			
		for(int i=0; i < winCombination.length; i++) {
			if ( moves[winCombination[i][0]] == moves[winCombination[i][1]] 
			  && moves[winCombination[i][0]] == moves[winCombination[i][2]] 
			  && moves[winCombination[i][0]] != null) {
				
				return true;
				
			}			
		}		
		
		//перевіряємо на нічію, якщо відсутні порожні то нічія	
		for (int i=0; i < moves.length; i++) {
			if (moves[i] == null) {
				return false;
			}
		}
				
		return true;
	}
	
	static int[][] setWinCombination(){
		
		int[][] winCombination = {
				{0,1,2},
				{3,4,5},{6,7,8},				
				{0,3,6},{1,4,7},{2,5,8},
				{0,4,8},{2,4,6}
		}; 
		return winCombination;
	}


}

