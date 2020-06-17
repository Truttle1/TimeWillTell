package net.truttle1.time.titlescreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.truttle1.time.battle.BattleAnimation;
import net.truttle1.time.battle.BattleMode;
import net.truttle1.time.effects.Fade;
import net.truttle1.time.main.AudioHandler;
import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.ModeType;
import net.truttle1.time.main.Quest;
import net.truttle1.time.main.Game;
import net.truttle1.time.main.WorldId;
import net.truttle1.time.overworld.OverworldAnimation;
import net.truttle1.time.overworld.OverworldMode;

public class TitleMode extends GameMode{

	BufferedImage[] screens = new BufferedImage[99];
	public static volatile String currentText = "";
	public static boolean intro = false;
	private static int introFade = 0;
	private static int introY = 100;
	public static boolean demoEnded;
	private double[] currentFrame = new double[9];
 	public TitleMode(Game window) {
 		
		super(window);
		for(int i=0; i<99;i++)
		{
			try
			{
				screens[i] = window.imageLoad.loadImage("/titlescreen/" + i + ".png");
			}
			catch(Exception e) {System.out.println("This screen does not exist");}
		}

	}

	@Override
	public void tick() {
		if(Global.zPressed && OverworldAnimation.loaded && BattleAnimation.loaded)
		{
			window.overworldMode = new OverworldMode(window);
			window.load();
			window.battleMode = new BattleMode(window);
			if(Quest.quests[Quest.TUTORIAL] == 0 && !intro)
			{
				intro = true;
				AudioHandler.playMusic(AudioHandler.intro);
			}
			else if(intro && introY < -3500)
			{
				window.textMode.setup("Act I/The Stone Age", 250, 250);
				Fade.run(window, ModeType.Text, true);
			}
			else
			{
				Fade.run(window, ModeType.Overworld, true);
			}
		}
		if(demoEnded)
		{
			AudioHandler.playMusic(AudioHandler.mountainLoop);
		}
	}

	@Override
	public void render(Graphics g) {

		if(Quest.quests[Quest.LOMO] >= Global.LOMOCONSTANT)
		{
			g.drawImage(screens[0],0,0,null);
		}
		else if(Quest.quests[Quest.PYRUZ_S] >= 2)
		{
			g.drawImage(screens[9],0,0,null);
		}
		else if(Quest.quests[Quest.PYRUZ_S] >= 1)
		{
			g.drawImage(screens[8],0,0,null);
		}
		else if(Quest.quests[Quest.PYRUZ_W] >= 6)
		{
			g.drawImage(screens[7],0,0,null);
		}
		else if(Quest.quests[Quest.LOMO] >= 8)
		{
			g.drawImage(screens[6],0,0,null);
		}
		else if(Quest.quests[Quest.LOMO] >= 1)
		{
			g.drawImage(screens[5],0,0,null);
		}
		else if(Quest.quests[Quest.LOMOBANK] >= 11)
		{
			g.drawImage(screens[4],0,0,null);
		}
		else if(Quest.quests[Quest.TUTORIAL] >= 9)
		{
			g.drawImage(screens[3],0,0,null);
		}
		else if(Quest.quests[Quest.TUTORIAL] >= 1)
		{
			g.drawImage(screens[2],0,0,null);
		}
		else
		{
			g.drawImage(screens[1],0,0,null);
		}
		if(OverworldAnimation.loaded && BattleAnimation.loaded)
		{
			
			g.setFont(Global.winFont2);
			g.setColor(Color.black);
			g.drawString("Press Z to play!", 128, 495);
			if(Quest.quests[Quest.TUTORIAL] <= 0)
			{
				g.setColor(Color.gray);
			}
			else
			{
				g.setColor(Color.white);
			}
			g.drawString("Press Z to play!", 124, 491);
		}
		else
		{
			if(Quest.quests[Quest.LOMO] >= Global.LOMOCONSTANT)
			{
				g.setColor(Color.white);
			}
			else
			{
				g.setColor(Color.black);
			}
			g.setFont(Global.winFont2);
			g.drawString("Reticulating Splines", 32, 495);
			g.setFont(Global.battleFont);
			g.drawString("Specifically, reticulating the spline: ", 32, 535);
			g.drawString(currentText, 32, 555);
		}
		if(intro && introFade<255)
		{
			introFade += 10;
			if(introFade > 255)
			{
				introFade = 255;
			}
		}
		Color c = new Color(0,0,0,introFade);
		g.setColor(c);
		g.fillRect(0, 0, window.WIDTH, window.HEIGHT);
		if(demoEnded)
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, window.WIDTH, window.HEIGHT);
			g.setFont(Global.winFont1);
			g.setColor(Color.white);
			g.drawString("The Time Will Tell demo has ended.", 128, 128);
			g.setFont(Global.textFont);
			g.drawString("More content should be on its way soon! Please be patient while", 96, 158);
			g.drawString("it is created using the power of magic Java code.", 96, 188);
			//g.drawString("In the meantime, here is a preview of a character that will be coming in", 32, 308);
			//g.drawString("the next build!", 32, 338);
			//this.animate(128, 400, BattleAnimation.skrappsTalkStick, 0, g, true);
			//this.animate(356, 400, BattleAnimation.skrappsIdle2, 1, g, false);
			//this.animate(584, 400, BattleAnimation.skrappsRun, 2, g, true);
			
		}
		if(intro)
		{
			//introY = 0;
			System.out.println(introY);
			g.setFont(Global.textFont);
			g.setColor(Color.white);
			g.drawString("In the far future, beyond the Stone Age, beyond the Digital Age,", 32, introY+600);
			g.drawString("and beyond the Medieval Age...", 256, introY+650);
			g.drawString("Of course it's beyond the Medieval Age if it is also beyond", 64, introY+800);
			g.drawString("the Digital Age... Whoever wrote this text is stupid!", 64, introY+840);
			g.drawString("A greatly evil something enslaved humanity...", 200, introY+1040);
			g.drawString("No, I don't know what it is exactly. Stop asking me. I'm just intro text.", 32, introY+1140);
			g.drawString("But anyways, only one person could save humanity.", 180, introY+1340);
			g.drawString("This person was also quite possibly the least qualified person for the job...", 0, introY+1540);
			g.drawString("He wasn't even a person, because all of humanity was enslaved...", 32, introY+1740);
			g.drawString("Also, none of this even matters right now...", 230, introY+2340);
			g.drawString("Because it isn't the Far Future yet...", 230, introY+2540);
			g.drawString("It is currently the Stone Age...", 256, introY+2740);
			g.drawString("Somethingthousand B.C.E is the specific year...", 220, introY+2780);
			g.drawString("And you are a Caveman...", 320, introY+3040);
			g.drawString("And your wife is going to ask you to run errands soon...", 180, introY+3240);
			g.drawString("That is where this story begins.", 280, introY+3440);
			g.drawString("CONTROLS:", 420, introY+4100);
			g.drawString("[Left] & [Right] Arrows: Walk", 300, introY+4200);
			g.drawString("[Z]: Jump / Advance Text", 300, introY+4240);
			g.drawString("[C]: Talk to things.", 300, introY+4280);
			g.drawString("[X]: Opens the Pause Menu.", 300, introY+4320);
			//g.drawString("[X]: Something related to that non-person mentioned earlier.", 100, introY+4320);
			//g.drawString("By the way, that person will be met within the first 20 or so minutes", 20, introY+4360);
			//g.drawString("of the game.", 20, introY+4390);
			g.drawString("Anyway, that's all I've got for you right now. Press [Z] to begin your", 20, introY+4480);
			g.drawString("epic and serious jorney!...actually, it's not that epic...or serious...", 20, introY+4510);
			g.drawString("Or don't press [Z] and listen to this MIDI track for the rest of eternity...", 20, introY+4540);
			if(introY>-4000)
			{
				introY -= 2;
			}
		}

	}

	private void animate(int x, int y,BufferedImage[] animation, int cFrame, Graphics g, boolean flipped)
	{
		Graphics2D g2d = (Graphics2D) g;
		if(!flipped)
		{
			g2d.drawImage(animation[(int) currentFrame[cFrame]],x,y, null);
		}
		else
		{
			g2d.drawImage(animation[(int) currentFrame[cFrame]],x+animation[(int) currentFrame[cFrame]].getWidth(),y,-animation[(int) currentFrame[cFrame]].getWidth(),animation[(int) currentFrame[cFrame]].getHeight(), null);
		}
		currentFrame[cFrame]++;
		if(currentFrame[cFrame] > animation.length-1)
		{
			currentFrame[cFrame] = 0;
		}
	}

}
