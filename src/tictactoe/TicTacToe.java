package tictactoe;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


public class TicTacToe implements ActionListener {

	private	boolean playerTurn; 	//true  - хід хрестиків, false - хід нуликів
	private boolean isAI = true;	//true  - грає ai
	private boolean isOver = false; //true  - кінець гри
	private boolean player1;		//визначаэмо за кого буде грати перший гравець
	private static final int DEFAULT_BOARDSIZE = 9; 
	private static int TOTALSCORE_PLAYER1 = 0;
	private static int TOTALSCORE_PLAYER2 = 0;
	private static final int[][] winCombination = setWinCombination(); 
	
	Random random = new Random();
	ImageIcon logo = new ImageIcon(getClass().getResource("/resources/logo.png"));
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel radio_panel = new JPanel();
	JPanel score_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JLabel scorePlayer1 = new JLabel();
	JLabel scorePlayer2 = new JLabel();
	JButton[] buttons = new JButton[DEFAULT_BOARDSIZE];
	JButton newGame = new JButton();
	JRadioButton playerPlayer = new JRadioButton("людина");
	JRadioButton aiPlayer = new JRadioButton("аі", true);
	ButtonGroup group = new ButtonGroup();
	

	TicTacToe() {
	
//налаштування панелі інформаційного текстового рядка 
		textfield.setBounds(0, 0, 400, 32);
		textfield.setForeground(new Color(25, 250, 0));
		textfield.setFont(new Font("Courier", Font.BOLD, 20));
		textfield.setHorizontalAlignment(JLabel.CENTER);		
		textfield.setText("Хрестики-Нолики");	
		textfield.setOpaque(false);
		
		title_panel.setBackground(new Color(60, 60, 60));
		title_panel.setLayout(null);
		title_panel.setBounds(0,0,400,32);
		title_panel.add(textfield);
		
		
//налаштування панелі рахунків
		scorePlayer1.setBounds(0, 0, 150, 75);
		scorePlayer1.setText(Integer.toString(TOTALSCORE_PLAYER1));
		scorePlayer1.setFont(new Font("MV Boli", Font.PLAIN,40));
		scorePlayer1.setHorizontalAlignment(JLabel.CENTER);

		scorePlayer2.setBounds(250, 0, 150, 75);
		scorePlayer2.setText(Integer.toString(TOTALSCORE_PLAYER2));
		scorePlayer2.setFont(new Font("MV Boli", Font.PLAIN,40));
		scorePlayer2.setHorizontalAlignment(JLabel.CENTER);		
		
		newGame.setBackground(new Color(120, 120, 120));
		newGame.setForeground(Color.green);
		newGame.setBounds(150, 0, 100, 75);
		newGame.setSize(100, 75);
		newGame.setText("Нова гра");
		newGame.setFocusable(false);
		newGame.addActionListener(this);	
		
		score_panel.setLayout(null);		
		score_panel.setBounds(0,32,400,75);
		score_panel.setBackground(new Color(140, 140, 140));
		score_panel.add(scorePlayer1);		
		score_panel.add(newGame);		
		score_panel.add(scorePlayer2);	
		
		
//налаштування панелі радіокнопок	
		playerPlayer.addActionListener(this);
		playerPlayer.setOpaque(false);

		aiPlayer.addActionListener(this);
		aiPlayer.setOpaque(false);
					
		group.add(playerPlayer);
		group.add(aiPlayer);

		radio_panel.setLayout(new FlowLayout());		
		radio_panel.setBounds(0,107,400,32);
		radio_panel.setBackground(Color.green);
		radio_panel.add(playerPlayer);
		radio_panel.add(aiPlayer);


		
//налаштування панелі кнопок
		button_panel.setLayout(new GridLayout(3, 3));
		button_panel.setBounds(0,139,400,400);
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFocusable(false);
			buttons[i].setBackground(new Color(99, 57, 116));
			buttons[i].setEnabled(false);
			buttons[i].addActionListener(this);						
		}
			
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(415, 577);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);		
		frame.setTitle("Хрестики-Нолики");
		frame.setIconImage(logo.getImage());
		
//додаємо панелі на екран		
		frame.add(title_panel);
		frame.add(radio_panel);
		frame.add(score_panel);
		frame.add(button_panel);
		
		frame.setVisible(true);

		player1 = firstTurn();
	}
  	
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource()==newGame) {
			reset();	
			return;
		}
		
		if(e.getSource()==playerPlayer) {
			isAI = false;
			return;
		} else if(e.getSource()==aiPlayer) {
			isAI = true;
			return;
		}
				
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			if (e.getSource() == buttons[i]) {				
				if (buttons[i].getName() == null) {
					if (playerTurn) {
						turnx(i);
					} else {
						turn0(i);
					}
//Якщо людина натиснула на порожню клітину то грає комп'ютер	
					if (isAI && !isOver) {
						aiPlaying();
					}
				
				}
	
			}
		}
	}

	public boolean firstTurn() {

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
		return playerTurn;

	}

//	хід за хрестики
	public void turnx(int i) {
		Icon icon = new ImageIcon(getClass().getResource("/resources/cross.png"));
//		Icon icon = new ImageIcon("img\\cross.png");
//		buttons[i].setText("X");
		buttons[i].setIcon(icon);
		buttons[i].setName("X");	
		textfield.setText("Хід 0");
		checkWinner();
		playerTurn = false;	
	}

//	хід за нулики
	public void turn0(int i) {		
		Icon icon = new ImageIcon(getClass().getResource("/resources/zero.png"));
//		Icon icon = new ImageIcon("img\\zero.png");
//		buttons[i].setText("0");
		buttons[i].setIcon(icon);
		buttons[i].setName("0");
		textfield.setText("Хід X");		
		checkWinner();
		playerTurn = true;		
	}

	public void checkWinner() {
		int fillButtons = 0;

// нічия може бути коли всі кнопки заповнені та відсутня переможна комбінація
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
//якщо перший гравець грає за хрестики то він переміг		
		if(player1 == playerTurn ){
			TOTALSCORE_PLAYER1++;
			scorePlayer1.setText(Integer.toString(TOTALSCORE_PLAYER1));
		} else {
			TOTALSCORE_PLAYER2++;
			scorePlayer2.setText(Integer.toString(TOTALSCORE_PLAYER2));
		}
		
		isOver = true;
	}

	public void Draw() {
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText("Нічия");
		isOver = true;
	}

//Комп'ютер виконує хід
	public void aiPlaying() {
//Для кожного можливого кроку знаходимо кінцевий стан		
		String[] copyButtons = convertToString();
//		System.out.println("******new click*****");
		
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

//	ініціалізація гри
	public void reset() {
		isOver = false;
		for (int i = 0; i < DEFAULT_BOARDSIZE; i++) {
			buttons[i].setEnabled(true);
			buttons[i].setName(null);
			buttons[i].setIcon(null);
			buttons[i].setBackground(new Color(99, 57, 116));
		}	
		
		if (playerTurn) {
			textfield.setText("Хід X");
		} else {
			textfield.setText("Хід 0");
		}
//Оновлюэмо за кого буде грати перший гравець		
		player1 = playerTurn;
	}
	
}

