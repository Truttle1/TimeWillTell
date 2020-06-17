package net.truttle1.time.overworld.npc.cutscene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.ObjectId;
import net.truttle1.time.main.Quest;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;
import net.truttle1.time.overworld.npc.NPC;

public class VolcanoExplodeCutscene extends NPC{

	private int timer;
	private int textY = -60;
	private boolean faded = false;
	public VolcanoExplodeCutscene(Game window, int x, int y, OverworldMode om) {
		super(window, x, y, om);
		this.id = ObjectId.Video;
		this.currentAnimation = OverworldAnimation.eruptVideo;
		AudioHandler.stopMusic();
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
		return null;
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public void render(Graphics g) {
		if(this.timer <= 48)
		{
			this.setFrame(0, 0);
		}
		this.animate(x, y, currentAnimation, 0, g);
		g.setFont(Global.winFont2);
		g.setColor(Color.gray.darker());
		g.drawString("ACT I COMPLETE!", om.tx+128+8, om.ty+textY+8);
		g.setColor(Color.yellow);
		g.drawString("ACT I COMPLETE!", om.tx+128, om.ty+textY);
	}
	@Override
	public void tick()
	{
		if(Fade.fadeIn && Quest.quests[Quest.ESCAPED] == 3)
		{
			AudioHandler.playMusic(AudioHandler.lomoMusic);
			om.objects.remove(this);
			Global.disableMovement = false;
		}
		System.out.println(timer);
		this.id = ObjectId.Video;
		this.x = om.tx;
		this.y = om.ty;
		if(this.timer <= 499)
		{
			Global.disableMovement = true;
		}
		if(timer >= 500 && !faded)
		{
			faded = true;
			this.setFrame(0,0);
			Quest.quests[Quest.ESCAPED] = 3;
			Fade.run(window, ModeType.Overworld, true, 5);
		}
		else if(timer == 200)
		{
			AudioHandler.playSound(AudioHandler.seActFinish);
			this.timer++;
		}
		else if(timer >= 144)
		{
			if(textY <= 300)
			{
				textY+= 12;
			}
			timer++;
			this.setFrame(0,0);
		}
		else if(getFrame(0)<=95)
		{
			this.timer++;
		}
		if(timer == 48)
		{
			AudioHandler.playSound(AudioHandler.seVolcanoCutscene);
		}
		
	}

}
