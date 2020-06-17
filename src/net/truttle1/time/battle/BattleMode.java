package net.truttle1.time.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.truttle1.time.battle.monsters.Monster;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.main.Game;
import net.truttle1.time.overworld.OverworldAnimation;

public class BattleMode extends GameMode{

	public int amountOfMonsters = -1;
	public int selectedMonsterID = 0;
	public Monster selectedMonster;
	public int attackingMonster;
	private boolean phaseOneStarted;
	private boolean lostSting;
	public int moneyGained;
	public int xpGainedSimon;
	private BufferedImage[] tutorialSuzy;
	private double[] currentFrame = new double[9];
	public int xpGainedWilliam;
	public Monster[] monsters = new Monster[9];
	public ArrayList<GameObject> objects = new ArrayList<GameObject>();
	public AttackSelection attackSelection;
	public int simonSelections = 1;
	public int williamSelections = 1;
	public int[] partnerSelections = new int[99];
	private int victoryY = -500;
	public boolean levelingUp;
	public int leveler;
	private int levelUpSelections;
	private int currentPartner;
	public int specialType;
	public boolean monsterIsSelected;
	public boolean partnerIsSelected;
	public int selectedPartner;
	public int[] xpGainedPartner = new int[99];
	
	public BattleMode(Game window) {
		super(window);
		phaseOneStarted = false;
		partnerSelections[Global.SKRAPPS] = 2;
	}

	private void tutorial()
	{
		if(Global.tutorialBattlePhase == 0)
		{

			if(Global.talking == 0)
			{
				tutorialSuzy = OverworldAnimation.suzyTalk;
				Global.talkingTo = null;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("To select an attack, you move up and down with the arrow keys/and select with 'Z'.");
			}
			if(Global.talking == 2)
			{
				Global.zPressed = false;
				Global.talking = 0;
				Global.disableMovement = false;
				Global.tutorialBattlePhase++;
				tutorialSuzy = OverworldAnimation.suzyIdle;
			}
		}
		if(Global.tutorialBattlePhase == 2)
		{

			if(Global.talking == 0)
			{
				tutorialSuzy = OverworldAnimation.suzyTalk;
				Global.talkingTo = null;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Press 'Z' when your arm is furthest back in your punch in order/to deal maximum damage.");
			}
			if(Global.talking == 2)
			{
				Global.zPressed = false;
				Global.talking = 0;
				Global.disableMovement = false;
				Global.tutorialBattlePhase++;
				tutorialSuzy = OverworldAnimation.suzyIdle;
			}
		}
		if(Global.tutorialBattlePhase == 4)
		{

			if(Global.talking == 0)
			{
				tutorialSuzy = OverworldAnimation.suzyTalk;
				Global.talkingTo = null;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("Press 'C' when your enemy is about to hit you./This will cause you to block, and you will take less damage!/It might be difficult to time it correctly, though.");
			}
			if(Global.talking == 2)
			{
				Global.zPressed = false;
				Global.talking = 0;
				Global.disableMovement = false;
				Global.tutorialBattlePhase++;
				tutorialSuzy = OverworldAnimation.suzyIdle;
			}
		}
		if(Global.tutorialBattlePhase == 5 && Global.attacker == Attacker.Simon)
		{

			if(Global.talking == 0)
			{
				tutorialSuzy = OverworldAnimation.suzyTalk;
				Global.talkingTo = null;
				Global.talking = 1;
				Global.disableMovement = true;
				SpeechBubble.talk("That's all you need to know for right now!/'Z' to attack, and 'C' to dodge.");
			}
			if(Global.talking == 2)
			{
				Global.zPressed = false;
				Global.talking = 0;
				Global.disableMovement = false;
				Global.tutorialBattlePhase++;
				tutorialSuzy = OverworldAnimation.suzyIdle;
			}
		}
	}
	@Override
	public void tick() {
		if(amountOfMonsters<selectedMonsterID)
		{
			selectedMonsterID = 0;
		}
		if(Global.williamSP>Global.williamSPMax)
		{
			Global.williamSP = Global.williamSPMax;
		}
		if(Global.simonSP>Global.simonSPMax)
		{
			Global.simonSP = Global.simonSPMax;
		}
		for(int i=0; i<Global.partnerSP.length;i++)
		{
			if(Global.partnerSP[i]>Global.partnerSPMax[i])
			{
				Global.partnerSP[i] = Global.partnerSPMax[i];
			}
		}
		//System.out.println(this.attackingMonster);
		if(levelingUp)
		{
			if(Global.downPressed)
			{
				if(levelUpSelections>=3)
				{
					levelUpSelections = 0;
				}
				else
				{
					levelUpSelections++;
				}
			}
			if(Global.upPressed)
			{
				if(levelUpSelections<=0)
				{
					levelUpSelections = 3;
				}
				else
				{
					levelUpSelections--;
				}
			}
		}
		if(!Global.hasSimon && Global.attacker == Attacker.Simon)
		{
			Global.attacker = Attacker.William;
		}
		if(Quest.quests[Quest.PYRUZ_S]>=2)
		{
			simonSelections = 3;
		}
		else if(Quest.quests[Quest.LOMOBANK]>=1)
		{
			simonSelections = 2;
		}
		if(Global.tutorialBattle)
		{
			tutorial();
		}
		if(!Global.hasWilliam && !Global.hasPartner && Global.simonHP == 0 && Global.attackPhase == 0)
		{
			Global.attacker = Attacker.Lose;
		}
		if(Global.hasWilliam && !Global.hasPartner && Global.simonHP == 0  && Global.williamHP == 0 && Global.attackPhase == 0)
		{
			Global.attacker = Attacker.Lose;
		}
		if(Global.hasWilliam && !Global.hasPartner && !Global.hasSimon && Global.williamHP == 0 && Global.attackPhase == 0)
		{
			Global.attacker = Attacker.Lose;
		}
		if(amountOfMonsters<0 && Global.attackPhase == 0)
		{
			if(Global.attacker != Attacker.Win)
			{
				Global.bossBattle = false;
				Global.simonXP += xpGainedSimon;
				Global.williamXP += xpGainedWilliam;
				for(int i=0; i<Global.partnerXP.length;i++)
				{
					Global.partnerXP[i] += xpGainedPartner[i];
				}
			}
			Global.attacker = Attacker.Win;
			
		}
		if(false && Global.vPressed)
		{
			if(Global.attacker == Attacker.Monsters)
			{
				Global.attacker = Attacker.Simon;
			}
			else
			{
				Global.attacker = Attacker.Monsters;
				this.attackingMonster = 0;
			}
		}
		if(!Fade.running && Global.attacker == Attacker.Win && victoryY == 0 && Global.zPressed)
		{
			if(Global.williamHP<1)
			{
				Global.williamHP = 1;
			}
			if(Global.simonHP<1)
			{
				Global.simonHP = 1;
			}
			if(levelingUp && leveler == 2)
			{
				if(levelUpSelections == 0)
				{
					Global.partnerHPMax[Global.SKRAPPS]+=5;
					Global.partnerHP[Global.SKRAPPS] = Global.partnerHPMax[Global.SKRAPPS];
					levelingUp = false;
				}
				if(levelUpSelections == 1)
				{
					Global.partnerSPMax[Global.SKRAPPS]+=4;
					Global.partnerSP[Global.SKRAPPS] = Global.partnerSPMax[Global.SKRAPPS];
					levelingUp = false;
				}
				if(((Global.partnerPow[Global.SKRAPPS])*5)-5<Global.partnerLevel[Global.SKRAPPS] && levelUpSelections == 2)
				{
					Global.partnerPow[Global.SKRAPPS]+=1;
					levelingUp = false;
				}
			}
			if(levelingUp && leveler == 1)
			{
				if(levelUpSelections == 0)
				{
					Global.williamHPMax+=5;
					Global.williamHP = Global.williamHPMax;
					levelingUp = false;
				}
				if(levelUpSelections == 1)
				{
					Global.williamSPMax+=4;
					Global.williamSP = Global.williamSPMax;
					levelingUp = false;
				}
				if(((Global.williamPow)*5)-5<=Global.williamLevel && levelUpSelections == 2)
				{
					Global.williamPow+=1;
					levelingUp = false;
				}
			}
			if(levelingUp && leveler == 0)
			{
				if(levelUpSelections == 0)
				{
					Global.simonHPMax+=5;
					Global.simonHP = Global.simonHPMax;
					levelingUp = false;
				}
				if(levelUpSelections == 1)
				{
					Global.simonSPMax+=4;
					Global.simonSP = Global.simonSPMax;
					levelingUp = false;
				}
				if(((Global.simonPow)*5)-5<Global.simonLevel && levelUpSelections == 2)
				{
					Global.simonPow+=1;
					levelingUp = false;
				}
			}
			if(!levelingUp)
			{
				if(Global.williamXP >= (int) ((Math.pow(2, Global.williamLevel)*25)))
				{
					levelingUp = true;
					leveler = 1;
					Global.williamLevel++;
					Global.williamXP -= (int) ((Math.pow(2, Global.williamLevel)*25));
					if(Global.williamXP<0)
					{
						Global.williamXP = 0;
					}
					levelUpSelections = 0;
					AudioHandler.playMusic(AudioHandler.levelUpWilliam);
					Global.williamHP = Global.williamHPMax;
					Global.williamSP = Global.williamSPMax;
					
				}
				else if(Global.partnerXP[Global.SKRAPPS] >= (int) ((Math.pow(2, Global.partnerLevel[Global.SKRAPPS])*25)))
				{
					levelingUp = true;
					leveler = 2;
					Global.partnerLevel[Global.SKRAPPS]++;
					Global.partnerXP[Global.SKRAPPS] -= (int) ((Math.pow(2, Global.partnerLevel[Global.SKRAPPS])*25));
					if(Global.partnerXP[Global.SKRAPPS]<0)
					{
						Global.partnerXP[Global.SKRAPPS] = 0;
					}
					levelUpSelections = 0;
					AudioHandler.playMusic(AudioHandler.skrapps);
					Global.partnerHP[Global.SKRAPPS] = Global.partnerHPMax[Global.SKRAPPS];
					Global.partnerSP[Global.SKRAPPS] = Global.partnerSPMax[Global.SKRAPPS];
					
				}
				else if(Global.simonXP >= (int) ((Math.pow(2, Global.simonLevel)*25)))
				{
					levelingUp = true;
					leveler = 0;
					Global.simonLevel++;
					Global.simonXP -= (int) ((Math.pow(2, Global.simonLevel)*25));
					if(Global.simonXP<0)
					{
						Global.simonXP = 0;
					}
					levelUpSelections = 0;
					AudioHandler.playMusic(AudioHandler.levelUpSimon);
					Global.simonHP = Global.simonHPMax;
					Global.simonSP = Global.simonSPMax;
					
				}
				else
				{
				for(int i=0; i<eyeCandy.size();i++)
				{
					eyeCandy.remove(i);
					continue;
				}
				Fade.run(window, ModeType.Overworld,true);
				for(int h=0;h<20;h++)
				{
					for(int i=0; i<objects.size();i++)
					{
						if(objects.get(i) != null && objects.get(i).id == ObjectId.SimonBattler)
						{
							objects.remove(i);
							continue;
						}
						if(objects.get(i).id == ObjectId.WilliamBattler)
						{
							objects.remove(i);
							continue;
						}
						if(objects.get(i).id == ObjectId.SkrappsBattler)
						{
							objects.remove(i);
							continue;
						}
						if(objects.get(i).id == ObjectId.RageBattler)
						{
							objects.remove(i);
							continue;
						}
						if(objects.get(i) != null && objects.get(i).id == ObjectId.Monster)
						{
							objects.remove(i);
							continue;
						}
					}
				}
				victoryY = -500;
				}
			}
		}
		for(int i=0; i<objects.size();i++)
		{
			objects.get(i).tick();
		}

		for(int i=0; i<eyeCandy.size();i++)
		{
			eyeCandy.get(i).tick();
		}
		if(Global.attackPhase == 0)
		{
			phaseOneStarted = false;
		}

		if(this.attackSelection == AttackSelection.Special && Global.attacker == Attacker.Partner && Global.currentPartner == Global.SKRAPPS && specialType == 2)
		{
			selectPartner();
		}
		if((Global.attacker != Attacker.Monsters && Global.attackPhase == 1)|| (Global.attacker != Attacker.Monsters && specialType == 1 && attackSelection == AttackSelection.Special))
		{

			if(Global.xPressed)
			{
				Global.attackPhase--;
			}
			if(Global.leftPressed)
			{
				if(selectedMonsterID == 0)
				{
					selectedMonsterID = amountOfMonsters;
				}
				else
				{
					selectedMonsterID--;
				}
			}
			else if(Global.rightPressed)
			{
				if(selectedMonsterID == amountOfMonsters)
				{
					selectedMonsterID = 0;
				}
				else
				{
					selectedMonsterID++;
				}
			}
			if(Global.attackPhase == 1)
			{
				if(Global.zPressed && phaseOneStarted)
				{
					Global.attackPhase++;
				}
				phaseOneStarted = true;
			}
			else
			{
				if(Global.zPressed)
				{
					monsterIsSelected = true;
				}
			}
		}
	}

	private BufferedImage loadFrame(BufferedImage[] animation, int frame)
	{
		int height = animation[1].getHeight();
		int width = animation[1].getWidth();
		int offset = (width * frame);
		try
		{
			 return animation[0].getSubimage(offset, 0, width, height);
		}
		catch(Exception e)
		{
			 return animation[0].getSubimage(0, 0, width, height);
		}
	}
	protected void animate(int x, int y,BufferedImage[] animation, int cFrame, Graphics g)
	{
		int tx = window.overworldMode.tx;
		int ty = window.overworldMode.ty;
		int roundedFrame = (int)(currentFrame[cFrame]/Global.framePerImg)*Global.framePerImg;
		if(loadFrame(animation,roundedFrame) == null)
		{
			roundedFrame = 0;
			currentFrame[cFrame] = 0;
		}
		if(roundedFrame < 0) 
		{
			roundedFrame = 0;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(loadFrame(animation,roundedFrame),x,y, null);

		currentFrame[cFrame]+=1;
		if(currentFrame[cFrame] > animation[2].getWidth())
		{
			currentFrame[cFrame] = 0;
		}
	}
	
	public int getFrame(int cFrame)
	{
		return (int) currentFrame[cFrame];
	}
	public void setFrame(int cFrame, double newFrame)
	{
		currentFrame[cFrame] = newFrame;
	}
	@Override
	public void render(Graphics g) {
		if(Game.currentWorld == WorldId.StoneAge)
		{
			g.drawImage(Game.prehistoricBackground,0,0,null);
		}
		else if(Game.currentWorld == WorldId.Pyruz)
		{
			g.drawImage(Game.pyruzBattle,0,0,null);
		}
		else if(Game.currentWorld == WorldId.Digital)
		{
			g.drawImage(Game.city,0,0,null);
		}
		if(Global.tutorialBattle)
		{
			if(Global.talking == 0)
			{
				if(tutorialSuzy == null)
				{
					tutorialSuzy = OverworldAnimation.suzyIdle;
				}
			}
			this.animate(150, 380, tutorialSuzy, 0, g);
		}
		if(Global.hasWilliam)
		{
			g.setColor(Color.red);
			g.fillRect(64, 128, 120, 32);
			g.setColor(Color.green);
			g.fillRect(64, 128, (int)(120*(double)Global.williamHP/(double)Global.williamHPMax), 32);

			if(Quest.quests[Quest.PYRUZ_W]>=1)
			{
				g.setColor(Color.black);
				g.fillRect(64, 160, 120, 16);
				g.setColor(Color.cyan);
				g.fillRect(64, 160, (int)(120*(double)Global.williamSP/(double)Global.williamSPMax), 16);
			}
			
		}
		if(Global.hasPartner)
		{
			if(Global.currentPartner == Global.SKRAPPS)
			{
				g.setColor(Color.red);
				g.fillRect(64, 192, 120, 32);
				g.setColor(Color.blue);
				g.fillRect(64, 192, (int)(120*(double)Global.partnerHP[Global.SKRAPPS]/(double)Global.partnerHPMax[Global.SKRAPPS]), 32);

				//System.out.println(Quest.quests[Quest.PYRUZ_W]);
				g.setColor(Color.black);
				g.fillRect(64, 224, 120, 16);
				g.setColor(Color.cyan);
				g.fillRect(64, 224, (int)(120*(double)Global.partnerSP[Global.SKRAPPS]/(double)Global.partnerSPMax[Global.SKRAPPS]), 16);
				
			}
			
		}
		if(Global.hasSimon)
		{
			g.setColor(Color.red);
			g.fillRect(64, 64, 120, 32);
			g.setColor(Color.orange);
			g.fillRect(64, 64, (int)(120*(double)Global.simonHP/(double)Global.simonHPMax), 32);
			
			if(Quest.quests[Quest.PYRUZ_S]>=1)
			{
				g.setColor(Color.black);
				g.fillRect(64, 96, 120, 16);
				g.setColor(Color.cyan);
				g.fillRect(64, 96, (int)(120*(double)Global.simonSP/(double)Global.simonSPMax), 16);
				
			
			}
		}
		if(Global.attacker == Attacker.Lose)
		{

			if(!lostSting)
			{
				lostSting = true;
				AudioHandler.stopMusic();
				//AudioHandler.playSound(AudioHandler.seLose);
			}
			g.setColor(Color.gray.darker());
			g.setFont(Global.winFont2);
			g.drawString("Game Over!", 305, 162);
			g.setColor(Color.red);
			g.setFont(Global.winFont2);
			g.drawString("Game Over!", 300, 156);
			for(int i=0; i<objects.size();i++)
			{
					objects.get(i).render(g);
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}
		
		for(int i=0; i<objects.size();i++)
		{
			if(Global.attacker == Attacker.Simon && !(objects.get(i) instanceof SimonBattler))
			{
				objects.get(i).render(g);
			}
			else if(Global.attacker == Attacker.Monsters && !(objects.get(i) instanceof Monster))
			{
				objects.get(i).render(g);
			}
			else if(Global.attacker == Attacker.William && !(objects.get(i) instanceof WilliamBattler))
			{
				objects.get(i).render(g);
			}
			else if(Global.attacker == Attacker.Partner && !(objects.get(i) instanceof SkrappsBattler))
			{
				objects.get(i).render(g);
			}
		}
		if(Global.attacker == Attacker.Simon)
		{
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i) instanceof SimonBattler)
				{
					objects.get(i).render(g);
				}
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}
		if(Global.attacker == Attacker.William)
		{
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i) instanceof WilliamBattler)
				{
					objects.get(i).render(g);
				}
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}

		if(Global.attacker == Attacker.Partner)
		{
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i) instanceof SkrappsBattler)
				{
					objects.get(i).render(g);
				}
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}
		if(Global.attacker == Attacker.Monsters)
		{
			for(int i=0; i<objects.size();i++)
			{
				if(objects.get(i) instanceof Monster)
				{
					objects.get(i).render(g);
				}
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
		}
		g.setColor(Color.gray);
		if(Global.attacker == Attacker.Win)
		{

			for(int i=0; i<objects.size();i++)
			{
					objects.get(i).render(g);
			}
			for(int i=0; i<eyeCandy.size();i++)
			{
				eyeCandy.get(i).render(g);
			}
			if(!lostSting)
			{
				lostSting = true;
				AudioHandler.stopMusic();
				AudioHandler.playSound(AudioHandler.seWin);
			}
			if(victoryY<0)
			{
				victoryY+=20;
			}
			if(victoryY>0)
			{
				victoryY = 0;
			}
			if(!levelingUp)
			{
				g.setColor(Color.gray.darker());
				g.setFont(Global.winFont2);
				g.drawString("You Won!", 305, 162+victoryY);
				g.setColor(Color.white);
				g.setFont(Global.winFont2);
				g.drawString("You Won!", 300, 156+victoryY);
	
				g.setFont(Global.winFont1);
				g.setColor(Color.black);
				if(Global.hasSimon)
				{
					g.drawString("Simon gained: " + xpGainedSimon + " XP!", 260, 232+victoryY);
				}
				if(Global.hasWilliam)
				{
					g.drawString("William gained: " + xpGainedWilliam + " XP!", 260, 282+victoryY);
				}		
				if(Global.hasPartner)
				{
					if(Global.currentPartner == Global.SKRAPPS)
					{
						g.drawString("Skrapps gained: " + xpGainedPartner[Global.currentPartner] + " XP!", 260, 332+victoryY);
					}
					
				}					
				g.drawString("You also gained: $" + moneyGained, 260, 382+victoryY);
				
			}
			
		}
		if(this.attackSelection == AttackSelection.Special && Global.attacker == Attacker.Partner && Global.currentPartner == Global.SKRAPPS && specialType == 2)
		{
			selectPartnerRender(g);
		}
		levelUp(g);
	}
	private void levelUp(Graphics g)
	{
		if(levelingUp && leveler == 0)
		{
			g.setColor(Color.orange);
			g.fillRect(420, 32, 600, 512);
			g.setFont(Global.winFont1);
			g.setColor(Color.orange.darker().darker());
			g.drawString("SIMON HAS LEVELED UP!", 445, 72);
			g.setFont(Global.battleFont);
			g.drawString("Select a stat to increase!", 445, 116);
			
			g.setColor(Color.white);
			g.fillRect(435, 140+(levelUpSelections*100), 570, 64);
			g.setFont(Global.winFont1);
			g.setColor(Color.orange.darker().darker());
			g.drawString("HP:", 445, 185);
			g.drawString("SP:", 445, 285);
			g.drawString("ST:", 445, 385);
			g.setFont(Global.battleFont);
			g.drawString("Increases Simon's HP from " + Global.simonHPMax + " to " + (Global.simonHPMax+5) + "!", 530, 157);
			g.drawString("With more HP, Simon can take more", 530, 177);
			g.drawString("damage from enemies!", 530, 197);

			g.drawString("Increases Simon's SP from " + Global.simonSPMax + " to " + (Global.simonSPMax+4) + "!", 530, 257);
			g.drawString("With more SP, Simon can use more", 530, 277);
			g.drawString("of his Special Attacks!", 530, 297);
			if(((Global.simonPow)*5)-5>Global.williamLevel)
			{
				int lvl = (Global.simonPow*5)-5;
				g.drawString("This stat cannot be leveled up until", 530, 357);
				g.drawString("level " + lvl + "!", 530, 377);
			}
			else
			{
				g.drawString("Increases Strength from " + Global.simonPow + " to " + (Global.simonPow+1) + "!", 530, 357);
				g.drawString("With more Strength, Simon's attacks", 530, 377);
				g.drawString("will do more damage!", 530, 397);
			}
		}
		if(levelingUp && leveler == 1)
		{
			g.setColor(Color.green);
			g.fillRect(420, 32, 600, 512);
			g.setFont(Global.winFont1);
			g.setColor(Color.green.darker().darker());
			g.drawString("WILLIAM HAS LEVELED UP!", 445, 72);
			g.setFont(Global.battleFont);
			g.drawString("Select a stat to increase!", 445, 116);
			
			g.setColor(Color.white);
			g.fillRect(435, 140+(levelUpSelections*100), 570, 64);
			g.setFont(Global.winFont1);
			g.setColor(Color.green.darker().darker());
			g.drawString("HP:", 445, 185);
			g.drawString("SP:", 445, 285);
			g.drawString("ST:", 445, 385);
			g.setFont(Global.battleFont);
			g.drawString("Increases William's HP from " + Global.williamHPMax + " to " + (Global.williamHPMax+5) + "!", 530, 157);
			g.drawString("With more HP, William can take more", 530, 177);
			g.drawString("damage from enemies!", 530, 197);

			g.drawString("Increases William's SP from " + Global.williamSPMax + " to " + (Global.williamSPMax+4) + "!", 530, 257);
			g.drawString("With more SP, William can use more", 530, 277);
			g.drawString("of his Special Attacks!", 530, 297);
			if(((Global.williamPow)*5)-5>Global.williamLevel)
			{
				int lvl = (Global.williamPow*5)-5;
				g.drawString("This stat cannot be leveled up until", 530, 357);
				g.drawString("level " + lvl + "!", 530, 377);
			}
			else
			{
				g.drawString("Increases Strength from " + Global.williamPow + " to " + (Global.williamPow+1) + "!", 530, 357);
				g.drawString("With more Strength, William's attacks", 530, 377);
				g.drawString("WILL do more damage!", 530, 397);
			}
		}
		if(levelingUp && leveler == 2)
		{
			g.setColor(Color.blue);
			g.fillRect(420, 32, 600, 512);
			g.setFont(Global.winFont1);
			g.setColor(Color.blue.darker().darker());
			g.drawString("SKRAPPS HAS LEVELED UP!", 445, 72);
			g.setFont(Global.battleFont);
			g.drawString("Select a stat to increase!", 445, 116);
			
			g.setColor(Color.white);
			g.fillRect(435, 140+(levelUpSelections*100), 570, 64);
			g.setFont(Global.winFont1);
			g.setColor(Color.blue.darker().darker());
			g.drawString("HP:", 445, 185);
			g.drawString("SP:", 445, 285);
			g.drawString("ST:", 445, 385);
			g.setFont(Global.battleFont);
			g.drawString("Increases Skrapps's HP from " + Global.partnerHPMax[Global.SKRAPPS] + " to " + (Global.partnerHPMax[Global.SKRAPPS]+5) + "!", 530, 157);
			g.drawString("With more HP, Skrapps can take more", 530, 177);
			g.drawString("damage from enemies!", 530, 197);

			g.drawString("Increases Skrapps's SP from " + Global.partnerSPMax[Global.SKRAPPS] + " to " + (Global.partnerSPMax[Global.SKRAPPS]+4) + "!", 530, 257);
			g.drawString("With more SP, Skrapps can use more", 530, 277);
			g.drawString("of his Special Attacks!", 530, 297);
			if(((Global.partnerPow[Global.SKRAPPS])*5)-5>Global.partnerLevel[Global.SKRAPPS])
			{
				int lvl = (Global.partnerPow[Global.SKRAPPS]*5)-5;
				g.drawString("This stat cannot be leveled up until", 530, 357);
				g.drawString("level " + lvl + "!", 530, 377);
			}
			else
			{
				g.drawString("Increases Strength from " + Global.partnerPow[Global.SKRAPPS] + " to " + (Global.partnerPow[Global.SKRAPPS]+1) + "!", 530, 357);
				g.drawString("With more Strength, Skrapps's attacks", 530, 377);
				g.drawString("will do more damage!", 530, 397);
			}
		}
	}
	public void startBattle()
	{
		partnerIsSelected = false;
		selectedPartner = 0;

		Global.disableMovement = false;
		xpGainedSimon = 0;
		xpGainedWilliam = 0;
		for(int i=0; i<99;i++)
		{
			xpGainedPartner[i] = 0;
		}
		moneyGained = 0;
		if(Global.hasSimon)
		{
			objects.add(new SimonBattler(window ,256,320,this));
		}
		if(Global.hasWilliam)
		{
			objects.add(new WilliamBattler(window ,150,380,this));
		}
		if(Global.hasPartner)
		{
			if(Global.currentPartner == Global.SKRAPPS)
			{
				System.out.println("SKRAPPS!");
				objects.add(new SkrappsBattler(window ,32,370,this));
			}
			if(Global.currentPartner == Global.RAGE)
			{
				System.out.println("RAGE!");
				objects.add(new RageBattler(window ,32,260,this));
			}
		}
		victoryY = -500;
		Global.attacker = Attacker.Simon;
		lostSting = false;
		
	}
	public void addMonster(Monster monster)
	{
		objects.add(monster);
		monster.monsterID = amountOfMonsters+1;
		monsters[amountOfMonsters+1] = monster;
		amountOfMonsters++;
	}

	public void selectPartnerRender(Graphics g)
	{
		
		System.out.println("Hey there! :) :: " + selectedPartner);
		if(selectedPartner == 0)
		{
			g.setColor(Color.green.darker());
			g.fillRect(320, 260, 82, 32);
			this.animate(340, 290, BattleAnimation.arrow,2, g);
			g.setFont(Global.battleFont);
			g.setColor(Color.white);
			g.drawString("Simon", 325, 285);
		}
		if(selectedPartner == 1)
		{
			g.setColor(Color.green.darker());
			g.fillRect(160, 260, 92, 32);
			this.animate(180, 290, BattleAnimation.arrow,2, g);
			g.setFont(Global.battleFont);
			g.setColor(Color.white);
			g.drawString("William", 165, 285);
		}
		if(selectedPartner == 2 && Global.currentPartner == Global.SKRAPPS)
		{
			g.setColor(Color.green.darker());
			g.fillRect(60, 260, 102, 32);
			this.animate(80, 290, BattleAnimation.arrow,2, g);
			g.setFont(Global.battleFont);
			g.setColor(Color.white);
			g.drawString("Skrapps", 65, 285);
		}
	}
	
	public void selectPartner()
	{
		if(Global.leftPressed)
		{
			if(selectedPartner == 2)
			{
				selectedPartner = 0;
			}
			else
			{
				selectedPartner++;
			}
		}
		if(Global.rightPressed)
		{
			if(selectedPartner == 0)
			{
				selectedPartner = 2;
			}
			else
			{
				selectedPartner--;
			}
		}
		if(Global.zPressed)
		{
			partnerIsSelected = true;
		}
		if(selectedPartner == 0 && !Global.hasSimon)
		{
			selectedPartner = 1;
		}
		if(selectedPartner == 1 && !Global.hasWilliam)
		{
			selectedPartner = 2;
		}
		if(selectedPartner == 2 && !Global.hasPartner)
		{
			selectedPartner = 0;
		}
	}
}
