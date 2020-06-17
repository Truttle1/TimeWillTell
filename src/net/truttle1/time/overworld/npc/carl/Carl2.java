package net.truttle1.time.overworld.npc.carl;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.WilliamBattler;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.cutscene.IggyCutscene;
import net.truttle1.time.overworld.npc.cutscene.IggyOverworld1;

public class Carl2 extends Carl{

	public boolean caged = false;
	private BufferedImage[] cage;
	public IggyCutscene iggy;
	int cageTalk;
	public Carl2(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.carlIdle;
		cage = OverworldAnimation.cage;
		this.id = ObjectId.NPC;
		this.flipped = true;
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x+16,y+16,164,144);
	}

	@Override
	public void tick()
	{

		if(Quest.quests[Quest.LOMO] >= 4)
		{
			om.objects.remove(this);
		}
		if(Quest.quests[Quest.LOMO] == 1)
		{
			if(Global.talking == 2)
			{
				currentAnimation = OverworldAnimation.carlIdle;
			}
			if(Global.talking == 4)
			{
				Global.talking = 5;
				currentAnimation = OverworldAnimation.carlTalk;
				SpeechBubble.talk("Well, my name's Carl...but shouldn't you already know that!?/I mean, I've seen YOU many times in the future...err...that came/out wrong, I've seen you many times in MY past...so, why are we/just meeting now?",Global.carlFont);
				
			}

			if(Global.talking == 6 || Global.talking == 7)
			{
				currentAnimation = OverworldAnimation.carlIdle;
			}
			if(Global.talking == 10 || Global.talking == 11)
			{
				currentAnimation = OverworldAnimation.carlIdle;
			}

			if(Global.talking == 8)
			{
				Global.talking = 9;
				currentAnimation = OverworldAnimation.carlTalk;
				SpeechBubble.talk("Umm, this town WAS upbeat and full of people YESTERDAY.../but guess what, IT'S NOT YESTERDAY ANYMORE!!!..../but besides that, the town was obviously deserted/...strange...I wonder why...",Global.carlFont);
				
			}
			if(Global.talking == 14)
			{
				Global.talking = 15;
				currentAnimation = OverworldAnimation.carlTalk;
				SpeechBubble.talk("Personally, I think they would've headed leftward. The enterance/to Mt. Pyruz is to the right, and due to the bully inhabiting/the mountain, I think they'd want to get as far away as possible./After all, what other reason would they have to leave?",Global.carlFont);
				
			}
			if(Global.talking == 16)
			{
				Global.talking = 17;
				currentAnimation = OverworldAnimation.carlTalk;
				SpeechBubble.talk("I'm going to go look around right now. You two should just stay/here and...I dunno, look for clues, I guess. I don't want you/two to get lost, so unless something unlucky happens, STAY HERE!",Global.carlFont);
				
			}
			if(Global.talking >= 18)
			{
				if(x<800)
				{
					
					if(!caged)
					{
						for(int i=0; i<om.objects.size();i++)
						{
							GameObject tempObject = om.objects.get(i);
						}
						AudioHandler.stopMusic();
						SpeechBubble.talk("AEEEEEEEEEEEE!!!! HEEEELP!!!",Global.carlFont);
						cageTalk = Global.talking;
						caged = true;
					}
					else if(caged && Global.talking > cageTalk)
					{
						Quest.quests[Quest.LOMO] = 2;
						Global.talking = 0;

						AudioHandler.playMusic(AudioHandler.timeForTrouble);
						iggy = new IggyCutscene(this.window,this.x-320,this.y-100,om,this);
						om.objects.add(iggy);
					}
				}
				else
				{
					this.flipped = false;
					this.hVelocity = -8;
					currentAnimation = OverworldAnimation.carlWalk;
				}
			}

		}
		if(Quest.quests[Quest.LOMO] == 2)
		{
			caged = true;
			if(x<1200 && Global.talking != 8 && Global.talking != 7)
			{
				Global.talking = 0;
				currentAnimation = OverworldAnimation.carlIdle;
				hVelocity = 6;
			}
			else if(iggy.currentAnimation == BattleAnimation.iggyWalk && iggy.x >= this.x-150&& Global.talking != 8 && Global.talking != 7)
			{
				hVelocity = 0;
				Global.talking = 0;
				iggy.currentAnimation = BattleAnimation.iggyIdle;
			}
			else if(iggy.currentAnimation == BattleAnimation.iggyPush && iggy.x < this.x-150)
			{
				hVelocity = 0;
				iggy.currentAnimation = BattleAnimation.iggyWalk;
			}
			if(Global.talking == 2)
			{
				Global.talking = 3;
				currentAnimation = OverworldAnimation.carlTalk;
				SpeechBubble.talk("I'M A CAIMAN!!! IDIOT!!!!! NOW UNHAND ME THIS INSTANT!!!",Global.carlFont);
			}
			if(Global.talking == 4)
			{
				currentAnimation = OverworldAnimation.carlIdle;
			}
		}
		continuePath();
		if(hPath[pathStage] != 99)
		{
			hVelocity = hPath[pathStage];
		}
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);
				if(g.getBounds().intersects(topRectangle())) {
					if(vVelocity<0)
					{
						vVelocity = 0;
					}
				}
				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-180;
				}
			}
		}
		x+=hVelocity;
		y+=vVelocity;
		vVelocity+=2;
	}

	@Override
	public void render(Graphics g) {
		if(distanceTo(ObjectId.Player)<165 && Global.talking == 0 && !Global.disableMovement)
		{
			if(flipped)
			{
				this.animateNoFlip(x+50, y-100, BattleAnimation.cIcon, 1, g);
			}
			else
			{
				this.animate(x+50, y-100, BattleAnimation.cIcon, 1, g);
			}
		}
		if(currentAnimation[2].getWidth()<this.getFrame(0))
		{
			setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		if(caged)
		{
			this.animate(x-20, y-50, cage, 1, g);
		}
	}

}
