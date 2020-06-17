package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.WilliamPlayer;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;

public class IggyOverworld1 extends NPC{

	BufferedImage[] cage;
	SimonPlayer simon;
	int cageY = 500;
	int cageX;
	int cageVelocity;
 	public IggyOverworld1(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		currentAnimation = BattleAnimation.iggyIdle;
		cage = OverworldAnimation.cage;
		this.id = ObjectId.IggyOverworld;
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
		return new Rectangle(x+52,y+270,250,120);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x+52,y+52,250,320);
	}

	@Override
	public void render(Graphics g) {
		this.animate(cageX, cageY, cage, 1, g);
		if(getFrame(0)>currentAnimation[2].getWidth())
		{
			setFrame(0,0);
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
					simon = (SimonPlayer) om.objects.get(i);
				}
			}
		}
		else
		{
			cageX = simon.x-25;
		}
		if(Quest.quests[Quest.TUTORIAL]<=13)
		{
			om.objects.remove(this);
		}
		else if(Quest.quests[Quest.LOMOBANK]>11)
		{
			om.objects.remove(this);
		}
		
		if(simon != null && simon.x >= 600 && Quest.quests[Quest.LOMOBANK] == 7)
		{
			Global.disableMovement = true;
			if(cageY>=1250)
			{
				Quest.quests[Quest.LOMOBANK] = 8;
				cageY = 1250;
				cageVelocity = 0;
				AudioHandler.playMusic(AudioHandler.timeForTrouble);
			}
			else
			{
				cageVelocity+=2;
				cageY += cageVelocity;
				System.out.println(cageY);
			}
			
		}
		if(Quest.quests[Quest.LOMOBANK] == 8)
		{
			currentAnimation = BattleAnimation.iggyWalk;
			x -= 8;
			if(x<simon.x+200)
			{
				Quest.quests[Quest.LOMOBANK] = 9;
			}
		}
		if(Quest.quests[Quest.LOMOBANK] == 9)
		{
			if(Global.talking == 0)
			{
				setFrame(0,0);
				Global.talking = 1;
				Global.talkingTo = this;
				currentAnimation = BattleAnimation.iggyTalk;
				SpeechBubble.talk("BWUH HAKK HEHHHH!!! I AM THE KING OF THE STONE AGE, IGNACIO!/NOW GIVE ME THE ALMIGHTY ORB OF POWER, FOR I/HAVE TRAPPED YOU IN A CAGE!!! THAT DEFINITELY GIVES YOU/REASON TO LISTEN TO ME, DOES IT NOT!?!",Global.iggyFont);
			}
			if(Global.talking == 2)
			{
				Global.talking = 3;
				Global.talkingTo = this;
				currentAnimation = BattleAnimation.iggyTalk;
				SpeechBubble.talk("NOW, SINCE I KNOW YOU'LL ATTEMPT TO DISOBEY MY ALMIGHTY POWER,/I'LL MAKE YOU A GUEST AT MY VOLCANO...AS THE PRISONER!!!/MY PRISON'S CONDITIONS ARE WORLD RENOWNED FOR THEIR HORRIBLENESS!!/THE ORB'S AS GOOD AS MINE!! BWUH HACK HAUH *cough* HUAHHHHH!",Global.iggyFont);
			}
			if(Global.talking == 4)
			{
				Global.disableMovement = true;
				currentAnimation = BattleAnimation.iggyWalk;
				Global.talking = 0;
				Quest.quests[Quest.LOMOBANK] = 10;
			}
		}
		if(Quest.quests[Quest.LOMOBANK] == 10)
		{
			Global.disableMovement = true;
			x -= 6;
			if(this.x<cageX-264)
			{
				Quest.quests[Quest.LOMOBANK] = 11;
			}
		}

		if(Quest.quests[Quest.LOMOBANK] == 11)
		{
			Global.disableMovement = true;
			this.flipped = true;
			this.currentAnimation = BattleAnimation.iggyPush;
			simon.x += 6;
			this.x += 6;
			if(x>1400)
			{
				om.objects.add(new WilliamPlayer(window,100,1300,om));
				om.objects.remove(simon);
				om.objects.remove(this);
				Global.hasSimon = false;
				Global.hasWilliam = true;
				Global.playingAs = 1;
				Quest.quests[Quest.LOMOBANK] = 11;
			}
		}
		vVelocity+=2;
		for(int i=0; i<window.overworldMode.objects.size();i++)
		{
			if(window.overworldMode.objects.get(i).id == ObjectId.Grass)
			{
				Grass g = (Grass)window.overworldMode.objects.get(i);

				if(g.getBounds().intersects(bottomRectangle()) && vVelocity>0)
				{
					vVelocity = 0;
					this.y = g.y-320;
				}
			}
		}
		y += vVelocity;
	}

}
