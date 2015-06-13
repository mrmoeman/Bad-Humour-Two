
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL40;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
//import org.lwjgl.opengl.glu.GLU;
import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.EXTFramebufferObject.*;



 
public class Main {
    private boolean done = false;
    private boolean fullscreen = false;
    private final String windowTitle = "Bad Humour";
    private boolean f1 = false;
    private DisplayMode displayMode;
    
    
    private GameHandler MyGame = new GameHandler();
    
  //window size
    final int WIDTH = 1920;
    final int HEIGHT = 1080;
    
    FBO OutlineFBO;
    FBO ToonFBO;
    FBO BaseFBO;
    FBO XBlurFBO;
    FBO YBlurFBO;
    FBO BulletBaseFBO;
    FBO PlanetLightingFBO;
    FBO FinalBlurFBO;	///////////
    FBO FinalBulletBaseFBO; ////////////
    FBO DepthFBO; //////////////
    
    Matrix4f MyCameraMatrix;
    
    /**
     * Everything starts and ends here.  Takes 1 optional command line argument.
     * If fullscreen is specified on the command line then fullscreen is used,
     * otherwise windowed mode will be used.
     * @param args command line arguments
     */
    public static void main(String args[]) {
        boolean fullscreen = true;
        if(args.length>0) {
            if(args[0].equalsIgnoreCase("fullscreen")) {
                fullscreen = true;
            }
        }
        Main l1 = new Main();
        l1.run(fullscreen);
    }

    /**
     * Launch point
     * @param fullscreen boolean value, set to true to run in fullscreen mode
     */
    public void run(boolean fullscreen) {
        this.fullscreen = fullscreen;
        try {
            init();
            ShaderHandler.ShaderSetup();
            
            while (!done) {
                mainloop();
                render();
                update();
                Display.update();
                Display.sync(60);
            }
            cleanup();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * All updating is done here.  Key and mouse polling as well as window closing and
     * custom updates, such as AI.
     */
    private void mainloop() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {       // Exit if Escape is pressed
            done = true;
        }
        if(Display.isCloseRequested()) {                     // Exit if window is closed
            done = true;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F1) && !f1) {    // Is F1 Being Pressed?
            f1 = true;                                      // Tell Program F1 Is Being Held
            switchMode();                                   // Toggle Fullscreen / Windowed Mode
        }
        if(!Keyboard.isKeyDown(Keyboard.KEY_F1)) {          // Is F1 Being Pressed?
            f1 = false;
        }
    }

    private void switchMode() {
        fullscreen = !fullscreen;
        try {
            Display.setFullscreen(fullscreen);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * For rendering all objects to the screen
     * @return boolean for success or not
     */
    private boolean render() {
    	
    	SwitchToPer();
    	RenderBase();
    	
    	//SwitchToOrtho();
    	//FBO DEPTH
    	SwitchToDepthPer();
        RenderDepth();
        SwitchToPer();
        
        //FBO TOON
        RenderToon();
        
        //PlanetToon
        RenderPlanetToon();
        

       //FBO Eddge
        SwitchToOrtho();
        RenderEdge();
        SwitchToPer();
        
        
        
        FinalBlurFBO.BindBuffer();
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        FinalBulletBaseFBO.BindBuffer();
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        
        int loc = GL20.glGetUniformLocation(ShaderHandler.XBlur.shaderProgram, "BlurScale");
        int locA = GL20.glGetUniformLocation(ShaderHandler.YBlur.shaderProgram, "BlurScale");
        for(int i = 0; i < 4; i++){
      //FBO Bullet
        SwitchToPer();
        if(i == 0){
        	RenderBulletBase(0.25f);
        	
        	ShaderHandler.XBlur.Activate();
        	GL20.glUniform1f(loc, 0.25f);
        	ShaderHandler.XBlur.DeActivate();
        	ShaderHandler.YBlur.Activate();
        	GL20.glUniform1f(locA, 0.25f);
        	ShaderHandler.YBlur.DeActivate();
        }
        
        if(i == 1){
            RenderBulletBase(0.5f);
            
            ShaderHandler.XBlur.Activate();
        	GL20.glUniform1f(loc, 0.5f);
        	ShaderHandler.XBlur.DeActivate();
        	ShaderHandler.YBlur.Activate();
        	GL20.glUniform1f(locA, 0.5f);
        	ShaderHandler.YBlur.DeActivate();
        }
        
        if(i == 3){
            RenderBulletBase(1.0f);
            
            ShaderHandler.XBlur.Activate();
        	GL20.glUniform1f(loc, 1.0f);
        	ShaderHandler.XBlur.DeActivate();
        	ShaderHandler.YBlur.Activate();
        	GL20.glUniform1f(locA, 1.0f);
        	ShaderHandler.YBlur.DeActivate();
        }
        
        
      //FBO Blur
        SwitchToOrtho();
        RenderBlur();
        
        }
        
        //FINALE RENDER STAGE
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        if(Input.GetF2() == false && Input.GetF3() == false && Input.GetF4() == false && Input.GetF5() == false && Input.GetF6() == false && Input.GetF7() == false && Input.GetF8() == false && Input.GetF9() == false  && Input.GetF10() == false){
        
        GL11.glLoadIdentity(); 
        //FInal Render
        RenderFinal();

      
        RenderUI();
        }
        if(Input.GetF2() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, DepthFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF3() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, OutlineFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF4() == true){	/////
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, PlanetLightingFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF5() == true){	////
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, YBlurFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF6() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, BaseFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF7() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, BulletBaseFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF8() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, FinalBlurFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF9() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, FinalBulletBaseFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
        if(Input.GetF10() == true){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, ToonFBO.fb_texture);
            ShaderHandler.ShipShader.Activate();
            drawTexture(0, 0, WIDTH, HEIGHT);
        }
    	
        return true;
        
        
    }
    
    private void drawTexture(float x, float y, int width, int height){
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glTexCoord2f(0f, 0f);
    	GL11.glVertex2f(x, y);
        
    	GL11.glTexCoord2f(1f, 0f);
    	GL11.glVertex2f(x + width, y);
        
    	GL11.glTexCoord2f(1f, 1f);
    	GL11.glVertex2f(x + width, y + height);
        
    	GL11.glTexCoord2f(0f, 1f);
    	GL11.glVertex2f(x, y + height);
    	GL11.glEnd();
     }

    /**
     * Create a window depending on whether fullscreen is selected
     * @throws Exception Throws the Window.create() exception up the stack.
     */
    private void createWindow() throws Exception {
        Display.setFullscreen(fullscreen);
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; i++) {
            if (d[i].getWidth() == WIDTH
                && d[i].getHeight() == HEIGHT
                && d[i].getBitsPerPixel() == 32) {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle(windowTitle);
        Display.create();
        Display.setResizable(true);
        Display.setVSyncEnabled(true);
        //Display.setFullscreen(true);
    }

    private void init() throws Exception {
        createWindow();

        initGL();
        
        OutlineFBO = new FBO(1080, 1920);
        ToonFBO = new FBO(1080, 1920);
        BaseFBO = new FBO(1080, 1920);
        XBlurFBO = new FBO(1080, 1920);
        YBlurFBO = new FBO(1080, 1920);
        BulletBaseFBO = new FBO(1080, 1920);
        PlanetLightingFBO = new FBO(1080, 1920);
        FinalBlurFBO = new FBO(1080, 1920);
        FinalBulletBaseFBO = new FBO(1080, 1920);
        DepthFBO = new FBO(1080, 1920);
        
        LoadScreen.LoadLoadTexture();
        MyGame.SetUpMenus();
        FontHandler.SetupFonts();

    }

    /**
     * Initialize OpenGL
     *
     */
    private void initGL() {
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glClearColor(0f, 0f, 0f, 0.0f); // Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do

        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        
       
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping 
        

    	GL11.glEnable(GL11.GL_LIGHT1); 
    	//GL11.glEnable ( GL11.GL_LIGHTING );

        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45.0f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          0.1f,
          100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix

        // Really Nice Perspective Calculations
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);  

    }

    private void cleanup() {
        Display.destroy();
    }

    private void update(){
    	
    	MyGame.Update();
    	
    }
    
    private void SwitchToPer(){
    	
    	GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          1f,
          500.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
    	
    }
    
    
    private void SwitchToDepthPer(){
    	
    	GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(
          45f,
          (float) displayMode.getWidth() / (float) displayMode.getHeight(),
          30f,
          500.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
    	
    }
    
    private void SwitchToOrtho(){
    	
    	GL11.glLoadIdentity(); 
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity(); 
        GL11.glOrtho(0, WIDTH, 0, HEIGHT, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    	
    }
    
    private void RenderBase(){
    	GameData.CurrentRenderState = RenderState.ShipBase;
    	//render to fbo 1
        BaseFBO.BindBuffer();
        
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
      
        MyGame.Draw();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    	
    }
    
    private void RenderToon(){
    	GameData.CurrentRenderState = RenderState.ShipLight;
    	ToonFBO.BindBuffer();
        
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
      
        
        MyGame.DrawLighting();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    	
    }
    
    private void RenderBulletBase(float BlurScale){
    	GameData.CurrentRenderState = RenderState.BulletBase;
    	BulletBaseFBO.BindBuffer();
        
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
      
        
        MyGame.DrawBullets(BlurScale);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        
        SwitchToOrtho();
        ShaderHandler.BulletFixerShader.Activate();
        FinalBulletBaseFBO.BindBuffer();
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, BulletBaseFBO.fb_texture);
        drawTexture(0, 0, WIDTH, HEIGHT);
        
        ShaderHandler.BulletFixerShader.DeActivate();
    	
    }
    
    private void RenderBlur(){
    	GameData.CurrentRenderState = RenderState.BulletBlurX;
    	XBlurFBO.BindBuffer();
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        GL11.glLoadIdentity(); 
        //render the texture
        ShaderHandler.XBlur.Activate();
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, BulletBaseFBO.fb_texture);
        int locET = GL20.glGetUniformLocation(ShaderHandler.XBlur.shaderProgram, "ColourMap");
		GL20.glUniform1i(locET, 0);
        
        drawTexture(0, 0, WIDTH, HEIGHT);
        
        ShaderHandler.XBlur.DeActivate();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        
        GameData.CurrentRenderState = RenderState.BulletBlurY;
        YBlurFBO.BindBuffer();
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        
        GL11.glLoadIdentity(); 
        //render the texture
        ShaderHandler.YBlur.Activate();
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, XBlurFBO.fb_texture);
        int locE = GL20.glGetUniformLocation(ShaderHandler.YBlur.shaderProgram, "ColourMap");
		GL20.glUniform1i(locE, 0);
        
        drawTexture(0, 0, WIDTH, HEIGHT);
        
        ShaderHandler.YBlur.DeActivate();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        ShaderHandler.BulletFixerShader.Activate();
        FinalBlurFBO.BindBuffer();
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, YBlurFBO.fb_texture);
        drawTexture(0, 0, WIDTH, HEIGHT);
        
        ShaderHandler.BulletFixerShader.DeActivate();
    	
    }
    
    private void RenderEdge(){
    	GameData.CurrentRenderState = RenderState.ShipEdge;
    	 OutlineFBO.BindBuffer();
         GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
         GL11.glClear (GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
         
         GL11.glLoadIdentity(); 
         //render the texture
         ShaderHandler.EdgeDetection.Activate();
         
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
         GL11.glBindTexture(GL11.GL_TEXTURE_2D, BaseFBO.fb_texture);
         int locET = GL20.glGetUniformLocation(ShaderHandler.EdgeDetection.shaderProgram, "ColourMap");
 		GL20.glUniform1i(locET, 0);
         
         drawTexture(0, 0, WIDTH, HEIGHT);
         
         ShaderHandler.EdgeDetection.DeActivate();
         
         GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    	
    }
    
    private void RenderPlanetToon(){
    	GameData.CurrentRenderState = RenderState.PlanetLight;
    	PlanetLightingFBO.BindBuffer();
        
        GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.5f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
      
        
        MyGame.DrawLighting();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
    
    private void RenderFinal(){
    	GameData.CurrentRenderState = RenderState.Final;
         
         //render the texture
         ShaderHandler.FinalShader.Activate();
         
         //base texture
         GL13.glActiveTexture(GL13.GL_TEXTURE0);
         GL11.glBindTexture(GL11.GL_TEXTURE_2D, ToonFBO.fb_texture);
         int locE = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "ColourMap");
 		GL20.glUniform1i(locE, 0);
 		
 		//edge texture
 		 GL13.glActiveTexture(GL13.GL_TEXTURE1);
 		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, OutlineFBO.fb_texture);
 	     int locED = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "EdgeMap");
 	     GL20.glUniform1i(locED, 1);
 	     
 	     //Bullet base texture
 		 GL13.glActiveTexture(GL13.GL_TEXTURE2);
 		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, FinalBulletBaseFBO.fb_texture);
 	     int locEDF = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "BulletBaseMap");
 	     GL20.glUniform1i(locEDF, 2);
 	     
 	     //Bullet glow texture
 		 GL13.glActiveTexture(GL13.GL_TEXTURE3);
 		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, FinalBlurFBO.fb_texture);
 	     int locEDFG = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "GlowMap");
 	     GL20.glUniform1i(locEDFG, 3);
 	     
 	  //Bullet glow texture
 		 GL13.glActiveTexture(GL13.GL_TEXTURE4);
 		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, PlanetLightingFBO.fb_texture);
 	     int locEDFGP = GL20.glGetUniformLocation(ShaderHandler.FinalShader.shaderProgram, "PlanetMap");
 	     GL20.glUniform1i(locEDFGP, 4);
 	     
         drawTexture(0, 0, WIDTH, HEIGHT);
         ShaderHandler.FinalShader.DeActivate();
         
         GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    	
    }
    
    private void RenderUI(){
    	GL11.glLoadIdentity(); 
    	MyGame.DrawUI();
    }
    
    private void RenderDepth(){
    	GameData.CurrentRenderState = RenderState.Depth;
    	
    	DepthFBO.BindBuffer();

    	GL11.glClearColor (0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);          // Clear The Screen And The Depth Buffer
        GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
    	
        GameData.PreviousCameraPosition = GameData.CameraPosition;
        GameData.PreviousCameraRotation = GameData.CameraRotation;
        
        GameData.CameraPosition = new Vector3f(30, 0, -200.0f);
        GameData.CameraRotation = new Vector3f(0, 47, 0);
        
       // System.out.println(MyCameraMatrix);
        
        ShaderHandler.DepthShader.Activate();
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL11.glCullFace(GL11.GL_FRONT);
        MyGame.DrawDepth();
        
        GameData.SHADOWTEXTURE = DepthFBO.fb_texture;
        
        ShaderHandler.DepthShader.DeActivate();
        //GL11.glDisable(GL11.GL_CULL_FACE);
        GameData.CameraPosition =  GameData.PreviousCameraPosition;
        GameData.CameraRotation = GameData.PreviousCameraRotation;
        
    }
}




