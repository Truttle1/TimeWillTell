package net.truttle1.time.overworld.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.monsters.Ursear;
import net.truttle1.time.battle.monsters.WilliamEnemy1;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.SimonPlayer;
import net.truttle1.time.overworld.items.YellowOrb;
import net.truttle1.time.overworld.npc.NPC;

public class WilliamE1 extends NPC{

	private int inputDelay = -1;

	SimonPlayer simon;
	public WilliamE1(Game window, int x, int y, OverworldMode om) {
		super(window,x,y, om);
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
		if(Quest.quests[Quest.TUTORIAL] == 9)
		{
			if(x>simon.x-200)
			{
				if(Global.talking == 0)
				{
					currentAnimation = BattleAnimation.williamTalkPoint;
					Global.talkingTo = this;
					Global.talking = 1;
					SpeechBubble.talk("Did you not read those signs that I put up back there?!/You're not supposed to be here!!! Listen dude, there are/secrets surrounding this place. Secretive secrets.../SECRETS YOU COULD NOT BEGIN TO UNDERSTAND!!!",Global.willFont);
				}
				if(Global.talking == 2)
				{
					Global.talkingTo = this;
					Global.talking = 3;
					SpeechBubble.talk("So if I were you, I would leave this place./AND NEVER RETURN!!! I don't need any cavemen coming in and screwing/up my plans! Also, let's face it, If I came into your house like this,/you'd be pretty ticked as well!",Global.willFont);

				}
				if(Global.talking == 4)
				{
					Global.talkingTo = this;
					Global.talking = 5;
					SpeechBubble.talk("WHY ARE YOU STILL HERE!?!?!/*sigh* I THINK I NEED TO TEACH YOU A LESSON!!!",Global.willFont);
				
				}
				if(Global.talking == 6)
				{
					this.flipped = true;
					currentAnimation = BattleAnimation.williamWalk2;
					setFrame(0,0);
					window.battleMode.addMonster(new WilliamEnemy1(window,720,380,window.battleMode));
					AudioHandler.playMusic(AudioHandler.williamBattleMusic);
					window.battleMode.startBattle();
					Fade.run(window, ModeType.Battle,false);
					Global.talking = 0;
					Global.disableMovement = false;
					Quest.quests[Quest.TUTORIAL] = 10;
				}
			}
			else
			{
				this.flipped = true;
				currentAnimation = BattleAnimation.williamWalk2;
				this.x += 14;
			}
		}

		if(Quest.quests[Quest.TUTORIAL] == 10)
		{
			this.x += 12;
		}
		if(Quest.quests[Quest.TUTORIAL] == 11)
		{

			if(Global.talking == 0)
			{
				Global.disableMovement = true;
				x = simon.x-100;
				AudioHandler.playMusic(AudioHandler.williamTheme);
				currentAnimation = BattleAnimation.williamTalkDead;
				Global.talkingTo = this;
				Global.talking = 1;
				SpeechBubble.talk("Yeowch!! That hurt...a lot...Just...get out/of here...and let me lie here in peace...and never come back/...NEVER AGAIN....",Global.willFont);
			}
			if(Global.talking == 2)
			{
				Quest.quests[Quest.TUTORIAL] = 12;
				Global.talking = 0;
				currentAnimation = BattleAnimation.williamDeadAnimation;
				YellowOrb orb = new YellowOrb(om.window,x+10,y,om);
				om.objects.add(orb);
				orb.vVelocity = -12;

			}
		}
	}

}
