import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] buttons = new JButton[9];
	boolean player_turn, isComputer = true;

	TicTacToe() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
//	frame.getContentPane().setBackground(new Color(99,57,116));
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		textfield.setBackground(new Color(60, 60, 60));
		textfield.setForeground(new Color(25, 250, 0));
		textfield.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Хрестики-Нолики");
		textfield.setOpaque(true);
		title_panel.setLayout(new BorderLayout());
//		title_panel.setBounds(0,0,800,100);

		button_panel.setLayout(new GridLayout(3, 3));
		// button_panel.setBackground(new Color(150,150,150));

		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
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

		for (int i = 0; i < 9; i++) {
			if (e.getSource() == buttons[i]) {
				if (buttons[i].getText() == "") {
					if (player_turn) {
						turnx(i);
					} else {
						turn0(i);
					}

				}

				if (isComputer) {
					comuterPlaying();
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
		for (int i = 0; i < 9; i++) {
			buttons[i].setEnabled(true);
		}

		if (random.nextInt(2) == 0) {
			player_turn = false;
			textfield.setText("Хід 0");
		} else {
			player_turn = true;
			textfield.setText("Хід X");
		}

	}

//	хід за хрестики
	public void turnx(int i) {
		buttons[i].setForeground(new Color(0, 0, 225));
		buttons[i].setText("X");
		textfield.setText("Хід 0");
		checkWinner();
		player_turn = false;
	}

//	хід за нулики
	public void turn0(int i) {
		buttons[i].setForeground(new Color(225, 0, 0));
		buttons[i].setText("0");
		textfield.setText("Хід X");
		checkWinner();
		player_turn = true;
	}

	public void checkWinner() {
		int fillButtons = 0;

		/*
		 * нічия може бути коли всі кнопки заповнені та відсутня переможна комбінація
		 */
		for (fillButtons = 0; fillButtons < 9; fillButtons++) {
			if (buttons[fillButtons].getText() == "") {
				break;
			}
		}
//	якщо всі клітинки заповнені то може бути нічия
		if (fillButtons == 9) {
			Draw();
		}
//перевіряємо що група кнопок має переможну комбінацію			
		checkWinnerCombination(0, 1, 2);
		checkWinnerCombination(3, 4, 5);
		checkWinnerCombination(6, 7, 8);
		checkWinnerCombination(0, 3, 6);
		checkWinnerCombination(1, 4, 7);
		checkWinnerCombination(2, 5, 8);
		checkWinnerCombination(0, 4, 8);
		checkWinnerCombination(2, 4, 6);

	}

	public void checkWinnerCombination(int first, int second, int third) {

		if (buttons[first].getText() == "X" && buttons[second].getText() == "X" && buttons[third].getText() == "X"
				|| buttons[first].getText() == "0" && buttons[second].getText() == "0"
						&& buttons[third].getText() == "0") {
			Wins(first, second, third);
		}

	}

	public void Wins(int first, int second, int third) {
		buttons[first].setBackground(Color.GREEN);
		buttons[second].setBackground(Color.GREEN);
		buttons[third].setBackground(Color.GREEN);

		for (int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
		if (player_turn) {
			textfield.setText("Перемогли X");
		} else {
			textfield.setText("Перемогли 0");
		}
		isComputer = false;
	}

	public void Draw() {
		for (int i = 0; i < 9; i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText("Нічия");
	}

	public void comuterPlaying() {
		for (int i = 0; i < 9; i++) {
			if (buttons[i].getText() == "") {
				if (player_turn) {
					turnx(i);
				} else {
					turn0(i);
				}
				break;
			}
		}
	}
}
