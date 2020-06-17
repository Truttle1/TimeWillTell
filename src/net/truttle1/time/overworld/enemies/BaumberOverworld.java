package net.truttle1.time.overworld.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.Baumber;
import net.truttle1.time.battle.monsters.FlairmerRed;
import net.truttle1.time.battle.monsters.Garbzop;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;

public class BaumberOverworld extends NPC{

	private int amount;
	private boolean defeated;
	private int defeatedTime;
	private long musicTime;
	private boolean idle1 = false;
	private boolean idle2 = false;
	private GameObject p;
	public BaumberOverworld(int x, int y, Game window, OverworldMode om, int amount) {
		super(window,x,y, om);
		this.window = window;
		this.id = ObjectId.BaumberOverworld;
		this.amount = amount;
		this.currentAnimation = BattleAnimation.baumberIncog;
	}

	@Override
	public void tick()
	{
		if(p == null || !om.objects.contains(p))
		{
			p = getPlayer();
		}
		if(p != null && distanceTo(ObjectId.Player)>300)
		{
			setFrame(0,0);
			hVelocity = 0;
			this.currentAnimation = BattleAnimation.baumberIncog;
		}
		else if(p != null)
		{
			this.currentAnimation = BattleAnimation.baumberIdle;
			if(p.x>this.x)
			{
				hVelocity = 5;
			}
			else
			{
				hVelocity = -5;
			}
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(Global.talking == 0 && !Fade.running && window.overworldMode.objects.get(i).id == ObjectId.Player)
			{
				if(!defeated && this.getBounds().intersects(window.overworldMode.objects.get(i).getBounds()))
				{
					if(amount == 1)
					{
						window.battleMode.addMonster(new Baumber(window,720,270,window.battleMode));
					}
					if(amount == 2)
					{
						window.battleMode.addMonster(new Baumber(window,550,270,window.battleMode));
						window.battleMode.addMonster(new Baumber(window,720,270,window.battleMode));
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
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.baumberDie, om);
			die.setRepeating(false);
			om.eyeCandy.add(die);
			om.objects.remove(this);
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
		return new Rectangle(x+47,y+172,160,67);
	}
	@Override
	public Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+4,160,50);
	}
	@Override
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+180,y+4,50,120);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+24,50,120);
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x+38,y+31,177,198);
	}


	@Override
	public void render(Graphics g) {
		try
		{
			Graphics2D g2d = (Graphics2D)(g);
			g2d.setColor(Color.RED);
			//g2d.draw(getBounds());
			animate(x, y, currentAnimation, 0, g);
		}catch(Exception e) {
			this.setFrame(0, 0);
			animate(x, y, currentAnimation, 0, g);
			}
	}

}
