package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.monsters.WilliamEnemy1;
import net.truttle1.time.battle.monsters.WilliamEnemy2;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.npc.NPC;

public class WilliamE2 extends NPC{

	SimonPlayer simon;
	int timer;
	public WilliamE2(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = BattleAnimation.williamSpikeWalk;
		this.id = ObjectId.NPC;
	}


	public Rectangle bottomRectangle()
	{
		return new Rectangle(x+60,y+150,100,50);
	}
	public Rectangle topRectangle()
	{
		return new Rectangle(x+60,y+4,60,50);
	}
	public Rectangle rightRectangle()
	{
		return new Rectangle(x+120,y+4,50,120);
	}
	public Rectangle leftRectangle()
	{
		return new Rectangle(x+45,y+4,50,120);
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+55,y+4,99,195);
	}
	@Override
	public void render(Graphics g) {
		if(getFrame(0)>=getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		
	}
	@Override
	public void tick()
	{
		timer++;
		if(simon == null)
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer || om.objects.get(i).id == ObjectId.Player)
				{
					simon = (SimonPlayer) om.objects.get(i);
				}
			}
		}
		if(Quest.quests[Quest.LOMOBANK] == 1 && Global.talking == 0 && timer>30)
		{
			Global.talking = 1;
			Global.talkingTo = this;
			Global.disableMovement = true;
			AudioHandler.stopMusic();
			SpeechBubble.talk("HEY! YOU JERK!!!");
			this.x = simon.x+800;
		}
		if(Quest.quests[Quest.LOMOBANK] == 1 && Global.talking == 2)
		{
			Global.talking = 0;
			Quest.quests[Quest.LOMOBANK] = 2;
			AudioHandler.playMusic(AudioHandler.williamTheme);
		}
		if(Quest.quests[Quest.LOMOBANK] == 2)
		{
			if(x>simon.x+200)
			{
				currentAnimation = BattleAnimation.williamSpikeWalk;
				x-=10;
			}
			else
			{
				Quest.quests[Quest.LOMOBANK] = 3;
			}
		}
		if(Quest.quests[Quest.LOMOBANK] == 3)
		{

			if(Global.talking == 0)
			{
				currentAnimation = BattleAnimation.williamSpikeTalk;
				Global.talking = 1;
				Global.talkingTo = this;
				Global.disableMovement = true;
				SpeechBubble.talk("First, you walk on up to that tree that YOU WERE SPECIFICALLY/TOLD NOT TO GO TO, then you BEAT ME UP, and then you/STEAL MY PROPERTY!?!? What kind of MONSTER are you!?!",Global.willFont);
			}
			if(Global.talking == 2)
			{
				currentAnimation = BattleAnimation.williamSpikeTalk;
				Global.talking = 3;
				Global.talkingTo = this;
				Global.disableMovement = true;
				SpeechBubble.talk("But, I'm a farily forgiving dinosaur...so just give me that/Strange Yellow Orb, and I, William, will never/bother you again...deal?",Global.willFont);
			}
			if(Global.talking == 4)
			{
				currentAnimation = BattleAnimation.williamSpikeTalk;
				Global.talking = 5;
				Global.talkingTo = this;
				Global.disableMovement = true;
				SpeechBubble.talk("What? No!?!? LISTEN HERE BUB, I don't know what I'm doing here,/or even where I am entirely, BUT I KNOW THAT/STRANGE YELLOW ORB HAS SOMETHING TO DO WITH IT!!! NOW GIVE IT!!!/DON'T MAKE ME USE MY NEW SPIKE!!!",Global.willFont);
			}
			if(Global.talking == 6)
			{
				currentAnimation = BattleAnimation.williamSpikeTalk;
				Global.talking = 7;
				Global.talkingTo = this;
				Global.disableMovement = true;
				SpeechBubble.talk("Fine then...I GUESS I WILL USE MY NEW SPIKE!!!",Global.willFont);
			}
			if(Global.talking == 8)
			{
				currentAnimation = BattleAnimation.williamSpikeWalk;
				setFrame(0,0);
				window.battleMode.addMonster(new WilliamEnemy2(window,720,380,window.battleMode));
				AudioHandler.playMusic(AudioHandler.williamBattleMusic);
				window.battleMode.startBattle();
				Fade.run(window, ModeType.Battle,false);
				Global.talking = 0;
				Global.disableMovement = true;
				Quest.quests[Quest.LOMOBANK] = 4;
			}
		}
		if(Quest.quests[Quest.LOMOBANK] == 4)
		{
			this.flipped = false;
			this.x -= 8;
		}
		if(Quest.quests[Quest.LOMOBANK] == 5)
		{
			if(Global.talking == 0)
			{
				this.x += 64;
				AudioHandler.playMusic(AudioHandler.williamTheme);
				currentAnimation = BattleAnimation.williamSpikeTalk;
				Global.talking = 1;
				Global.talkingTo = this;
				Global.disableMovement = true;
				SpeechBubble.talk("Ha ha, NOT FUNNY! You don't seem to realize what you're doing.../I'm leaving for now, but I WILL NOT STOP UNTIL THAT ORB IS/SAFELY IN MY HANDS!!!...but for right now, see ya later!",Global.willFont);
			}
			if(Global.talking == 2)
			{
				currentAnimation = BattleAnimation.williamSpikeTalk;
				Quest.quests[Quest.LOMOBANK] = 6;
				Global.talking = 0;
			}
		}
		if(Quest.quests[Quest.LOMOBANK] == 6)
		{
			this.currentAnimation = BattleAnimation.williamSpikeWalk;
			this.flipped = true;
			x += 8;
			if(this.x>simon.x+800)
			{
				AudioHandler.playMusic(AudioHandler.prehistoricMusic);
				Global.disableMovement = false;
				Quest.quests[Quest.LOMOBANK] = 7;
				om.objects.remove(this);
			}
		}
	}

}
