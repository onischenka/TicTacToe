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
	boolean player_turn;
	boolean gameover = false;
	
	TicTacToe(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.getContentPane().setBackground(new Color(99,57,116));
		frame.setLayout(new BorderLayout());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		textfield.setBackground(new Color(60,60,60));
		textfield.setForeground(new Color(25,250,0));
		textfield.setFont(new Font("Comic Sans MS",Font.BOLD,22));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Хрестики-Нолики");
		textfield.setOpaque(true);
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);
		
		button_panel.setLayout(new GridLayout(3,3));
		//button_panel.setBackground(new Color(150,150,150));
		
		for(int i=0;i<9;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli", Font.BOLD,120));
			buttons[i].setFocusable(false);
			buttons[i].setBackground(new Color(99,57,116));
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
		
		for(int i=0;i<9;i++){
			if(e.getSource()==buttons[i]) {
				if(player_turn) {
					if( buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(0,0,225));
						buttons[i].setText("X");						
						textfield.setText("Хід 0");		
						check();
						player_turn = false;
					} 
				} else {
					if(buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(225,0,0));
						buttons[i].setText("0");						
						textfield.setText("Хід X");
						check();
						player_turn = true;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

/*активуємо кнопки для початку гри, якщо цього не робити, 
то гравець може почати гру до того як згенерується рандомно 
хто перший починає гру
*/
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(true);
		}
		
		if(random.nextInt(2)==0) {
			player_turn = false;
			textfield.setText("Хід 0");
		} else {
			player_turn = true;
			textfield.setText("Хід X");
		}
	
	}
	
	public void check() {
		int countFilledButtons=0;
		
		gameover = check_buttons_group(0,1,2,"X");
		gameover = check_buttons_group(3,4,5,"X");
		gameover = check_buttons_group(6,7,8,"X");		
		gameover = check_buttons_group(0,3,6,"X");
		gameover = check_buttons_group(1,4,7,"X");
		gameover = check_buttons_group(2,5,8,"X");
		gameover = check_buttons_group(0,4,8,"X");
		gameover = check_buttons_group(2,4,6,"X");

		gameover = check_buttons_group(0,1,2,"0");
		gameover = check_buttons_group(3,4,5,"0");
		gameover = check_buttons_group(6,7,8,"0");	
		gameover = check_buttons_group(0,3,6,"0");
		gameover = check_buttons_group(1,4,7,"0");
		gameover = check_buttons_group(2,5,8,"0");
		gameover = check_buttons_group(0,4,8,"0");
		gameover = check_buttons_group(2,4,6,"0");

/*нічія може бути коли всі кнопки заповнені та відсутня 
  переможна комбінація
*/		
		for(;countFilledButtons<9;countFilledButtons++) {
			if (buttons[countFilledButtons].getText()=="") {
				break;
			}			
		}
		
		if(countFilledButtons==9 && gameover==false) {
			Draw();
		}
		
	}
	
	public boolean check_buttons_group(int first, int second, int third, String turn) {
		boolean gameover = false;
		if( buttons[first].getText()==turn  &&
			buttons[second].getText()==turn &&
			buttons[third].getText()==turn  )
			{
				Wins(first,second,third,this.player_turn);
				gameover = true;
			}
		
		return gameover;
	}
	
	public void Wins(int first, int second, int third, boolean player) {
		buttons[first].setBackground(Color.GREEN);
		buttons[second].setBackground(Color.GREEN);
		buttons[third].setBackground(Color.GREEN);
		
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		if(player) {
			textfield.setText("Перемогли X");
		} else {
			textfield.setText("Перемогли 0");		
		}
		
	}
	
	public void Draw() {		
		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
			textfield.setText("Нічия");		
	}
	
}
