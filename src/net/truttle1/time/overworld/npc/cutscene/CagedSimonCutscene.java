package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.npc.NPC;

public class CagedSimonCutscene extends NPC{

	private int tx;
	private int ty;
	private BufferedImage[] cage;
	private BufferedImage[] ignacio;
	private int ignacioX;
	private boolean ignacioFlipped;
	private BufferedImage[] rage;
	private int rageX;
	private boolean rageFlipped;
	private boolean showCage = true;
	private int stage;
	private final int REMOVE_IGNACIO = 3;
	private int punches;
	public CagedSimonCutscene(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = BattleAnimation.simonIdleAnimation;
		ignacio = BattleAnimation.iggyIdle;
		rage = OverworldAnimation.rageIdle1;
		cage = OverworldAnimation.cage;
		this.id = ObjectId.NPC;
		if(Quest.quests[Quest.PYRUZ_S] <= 0)
		{
			stage = 0;
		}
		else
		{
			stage = -1;
		}
		tx = 0;
		ignacioX = this.x+200;
	}

	@Override
	public Rectangle topRectangle() {
		return null;
	}

	@Override
	public Rectangle leftRectangle() {
		return null;
	}

	@Override
	public Rectangle rightRectangle() {
		return null;
	}

	@Override
	public Rectangle bottomRectangle() {
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public void render(Graphics g) {

		
		animate(this.x,this.y,currentAnimation,0,g);
		if(showCage)
		{
			animate(this.x-50,this.y-50,cage,1,g);
		}
		if(stage<REMOVE_IGNACIO)
		{
			try
			{
				if(ignacioFlipped)
				{
					animateFlip(ignacioX,this.y-130,ignacio,2,g);
				}
				else
				{
					animateNoFlip(ignacioX,this.y-130,ignacio,2,g);
				}
			}
			catch(Exception e)
			{
				this.setFrame(2, 0);
				if(ignacioFlipped)
				{
					animateFlip(ignacioX,this.y-130,ignacio,2,g);
				}
				else
				{
					animateNoFlip(ignacioX,this.y-130,ignacio,2,g);
				}
			}
		}
		else if(stage < 8)
		{
			if(rageFlipped)
			{
				animateFlip(rageX,this.y-70,rage,2,g);
			}
			else
			{
				animateNoFlip(rageX,this.y-70,rage,2,g);
			}
		}
		if(stage == 0)
		{
			g.setFont(Global.winFont2);
			g.setColor(Color.gray.darker());
			g.drawString("Meanwhile...", tx+55, ty+69);
			g.setColor(Color.white);
			g.drawString("Meanwhile...", tx+50, ty+64);
		}
	}

	@Override
	public void tick()
	{
		ty = 900;
		if(stage<0)
		{
			om.objects.remove(this);
		}
		else
		{
			removePlayers();
			runCutscene();
			om.tx = tx;
			om.ty = ty;
		}
	}
	
	private void removePlayers()
	{
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == ObjectId.Player)
			{
				om.objects.remove(i);
			}
		}
	}

	private void runCutscene()
	{
		if(stage == 0)
		{
			tx += 4;
			if(Global.zPressed)
			{
				tx = this.x-500;
			}
			if(tx>=this.x-450)
			{
				stage++;
			}
		}
		if(stage == 1)
		{
			if(Global.talking == 0)
			{
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				ignacio = BattleAnimation.iggyTaunt;
				Global.disableMovement = true;
				Global.talking = 1;
				SpeechBubble.talk("BWAR HAR HAR HAR! I FINALLY GOT IT!! AND IT WAS ALL SO EASY!/It was just like taking candy from a big uncivilized baby!",Global.iggyFont);
			}
			if(Global.talking == 2)
			{
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				ignacio = BattleAnimation.iggyTaunt;
				Global.disableMovement = true;
				Global.talking = 3;
				SpeechBubble.talk("THE TIME ORB IS MINE!! My Boss's master plan is finally falling into place!/Without this Time Orb, that little squirt will never be able to travel to the future, and/stop us! BWARR HARR HARR HARR!!! Thanks for the little-squirt-stopping orb,/NEANDERTHAL LOSER!!! BWAAAARRR HARRR HARRRR!!!",Global.iggyFont);
			}
			if(Global.talking == 4)
			{
				stage++;
				ignacio = BattleAnimation.iggyIdle;
				Global.talking = 0;
			}
		}
		if(stage == 2)
		{
			ignacio = BattleAnimation.iggyWalk;
			ignacioFlipped = true;
			ignacioX += 6;
			if(ignacioX>=this.x+600)
			{
				stage++;
				rageX = this.x-600;
			}
		}
		if(stage == 3)
		{
			rage = OverworldAnimation.rageWalk;
			rageFlipped = true;
			rageX += 12;
			if(rageX>=this.x-300)
			{
				stage++;
			}
		}
		if(stage == 4)
		{
			AudioHandler.playMusic(AudioHandler.mountainLoop);
			if(Global.talking == 0)
			{
				
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageTalk1;
				this.flipped = true;
				Global.disableMovement = true;
				Global.talking = 1;
				SpeechBubble.talk("I'm Rage, I'm here to rescue you!.../Yes, I know my name makes me sound like a villain, but TRUST ME, I'm/not. Also, you're kinda necessary for the entire future of your species.../Let me break you outta there, I'll explain later!");
			}
			if(Global.talking == 2)
			{
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageIdle4;
				this.flipped = true;
				Global.disableMovement = true;
				this.setFrame(2, 0);
				Global.talking = 0;
				this.stage++;
			}
		}
		if(stage == 5)
		{
			rage = OverworldAnimation.ragePunch;
			if(getFrame(2)>=47)
			{
				if(punches == 0)
				{
					AudioHandler.playSound(AudioHandler.seHit);
					punches++;
				}
				else
				{
					AudioHandler.playSound(AudioHandler.seHit2);
					this.setFrame(2, 0);
					stage++;
				}
			}
			if(getFrame(2) == 31)
			{
				if(punches == 1)
				{
					showCage = false;
				}
			}
		}
		if(stage == 6)
		{
			if(Global.talking == 0)
			{
				
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageTalk;
				this.flipped = true;
				Global.disableMovement = true;
				Global.talking = 1;
				SpeechBubble.talk("Okay cavedude, let's roll! You're needed by a couple brave adventurers/that are trying to bring down Ignacio's tyrannical reign. They're/currently trapped in a cage that can only be broken with a club,/and you happen to own a club. That's why we're counting on you!");
			}
			if(Global.talking == 2)
			{
				
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageTalk;
				this.flipped = true;
				Global.disableMovement = true;
				Global.talking = 3;
				SpeechBubble.talk("Now I know that's alot to process, but just remember, we believe in you!/If you can't save those two, than nobody can...well, nobody that would/be willing to anyway... Also, remember, the prosperity of your entire/species is resting on your shoulders and yours alone...but no pressure!!");
			}
			if(Global.talking == 4)
			{
				
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageTalk;
				this.flipped = true;
				Global.disableMovement = true;
				Global.talking = 5;
				SpeechBubble.talk("I'm gonna be following you around for a bit, Cavedude. Now, I'm a/pacifist, so I won't be beating up anyone who attacks you, however I/will be healing you during battles you encounter. Also, if you see any/green or red dudes that kinda look like me, DO NOT WALK UP TO THEM!!");
			}
			if(Global.talking == 6)
			{
				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageTalk;
				this.flipped = true;
				Global.disableMovement = true;
				Global.talking = 7;
				SpeechBubble.yesNo("Now that's kinda alot to process, so do ya need me to go over/that whole spiel one more time?",Global.textFont);
			
			}
			if(Global.talking == 8)
			{
				if(SpeechBubble.selection == 0)
				{
					this.currentAnimation = BattleAnimation.simonIdleAnimation;
					rage = OverworldAnimation.rageTalk;
					this.flipped = true;
					Global.disableMovement = true;
					Global.talking = 1;
					SpeechBubble.talk("Here we go again! You're needed by a couple brave adventurers/that are trying to bring down Ignacio's tyrannical reign. They're/currently trapped in a cage that can only be broken with a club,/and you happen to own a club. That's why we're counting on you!");
				}
				else
				{
					this.currentAnimation = BattleAnimation.simonIdleAnimation;
					rage = OverworldAnimation.rageTalk;
					this.flipped = true;
					Global.disableMovement = true;
					Global.talking = 9;
					SpeechBubble.talk("Wow, Cavedude, you're quite confident! I like that! It's all you now, bud!");
				}
			}
			if(Global.talking == 10)
			{

				this.currentAnimation = BattleAnimation.simonIdleAnimation;
				rage = OverworldAnimation.rageIdle4;
				this.flipped = true;
				Global.disableMovement = true;
				this.setFrame(2, 0);
				Global.talking = 0;
				this.stage++;
			}
		}
		if(stage == 7)
		{
			rageX += 8;
			rage = OverworldAnimation.rageWalk;
			if(rageX >= this.x)
			{
				stage++;
			}
		}
		
		if(stage == 8)
		{
			Global.hasWilliam = false;
			Global.unlockedPartner[Global.SKRAPPS] = false;
			Global.currentPartner = Global.RAGE;
			Global.hasSimon = true;
			Global.unlockedPartner[Global.RAGE] = true;
			for(int i=0; i<Global.items.length;i++)
			{
				Quest.quests[i+9000] = Global.items[i];
				Quest.quests[i+9500] = Global.keyItems[i];
				Global.items[i] = 0;
				Global.keyItems[i] = 0;
			}
			Quest.quests[Quest.PYRUZ_S]++;
			om.objects.add(new SimonPlayer(window, this.x, this.y));
			Global.talking = 0;
			Global.disableMovement = false;
			Global.talkingTo = null;
			om.objects.remove(this);
			AudioHandler.playMusic(AudioHandler.evilShuffle);
		}
	}
}
