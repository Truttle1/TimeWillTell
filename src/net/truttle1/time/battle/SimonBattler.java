package net.truttle1.time.battle;

import java.awt.Color;
import java.awt.Graphics;

import net.truttle1.time.effects.Store;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class SimonBattler extends GameObject{

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
	private int specialDir;
	private int special;
	private int specialSelections;
	private int clubCharge;
	
	public SimonBattler(Game window, int x, int y, BattleMode bm) {
		super(window);
		super.x = x;
		super.y = y;
		this.bm = bm;
		currentAnimation = BattleAnimation.simonIdleAnimation;
		startX = super.x;
		startY = super.y;
		this.id = ObjectId.SimonBattler;
	}

	@Override
	public void tick() {

		if(bm.levelingUp == true && bm.leveler == 0)
		{
			currentAnimation = BattleAnimation.simonLevelUp;
		}
		if(Global.simonHP <= 0 && !dead)
		{
			this.setFrame(0, 0);
			Global.simonHP = 0;
			dead = true;
		}
		if(dead && Global.simonHP>0)
		{
			this.canDodge = true;
			dead = false;
			currentAnimation = BattleAnimation.simonIdleAnimation;
		}
		if(dead)
		{
			currentAnimation = BattleAnimation.simonDeadAnimation;
			if(Global.attacker == Attacker.Simon)
			{
				x = startX;
				setFrame(0,0);
				Global.attackPhase = 0;
				bm.attackSelection = null;
				if(Global.hasWilliam)
				{
					Global.attacker = Attacker.William;
				}
				else if(Global.hasPartner)
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
		if(Global.attacker == Attacker.Simon && !Global.disableMovement)
		{
			attack();
			canDodge = true;
		}
		else
		{
			if(Global.attacker == Attacker.Monsters && canDodge && Global.cPressed)
			{
				setFrame(0,0);
				canDodge = false;
				dodging = true;
				if(bm.monsters[bm.attackingMonster].dodgeType==1)
				{
					currentAnimation = BattleAnimation.simonCrouchAnimation;	
				}
				else
				{
					currentAnimation = BattleAnimation.simonDodgeAnimation;
				}
			}
			if(dodging && getFrame(0)>=11 || (!dodging && currentAnimation == BattleAnimation.simonDodgeAnimation))
			{
				System.out.println("Hi!");
				currentAnimation = BattleAnimation.simonIdleAnimation;
				setFrame(0,0);
				dodging = false;
			}
		}
		if(currentAnimation == BattleAnimation.simonHitAnimation && getFrame(0)>=11)
		{
			currentAnimation = BattleAnimation.simonIdleAnimation;
		}
	}

	@Override
	public void render(Graphics g) 
	{
		if(Global.attacker == Attacker.Simon && Global.attackPhase == 0)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.orange);
			g.fillRect(x-16, y-150, 250, 160);
			if(menuSelection == 0)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-142, 234, 24);
			}
			if(menuSelection == 1)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-112, 234, 24);
			}
			if(menuSelection == 2)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-82, 234, 24);
			}
			if(menuSelection == 3)
			{
				g.setColor(Color.white);
				g.fillRect(x-8, y-52, 234, 24);
			}
			g.setColor(Color.orange.darker().darker());
			g.drawString("Punch", x-2, y-122);
			g.drawString("Item", x-2, y-92);
			if(bm.simonSelections >= 2)
			{
				g.drawString("Club", x-2, y-62);
			}

			if(bm.simonSelections >= 3)
			{
				g.drawString("Special", x-2, y-32);
			}
		}

		if(Global.attacker == Attacker.Simon && bm.attackSelection == AttackSelection.Item && Global.attackPhase == 3)
		{
			if(attackMiniPhase == 0)
			{
				/*
				g.setFont(Global.battleFont);
				g.setColor(Color.orange);
				g.fillRect(x-16, y-180, 250, 190);
				int yOff = 0;
				for(int i=0; i<Global.items.length; i++)
				{
					if(Global.items[i]>0)
					{
						if(itemSelection == i)
						{
							g.setColor(Color.white);
							g.fillRect(x-4, y-(138-yOff), 225, 22);
						}
						g.setColor(Color.orange.darker().darker());
						g.drawString(Store.itemNames[i], x-2, y-(122-yOff));
						g.drawString("x" + Integer.toString(Global.items[i]), x+180, y-(122-yOff));
						yOff += 22;
					}
				}
				g.setFont(Global.battleFont);
				g.setColor(Color.orange.darker());
				g.drawString("Press [X] to go back",x-16,y-155);*/
				drawItemMenu(Color.orange,g,itemSelection);
			}
			else
			{
				this.currentAnimation = BattleAnimation.simonItem;
				this.setFrame(0, 0);
				g.drawImage(Store.itemImage[itemSelection],this.x+64,this.y-32,null);
			}
		}
		if(Global.attacker == Attacker.Simon && Global.attackPhase == 2)
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
		}

		if(Global.attacker == Attacker.Simon && bm.attackSelection == AttackSelection.Special && Global.attackPhase == 3)
		{
			renderSpecialMenu(g);
		}
	}
	private void attack()
	{
		if(Global.attackPhase == 0 && Global.attacker == Attacker.Simon)
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
					bm.attackSelection = AttackSelection.Club;
					Global.attackPhase++;
					noMoveAttack = false;
				}
				if(menuSelection == 3)
				{
					setFrame(0,0);
					bm.attackSelection = AttackSelection.Special;
					Global.attackPhase = 3;
					Global.zPressed = false;
					attackMiniPhase = 0;
					noMoveAttack = true;
				}
			}
			if(menuSelection>bm.simonSelections)
			{
				menuSelection = 0;
			}
			if(menuSelection<0)
			{
				menuSelection = bm.simonSelections;
			}
			if(Global.downPressed)
			{
				menuSelection++;
			}
			if(Global.upPressed)
			{
				menuSelection--;
			}
			currentAnimation = BattleAnimation.simonIdleAnimation;
		}
		if(Global.attackPhase == 2)
		{
			if(!noMoveAttack)
			{
				selectedMonsterDistance = getMonsterDistance();
				super.x+=10;
				currentAnimation = BattleAnimation.simonWalkAnimation;
				if(window.battleMode.selectedMonster != null)
				{
					if(x >= window.battleMode.selectedMonster.x-selectedMonsterDistance)
					{
						setFrame(0,0);
						Global.attackPhase++;
						currentAnimation = BattleAnimation.simonIdleAnimation;
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
				if(!Global.tutorialBattle || Global.tutorialBattlePhase > 2)
				{
					basicAttack();
				}
				else if(Global.tutorialBattlePhase == 1)
				{
					Global.tutorialBattlePhase = 2;
				}
			}
			if(bm.attackSelection == AttackSelection.Club)
			{
				clubAttack();
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
			if(x <= startX)
			{
				x = startX;
				setFrame(0,0);
				currentAnimation = BattleAnimation.simonIdleAnimation;
				Global.attackPhase = 0;
				bm.attackSelection = null;
				if(Global.hasWilliam)
				{
					Global.attacker = Attacker.William;
				}
				else if(Global.hasPartner)
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
	}
	private void testAttack()
	{
		currentAnimation = BattleAnimation.simonHitAnimation;
		if(getFrame(0)==5)
		{
			Global.simonHP = Global.simonHPMax;
		}
		if(getFrame(0)>=11)
		{
			timedHit = 0;
			setFrame(0,0);
			Global.attackPhase++;
			currentAnimation = BattleAnimation.simonWalkAnimation;
		}
	}
	private void basicAttack()
	{
		if(currentAnimation != BattleAnimation.simonHitAnimation)
		{
			currentAnimation = BattleAnimation.simonPunchAnimation;
		}
		if(getFrame(0)==17)
		{
			zButtonCandy = new EyeCandy(window, super.x+72, super.y-72, BattleAnimation.zIcon, bm);
			bm.eyeCandy.add(zButtonCandy);
		}
		if(getFrame(0)==41)
		{
			bm.eyeCandy.remove(zButtonCandy);
		}
		if(Global.zPressed && timedHit == 0)
		{
			if(getFrame(0)>=17 && getFrame(0)<=41)
			{
				setFrame(0,41);
				timedHit++;
				bm.eyeCandy.remove(zButtonCandy);
			}
			else if(getFrame(0)<17)
			{
				setFrame(0,41);
			}
		}
		if(timedHit == 0 && getFrame(0) == 53)
		{
			setFrame(0,71);
		}
		if(getFrame(0) >= 53 && window.battleMode.selectedMonster.hp<=0)
		{
			setFrame(0,71);
		}
		if(getFrame(0) == 44)
		{
			if(!window.battleMode.selectedMonster.spikeFront)
			{
				EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+48, BattleAnimation.hitAny, bm, true, Global.simonPow-bm.selectedMonster.defense);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
				AudioHandler.playSoundOld(AudioHandler.sePunch);
				window.battleMode.selectedMonster.setFrame(0,0);
				window.battleMode.selectedMonster.hp -= Global.simonPow-bm.selectedMonster.defense;
				window.battleMode.selectedMonster.damageDealt+=Global.simonPow-bm.selectedMonster.defense;
				window.battleMode.selectedMonster.simonDealt+=Global.simonPow-bm.selectedMonster.defense;
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
			else
			{
				AudioHandler.playSoundOld(AudioHandler.seDodge);
				timedHit = 0;
				Global.simonHP--;
				EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+48, BattleAnimation.hitAny, bm, true, 1);
				atk.setRepeating(true);
				bm.eyeCandy.add(atk);
				this.setFrame(0,0);
				this.currentAnimation = BattleAnimation.simonHitAnimation;
			
			}
		}
		if(getFrame(0) == 56)
		{
			AudioHandler.playSoundOld(AudioHandler.sePunch);
			
			EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+48, BattleAnimation.hitAny, bm, true, Global.simonPow-bm.selectedMonster.defense);
			atk.setRepeating(true);
			bm.eyeCandy.add(atk);
			window.battleMode.selectedMonster.setFrame(0,0);
			window.battleMode.selectedMonster.hp -= Global.simonPow-bm.selectedMonster.defense;
			window.battleMode.selectedMonster.damageDealt+=Global.simonPow-bm.selectedMonster.defense;
			window.battleMode.selectedMonster.simonDealt+=Global.simonPow-bm.selectedMonster.defense;
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
		if(getFrame(0)>=71)
		{
			timedHit = 0;
			setFrame(0,0);
			Global.attackPhase++;
			currentAnimation = BattleAnimation.simonWalkAnimation;
		}
		if(getFrame(0)>=11 && currentAnimation == BattleAnimation.simonHitAnimation)
		{
			timedHit = 0;
			setFrame(0,0);
			Global.attackPhase++;
			currentAnimation = BattleAnimation.simonWalkAnimation;
		}
	}
	private void clubAttack()
	{
		if(currentAnimation != BattleAnimation.simonClubMiss)
		{
			currentAnimation = BattleAnimation.simonClubHit;
		}
		if(getFrame(0)==8)
		{
			zButtonCandy = new EyeCandy(window, super.x+72, super.y-72, BattleAnimation.zIcon, bm);
			bm.eyeCandy.add(zButtonCandy);
		}
		if(getFrame(0)==18)
		{
			bm.eyeCandy.remove(zButtonCandy);
		}
		if(Global.zPressed && timedHit == 0)
		{
			if(getFrame(0)>=8 && getFrame(0)<=18 && currentAnimation == BattleAnimation.simonClubHit)
			{
				setFrame(0,20);
				timedHit++;
				bm.eyeCandy.remove(zButtonCandy);
			}
			else if(getFrame(0)<17)
			{
				currentAnimation = BattleAnimation.simonClubMiss;
			}
		}
		if(getFrame(0)==19 && timedHit == 0)
		{
			currentAnimation = BattleAnimation.simonClubMiss;
		}
		if(getFrame(0) == 24 && currentAnimation == BattleAnimation.simonClubHit)
		{
			AudioHandler.playSoundOld(AudioHandler.seHit);
			
			EyeCandy atk = new EyeCandy(window, window.battleMode.selectedMonster.x+32, window.battleMode.selectedMonster.y+48, BattleAnimation.hitAny, bm, true, Global.simonPow-bm.selectedMonster.defense+this.clubCharge);
			atk.setRepeating(true);
			bm.eyeCandy.add(atk);
			window.battleMode.selectedMonster.setFrame(0,0);
			window.battleMode.selectedMonster.hp -= Global.simonPow-bm.selectedMonster.defense+this.clubCharge;
			window.battleMode.selectedMonster.damageDealt+=Global.simonPow-bm.selectedMonster.defense+this.clubCharge;
			window.battleMode.selectedMonster.simonDealt+=Global.simonPow-bm.selectedMonster.defense+this.clubCharge;
			window.battleMode.selectedMonster.flinch();
			Global.flinchAmount = 16;
		}
		if(getFrame(0)>=35)
		{
			this.clubCharge = 0;
			timedHit = 0;
			setFrame(0,0);
			Global.attackPhase++;
			currentAnimation = BattleAnimation.simonWalkAnimation;
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
				currentAnimation = BattleAnimation.simonIdleAnimation;
			}
		}
		
	}
	private void renderSpecialMenu(Graphics g)
	{
		if(attackMiniPhase == 0)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.orange);
			g.fillRect(x+64, y-300, 250, 250);
			int yOff = 0;
			g.setColor(Color.white);
			g.fillRect(x+70, (y-290)+25*special, 240, 24);
			if(Quest.quests[Quest.PYRUZ_S]>=1)
			{
				if(Global.simonSP >= 2)
				{
					g.setColor(Color.orange.darker().darker());
				}
				else
				{
					g.setColor(Color.red.darker().darker());
				}
				g.drawString("Charge Club", x+72, y-270);
				g.drawString("2 SP", x+250, y-270);
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

			if(special == 0)
			{
				Global.simonSP -= 2;
				currentAnimation = BattleAnimation.simonClubHit;
				setFrame(0,0);
				bm.specialType = 0;
				attackMiniPhase = 10;
			}
		}
		if(attackMiniPhase == 10)
		{
			if(special == 0)
			{
				if(getFrame(0) == 23)
				{
					clubCharge+=8;
					if(clubCharge>=14)
					{
						clubCharge = 14;
					}
					this.currentAnimation = BattleAnimation.simonIdleAnimation;
					attackMiniPhase++;
					setFrame(0,0);
				}
			}
		}
		if(attackMiniPhase == 11)
		{
			if(special == 0)
			{
				Global.attackPhase++;
				attackMiniPhase = 0;
				special = 0;
			}
		}
	}
	private void specialMenu()
	{
		if(Global.zPressed && special == 0 && Global.simonSP >= 2)
		{
			System.out.println("hey there!");
			attackMiniPhase++;
			bm.specialType = 0;
			bm.monsterIsSelected = false;
			Global.zPressed = false;
		}
		if(Global.xPressed)
		{
			Global.attackPhase = 1;
		}
		if(Quest.quests[Quest.PYRUZ_S]>=1)
		{
			specialSelections = 0;
		}
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
}
