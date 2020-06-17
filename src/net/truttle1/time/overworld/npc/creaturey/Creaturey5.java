package net.truttle1.time.overworld.npc.creaturey;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.npc.NPC;

public class Creaturey5 extends NPC{

	private int stage;
	GameObject player;
	public Creaturey5(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		this.currentAnimation = OverworldAnimation.creatHappyIdle2;
		this.id = ObjectId.NPC;
	}
	@Override
	public Rectangle topRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle leftRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle rightRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle bottomRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void render(Graphics g) {
		this.animate(x, y, currentAnimation, 0, g);
		if(stage == 3)
		{
			this.animate(player.x+50, player.y-50,OverworldAnimation.yOrbItem, 1, g);
		}
		if(stage == 5)
		{
			this.animate(player.x+50, player.y-50,OverworldAnimation.bookItem, 2, g);
		}
		
	}
	public void tick()
	{
		if(player == null || !om.objects.contains(player))
		{
			player = getPlayer();
		}
		if(stage == 0 && player != null)
		{
			if(player.x >= this.x-120)
			{
				player.hVelocity = 0;
				System.out.println("Hai!");
				Global.disableMovement = true;
				if(player.vVelocity == 0)
				{
					stage++;
				}
			currentAnimation = OverworldAnimation.creatHappyIdle2;
			this.flipped = false;
			}
		}
		if(stage >= 1)
		{
			if(player instanceof SimonPlayer)
			{
				simonCutscene();
			}
			else
			{
				williamCutscene();
			}
		}
	}
	private void simonCutscene()
	{
		if(stage == 1)
		{
			if(Global.talking == 0)
			{
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.setAnimation(BattleAnimation.simonIdleAnimation);
				this.currentAnimation = OverworldAnimation.creatSadTalk2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Umm, this gift is for William...can I see him please?/You can change characters in the pause menu, which is opened/with [X].",Global.creatFont);
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				player.setAnimation(BattleAnimation.simonWalkAnimation);
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		if(stage == 2)
		{
			Global.talkingTo = this;
			Global.disableMovement = true;
			SimonPlayer s = (SimonPlayer) player;
			s.animationManualOverride = true;
			player.x -= 8;
			player.flipped = true;
			player.setAnimation(BattleAnimation.simonWalkAnimation);
			if(player.x<=this.x-300)
			{
				s.animationManualOverride = false;
				stage = 0;
				Global.talking = 0;
				Global.talkingTo = null;
				Global.disableMovement = false;
			}
		}
	}
	private void williamCutscene()
	{

		if(stage == 1)
		{
			if(Global.talking == 0)
			{
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdle2;
				this.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Hello again, William! You see this nifty little car thing here?/Well, my friend, this is a Time Machine! You simply insert a Time Orb,/and it allows you to warp between two different periods of time! I think/the yellow one goes to the Digital Age! Anyway, try putting it in!",Global.creatFont);
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				player.setAnimation(BattleAnimation.simonWalkAnimation);
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		if(stage == 2)
		{
			player.x+=4;
			player.currentAnimation = BattleAnimation.williamWalkAnimation;
			if(player.x >= this.x+100)
			{
				this.flipped = true;
			}
			if(player.x >= this.x+300)
			{
				stage++;
			}
		}
		if(stage == 3)
		{

			if(Global.talking == 0)
			{
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamItem;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("William put the Yellow Time Orb into his brand new Time Machine!");
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				player.currentAnimation = BattleAnimation.williamIdleAnimation;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		if(stage == 4)
		{
			player.flipped = false;

			if(Global.talking == 0)
			{
				Quest.quests[Quest.TIMEMACHINE]++;
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdleAnimation;
				this.currentAnimation = OverworldAnimation.creatHappyTalkStomp;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Also, William, I have a new Encyclopedia for you! This one contains/updated entries for the Digital Age! Don't worry though, I'll be seeing/you there! I'll just be slightly younger. Specifically, I'll be one year/younger.",Global.creatFont);
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				player.currentAnimation = BattleAnimation.williamIdleAnimation;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		if(stage == 5)
		{

			if(Global.talking == 0)
			{
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamItem;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("William obtained an UPDATED ENCYCLOPEDIA!");
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				player.currentAnimation = BattleAnimation.williamIdleAnimation;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		if(stage == 6)
		{
			player.flipped = false;

			if(Global.talking == 0)
			{
				Quest.quests[Quest.TIMEMACHINE]++;
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamTalkHappy;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Wow Creaturey, I can't wait to try this out!!! By the way, where did you/find this thing? Is it from the future? And how do I ride it??",Global.willFont);
			}
			if(Global.talking == 2)
			{
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdleHappy;
				this.currentAnimation = OverworldAnimation.creatSadTalk2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Oh, heh...Future you gave me this Time Machine and told me to pass it/down to past you, AKA current you! And to ride the Time Machine, you/press [C] while standing by it. Also, you're the only one who can use the/Time Machine, so be sure to switch the Overworld character to yourself!",Global.creatFont);
			}
			if(Global.talking == 4)
			{
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamTalkHappy;
				this.currentAnimation = OverworldAnimation.creatSadIdle2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Sweet! I'm gonna try it out right now! See you then!",Global.willFont);
			}
			if(Global.talking == 6)
			{
				player.flipped = false;
				this.setFrame(0, 0);
				om.ty = this.y-200;
				Global.talkingTo = this;
				player.currentAnimation = BattleAnimation.williamIdleHappy;
				this.currentAnimation = OverworldAnimation.creatHappyTalk2;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Ha, I've already seen you then! Come back to Lomo Village if you want/your HP and SP restored!",Global.creatFont);
			}
			if(Global.talking == 8)
			{
				Global.talking = 0;
				stage++;
				player.currentAnimation = BattleAnimation.williamIdleAnimation;
				this.currentAnimation = OverworldAnimation.creatHappyIdle2;
			}
		}
		if(stage == 7)
		{
			this.flipped = false;
			this.x -= 12;
			currentAnimation = OverworldAnimation.creatHappyWalk;
			if(this.x<1500)
			{
				om.objects.remove(this);
				Global.talking = 0;
				Global.disableMovement = false;
				Quest.quests[Quest.PYRUZ_S] = 10;
				Global.talkingTo = null;
			}
		}
	}

}
