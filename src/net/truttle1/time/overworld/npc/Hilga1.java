package net.truttle1.time.overworld.npc;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;

public class Hilga1 extends NPC{

	private int inputDelay = -1;
	public GameObject simon;
	public Hilga1(Game window, int x, int y, OverworldMode om) {
		super(window,x,y, om);
		currentAnimation = OverworldAnimation.hilgaIdle;
		this.id = ObjectId.NPC;
		if(Quest.quests[Quest.TUTORIAL]>=10 && (Quest.quests[Quest.LOMO]<6 || Quest.quests[Quest.LOMO]>=Global.LOMOCONSTANT)) {
			om.objects.add(new Suzy2(window, x-500, y, om));
		}
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
		if(distanceTo(ObjectId.Player)<150 && Global.talking == 0 && !Global.disableMovement)
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
		if(getFrame(0)>getAnimationLength(currentAnimation))
		{
			setFrame(0,0);
		}
		try
		{
			this.animate(x, y, currentAnimation, 0, g);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			setFrame(0,0);
		}
	}

	@Override
	public void tick()
	{
		if(Quest.quests[Quest.LOMO]>=6 && Quest.quests[Quest.LOMO]<Global.LOMOCONSTANT)
		{
			om.objects.remove(this);
		}
		if(simon == null)
		{

			for(int i=0; i<om.objects.size();i++)
			{
				if(om.objects.get(i) instanceof SimonPlayer || om.objects.get(i).id == ObjectId.Player)
				{
					simon = (GameObject) om.objects.get(i);
				}
			}
		}
		if(inputDelay>-1)
		{
			inputDelay++;
			if(inputDelay>2)
			{
				inputDelay = -1;
				Global.talking = 0;
				Global.disableMovement = false;
				if(Quest.quests[Quest.TUTORIAL] == 1)
				{
					Quest.quests[Quest.TUTORIAL] = 2;
				}
			}
		}
		super.tick();
		if(Quest.quests[Quest.TUTORIAL]>=9 && Quest.quests[Quest.LOMOBANK]<=0)
		{
			if(simon.x>this.x-150 && Global.talking == 0)
			{
				Global.disableMovement = true;
				Global.talkingTo = this;
				Global.talking = 1;
				currentAnimation = OverworldAnimation.hilgaTalk;
				SpeechBubble.talk("Go deposit that Yellow Orb in the Lomo Bank./The bank is in Lomo Village, which above the place where the apple tree/is. That Yellow Orb looks like it could make us serious money later./Oh, and take your club with you to protect yourself.");
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talking = 3;
				currentAnimation = OverworldAnimation.hilgaIdle;
				SpeechBubble.talk("Simon obtained a WOODEN CLUB!");
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.talkingTo = null;
				Global.talking = 0;
				Global.disableMovement = false;
				Quest.quests[Quest.LOMOBANK] = 1;
			}
			
		}
		if(Global.cPressed && Global.talking == 0 && distanceTo(ObjectId.Player)<150)
		{
			om.stopPlayerMoving(getPlayer());
			Global.talkingTo = this;
			Global.talking = 1;
			if(Quest.quests[Quest.TUTORIAL]>=2 && Quest.quests[Quest.TUTORIAL]<=9)
			{
				SpeechBubble.talk("What are you waiting for? Those apples aren't gonna go/pick themselves! No apples, no salad!");
			}
			if(Quest.quests[Quest.LOMOBANK]>=1)
			{
				if(Global.hasSimon && Global.playingAs == 0 && Quest.quests[Quest.PYRUZ_S]<=1)
				{
					SpeechBubble.talk("Go deposit that Yellow Orb in the Lomo Bank./The bank is in Lomo Village, which is just above the apple tree./That Yellow Orb looks like it could make us serious money later./Also, talk to Suzy if you want your HP restored!");
				}
				else if(!Global.hasSimon && Global.playingAs == 1)
				{
					SpeechBubble.talk("Eww, go away you street urchin! You have twelve seconds to get/out of my house before I send a stone tablet to/the Lomo Village Pest Extermination Company!");
				}
				else if(Global.hasSimon && Global.playingAs == 1)
				{
					SpeechBubble.talk("Hey, Slimeball, can I talk to Simon? I mean, he's on this dangerous/journey, and I just want to know that he's okay!/Remember, if you get my Simon killed, the blood is on YOUR HANDS,/STREET URCHIN!");
				}
				else if(Global.hasSimon && Global.playingAs == 0 && Quest.quests[Quest.PYRUZ_S]>1)
				{
					SpeechBubble.talk("Hello Honey! Do you really need to travel with a slimy little green urchin/like that guy? Why can't you travel with handsome hunks like yourself?/I know that the reptile's probably got a tiny shred of good in him, but/COME ON! Anyway, I love you Simon! Be sure not to die!");
				}
			}
		}
		if(Global.talkingTo == this && Global.talking == 2 && inputDelay==-1)
		{
			inputDelay = 0;
		}
		if(Global.talkingTo == this && Global.talking>0)
		{
			currentAnimation = OverworldAnimation.hilgaTalk;
			if(getFrame(0)>getAnimationLength(currentAnimation))
			{
				setFrame(0,0);
			}
		}
		else
		{
			if(Quest.quests[Quest.TUTORIAL] == 0)
			{
				hVelocity = -8;
				currentAnimation = OverworldAnimation.hilgaWalk;
				Global.disableMovement = true;
				if(x == 388)
				{
					Quest.quests[Quest.TUTORIAL] = 1;
				}
			}
			else if(Quest.quests[Quest.TUTORIAL] == 1)
			{
				hVelocity = 0;
				Global.talkingTo = this;
				Global.talking = 1;
				SpeechBubble.talk("Simon honey, I need you to go out and get me some purple apples./I'm going to make some Purple Apple salad for dinner tonight./Oh, and take Suzy with you. She should be playing outside.");
			}
			else
			{
				currentAnimation = OverworldAnimation.hilgaIdle;
			}
		}
	}

}
