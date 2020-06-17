package net.truttle1.time.overworld.npc.skrapps;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;

public class SkrappsNPC1 extends NPC{

	private GameObject player;
	public SkrappsNPC1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = BattleAnimation.skrappsIdle;
		this.id = ObjectId.NPC;
	}

	@Override
	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+40,y+110,60,30);
	}
	@Override
	public Rectangle topRectangle()
	{
		return new Rectangle(x+40,y,60,20);
	}
	@Override
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+90,y,30,80);
	}
	@Override
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+15,y,30,80);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+15,y,135,140);
	}

	@Override
	public void render(Graphics g) {
		try
		{
			this.animate(x, y, currentAnimation, 0, g);
		}catch(ArrayIndexOutOfBoundsException e)
		{
			this.setFrame(0, 0);
			this.animate(x, y, currentAnimation, 0, g);
		}
		
	}
	@Override
	public void tick()
	{
		y+=vVelocity;
		vVelocity+=4;
		this.id = ObjectId.NPC;
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);
	
				if(g.getBounds().intersects(leftRectangle())) {
					if(hVelocity<0)
					{
						hVelocity = 0;
					}
				}
	
				if(g.getBounds().intersects(rightRectangle())) {
					if(hVelocity>0)
					{
						hVelocity = 0;
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
					this.y = g.y-120;
				}
			}
		}

		if(player == null)
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i).id == ObjectId.Player)
				{
					player = om.objects.get(i);
				}
			}
		}
		if(Quest.quests[Quest.PYRUZ_W]>=6)
		{
			om.objects.remove(this);
		}
		if(player.x >= 500 && Quest.quests[Quest.PYRUZ_W] == 4)
		{
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 1;
				AudioHandler.stopMusic();
				player.currentAnimation = BattleAnimation.williamIdle2;
				SpeechBubble.talk("Hey! William! Remember me!?", Global.skrappsFont);
			}
			if(Global.talking >= 2)
			{
				AudioHandler.playMusic(AudioHandler.skrapps);
				this.x -= 8;
				this.flipped = false;
				currentAnimation = BattleAnimation.skrappsRun;
				if(this.x < player.x+200)
				{
					Global.talking = 0;
					Quest.quests[Quest.PYRUZ_W] = 5;
				}
			}
		}

		if(Quest.quests[Quest.PYRUZ_W] == 5)
		{
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 1;
				currentAnimation = BattleAnimation.skrappsTalk;
				player.flipped = true;
				SpeechBubble.talk("It's me, your buddy Skrapps! How've you been doin' recently?", Global.skrappsFont);
			}
			if(Global.talking == 2)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 3;
				currentAnimation = BattleAnimation.skrappsIdle2;
				player.currentAnimation = BattleAnimation.williamTalk;
				player.flipped = true;
				SpeechBubble.talk("Oh, I've been...fine...I don't remember having a buddy named Skrapps though.../Say, do you happen to have access to time travel by any chance.../I know that's a weird question, but there were these other/two guys that knew future me, but not current me...", Global.willFont);
			}
			if(Global.talking == 4)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 5;
				currentAnimation = BattleAnimation.skrappsTalk;
				player.currentAnimation = BattleAnimation.williamIdle2;
				player.flipped = true;
				SpeechBubble.talk("Yeah, I do have access to time travel! You even built the time machine for me! You were also/on some sort of grand adventure, and I wanted to come with you, and you said that I/could help you \"when I was older.\" Well, now I AM older and I was wondering...can I come/along with you on your adventure? PLEEEEEEEEEEEEEEEEASE???", Global.skrappsFont);
			}
			if(Global.talking == 6)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 7;
				currentAnimation = BattleAnimation.skrappsIdle2;
				player.currentAnimation = BattleAnimation.williamTalk2;
				player.flipped = true;
				SpeechBubble.talk("Uhh, sure...I guess...you seem pretty nice...let me just consult my little/book first...", Global.willFont);
			}
			if(Global.talking == 8)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 9;
				currentAnimation = BattleAnimation.skrappsIdle2;
				player.currentAnimation = BattleAnimation.williamRead;
				player.flipped = true;
				SpeechBubble.talk("\"Chapter 46: Skrapps - This young dino is...a good person...and he is also...kind and just/...if he asks to go on your travels, it is recommended that you...take him along.\"/...That's strange, why is there a puke stain on this page? But anyways,/this book seems to paint you as trustworthy, so I guess you can come along...", Global.willFont);
			}
			if(Global.talking == 10)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 11;
				currentAnimation = BattleAnimation.skrappsTalkStick;
				player.currentAnimation = BattleAnimation.williamIdle2;
				player.flipped = true;
				SpeechBubble.talk("YYIPEEEE!!! You won't regret this, buddy!", Global.skrappsFont);
			}
			if(Global.talking == 12)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 13;
				currentAnimation = BattleAnimation.skrappsIdleBattle;
				player.currentAnimation = BattleAnimation.williamIdle2;
				player.flipped = true;
				Global.currentPartner = Global.SKRAPPS;
				Global.hasPartner = true;
				SpeechBubble.talk("Skrapps has joined your party!/Skrapps can attack enemies using a branch that he carries around./He is also a strict carnivore. Plant products have lowered effects on him,/but meat products have hightened effects!");
			}
			if(Global.talking>13)
			{
				this.x -= 8;
				currentAnimation = BattleAnimation.skrappsRun;
				if(this.x <= player.x)
				{
					Global.hasPartner = true;
					Global.talking = 0;
					Global.talkingTo = null;
					Global.disableMovement = false;
					Global.unlockedPartner[Global.SKRAPPS] = true;
					om.objects.remove(this);
					Quest.quests[Quest.PYRUZ_W] = 6;
					AudioHandler.playMusic(AudioHandler.mountainLoop);
				}
			}
		}
	}
}
