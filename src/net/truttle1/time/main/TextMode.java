package net.truttle1.time.main;

import java.awt.Color;
import java.awt.Graphics;

import net.truttle1.time.effects.Fade;

public class TextMode extends GameMode{

	int x;
	int y;
	String str;
	String[] splitString;
	public TextMode(Game window) {
		super(window);
		// TODO Auto-generated constructor stub
	}

	public void setup(String text, int x, int y)
	{
		str = text;
		this.x = x;
		this.y = y;
		Fade.run(window, ModeType.Text, true);
	}
	@Override
	public void tick() {
		if(Global.zPressed)
		{
			Fade.run(window, ModeType.Overworld, false);
			if(Quest.quests[Quest.ESCAPED] == 3)
			{
				Quest.quests[Quest.ESCAPED]++;
				Global.comingFromTime = -1;
			}
		}
		
	}
	@Override
	public void render(Graphics g) {

		if(str != null)
		{
			splitString = str.split("/");
		}
		g.setColor(Color.black);
		g.fillRect(0, 0, window.WIDTH, window.HEIGHT);
		for(int i=0; i<splitString.length; i++)
		{
			if(splitString[i] != null)
			{
				g.setColor(Color.gray);
				g.setFont(Global.winFont2);
				g.drawString(splitString[i], x-4, (y-4)+(i*80));
				g.setColor(Color.white);
				g.setFont(Global.winFont2);
				g.drawString(splitString[i], x, y+(i*80));
			}
		}
	}

}
