package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.Rage1;

public class WilliamSkrappsCutscene extends NPC{

	private BufferedImage[] skrapps;
	private int skrappsX;
	private boolean skrappsFlipped;
	
	private BufferedImage[] rage;
	private int rageX;
	private boolean rageFlipped;
	
	private int stage;
	
	private SimonPlayer simon;
	
	public WilliamSkrappsCutscene(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = BattleAnimation.williamIdle3;
		skrapps = BattleAnimation.skrappsIdle2;
		skrappsX = this.x+130;
		skrappsFlipped = true;
		this.flipped = true;
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
		return null;
	}

	@Override
	public void render(Graphics g) {
		if(getFrame(1)>=getAnimationLength(skrapps))
		{
			setFrame(1,0);
		}
		if(skrappsFlipped)
		{
			this.animateFlip(skrappsX, y-10, skrapps, 1, g);
		}
		else
		{
			this.animateNoFlip(skrappsX, y-10, skrapps, 1, g);
		}
		if(stage>=7)
		{
			if(rageFlipped)
			{
				this.animateFlip(rageX, y-130, rage, 2, g);
			}
			else
			{
				this.animateNoFlip(rageX, y-130, rage, 2, g);
			}
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	@Override
	public void tick()
	{
		if(simon == null)
		{
			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer)
				{
					simon = (SimonPlayer)om.objects.get(i);
				}
			}
		}
		if(simon != null && simon.x<=this.x+300)
		{
			runCutscene();
		}
	}

	private void runCutscene()
	{
		if(stage == 0)
		{
			if(Global.talkingTo == null && Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				currentAnimation = BattleAnimation.williamTalk3;
				SpeechBubble.talk("What the??? CAVEMAN!?! You're coming to rescue us!?!? Dude, I'm...surprised, really.../Hey, do you think maybe we could...start over...and pretend the whole /\"me spontaneously attacking you\" thing never even happened???",Global.willFont);
			}
			if(Global.talking == 2)
			{
				currentAnimation = BattleAnimation.williamIdle4;
				Global.talking = 0;
				stage++;
				simon.setFrame(0, 0);
			}
		}
		if(stage == 1)
		{
			//This will be a nod soon
			simon.currentAnimation = BattleAnimation.simonNod;
			if(simon.getFrame(0)>=getAnimationLength(BattleAnimation.simonNod)-1)
			{
				stage++;
				simon.setFrame(0, 0);
			}
		}

		if(stage == 2)
		{
			if(Global.talking == 0)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				currentAnimation = BattleAnimation.williamTalk2;
				SpeechBubble.talk("Good to know we're on good terms now! I never hated you, Mr. Caveman./Now, if you would be so kind as to break the wall behind me,/that would be just wonderful...because I'd kinda want to leave this stupid place/at some point...",Global.willFont);
			}
			if(Global.talking == 2)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				skrappsFlipped = false;
				currentAnimation = BattleAnimation.williamIdle2;
				skrapps = BattleAnimation.skrappsTalk;
				SpeechBubble.talk("Wait a second: Do you think the caveman would want to come help us on our quest? I mean,/we're kinda trying to save all of his friends after all, and I'm sure he'd wanna help with that!",Global.skrappsFont);
			}
			if(Global.talking == 4)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 5;
				Global.disableMovement = true;
				currentAnimation = BattleAnimation.williamTalk2;
				skrapps = BattleAnimation.skrappsIdle;
				skrappsFlipped = true;
				SpeechBubble.talk("Heh, that's funny Skrapps. So, Mr. Caveman, do you want to come along with two kids/who aren't even the same species as you on a dangerous journey through a/volcano filled with monsters?",Global.willFont);
			}
			if(Global.talking == 6)
			{
				currentAnimation = BattleAnimation.williamIdle2;
				Global.talking = 0;
				stage++;
				simon.setFrame(0, 0);
			}
		}
		if(stage == 3)
		{
			//This will be a nod soon
			simon.currentAnimation = BattleAnimation.simonNod;
			if(simon.getFrame(0)>=getAnimationLength(BattleAnimation.simonNod)-1)
			{
				stage++;
				simon.setFrame(0, 0);
			}
		}
		if(stage == 4)
		{
			if(Global.talking == 0)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				currentAnimation = BattleAnimation.williamTalk2;
				SpeechBubble.talk("OH!!!!! Well, then, I'm pleasently surprised again! I mean, if I was a caveman, I/wouldn't want to go through with this...",Global.willFont);
			}
			if(Global.talking == 2)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 3;
				Global.disableMovement = true;
				skrappsFlipped = true;
				currentAnimation = BattleAnimation.williamIdle2;
				skrapps = BattleAnimation.skrappsTalk;
				SpeechBubble.talk("By the way, Mr. Caveman, what's your ACTUAL name? I want to call you something other than/\"Mr. Caveman.\" By the way, I'm Skrapps, and my friend is William!",Global.skrappsFont);
			}
			if(Global.talking == 4)
			{
				currentAnimation = BattleAnimation.williamIdle2;
				skrapps = BattleAnimation.skrappsIdle2;
				Global.talking = 0;
				stage++;
				simon.setFrame(0, 0);
			}
		}
		if(stage == 5)
		{
			//This will be a nod soon
			simon.currentAnimation = BattleAnimation.simonNod;
			if(simon.getFrame(0)>=getAnimationLength(BattleAnimation.simonNod)-1)
			{
				stage++;
				simon.setFrame(0, 0);
			}
		}

		if(stage == 6)
		{
			if(Global.talking == 0)
			{
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				currentAnimation = BattleAnimation.williamTalk2;
				SpeechBubble.talk("Simon, huh? I like that name! Sounds cool!",Global.willFont);
			}
			if(Global.talking == 2)
			{
				rageX = simon.x;
				Global.talking = 0;
				currentAnimation = BattleAnimation.williamIdleAnimation;
				stage++;
			}
		}
		if(stage == 7)
		{
			rage = OverworldAnimation.rageWalk;
			rageFlipped = true;
			rageX += 4;
			if(rageX>=simon.x+200)
			{
				rageFlipped = false;
				stage++;
			}
		}
		if(stage == 8)
		{
			if(Global.talking == 0)
			{
				rageFlipped = false;
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				simon.flipped = false;
				currentAnimation = BattleAnimation.williamIdleAnimation;
				rage = OverworldAnimation.rageTalk;
				SpeechBubble.talk("By the way, Cavedude, there's one more thing I'd like to/give you. It's a special type of spell for club users!");
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				currentAnimation = BattleAnimation.williamIdleAnimation;
				setFrame(2,0);
				stage++;
			}
		}
		if(stage == 9)
		{
			rage = OverworldAnimation.rageGive;
			if(getFrame(2)>=47)
			{
				setFrame(2,0);
				stage++;
				rage = OverworldAnimation.rageIdle;
			}
		}
		if(stage == 10)
		{
			if(Global.talking == 0)
			{
				rageFlipped = false;
				simon.currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.talkingTo = this;
				Global.talking = 1;
				Global.disableMovement = true;
				simon.flipped = false;
				currentAnimation = BattleAnimation.williamIdleAnimation;
				rage = OverworldAnimation.rageTalk;
				SpeechBubble.talk("This spell allows you to charge your club for 2 SP! Also, if you want to/change who you are controlling on the overworld, you can select your/player in the Pause Menu! Each overworld dude has different abilities!/Anyway dudes, I'm afraid I must leave you, good luck!");
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				currentAnimation = BattleAnimation.williamIdleAnimation;
				rage = OverworldAnimation.rageIdle4;
				setFrame(2,0);
				setFrame(1,0);
				setFrame(0,0);
				stage++;
			}
		}
		if(stage == 11)
		{
			skrapps = BattleAnimation.skrappsRun;
			currentAnimation = BattleAnimation.williamWalkAnimation;
			this.x += 6;
			skrappsX += 6;
			if(skrappsX >= simon.x)
			{
				skrappsX = 1403598;
			}
			if(this.x >= simon.x)
			{
				stage++;
			}
		}
		if(stage == 12)
		{
			Quest.quests[Quest.PYRUZ_S]++;
			Global.hasWilliam = true;
			Global.unlockedPartner[Global.RAGE] = false;
			Global.unlockedPartner[Global.SKRAPPS] = true;
			Global.currentPartner = 0;
			for(int i=0; i<Global.items.length;i++)
			{
				Global.items[i] = Quest.quests[i+9000];
				Global.keyItems[i] = Quest.quests[i+9500];
				Quest.quests[i+9000] = 0;
				Quest.quests[i+9500] = 0;
			}
			Global.disableMovement = false;
			om.objects.add(new Rage1(window, rageX, y-130, om));
			om.objects.remove(this);
		}
	}
}
