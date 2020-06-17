package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.battle.monsters.Vult;
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

public class VultOverworld extends NPC{

	int amount;
	boolean defeated;
	int defeatedTime;
	public VultOverworld(int x, int y, Game window, OverworldMode om, int amount) {
		super(window,x,y, om);
		this.window = window;
		hPath[10] = 8;
		hPath[75] = 0;
		hPath[150] = -8;
		hPath[200] = 8;
		hPath[250] = 0;
		this.id = ObjectId.VultOverworld;
		this.amount = amount;
	}

	@Override
	public void tick()
	{continuePath();
	if(hPath[pathStage] != 99)
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
				this.y = g.y-200;
			}
		}
	}
	x+=hVelocity;
	y+=vVelocity;
	vVelocity+=2;

		if(Quest.quests[Quest.LOMOBANK]<1)
		{
			om.objects.remove(this);
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(Global.talking == 0 && !Fade.running && window.overworldMode.objects.get(i).id == ObjectId.Player)
			{
				if(!defeated && this.getBounds().intersects(window.overworldMode.objects.get(i).getBounds()))
				{
					window.battleMode.addMonster(new Vult(window,580,320,window.battleMode));
					if(amount>=2)
					{
						window.battleMode.addMonster(new Vult(window,720,320,window.battleMode));
					}
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
			AudioHandler.playMusic(Global.currentRoom.music);
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.vultDie, om);
			die.setRepeating(false);
			om.eyeCandy.add(die);
			om.objects.remove(this);
		}
		currentAnimation = BattleAnimation.vultIdle;
	}

	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+250,100,50);
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
		return new Rectangle(x+48,y+10,75,160);
	}


	@Override
	public void render(Graphics g) {
		try
		{
			animate(x, y, currentAnimation, 0, g);
		}catch(Exception e) {e.printStackTrace();}
	}

}
