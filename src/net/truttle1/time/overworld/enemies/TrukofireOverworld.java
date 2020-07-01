package net.truttle1.time.overworld.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.Garbzop;
import net.truttle1.time.battle.monsters.Trukofire;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public class TrukofireOverworld extends NPC{

	int amount;
	boolean defeated;
	int defeatedTime;
	private long musicTime;
	public TrukofireOverworld(int x, int y, Game window, OverworldMode om, int amount) {
		super(window,x,y, om);
		this.window = window;
		hPath[10] = 8;
		hPath[100] = 0;
		hPath[200] = -8;
		hPath[290] = 0;
		for(int i=0;i<hPath.length;i+=10)
		{
			double r = Math.random();
			if(r<0.10)
			{
				hPath[i] = 12;
			}
			else if(r<0.2)
			{
				hPath[i] = -12;
			}
			else if(r<0.3)
			{
				hPath[i] = 0;
			}
			
		}
		this.id = ObjectId.TrukofireOverworld;
		this.amount = amount;
	}

	@Override
	public void tick()
	{
		super.tick();
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(Global.talking == 0 && !Fade.running && window.overworldMode.objects.get(i).id == ObjectId.Player)
			{
				if(!defeated && this.getBounds().intersects(window.overworldMode.objects.get(i).getBounds()))
				{
					if(amount == 1)
					{
						window.battleMode.addMonster(new Trukofire(window,680,230,window.battleMode));
					}
					if(amount == 2)
					{
						window.battleMode.addMonster(new Garbzop(window,580,320,window.battleMode));
						window.battleMode.addMonster(new Garbzop(window,720,320,window.battleMode));
					}
					musicTime = AudioHandler.music.getMicrosecondPosition();
					AudioHandler.playMusic(AudioHandler.battleMusic);
					window.battleMode.startBattle();
					Fade.run(window, ModeType.Battle,true);
					defeated = true;
				}
			}
		}
		if(hVelocity<0)
		{
			this.flipped = false;
		}
		else if(hVelocity>0)
		{
			this.flipped = true;
		} 
		if(defeated)
		{
			hVelocity = 0;
			defeatedTime++;
		}
		if(defeatedTime>1)
		{
			AudioHandler.playMusicAtPosition(Global.currentRoom.music,musicTime);
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.trukofireDie, om);
			die.setRepeating(false);
			om.eyeCandy.add(die);
			om.objects.remove(this);
		}
		
		currentAnimation = BattleAnimation.trukofireIdle;
	}

	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+150,100,50);
	}
	@Override
	public Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+4,60,50);
	}
	@Override
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+220,y+4,50,120);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+25,y+24,50,120);
	}
	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle(x+40,y+14,214,180);
	}


	@Override
	public void render(Graphics g) {
		try
		{
			Graphics2D g2d = (Graphics2D)(g);
			g2d.setColor(Color.GREEN);
			//g2d.draw(getBounds());
			g2d.setColor(Color.CYAN);
			//g2d.draw(leftRectangle());
			//g2d.draw(rightRectangle());
			animate(x, y-75, currentAnimation, 0, g);
		}catch(Exception e) {e.printStackTrace();}
	}

}
