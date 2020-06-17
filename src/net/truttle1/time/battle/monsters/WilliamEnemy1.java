package net.truttle1.time.battle.monsters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.battle.Attacker;
import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.battle.EyeCandy;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;

public class WilliamEnemy1 extends Monster{

	BufferedImage[] idleAnimation;
	boolean beganAttack = false;
	int flinchTime = 0;
	int jumpVelocity = 0;
	private EyeCandy dodgeCandy;
	public WilliamEnemy1(Game window, int x, int y, BattleMode bm) {
		super(window, x, y, bm);
		super.boxOffsetX = 30;
		super.boxOffsetY = 96;
		super.boxWidth = 220;
		super.hp = 15;
		super.maxHp = 15;
		super.attack = 1;
		super.name = "Crazed Lizard Kid";
		super.information = "It's me...how are you viewing this message?/No seriously, if you see this, notify the Time Will Tell developer/IMMEDIATELY...unless you modified the save file, in which,/SHAME ON YOU...except not really...";
		super.type = MonsterType.William1;
		currentAnimation = BattleAnimation.williamIdleAnimation;
		dodgeType = 1;
		xp = 15;
		money = 5;
	}
	@Override
	public void attack() {
		if(Global.attackPhase == 0)
		{
			charAttacking.canDodge = true;
			currentAnimation = BattleAnimation.williamWalkAnimation;
			x-=10;
			if(x<=attackWalkDistance()+64)
			{
				x = attackWalkDistance()+64;
				setFrame(0, 0);
				currentAnimation = BattleAnimation.williamJumpAnimation;
				Global.attackPhase++;
				AudioHandler.playSound(AudioHandler.seJump);
				
			}
		}
		if(Global.attackPhase == 1)
		{

				x-=8;
				y-=jumpVelocity*2;
				jumpVelocity-=2;
				if(jumpVelocity<8)
				{

					if(!bm.eyeCandy.contains(dodgeCandy))
					{
						dodgeCandy = new EyeCandy(window, super.x-72, super.y, BattleAnimation.cIcon, bm);
						bm.eyeCandy.add(dodgeCandy);
					}
				}
				if(jumpVelocity==0)
				{
					currentAnimation = BattleAnimation.williamLandAnimation;
				}
				if(jumpVelocity<0 && y>=charAttacking.y-24)
				{
					Global.attackPhase++;
					jumpVelocity = 12;
					if(!(charAttacking.dodging && charAttacking.getFrame(0)>3 && charAttacking.getFrame(0)<10))
					{
						AudioHandler.playSound(AudioHandler.seHit);
						Global.simonHP--;
						EyeCandy atk = new EyeCandy(window, charAttacking.x+100, charAttacking.y+64, BattleAnimation.hitOneHP, bm);
						atk.setRepeating(false);
						bm.eyeCandy.add(atk);
						charAttacking.setFrame(0,0);
						charAttacking.currentAnimation = BattleAnimation.simonHitAnimation;
					}
				}
		}
		if(Global.attackPhase == 2)
		{
			if(bm.eyeCandy.contains(dodgeCandy))
			{
				bm.eyeCandy.remove(dodgeCandy);
			}
			x+=16;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			if(y>=startY)
			{
				y = startY;
				Global.attackPhase++;
				jumpVelocity = 8;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(Global.attackPhase == 3)
		{
			x+=8;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			if(y>=startY)
			{
				y = startY;
				Global.attackPhase++;
				jumpVelocity = 4;
				AudioHandler.playSound(AudioHandler.seJump2);
			}
		}
		if(Global.attackPhase == 4)
		{
			x+=4;
			y-=jumpVelocity*2;
			jumpVelocity-=2;
			if(y>=startY)
			{
				y = startY;
				Global.attackPhase++;
				currentAnimation = BattleAnimation.williamWalkAnimation;
			}
		}
		if(Global.attackPhase == 5)
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
				this.flipped = false;
				x = startX;
				Global.attackPhase = 0;
				beganAttack = false;
				currentAnimation = BattleAnimation.williamIdleAnimation;
				if(this.monsterID >= bm.amountOfMonsters)
				{
					Global.attacker = Attacker.Simon;
				}
				else
				{
					bm.attackingMonster++;
				}
			}
		}
	}

	@Override
	public void tick() {
		Quest.quests[Quest.TUTORIAL] = 11;
		checkSelected();
		if(super.hp<=0 && Global.attacker == Attacker.Monsters) 
		{
			bm.xpGainedSimon+=xp;
			bm.moneyGained += money;
			Global.money += money;
			EyeCandy die = new EyeCandy(window, x, y, BattleAnimation.williamDeadAnimation, bm);
			die.setRepeating(true);
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
		if(currentAnimation.equals(BattleAnimation.williamHitAnimation))
		{
			flinchTime++;
			if(flinchTime>Global.flinchAmount)
			{
				setFrame(0,0);
				currentAnimation = BattleAnimation.williamIdleAnimation;
			}
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
		if(currentAnimation[2].getWidth()<getFrame(0))
		{
			setFrame(0,0);
		}
		super.animate(super.x,super.y,currentAnimation,0,g);
	}

	@Override
	public void flinch() 
	{
		setFrame(0,0);
		currentAnimation = BattleAnimation.williamHitAnimation;
		flinchTime = 0;
	}
	protected void attackInit()
	{
		jumpVelocity = 20;
		beganAttack = true;
		setFrame(0,0);
		for(int i=0; i<bm.objects.size(); i++)
		{
			if (bm.objects.get(i).id == ObjectId.SimonBattler)
			{
				this.charAttacking = bm.objects.get(i);
			}
		}
	}

}
