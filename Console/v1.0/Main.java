/*
	Console Memory Game for Java Runtime Environment

	Author: Rafael Sabe
	Email: rafaelmsabe@gmail.com
*/

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main
{
	public static Scanner keyboard = new Scanner(System.in);

	public static Card[] cardArray = new Card[Definitions.numberCards];
	public static Card[] sortedArray = new Card[Definitions.numberCards];

	public static int[] selectedCardIndex = new int[2];
	public static int index = 0;

	public static int runtimeStatus = 0;
	public static int remainingPairs = 0;
	public static boolean cardSelected = false;

	public static String input = "";

	public static void main(String[] args)
	{
		initCardArray();
		startGame();
		while(runtimeLoop());
	}

	public static boolean runtimeLoop()
	{
		input = keyboard.nextLine();

		if(input.equals("quit")) return false;

		if(runtimeStatus == 1 && input.equals("start"))
		{
			startGame();
			return true;
		}
		else if(runtimeStatus == 0)
		{
			if(input.equals("remain"))
			{
				printRemainersList();
				return true;
			}

			if(input.equals("help"))
			{
				printGameCommands();
				return true;
			}

			try
			{
				index = Integer.parseInt(input);
			}
			catch(Exception e)
			{
				System.out.println("Invalid value entered\n");
				return true;
			}

			if(index < 0 || index > (Definitions.numberCards - 1))
			{
				System.out.println("Invalid card index\n");
				return true;
			}

			if(!sortedArray[index].active)
			{
				System.out.println("This card has already been solved\n");
				return true;
			}

			onCardPicked(index);

			if(remainingPairs == 0) endGame();

			return true;
		}

		System.out.println("Invalid command entered\n");
		return true;
	}

	public static void initCardArray()
	{
		Definitions.initCardTextArray();

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

	public static void startGame()
	{
		sortCards();
		cardSelected = false;
		runtimeStatus = 0;
		remainingPairs = Definitions.totalCardId*Definitions.numberPairs;

		System.out.println("Welcome to my memory game Console Version.");
		printGameCommands();
	}

	public static void endGame()
	{
		System.out.println("Congratulations");
		System.out.println("Enter \"start\" to start a new game");
		System.out.println("Enter \"quit\" to quit application\n");
		runtimeStatus = 1;
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

	public static void onCardPicked(int cardIndex)
	{
		if(cardSelected)
		{
			selectedCardIndex[1] = cardIndex;

			if(selectedCardIndex[0] == selectedCardIndex[1]) System.out.println("Card " + sortedArray[selectedCardIndex[0]].getText() + " unselected");
			else
			{
				System.out.println("Selected Card: " + sortedArray[selectedCardIndex[1]].getText());
				if(sortedArray[selectedCardIndex[0]].getId() == sortedArray[selectedCardIndex[1]].getId()) rightAnswer();
				else wrongAnswer();
			}
		}
		else 
		{
			selectedCardIndex[0] = cardIndex;
			System.out.println("Selected Card: " + sortedArray[selectedCardIndex[0]].getText());
		}

		System.out.print("\n");
		cardSelected = !cardSelected;
	}

	public static void rightAnswer()
	{
		System.out.println("The pairs matched!");
		sortedArray[selectedCardIndex[0]].active = false;
		sortedArray[selectedCardIndex[1]].active = false;

		remainingPairs--;
	}

	public static void wrongAnswer()
	{
		System.out.println("The pairs don't match! Try again!");
	}

	public static void printRemainersList()
	{
		System.out.println("The following cards are remaining:");

		for(int i = 0; i < Definitions.numberCards; i++) if(sortedArray[i].active) System.out.print(String.valueOf(i) + " ");

		System.out.println("\n");
	}

	public static void printGameCommands()
	{
		System.out.println("There are " + String.valueOf(Definitions.numberCards) + " cards with match pairs that you need to find.");
		System.out.println("To select a card, enter the card index number (any number from 0 to " + String.valueOf((Definitions.numberCards - 1)) + ").");
		System.out.println("To list the remaining pairs, enter \"remain\".");
		System.out.println("To print this command list, enter \"help\".");
		System.out.println("To quit application, enter \"quit\".\n");
	}
}
