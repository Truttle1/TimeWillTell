package net.truttle1.time.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener{

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
		if(key == KeyEvent.VK_S)
		{
			Global.save = true;
		}
		if(key == KeyEvent.VK_L)
		{
			Global.load = true;
		}
		if(key == KeyEvent.VK_Z && !Global.zDown)
		{
			Global.zDown = true;
			Global.zPressed = true;
		}
		if(key == KeyEvent.VK_X && !Global.xDown)
		{
			Global.xDown = true;
			Global.xPressed = true;
		}
		if(key == KeyEvent.VK_C && !Global.cDown)
		{
			Global.cDown = true;
			Global.cPressed = true;
		}
		if(key == KeyEvent.VK_V && !Global.vDown)
		{
			Global.vDown = true;
			Global.vPressed = true;
		}
		if(key == KeyEvent.VK_LEFT && !Global.leftDown)
		{
			Global.leftDown = true;
			Global.leftPressed = true;
		}
		if(key == KeyEvent.VK_RIGHT && !Global.leftDown)
		{
			Global.rightDown = true;
			Global.rightPressed = true;
		}
		if(key == KeyEvent.VK_UP && !Global.leftDown)
		{
			Global.upDown = true;
			Global.upPressed = true;
		}
		if(key == KeyEvent.VK_DOWN && !Global.leftDown)
		{
			Global.downDown = true;
			Global.downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_Z)
		{
			Global.zDown = false;
			Global.zReleased = true;
		}
		if(key == KeyEvent.VK_X)
		{
			Global.xDown = false;
			Global.xReleased = true;
		}	
		if(key == KeyEvent.VK_C)
		{
			Global.cDown = false;
			Global.cReleased = true;
		}
		if(key == KeyEvent.VK_V)
		{
			Global.vDown = false;
			Global.vReleased = true;
		}
		if(key == KeyEvent.VK_LEFT)
		{
			Global.leftDown = false;
			Global.leftReleased = true;
		}
		if(key == KeyEvent.VK_RIGHT)
		{
			Global.rightDown = false;
			Global.rightReleased = true;
		}
		if(key == KeyEvent.VK_UP)
		{
			Global.upDown = false;
			Global.upReleased = true;
		}
		if(key == KeyEvent.VK_DOWN)
		{
			Global.downDown = false;
			Global.downReleased = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println(e.getKeyCode());
	}

}
