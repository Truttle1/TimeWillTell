package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public class UrsearOverworld extends NPC{

	int amount;
	boolean defeated;
	int defeatedTime;
	private long musicTime;
	public UrsearOverworld(int x, int y, Game window, OverworldMode om, int amount) {
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
				hPath[i] = 8;
			}
			else if(r<0.2)
			{
				hPath[i] = -8;
			}
			else if(r<0.3)
			{
				hPath[i] = 0;
			}
			
		}
		this.id = ObjectId.UrsearOverworld;
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
						window.battleMode.addMonster(new Ursear(window,580,320,window.battleMode));
					}
					if(amount == 2)
					{
						window.battleMode.addMonster(new Ursear(window,580,320,window.battleMode));
						window.battleMode.addMonster(new Ursear(window,720,320,window.battleMode));
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
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.ursearDieAnimation, om);
			die.setRepeating(false);
			om.eyeCandy.add(die);
			om.objects.remove(this);
		}
		if(hVelocity == 0)
		{
			currentAnimation = BattleAnimation.ursearIdleAnimation;
		}
		else
		{
			currentAnimation = BattleAnimation.ursearWalkAnimation;
		}
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
		return new Rectangle(x+120,y+4,50,120);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+24,50,120);
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x+58,y,80,145);
	}


	@Override
	public void render(Graphics g) {
		try
		{
			animate(x, y, currentAnimation, 0, g);
		}catch(Exception e) {e.printStackTrace();}
	}

}
