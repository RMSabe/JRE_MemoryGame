/*
	Console Memory Game for Java Runtime Environment

	Author: Rafael Sabe
	Email: rafaelmsabe@gmail.com
*/

public class Card
{
	private String text;
	private int id;
	private int number;
	public boolean active;

	public Card(String text, int id, int number)
	{
		this.text = text;
		this.id = id;
		this.number = number;
		this.active = false;
	}

	public String getText()
	{
		return this.text;
	}

	public int getId()
	{
		return this.id;
	}

	public int getNumber()
	{
		return this.number;
	}
}
