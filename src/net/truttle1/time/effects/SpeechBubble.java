package net.truttle1.time.effects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.Sign;

public class SpeechBubble {

	public static String text;
	public static String currentText;
	public static int amount;
	public static Font font;
	public static boolean yes = false;
	public static boolean yesNoBox = false;
	public static int selection = 0;
	public static int selectionMax = 1;
	public static void talk(String str)
	{
		text = str;
		amount = 0;
		Global.disableMovement = true;
		font = Global.textFont;
		yesNoBox = false;
	}
	public static void talk(String str, Font f)
	{
		text = str;
		amount = 0;
		Global.disableMovement = true;
		font = f;
		yesNoBox = false;
	}
	public static void yesNo(String str, Font f)
	{
		text = str;
		amount = 0;
		Global.disableMovement = true;
		font = f;
		yesNoBox = true;
	}
	public static void tick()
	{
		if(text != null)
		{
			if(amount < text.length())
			{
				amount++;
				if(Global.talkSound == 0)
				{
					AudioHandler.playSoundSpeech(amount);
				}
				else if(Global.talkSound == 1)
				{
					AudioHandler.playSoundOld(AudioHandler.talk1);
				}
			}
			currentText = text.substring(0,amount);
		}
		if(Global.zPressed && Global.talking != 0 && !Store.running)
		{
			if(amount<text.length())
			{
				amount = text.length();
			}
			else
			{
				Global.zPressed = false;
				Global.talking++;

			}
		}
		if(Global.downPressed)
		{
			if(selection<=0)
			{
				selection = selectionMax;
			}
			else
			{
				selection--;
			}
		}
		if(Global.upPressed)
		{
			if(selection>=selectionMax)
			{
				selection = 0;
			}
			else
			{
				selection++;
			}
		}
		if(Global.talking == 0)
		{
			amount = 0;
			currentText = "";
			text = "";
		}
	}
	public static void render(Graphics g)
	{
		
		if(text.equals(""))
		{
			return;
		}
		if(Global.talking != 0 && !Store.running)
		{
			if(Global.talkingTo instanceof Sign && Game.currentWorld == WorldId.Digital)
			{
				g.setColor(Color.green.darker().darker());
			}
			else
			{
				g.setColor(Color.white);
			}
			g.setFont(font);
			g.fillRect(8,8,1008,128);
			String[] dialog = new String[6];
			if(currentText != null)
			{
				dialog = currentText.split("/");
			}
			if(Global.talkingTo instanceof Sign && Game.currentWorld == WorldId.Digital)
			{
				g.setColor(Color.white);
			}
			else
			{
				g.setColor(Color.black);
			}
			for(int i=0; i<dialog.length; i++)
			{
				if(dialog[i] != null)
				{
					g.drawString(dialog[i], 16, 32+(i*30));
				}
			}
			if(amount >= text.length() && yesNoBox)
			{
				g.setColor(Color.blue);
				g.fillRect(8, 150, 128, 64);
				g.setFont(Global.battleFont);
				g.setColor(Color.white);
				g.fillRect(16, 158+(26*selection), 112, 22);
				g.setColor(Color.blue.darker().darker());
				g.drawString("Yes!", 20, 176);
				g.drawString("No!", 20, 202);
				
			}
			else
			{
				selection = 0;
			}
				
		}
	}
}
