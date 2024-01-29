/*
	Console Memory Game for Java Runtime Environment

	Author: Rafael Sabe
	Email: rafaelmsabe@gmail.com
*/

public class Definitions
{
	public static final String card0Text = "Fire";
	public static final String card1Text = "Person";
	public static final String card2Text = "Building";
	public static final String card3Text = "Computer";
	public static final String card4Text = "Room";
	public static final String card5Text = "Shirt";
	public static final String card6Text = "Bed";
	public static final String card7Text = "Lamp";

	public static final int totalCardId = 8;
	public static final int numberPairs = 1;

	public static final int numberCards = (totalCardId*numberPairs*2);

	public static String[] cardTextArray = new String[totalCardId];

	public static void initCardTextArray()
	{
		cardTextArray[0] = card0Text;
		cardTextArray[1] = card1Text;
		cardTextArray[2] = card2Text;
		cardTextArray[3] = card3Text;
		cardTextArray[4] = card4Text;
		cardTextArray[5] = card5Text;
		cardTextArray[6] = card6Text;
		cardTextArray[7] = card7Text;
	}
}
