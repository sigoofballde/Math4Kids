import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * Main class for game. Creates the initial frame and panel for the game that
 * allows the user to choose which operation they choose to use, the number limit
 * they want to work within, and the number of questions.
 * 
 * @method start: Begins game, creates beginning frame
 * @method getQuestion: Gets array of questions for game
 * @author Goofball
 *
 */
public class Math4Kids {

	public static Dimension panelDim1 = new Dimension(350,250);
	public static JFrame frame1;
	public static Color panelColor1 = Color.PINK;
	private JTextField jtfLimit;
	private JTextField jtfNumQuestions;
	
	public Math4Kids(){
		
	}
	
	/* Function to start the program. Should create the first screen and ask for
	 * the math limit before calling next screen*/
	public void start () {
		frame1 = new JFrame("Math4Kids");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel subPanel1 = new JPanel();
		subPanel1.setBackground(panelColor1);
		subPanel1.setPreferredSize(panelDim1);
		
		
		
		/*Add sub panel to frame and display the frame*/
		frame1.getContentPane().add(subPanel1, BorderLayout.CENTER);
		subPanel1.setLayout(null);
		
		JLabel lblSelectNumber = new JLabel("Please select number limit for answers: ");
		lblSelectNumber.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblSelectNumber.setBounds(10, 11, 230, 34);
		subPanel1.add(lblSelectNumber);
		
		JButton btnAddition = new JButton("Addition");
		btnAddition.setBounds(10, 120, 131, 23);
		btnAddition.addActionListener(new ButtonListener());
		subPanel1.add(btnAddition);
		
		JButton btnSubtraction = new JButton("Subtraction");
		btnSubtraction.setBounds(10, 155, 131, 23);
		btnSubtraction.addActionListener(new ButtonListener());
		subPanel1.add(btnSubtraction);
		
		JButton btnDivision = new JButton("Division");
		btnDivision.setBounds(198, 120, 127, 23);
		btnDivision.addActionListener(new ButtonListener());
		subPanel1.add(btnDivision);
		
		JButton btnMultiplication = new JButton("Multiplication");
		btnMultiplication.setBounds(198, 155, 127, 23);
		btnMultiplication.addActionListener(new ButtonListener());
		subPanel1.add(btnMultiplication);
		
		
		
		jtfLimit = new JTextField();
		jtfLimit.setText("0");
		jtfLimit.setBounds(236, 18, 89, 20);
		subPanel1.add(jtfLimit);
		jtfLimit.setColumns(10);
		
		JLabel lblNumQuestions = new JLabel("Please select number of questions: ");
		lblNumQuestions.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblNumQuestions.setBounds(12, 57, 217, 16);
		subPanel1.add(lblNumQuestions);
		
		jtfNumQuestions = new JTextField();
		jtfNumQuestions.setText("10");
		jtfNumQuestions.setBounds(236, 57, 89, 20);
		subPanel1.add(jtfNumQuestions);
		jtfNumQuestions.setColumns(10);
		
		
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	
	/**
	 * Class to process the button click. 
	 * @author Goofball
	 *
	 */
	
	private class ButtonListener implements ActionListener{
	
		public ButtonListener() {

		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String cmd = event.getActionCommand();
			try {
				int cnvLimit = Integer.valueOf(jtfLimit.getText());
				int cnvNumQuestions = Integer.valueOf(jtfNumQuestions.getText());
				Game game = new Game(cnvLimit, cnvNumQuestions, cmd);
				game.start();
			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Values entered must be valid numbers");
			}
		}
	}
	
	/**
	 * Function to create an array of questions for the game.
	 * @param limit: Number limit user chose. Answers are not allowed to be greater than this limit.
	 * @param numQuestions: Number of questions user chose.
	 * @param op: Operator user chose.
	 * @return: Array of questions.
	 */
	public static Question[] getQuestion(int limit, int numQuestions, String op) {
		Question[] arrQuestion = new Question[numQuestions];
		for (int i=0; i < numQuestions; i++) {
			Question question = new Question();
			question.setFirstNumber((int)(Math.random()*limit));
			double answer = 0;
			boolean inLimit = false;
			int count = 0;
			while (!inLimit && count++ < 1000) {
				int secondNumber = (int)(Math.random()*limit);
				if (op == "Addition") {
					answer = question.getFirstNumber() + secondNumber;
					if (answer <= limit) {
						inLimit = true;
					}
				}
				if (op == "Subtraction") {
					question.setFirstNumber((int)(Math.random()*(limit*2)));
					if (question.getFirstNumber() == 0) {
						question.setFirstNumber((int)(Math.random()*limit));
					}
					secondNumber = (int)(Math.random()*question.getFirstNumber());
					answer = question.getFirstNumber() - secondNumber;
					if (answer <= limit && (limit >= 0 && answer > 0)) {
						inLimit = true;
					}
				}
				if (op == "Multiplication") {
					question.setFirstNumber((int)(Math.random()*(limit/5)));
					secondNumber = (int)(Math.random()*(limit/5));
					answer = question.getFirstNumber() * secondNumber;
					if (answer <= limit) {
						inLimit = true;
					}
				}
				if (op == "Division") {
					question.setFirstNumber((int)(Math.random()*(limit*5)));
					if (secondNumber == 0) {
						secondNumber = (int)(Math.random()*(limit/5));
					}
					answer = ((double)question.getFirstNumber() / secondNumber);
					if (answer <= limit && (answer == (int)answer)) {
						inLimit = true;
					}
				}
				if (inLimit) {
					question.setAnswer((int)answer);
					question.setSecondNumber(secondNumber);
					arrQuestion[i] = question;
				}
			}
			if (count >= 1000) {
				System.out.println("Count hit 1000");
				System.exit(0);
			}
		}


		return arrQuestion;
	}
	
	/**
	 * Begin the game by creating Math4Kids object and running the start method.
	 * @param args
	 */
	public static void main(String[] args) {
		Math4Kids runGame = new Math4Kids();
		runGame.start();
	}
}
