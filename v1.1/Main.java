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
	public static JFrame appWindow = new JFrame();
	public static JPanel screen = new JPanel();
	public static JLabel text = new JLabel();

	public static Card[] cardArray = new Card[Definitions.numberCards];
	public static Card[] sortedArray = new Card[Definitions.numberCards];
	public static JButton[] cardButtonArray = new JButton[Definitions.numberCards];

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
			int index = 0;
			String tmp = "";

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

			for(index = 0; index < Definitions.numberCards; index++)
			{
				tmp = "GB";
				tmp += String.valueOf(index);

				if(cmd.equals(tmp))
				{
					gameCardClicked(index);
					break;
				}
			}
		}
	};

	public static void main(String[] args)
	{
		gameInitialize();
		screenInitialize();
		runtimeStatus = 0;

		appWindow.setSize(1280, 720);
		appWindow.setTitle("My Little Memory Game");
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.add(screen);
		appWindow.setVisible(true);

		paintMainScreen();
	}

	public static void gameInitialize()
	{
		Definitions.initializeCardTextArray();

		int cardId = 0;
		int cardNumber = 0;
		int cardIndex = 0;

		for(cardId = 0; cardId < Definitions.totalCardId; cardId++)
		{
			cardIndex = cardId*Definitions.numberPairs*2;

			for(cardNumber = 0; cardNumber < (Definitions.numberPairs*2); cardNumber++) 
				cardArray[cardIndex + cardNumber] = new Card(Definitions.cardTextArray[cardId], cardId, cardNumber);
		}
	}

	public static void screenInitialize()
	{
		text.setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
		text.setForeground(Color.BLACK);
		text.setLayout(screenLayout);
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setLocation(340, 20);
		text.setSize(600, 50);
		text.setVisible(true);

		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.addActionListener(actionManager);
		button.setActionCommand("BUTTON_START");
		button.setLayout(screenLayout);
		button.setLocation(590, 600);
		button.setSize(100, 20);

		String temptext = "";

		for(int i = 0; i < Definitions.numberCards; i++)
		{
			cardButtonArray[i] = new JButton();

			temptext = "GB";
			temptext += String.valueOf(i);

			cardButtonArray[i].addActionListener(actionManager);
			cardButtonArray[i].setActionCommand(temptext);
			cardButtonArray[i].setLayout(screenLayout);
			cardButtonArray[i].setSize(100, 20);
		}

		for(int i = 0; i < Definitions.numberCards; i += 8)
		{
			cardButtonArray[i].setLocation(170, (100 + 4*i));
			cardButtonArray[i + 1].setLocation(290, (100 + 4*i));
			cardButtonArray[i + 2].setLocation(410, (100 + 4*i));
			cardButtonArray[i + 3].setLocation(530, (100 + 4*i));
			cardButtonArray[i + 4].setLocation(650, (100 + 4*i));
			cardButtonArray[i + 5].setLocation(770, (100 + 4*i));
			cardButtonArray[i + 6].setLocation(890, (100 + 4*i));
			cardButtonArray[i + 7].setLocation(1010, (100 + 4*i));
		}

		screen.setSize(1280, 720);
		screen.setBackground(Color.LIGHT_GRAY);
		screen.setLayout(screenLayout);
		screen.add(text);

		for(int i = 0; i < Definitions.numberCards; i++) screen.add(cardButtonArray[i]);

		screen.add(button);
	}

	public static void startGame()
	{
		sortCards();
		remainingPairs = (Definitions.numberPairs*Definitions.totalCardId);
		cardSelected = false;

		for(int i = 0; i < Definitions.numberCards; i++)
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

	public static void gameCardClicked(int cardIndex)
	{
		if(cardSelected)
		{
			selectedCardIndex[1] = cardIndex;

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
			selectedCardIndex[0] = cardIndex;

			resetButtons();
			selectedCardButtons[0] = cardButtonArray[selectedCardIndex[0]];
			selectedCardButtons[0].setText(sortedArray[selectedCardIndex[0]].getText());
			selectedCardButtons[0].setBackground(Color.BLUE);
			selectedCardButtons[0].setForeground(Color.WHITE);
		}

		cardSelected = !cardSelected;
	}

	public static void paintMainScreen()
	{
		text.setText("Click the Button to Start");

		button.setText("Start");
		button.setVisible(true);

		for(int i = 0; i < Definitions.numberCards; i++) cardButtonArray[i].setVisible(false);
	}

	public static void paintGameScreen()
	{
		text.setText("Match the Pairs");

		button.setText("Return");
		button.setVisible(false);

		for(int i = 0; i < Definitions.numberCards; i++) cardButtonArray[i].setVisible(true);
	}

	public static void sortCards()
	{
		resetCards();
		int nSort = 0;
		int nCard = 0;

		while(nSort < Definitions.numberCards)
		{
			nCard = ThreadLocalRandom.current().nextInt(0, Definitions.numberCards);
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
		for(int i = 0; i < Definitions.numberCards; i++) cardArray[i].active = false;
	}

	public static void resetButtons()
	{
		for(int i = 0; i < Definitions.numberCards; i++)
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
