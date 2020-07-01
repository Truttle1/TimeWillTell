package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.main.Game;

public class Garbzop extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	public Garbzop(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -40;
		super.boxOffsetY = 96;
		super.boxWidth = 96;
		super.hp = 25;
		super.maxHp = 25;
		super.boxWidth = 116;
		super.attack = 3;
		super.name = "Garbzop";
		super.type = MonsterType.Garbzop;
		currentAnimation = BattleAnimation.garbzopIdle;
		dodgeType = 0;
		xp = 24;
		money = 10;
		heightMod = 80;
		}


	
	@Override
	public void tick() {
		information = "This monster is literal garbage. It's a Garbzop! Max HP: 25; Attack: 3; The Garbzop is/the most basic of enemies found in Aqua City. It is able to attack you by hitting/you with it's lid. These attacks deal three damage! Supposedly, Garbzops were/created by an evil company secretly focused on destroying Aqua City! OH NO!!!";
		
		if(super.hp <= 0 && killedAttacker == null)
		{
			killedAttacker = Global.attacker;
		}
		if(Global.attacker != Attacker.Monsters)
		{
			bm.eyeCandy.remove(dodgeCandy);
		}
		if(super.hp<=0 && (Global.attacker != killedAttacker)) 
		{
			bm.xpGainedSimon+=(double)xp*((double)simonDealt/damageDealt);
			bm.xpGainedWilliam+=(double)xp*((double)williamDealt/damageDealt);
			bm.xpGainedPartner[Global.currentPartner]+=(double)xp*((double)partnerDealt[Global.currentPartner]/damageDealt);
			//Global.partnerXP[Global.currentPartner] += bm.xpGainedPartner[Global.currentPartner];
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.garbzopDie, bm);
			die.setRepeating(false);
			bm.eyeCandy.add(die);
			for(int i = super.monsterID; i<=bm.amountOfMonsters;++i)
			{
				for(int j=0; j<bm.objects.size();j++)
				{
					if(bm.objects.get(j) instanceof Monster)
					{
						Monster m = (Monster) bm.objects.get(j);
						if(m.monsterID == i)
						{
							m.monsterID--;
						}
					}
				}
			}
			bm.amountOfMonsters--;
			bm.attackingMonster = 0;
			bm.objects.remove(this);
			
		}
		checkSelected();
		if(Global.attacker != Attacker.Monsters && currentAnimation == BattleAnimation.garbzopFlinch && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.garbzopIdle;
		}
		if(Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack)
			{
				attackInit();
			}

			if(!Global.tutorialBattle || Global.tutorialBattlePhase > 4)
			{
				attack();
			}
			else if(Global.tutorialBattlePhase == 3)
			{
				Global.tutorialBattlePhase = 4;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		super.arrowRender(g);
		if(Global.attackPhase == 0 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,2);
		}
		else if(Global.attackPhase == 2 && Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			super.animateAtSpeed(super.x,super.y,currentAnimation,0,g,2);
		}
		else
		{
			if(currentAnimation[2].getWidth()<getFrame(0))
			{
				setFrame(0,0);
			}
			super.animate(super.x,super.y,currentAnimation,0,g);
		}
	}
	@Override
	public void attack()
	{
		
		if(Global.attackPhase == 0)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.garbzopWalk;
			x-=10;
			if(!bm.eyeCandy.contains(dodgeCandy) && x<=charAttacking.x+180 && charAttacking.id == ObjectId.WilliamBattler)
			{
				dodgeCandy = new EyeCandy(window, super.x-72, charAttacking.y-135, BattleAnimation.xIcon, bm);
				bm.eyeCandy.add(dodgeCandy);
			}
			if(x<=attackWalkDistance())
			{
				x = attackWalkDistance();
				setFrame(0, 0);
				currentAnimation = BattleAnimation.garbzopAttack;
				Global.attackPhase++;
				
			}
		}
		else if(Global.attackPhase == 1)
		{
			if(!currentAnimation.equals(BattleAnimation.garbzopFlinch))
			{
				attackFrameBased(4,10,3,BattleAnimation.garbzopFlinch);
				if(getFrame(0)>=23)
				{
					x = attackWalkDistance();
					setFrame(0, 0);
					currentAnimation = BattleAnimation.garbzopWalk;
					this.flipped = true;
					Global.attackPhase++;
				}
			}
			else if(getFrame(0)>=5)
			{
				this.flipped = true;
				Global.attackPhase++;
			}
		}
		else if(Global.attackPhase == 2)
		{
			currentAnimation = BattleAnimation.garbzopWalk;
			x+=10;
			if(x>=startX)
			{
				x = startX;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.garbzopIdle;
				Global.attackPhase = 0;
				if(this.monsterID >= bm.amountOfMonsters)
				{
					Global.attacker = Attacker.Simon;
					beganAttack = false;
					bm.selectedMonsterID = 0;
				}
				else
				{
					bm.attackingMonster++;
					beganAttack = false;
				}
				this.flipped = false;
				
			}
		}
	}
	protected void attackInit()
	{

		if(Global.attacker != Attacker.Lose)
		{
			try
			{
				selectAttacking();
				beganAttack = true;
			}
			catch(Exception e) {System.out.println("HLEP THERE IS NO PLAYER! TRYING TO KEEP THE GAME RUNNING SMOOTHLY. HIGH CHANCE OF ULTIMATE FAIL!");}
		}
		
	}

	@Override
	public void flinch() 
	{
		setFrame(0,0);
		this.currentAnimation = BattleAnimation.garbzopFlinch;
		
		
	}
	
	

}
