package net.truttle1.time.main;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.overworld.OverworldAnimation;

public class GraphicsLoader extends Thread implements Runnable{

	Game w;
	public static Queue<BufferedImage[]> animationQueue = new LinkedList<BufferedImage[]>();
	public GraphicsLoader(Game window)
	{
		w = window;
	}
	@Override
	public void run() {
		if(!BattleAnimation.loaded)
		{

			BattleAnimation.loadBattleAnimations(w);
		}
		if(!OverworldAnimation.loaded)
		{
			OverworldAnimation.loadOverworldAnimations(w);
		}

		if(Global.comingFromTime == 3 && !w.menuMode.ready)
		{
			w.menuMode.ready = loadDigitalAgeAnimations(w);
		}
		if(Global.comingFromTime == 0 && !w.menuMode.ready)
		{
			w.menuMode.ready = loadStoneAgeAnimations(w);
		}
		
	}
	public boolean loadStoneAgeAnimations(Game window)
	{
		System.out.println("aaaa");
		if(!OverworldAnimation.loadedStoneAge)
		{
			OverworldAnimation.loadStoneAge(window);
		}
		if(!BattleAnimation.loadedStoneAge)
		{
			BattleAnimation.loadStoneAge(window);
		}
		return true;
	}
	public boolean loadDigitalAgeAnimations(Game window)
	{

		if(!OverworldAnimation.loadedDigitalAge)
		{
			OverworldAnimation.loadDigitalAge(window);
		}
		if(!BattleAnimation.loadedDigitalAge)
		{
			BattleAnimation.loadDigitalAge(window);
		}
		return true;
	}

	public void loadUnloadedGraphics(BufferedImage[] animation, int cFrame,double[] currentFrame, Game window)
	{
		//window.graphicsLoader.run();
		/*
		String path = null;
		if(animation[(int) currentFrame[cFrame]] == null)
		{
			for(int i=0; i<BattleAnimation.currentKey; i++)
			{
				if(BattleAnimation.imageKey[i] != null && BattleAnimation.imageKey[i].equals(animation[0]))
				{
					path = BattleAnimation.imagePath[i];
					break;
				}
			}

			for(int i=0; i<animation.length; i++)
			{
				if(i<9)
				{
					animation[i] = window.imageLoad.loadImage(path + "_0000" + (i+1) + ".png");
				}
				else if(i<99)
				{
					animation[i] = window.imageLoad.loadImage(path + "_000" + (i+1) + ".png");
				}
				else if(i<999)
				{
					animation[i] = window.imageLoad.loadImage(path + "_00" + (i+1) + ".png");
				}
			}
			//animation[(int) currentFrame[cFrame]] = BattleAnimation.loadFrame(path, (int)currentFrame[cFrame], window);
			
		}*/
		
	}

}
