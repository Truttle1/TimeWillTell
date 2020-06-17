package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public class RageCarneCutscene extends NPC{

	private BufferedImage[] carne;
	private int stage;
	private int carneX;
	private boolean carneFlipped;
	private GameObject player;
	public RageCarneCutscene(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = OverworldAnimation.rageIdle;
		carne = OverworldAnimation.carneIdle;
		carneFlipped = false;
		carneX = this.x+280;
		this.id = ObjectId.NPC;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(Graphics g) 
	{
		if(carneFlipped)
		{
			this.animateFlip(carneX,y+40, carne, 1, g);
		}
		else
		{
			this.animateNoFlip(carneX,y+40, carne, 1, g);
		}
		this.animate(x,y,currentAnimation,0,g);
	}
	
	@Override
	public void tick()
	{
		player = getPlayer();
		if(Quest.quests[Quest.RAGECUTSCENE]>=1)
		{
			om.objects.remove(this);
		}

		else if(stage == 0)
		{
			if(player.x >= this.x - 100)
			{
				om.stopPlayerMoving(player);
				player.hVelocity = 0;
				Global.disableMovement = true;
				if(player.vVelocity == 0)
				{
					stage++;
				}
			}
			this.flipped = false;
		}
		else if(stage == 1)
		{
			if(Global.talking == 0)
			{
				this.currentAnimation = OverworldAnimation.rageTalk;
				carne = OverworldAnimation.carneIdle;
				Global.talkingTo = this;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Hey there! We've got some pretty big news for you! First of all,/now that Ignacio is no longer the Flairmer king, a new one needed/to be found, and, well, it turns out that I am the closest relative to the/old king! Also, it turns out that Ignacio isn't even a Flairmer!");
			}
			else if(Global.talking == 2 && Global.talkingTo == this)
			{
				this.currentAnimation = OverworldAnimation.rageIdle;
				carne = OverworldAnimation.carneTalk;
				Global.talkingTo = this;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("We're also coming up with this new way to choose the leader./Me and Rage decided that being born into ruling is unfair for everyone/involved, so we're coming up with a system where all Flairmers/choose their leader and how the Kingdom works! We call it \"Democracy!\"");
			}
			else if(Global.talking == 4 && Global.talkingTo == this)
			{
				this.currentAnimation = OverworldAnimation.rageTalk1;
				carne = OverworldAnimation.carneIdle;
				Global.talkingTo = this;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Now, even though we're doing all these reforms, some of the/Flairmers in Mt. Pyruz might still attack you out of habit. Hopefully,/in the coming years, we can make the Flairmers a much more peaceful/group. For right now, enter Mt. Pyruz at your own risk.");
			}
			else if(Global.talking == 6 && Global.talkingTo == this)
			{
				this.currentAnimation = OverworldAnimation.rageIdle;
				carne = OverworldAnimation.carneTalk;
				Global.talkingTo = this;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Also, I'm going to still be selling stuff, and Rage will still/be selling spells. You'll be finding me outside of Mt. Pyruz like/I was earlier, and Rage will be in the old throne room./Anyway, we should be going now, we've got lots of work to do!");
			}
			else if(Global.talking == 8 && Global.talkingTo == this)
			{
				this.currentAnimation = OverworldAnimation.rageTalk;
				carne = OverworldAnimation.carneIdle;
				Global.talkingTo = this;
				Global.talking++;
				Global.disableMovement = true;
				SpeechBubble.talk("Yeah dudes, take care!");
			}
			if(Global.talking == 10 && Global.talkingTo == this)
			{
				Global.talking = 0;
				stage++;
			}
		}
		else if(stage == 2)
		{
			this.flipped = true;
			carneFlipped = true;
			this.currentAnimation = OverworldAnimation.rageWalk;
			carne = OverworldAnimation.carneWalk;
			this.x += 4;
			carneX += 4;
			if(this.x > 3100)
			{
				stage++;
			}
		}
		else if(stage == 3)
		{
			Global.talkingTo = null;
			Global.disableMovement = false;
			om.objects.remove(this);
			Quest.quests[Quest.RAGECUTSCENE] = 1;
		}
	}

}
