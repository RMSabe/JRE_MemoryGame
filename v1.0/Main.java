/*
	Memory Game for Java Runtime Environment

	Author: Rafael Sabe
	Email: rafaelmsabe@gmail.com
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class Main
{
	public static final Card card_1_1 = new Card("Fire", 1, 1);
	public static final Card card_1_2 = new Card("Fire", 1, 2);
	public static final Card card_2_1 = new Card("Person", 2, 1);
	public static final Card card_2_2 = new Card("Person", 2, 2);
	public static final Card card_3_1 = new Card("Building", 3, 1);
	public static final Card card_3_2 = new Card("Building", 3, 2);
	public static final Card card_4_1 = new Card("Computer", 4, 1);
	public static final Card card_4_2 = new Card("Computer", 4, 2);

	public static final int totalCardId = 4;
	public static final int numberPairs = 1;
	public static final int numberCards = (totalCardId*numberPairs*2);

	public static JFrame appWindow = new JFrame();
	public static JPanel screen = new JPanel();
	public static JLabel text = new JLabel();

	public static Card[] cardArray = new Card[numberCards];
	public static Card[] sortedArray = new Card[numberCards];
	public static JButton[] cardButtonArray = new JButton[numberCards];

	public static JButton[] selectedCardButtons = new JButton[2];
	public static int[] selectedCardIndex = new int[2];
	public static int runtimeStatus = 0;
	public static boolean cardSelected = false;
	public static int remainingPairs = 0;
	public static JButton button = new JButton();
	public static ScreenLayout screenLayout = new ScreenLayout();

	public static ActionListener actionManager = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			String cmd = event.getActionCommand();

			if(cmd.equals("BUTTON_START"))
			{
				switch(runtimeStatus)
				{
					case 0:
						paintGameScreen();
						startGame();
						runtimeStatus = 1;
						break;

					case 2:
						paintMainScreen();
						runtimeStatus = 0;
						break;
				}

				return;
			}

			if(cmd.equals("BUTTON_0"))
			{
				if(cardSelected) selectedCardIndex[1] = 0;
				else selectedCardIndex[0] = 0;
			}
			else if(cmd.equals("BUTTON_1"))
			{
				if(cardSelected) selectedCardIndex[1] = 1;
				else selectedCardIndex[0] = 1;
			}
			else if(cmd.equals("BUTTON_2"))
			{
				if(cardSelected) selectedCardIndex[1] = 2;
				else selectedCardIndex[0] = 2;
			}
			else if(cmd.equals("BUTTON_3"))
			{
				if(cardSelected) selectedCardIndex[1] = 3;
				else selectedCardIndex[0] = 3;
			}
			else if(cmd.equals("BUTTON_4"))
			{
				if(cardSelected) selectedCardIndex[1] = 4;
				else selectedCardIndex[0] = 4;
			}
			else if(cmd.equals("BUTTON_5"))
			{
				if(cardSelected) selectedCardIndex[1] = 5;
				else selectedCardIndex[0] = 5;
			}
			else if(cmd.equals("BUTTON_6"))
			{
				if(cardSelected) selectedCardIndex[1] = 6;
				else selectedCardIndex[0] = 6;
			}
			else if(cmd.equals("BUTTON_7"))
			{
				if(cardSelected) selectedCardIndex[1] = 7;
				else selectedCardIndex[0] = 7;
			}

			if(cardSelected)
			{
				if(selectedCardIndex[0] == selectedCardIndex[1]) resetButtons();
				else
				{
					selectedCardButtons[1] = cardButtonArray[selectedCardIndex[1]];
					selectedCardButtons[1].setText(sortedArray[selectedCardIndex[1]].getText());
					if(sortedArray[selectedCardIndex[0]].getId() == sortedArray[selectedCardIndex[1]].getId()) rightAnswer();
					else wrongAnswer();
				}
			}
			else
			{
				resetButtons();
				selectedCardButtons[0] = cardButtonArray[selectedCardIndex[0]];
				selectedCardButtons[0].setText(sortedArray[selectedCardIndex[0]].getText());
				selectedCardButtons[0].setBackground(Color.BLUE);
				selectedCardButtons[0].setForeground(Color.WHITE);
			}

			cardSelected = !cardSelected;
		}
	};

	public static void main(String[] args)
	{
		gameInitialize();
		screenInitialize();
		runtimeStatus = 0;

		appWindow.setSize(720, 360);
		appWindow.setTitle("My Little Memory Game");
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.add(screen);
		appWindow.setVisible(true);

		paintMainScreen();
	}

	public static void gameInitialize()
	{
		cardArray[0] = card_1_1;
		cardArray[1] = card_1_2;
		cardArray[2] = card_2_1;
		cardArray[3] = card_2_2;
		cardArray[4] = card_3_1;
		cardArray[5] = card_3_2;
		cardArray[6] = card_4_1;
		cardArray[7] = card_4_2;
	}

	public static void screenInitialize()
	{
		text.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		text.setForeground(Color.BLACK);
		text.setLayout(screenLayout);
		text.setLocation(60, 10);
		text.setSize(600, 50);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVisible(true);

		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.addActionListener(actionManager);
		button.setActionCommand("BUTTON_START");
		button.setLayout(screenLayout);
		button.setLocation(310, 260);
		button.setSize(100, 20);

		String temptext = "";

		for(int i = 0; i < numberCards; i++)
		{
			cardButtonArray[i] = new JButton();

			temptext = "BUTTON_";
			temptext += String.valueOf(i);

			cardButtonArray[i].addActionListener(actionManager);
			cardButtonArray[i].setActionCommand(temptext);
			cardButtonArray[i].setLayout(screenLayout);
			cardButtonArray[i].setSize(100, 20);
		}

		cardButtonArray[0].setLocation(250, 100);
		cardButtonArray[1].setLocation(370, 100);
		cardButtonArray[2].setLocation(250, 130);
		cardButtonArray[3].setLocation(370, 130);
		cardButtonArray[4].setLocation(250, 160);
		cardButtonArray[5].setLocation(370, 160);
		cardButtonArray[6].setLocation(250, 190);
		cardButtonArray[7].setLocation(370, 190);

		screen.setSize(720, 360);
		screen.setBackground(Color.LIGHT_GRAY);
		screen.setLayout(screenLayout);
		screen.add(text);
		screen.add(button);

		for(int i = 0; i < numberCards; i++) screen.add(cardButtonArray[i]);
	}

	public static void startGame()
	{
		sortCards();
		remainingPairs = (numberPairs*totalCardId);

		for(int i = 0; i < numberCards; i++)
		{
			cardButtonArray[i].setText("");
			cardButtonArray[i].setBackground(Color.WHITE);
			cardButtonArray[i].setForeground(Color.BLACK);
			cardButtonArray[i].setEnabled(true);
		}

		runtimeStatus = 1;
	}

	public static void endGame()
	{
		text.setText("Congratulations");
		button.setVisible(true);
		runtimeStatus = 2;
	}

	public static void paintMainScreen()
	{
		text.setText("Click the Button to Start");

		button.setText("Start");
		button.setVisible(true);

		for(int i = 0; i < numberCards; i++) cardButtonArray[i].setVisible(false);
	}

	public static void paintGameScreen()
	{
		text.setText("Match the Pairs");

		button.setText("Return");
		button.setVisible(false);

		for(int i = 0; i < numberCards; i++) cardButtonArray[i].setVisible(true);
	}

	public static void sortCards()
	{
		resetCards();
		int nSort = 0;
		int nCard = 0;

		while(nSort < numberCards)
		{
			nCard = ThreadLocalRandom.current().nextInt(0, numberCards);
			if(!cardArray[nCard].active)
			{
				sortedArray[nSort] = cardArray[nCard];
				cardArray[nCard].active = true;
				nSort++;
			}
		}
	}

	public static void resetCards()
	{
		for(int i = 0; i < numberCards; i++) cardArray[i].active = false;
	}

	public static void resetButtons()
	{
		for(int i = 0; i < numberCards; i++)
		{
			if(cardButtonArray[i].isEnabled())
			{
				cardButtonArray[i].setBackground(Color.WHITE);
				cardButtonArray[i].setForeground(Color.BLACK);
				cardButtonArray[i].setText("");
			}
		}
	}

	public static void rightAnswer()
	{
		selectedCardButtons[0].setBackground(Color.GREEN);
		selectedCardButtons[0].setForeground(Color.WHITE);
		selectedCardButtons[0].setEnabled(false);

		selectedCardButtons[1].setBackground(Color.GREEN);
		selectedCardButtons[1].setForeground(Color.WHITE);
		selectedCardButtons[1].setEnabled(false);

		remainingPairs--;

		if(remainingPairs == 0) endGame();
	}

	public static void wrongAnswer()
	{
		selectedCardButtons[0].setBackground(Color.RED);
		selectedCardButtons[0].setForeground(Color.WHITE);
		selectedCardButtons[1].setBackground(Color.RED);
		selectedCardButtons[1].setForeground(Color.WHITE);
	}
}
