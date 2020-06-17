package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.blocks.Grass;
import net.truttle1.time.overworld.npc.NPC;
import net.truttle1.time.overworld.npc.carl.Carl2;

public class IggyCutscene extends NPC{

	private Carl2 carl;
	private int walkPhase = 0;
	public IggyCutscene(Game window, int x, int y, OverworldMode om, Carl2 carl) {
		super(window, x, y, om);
		this.carl = carl;
		currentAnimation = BattleAnimation.iggyPush;
		this.id = ObjectId.IggyOverworld;
		this.flipped = true;
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
		if(getFrame(0)>currentAnimation[2].getWidth())
		{
			setFrame(0,0);
		}
		this.animate(x, y, currentAnimation, 0, g);
	}
	public void tick()
	{

		if(Quest.quests[Quest.LOMO] == 2)
		{
			if(currentAnimation == BattleAnimation.iggyPush || currentAnimation == BattleAnimation.iggyWalk)
			{
				hVelocity = 6;
			}
			else
			{
				hVelocity = 0;
				if(Global.talking == 0)
				{
					this.flipped = true;
					currentAnimation = BattleAnimation.iggyTaunt;
					Global.talking = 1;
					SpeechBubble.talk("BWUCK HUCK HACK HAAAAAACK!!! Look what I just captured! Now, just/for fun, I'm gonna hide this loser crocodile, and STUPIDZILLA and LITTLE SQUIRT/are gonna have to find him! BWUCK HACK HAAAAAAAACK!!",Global.iggyFont);
					
				}
				if(Global.talking == 2 || Global.talking == 3)
				{
					currentAnimation = BattleAnimation.iggyIdleTaunt;
				}
				if(Global.talking == 4)
				{
					currentAnimation = BattleAnimation.iggyTaunt2;
					Global.talking = 5;
					SpeechBubble.talk("Shut up you puny little crocodile! Face it, you've been captured by the/King of the Stone Age himself, IGNACIO THE FIRST!! Oh, and don't count/on Stupidzilla to find you. He looks dumb and stupid. And Little Squirt's all...little!/Well, time to put you somewhere and get some beauty sleep!",Global.iggyFont);
				}
				if(Global.talking == 6)
				{
					currentAnimation = BattleAnimation.iggyTaunt;
					Global.talking = 7;
					SpeechBubble.talk("BWACK HACK HACK HACCCCCCCCKKK!!! G'night!",Global.iggyFont);
					walkPhase = 0;
				}
				if(Global.talking == 8)
				{
					Quest.quests[Quest.LOMO] = 3;
				}
				
			}
			
		}
		if(Quest.quests[Quest.LOMO] == 3)
		{
			
			if(walkPhase == 0)
			{
				Global.talking = 0;
				currentAnimation = BattleAnimation.iggyWalk;
				hVelocity = 6;
				if(x>carl.x+180)
				{
					walkPhase = 1;
				}
			}

			if(walkPhase == 1)
			{
				this.flipped = false;
				hVelocity = -6;
				carl.hVelocity = -6;
				currentAnimation = BattleAnimation.iggyPush;
				if(x<800)
				{
					Quest.quests[Quest.LOMO] = 4;
					Global.talking = 0;
					om.objects.remove(carl);
					om.objects.remove(this);
				}
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
		x += hVelocity;
	}
}
