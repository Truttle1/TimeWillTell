package net.truttle1.time.overworld.blocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class BoulderThin extends Grass{

	BufferedImage[] currentAnimation;
	OverworldMode om;
	int unmodifiedX;
	int unmodifiedY;
	private BufferedImage[] rage;
	private boolean showingRage;
	private int rageX;
	private int stage;
	private boolean rageFlipped;
	boolean makesDirt = true;
	boolean broken = false;
	private GameObject player;
	private SimonPlayer simon;
	public BoulderThin(int x, int y,Game window,OverworldMode om) {
		super(x,y,window,om);
		unmodifiedX = x;
		unmodifiedY = y;
		this.x = x*100;
		this.y = (y*100)-24;
		this.id = ObjectId.Grass;
		this.om = om;
		rage = OverworldAnimation.rageIdle;
		showingRage = false;
		currentAnimation = OverworldAnimation.boulderThin;
	}

	@Override
	public void tick() {
		for(int i=0; i<om.objects.size();i++)
		{
			if(om.objects.get(i).id == ObjectId.Player)
			{
				player = om.objects.get(i);
			}
			if(om.objects.get(i) instanceof SimonPlayer)
			{
				simon = (SimonPlayer) om.objects.get(i);
			}
		}
		if(simon != null && !broken && simon.leftRectangle().intersects(this.getBounds()) && simon.flipped && Global.swingingClub)
		{
			broken = true;
		}
		if(simon != null && !broken && simon.rightRectangle().intersects(this.getBounds()) && !simon.flipped && Global.swingingClub)
		{
			broken = true;
		}
		if(Quest.quests[Quest.PYRUZ_S] == 1)
		{
			learnClub();
		}
	}

	@Override
	public void render(Graphics g) {
		if(broken && getFrame(0) == 0)
		{
			AudioHandler.playSound(AudioHandler.seBreak);
		}
		this.animate(x,y,currentAnimation,0,g);
		if(showingRage)
		{
			if(rageFlipped)
			{
				this.animateFlip(rageX,y+150,rage,1,g);
			}
			else
			{
				this.animateNoFlip(rageX,y+150,rage,1,g);
			}
		}
		if(!broken)
		{
			setFrame(0,0);
		}
		if(broken && getFrame(0)>=5)
		{
			om.objects.remove(this);
		}
	}
	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(x,y,100,400);
	}
	private void learnClub()
	{
		if(stage == 0 && player.x < this.x + 50 && player.y >= this.y)
		{
			Global.disableMovement = true;
			showingRage = true;
			rageFlipped = true;
			rage = OverworldAnimation.rageWalk;
			rageX = player.x;
			stage++;
		}
		if(stage == 1)
		{
			rageX += 6;
			player.flipped = true;
			if(rageX >= player.x+150)
			{
				stage++;
				rage = OverworldAnimation.rageIdle;
			}
		}
		if(stage == 2)
		{
			if(Global.talking == 0)
			{
				rageFlipped = false;
				rage = OverworldAnimation.rageTalk;
				Global.talking = 1;
				SpeechBubble.talk("Cavedude, if you press [V] on the Overworld (the place outside of/battles), you are able to swing that little club of yours!/Go on, try using it to break this stupid wall thingy!");
			}
			if(Global.talking == 2)
			{
				Global.talking = 0;
				stage++;
				Global.disableMovement = true;
			}
		}
		if(stage == 3)
		{
			rage = OverworldAnimation.rageWalk;
			rageX -= 6;
			if(rageX<=player.x)
			{
				showingRage = false;
				stage = 0;
				Quest.quests[Quest.PYRUZ_S]++;
				Global.disableMovement = false;
			}
		}
	}

}
