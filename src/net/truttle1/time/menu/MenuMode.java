package net.truttle1.time.menu;

import java.awt.Color;
import java.awt.Graphics;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.GraphicsLoader;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.warps.StoneAgeWarps;

public class MenuMode extends GameMode{

	private int selection;
	public boolean loading;
	public boolean ready;
	public static String currentText;
	private GraphicsLoader g;
	public MenuMode(Game window) {
		super(window);
		this.init();
	}
	public void init()
	{
		selection = 0;
		loading = false;
		ready = false;
	}
	@Override
	public void tick() {
		if(loading)
		{
			runLoad();
		}
		else
		{
			runSelect();
		}
	}

	@Override
	public void render(Graphics g) {

		if(loading)
		{
			renderLoad(g);
		}
		else
		{
			renderSelect(g);
		}
	}

	private void runLoad()
	{
		if(selection == 0)
		{
			if(ready && Global.zPressed)
			{
				Global.disableMovement = false;
				Fade.run(window, ModeType.Overworld, false);
				Global.comingFromTime = 0;
				OverworldAnimation.sign = OverworldAnimation.loadAnimation("/overworld/bg/sign",1,window);
			}
		}
		if(selection == 3)
		{
			Global.comingFromTime = 3;
			if(ready && Global.zPressed)
			{
				Global.disableMovement = false;
				Fade.run(window, ModeType.Overworld, false);
				Global.comingFromTime = 3;
				OverworldAnimation.sign = OverworldAnimation.loadAnimation("/overworld/bg/sign1",1,window);
				
			}
		}
	}
	private void renderLoad(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1024, 576);
		if(!ready)
		{
			g.setColor(Color.WHITE);
			g.setFont(Global.winFont2);
			g.drawString("Transporting...", 32, 495);
			g.setFont(Global.battleFont);
			g.drawString("But this needs to be loaded first... ", 32, 535);
			g.drawString(currentText, 32, 555);
		}
		else
		{

			g.setFont(Global.winFont2);
			g.setColor(Color.gray);
			g.drawString("Press Z!", 128, 495);
			g.setColor(Color.white);
			g.drawString("Press Z!", 124, 491);

			g.setFont(Global.winFont2);
			g.setColor(Color.gray);
			g.drawString("WARP COMPLETE!", 128, 195);
			g.setColor(Color.white);
			g.drawString("WARP COMPLETE!", 124, 191);
		}
	}

	private void runSelect()
	{
		if(Global.downPressed)
		{
			if(selection>3)
			{
				selection = 0;
			}
			else
			{
				selection++;
			}
		}
		if(Global.upPressed)
		{
			if(selection == 0)
			{
				selection = 4;
			}
			else
			{
				selection--;
			}
		}
		if(Global.zPressed)
		{
			if(selection == 0)
			{
				loading = true;
				ready = false;
				Global.comingFromTime = 0;
			}
			if(selection == 3)
			{
				loading = true;
				ready = false;
				Global.comingFromTime = 3;
			}
			g = new GraphicsLoader(window);
			g.start();
		}
	}
	private void renderSelect(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1024, 576);
		g.setColor(Color.WHITE);
		g.setFont(Global.winFont2);
		g.drawString("Select a Time Period!", 48, 72);
		g.fillRect(32, 128+(selection*80), 960, 64);
		g.setColor(Color.GRAY);
		g.setFont(Global.winFont1);
		g.drawString("Stone Age", 32, 170);
		g.drawString("???????? Age", 32, 245);
		g.drawString("??? ???? Age", 32, 330);
		g.drawString("Digital Age", 32, 410);
		g.drawString("??? ??? ??????", 32, 490);

		g.setColor(Color.BLACK);
		g.setFont(Global.battleFont);
		g.drawString("The primative age of cavemen. The landscape is", 320, 145);
		g.drawString("mountainous, there is only one major settlement, and", 320, 165);
		g.drawString("technology is pretty much nonexistant.", 320, 185);

		g.drawString("This time period is locked. Sorry about that.", 360, 230);

		g.drawString("This time period is locked. Sorry about that.", 360, 310);
		

		g.drawString("The technological revolution is in full swing! There's", 320, 385);
		g.drawString("computers, phone computers, and spying computers!", 320, 405);
		g.drawString("If something can be done, there's a computer for it.", 320, 425);
		
		g.drawString("This time period is locked. Sorry about that.", 360, 470);
	}

}
