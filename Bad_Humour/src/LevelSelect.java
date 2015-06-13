import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class LevelSelect {
	
	ArrayList<LevelSelectData> LevelSelectDataList = new ArrayList<>();
	int CurrentPosition = 0;
	int LevelNumber = 0;
	
	boolean UpDown = false;
	boolean UpReleased = false;
	
	boolean DownDown = false;
	boolean DownReleased = false;
	
	Texture Pointer;

	public LevelSelect(){
		String TempString;
		
		try {
			Pointer = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/LevelSelect.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		Scanner s = null;
		try {
			InputStream i = LevelSelect.class.getResourceAsStream("Res/LevelData/LevelList.Dat");
			s = new Scanner(new BufferedReader(new InputStreamReader(i)));
			//s = new Scanner(new BufferedReader(new FileReader("src/Res/LevelData/LevelList.Dat")));

			while (s.hasNext()) {

				TempString = s.next();
				
				if(TempString.equals("Level:")){
					LevelSelectData TempData = new LevelSelectData();
					String CheckString = null;
					TempData.LevelLoadName = s.next();
					CheckString = s.next();
					TempData.LevelDisplayName = CheckString.replaceAll("_", " ");
					TempData.LoadPostion = LevelNumber;
					LevelSelectDataList.add(TempData);
					TempData = null;
					LevelNumber++;
					System.out.println("Level Added");
				}
				
				
				}
				
			} finally {
				s.close();

			}
		}
	
	public void Update(){
		
		if(Input.GetUp() == true){
			UpDown = true;
			UpReleased = false;
		}
		else{
			UpReleased = true;
		}
		if(UpReleased == true && UpDown == true){
			CurrentPosition--;
			UpDown = false;
		}
		
		
		if(Input.GetDown() == true){
			DownDown = true;
			DownReleased = false;
		}
		else{
			DownReleased = true;
		}
		if(DownReleased == true && DownDown == true){
			CurrentPosition++;
			DownDown = false;
		}
		
		if(CurrentPosition < 0){
			CurrentPosition = LevelNumber-1;
		}
		
		if(CurrentPosition > LevelNumber-1){
			CurrentPosition = 0;
		}
		
		//System.out.println(CurrentPosition +" " + LevelNumber);
	
	}

	public void DrawLevelSelect(){
		ShaderHandler.FlatColourShader.Activate();
		//GL11.glLoadIdentity();
		drawColour(0,0,1920,1080,new Vector3f(0.52f, 0.80f,0.98f));
		ShaderHandler.FlatColourShader.DeActivate();
		
		ShaderHandler.ShipShader.Activate();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Pointer.getTextureID());
        int locE = GL20.glGetUniformLocation(ShaderHandler.ShipShader.shaderProgram, "ColourMap");
 		GL20.glUniform1i(locE, 0);
		drawTexture(0,0,1920,1080);
		
		FontHandler.ElitePro.DisplayFont(600, 1000, 2f,"Level Select");
		
		for(LevelSelectData MyData: LevelSelectDataList){
			if(MyData.LoadPostion == CurrentPosition){
				 //MyFont.Scale(0.5f, 0.5f);
				 //MyFont.Print( MyData.LevelDisplayName, -0.5f, 0f, 1.0f, 1.0f, 1.0f);
				 FontHandler.ElitePro.DisplayFont(550, 525, 1.5f, MyData.LevelDisplayName);
				 
				 if(Input.GetEnterReleased() == true){
						GameData.LevelName = MyData.LevelLoadName;
						GameData.LevelLoaded = false;
						GameData.BeginLoading = true;
					}
				 
			}
			if(MyData.LoadPostion == 0 && CurrentPosition == LevelNumber-1){
				FontHandler.ElitePro.DisplayFont(490, 405, 1f, MyData.LevelDisplayName);
			}
			
			if(MyData.LoadPostion == 1 && CurrentPosition == LevelNumber-1){
				FontHandler.ElitePro.DisplayFont(380, 285, 1f, MyData.LevelDisplayName);
			}
			
			if(MyData.LoadPostion == 0 && CurrentPosition == LevelNumber-2){
				FontHandler.ElitePro.DisplayFont(380, 285, 1f, MyData.LevelDisplayName);
			}
			
			if(MyData.LoadPostion == LevelNumber-1 && CurrentPosition == 0){
				FontHandler.ElitePro.DisplayFont(490, 645, 1f, MyData.LevelDisplayName);
			}
			
			if(MyData.LoadPostion == LevelNumber-1 && CurrentPosition == 1){
				FontHandler.ElitePro.DisplayFont(380, 765, 1f, MyData.LevelDisplayName);
			}
			
			if(MyData.LoadPostion == LevelNumber-2 && CurrentPosition == 0){
				FontHandler.ElitePro.DisplayFont(380, 765, 1f, MyData.LevelDisplayName);
			}
			
			
			if(MyData.LoadPostion == CurrentPosition-1){
				 //MyFont.Scale(0.5f, 0.5f);
				 //MyFont.Print( MyData.LevelDisplayName, -0.5f, 0.2f, 1.0f, 1.0f, 1.0f);
				 FontHandler.ElitePro.DisplayFont(480, 645, 1f, MyData.LevelDisplayName);
			}
			if(MyData.LoadPostion == CurrentPosition+1){
				 //MyFont.Scale(0.5f, 0.5f);
				 //MyFont.Print( MyData.LevelDisplayName, -0.5f, -0.2f, 1.0f, 1.0f, 1.0f);4
				 FontHandler.ElitePro.DisplayFont(480, 405, 1f, MyData.LevelDisplayName);
			}
			
			if(MyData.LoadPostion == CurrentPosition+2){
				 //MyFont.Scale(0.5f, 0.5f);
				 //MyFont.Print( MyData.LevelDisplayName, -0.5f, -0.2f, 1.0f, 1.0f, 1.0f);4
				 FontHandler.ElitePro.DisplayFont(380, 285, 1f, MyData.LevelDisplayName);
			}
			if(MyData.LoadPostion == CurrentPosition-2){
				 //MyFont.Scale(0.5f, 0.5f);
				 //MyFont.Print( MyData.LevelDisplayName, -0.5f, 0.2f, 1.0f, 1.0f, 1.0f);
				 FontHandler.ElitePro.DisplayFont(380, 765, 1f, MyData.LevelDisplayName);
			}
			
			
		}

		ShaderHandler.ShipShader.DeActivate();
	}

	private void drawColour(float x, float y, int width, int height, Vector3f Colour){
    	GL11.glBegin(GL11.GL_QUADS);
    	GL11.glColor3f(Colour.x, Colour.y, Colour.z);
    	GL11.glVertex2f(x, y);
        
    	GL11.glColor3f(Colour.x, Colour.y, Colour.z);
    	GL11.glVertex2f(x + width, y);
        
    	GL11.glColor3f(Colour.x, Colour.y, Colour.z);
    	GL11.glVertex2f(x + width, y + height);
        
    	GL11.glColor3f(Colour.x, Colour.y, Colour.z);
    	GL11.glVertex2f(x, y + height);
    	GL11.glEnd();
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

	
}
