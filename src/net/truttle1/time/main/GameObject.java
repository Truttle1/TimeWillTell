package net.truttle1.time.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.monsters.MonsterType;
import net.truttle1.time.effects.Store;

public abstract class GameObject {
	protected Game window;
	public int x;
	public int y;

	public int startX;
	public int startY;
	public boolean flipped = false;
	private double[] currentFrame = new double[9];
	public ObjectId id;
	public BufferedImage[] currentAnimation;
	public BufferedImage[] heldItem;
	public boolean canDodge = true;
	public boolean dodging = false;
	public boolean dead = false;
	public int vVelocity = 0;
	public int hVelocity = 0;
	public int timedHit = 0;
	public GameObject(Game window)
	{
		this.window = window;
	}

	private int topItem = 0;
	private int bottomItem = 0;
	
	protected void drawItemMenu(Color menuColor, Graphics g, int itemSelection)
	{
		int yOff = 50;
		g.setFont(Global.battleFont);
		g.setColor(menuColor);
		g.fillRect(x-16, y-(300-yOff), 250, 160);
		int itemsCounted = 0;
		g.setColor(menuColor.darker());
		g.drawString("Press [X] to go back",x-16,y-(155-yOff));
		for(int i=topItem; i<Global.items.length; i++)
		{
			if(Global.items[i]>0)
			{
				if(itemSelection == i)
				{
					g.setColor(Color.white);
					g.fillRect(x-4, y-(288-yOff), 225, 22);
				}
				g.setColor(menuColor.darker().darker());
				g.drawString(Store.itemNames[i], x-2, y-(272-yOff));
				g.drawString("x" + Integer.toString(Global.items[i]), x+180, y-(272-yOff));
				yOff += 22;
				itemsCounted++;
			}
			g.setFont(Global.battleFont);
			if(topItem > itemSelection)
			{
				topItem = itemSelection;
			}
			
			if(itemsCounted >= 5)
			{
				bottomItem = i;
				if(bottomItem < itemSelection)
				{
					topItem = findTopItem(bottomItem);
				}
				break;
			}
		}
	}

	private int findTopItem(int bottomItem)
	{
		int topItem = bottomItem;
		int itemsCounted = 0;
		while(itemsCounted < 3)
		{
			if(topItem <= 0)
			{
				return 0;
			}
			if(Global.items[topItem] > 0)
			{
				itemsCounted++;
			}
			topItem--;
		}
		return topItem;
	}
	protected GameObject getPlayer()
	{
		for(int i = 0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Player)
			{
				return window.overworldMode.objects.get(i);
			}
		}
		return null;
	}
	public void setAnimation(BufferedImage[] animation)
	{
		this.currentAnimation = animation;
		currentAnimation = animation;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public Rectangle getBounds()
	{
		return null;
	}
	protected void animate(int x, int y,BufferedImage[] animation, int cFrame, Graphics g)
	{
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		
		int txm = window.minigameMode1.tx;
		int tym = window.minigameMode1.ty;
		int roundedFrame = (int)(currentFrame[cFrame]/Global.framePerImg)*Global.framePerImg;
		if(loadFrame(animation,roundedFrame) == null)
		{
			roundedFrame = 0;
			currentFrame[cFrame] = 0;
		}
		if(roundedFrame < 0) 
		{
			roundedFrame = 0;
		}
		if((window.mode != ModeType.Overworld && window.mode != ModeType.Minigame1) || ( window.mode == ModeType.Overworld && x>tx-300 && x<tx+1200 && y>ty-300 && y<ty+600) || ( window.mode == ModeType.Minigame1 && x>txm-1200 && x<txm+2400 && y>tym-700 && y<tym+1200))
		{
			Graphics2D g2d = (Graphics2D) g;
			if(!flipped)
			{
				g2d.drawImage(loadFrame(animation,roundedFrame),x,y, null);
			}
			else
			{
				g2d.drawImage(loadFrame(animation,roundedFrame),x+loadFrame(animation,roundedFrame).getWidth(),y,-loadFrame(animation,roundedFrame).getWidth(),loadFrame(animation,roundedFrame).getHeight(), null);	
			}
		}

		currentFrame[cFrame]+=1;
		if(currentFrame[cFrame] > animation[2].getWidth())
		{
			currentFrame[cFrame] = 0;
		}
	}
	protected void animateBig(int x, int y,BufferedImage[] animation, int cFrame, Graphics g)
	{
		loadUnloadedGraphics(animation,cFrame);
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		int roundedFrame = (int)(currentFrame[cFrame]/Global.framePerImg)*Global.framePerImg;
		if(window.mode != ModeType.Overworld || (x>tx-400 && x<tx+1400 && y>ty-800 && y<ty+600))
		{
			Graphics2D g2d = (Graphics2D) g;
			if(!flipped)
			{
				g2d.drawImage(loadFrame(animation,roundedFrame),x,y, null);
			}
			else
			{
				g2d.drawImage(loadFrame(animation,roundedFrame),x+loadFrame(animation,roundedFrame).getWidth(),y,-loadFrame(animation,roundedFrame).getWidth(),loadFrame(animation,roundedFrame).getHeight(), null);
			}
		}

		currentFrame[cFrame]+=1;
		if(currentFrame[cFrame] > animation[2].getWidth())
		{
			currentFrame[cFrame] = 0;
		}
	}
	protected void animateNoFlip(int x, int y,BufferedImage[] animation, int cFrame, Graphics g)
	{
		loadUnloadedGraphics(animation,cFrame);
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		int roundedFrame = (int)(currentFrame[cFrame]/Global.framePerImg)*Global.framePerImg;
		if(window.mode != ModeType.Overworld || (x>tx-600 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			Graphics2D g2d = (Graphics2D) g;
	
			g2d.drawImage(loadFrame(animation,roundedFrame),x,y, null);
			currentFrame[cFrame]+=1;
			if(currentFrame[cFrame] > animation[2].getWidth())
			{
				currentFrame[cFrame] = 0;
			}
		}
	}
	protected void animateFlip(int x, int y,BufferedImage[] animation, int cFrame, Graphics g)
	{
		loadUnloadedGraphics(animation,cFrame);
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		int roundedFrame = (int)(currentFrame[cFrame]/Global.framePerImg)*Global.framePerImg;
		if(window.mode != ModeType.Overworld || (x>tx-600 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			Graphics2D g2d = (Graphics2D) g;
	
			g2d.drawImage(loadFrame(animation,roundedFrame),x+loadFrame(animation,roundedFrame).getWidth(),y,-loadFrame(animation,roundedFrame).getWidth(),loadFrame(animation,roundedFrame).getHeight(), null);
			currentFrame[cFrame]+=1;
			if(currentFrame[cFrame] > animation[2].getWidth())
			{
				currentFrame[cFrame] = 0;
			}
		}
	}
	protected int getMonsterDistance()
	{

		int dist = 0;
		if(window.battleMode.selectedMonster.type == MonsterType.Ursear)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.William1)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.William2)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Vult)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Flairmer1)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Flairmer2)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Rockstar)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Garbzop)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Baumber)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Trukofire)
		{
			dist = 140;
		}
		if(window.battleMode.selectedMonster.type == MonsterType.Ignacio)
		{
			dist = 80;
		}
		return dist;
	}
	protected void animateAtSpeed(int x, int y,BufferedImage[] animation, int cFrame, Graphics g, double d)
	{
		loadUnloadedGraphics(animation,cFrame);
		Graphics2D g2d = (Graphics2D) g;
		int roundedFrame = (int)(currentFrame[cFrame]/Global.framePerImg)*Global.framePerImg;
		if(!flipped)
		{
			g2d.drawImage(loadFrame(animation,roundedFrame),x,y, null);
		}
		else
		{
			g2d.drawImage(loadFrame(animation,roundedFrame),x+loadFrame(animation,roundedFrame).getWidth(),y,-loadFrame(animation,roundedFrame).getWidth(),loadFrame(animation,roundedFrame).getHeight(), null);
		}
		currentFrame[cFrame]+=d;
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
	public int getFrame(int cFrame)
	{
		return (int) currentFrame[cFrame];
	}
	public void setFrame(int cFrame, double newFrame)
	{
		currentFrame[cFrame] = newFrame;
	}
	public int getAnimationLength(BufferedImage[] animation)
	{
		return animation[2].getWidth();
	}
	private void loadUnloadedGraphics(BufferedImage[] animation, int cFrame)
	{
		
		window.graphicsLoader.run();
		String path = null;
		window.graphicsLoader.loadUnloadedGraphics(animation, cFrame, currentFrame, window);
		/*
		if(animation[(int) currentFrame[cFrame]] == null)
		{
			for(int i=0; i<BattleAnimation.currentKey; i++)
			{
				if(BattleAnimation.imageKey[i] != null && BattleAnimation.imageKey[i].equals(animation[0]))
				{
					path = BattleAnimation.imagePath[i];
					break;
				}
			}animation[(int) currentFrame[cFrame]] = BattleAnimation.loadFrame(path, (int)currentFrame[cFrame], window);
			
		}*/
		
	}

	protected BufferedImage replaceColors(Color start, Color end, BufferedImage input)
	{
		BufferedImage img = input;
		for(int x=0; x<input.getWidth();x++)
		{
			for(int y=0; y<input.getHeight();y++)
			{
				if(img.getRGB(x, y) == start.getRGB())
				{
					img.setRGB(x, y, end.getRGB());
				}
			}	
		}
		return img;
	}
	protected BufferedImage replaceTwoColors(Color start, Color end,Color start2, Color end2, BufferedImage input)
	{
		BufferedImage img = input;
		for(int x=0; x<input.getWidth();x++)
		{
			for(int y=0; y<input.getHeight();y++)
			{
				if(img.getRGB(x, y) == start.getRGB())
				{
					img.setRGB(x, y, end.getRGB());
				}
				if(img.getRGB(x, y) == start2.getRGB())
				{
					img.setRGB(x, y, end2.getRGB());
				}
			}	
		}
		return img;
	}
	
	public int getPartnerId()
	{
		return -1;
	}
}
