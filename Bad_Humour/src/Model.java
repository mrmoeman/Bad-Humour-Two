import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Model {
	
	int Temp;
	int TempB;
	int TempC;
	
	float VertArray[][] = new float [2000000][3];
	float TextArray[][] = new float [2000000][2];
	float NormArray[][] = new float [2000000][3];
	int FaceArray[][] = new int [200000][10];
	
	//float FinVertArray[][];
	//float FinTextArray[][];
	//float FinNormArray[][];
	//int FinfaceArray[][];
	float FinVertArray[];
	float FinTextArray[];
	float FinNormArray[];
	int FinfaceArray[];
	/////////////////////////////////VBO STUFF
	FloatBuffer VertBuffer;
	FloatBuffer NormBuffer;
	FloatBuffer TextBuffer;
	IntBuffer FaceBuffer;
	
	int vbo_vertex_handle;
	int vbo_normal_handle;
	int vbo_texture_handle;
	
	Vector3f VA = new Vector3f();
	Vector3f VB= new Vector3f();
	Vector3f VC= new Vector3f();
	Vector3f VD= new Vector3f();
	
	Vector3f Movement = new Vector3f(0,0,0);
	
	Vector3f Colour = new Vector3f(1,1,1);
	Vector4f FourColour = new Vector4f(1,1,1,1);
	
	Vector3f Position= new Vector3f(0,0,0);
	Vector3f Offset = new Vector3f(0,0,0);
	float Orbit = 0;
	
	Vector3f Scale= new Vector3f(1,1,1);
	
	float Shininess = 70;
	
	float Transparency = 0;
	
	float Rotation;
	float ZRotation = 0;
	
	boolean Collis;
	boolean ShowModel = true;
	boolean TopDown = false;
	
	boolean UseAlpha;
	Texture AlphaMap;
	
	boolean HasAlpha;
	Texture MyAlphaMap;
	
	boolean HasSpecular;
	Texture MySpecularMap;
	
	int Faces = 0;
	
	Texture texture;
	
	String ModelName;
	String TextureName;
	
	int Reference = 0;
	
	FloatBuffer modelview;
	FloatBuffer projection;
	
	public Model(){
	
	}
			
    public Model(String ObjName, boolean Collision){
    	
    	ModelName = ObjName;
    	TextureName = ObjName;
    	
    	Collis = Collision;
    	Scanner s = null;
    	int Vertice = 0;
    	int Normals = 0;
    	int Texts = 0;
    	String teststring;
    	String FileOpen;
    	
    	modelview = BufferUtils.createFloatBuffer(16);
    	projection = BufferUtils.createFloatBuffer(16);
    	

    	FileOpen = "Res/OBJ/" + ObjName + ".obj";
    	
    	try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/" + ObjName +".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	InputStream i = Model.class.getResourceAsStream(FileOpen);
			s = new Scanner(new BufferedReader(new InputStreamReader(i)));
            //s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
            s.useDelimiter("[\\/\\s+]");
            while (s.hasNext()) {
            	teststring = s.next();
            	if (teststring.equals("v")){
            		Vertice++;
            		VertArray[Vertice - 1][0] = s.nextFloat();
            		VertArray[Vertice - 1][1] = s.nextFloat();
            		VertArray[Vertice - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("vt")){
            		Texts++;
            		if (Texts == 1){
            		}
            		TextArray[Texts - 1][0] = s.nextFloat();
            		TextArray[Texts - 1][1] = s.nextFloat();
            	}
            	if (teststring.equals("vn")){
            		Normals++;
            		if (Normals == 1){
            		}
            		NormArray[Normals - 1][0] = s.nextFloat();
            		NormArray[Normals - 1][1] = s.nextFloat();
            		NormArray[Normals - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("f")){
            		Faces++;
            		FaceArray[Faces - 1][0] = s.nextInt();
            		FaceArray[Faces - 1][1] = s.nextInt();
            		FaceArray[Faces - 1][2] = s.nextInt();
            		FaceArray[Faces - 1][3] = s.nextInt();
            		FaceArray[Faces - 1][4] = s.nextInt();
            		FaceArray[Faces - 1][5] = s.nextInt();
            		FaceArray[Faces - 1][6] = s.nextInt();
            		FaceArray[Faces - 1][7] = s.nextInt();
            		FaceArray[Faces - 1][8] = s.nextInt();
            	}
            	
            }
        } finally {
        	s.close();
        	
        	
        	FinVertArray = new float[Faces * 9];
        	FinNormArray = new float[Faces * 9];
        	FinTextArray = new float[Faces * 6];
        	//System.out.println(Faces);
        	for(int i = 0; i < Faces; i ++){
        		
        		//System.out.println();
        		
        		FinVertArray[i*9] = VertArray[FaceArray[i][0] - 1][0];
        		FinVertArray[i*9+1] = VertArray[FaceArray[i][0] - 1][1];
        		FinVertArray[i*9+2] = VertArray[FaceArray[i][0] - 1][2];
        		
        		FinVertArray[i*9+3] = VertArray[FaceArray[i][3] - 1][0];
        		FinVertArray[i*9+4] = VertArray[FaceArray[i][3] - 1][1];
        		FinVertArray[i*9+5] = VertArray[FaceArray[i][3] - 1][2];
        		
        		FinVertArray[i*9+6] = VertArray[FaceArray[i][6] - 1][0];
        		FinVertArray[i*9+7] = VertArray[FaceArray[i][6] - 1][1];
        		FinVertArray[i*9+8] = VertArray[FaceArray[i][6] - 1][2];
        		
        		FinNormArray[i*9] = NormArray[FaceArray[i][1] - 1][0];
        		FinNormArray[i*9+1] = NormArray[FaceArray[i][1] - 1][1];
        		FinNormArray[i*9+2] = NormArray[FaceArray[i][1] - 1][2];
        		
        		FinNormArray[i*9+3] = NormArray[FaceArray[i][4] - 1][0];
        		FinNormArray[i*9+4] = NormArray[FaceArray[i][4] - 1][1];
        		FinNormArray[i*9+5] = NormArray[FaceArray[i][4] - 1][2];
        		
        		FinNormArray[i*9+6] = NormArray[FaceArray[i][7] - 1][0];
        		FinNormArray[i*9+7] = NormArray[FaceArray[i][7] - 1][1];
        		FinNormArray[i*9+8] = NormArray[FaceArray[i][7] - 1][2];
        		
        		FinTextArray[i*6] = TextArray[FaceArray[i][2] - 1][0];
        		FinTextArray[i*6+1] = TextArray[FaceArray[i][2] - 1][1] * -1;
        		
        		FinTextArray[i*6+2] = TextArray[FaceArray[i][5] - 1][0];
        		FinTextArray[i*6+3] = TextArray[FaceArray[i][5] - 1][1] * -1;
        		
        		FinTextArray[i*6+4] = TextArray[FaceArray[i][8] - 1][0];
        		FinTextArray[i*6+5] = TextArray[FaceArray[i][8] - 1][1] * -1;
        		
        	}
        	
        	VertArray = null;
        	NormArray = null;
        	TextArray = null;
        	FaceArray = null;
        	
        	VertBuffer = BufferUtils.createFloatBuffer(Faces * 9);
	        NormBuffer = BufferUtils.createFloatBuffer(Faces * 9);
	        TextBuffer = BufferUtils.createFloatBuffer(Faces * 6);
	        
	        VertBuffer.put(FinVertArray);
	        NormBuffer.put(FinNormArray);
	        TextBuffer.put(FinTextArray);
	        
	        VertBuffer.rewind();
	        NormBuffer.rewind();
	        TextBuffer.rewind();
	        
	        vbo_vertex_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	        vbo_normal_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	        vbo_texture_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, TextBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        	
        }
    }
    
    
    
    public Model(String ObjName, String Texture, boolean Collision){
    	
    	ModelName = ObjName;
    	TextureName = Texture;
    	
    	Collis = Collision;
    	Scanner s = null;
    	int Vertice = 0;
    	int Normals = 0;
    	int Texts = 0;
    	String teststring;
    	String FileOpen;

    	FileOpen = "Res/OBJ/" + ObjName + ".obj";
    	
    	modelview = BufferUtils.createFloatBuffer(16);
    	projection = BufferUtils.createFloatBuffer(16);
    	
    	try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/" + Texture +".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	InputStream i = Model.class.getResourceAsStream(FileOpen);
			s = new Scanner(new BufferedReader(new InputStreamReader(i)));
            //s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
            s.useDelimiter("[\\/\\s+]");
            while (s.hasNext()) {
            	teststring = s.next();
            	if (teststring.equals("v")){
            		Vertice++;
            		VertArray[Vertice - 1][0] = s.nextFloat();
            		VertArray[Vertice - 1][1] = s.nextFloat();
            		VertArray[Vertice - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("vt")){
            		Texts++;
            		if (Texts == 1){
            		}
            		TextArray[Texts - 1][0] = s.nextFloat();
            		TextArray[Texts - 1][1] = s.nextFloat();
            	}
            	if (teststring.equals("vn")){
            		Normals++;
            		if (Normals == 1){
            		}
            		NormArray[Normals - 1][0] = s.nextFloat();
            		NormArray[Normals - 1][1] = s.nextFloat();
            		NormArray[Normals - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("f")){
            		Faces++;
            		FaceArray[Faces - 1][0] = s.nextInt();
            		FaceArray[Faces - 1][1] = s.nextInt();
            		FaceArray[Faces - 1][2] = s.nextInt();
            		FaceArray[Faces - 1][3] = s.nextInt();
            		FaceArray[Faces - 1][4] = s.nextInt();
            		FaceArray[Faces - 1][5] = s.nextInt();
            		FaceArray[Faces - 1][6] = s.nextInt();
            		FaceArray[Faces - 1][7] = s.nextInt();
            		FaceArray[Faces - 1][8] = s.nextInt();
            	}
            	
            	
            	//LoadScreen.ShowLoad();
            	
            }
        } finally {
        	s.close();
        	//s.close();
        	
        	
        	FinVertArray = new float[Faces * 9];
        	FinNormArray = new float[Faces * 9];
        	FinTextArray = new float[Faces * 6];
        	System.out.println(Faces);
        	for(int i = 0; i < Faces; i ++){
        		
        		//System.out.println();
        		
        		FinVertArray[i*9] = VertArray[FaceArray[i][0] - 1][0];
        		FinVertArray[i*9+1] = VertArray[FaceArray[i][0] - 1][1];
        		FinVertArray[i*9+2] = VertArray[FaceArray[i][0] - 1][2];
        		//System.out.println(VertArray[FaceArray[i][3] - 1][0]);
        		FinVertArray[i*9+3] = VertArray[FaceArray[i][3] - 1][0];
        		FinVertArray[i*9+4] = VertArray[FaceArray[i][3] - 1][1];
        		FinVertArray[i*9+5] = VertArray[FaceArray[i][3] - 1][2];
        		
        		FinVertArray[i*9+6] = VertArray[FaceArray[i][6] - 1][0];
        		FinVertArray[i*9+7] = VertArray[FaceArray[i][6] - 1][1];
        		FinVertArray[i*9+8] = VertArray[FaceArray[i][6] - 1][2];
        		
        		FinNormArray[i*9] = NormArray[FaceArray[i][1] - 1][0];
        		FinNormArray[i*9+1] = NormArray[FaceArray[i][1] - 1][1];
        		FinNormArray[i*9+2] = NormArray[FaceArray[i][1] - 1][2];
        		
        		FinNormArray[i*9+3] = NormArray[FaceArray[i][4] - 1][0];
        		FinNormArray[i*9+4] = NormArray[FaceArray[i][4] - 1][1];
        		FinNormArray[i*9+5] = NormArray[FaceArray[i][4] - 1][2];
        		
        		FinNormArray[i*9+6] = NormArray[FaceArray[i][7] - 1][0];
        		FinNormArray[i*9+7] = NormArray[FaceArray[i][7] - 1][1];
        		FinNormArray[i*9+8] = NormArray[FaceArray[i][7] - 1][2];
        		
        		FinTextArray[i*6] = TextArray[FaceArray[i][2] - 1][0];
        		FinTextArray[i*6+1] = TextArray[FaceArray[i][2] - 1][1] * -1;
        		
        		FinTextArray[i*6+2] = TextArray[FaceArray[i][5] - 1][0];
        		FinTextArray[i*6+3] = TextArray[FaceArray[i][5] - 1][1] * -1;
        		
        		FinTextArray[i*6+4] = TextArray[FaceArray[i][8] - 1][0];
        		FinTextArray[i*6+5] = TextArray[FaceArray[i][8] - 1][1] * -1;
        		
        	}
        	
        	VertArray = null;
        	NormArray = null;
        	TextArray = null;
        	FaceArray = null;
        	
        	VertBuffer = BufferUtils.createFloatBuffer(Faces * 9);
	        NormBuffer = BufferUtils.createFloatBuffer(Faces * 9);
	        TextBuffer = BufferUtils.createFloatBuffer(Faces * 6);
	        
	        VertBuffer.put(FinVertArray);
	        NormBuffer.put(FinNormArray);
	        TextBuffer.put(FinTextArray);
	        
	        VertBuffer.rewind();
	        NormBuffer.rewind();
	        TextBuffer.rewind();
	        
	        vbo_vertex_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, VertBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	        vbo_normal_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, NormBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	        
	        vbo_texture_handle = GL15.glGenBuffers();
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
	        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, TextBuffer, GL15.GL_STATIC_DRAW);
	        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        	
        
        	
        }
    }
    
    public void SetPosition(Vector3f Pos){
    	Position = Pos;
    }
    
    public void SetRotation(float rotation){
    	Rotation = rotation;
    }
    
    public void SetZRotation(float rotation){
    	ZRotation = rotation;
    }
    
    public void SetShiniess(float shiny){
    	Shininess = shiny;
    }
    
    public void Draw(){
    	
    	GL11.glEnable( GL11.GL_TEXTURE_2D); 
		
    	GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
    	
    	
    	//CAMERA
    	GL11.glTranslatef(GameData.CameraPosition.x, GameData.CameraPosition.y, GameData.CameraPosition.z);
    	GL11.glRotatef(GameData.CameraRotation.z, 0, 0, 1);
    	GL11.glRotatef(GameData.CameraRotation.x, 1, 0, 0);
    	GL11.glRotatef(GameData.CameraRotation.y, 0, 1, 0);
    	
    	
    	
    	GL11.glTranslatef(Position.x, Position.y, Position.z);
    	
    	GL11.glRotatef(Orbit, 0, 1, 0);
    	//System.out.println(Orbit);
    	
    	if(TopDown == true){
    		GL11.glRotatef(90, 1, 0, 0);
    		GL11.glRotatef(90, 0, 1, 0);
    	}
    	//MODEL
    	GL11.glTranslatef(Offset.x, Offset.y, Offset.z);
    	 
    	GL11.glRotatef(Rotation, 0, 1, 0);
    	
    	GL11.glRotatef(ZRotation, 0, 0, 1);
    	
    	GL11.glScalef(Scale.x, Scale.y, Scale.z);
    
    	
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		 
		int loc = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ColourMap");
		 
		GL20.glUniform1i(loc, 0);
		//System.out.println(loc);
		
		if(UseAlpha == true){
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
	    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, AlphaMap.getTextureID());
			int locA = GL20.glGetUniformLocation(ShaderHandler.PlanetShader.shaderProgram, "AlphaMap");
			GL20.glUniform1i(locA, 1);
		}
		
		if(HasAlpha == true){
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
	    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, MyAlphaMap.getTextureID());
			int locA = GL20.glGetUniformLocation(ShaderHandler.SpaceStationShader.shaderProgram, "AlphaMap");
			GL20.glUniform1i(locA, 1);
		}
		
		if(HasSpecular == true){
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
	    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, MySpecularMap.getTextureID());
			int locS = GL20.glGetUniformLocation(ShaderHandler.SpaceStationShader.shaderProgram, "SpecularMap");
			GL20.glUniform1i(locS, 2);
			//System.out.println("SPECULAR");
		}
		
		int locC = GL20.glGetUniformLocation(ShaderHandler.BulletBase.shaderProgram, "Colour");
		GL20.glUniform3f(locC, Colour.x, Colour.y, Colour.z);
		
		int locCP = GL20.glGetUniformLocation(ShaderHandler.ParticleShader.shaderProgram, "color");
		GL20.glUniform4f(locCP, FourColour.x, FourColour.y, FourColour.z, FourColour.w);
		 
		 //int locc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "PlayerShadow");
		 
		 //int loct = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "Transparency");
		 
		 
		 //GL20.glUniform1f(loct, Transparency);
		 
		 
		 /*if (AllowPlayerShadow == true && GameData.ScreenShootMode == false){
			 GL20.glUniform1i(locc,1);
		 }
		 else{
			 GL20.glUniform1i(locc,0);
		 }*/
		
		GL13.glActiveTexture(GL13.GL_TEXTURE8);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, GameData.SHADOWTEXTURE);
        
        int locSM = GL20.glGetUniformLocation(ShaderHandler.SpecularShader.shaderProgram, "ShadowMap");
		GL20.glUniform1i(locSM, 8);
		int locSMP = GL20.glGetUniformLocation(ShaderHandler.PlanetShader.shaderProgram, "ShadowMap");
		GL20.glUniform1i(locSMP, 8);
		
		
		//shadow
		int locMM= GL20.glGetUniformLocation(ShaderHandler.SpecularShader.shaderProgram, "ModelMatrix4x4");
	     GL20.glUniformMatrix4(locMM,false,modelview);
	     
	     int locPM= GL20.glGetUniformLocation(ShaderHandler.SpecularShader.shaderProgram, "ProjectionMatrix4x4");
	     GL20.glUniformMatrix4(locPM,false,projection);
	     
	     int locMMP= GL20.glGetUniformLocation(ShaderHandler.PlanetShader.shaderProgram, "ModelMatrix4x4");
	     GL20.glUniformMatrix4(locMM,false,modelview);
	     
	     int locPMP= GL20.glGetUniformLocation(ShaderHandler.PlanetShader.shaderProgram, "ProjectionMatrix4x4");
	     GL20.glUniformMatrix4(locPM,false,projection);
		
		
		if(GameData.CurrentRenderState == RenderState.Depth){
	     GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
	     //modelview.flip();
	     
	     GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
	     //projection.flip();
	     
		}
    	
    	if (ShowModel == true){
    		/*
    	 GL11.glBegin(GL11.GL_TRIANGLES);                    // Drawing Using Triangles
         GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, Shininess);
         
         Temp = FinfaceArray[i][0]-1;
         TempB = FinfaceArray[i][1]-1;
         TempC = FinfaceArray[i][2]-1;
         
         GL11.glTexCoord2f(FinTextArray[TempC][0] ,FinTextArray[TempC][1]*-1);
         GL11.glNormal3f( FinNormArray[TempB][0], FinNormArray[TempB][1],FinNormArray[TempB][2]);         // Bottom Right
         GL11.glVertex3f( FinVertArray[Temp][0], FinVertArray[Temp][1], FinVertArray[Temp][2]);         // Bottom Right
        
         Temp = FinfaceArray[i][3]-1;
         TempB = FinfaceArray[i][4]-1;
         TempC = FinfaceArray[i][5]-1;
         
         GL11.glTexCoord2f(FinTextArray[TempC][0],FinTextArray[TempC][1]*-1);
         GL11.glNormal3f( FinNormArray[TempB][0], FinNormArray[TempB][1], FinNormArray[TempB][2]);  
         GL11.glVertex3f( FinVertArray[Temp][0], FinVertArray[Temp][1], FinVertArray[Temp][2]);        // Bottom Right
         
         Temp = FinfaceArray[i][6]-1;
         TempB = FinfaceArray[i][7]-1;
         TempC = FinfaceArray[i][8]-1;
         
         GL11.glTexCoord2f(FinTextArray[TempC][0],FinTextArray[TempC][1]*-1);
         GL11.glNormal3f( FinNormArray[TempB][0], FinNormArray[TempB][1], FinNormArray[TempB][2]);  
         GL11.glVertex3f( FinVertArray[Temp][0], FinVertArray[Temp][1], FinVertArray[Temp][2]);        // Bottom Right
         GL11.glEnd();   */
    		
    	GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
  		  GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
  		  GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
  		  
  		  GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_vertex_handle);
  		  GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);

  		  GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_normal_handle);
  		  GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
  		  
  		  GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, vbo_texture_handle);
  		  GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);


  		  GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, Faces*3);
         
    	}

         
         //GL11.glDisable(GL11.GL_ALPHA_TEST);
         
    	}
    	
    	//UseAlpha = false;
    //}
    
    public void SetReference(int Ref){
    	Reference = Ref;
    }
    
    public void SetScale(Vector3f MyScale){
    	Scale = MyScale;
    }
    
    public void SetTransparency(float transparency){
    	Transparency = 1 - transparency;
    }
    
    public int GetReference(){
    	return Reference;
    }
    
    public void AlphaClear(){
    	
    	GL11.glEnable(GL11.GL_ALPHA_TEST);
    	GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
    	
    }
    
    public void ShowModel(Boolean State){
    	ShowModel = State;
    }
    
    public void SetMovement (Vector3f movement){
    	Movement = movement;
    }
    
    public void SetOffset (Vector3f offset){
    	Offset = offset;
    }
    
    public void SetTopDown(boolean state){
    	TopDown = state;
    }
    
    public void SetOrbit(float OrbitSpeed){
    	Orbit = OrbitSpeed;
    }
    
    public String GetModelName(){
    	return ModelName;
    }
    
    public String GetTextureName(){
    	return TextureName;
    }
    
    public void SetAlpha(Texture MyAlpha){
    	AlphaMap = MyAlpha;
    	UseAlpha = true;
    }
    
    public void SetMyAlpha(Texture MyAlpha){
    	MyAlphaMap = MyAlpha;
    	HasAlpha = true;
    }
    
    public void SetMySpecular(Texture MySpecular){
    	MySpecularMap = MySpecular;
    	HasSpecular = true;
    }
    
    public void SetColour(Vector3f MyColour){
    	Colour = MyColour;
    }
    
    public void SetFourColour(Vector4f MyColour){
    	FourColour = MyColour;
    }
    
 }


