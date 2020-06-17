package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.FlairmerRed;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;

public class FlairmerROverworld extends NPC{

	int amount;
	boolean defeated;
	int defeatedTime;
	private long musicTime;
	boolean idle1 = false;
	boolean idle2 = false;
	public FlairmerROverworld(int x, int y, Game window, OverworldMode om, int amount) {
		super(window,x,y, om);
		this.window = window;
		for(int i=0;i<hPath.length;i+=25)
		{
			double r = Math.random();
			if(r<0.3)
			{
				hPath[i] = 8;
			}
			else if(r<0.6)
			{
				hPath[i] = -8;
			}
			else if(r<0.8)
			{
				hPath[i] = 0;
			}
			
		}
		this.id = ObjectId.FlairmerOverworld;
		this.amount = amount;
		this.currentAnimation = BattleAnimation.flareRedIdle;
	}

	@Override
	public void tick()
	{
		if(hPath[pathStage] == 0 && !idle1 && !idle2)
		{
			if(Math.random() >0.5)
			{
				idle2 = true;
				idle1 = false;
			}
			else
			{
				idle1 = true;
				idle2 = false;
			}
			hVelocity = 0;
		}
		else if(hPath[pathStage] != 0 && hPath[pathStage] != 99)
		{
			idle2 = false;
			idle1 = false;
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(Global.talking == 0 && !Fade.running && window.overworldMode.objects.get(i).id == ObjectId.Player)
			{
				if(!defeated && this.getBounds().intersects(window.overworldMode.objects.get(i).getBounds()))
				{
					if(amount == 1)
					{
						window.battleMode.addMonster(new FlairmerRed(window,720,270,window.battleMode));
					}
					if(amount == 2)
					{
						window.battleMode.addMonster(new FlairmerRed(window,550,270,window.battleMode));
						window.battleMode.addMonster(new FlairmerRed(window,720,270,window.battleMode));
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
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.flareRedDie, om);
			die.setRepeating(false);
			om.eyeCandy.add(die);
			om.objects.remove(this);
		}
		if(hVelocity == 0)
		{
			if(idle1)
			{
				currentAnimation = BattleAnimation.flareRedWait;
			}
			else if(idle2)
			{
				currentAnimation = BattleAnimation.flareRedIdle2;
			}
			else
			{
				currentAnimation = BattleAnimation.flareRedIdle;
			}
		}
		else
		{
			currentAnimation = BattleAnimation.flareRedWalk;
		}

		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		if(window.mode != ModeType.Overworld || (x>tx-300 && x<tx+1200 && y>ty-300 && y<ty+600))
		{
			NPCTick();
		}
	}

	private void NPCTick()
	{
		continuePath();
		if(hPath[pathStage] != 99 && hPath[pathStage] != 199)
		{
			hVelocity = hPath[pathStage];
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);

				if(g.getBounds().intersects(leftRectangle())) {
					if(hVelocity<0)
					{
						hVelocity = -hVelocity;
					}
				}

				if(g.getBounds().intersects(rightRectangle())) {
					if(hVelocity>0)
					{
						hVelocity = -hVelocity;
					}
				}
				if(g.getBounds().intersects(topRectangle())) {
					if(vVelocity<0)
					{
						vVelocity = 0;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-220;
				}
			}
		}
		x+=hVelocity;
		y+=vVelocity;
		vVelocity+=2;
	}
	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+180,100,50);
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
		return new Rectangle(x+38,y+100,120,185);
	}


	@Override
	public void render(Graphics g) {
		try
		{
			animate(x, y, currentAnimation, 0, g);
		}catch(Exception e) {
			this.setFrame(0, 0);
			animate(x, y, currentAnimation, 0, g);
			}
	}

}
