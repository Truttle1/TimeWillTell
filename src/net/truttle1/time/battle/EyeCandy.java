package net.truttle1.time.battle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;

import net.truttle1.time.main.GameMode;
import net.truttle1.time.main.GameObject;
import net.truttle1.time.main.Global;
import net.truttle1.time.main.Game;

public class EyeCandy extends GameObject{

	BufferedImage[] animation;
	GameMode bm;
	private boolean repeating = true;
	private boolean exploding;
	private boolean explodingUp;
	private int number;
	private boolean positive = false;
	private double scale;
	private Color color;
	private BufferedImageOp colorize;
	private BufferedImage img;
	public EyeCandy(Game window, int x, int y, BufferedImage[] animation, GameMode bm) {
		super(window);
		super.x = x;
		super.y = y;
		this.animation = animation;
		this.bm = bm;
	}
	public EyeCandy(Game window, int x, int y, BufferedImage[] animation, GameMode bm, boolean e, int number) {
		super(window);
		super.x = x;
		super.y = y;
		this.animation = animation;
		this.bm = bm;
		this.exploding = e;
		this.number = number;
		if(exploding && number>0)
		{
			img = tint(1,1/(float)number,0,1,animation[0]);
			explodingUp = true;
			scale = 0;
		}
		else if(exploding)
		{
			img = tint(1,1,1,1,animation[0]);
			explodingUp = true;
			scale = 0;
		}
	}public EyeCandy(Game window, int x, int y, BufferedImage[] animation, GameMode bm, boolean e, int number, boolean positive, float r, float g, float b) {
		super(window);
		super.x = x;
		super.y = y;
		this.animation = animation;
		this.bm = bm;
		this.exploding = e;
		this.number = number;
		this.positive = true;
		if(exploding)
		{
			img = tint(r,g,b,1,animation[0]);
			explodingUp = true;
			scale = 0;
		}
	}

	public void setRepeating(boolean repeat)
	{
		repeating = repeat;
	}

	@Override
	public void tick() {
		if(exploding)
		{
			if(explodingUp)
			{
				scale += 0.2;
				if(scale>1.4)
				{
					explodingUp = false;
				}
			}
			else
			{
				scale -= 0.2;
				if(scale<0)
				{
					bm.eyeCandy.remove(this);
				}
			}
		}
		if(getFrame(0)>=getAnimationLength(animation))
		{
			if(repeating)
			{
				setFrame(0,0);
			}
			else
			{
				bm.eyeCandy.remove(this);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		double scaleA = scale;
		if (scaleA>1)
		{
			scaleA = 1;
		}
		if(exploding)
		{
			g.setFont(Global.battleFont);
			g.setColor(Color.black);
			g.drawImage(img, (int)(x+32-(32*scaleA)), (int)(y+32-(32*scaleA)), (int)(64*scaleA), (int)(64*scaleA), null);
			g.setFont(Global.battleFont);
			g.setColor(Color.black);
			if(positive)
			{
				g.drawString("+" + Integer.toString(number), x+20, y+40);
			}
			else
			{
				g.drawString("-" + Integer.toString(number), x+20, y+40);
			}
		}
		else
		{
			this.animate(x, y, animation, 0, g);
		}
	}
	protected BufferedImage tint(float r, float g, float b,float a, BufferedImage sprite)
	{
		BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics2D graphics = tintedSprite.createGraphics();
		graphics.drawImage(sprite, 0, 0, null);
		graphics.dispose();
	
		for (int i = 0; i < tintedSprite.getWidth(); i++)
		{
			for (int j = 0; j < tintedSprite.getHeight(); j++)
			{
				int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
				int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
				int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
				int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
				rx *= r;
				gx *= g;
				bx *= b;
				ax *= a;
				tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
			}
		}
		return tintedSprite;
	}

}
