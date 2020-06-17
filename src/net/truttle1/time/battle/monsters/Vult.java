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

public class Vult extends Monster{

	BufferedImage[] idleAnimation;
	private boolean beganAttack = false;
	private double vx;
	private double vy;
	public Vult(Game window, int x, int y, BattleMode bm) {
		super(window,x,y,bm);
		super.boxOffsetX = -40;
		super.boxOffsetY = 72;
		super.boxWidth = 72;
		super.hp = 4;
		super.maxHp = 4;
		super.attack = 1;
		super.name = "Vult";
		super.type = MonsterType.Vult;
		currentAnimation = BattleAnimation.vultIdle;
		dodgeType = 0;
		xp = 6;
		money = 2;
		heightMod = 80;
		super.spikeFront = true;
		dodgeType = 0;
		information = "This is a Vulture...err...Vult! Max HP: 4; Attack: 1;/The Vult has a pointy beak, so you can't punch it without feeling a horrible pain.../Vults are known for being difficult to dodge.  A tip for counterattacking/a Vult as William is to press [X] right when the [X] icon appears.";
		
	}


	
	@Override
	public void tick() {
		if(super.hp <= 0 && killedAttacker == null)
		{
			killedAttacker = Global.attacker;
		}
		if(super.hp<=0 && (Global.attacker != killedAttacker)) 
		{
			bm.xpGainedSimon+=(double)xp*((double)simonDealt/damageDealt);
			bm.xpGainedWilliam+=(double)xp*((double)williamDealt/damageDealt);
			bm.xpGainedPartner[Global.currentPartner]+=(double)xp*((double)partnerDealt[Global.currentPartner]/damageDealt);
			Global.partnerXP[Global.currentPartner] += bm.xpGainedPartner[Global.currentPartner];Global.simonXP += bm.xpGainedSimon;
			Global.williamXP += bm.xpGainedWilliam;
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.vultDie, bm);
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
		if(currentAnimation == BattleAnimation.vultFlinch && getFrame(0)>=5)
		{
			currentAnimation = BattleAnimation.vultIdle;
		}
		if(Global.attacker == Attacker.Monsters && bm.attackingMonster == this.monsterID)
		{
			if(!beganAttack)
			{
				attackInit();
			}
			attack();

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
		System.out.println("X::" + x);
		if(Global.attackPhase == 0)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.vultIdle;
			this.x-=8;
			y-=1;
			if(x<=attackWalkDistance())
			{
				x = attackWalkDistance();
				setFrame(0, 0);
				currentAnimation = BattleAnimation.vultAttack;
				Global.attackPhase++;
				
			}
		}
		else if(Global.attackPhase == 1)
		{
			x+=vx;
			y-=vy;
			vx-=0.25;
			vy-=0.25;
			if(vx == 0)
			{
				Global.attackPhase++;
			}
			if(x<500 && vx<=0.75 && !candyAdded && charAttacking.id == ObjectId.WilliamBattler)
			{
				candyAdded = true;
				System.out.println("ADDED AN EYECANDY!");
				dodgeCandy = new EyeCandy(window, charAttacking.x+72, charAttacking.y-200, BattleAnimation.xIcon, bm);
				bm.eyeCandy.add(dodgeCandy);
			}
		}
		else if(Global.attackPhase == 2)
		{
			if(!attacked)
			{
				attackXBased(250,42,32,72);
			}
			else if(attacked && currentAnimation != BattleAnimation.vultFlinch)
			{
				y = startY;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.vultIdle;
				this.flipped = true;
				Global.attackPhase++;
			}
			else
			{
				System.out.println("Hi!");
				hitTime++;
				if(hitTime>10)
				{
					y = startY;
					setFrame(0, 0);
					currentAnimation = BattleAnimation.vultIdle;
					this.flipped = true;
					Global.attackPhase++;
				}
			}
		}
		else if(Global.attackPhase == 3)
		{
			currentAnimation = BattleAnimation.vultIdle;
			x+=10;
			y = startY;
			if(x>=startX)
			{
				x = startX;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.vultIdle;
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

		hitTime = 0;
		vx = 10;
		vy = 8;
		attacked = false;
		candyAdded = false;
		selectAttacking();
		beganAttack = true;
	}

	@Override
	public void flinch() 
	{
		setFrame(0,0);
		window.battleMode.selectedMonster.currentAnimation = BattleAnimation.vultFlinch;
	}
	
	

}
