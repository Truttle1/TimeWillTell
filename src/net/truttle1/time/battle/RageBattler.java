package net.truttle1.time.battle;

import java.awt.Graphics;

import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.overworld.OverworldAnimation;

public class RageBattler extends GameObject{

	private BattleMode bm;
	public RageBattler(Game window, int x, int y, BattleMode bm) {
		super(window);
		super.x = x;
		super.y = y;
		this.bm = bm;
		currentAnimation = OverworldAnimation.rageIdle;
		startX = super.x;
		startY = super.y;
		this.id = ObjectId.RageBattler;
		this.flipped = true;
	}

	@Override
	public void tick() {
		if(Global.attacker == Attacker.Partner)
		{
			if(Global.attackPhase == 0)
			{
				this.setFrame(0, 0);
				Global.attackPhase++;
			}
			if(Global.attackPhase == 1)
			{
				currentAnimation = OverworldAnimation.rageTalk;
				if(getFrame(0) == 10)
				{
					AudioHandler.playSound(AudioHandler.seWoosh);
					Global.simonHP += 1;
					EyeCandy atk = new EyeCandy(bm.window, x+300, y, BattleAnimation.heart, bm,true,1,true,1,0,0);
					atk.setRepeating(true);
					bm.eyeCandy.add(atk);
				}
				if(getFrame(0) >= 23)
				{
					this.setFrame(0, 0);
					Global.attackPhase++;
				}
			}
			if(Global.attackPhase == 2)
			{
				currentAnimation = OverworldAnimation.rageIdle;
				Global.attackPhase = 0;
				bm.attackSelection = null;
				Global.attacker = Attacker.Monsters;
				bm.attackingMonster = 0;
			}
		}
		if(Global.attacker == Attacker.William && !Global.hasWilliam)
		{
			Global.attacker = Attacker.Partner;
		}
	}

	@Override
	public void render(Graphics g) {
		this.animate(x, y, currentAnimation, 0, g);
	}

}
