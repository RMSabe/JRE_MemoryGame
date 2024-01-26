/*
	Memory Game for Java Runtime Environment

	Author: Rafael Sabe
	Email: rafaelmsabe@gmail.com
*/

//Empty object class, used just for instantiated object reference purpose.

import java.awt.*;

public class ScreenLayout implements LayoutManager
{
	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {}
}
