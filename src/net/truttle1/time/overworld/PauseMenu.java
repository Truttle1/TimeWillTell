package net.truttle1.time.overworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;

public class PauseMenu {

	OverworldMode om;
	GameObject player;
	private int layer = 0;
	private int selection[] = new int[99];
	private int selectionAmount[] = new int[99];
	private static final int MAIN = 0; 
	private static final int ITEMS = 10; 
	private static final int KEY_ITEMS = 20; 
	private static final int PARTY_MEMBERS = 30; 
	private int itemSelection;
	private int isDir;
	private int pmBoxOffset = 0;
	public boolean closed = false;
	private long musicTime;

	public boolean flipped = false;
	private double[] currentFrame = new double[9];
	public PauseMenu(OverworldMode om)
	{
		this.om = om;
		selection[0] = 0;
		selectionAmount[0] = 2;
	}
	public void tick()
	{
		//Move to constructor later
		selectionAmount[ITEMS+1] = 1;
		//System.out.println("LAYER :: " + layer);
		if(Global.xPressed)
		{
			if(Global.talking >= 1)
			{
				Global.talking = 0;
			}
			switch(layer)
			{
			case PARTY_MEMBERS + 1:
				layer--;
				AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
				break;
			case ITEMS:
				layer = 0;
				break;
			case KEY_ITEMS:
				layer = 0;
				break;
			case PARTY_MEMBERS:
				layer = 0;
				break;
			default:
				layer--;
				break;
			}
		}
		if(layer<0)
		{
			layer = 0;
			closed = true;
			Global.disableMovement = false;
		}
		if(player == null || !om.objects.contains(player))
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player)
				{
					player = om.objects.get(i);
				}
			}
		}
		om.stopPlayerMoving(player);
		if(Global.talking == 0)
		{
			if(layer == ITEMS)
			{
				if(Global.downPressed)
				{
					selection[ITEMS]++;
					isDir = 0;
				}
				if(Global.upPressed)
				{
					selection[ITEMS]--;
					isDir = 1;
				}
				if(selection[ITEMS]<0)
				{
					selection[ITEMS] = 98;
				}
				if(selection[ITEMS]>98)
				{
					selection[ITEMS] = 0;
				}
				while(selection[ITEMS] >= 0 && selection[ITEMS] <= 98 && Global.items[selection[ITEMS]] == 0)
				{
					if(isDir == 0)
					{
						selection[ITEMS]++;
					}
					else
					{
						selection[ITEMS]--;
					}
				}
			}
			else if(layer == ITEMS + 2 || layer == PARTY_MEMBERS)
			{
				if(selection[layer]<0)
				{
					selection[layer] = 99;
				}
				if(selection[layer] == 0 && !Global.hasSimon)
				{
					selection[layer]++;
				}
				if(selection[layer] == 1 && !Global.hasWilliam)
				{
					if(isDir == 0)
					{
						selection[layer] = 2;
					}
					else
					{
						selection[layer] = 0;
					}
				}

				if(selection[layer] == 2 && !Global.hasPartner)
				{
					selection[layer] = 0;
				}
				else if(selection[layer]>=2)
				{
					
					while(selection[layer] >= 2 && !Global.unlockedPartner[selection[layer]-2])
					{
						if(isDir == 0)
						{
							selection[layer]++;
						}
						else if(isDir == 1)
						{
							selection[layer]--;
						}
						if(selection[layer]>95)
						{
							selection[layer] = 0;
						}
						if(selection[layer]<0)
						{
							selection[layer] = 0;
						}
					}
				}
				if(Global.downPressed)
				{
					selection[layer]++;
					isDir = 0;
				}
				if(Global.upPressed)
				{
					selection[layer]--;
					isDir = 1;
				}
			}
			else
			{
				if(Global.downPressed)
				{
					if(selectionAmount[layer]<=selection[layer])
					{
						selection[layer] = 0;
					}
					else
					{
						selection[layer]++;
					}
				}
				if(Global.upPressed)
				{
					if(0>=selection[layer])
					{
						selection[layer] = selectionAmount[layer];
					}
					else
					{
						selection[layer]--;
					}
				}
			}
		}
		
		if(Global.talking >= 2)
		{
			Global.talking = 0;
		}
		PressZ:
		if(Global.zPressed && Global.talking == 0)
		{
			if(layer == 0)
			{
				mainSelection();
				Global.zPressed = false;
				break PressZ;
			}
			else if(layer == ITEMS+1)
			{
				if(selection[layer] == 2)
				{
					Global.items[selection[ITEMS]]--;
					Global.zPressed = false;
					//System.out.println("RIPAROONI :: " + selection[ITEMS]);
					if(Global.items[selection[ITEMS]]<=0)
					{
						layer--;
					}
					break PressZ;
				}
				if(selection[layer] == 1)
				{
					if(Global.talking == 0)
					{
						Global.talking = 1;
						Global.talkingTo = null;
						SpeechBubble.talk(Store.itemDescriptions[selection[ITEMS]]);
					}
					if(Global.talking == 2)
					{
						Global.talking = 0;
						Global.zPressed = false;
					}
					break PressZ;
				}
				else
				{
					incrementSelection();
					Global.zPressed = false;
					break PressZ;
				}
			}
			else if(layer == ITEMS+2)
			{
				useItem(selection[ITEMS],selection[ITEMS+2]);
				Global.items[selection[ITEMS]]--;
				layer = ITEMS;
				selection[ITEMS+2] = 0;
				Global.zPressed = false;
				break PressZ;
			}
			else if(layer == PARTY_MEMBERS+1)
			{
				int guy = selection[PARTY_MEMBERS];
				System.out.println(guy);
				if(guy == 0)
				{
					AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
					SimonPlayer newSimon = new SimonPlayer(om.window,player.x,player.y);
					om.objects.add(newSimon);
					om.objects.remove(player);
					om.objects.remove(player);
					player = newSimon;
					layer = 0;
				}
				if(guy == 1 && player instanceof SimonPlayer)
				{
					AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
					WilliamPlayer newwilliam = new WilliamPlayer(om.window,player.x+32,player.y,om);
					om.objects.add(newwilliam);
					om.objects.remove(player);
					om.objects.remove(player);
					player = newwilliam;
					layer = 0;
				}
				if(guy == 2)
				{
					layer = PARTY_MEMBERS+1;
				}
			}
			else if(layer != KEY_ITEMS)
			{
				incrementSelection();
				Global.zPressed = false;
				break PressZ;
			}
		}
		player.hVelocity = 0;
	}
	public void render(Graphics g)
	{
		if(Quest.quests[Quest.ESCAPED] == 1)
		{
			this.closed = true;
			Global.disableMovement = false;
		}
		g.setColor(Color.red);
		g.fillRect(64, 64, 256, 120);
		drawMenuText(g);
		g.setFont(Global.winFont2);
		g.setColor(Color.gray.darker());
		g.drawString("GAME PAUSED!", 368, 134);
		g.drawString("$" + Global.money, 804, 544);
		g.setColor(Color.white);
		g.drawString("GAME PAUSED!", 360, 128);
		g.drawString("$" + Global.money, 800, 540);
		g.setFont(Global.winFont1);
		if(layer == 0)
		{
			g.drawString("Press [X] to resume!", 360, 170);
		}
		else
		{
			g.drawString("Press [X] to go back!", 360, 170);
		}
		if(layer == PARTY_MEMBERS+1 && (selection[PARTY_MEMBERS] == 0))
		{
			g.drawString("Press [Z] to play as Simon!", 360, 220);
		}
		else if(layer == PARTY_MEMBERS+1 && (selection[PARTY_MEMBERS] == 1))
		{
			g.drawString("Press [Z] to play as William!", 360, 220);
		}
		else if(layer == PARTY_MEMBERS+1 && (selection[PARTY_MEMBERS] == 2))
		{
			g.drawString("Skrapps can not be played as...", 360, 220);
		}
		else
		{
			g.drawString("Press [Z] to select!", 360, 220);
		}
		
		
	}
	private void drawMenuText(Graphics g)
	{

		g.setFont(Global.battleFont);
		g.setColor(Color.white);
		g.fillRect(72, 74+(selection[MAIN]*20), 240, 20);
		g.setColor(Color.red.darker());
		g.drawString("Items",72,92);
		g.drawString("Key Items",72,112);
		g.drawString("Party Members",72,132);
		if(layer>=ITEMS && layer<KEY_ITEMS)
		{
			drawMenuItems(g);
			if(layer>=ITEMS+1)
			{
				drawMenuItemOptions(g);
			}
			if(layer>=ITEMS+2)
			{
				pmBoxOffset = 340;
				drawPartyList(g);
			}
		}
		if(layer>=PARTY_MEMBERS)
		{
			pmBoxOffset = 60;
			drawPartyList(g);
		}
		if(layer >= PARTY_MEMBERS+1)
		{
			drawCharacter(selection[PARTY_MEMBERS],g);
		}
		if(layer < PARTY_MEMBERS && layer >= KEY_ITEMS)
		{
			drawMenuKeyItems(g);
		}
		
	}

	private void drawMenuKeyItems(Graphics g)
	{
		int x = 82;
		int y = 512;
		g.setFont(Global.battleFont);
		g.setColor(Color.red);
		g.fillRect(x-16, y-300, 250, 160);
		int yOff = 0;
		for(int i=0; i<Global.keyItems.length; i++)
		{
			if(Global.keyItems[i]>0)
			{
				/*
				if(selection[KEY_ITEMS] == i)
				{
					g.setColor(Color.white);
					g.fillRect(x-4, y-(288-yOff), 225, 22);
				}*/
				g.setColor(Color.white);
				g.drawString(Store.keyItemNames[i], x-2, y-(272-yOff));
				g.drawString("x" + Integer.toString(Global.keyItems[i]), x+197, y-(272-yOff));
				yOff += 22;
			}
		}
	}
	private void drawMenuItems(Graphics g)
	{
		int x = 82;
		int y = 512;
		g.setFont(Global.battleFont);
		g.setColor(Color.red);
		g.fillRect(x-16, y-300, 250, 160);
		int yOff = 0;
		for(int i=0; i<Global.items.length; i++)
		{
			if(Global.items[i]>0)
			{
				if(selection[ITEMS] == i)
				{
					g.setColor(Color.white);
					g.fillRect(x-4, y-(288-yOff), 225, 22);
				}
				g.setColor(Color.red.darker());
				g.drawString(Store.itemNames[i], x-2, y-(272-yOff));
				g.drawString("x" + Integer.toString(Global.items[i]), x+180, y-(272-yOff));
				yOff += 22;
			}
		}
	}
	private void drawMenuItemOptions(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(64, 425, 256, 84);


		g.setFont(Global.battleFont);
		g.setColor(Color.white);
		g.fillRect(72, 434+(selection[ITEMS+1]*20), 240, 20);
		g.setColor(Color.red.darker());
		g.drawString("Use",72,452);
		g.drawString("About",72,472);
		g.drawString("Throw Away",72,492);
	}
	private void drawPartyList(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(pmBoxOffset+4, 345, 256, 200);


		g.setFont(Global.battleFont);
		g.setColor(Color.white);
		if(layer < KEY_ITEMS)
		{
			g.fillRect(pmBoxOffset+12, 354+(selection[ITEMS+2]*20), 240, 20);
		}
		else
		{
			g.fillRect(pmBoxOffset+12, 354+(selection[PARTY_MEMBERS]*20), 240, 20);
		}
		g.setColor(Color.red.darker());
		if(Global.hasSimon)
		{
			g.drawString("Simon",pmBoxOffset+12,372);
		}
		if(Global.hasWilliam)
		{
			g.drawString("William",pmBoxOffset+12,392);
		}
		if(Global.unlockedPartner[Global.SKRAPPS])
		{
			g.drawString("Skrapps",pmBoxOffset+12,412);
		}
		if(Global.unlockedPartner[Global.RAGE])
		{
			g.drawString("Rage",pmBoxOffset+12,432);
		}
	}
	private void mainSelection()
	{
		switch(selection[MAIN])
		{
		case 0:
			layer = ITEMS;
			break;
		case 1:
			layer = KEY_ITEMS;
			break;
		case 2:
			layer = PARTY_MEMBERS;
			break;
		default:
			layer = MAIN;
			break;
		}
	}
	private void incrementSelection()
	{
		layer++;
	}
	private void useItem(int item, int character)
	{
		if(character == 0)
		{
			System.out.println("abcdefg");
			Store.itemUse(null, item,false,"Simon");
		}
		if(character == 1)
		{
			Store.itemUse(null, item,false,"William");
		}
		if(character == 2)
		{
			Store.itemUse(null, item,false,"Skrapps");
		}
		
	}
	private void drawCharacter(int character, Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(340, 240, 640, 304);
		g.setColor(Color.white);
		g.fillRect(356, 256, 256, 256);
		if(character == 0)
		{
			musicTime = AudioHandler.music.getMicrosecondPosition();
			AudioHandler.playMusic(AudioHandler.levelUpSimon);
			if(!Global.hasSimon)
			{
				AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
				this.layer = PARTY_MEMBERS;
			}
			this.animate(375, 300, BattleAnimation.simonIdleAnimation, 0, g);
			g.setColor(Color.red.darker().darker());
			g.setFont(Global.winFont1);
			g.drawString("Simon", 640, 282);
			g.setFont(Global.battleFont);
			g.drawString("Level: " + Global.simonLevel,356,532);
			g.drawString("EXP: " + Global.simonXP + "/" + (int) ((Math.pow(2, Global.simonLevel)*25)),496,532);
			g.drawString("HP: " + Global.simonHP + "/" + Global.simonHPMax,630, 322);
			g.drawString("SP: " + Global.simonSP + "/" + Global.simonSPMax,630, 342);
			g.drawString("POWER: " + Global.simonPow,630, 362);
			//TODO REPLACE "0" WITH ACTUAL VARIABLE
			g.drawString("DEFENSE: " + 0,630, 382);
			g.drawString("FAVORITE FOOD: APPLES",630, 412);
			g.drawString("HOBBIES: NONE,",630, 442);
			g.drawString("CAVEMEN HAVE NO TIME",630, 462);
			g.drawString("FOR POINTLESS HOBBIES",630, 482);
			
		}
		else if(character == 1)
		{
			musicTime = AudioHandler.music.getMicrosecondPosition();
			AudioHandler.playMusic(AudioHandler.williamTheme);
			if(!Global.hasWilliam)
			{
				AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
				this.layer = PARTY_MEMBERS;
			}
			this.animate(400, 350, BattleAnimation.williamIdleAnimation, 0, g);
			g.setColor(Color.red.darker().darker());
			g.setFont(Global.winFont1);
			g.drawString("William", 640, 282);
			g.setFont(Global.battleFont);
			g.drawString("Level: " + Global.williamLevel,356,532);
			g.drawString("EXP: " + Global.williamXP + "/" + (int) ((Math.pow(2, Global.williamLevel)*25)),496,532);
			g.drawString("HP: " + Global.williamHP + "/" + Global.williamHPMax,630, 322);
			g.drawString("SP: " + Global.williamSP + "/" + Global.williamSPMax,630, 342);
			g.drawString("POWER: " + Global.williamPow,630, 362);
			g.drawString("DEFENSE: " + Global.williamDefense,630, 382);
			g.drawString("FAVORITE FOOD: COOKIES",630, 412);
			g.drawString("HOBBIES: BOARD GAMES,",630, 442);
			g.drawString("JOGGING, INVENTING",630, 462);
			
		}
		else if(character == 2)
		{
			musicTime = AudioHandler.music.getMicrosecondPosition();
			AudioHandler.playMusic(AudioHandler.skrapps);
			if(!Global.unlockedPartner[Global.SKRAPPS])
			{
				AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
				this.layer = PARTY_MEMBERS;
			}
			this.animate(400, 350, BattleAnimation.skrappsIdleBattle, 0, g);
			g.setColor(Color.red.darker().darker());
			g.setFont(Global.winFont1);
			g.drawString("Skrapps", 640, 282);
			g.setFont(Global.battleFont);
			g.drawString("Level: " + Global.partnerLevel[Global.SKRAPPS],356,532);
			g.drawString("EXP: " + Global.partnerXP[Global.SKRAPPS] + "/" + (int) ((Math.pow(2, Global.partnerLevel[Global.SKRAPPS])*25)),496,532);
			g.drawString("HP: " + Global.partnerHP[Global.SKRAPPS] + "/" + Global.partnerHPMax[Global.SKRAPPS],630, 322);
			g.drawString("SP: " + Global.partnerSP[Global.SKRAPPS] + "/" + Global.partnerSPMax[Global.SKRAPPS],630, 342);
			g.drawString("POWER: " + Global.partnerPow[Global.SKRAPPS],630, 362);
			//g.drawString("DEFENSE: " + Global.partnerDefense,630, 382);
			g.drawString("FAVORITE FOOD: ANYTHING",630, 412);
			g.drawString("THAT DOESN'T EAT HIM FIRST,",630, 432);
			g.drawString("PREFERRABLY MEAT.",630, 452);
			g.drawString("HOBBIES: STICKBALL,",630, 482);
			g.drawString("MAGIC",630, 502);
			
		}

		else if(character == 3)
		{
			if(!Global.unlockedPartner[Global.RAGE])
			{
				this.layer = PARTY_MEMBERS;
			}
			this.animate(320, 240, OverworldAnimation.rageIdle2, 0, g);
			g.setColor(Color.red.darker().darker());
			g.setFont(Global.winFont1);
			g.drawString("Rage", 640, 282);
			g.setFont(Global.battleFont);
			g.drawString("Level: " + Global.partnerLevel[Global.RAGE],356,532);
			g.drawString("EXP: " + Global.partnerXP[Global.RAGE] + "/" + (int) ((Math.pow(2, Global.partnerLevel[Global.RAGE])*25)),496,532);
			g.drawString("HP: " + Global.partnerHP[Global.RAGE] + "/" + Global.partnerHPMax[Global.RAGE],630, 322);
			g.drawString("SP: " + Global.partnerSP[Global.RAGE] + "/" + Global.partnerSPMax[Global.RAGE],630, 342);
			g.drawString("POWER: " + Global.partnerPow[Global.RAGE],630, 362);
			//g.drawString("DEFENSE: " + Global.partnerDefense,630, 382);
			g.drawString("FAVORITE FOOD: TENDERLOMO",630, 412);
			g.drawString("HOBBIES: LIFTING WEIGHTS,",630, 482);
			g.drawString("MAGIC",630, 502);
			
		}
	}

	private void animate(int x, int y,BufferedImage[] animation, int cFrame, Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		if(!flipped)
		{
			g2d.drawImage(loadFrame(animation,(int)currentFrame[cFrame]),x,y, null);
		}
		else
		{
			g2d.drawImage(loadFrame(animation,(int)currentFrame[cFrame]),x+loadFrame(animation,(int)currentFrame[cFrame]).getWidth(),y,-loadFrame(animation,(int)currentFrame[cFrame]).getWidth(),loadFrame(animation,(int)currentFrame[cFrame]).getHeight(), null);
		}
		currentFrame[cFrame]++;
		if(currentFrame[cFrame] > animation[2].getWidth())
		{
			currentFrame[cFrame] = 0;
		}
	}
	private BufferedImage loadFrame(BufferedImage[] animation, int frame)
	{
		int height = animation[1].getHeight();
		int width = animation[1].getWidth();
		int offset = (width * frame);
		try
		{
			 return animation[0].getSubimage(offset, 0, width, height);
		}
		catch(Exception e)
		{
			 return animation[0].getSubimage(0, 0, width, height);
		}
	}
}
