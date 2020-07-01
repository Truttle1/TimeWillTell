package net.truttle1.time.battle;

import java.awt.Color;
import java.awt.Graphics;

import net.truttle1.time.effects.SpeechBubble;
import net.truttle1.time.effects.Store;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class WilliamBattler extends GameObject{

	private int selectedMonsterDistance;
	public BattleMode bm;
	private EyeCandy zButtonCandy;
	private int startX;
	private int startY;
	private boolean noMoveAttack = false;
	private int menuSelection;
	private int attackMiniPhase = 0;
	private boolean resetFrame = false;
	int jumpVelocity = 0;
	private int timer = 0;
	private int itemSelection;
	private int itemSelectionOffset;
	private int continueTime;
	private boolean jumpSound = false;
	private SpecialType specialAttack;
	private int specialSelections;
	private int special;
	private int specialDir;
	private int attackMiniMiniPhase;
	private int dartX[] = new int[9];
	private boolean dartVisible[] = new boolean[9];
	private int arrowX;
	private int attackStrength;
	private boolean arrowXRight = true;
	
	public WilliamBattler(Game window, int x, int y, BattleMode bm) {
		super(window);
		super.x = x;
		super.y = y;
		this.bm = bm;
		this.flipped = true;
		currentAnimation = BattleAnimation.williamIdleAnimation;
		startX = super.x;
		startY = super.y;
		this.id = ObjectId.WilliamBattler;
	}

	@Override
	public void tick() {


		if(Quest.quests[Quest.PYRUZ_W]==6 && Global.hasPartner)
		{
			runSpecialIntro();
		}
		if(Quest.quests[Quest.LOMOBANK]<14)
		{
			bm.williamSelections = 1;
		}
		else
		{
			if(Quest.quests[Quest.PYRUZ_W]>=7)
			{
				bm.williamSelections = 3;
			}
			else if(Quest.quests[Quest.LOMOBANK]>=13)
			{
				bm.williamSelections = 2;
			}
		}
		if(Quest.quests[Quest.WILLIAMFIRSTBATTLE] == 0 && Quest.quests[Quest.LOMOBANK]>=14)
		{
			Global.attackPhase = 0;
			if(Global.talking == 0)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 1;
				SpeechBubble.talk("UGGGGGGGGHHH!!! I hate reading...but Creaturey told me that/I carried around this book all the time...and that/probably means that it contains some fighting tips.../I mean, why else would I have it...",Global.willFont);
				this.currentAnimation = BattleAnimation.williamTalk;
				Global.informing = true;
			}
			if(Global.talking == 2 && Global.talkingTo == this)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 3;
				SpeechBubble.talk("Oh hey! It even has a chapter about me! Let's see here...Chapter 2 - William Grigliosaur:/William (That's me!) attacks his opponents by jumping on them. If William presses [Z]/at the same time he lands on the enemy, he will double-jump and do DOUBLE DAMAGE!!!/Man, I'm good! Why have I never tried this before!?",Global.willFont);
				this.currentAnimation = BattleAnimation.williamRead;
				Global.informing = true;
			}
			if(Global.talking == 4 && Global.talkingTo == this)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 5;
				SpeechBubble.talk("There's also a section on dodging...I'm gonna give it a read, it sounds helpful. William/(The most epic dino on Earth) is able to dodge by counterattacking! Press [X] about a/second before the enemy hits you to do a counterattack and deal damage to THEM!/Be careful though! If you do it too late, you will take DOUBLE THE DAMAGE!!! YIKES!/",Global.willFont);
				this.currentAnimation = BattleAnimation.williamRead;
				Global.informing = true;
			}
			if(Global.talking == 6 && Global.talkingTo == this)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 7;
				SpeechBubble.talk("Here's a tip: For William, it's better to counterattack EARLY than late, so/press [X] right when you see the [X] icon!",Global.willFont);
				this.currentAnimation = BattleAnimation.williamRead;
				Global.informing = true;
			}
			if(Global.talking == 8 && Global.talkingTo == this)
			{
				Global.talkingTo = this;
				Global.disableMovement = true;
				Global.talking = 9;
				SpeechBubble.talk("Also, feel free to check this book for information about your enemies./Simply select the 'Inform' Option when it is your turn in battle!/Of course, this will take up your turn, so don't use it if you/plan on making a big attack.",Global.willFont);
				this.currentAnimation = BattleAnimation.williamRead;
				Global.informing = true;
			}
			if(Global.talking == 10 && Global.talkingTo == this)
			{
				Quest.quests[Quest.WILLIAMFIRSTBATTLE] = 1;
				Global.talkingTo = null;
				Global.disableMovement = false;
				Global.talking = 0;
				this.currentAnimation = BattleAnimation.williamIdleAnimation;
				Global.attackPhase = 0;
				Global.informing = false;
			}
		}
		if(bm.levelingUp == true && bm.leveler == 1)
		{
			currentAnimation = BattleAnimation.williamLevelUp;
		}
		if(Global.williamHP <= 0)
		{
			Global.williamHP = 0;
			dead = true;
		}
		if(dead)
		{
			currentAnimation = BattleAnimation.williamDeadAnimation;
			if(Global.attacker == Attacker.William)
			{
				x = startX;
				setFrame(0,0);
				Global.attackPhase = 0;
				bm.attackSelection = null;
				if(Global.hasPartner)
				{
					Global.attacker = Attacker.Partner;
				}
				else
				{
					Global.attacker = Attacker.Monsters;
					bm.attackingMonster = 0;
				}
			}
		}
		if(dead && Global.williamHP>0)
		{
			this.canDodge = true;
			dead = false;
			currentAnimation = BattleAnimation.williamIdleAnimation;
		}
		if(Global.attacker == Attacker.William)
		{
			attack();
			canDodge = true;
		}
		else
		{
			if(canDodge && Global.xPressed && Global.attacker == Attacker.Monsters)
			{
				setFrame(0,0);
				dodging = true;
				canDodge = false;
				currentAnimation = BattleAnimation.williamCounterAnimation;
			}
		}

		if(currentAnimation == BattleAnimation.williamHitAnimation && getFrame(0)>=15)
		{
			currentAnimation = BattleAnimation.williamIdleAnimation;
		}
		if(currentAnimation == BattleAnimation.williamCounterAnimation && getFrame(0)>=29)
		{
			currentAnimation = BattleAnimation.williamIdleAnimation;
			dodging = false;
		}
		if(currentAnimation == BattleAnimation.williamFailAnimation && getFrame(0)>=35)
		{
			currentAnimation = BattleAnimation.williamIdleAnimation;
			dodging = false;
		}
	}

	@Override
	public void render(Graphics g) 
	{
		if(Global.attacker == Attacker.William && Global.attackPhase == 0 && !Global.disableMovement)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.green);
			g.fillRect(x-16, y-300, 250, 160);
			if(menuSelection == 0)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-292, 234, 24);
			}
			if(menuSelection == 1)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-268, 234, 24);
			}
			if(menuSelection == 2)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-248, 234, 24);
			}
			if(menuSelection == 3)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-224, 234, 24);
			}
			g.setColor(Color.green.darker().darker());
			g.drawString("Jump", x-2, y-272);
			g.drawString("Item", x-2, y-250);
			if(bm.williamSelections>=2)
			{
				g.drawString("Inform", x-2, y-228);
			}
			if(bm.williamSelections>=3)
			{
				g.drawString("Special", x-2, y-206);
			}
		}
		if(getFrame(0)>currentAnimation[2].getWidth()-1)
		{
			setFrame(0,0);
		}
		if(Global.attacker == Attacker.William && bm.attackSelection == AttackSelection.Item && Global.attackPhase == 3)
		{
			if(attackMiniPhase == 0)
			{
				drawItemMenu(Color.green, g, itemSelection);
			}
			else
			{
				this.setFrame(0, 0);
				this.currentAnimation = BattleAnimation.williamItem;
				g.drawImage(Store.itemImage[itemSelection],this.x+32,this.y-64,null);
			}
		}
		if(Global.attacker == Attacker.William && bm.attackSelection == AttackSelection.Special && Global.attackPhase == 3)
		{
			renderSpecialMenu(g);
		}
		animate(this.x,this.y,currentAnimation,0,g);
		
		if(attackMiniPhase >= 10 && bm.attackSelection == AttackSelection.Special && special == 0)
		{
			drawDarts(g);
			g.setColor(Color.green.darker().darker());
			g.fillRect(0, 0, 1024, 100);
			g.setFont(Global.battleFont);
			g.setColor(Color.white);
			if(attackMiniPhase == 10)
			{
				g.drawString("Press [Z] when the arrow is in the green region!", 210, 20);	
			}
			else
			{
				g.drawString("Attack Strength: " + attackStrength, 210, 20);	
			}
			this.animate(375,32,BattleAnimation.specialBar,1,g);
			this.setFrame(2, 0);
			this.animate(375+arrowX, 36, BattleAnimation.arrowUpwards, 2, g);
		}
	}
	private void renderSpecialMenu(Graphics g)
	{
		if(attackMiniPhase == 0)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.green);
			g.fillRect(x+64, y-300, 250, 250);
			int yOff = 0;
			g.setColor(Color.white);
			g.fillRect(x+70, (y-290)+25*special, 240, 24);
			if(Quest.quests[Quest.PYRUZ_W]>=7)
			{
				if(Global.williamSP >= 2)
				{
					g.setColor(Color.green.darker().darker());
				}
				else
				{
					g.setColor(Color.red.darker().darker());
				}
				g.drawString("Dart Throw", x+72, y-270);
				g.drawString("2 SP", x+250, y-270);
			}
			
			
		}
	}
	private void attack()
	{
		if(Global.attackPhase == 0 && Global.attacker == Attacker.William)
		{
			if(Global.zPressed)
			{
				if(menuSelection == 0)
				{
					resetFrame = true;
					bm.attackSelection = AttackSelection.Basic;
					Global.attackPhase++;
					noMoveAttack = false;
					attackMiniPhase = 0;
					jumpVelocity = 20;
				}
				if(menuSelection == 1)
				{
					bm.attackSelection = AttackSelection.Item;
					Global.attackPhase = 3;
					Global.zPressed = false;
					attackMiniPhase = 0;
					noMoveAttack = true;
				}
				if(menuSelection == 2)
				{
					bm.attackSelection = AttackSelection.Inform;
					Global.attackPhase++;
					Global.zPressed = false;
					attackMiniPhase = 0;
					noMoveAttack = true;
				}
				if(menuSelection == 3)
				{
					bm.attackSelection = AttackSelection.Special;
					Global.attackPhase = 3;
					Global.zPressed = false;
					attackMiniPhase = 0;
					noMoveAttack = true;
					bm.specialType = 0;
					bm.monsterIsSelected = false;
				}
			}
			if(menuSelection>bm.williamSelections)
			{
				menuSelection = 0;
			}
			if(menuSelection<0)
			{
				menuSelection = bm.williamSelections;
			}
			if(Global.downPressed)
			{
				menuSelection++;
			}
			if(Global.upPressed)
			{
				menuSelection--;
			}
		}
		if(Global.attackPhase == 2)
		{
			itemSelection = 0;
			if(!noMoveAttack)
			{
				if(!resetFrame)
				{
					setFrame(0,0);
					resetFrame = true;
				}
				selectedMonsterDistance = getMonsterDistance();
				currentAnimation = BattleAnimation.williamWalkAnimation;
				super.x+=12;
				if(window.battleMode.selectedMonster != null)
				{
					if(x >= window.battleMode.selectedMonster.x-selectedMonsterDistance)
					{
						setFrame(0,0);
						Global.attackPhase++;
						if(window.battleMode.selectedMonster.heightMod == 120)
						{
							this.jumpVelocity = 30;
						}
					}
				}
			}
			else
			{
				setFrame(0,0);
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
			if(bm.attackSelection == AttackSelection.Inform)
			{
				informAttack();
			}
			if(bm.attackSelection == AttackSelection.Special)
			{
				specialAttack();
			}
		}
		else if(Global.attackPhase == 4)
		{
			x = startX;
			setFrame(0,0);
			Global.attackPhase = 0;
			bm.attackSelection = null;
			jumpSound = false;
			if(Global.hasPartner)
			{
				Global.attacker = Attacker.Partner;
			}
			else
			{
				Global.attacker = Attacker.Monsters;
				bm.attackingMonster = 0;
			}
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
			if(Global.zPressed)
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
				currentAnimation = BattleAnimation.williamIdleAnimation;
			}
		}
		
	}
	private void informAttack()
	{
		String str = window.battleMode.selectedMonster.information;
		if(Global.talking == 0)
		{
			Global.talkingTo = this;
			Global.disableMovement = true;
			Global.talking = 1;
			SpeechBubble.talk(str,Global.willFont);
			this.currentAnimation = BattleAnimation.williamRead;
			Global.informing = true;
		}
		if(Global.talking == 2 && Global.talkingTo == this)
		{

			Global.talkingTo = null;
			Global.disableMovement = false;
			Global.talking = 0;
			this.currentAnimation = BattleAnimation.williamIdleAnimation;
			Global.attackPhase = 4;
			Global.informing = false;
		}
	}
	private void basicAttack()
	{
		if(attackMiniPhase==0)
		{
				if(!jumpSound)
				{
					jumpSound = true;
					AudioHandler.playSound(AudioHandler.seJump);
				}
				if(timedHit != 2)
				{
					x+=8;
				}
				y-=jumpVelocity*2;
				jumpVelocity-=1.6;

				if(jumpVelocity==0)
				{
					currentAnimation = BattleAnimation.williamLandAnimation;
				}

				if(Global.zPressed && timedHit == 0 && y>=window.battleMode.selectedMonster.y-(window.battleMode.selectedMonster.heightMod+32) && y<=window.battleMode.selectedMonster.y-(window.battleMode.selectedMonster.heightMod-48))
				{
					timedHit = 1;
				}
				if(timedHit<=1 && jumpVelocity<0 && y>=window.battleMode.selectedMonster.y-window.battleMode.selectedMonster.heightMod)
				{
					int toDeal = Global.williamPow-window.battleMode.selectedMonster.defense;
					if(toDeal<0)
					{
						toDeal = 0;
					}
					window.battleMode.selectedMonster.flinch();
					window.battleMode.selectedMonster.hp-=toDeal;
					window.battleMode.selectedMonster.damageDealt+=toDeal;
					window.battleMode.selectedMonster.williamDealt+=toDeal;
					EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+64, BattleAnimation.hitAny, bm,true,toDeal);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					if(timedHit == 1 && window.battleMode.selectedMonster.hp>0)
					{
						AudioHandler.playSound(AudioHandler.seHit);
						jumpVelocity = 18;
						timedHit = 2;
						currentAnimation = BattleAnimation.williamFlipAnimation;
					}
					else
					{
						AudioHandler.playSound(AudioHandler.seHit2);
						timedHit = 0;
						jumpVelocity = 12;
						attackMiniPhase++;
					}
				}
				if(timedHit == 2 && jumpVelocity<0 && y>=window.battleMode.selectedMonster.y-window.battleMode.selectedMonster.heightMod-2)
				{
					int toDeal = Global.williamPow-window.battleMode.selectedMonster.defense;
					if(toDeal<0)
					{
						toDeal = 0;
					}
					AudioHandler.playSound(AudioHandler.seHit2);
					jumpVelocity = 12;
					attackMiniPhase++;
					window.battleMode.selectedMonster.hp-=toDeal;
					window.battleMode.selectedMonster.damageDealt+=toDeal;
					window.battleMode.selectedMonster.williamDealt+=toDeal;
					EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+64, BattleAnimation.hitAny, bm,true,toDeal);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
					window.battleMode.selectedMonster.flinch();
					timedHit = 0;
				}
		}
		if(attackMiniPhase == 1)
		{
			x-=16;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			setFrame(0,0);
			if(y>=startY)
			{
				y = startY;
				attackMiniPhase++;
				jumpVelocity = 8;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(attackMiniPhase == 2)
		{
			x-=8;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			setFrame(0,0);
			if(y>=startY)
			{
				y = startY;
				attackMiniPhase++;
				jumpVelocity = 4;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(attackMiniPhase == 3)
		{
			x-=4;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			setFrame(0,0);
			if(y>=startY)
			{
				y = startY;
				attackMiniPhase++;
				currentAnimation = BattleAnimation.williamWalkAnimation;
			}
		}
		if(attackMiniPhase == 4)
		{
			if(x>startX+8)
			{
				this.flipped = false;
				x-=12;
			}
			else if(x<startX-8)
			{
				this.flipped = true;
				x+=12;
			}
			else
			{
				setFrame(0,0);
				this.flipped = true;
				Global.attackPhase++;
				x = startX;
				currentAnimation = BattleAnimation.williamIdleAnimation;
			}
		}
	}
	
	private void specialAttack()
	{
		System.out.println(attackMiniPhase);
		if(attackMiniPhase ==0 )
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
			if(bm.monsterIsSelected)
			{
				attackMiniPhase++;
				bm.specialType = 0;
				attackMiniPhase = 10;
			}
		}
		if(special == 0)
		{
			dartAttack();
		}
	}
	private void dartAttack()
	{
		if(attackMiniPhase == 10)
		{
			if(special == 0)
			{

				if(arrowXRight)
				{
					arrowX += 22;
				}
				else
				{
					arrowX -= 22;
				}
				if(Global.zPressed)
				{
					Global.williamSP -= 2;
					attackMiniPhase++;
					Global.zPressed = false;
					currentAnimation = BattleAnimation.williamDart;
					setFrame(0,0);
					if(arrowX<60 || arrowX>196)
					{
						attackStrength = 1;
					}
					else if(arrowX<102 || arrowX>152)
					{
						attackStrength = 2;
					}
					else
					{
						attackStrength = 3;
					}
				}
				if(arrowX>=246)
				{
					arrowXRight = false;
				}
				if(arrowX<=10)
				{
					arrowXRight = true;
				}
				
			}
		}
		if(attackMiniPhase == 11)
		{
			if(special == 0)
			{
				if(this.getFrame(0) == 16)
				{
					dartVisible[0] = true;
					dartX[0] = this.x+140;
				}
				if(getFrame(0) == 22)
				{
					AudioHandler.playSound(AudioHandler.sePew);
					setFrame(0,0);
					if(attackStrength == 1)
					{
						attackMiniPhase = 14;
					}
					else
					{
						attackMiniPhase++;
					}
				}
			}
		}
		if(attackMiniPhase == 12)
		{
			if(special == 0)
			{
				if(getFrame(0) == 16)
				{
					dartVisible[1] = true;
					dartX[1] = this.x+140;
				}
				if(getFrame(0) == 22)
				{
					setFrame(0,0);
					AudioHandler.playSound(AudioHandler.sePew);
					if(attackStrength == 2)
					{
						attackMiniPhase = 14;
					}
					else
					{
						attackMiniPhase++;
					}
				}
			}
		}
		if(attackMiniPhase == 13)
		{
			if(special == 0)
			{
				if(getFrame(0) == 16)
				{
					dartVisible[2] = true;
					dartX[2] = this.x+140;
				}
				if(getFrame(0) == 22)
				{
					setFrame(0,0);
					AudioHandler.playSound(AudioHandler.sePew);
					attackMiniPhase++;
				}
			}
		}
		if(attackMiniPhase == 14)
		{
			currentAnimation = BattleAnimation.williamIdleAnimation;
			if(!dartVisible[0] && attackStrength == 1)
			{
				attackMiniPhase = 0;
				Global.attackPhase++;
				special = 0;
			}
			if(!dartVisible[1] && !dartVisible[0] && attackStrength == 2)
			{
				attackMiniPhase = 0;
				Global.attackPhase++;
				special = 0;
			}
			if(!dartVisible[2] && !dartVisible[0] && !dartVisible[1] && attackStrength == 3)
			{
				attackMiniPhase = 0;
				Global.attackPhase++;
				special = 0;
			}
			
		}
	}
	
	private void specialMenu()
	{
		if(Global.zPressed && special == 0 && Global.williamSP >= 2)
		{
			System.out.println("hey there!");
			attackMiniPhase++;
			bm.specialType = 1;
			bm.monsterIsSelected = false;
			Global.zPressed = false;
		}
		if(Global.xPressed)
		{
			Global.attackPhase = 1;
		}
		if(Quest.quests[Quest.PYRUZ_W]>=7)
		{
			specialSelections = 0;
		}
		if(Global.downPressed)
		{
			specialDir = 0;
			if(special<specialSelections)
			{
				System.out.println("DAUNY");
				special++;
			}
			else
			{
				System.out.println("DAUN");
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
	
	private void runSpecialIntro()
	{
		if(Global.talking == 2)
		{
			this.flipped = false;
			currentAnimation = BattleAnimation.williamTalk2;
			SpeechBubble.talk("I can't say that I have, Skrapps! What are they?",Global.willFont);
			Global.talking = 3;
		}
		if(Global.talking == 5)
		{
			currentAnimation = BattleAnimation.williamIdle2;
		}
		if(Global.talking == 6)
		{
			this.flipped = false;
			currentAnimation = BattleAnimation.williamTalk2;
			SpeechBubble.talk("Attacks specific to certain people, huh? Tell me, what are my Special Attacks?",Global.willFont);
			Global.talking = 7;
		}
		if(Global.talking == 9)
		{
			currentAnimation = BattleAnimation.williamIdle2;
		}
		if(Global.talking == 10)
		{
			this.flipped = false;
			currentAnimation = BattleAnimation.williamTalk;
			SpeechBubble.talk("Just one special attack, huh? How do I get more, I want more special attacks!",Global.willFont);
			Global.talking = 11;
		}
		if(Global.talking == 13)
		{
			currentAnimation = BattleAnimation.williamIdle2;
		}
		if(Global.talking == 14)
		{
			this.flipped = false;
			currentAnimation = BattleAnimation.williamTalk2;
			SpeechBubble.talk("Ah, yes: Attack order! I will always attack before you, since I trust myself more./Also, you dodge with the [Z] button, while I dodge with [X]./Monsters will choose one of us to attack, so make sure you're on your toes!/You can dodge things such as Flairmer's fireballs by crouching as well, can't you?!",Global.willFont);
			Global.talking = 15;
		}
		if(Global.talking == 17)
		{
			currentAnimation = BattleAnimation.williamIdleAnimation;
		}
		if(Global.talking == 18)
		{
			flipped = true;
		}
	}
	private void drawDarts(Graphics g)
	{
		if(dartVisible[0])
		{
			dartX[0] += 16;
			this.setFrame(2, 0);
			this.animateFlip(dartX[0], this.y+48, BattleAnimation.dart, 2, g);
		}
		if(dartVisible[1])
		{
			dartX[1] += 16;
			this.setFrame(3, 0);
			this.animateFlip(dartX[1], this.y+48, BattleAnimation.dart, 3, g);
		}
		if(dartVisible[2])
		{
			dartX[2] += 16;
			this.setFrame(4, 0);
			this.animateFlip(dartX[2], this.y+48, BattleAnimation.dart, 4, g);
		}
		for(int i=0; i<3;i++)
		{
			if(dartX[i] >= bm.selectedMonster.x && dartVisible[i])
			{
				int toDeal = (int) (Global.williamPow*1.5)-bm.selectedMonster.defense;
				if(toDeal<0)
				{
					toDeal = 0;
				}
				window.battleMode.selectedMonster.flinch();
				window.battleMode.selectedMonster.hp-=toDeal;
				window.battleMode.selectedMonster.damageDealt+=toDeal;
				window.battleMode.selectedMonster.williamDealt+=toDeal;
				EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+64, BattleAnimation.hitAny, bm,true,toDeal);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
				AudioHandler.playSound(AudioHandler.seHit);
				dartVisible[i] = false;
			}
			
		}
	}
}
