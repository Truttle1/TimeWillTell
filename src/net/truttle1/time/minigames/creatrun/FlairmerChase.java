package net.truttle1.time.minigames.creatrun;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;

public class FlairmerChase extends GameObject{

	MinigameMode1 mm;
	CreatureyPlayer1 creaturey;
	int fixedY;
	boolean dead = false;
	boolean trulyDead = false;
	public FlairmerChase(Game window, int x, int y, MinigameMode1 mm) {
		super(window);
		currentAnimation = BattleAnimation.flareFly;
		this.x = x;
		this.y = y;
		this.mm = mm;
		id = ObjectId.FlareMini1;
		this.flipped = true;
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+16,y+64,256,128);
				
	}
	@Override
	public void tick() {

		if(dead)
		{
			currentAnimation = BattleAnimation.flareRedDie;
			if(getFrame(0)>=17)
			{
				trulyDead = true;
			}
		}
		for(int i=0; i<window.minigameMode1.objects.size();i++)
		{
			if(!dead && window.minigameMode1.objects.get(i).id == ObjectId.Spike2 && window.minigameMode1.objects.get(i).getBounds().intersects(this.getBounds()))
			{
				dead = true;
				setFrame(0,0);
				this.setFrame(0, 0);
			}
		}
		if(creaturey == null)
		{
			for(int i=0; i<mm.objects.size(); i++)
			{
				if(mm.objects.get(i) instanceof CreatureyPlayer1)
				{
					creaturey = (CreatureyPlayer1)mm.objects.get(i);
				}
			}
		}
		if(this.x<150 && !mm.flairmerTalked)
		{
			this.x += 10;
		}
		if(this.x > mm.creatX + 50 && mm.creatX<48700 && creaturey.y < 1200)
		{
			currentAnimation = BattleAnimation.flareRedTalk3;
			this.y = mm.creatY+50;
			this.x = mm.creatX+51;
		}
		else if(!dead && mm.flairmerTalked && mm.gameStarted && mm.creatX>1000 && mm.scale <= 0.5)
		{
			if(mm.creatX-this.x > 1200)
			{
				this.x += 40;
			}
			else
			{
				this.x += 23;
			}
			if(mm.creatX<48700)
			{
				this.y = mm.creatY+25;
			}
			currentAnimation = BattleAnimation.flareFly;
		}
		else if(this.x>=150 && !mm.flairmerTalked)
		{
			if(Global.talking == 0)
			{
				this.currentAnimation = BattleAnimation.flareFlyTalk;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("BWAR HAR HAR HARRR!!! King Ignacio send Flairmer to prevent anyone/from finding out secret plans to rule over human race/by abducting all human. Now Flairmer KO ugly lizard monster!/BWARR HARR HARR HARRR!!!");
			}
			if(Global.talking == 2)
			{
				this.currentAnimation = BattleAnimation.flareFlyTalk;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				SpeechBubble.talk("Wait second...Flairmer think King Ignacio tell/Flairmer to keep plan secret...specifically, secret human-capturing-/and-species-controlling plan...ehh, Flairmer no care. Flairmer just/wanna destroy stupid no-good monster!");
			}
			if(Global.talking == 4)
			{
				this.currentAnimation = BattleAnimation.flareFlyTalk;
				Global.talkingTo = this;
				Global.talking = 5;
				Global.disableMovement = true;
				SpeechBubble.talk("TIME FOR FLAIRMER TO HAVE FUN!!! BETTER RUN LAME-O LIZARD!!! BWARR/HARR HARR HARR!!! BWAAAAAAAAAAAR HARRR HARRRRRRR!!!!");
			}
			if(Global.talking >= 6)
			{
				mm.gameStarted = true;
				AudioHandler.playMusic(AudioHandler.run);
				if(mm.scale>0.5)
				{
					mm.scale -= 0.01;
				}
				else
				{
					mm.scale = 0.5;
					Global.talking = 0;
					mm.flairmerTalked = true;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(!trulyDead)
		{
			this.animate(x, y, currentAnimation, 0, g);
		}
	}

}
