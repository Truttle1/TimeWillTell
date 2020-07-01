package net.truttle1.time.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioHandler {
	
	public static int clipTime;
	
	public static File talk0;
	public static File talk1;
	public static File prehistoricMusic;
	public static File battleMusic;
	public static File evilShuffle;
	public static File williamTheme;
	public static File williamBattleMusic;
	public static File bossMusic;
	public static File timeForTrouble;
	public static File levelUpWilliam;
	public static File levelUpSimon;
	public static File lomoMusic;
	public static File lomoDeserted;
	public static File cutscene1;
	public static File cutscene2;
	public static File mountainLoop;
	public static File skrapps;
	public static File run;
	public static File title;
	public static File intro;
	public static File throneRoom;
	public static File actOneFinalBoss;
	public static File metro;
	public static File metroMinor;
	public static File currentMusic = null;

	public static File seJump;
	public static File seJump2;
	public static File seJump3;
	public static File seFall;
	public static File seHit;
	public static File seHit2;
	public static File seCrack;
	public static File seWoosh;
	public static File sePunch;
	public static File seFire;
	public static File seDodge;
	public static File seSizzle;
	public static File seCrash;
	public static File sePew;
	public static File seWin;
	public static File seLose;
	public static File seGuitar0;
	public static File seGuitar1;
	public static File seBreak;
	public static File seChomp;
	public static File seStomp;
	public static File seQuake;
	public static File seActFinish;
	public static File seVolcanoCutscene;
	public static File seBounce;

	public static Clip[] speechClips = new Clip[10];
	public static void init()
	{
		talk0 = new File("res/audio/talk0.wav");
		talk1 = new File("res/audio/talk1.wav");
		prehistoricMusic = new File("res/music/prehistoric.wav");
		battleMusic = new File("res/music/battle1.wav");
		evilShuffle = new File("res/music/evilshuffle.wav");
		williamTheme = new File("res/music/williamtheme.wav");
		williamBattleMusic = new File("res/music/williambattle.wav");
		timeForTrouble = new File("res/music/timefortrouble.wav");
		bossMusic = new File("res/music/boss.wav");
		levelUpWilliam = new File("res/music/lvlup.wav");
		levelUpSimon = new File("res/music/simonlevel.wav");
		lomoMusic = new File("res/music/lomovillage.wav");
		lomoDeserted = new File("res/music/desertedlomo.wav");
		cutscene1 = new File("res/music/mountaincutscene.wav");
		cutscene2 = new File("res/music/friendlycutscene.wav");
		mountainLoop = new File("res/music/mountaindrums.wav");
		title = new File("res/music/titlesong.wav");
		run = new File("res/music/run.wav");
		intro = new File("res/music/intro.wav");
		skrapps = new File("res/music/skrapps.wav");
		throneRoom = new File("res/music/throneroom.wav");
		actOneFinalBoss = new File("res/music/letusout.wav");
		metro = new File("res/music/metro.wav");
		metroMinor = new File("res/music/convex_path.wav");

		seHit = new File("res/audio/doink0.wav");
		seHit2 = new File("res/audio/doink1.wav");
		seJump = new File("res/audio/doink2.wav");
		seJump2 = new File("res/audio/jump0.wav");
		seJump3 = new File("res/audio/jump1.wav");
		seFall = new File("res/audio/fall0.wav");
		seCrack = new File("res/audio/crack0.wav");
		seWoosh = new File("res/audio/swoosh0.wav");
		sePunch = new File("res/audio/punch0.wav");
		seFire = new File("res/audio/burn0.wav");
		seSizzle = new File("res/audio/burn1.wav");
		seDodge = new File("res/audio/hit0.wav");
		seCrash = new File("res/audio/crash0.wav");
		sePew = new File("res/audio/pew0.wav");
		seWin = new File("res/audio/win0.wav");
		seLose = new File("res/audio/lose0.wav");
		seGuitar0 = new File("res/audio/guitar0.wav");
		seGuitar1 = new File("res/audio/guitar1.wav");
		seBreak = new File("res/audio/doink0.wav");
		seChomp = new File("res/audio/chomp0.wav");
		seStomp = new File("res/audio/stomp0.wav");
		seQuake = new File("res/audio/quake0.wav");
		seActFinish = new File("res/audio/brass0.wav");
		seBounce = new File("res/audio/bounce0.wav");
		seVolcanoCutscene = new File("res/audio/earthshatteringkaboom0.wav");

		for(int i=0;i<10;i++)
		{
			try {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(talk0);
				speechClips[i] = AudioSystem.getClip();
				speechClips[i].open(audioIn);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static ArrayList<Clip> effectsOld = new ArrayList<Clip>();
	public static ArrayList<Clip> effects = new ArrayList<Clip>();
	public static ArrayList<File> effectFiles = new ArrayList<File>();
	public static Clip music;
	

	public static void stopMusic()
	{
		music.stop();
	}

	public static void playMusicAtPosition(File sound, long pos)
	{
		if(currentMusic != sound)
			{
			try {
				try
				{
				music.close();
				}
				catch(Exception e) {System.out.println("There is no music. Playing some now.");}
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
				music = AudioSystem.getClip();
				music.open(audioIn);
				music.setMicrosecondPosition(pos);
				music.start();
				music.setMicrosecondPosition(pos);
				music.loop(Clip.LOOP_CONTINUOUSLY);
				currentMusic = sound;
				} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	public static void playMusic(File sound)
	{
		if(currentMusic != sound)
			{
			try {
				try
				{
				music.close();
				}
				catch(Exception e) {System.out.println("There is no music. Playing some now.");}
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
				music = AudioSystem.getClip();
				music.open(audioIn);
				music.start();
				music.loop(Clip.LOOP_CONTINUOUSLY);
				currentMusic = sound;
				} catch (UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	public static void playSound(File sound)
	{
		try {
			/*
			for(int i=0; i<effects.size(); i++)
			{
				Clip e = effects.get(i);
				if(!e.isRunning())
				{
					e.close();
				}
			}*/
			if(effectFiles.contains(sound))
			{
				for(int i=0; i<effectFiles.size();i++)
				{
					if(effectFiles.get(i).equals(sound))
					{
						effects.get(i).setFramePosition(0);
						effects.get(i).start();
					}
				}
			}
			else
			{
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
				Clip c = AudioSystem.getClip();
				c.open(audioIn);
				c.setMicrosecondPosition(0);
				c.start();
				effects.add(c);
				effectFiles.add(sound);
			}
			if(effects.size() != effectFiles.size())
			{
				System.out.println("AUDIO WARNING!");
				effects.clear();
				effectFiles.clear();
			}
			
			//effects.add(c);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}catch(Exception e) {e.printStackTrace();}
	}
	public static void playSoundSpeech(int soundToPlay)
	{
		speechClips[soundToPlay%10].setMicrosecondPosition(0);
		speechClips[soundToPlay%10].start();
	}
	public static void playSoundOld(File sound)
	{
		try {
			for(int i=0; i<effectsOld.size(); i++)
			{
				Clip e = effectsOld.get(i);
				if(!e.isRunning())
				{
					e.close();
				}
			}
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
			Clip c = AudioSystem.getClip();
			c.open(audioIn);
			c.setMicrosecondPosition(0);
			c.start();
			effectsOld.add(c);
			
			//effects.add(c);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}catch(Exception e) {e.printStackTrace();}
	}
	public static void stopSoundEffects()
	{
		for(int i=0; i<effects.size();i++)
		{
			effects.get(i).setMicrosecondPosition(99999999);
		}
	}
}
