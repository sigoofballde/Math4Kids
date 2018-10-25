import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Secondary class that runs the actual math game
 * @method start: Create panels with questions to add to frame and adds first panel to frame
 * @class SubmitListener: Takes input from mouse/keyboard
 * @author Goofball
 *
 */
public class Game {
	private JFrame frame2; 
	private int limit, numQuestions, count = 0;
	private String button;
	private JPanel[] arrPanels;
	private Question[] question;
	
	/**
	 * Constructor that creates frame, question array, and sets incoming
	 * parameters to the private class variables
	 * @param limit: Number limit user chose. Answers are not allowed to be greater than this limit.
	 * @param numQuestions: Number of questions user chose.
	 * @param button: Button user clicked
	 */
	public Game(int limit, int numQuestions, String button) {
		this.limit = limit;
		this.numQuestions = numQuestions;
		this.button = button;
		this.question = new Question[numQuestions];
		frame2 = new JFrame(button);
	}
	
	/**
	 * Main function for individual game. Creates frame. Creates panel array with questions
	 * for attaching to the frame. Attaches first panel to the frame.
	 */
	public void start() {
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		arrPanels = new JPanel[numQuestions];
		question = Math4Kids.getQuestion(limit, numQuestions, button);

		
		for (int i = 0; i < numQuestions; i++) {
			JPanel subPanel2 = new JPanel();
			subPanel2.setBackground(Math4Kids.panelColor1);
			subPanel2.setPreferredSize(Math4Kids.panelDim1);


			String strQuestion = "";
			if (button == "Addition") {
				strQuestion = question[i].getFirstNumber() + " + " + question[i].getSecondNumber() + " = ";
			}
			if (button == "Subtraction") {
				strQuestion = question[i].getFirstNumber() + " - " + question[i].getSecondNumber() + " = ";
			}
			if (button == "Multiplication") {
				strQuestion = question[i].getFirstNumber() + " * " + question[i].getSecondNumber() + " = ";
			}
			if (button == "Division") {
				strQuestion = question[i].getFirstNumber() + " \u00F7 " + question[i].getSecondNumber() + " = ";
			}
			JLabel lblQuestion = new JLabel(strQuestion);
			lblQuestion.setBounds(10, 11, 75, 34);
			JTextField jtfAnswer = new JTextField(20);
			jtfAnswer.setBounds(85, 11, 30, 34);
			jtfAnswer.addKeyListener(new SubmitListener());
			
			JButton submit = new JButton("Submit");
			submit.setBounds(125, 150, 100, 50);
			submit.addActionListener(new SubmitListener());

			subPanel2.add(lblQuestion);
			subPanel2.add(jtfAnswer);
			subPanel2.add(submit);
			
	
			arrPanels[i] = subPanel2;
		}
		
		arrPanels[count].setLayout(null);
		frame2.getContentPane().add(arrPanels[count], BorderLayout.CENTER);
		
		frame2.pack();
		frame2.setLocationRelativeTo(null);
		frame2.setVisible(true);

	}
	
	/**
	 * Class to process button click or enter key press
	 * @method CheckAnswer: Contains logic for checking users answer and continuing
	 * game based on result 
	 * @author Goofball
	 *
	 */
	private class SubmitListener implements ActionListener, KeyListener{
		
		public SubmitListener() {
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			checkAnswer();
		}
		
		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_ENTER) {
				checkAnswer();
			}
		}
		
		@Override
		public void keyReleased(KeyEvent event) {
			
		}
		
		@Override
		public void keyTyped(KeyEvent event) {
			
		}
		
		/**
		 * Function to check user's answer and continue game based on result
		 */
		private void checkAnswer() {
			try {
				JTextField temp = null;
				Component[] components = arrPanels[count].getComponents();
				for (Component comp : components) {
					if (comp instanceof JTextField) {
						temp = (JTextField) comp;
					}
				}

				int cnvAnswer = Integer.valueOf(temp.getText());
				if (cnvAnswer == question[count].getAnswer()) {
					JOptionPane.showMessageDialog(frame2, "Correct");
					/**
					 * If we haven't reached the number of questions the user wanted then we remove the
					 * current panel and load the next one. Else we tell them great job and exit the game.
					 */
					if (count < (numQuestions-1)) {
						frame2.getContentPane().remove(arrPanels[count]);
						arrPanels[++count].setLayout(null);
						frame2.getContentPane().add(arrPanels[count], BorderLayout.CENTER);
						frame2.pack();
					} else {
						JOptionPane.showMessageDialog(null, "You have answered all the questions, Great Job!");
						frame2.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect, please try again");
				}
			
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Values entered must be valid numbers");
				
			}
		}
	}
}


		
