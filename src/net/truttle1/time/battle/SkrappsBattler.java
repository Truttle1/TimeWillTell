package net.truttle1.time.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.truttle1.time.battle.monsters.Monster;
import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class SkrappsBattler extends GameObject{

	private int selectedMonsterDistance;
	private int timedHit = 0;
	public BattleMode bm;
	private EyeCandy zButtonCandy;
	private int startX;
	private int startY;
	private boolean noMoveAttack = false;
	private int menuSelection;
	private int itemSelection;
	private int itemSelectionOffset;
	private int continueTime;
	private int attackMiniPhase = 0;
	private int currentMonster = 0;
	private int specialDir;
	private int special;
	private boolean ballVisible = false;
	private int ballX;
	private int ballY;
	private BufferedImage[] ballSprite;
	
	private int specialAttackDamage;
	private int specialAttackHiddenVariable;
	
	private int clubCharge;
	private final int STICKBALL_ATTACK = 10;
	private final int HEAL_ATTACK = 20;
	private int specialSelections;
	private ArrayList<SpecialAttack> specialAttacks;
	
	private int specialBufferTime = 15;
	
	public SkrappsBattler(Game window, int x, int y, BattleMode bm) {
		super(window);
		super.x = x;
		super.y = y;
		this.bm = bm;
		currentAnimation = BattleAnimation.skrappsIdleBattle;
		startX = super.x;
		startY = super.y;
		this.id = ObjectId.SkrappsBattler;
		this.flipped = true;
		specialAttacks = new ArrayList<SpecialAttack>();
		
		if(true)
		{
			SpecialAttack fireSpell = new SpecialAttack("Fireball",3,5,1);
			specialAttacks.add(fireSpell);
		}
	}

	@Override
	public void tick() {

		if(dead && Global.partnerHP[Global.SKRAPPS]>0)
		{
			this.canDodge = true;
			dead = false;
			currentAnimation = BattleAnimation.skrappsIdleBattle;
		}
		if(Quest.quests[Quest.PYRUZ_W]==6)
		{
			runSpecialIntro();
		}
		if(bm.levelingUp == true && bm.leveler == 2)
		{
			currentAnimation = BattleAnimation.skrappsLevelUp;
		}
		if(Global.partnerHP[Global.SKRAPPS] <= 0)
		{
			Global.partnerHP[Global.SKRAPPS] = 0;
			dead = true;
		}
		if((Global.attacker == Attacker.Simon) || (Global.attacker == Attacker.William) || currentMonster != bm.attackingMonster )
		{
			if(currentAnimation == BattleAnimation.skrappsCrouch)
			{
				currentAnimation = BattleAnimation.skrappsIdleBattle;
			}
		}
		if(dead)
		{
			currentAnimation = BattleAnimation.skrappsDead;
			if(Global.attacker == Attacker.Partner)
			{
				x = startX;
				setFrame(0,0);
				Global.attackPhase = 0;
				bm.attackSelection = null;
				Global.attacker = Attacker.Monsters;
				bm.attackingMonster = 0;
			}
		}
		if(Global.attacker == Attacker.Partner && !Global.disableMovement)
		{
			attack();
			canDodge = true;
		}
		else
		{
			if(Global.attacker == Attacker.Monsters && canDodge && Global.zPressed)
			{
				setFrame(0,0);
				canDodge = false;
				dodging = true;
				currentAnimation = BattleAnimation.skrappsCrouch;
				currentMonster = bm.attackingMonster;
			}
			if(dodging && getFrame(0)>=11 && currentMonster == bm.attackingMonster)
			{
				setFrame(0,0);
				currentAnimation = BattleAnimation.skrappsIdleBattle;
				dodging = false;
			}
			if(currentMonster != bm.attackingMonster)
			{
				canDodge = true;
			}
		}
		if(currentAnimation == BattleAnimation.skrappsFlinch && getFrame(0)>=11)
		{
			currentAnimation = BattleAnimation.skrappsIdleBattle;
		}
	}

	@Override
	public void render(Graphics g) 
	{
		if(Global.attacker == Attacker.Partner && Global.attackPhase == 0)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.blue);
			g.fillRect(x-16, y-250, 250, 160);
			if(menuSelection == 0)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-242, 234, 24);
			}
			if(menuSelection == 1)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-212, 234, 24);
			}
			if(menuSelection == 2)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-182, 234, 24);
			}
			g.setColor(Color.blue.darker().darker());
			g.drawString("Whack", x-2, y-222);
			g.drawString("Item", x-2, y-192);
			if(bm.partnerSelections[Global.SKRAPPS] >= 2)
			{
				g.drawString("Special", x-2, y-162);
			}
		}

		if(Global.attacker == Attacker.Partner && bm.attackSelection == AttackSelection.Item && Global.attackPhase == 3)
		{
			if(attackMiniPhase == 0)
			{
				/*
				g.setFont(Global.battleFont);
				g.setColor(Color.blue);
				g.fillRect(x+160, y-280, 250, 190);
				int yOff = 0;
				for(int i=0; i<Global.items.length; i++)
				{
					if(Global.items[i]>0)
					{
						if(itemSelection == i)
						{
							g.setColor(Color.white);
							g.fillRect(x+172, y-(238-yOff), 225, 22);
						}
						g.setColor(Color.blue.darker().darker());
						g.drawString(Store.itemNames[i], x+172, y-(222-yOff));
						g.drawString("x" + Integer.toString(Global.items[i]), x+340, y-(222-yOff));
						yOff += 22;
					}
				}
				g.setFont(Global.battleFont);
				g.setColor(Color.blue.darker());
				g.drawString("Press [X] to go back",x+160,y-255);*/

				drawItemMenu(Color.blue,g,itemSelection);
			}
			else
			{
				this.currentAnimation = BattleAnimation.skrappsItem;
				this.setFrame(0, 0);
				g.drawImage(Store.itemImage[itemSelection],this.x+64,this.y-64,null);
			}
		}
/*		if(Global.attacker == Attacker.Partner && Global.attackPhase == 2)
		{
			animateAtSpeed(this.x,this.y,currentAnimation,0,g,2);
		}
		else if(Global.attacker == Attacker.Simon && Global.attackPhase == 3 && currentAnimation == BattleAnimation.simonPunchAnimation)
		{
			animateAtSpeed(this.x,this.y,currentAnimation,0,g,1);
		}
		else if(Global.attacker == Attacker.Simon && Global.attackPhase == 4 && currentAnimation == BattleAnimation.simonWalkAnimation)
		{
			this.flipped = true;
			animateAtSpeed(this.x,this.y,currentAnimation,0,g,2);
		}
		else
		{
			this.flipped = false;
			animate(this.x,this.y,currentAnimation,0,g);
		}*/

		if(Global.attacker == Attacker.Partner && bm.attackSelection == AttackSelection.Special && Global.attackPhase == 3)
		{
			renderSpecialMenu(g);
		}
		try
		{
			if(getFrame(0)>=15 && Global.attackPhase == 3 && bm.attackSelection == AttackSelection.Basic)
			{
				animateAtSpeed(this.x,this.y,currentAnimation,0,g,1);
			}
			else
			{
				animate(this.x,this.y,currentAnimation,0,g);
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			setFrame(0,0);
			animate(this.x,this.y,currentAnimation,0,g);
			}
		if(special == 0 && ballVisible)
		{
			animate(ballX,ballY,BattleAnimation.ball,1,g);
		}
		
		if(special >= 2 && ballVisible)
		{
			animate(ballX,ballY,ballSprite,1,g);
		}
		
		if(special == 3)
		{
			renderFireballAction(g);
		}
	}
	
	private void renderFireballAction(Graphics g)
	{

		if(attackMiniPhase >= 4)
		{
			g.setColor(Color.blue.darker().darker());
			g.fillRect(0, 0, 1024, 100);
			g.setFont(Global.battleFont);
			g.setColor(Color.white);
			g.drawString("Mash [Z] to increase power!", 338, 32);
			g.setColor(Color.gray);
			g.drawString("Power: ", 338+2, 64+2);
			g.setColor(Color.yellow);
			g.drawString("Power: ", 338, 64);
			int offsetX = 0;
			int offsetY = 0;
			if(Global.zDown)
			{
				g.setFont(Global.winFont2);
				offsetX = -12;
				offsetY = 12;
			}
			else
			{
				g.setFont(Global.battleFont);
			}
			g.setColor(Color.gray);
			g.drawString(Integer.toString(Global.partnerPow[Global.SKRAPPS] + specialAttackDamage), 438+offsetX+2, 64+offsetY+2);
			g.setColor(Color.yellow);
			g.drawString(Integer.toString(Global.partnerPow[Global.SKRAPPS] + specialAttackDamage), 438+offsetX, 64+offsetY);
			
		}
	}
	
	private void attack()
	{
		if(Global.attackPhase == 0 && Global.attacker == Attacker.Partner)
		{
			if(Global.zPressed)
			{
				if(menuSelection == 0)
				{

					bm.attackSelection = AttackSelection.Basic;
					Global.attackPhase++;
					noMoveAttack = false;
				}
				if(menuSelection == 1)
				{
					setFrame(0,0);
					bm.attackSelection = AttackSelection.Item;
					Global.attackPhase = 3;
					Global.zPressed = false;
					attackMiniPhase = 0;
					noMoveAttack = true;
				}
				if(menuSelection == 2)
				{
					setFrame(0,0);
					bm.attackSelection = AttackSelection.Special;
					Global.attackPhase = 3;
					Global.zPressed = false;
					attackMiniPhase = 0;
					noMoveAttack = true;
				}
				
			}
			if(menuSelection>bm.partnerSelections[Global.SKRAPPS])
			{
				menuSelection = 0;
			}
			if(menuSelection<0)
			{
				menuSelection = bm.partnerSelections[Global.SKRAPPS];
			}
			if(Global.downPressed)
			{
				menuSelection++;
			}
			if(Global.upPressed)
			{
				menuSelection--;
			}
			currentAnimation = BattleAnimation.skrappsIdleBattle;
		}
		if(Global.attackPhase == 2)
		{
			if(!noMoveAttack)
			{
				this.flipped = true;
				selectedMonsterDistance = getMonsterDistance();
				super.x+=10;
				currentAnimation = BattleAnimation.skrappsRunStick;
				if(window.battleMode.selectedMonster != null)
				{
					if(x >= (window.battleMode.selectedMonster.x-selectedMonsterDistance)+120)
					{
						setFrame(0,0);
						Global.attackPhase++;
						currentAnimation = BattleAnimation.skrappsIdleBattle;
					}
				}
			}
			else
			{
				Global.attackPhase++;
			}
		}
		else if(Global.attackPhase == 3)
		{
			if(bm.attackSelection == AttackSelection.Basic)
			{
				basicAttack();
			}
			if(bm.attackSelection == AttackSelection.Item)
			{
				itemAttack();
			}

			if(bm.attackSelection == AttackSelection.Special)
			{
				specialAttack();
			}
		}
		else if(Global.attackPhase == 4)
		{
			super.x-=10;
			this.flipped = false;
			if(x <= startX)
			{
				this.flipped = true;
				x = startX;
				setFrame(0,0);
				currentAnimation = BattleAnimation.skrappsIdleBattle;
				Global.attackPhase = 0;
				bm.attackSelection = null;
				Global.attacker = Attacker.Monsters;
				bm.attackingMonster = 0;
			}
		}
	}
	private void basicAttack()
	{
		if(currentAnimation != BattleAnimation.skrappsFlinch)
		{
			currentAnimation = BattleAnimation.skrappsWhack;
		}
		if(getFrame(0) == 4)
		{
			AudioHandler.playSound(AudioHandler.seWoosh);
		}
		if(getFrame(0)==15 && !bm.eyeCandy.contains(zButtonCandy))
		{
			zButtonCandy = new EyeCandy(window, super.x+72, super.y-72, BattleAnimation.zIcon, bm);
			bm.eyeCandy.add(zButtonCandy);
		}
		if(this.getFrame(0)>=23)
		{
			bm.eyeCandy.remove(zButtonCandy);
		}
		if(Global.zPressed && timedHit == 0)
		{
			if(getFrame(0)>=15 && getFrame(0)<=23)
			{
				setFrame(0,23);
				timedHit++;
			}
			else if(getFrame(0)<15)
			{
				setFrame(0,23);
			}
		}
		if(getFrame(0) >= 33 && window.battleMode.selectedMonster.hp<=0)
		{
			bm.eyeCandy.remove(zButtonCandy);
			setFrame(0,47);
		}
		if(getFrame(0) >= 33 && timedHit == 0)
		{
			bm.eyeCandy.remove(zButtonCandy);
			setFrame(0,47);
		}
		if(getFrame(0) == 30)
		{
			AudioHandler.playSound(AudioHandler.seCrack);
			bm.eyeCandy.remove(zButtonCandy);
			int toDeal = Global.partnerPow[Global.SKRAPPS]-window.battleMode.selectedMonster.defense;
			if(toDeal<0)
			{
				toDeal = 0;
			}
			EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+48, BattleAnimation.hitAny, bm, true, toDeal);
			atk.setRepeating(true);
			bm.eyeCandy.add(atk);
			
			window.battleMode.selectedMonster.setFrame(0,0);
			window.battleMode.selectedMonster.hp -= toDeal;
			window.battleMode.selectedMonster.damageDealt+=toDeal;
			window.battleMode.selectedMonster.partnerDealt[Global.SKRAPPS]+=toDeal;
			window.battleMode.selectedMonster.flinch();
			if(timedHit != 0)
			{
				Global.flinchAmount = 5;
			}
			else
			{
				Global.flinchAmount = 16;
			}
			
		}
		if(getFrame(0) == 40)
		{
			int toDeal = (Global.partnerPow[Global.SKRAPPS]/2)-window.battleMode.selectedMonster.defense;
			if(toDeal<0)
			{
				toDeal = 0;
			}
			AudioHandler.playSound(AudioHandler.seCrack);
			EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+48, BattleAnimation.hitAny, bm, true, toDeal);
			atk.setRepeating(true);
			bm.eyeCandy.add(atk);
			window.battleMode.selectedMonster.setFrame(0,0);
			window.battleMode.selectedMonster.hp -= toDeal;
			window.battleMode.selectedMonster.damageDealt+=toDeal;
			window.battleMode.selectedMonster.partnerDealt[Global.SKRAPPS]+=toDeal;
			window.battleMode.selectedMonster.flinch();
			if(timedHit != 0)
			{
				Global.flinchAmount = 5;
			}
			else
			{
				Global.flinchAmount = 16;
			}
		}
		if(getFrame(0)>=47)
		{
			timedHit = 0;
			setFrame(0,0);
			Global.attackPhase++;
			currentAnimation = BattleAnimation.skrappsRunStick;
		}
		if(getFrame(0)>=11 && currentAnimation == BattleAnimation.skrappsFlinch)
		{
			timedHit = 0;
			setFrame(0,0);
			Global.attackPhase++;
			currentAnimation = BattleAnimation.skrappsRunStick;
		}
	}
	private void itemAttack()
	{
		int isDir = 0;
		System.out.println(itemSelection);
		if(attackMiniPhase == 0)
		{
			if(Global.xPressed)
			{
				Global.attackPhase = 0;
			}
			if(Global.downPressed)
			{
				itemSelection++;
				isDir = 0;
			}
			if(Global.upPressed)
			{
				itemSelection--;
				isDir = 1;
			}
			if(itemSelection<0)
			{
				itemSelection = 98;
			}
			if(itemSelection>98)
			{
				itemSelection = 0;
			}
			while(itemSelection >= 0 && itemSelection <= 98 && Global.items[itemSelection] == 0)
			{
				if(isDir == 0)
				{
					itemSelection++;
				}
				else
				{
					itemSelection--;
				}
			}
			if(Global.zPressed && Global.items[itemSelection]>0)
			{
				attackMiniPhase++;
			}
		}
		if(attackMiniPhase == 1)
		{
			Store.itemUse(this, itemSelection);
			Global.items[itemSelection]--;
			continueTime = 30;
			attackMiniPhase++;
		}
		if(attackMiniPhase == 2)
		{
			continueTime--;
			if(continueTime<=0)
			{
				setFrame(0,0);
				this.flipped = true;
				Global.attackPhase++;
				x = startX;
				currentAnimation = BattleAnimation.skrappsIdleBattle;
			}
		}
		
	}
	private void runSpecialIntro()
	{
		Global.attackPhase = 0;
		if(Global.talking == 0)
		{
			Global.disableMovement = true;
			Global.talking = 1;
			currentAnimation = BattleAnimation.skrappsTalk;
			SpeechBubble.talk("Hey William, you ever heard of Special Attacks?",Global.skrappsFont);
		}
		if(Global.talking == 3)
		{
			currentAnimation = BattleAnimation.skrappsIdle2;
		}
		if(Global.talking == 4)
		{
			Global.disableMovement = true;
			Global.talking = 5;
			currentAnimation = BattleAnimation.skrappsTalk;
			SpeechBubble.talk("Well, you see those cyan bars beneath our HP meters? Those are our SP meters!/Special Attacks are attacks unique to certain people. When they are used, they drain SP/from the SP meter! Don't worry, though, because SP can be gianed by/drinking Orange Juice, or, for carnivores like me, eating SP sticks!",Global.skrappsFont);
		}
		if(Global.talking == 7)
		{
			currentAnimation = BattleAnimation.skrappsIdle2;
		}
		if(Global.talking == 8)
		{
			Global.disableMovement = true;
			Global.talking = 9;
			currentAnimation = BattleAnimation.skrappsTalk;
			SpeechBubble.talk("I think you are able to throw darts at your opponents! It costs 2 SP, and/it would break through your opponent's defenses!/I am able to attack enemies using a stickball attack, which will deal three times/more damage than a regular attack!",Global.skrappsFont);
		}
		if(Global.talking == 11)
		{
			currentAnimation = BattleAnimation.skrappsIdle2;
		}
		if(Global.talking == 12)
		{
			Global.disableMovement = true;
			Global.talking = 13;
			currentAnimation = BattleAnimation.skrappsTalk;
			SpeechBubble.talk("Oh, you find someone willing to teach it to you. Be warned though, Special Attacks may/be expensive, especially the good ones that everybody wants!/Anyway William, will I be attacking this guy after you or...",Global.skrappsFont);
		}
		if(Global.talking == 15)
		{
			currentAnimation = BattleAnimation.skrappsIdle2;
		}

		if(Global.talking == 16)
		{
			Global.disableMovement = true;
			Global.talking = 17;
			currentAnimation = BattleAnimation.skrappsTalkStick;
			SpeechBubble.talk("Yep! NOW LET'S SHOW THIS MONSTER WHO'S BOSS! BWAHAHAHAHAAAAA!",Global.skrappsFont);
		}
		if(Global.talking == 18)
		{
			Global.talking = 0;
			Global.attacker = Attacker.William;
			Global.attackPhase = 0;
			Global.disableMovement = false;
			currentAnimation = BattleAnimation.skrappsIdleBattle;
			Quest.quests[Quest.PYRUZ_W]++;
		}
	}
	private void specialAttack()
	{
		if(attackMiniPhase == 0)
		{
			specialMenu();
		}
		if(attackMiniPhase == 1)
		{
			if(Global.xPressed)
			{
				System.out.println("aaa");
				Global.xPressed = false;
				attackMiniPhase--;
				bm.specialType = 0;
			}
			System.out.println(special);
			if(special == 1 && bm.partnerIsSelected)
			{
				Global.partnerSP[Global.SKRAPPS] -= 3;
				currentAnimation = BattleAnimation.skrappsCast;
				setFrame(0,0);
				bm.specialType = 0;
				attackMiniPhase = 10;
			}
			if(special == 0 && bm.monsterIsSelected)
			{
				Global.partnerSP[Global.SKRAPPS] -= 2;
				currentAnimation = BattleAnimation.skrappsBall1;
				setFrame(0,0);
				bm.specialType = 0;
				attackMiniPhase = 10;
			}
			if(special >= 2)
			{
				if((bm.specialType == 1 && bm.monsterIsSelected) || (bm.specialType == 2 && bm.monsterIsSelected))
				{
					System.out.println("SPECIAL: " + special);
					Global.partnerSP[Global.SKRAPPS] -= specialAttacks.get(special-2).getCost();
					special = specialAttacks.get(special-2).getId();
					bm.specialType = 0;
					attackMiniPhase = 2;
				}
			}
		}
		if(attackMiniPhase >= 2 && special >= 2)
		{
			if(special == 3)
			{
				fireballAttack();
			}
		}
		else if(special <= 1)
		{
			if(attackMiniPhase == 10)
			{
				if(special == 0)
				{
					if(getFrame(0) == 23)
					{
						currentAnimation = BattleAnimation.skrappsBallThrow;
						attackMiniPhase++;
						setFrame(0,0);
					}
				}
				if(special == 1)
				{
					if(getFrame(0) == 21)
					{
						currentAnimation = BattleAnimation.skrappsCast;
						attackMiniPhase++;
					}
				}
			}
			if(attackMiniPhase == 11)
			{
				if(special == 0)
				{
					if(getFrame(0) == 14)
					{
						currentAnimation = BattleAnimation.skrappsHit0;
						attackMiniPhase++;
						setFrame(0,0);
					}
				}
				if(special == 1)
				{
					if(bm.selectedPartner == 0)
					{
						AudioHandler.playSound(AudioHandler.seWoosh);
						Global.simonHP += 5;
						EyeCandy atk = new EyeCandy(bm.window, x+300, y-100, BattleAnimation.heart, bm,true,5,true,1,0,0);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
					}
					if(bm.selectedPartner == 1)
					{
						AudioHandler.playSound(AudioHandler.seWoosh);
						Global.williamHP += 5;
						EyeCandy atk = new EyeCandy(bm.window, x+200, y-50, BattleAnimation.heart, bm,true,5,true,1,0,0);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
					}
					if(bm.selectedPartner == 2)
					{
						AudioHandler.playSound(AudioHandler.seWoosh);
						Global.partnerHP[Global.SKRAPPS] += 5;
						EyeCandy atk = new EyeCandy(bm.window, x+50, y-50, BattleAnimation.heart, bm,true,5,true,1,0,0);
						atk.setRepeating(true);
						bm.eyeCandy.add(atk);
					}
					attackMiniPhase++;
				}
			}
			if(attackMiniPhase == 12)
			{
				if(special == 0)
				{
					if(getFrame(0) == 5)
					{
						currentAnimation = BattleAnimation.skrappsHit1;
						attackMiniPhase++;
						setFrame(0,0);
					}
				}
				if(special == 1)
				{
	
					if(getFrame(0)>=47)
					{
						this.setFrame(0, 0);
						Global.attackPhase++;
						attackMiniPhase = 0;
						special = 0;
						bm.partnerIsSelected = false;
						bm.selectedPartner = 0;
					}
				}
			}
			if(attackMiniPhase == 13)
			{
				if(special == 0)
				{
					if(getFrame(0) == 5)
					{
						AudioHandler.playSound(AudioHandler.seCrack);
						currentAnimation = BattleAnimation.skrappsIdleBattle;
						ballX = this.x+140;
						ballY = this.y+0;
						ballVisible = true;
					}
					if(ballVisible)
					{
						ballX+=20;
						ballY-=2;
					}
					if(getFrame(0) == 23)
					{
						setFrame(0,0);
						attackMiniPhase++;
					}
				}
			}
			if(attackMiniPhase == 14)
			{
				if(special == 0)
				{
					ballX+=20;
					ballY-=2;
					if(ballX >= bm.selectedMonster.x)
					{
						int toDeal = (int) (Global.partnerPow[Global.SKRAPPS]*3) - window.battleMode.selectedMonster.defense;
						if(toDeal<0)
						{
							toDeal = 0;
						}
						window.battleMode.selectedMonster.takeDamage(toDeal);
						window.battleMode.selectedMonster.partnerDealt[Global.SKRAPPS]+=toDeal;
						
						AudioHandler.playSound(AudioHandler.seDodge);
						ballVisible = false;
						attackMiniPhase++;
						if(timedHit != 0)
						{
							Global.flinchAmount = 5;
						}
						else
						{
							Global.flinchAmount = 16;
						}
					}
				}
			}
			if(attackMiniPhase == 15)
			{
				if(this.specialBufferTime > 0)
				{
					this.specialBufferTime--;
				}
				else
				{
					this.specialBufferTime = 15;
					Global.attackPhase++;
					attackMiniPhase = 0;
					special = 0;
				}
			}
		}
	}
	
	private void fireballAttack()
	{
		System.out.println("PHASE " + attackMiniPhase);
		if(attackMiniPhase == 2)
		{
			setFrame(0, 0);
			setAnimation(BattleAnimation.skrappsCast);
			attackMiniPhase++;
		}
		
		if(attackMiniPhase == 3)
		{
			specialAttackHiddenVariable = 0;
			if(getFrame(0) == 23)
			{
				AudioHandler.playSound(AudioHandler.seFire);
				attackMiniPhase++;
				ballX = this.x+146;
				ballY = this.y+64;
				ballVisible = true;
				ballSprite = BattleAnimation.fire;
			}
		}
		
		if(attackMiniPhase == 4)
		{
			ballX+=10;
			ballY-=1;
			if(ballX >= bm.selectedMonster.x)
			{
				attackMiniPhase++;
			}
			if(this.currentAnimation == BattleAnimation.skrappsCast && getFrame(0) >= 47)
			{
				setAnimation(BattleAnimation.skrappsIdleBattle);
			}
			
			if(Global.zPressed)
			{
				specialAttackHiddenVariable++;
				
			}
			specialAttackDamage = specialAttackHiddenVariable;
		}
		
		if(attackMiniPhase == 5)
		{

			int toDeal = (int) (Global.partnerPow[Global.SKRAPPS]) + specialAttackDamage;
			if(toDeal<0)
			{
				toDeal = 0;
			}
			window.battleMode.selectedMonster.takeDamage(toDeal);
			window.battleMode.selectedMonster.applyStatusEffect(Monster.STATUS_FIRE, toDeal/4);
			window.battleMode.selectedMonster.partnerDealt[Global.SKRAPPS]+=toDeal;
			AudioHandler.playSound(AudioHandler.seDodge);
			ballVisible = false;
			this.attackMiniPhase++;
		}
		if(attackMiniPhase == 6)
		{
			if(this.specialBufferTime > 0)
			{
				this.specialBufferTime--;
			}
			else
			{
				this.specialBufferTime = 15;
				Global.attackPhase++;
				attackMiniPhase = 0;
				special = 0;
			}
		}
	}
	
	private void specialMenu()
	{
		if(Global.zPressed && special == 0 && Global.partnerSP[Global.SKRAPPS] >= 2)
		{
			System.out.println("hey there!");
			attackMiniPhase++;
			bm.specialType = 1;
			bm.monsterIsSelected = false;
			Global.zPressed = false;
		}
		if(Global.zPressed && special == 1 && Global.partnerSP[Global.SKRAPPS] >= 3)
		{
			System.out.println("hey there!");
			attackMiniPhase++;
			bm.specialType = 2;
			Global.zPressed = false;
		}
		if(Global.zPressed && special > 1)
		{
			bm.monsterIsSelected = false;
			if(Global.partnerSP[Global.SKRAPPS] >= specialAttacks.get(special-2).getCost())
			{
				System.out.println("Litty!");
				attackMiniPhase++;
				bm.specialType = specialAttacks.get(special-2).getType();
			}
			Global.zPressed = false;
		}
		if(Global.xPressed)
		{
			Global.attackPhase = 1;
		}
		if(Quest.quests[Quest.PYRUZ_S]>=1)
		{
			specialSelections = 1;
		}
		else if(Quest.quests[Quest.PYRUZ_W]>=7)
		{
			specialSelections = 0;
		}
		specialSelections += specialAttacks.size();
		if(Global.downPressed)
		{
			specialDir = 0;
			if(special<specialSelections)
			{
				special++;
			}
			else
			{
				special = 0;
			}
		}
		if(Global.upPressed)
		{
			specialDir = 1;
			if(special>0)
			{
				special--;
			}
			else
			{
				special = specialSelections;
			}
		}
		if(special<0)
		{
			special = specialSelections;
			specialDir = 1;
		}
		if(special>specialSelections)
		{
			special = 0;
			specialDir = 0;
		}
		//TEST BLOCK FOR SKIPPING OVER LOCKED SPECIAL ATTACKS
		while(special == 1 && Quest.quests[Quest.PYRUZ_W]<9)
		{
			if(specialDir == 0)
			{
				special++;
			}
			if(specialDir == 1)
			{
				special--;
			}

			if(special<0)
			{
				special = specialSelections;
				specialDir = 1;
			}
			if(special>specialSelections)
			{
				special = 0;
				specialDir = 0;
			}
		}
		
	}
	
	private int getSpecial(int id)
	{
		for(int i = 0; i < specialAttacks.size(); i++)
		{
			if(specialAttacks.get(i).getId() == id)
			{
				return i;
			}
		}
		return -1;
	}
	
	private void renderSpecialMenu(Graphics g)
	{
		if(attackMiniPhase == 0)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.blue);
			g.fillRect(x+64, y-300, 250, 250);
			int yOff = 0;
			g.setColor(Color.white);
			g.fillRect(x+70, (y-290)+25*special, 240, 24);
			if(Quest.quests[Quest.PYRUZ_W]>=7)
			{
				if(Global.partnerSP[Global.SKRAPPS] >= 2)
				{
					g.setColor(Color.blue.darker().darker());
				}
				else
				{
					g.setColor(Color.red.darker().darker());
				}
				g.drawString("Stickball", x+72, y-270);
				g.drawString("2 SP", x+250, y-270);
			}
			if(Quest.quests[Quest.PYRUZ_S]>=1)
			{
				if(Global.partnerSP[Global.SKRAPPS] >= 3)
				{
					g.setColor(Color.blue.darker().darker());
				}
				else
				{
					g.setColor(Color.red.darker().darker());
				}
				g.drawString("Heal", x+72, y-245);
				g.drawString("3 SP", x+250, y-245);
			}
			
			for(int i = 0; i < specialAttacks.size(); i++)
			{

				if(Global.partnerSP[Global.SKRAPPS] >= specialAttacks.get(i).getCost())
				{
					g.setColor(Color.blue.darker().darker());
				}
				else
				{
					g.setColor(Color.red.darker().darker());
				}
				g.drawString(specialAttacks.get(i).getName(), x+72, y-245+((i+1)*25));
				g.drawString(specialAttacks.get(i).getCost() + " SP", x+250, y-245+((i+1)*25));
			}
			
		}
	}
	
	@Override
	public int getPartnerId()
	{
		return Global.SKRAPPS;
	}
}
