
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import objects3D.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import GraphicsObjects.Arcball;
import GraphicsObjects.Utils;
import objects3D.TexCube;
import objects3D.TexSphere;
import objects3D.Grid;
import objects3D.Human;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {

	private  boolean MouseOnepressed = true;
	private boolean dragMode=false;
	private boolean Run = true;
	private boolean Earth= false;
	/** position of pointer */
	float x = 400, y = 300;
	/** angle of rotation */
	float rotation = 0;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;
	
	long  myDelta =0 ; //to use for animation
	float Alpha =0 ; //to use for animation
	long StartTime; // beginAnimiation

	int ballStep = 0;
	int stage = 0;
	int judge = 0;

	int camera_posi = 300;
	int in_camera = 0;

	ArrayList<Object> posi = new ArrayList<>();
	float ball_posi = -90;
	boolean is_on = false;

	Arcball MyArcball= new Arcball();
	
	boolean DRAWGRID = false;
	boolean waitForKeyrelease= true;
	/** Mouse movement */
	int LastMouseX = -1 ;
	int LastMouseY= -1;
	
	 float pullX = 0.0f; // arc ball  X cord. 
	 float pullY = 0.0f; // arc ball  Y cord. 

	 
	int OrthoNumber = 1200; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2 // do not change this for assignment 3 but you can change everything for your project 
	
	// basic colours
	static float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	static float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	static float spot[] = { 0.1f, 0.1f, 0.1f, 0.5f };

	// primary colours
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	static float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
	static float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };

	// secondary colours
	static float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	static float magenta[] = { 1.0f, 0.0f, 1.0f, 1.0f };
	static float cyan[] = { 0.0f, 1.0f, 1.0f, 1.0f };

	// other colours
	static float orange[] = { 1.0f, 0.5f, 0.0f, 1.0f, 1.0f };
	static float brown[] = { 0.5f, 0.25f, 0.0f, 1.0f, 1.0f };
	static float dkgreen[] = { 0.0f, 0.5f, 0.0f, 1.0f, 1.0f };
	static float pink[] = { 1.0f, 0.6f, 0.6f, 1.0f, 1.0f };

	// static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

	//support method to aid in converting a java float array into a Floatbuffer which is faster for the opengl layer to process 
	

	public void start() {
		
		StartTime = getTime();
		try {
			Display.setDisplayMode(new DisplayMode(1200, 800));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		 
		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			update(delta);
			renderGL();
			Display.update();
			Display.sync(120); // cap fps to 120fps
		}

		Display.destroy();
	}

	public void update(int delta) {
		// rotate quad
		//rotation += 0.01f * delta;
		  
		  
		int MouseX= Mouse.getX();
		int MouseY= Mouse.getY();
		int WheelPostion = Mouse.getDWheel();
	  
		  
		boolean  MouseButonPressed= Mouse.isButtonDown(0);
		  
		 
		  
		if(MouseButonPressed && !MouseOnepressed ) {
			MouseOnepressed =true;
//			  System.out.println("Mouse drag mode");
//			MyArcball.startBall( MouseX, MouseY, 1200, 800);
			dragMode=true;
				
				
		} else if( !MouseButonPressed) {
				// System.out.println("Mouse drag mode end ");
			  MouseOnepressed =false;
			  dragMode=false;
		}
		  
		if(dragMode) {
			MyArcball.updateBall( MouseX  , MouseY  , 1200, 800);
		}
		  
		if(WheelPostion>0) {
			OrthoNumber += 10;
			 
		}
		  
		if(WheelPostion<0) {
			OrthoNumber -= 10;
			if( OrthoNumber<610) {
				OrthoNumber=610;
			}
			  
			//  System.out.println("Orth nubmer = " +  OrthoNumber);
			  
		}
		  
		/** rest key is R*/
		if (Keyboard.isKeyDown(Keyboard.KEY_R))
			MyArcball.reset();

		if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
			if (ballStep< 50) {
				stage = 1;
				judge = 1;
			} else if (ballStep >= 100 && ballStep < 150) {
				stage = 2;
				judge = 1;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (camera_posi < 970) {
				camera_posi += 5;
			}

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (camera_posi > 230) {
				camera_posi -= 5;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			System.out.println(in_camera);
			if (in_camera == 0) {
				in_camera = 1;
				changeCamera();

			} else {
				in_camera = 0;
				changeCamera();
			}
		}

		if (Mouse.isButtonDown(1)) {
			if (Run == true) {
				Run = false;
			} else {
				Run = true;
				StartTime += getTime() - (StartTime + (long) posi.get(3));
			}
		}

		if (Mouse.isButtonDown(0)) {
			if (is_on == false) {
				float distance = Math.abs((float) posi.get(2) - ball_posi);
				if (distance < 10) {
					is_on = true;
				}
			} else {
				ball_posi = (float) posi.get(2);
				is_on = false;
			}
		}

		/* bad animation can be turn on or off using A key)*/
		  
//		if (Keyboard.isKeyDown(Keyboard.KEY_A))
//			BadAnimation=!BadAnimation;
//		if (Keyboard.isKeyDown(Keyboard.KEY_D))
//			x += 0.35f * delta;
//
//		if (Keyboard.isKeyDown(Keyboard.KEY_W))
//			y += 0.35f * delta;
//		if (Keyboard.isKeyDown(Keyboard.KEY_S))
//			y -= 0.35f * delta;
//
//		if (Keyboard.isKeyDown(Keyboard.KEY_Q))
//			rotation += 0.35f * delta;
//		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
//			Earth=!Earth;
//		}
//
//		if(waitForKeyrelease) {// check done to see if key is released
//			if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
//
//				DRAWGRID = !DRAWGRID;
//				Keyboard.next();
//				if(Keyboard.isKeyDown(Keyboard.KEY_G))
//				{
//					waitForKeyrelease=true;
//				}else
//				{
//					waitForKeyrelease=false;
//
//				}
//			}
//		}
		 
//		/** to check if key is released */
//		if(Keyboard.isKeyDown(Keyboard.KEY_G)==false) {
//			waitForKeyrelease=true;
//		} else {
//			waitForKeyrelease=false;
//		}
		 
		 
		 


		// keep quad on the screen
		if (x < 0)
			x = 0;
		if (x > 1200)
			x = 1200;
		if (y < 0)
			y = 0;
		if (y > 800)
			y = 800;

		updateFPS(); // update FPS Counter
		
		LastMouseX= MouseX;
		LastMouseY= MouseY ;
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
//
		setCamera();
		MyArcball.startBall(0, 0, 1200,800);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
		lightPos.put(10000).put(10000).put(10000).put(10000).flip();

		FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
		lightPos2.put(0).put(1000).put(0).put(10000).flip();

		FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
		lightPos3.put(10000f).put(1000).put(1000).put(0).flip();

		FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
		lightPos4.put(-10000f).put(1000f).put(1000).put(10000).flip();

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup specific materials so in real light it will look abit strange
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, Utils.ConvertForGL(grey));

		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));

		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
																	// the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
																	// the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
		 GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

		GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
		GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
											// on
	 	GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
	 	GL11.glEnable(GL11.GL_COLOR_MATERIAL);

		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //load in texture
          

	}

	 

	public void changeOrth() {

		 GL11.glMatrixMode(GL11.GL_PROJECTION);
		 GL11.glLoadIdentity();
		  GL11.glOrtho(1200 -  OrthoNumber , OrthoNumber, (800 - (OrthoNumber  * 0.66f)) , (OrthoNumber * 0.66f), 100000, -100000);
		 	GL11.glMatrixMode(GL11.GL_MODELVIEW); 
		 	
		 	FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16); 
		 	GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);
		 
		 	if(MouseOnepressed)
		 	{
		  
		 	MyArcball.getMatrix(CurrentMatrix);
		 	}
		 	
		    GL11.glLoadMatrix(CurrentMatrix);
		 	
	}

	/*
	 * You can edit this method to add in your own objects /  remember to load in textures in the INIT method as they take time to load 
	 * 
	 */
	public void renderGL() { 
//		changeOrth();
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glColor3f(0.5f, 0.5f, 1.0f); 
		 
		myDelta = getTime() - StartTime;
		float delta =((float) myDelta)/50000;
		   
		// code to aid in animation
		float theta = (float) (delta * 2 * Math.PI);
		float thetaDeg = delta * 360;
		float posn_x = (float) -Math.cos(theta); // same as your circle code in your notes
		float posn_y  = (float) -Math.sin(theta);
		  
		/*
		 * This code draws a grid to help you view the human models movement
		 *  You may change this code to move the grid around and change its starting angle as you please
		 */
		if (DRAWGRID) {
			GL11.glPushMatrix();
			Grid MyGrid = new Grid();
			GL11.glTranslatef(600, 400, 0);
			GL11.glScalef(200f, 200f,  200f);
			MyGrid.DrawGrid();
			GL11.glPopMatrix();
		}

		if (!is_on) {
			GL11.glPushMatrix();
			Human MyHuman = new Human();
			GL11.glTranslatef(600, 200,600 );
			GL11.glScalef(45f, 45f,  45f);
			if (Run) {
				GL11.glTranslatef(posn_x*12.0f, 0.0f, posn_y*12.0f);
				GL11.glRotatef(-thetaDeg, 0.0f, 1.0f, 0.0f);
				posi.set(0, posn_x);
				posi.set(1, posn_y);
				posi.set(2, -thetaDeg);
				posi.set(3, myDelta);
			} else {
				GL11.glTranslatef((float) posi.get(0)*12.0f, 0.0f, (float) posi.get(1)*12.0f);
				GL11.glRotatef((float) posi.get(2), 0.0f, 1.0f, 0.0f);
			}
			MyHuman.DrawHuman(delta, Run, texture_jeans, texture_clothes); // give a delta for the Human object ot be animated
			GL11.glPopMatrix();


			// draw sphere with a sky texture
			GL11.glPushMatrix();
			TexSphere ball = new TexSphere();
			GL11.glTranslatef((float) (600 - 480 * Math.cos(Math.toRadians(-ball_posi))), 200, (float) (600 - 480 * Math.sin(Math.toRadians(-ball_posi))));
			// rotate the sphere so that it can display correctly
			GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
			GL11.glScalef(2.5f, 2.5f, 2.5f);
			GL11.glTexParameteri(
					GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
					GL11.GL_CLAMP);
			Color.white.bind();
			texture_sky.bind();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			ball.DrawTexSphere(6f, 100, 100, texture_watermelon);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			Staff staff = new Staff();
			GL11.glTranslatef(600, 200,600 );
			GL11.glScalef(45f, 45f,  45f);
			if (Run) {
				GL11.glTranslatef(posn_x*12.0f, 0.0f, posn_y*12.0f);
				GL11.glRotatef(-thetaDeg, 0.0f, 1.0f, 0.0f);
				posi.set(0, posn_x);
				posi.set(1, posn_y);
				posi.set(2, -thetaDeg);
				posi.set(3, myDelta);
			} else {
				GL11.glTranslatef((float) posi.get(0)*12.0f, 0.0f, (float) posi.get(1)*12.0f);
				GL11.glRotatef((float) posi.get(2), 0.0f, 1.0f, 0.0f);
			}
			staff.DrawStaff(delta, Run, texture_jeans, texture_clothes, texture_sky); // give a delta for the Human object ot be animated
			GL11.glPopMatrix();
		}

		GL11.glPushMatrix();
		SmashPlayer player1 = new SmashPlayer();
		GL11.glTranslatef(470, 210,495 );
		GL11.glRotatef(-90, 0, 1, 0);
		GL11.glScalef(45f, 45f,  45f);
		player1.DrawSmashPlayer("player1", ballStep, texture_jeans, texture_clothes); // give a delta for the Human object ot be animated
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		CatchPlayer player2 = new CatchPlayer();
		GL11.glTranslatef(230, 190,715 );
		GL11.glRotatef(-60, 0, 1, 0);
		GL11.glScalef(45f, 45f,  45f);
		player2.DrawCatchPlayer("player2", judge, ballStep, texture_jeans, texture_clothes); // give a delta for the Human object ot be animated
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SmashPlayer player3 = new SmashPlayer();
		GL11.glTranslatef(730, 210,665 );
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glScalef(45f, 45f,  45f);
		player3.DrawSmashPlayer("player3", ballStep, texture_jeans, texture_clothes); // give a delta for the Human object ot be animated
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		CatchPlayer player4 = new CatchPlayer();
		GL11.glTranslatef(970, 190,485 );
		GL11.glRotatef(120, 0, 1, 0);
		GL11.glScalef(45f, 45f,  45f);
		player4.DrawCatchPlayer("player4", judge, ballStep, texture_jeans, texture_clothes); // give a delta for the Human object ot be animated
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		Referee referee = new Referee();
		GL11.glTranslatef(600, 350,900 );
		GL11.glScalef(45f, 45f,  45f);
		referee.DrawReferee(stage, judge, ballStep, texture_jeans, texture_clothes); // give a delta for the Human object ot be animated
		GL11.glPopMatrix();

		// draw cube with a sky texture
		GL11.glPushMatrix();
		SimpleTexCube sky1 = new SimpleTexCube();
		GL11.glTranslatef(600, 1500, 2000 );
		GL11.glScalef(300f, 300f, 1f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_sky_b.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		sky1.DrawSimpleTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube sky2 = new SimpleTexCube();
		GL11.glTranslatef(2000, 1500, 600 );
		GL11.glScalef(1f, 300f, 300f);
		GL11.glRotatef(-90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_sky_b.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		sky2.DrawSimpleTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube sky3 = new SimpleTexCube();
		GL11.glTranslatef(600, 1500, -2000 );
		GL11.glScalef(300f, 300f, 1f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_sky_b.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		sky3.DrawSimpleTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube sky4 = new SimpleTexCube();
		GL11.glTranslatef(-800, 1500, 600 );
		GL11.glScalef(1f, 300f, 300f);
//		GL11.glRotatef(-90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_sky_b.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		sky4.DrawSimpleTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// draw cube with a floor texture
		GL11.glPushMatrix();
		SimpleTexCube floor = new SimpleTexCube();
		GL11.glTranslatef(600, 144, 600 );
		GL11.glScalef(300f, 1f,  300f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_floor.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		floor.DrawSimpleTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// draw cube with a court texture
		GL11.glPushMatrix();
		SimpleTexCube court = new SimpleTexCube();
		GL11.glTranslatef(600, 145, 600 );
		GL11.glScalef(100f, 1f,  50f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_court.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		court.DrawSimpleTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// draw cube with a net texture
		GL11.glPushMatrix();
		TexCube net1 = new TexCube();
		GL11.glTranslatef(599, 250, 600 );
		GL11.glScalef(0.5f, 9f, 35f);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_net.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		net1.DrawTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// draw cube with a net texture
		GL11.glPushMatrix();
		TexCube net2 = new TexCube();
		GL11.glTranslatef(601, 250, 600 );
		GL11.glScalef(0.5f, 9f, 35f);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_net.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		net2.DrawTexCube(5.0f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// draw cylinder
		GL11.glPushMatrix();
		Cylinder cylinder1 = new Cylinder();
		GL11.glTranslatef(600, 300, 780 );
		GL11.glRotatef(90, 1, 0, 0);
		cylinder1.DrawCylinder(5, 150, 32);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		Cylinder cylinder2 = new Cylinder();
		GL11.glTranslatef(600, 300, 420 );
		GL11.glRotatef(90, 1, 0, 0);
		cylinder2.DrawCylinder(5, 150, 32);
		GL11.glPopMatrix();

		// draw shadow
		GL11.glPushMatrix();
		SimpleTexCube shadow1 = new SimpleTexCube();
		GL11.glTranslatef(260, 151,715 );
		GL11.glScalef(30, 0.1f, 30);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		shadow1.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube shadow2 = new SimpleTexCube();
		GL11.glTranslatef(530, 151,495 );
		GL11.glScalef(30, 0.1f, 30);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		shadow2.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube shadow3 = new SimpleTexCube();
		GL11.glTranslatef(760, 151,665 );
		GL11.glScalef(30, 0.1f, 30);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		shadow3.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube shadow4 = new SimpleTexCube();
		GL11.glTranslatef(1000, 151,485 );
		GL11.glScalef(30, 0.1f, 30);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		shadow4.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube shadow_net = new SimpleTexCube();
		GL11.glTranslatef(650, 151, 600 );
		GL11.glScalef(30, 0.1f, 170);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		shadow_net.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		SimpleTexCube shadow_referee = new SimpleTexCube();
		GL11.glTranslatef(730, 151, 930 );
		GL11.glScalef(100, 0.1f, 30);
		GL11.glRotatef(90, 1, 0, 0);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		shadow_referee.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// line of camera
		GL11.glPushMatrix();
		SimpleTexCube line = new SimpleTexCube();
		GL11.glTranslatef(600, 550, 200 );
		GL11.glScalef(500, 1f, 1f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		line.DrawSimpleTexCube(1);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// camera
		GL11.glPushMatrix();
		Camera camera = new Camera();
		GL11.glTranslatef(camera_posi, 550, 203 );
		GL11.glRotatef(30, 1, 0, 0);
		camera.DrawCamera(texture_camera);
		GL11.glPopMatrix();

		changeCamera();

		moveBall();

		/*
		 * This code puts the earth code in which is larger than the human so it appears to change the scene 
		 */
		if (Earth) {
			//Globe in the centre of the scene 
			GL11.glPushMatrix();
			TexSphere MyGlobe = new TexSphere();
			//TexCube MyGlobe = new TexCube();
			GL11.glTranslatef(500, 500,500 );
			GL11.glScalef(140f, 140f,  140f);
			 
			GL11.glTexParameteri(
					GL11.GL_TEXTURE_2D, 	GL11.GL_TEXTURE_WRAP_T, 
					GL11.GL_CLAMP);
		  
			    Color.white.bind();
			    texture.bind();
			    GL11.glEnable(GL11.GL_TEXTURE_2D);    
			    GL11.glTexParameteri( GL11.GL_TEXTURE_2D,  GL11.GL_TEXTURE_MAG_FILTER,  GL11.GL_NEAREST);
		        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);   
	  
		 	MyGlobe.DrawTexSphere(8f, 100, 100, texture);
		//	MyGlobe.DrawTexCube(); 
			GL11.glPopMatrix();
		}
//		changeCamera();
		
	}

	public void setCamera() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(80f, 1.25f, 1f, 10000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GLU.gluLookAt(100, 600, 100,
				600, 150, 600,
				0, 1, 0
		);
	}

	public void changeCamera() {
		if (in_camera == 0) {
			GL11.glLoadIdentity();
			GLU.gluLookAt(200, 600, 200,
					600, 150, 600,
					0, 1, 0
			);
		} else {
			GL11.glLoadIdentity();
			GLU.gluLookAt(camera_posi, 500, 230,
					600, 150, 600,
					0, 1, 0
			);
		}
	}

	public void moveBall() {

		float x = 0, y = 0, z = 0;
		if (judge == 51) {
			stage = 0;
			judge = 0;
			ballStep = 0;
		}
		if (ballStep < 50) {
			int move = ballStep;
			x = 290 + 22 / 5 * move;
			y = (float) (240 - 0.077 * move * move + 6.2 * move);
			z = 695 - 22 / 5 * move;
//				GL11.glTranslatef(290 + 22 / 5 * move, (float) (240 - 0.077 * move * move  + 6.2 * move), 695 - 22 / 5 * move);
		} else if (ballStep >= 50 && ballStep < 100) {
			if (judge == 0) {
				int move = ballStep - 50;
				x = 510 + 8 * move;
				y = (float) (330 - 1.8 * move);
				z = 475;
//				GL11.glTranslatef(510 + 8 * move, (float) (330 - 1.8 * move), 475);
			} else {
				int move = ballStep - 50;
				x = 510 + 16 * move;
				y = (float) (330 - 1.8 * move);
				z = 475;
				judge += 1;
			}
		} else if (ballStep >= 100 && ballStep < 150) {
			int move = ballStep - 100;
			x = 910 - 22 / 5 * move;
			y = (float) (240 - 0.077 * move * move + 6.2 * move);
			z = 475 + 22 / 5 * move;
//				GL11.glTranslatef(910 - 22 / 5 * move, (float) (240 - 0.077 * move * move + 6.2 * move), 475 + 22 / 5 * move);
		} else if (ballStep >= 150 && ballStep < 200){
			if (judge == 0) {
				int move = ballStep - 150;
				x = 690 - 8 * move;
				y = (float) (330 - 1.8 * move);
				z = 695;
//				GL11.glTranslatef(690 - 8 * move, (float) (330 - 1.8 * move), 695);
			} else {
				int move = ballStep - 150;
				x = 690 - 16 * move;
				y = (float) (330 - 1.8 * move);
				z = 695;
				judge += 1;
			}
		}

		// draw sphere with a sky texture
		GL11.glPushMatrix();
		TexSphere ball = new TexSphere();
		GL11.glTranslatef(x, y, z);
		// rotate the sphere so that it can display correctly
		GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glScalef(2.5f, 2.5f, 2.5f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_sky.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		ball.DrawTexSphere(6f, 100, 100, texture_sky);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		// draw ball shadow
		GL11.glPushMatrix();
		TexSphere ballShadow = new TexSphere();
		GL11.glTranslatef(x, 150, z);
		GL11.glScalef(2.5f, 0.3f, 2.5f);
		GL11.glTexParameteri(
				GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_CLAMP);
		Color.white.bind();
		texture_shadow.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		ball.DrawTexSphere(6f, 100, 100, texture_shadow);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();

		if (ballStep < 200) {
			ballStep += 1;
		} else {
			ballStep = 0;
		}
	}
		  
	public static void main(String[] argv) {
		MainWindow hello = new MainWindow();
		hello.posi.add(0f);
		hello.posi.add(0f);
		hello.posi.add(0f);
		hello.posi.add(0L);
		hello.start();
	}

	// all textures
	Texture texture;
	Texture texture_watermelon;
	Texture texture_scene;
	Texture texture_clothes;
	Texture texture_jeans;
	Texture texture_dice;
	Texture texture_sky;
	Texture texture_court;
	Texture texture_net;
	Texture texture_shadow;
	Texture texture_camera;
	Texture texture_floor;
	Texture texture_sky_b;
	 
	/*
	 * Any additional textures for your assignment should be written in here. 
	 * Make a new texture variable for each one so they can be loaded in at the beginning 
	 */

	// load the texture
	public void init() throws IOException {
		texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/earthspace.png"));
		texture_watermelon = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/watermelon.png"));
		texture_scene = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/scene.png"));
		texture_clothes = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/clothes.png"));
		texture_jeans = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/jeans.png"));
		texture_dice = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/dice.png"));
		texture_sky = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/2021.jpg"));
		texture_court = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/court.jpg"));
		texture_net = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/net.jpg"));
		texture_shadow = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/shadow.jpg"));
		texture_camera = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/camera.jpg"));
		texture_floor = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/floor.jpg"));
		texture_sky_b = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sky.png"));
		System.out.println("Texture loaded okay ");
	}
}
