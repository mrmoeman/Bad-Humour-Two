import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class DialogueHandler {
	
	Texture DialogueBoxTexture;
	Texture CharacterBoxes;
	
	ArrayList<DialogueEvent> DialogueList = new ArrayList<>();
	
	int SequenceCount = 1;
	
	boolean EnterDown = true;
	boolean EnterReleased = false;
	
	public DialogueHandler(){
		
		try {
			DialogueBoxTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/chardialogbox.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			CharacterBoxes = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/CharDialog.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Dialogue Handler Created");
		
	}
	
	public void Update(int CurrentWave){
		

		if(Input.GetEnterReleased() == true){
			SequenceCount++;
			//EnterDown = false;
		}
		
		
		SequenceCheck();
	}
	
	public void DrawDialogue(int CurrentWave){
		
		ShaderHandler.ShipShader.Activate();
		
		 for(DialogueEvent MyDialogueEvent: DialogueList){
			 if(MyDialogueEvent.Wave == CurrentWave && MyDialogueEvent.Sequence == SequenceCount){
			 
			 GL11.glLoadIdentity();
			 	if(MyDialogueEvent.LeftState > 0){
			 		GL13.glActiveTexture(GL13.GL_TEXTURE0);
			 		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CharacterBoxes.getTextureID());
			 		drawCharacterleft(68, 66, 240, 240, MyDialogueEvent.CharacterLeft.x, MyDialogueEvent.CharacterLeft.y);
			 	}
				
			 	if(MyDialogueEvent.RightState > 0){
			 		GL11.glLoadIdentity();
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, CharacterBoxes.getTextureID());
					drawCharacterright(1612, 66, 240, 240, MyDialogueEvent.CharacterRight.x, MyDialogueEvent.CharacterRight.y);
			 	}

				if (MyDialogueEvent.LeftState == 1){
					GL11.glLoadIdentity();
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
					drawLeftTalk(50,50,700,272);
				}
				if (MyDialogueEvent.LeftState == 2){
					GL11.glLoadIdentity();
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
					drawLeftListen(50,50,284,272);
				}
				
				
				if (MyDialogueEvent.RightState == 1){
					GL11.glLoadIdentity();
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
					drawRightTalk(1170,50,700,272);
				}

				if (MyDialogueEvent.RightState == 2){
					GL11.glLoadIdentity();
					GL13.glActiveTexture(GL13.GL_TEXTURE0);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
					drawRightListen(1586,50,284,272);
				}
				
				if(MyDialogueEvent.LeftState == 1){
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.CharacterNameLeft.replace("_", " "), -0.64f, -0.865f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(335, 65, 0.5f, MyDialogueEvent.CharacterNameLeft.replace("_", " "));
					
					
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.LeftLineOne, -0.675f, -0.7f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(312, 153, 0.5f, MyDialogueEvent.LeftLineOne);
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.LeftLineTwo, -0.675f, -0.75f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(312, 128, 0.5f, MyDialogueEvent.LeftLineTwo);
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.LeftLineThree, -0.675f, -0.8f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(312, 102, 0.5f, MyDialogueEvent.LeftLineThree);
				}
				
				if(MyDialogueEvent.RightState == 1){
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.CharacterNameRight.replace("_", " "), 0.455f, -0.865f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(1386, 65, 0.5f, MyDialogueEvent.CharacterNameRight.replace("_", " "));
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.RightLineOne, 0.232f, -0.7f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(1180, 153, 0.5f, MyDialogueEvent.RightLineOne);
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.RightLineTwo, 0.232f, -0.75f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(1180, 128, 0.5f, MyDialogueEvent.RightLineTwo);
					//MyFont.Scale(0.35f, 0.35f);
					//MyFont.Print(MyDialogueEvent.RightLineThree, 0.232f, -0.8f, 1f, 1f, 1f);
					FontHandler.ElitePro.DisplayFont(1180, 102, 0.5f, MyDialogueEvent.RightLineThree);
				}
			 
			 }
		 }
		
		
		//DrawDialogueBoxes(1,2);
		
		ShaderHandler.ShipShader.DeActivate();
		
	}
	
	private void DrawDialogueBoxes(int talkingleft, int talkingright){
		//0 - no one
		//1 - talking
		//2 - listening
		
		GL11.glLoadIdentity();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CharacterBoxes.getTextureID());
		drawCharacterleft(68, 66, 240, 240, 3, 4);
		
		GL11.glLoadIdentity();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, CharacterBoxes.getTextureID());
		drawCharacterright(1612, 66, 240, 240, 3, 3);

		if (talkingleft == 1){
			GL11.glLoadIdentity();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
			drawLeftTalk(50,50,700,272);
		}
		if (talkingleft == 2){
			GL11.glLoadIdentity();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
			drawLeftListen(50,50,284,272);
		}
		
		
		if (talkingright == 1){
			GL11.glLoadIdentity();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
			drawRightTalk(1170,50,700,272);
		}

		if (talkingright == 2){
			GL11.glLoadIdentity();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, DialogueBoxTexture.getTextureID());
			drawRightListen(1586,50,284,272);
		}
		
	}
	
	 private void drawLeftTalk(float x, float y, int width, int height){
	    	GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0.0078f, 1-0.269f);
	    	GL11.glVertex2f(x, y);
	        
	    	GL11.glTexCoord2f(0.691f, 1-0.269f);
	    	GL11.glVertex2f(x + width, y);
	        
	    	GL11.glTexCoord2f(0.691f, 1-0.0039f);
	    	GL11.glVertex2f(x + width, y + height);
	        
	    	GL11.glTexCoord2f(0.0078f, 1-0.0039f);
	    	GL11.glVertex2f(x, y + height);
	    	GL11.glEnd();
	     }
	 
	 private void drawRightTalk(float x, float y, int width, int height){
	    	GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0.691f, 1-0.269f);
	    	GL11.glVertex2f(x, y);
	        
	    	GL11.glTexCoord2f(0.0078f, 1-0.269f);
	    	GL11.glVertex2f(x + width, y);
	        
	    	GL11.glTexCoord2f(0.0078f, 1-0.0039f);
	    	GL11.glVertex2f(x + width, y + height);
	        
	    	GL11.glTexCoord2f(0.691f, 1-0.0039f);
	    	GL11.glVertex2f(x, y + height);
	    	GL11.glEnd();
	     }

	 private void drawLeftListen(float x, float y, int width, int height){
		 GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0.705f, 1-0.269f);
	    	GL11.glVertex2f(x, y);
	        
	    	GL11.glTexCoord2f(0.982f, 1-0.269f);
	    	GL11.glVertex2f(x + width, y);
	        
	    	GL11.glTexCoord2f(0.982f, 1-0.0039f);
	    	GL11.glVertex2f(x + width, y + height);
	        
	    	GL11.glTexCoord2f(0.705f, 1-0.0039f);
	    	GL11.glVertex2f(x, y + height);
	    	GL11.glEnd();
	     }

	 private void drawRightListen(float x, float y, int width, int height){
		 GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0.982f, 1-0.269f);
	    	GL11.glVertex2f(x, y);
	        
	    	GL11.glTexCoord2f(0.705f, 1-0.269f);
	    	GL11.glVertex2f(x + width, y);
	        
	    	GL11.glTexCoord2f(0.705f, 1-0.0039f);
	    	GL11.glVertex2f(x + width, y + height);
	        
	    	GL11.glTexCoord2f(0.982f, 1-0.0039f);
	    	GL11.glVertex2f(x, y + height);
	    	GL11.glEnd();
	     }

	 private void drawCharacterleft(float x, float y, int width, int height, float gridx, float gridy){
	    	GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(0.117f * (gridx-1), 1-(0.117f * gridy));
	    	GL11.glVertex2f(x, y);
	        
	    	GL11.glTexCoord2f((0.117f * gridx), 1-(0.117f * gridy));
	    	GL11.glVertex2f(x + width, y);
	        
	    	GL11.glTexCoord2f((0.117f * gridx), 1f-(0.117f * (gridy - 1)));
	    	GL11.glVertex2f(x + width, y + height);
	        
	    	GL11.glTexCoord2f(0.117f * (gridx-1), 1f-(0.117f * (gridy - 1)));
	    	GL11.glVertex2f(x, y + height);
	    	GL11.glEnd();
	     }
	 
	 private void drawCharacterright(float x, float y, int width, int height, float gridx, float gridy){
	    	GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f((0.117f * gridx), 1-(0.117f * gridy));
	    	GL11.glVertex2f(x, y);
	        
	    	GL11.glTexCoord2f(0.117f * (gridx-1), 1-(0.117f * gridy));
	    	GL11.glVertex2f(x + width, y);
	        
	    	GL11.glTexCoord2f(0.117f * (gridx-1), 1f-(0.117f * (gridy - 1)));
	    	GL11.glVertex2f(x + width, y + height);
	        
	    	GL11.glTexCoord2f((0.117f * gridx), 1f-(0.117f * (gridy - 1)));
	    	GL11.glVertex2f(x, y + height);
	    	GL11.glEnd();
	     }
	 
	 public void AddDialogueEvent(DialogueEvent MyDialogueEvent){
		 DialogueList.add(MyDialogueEvent);
		 System.out.println("Dialogue Event Added");
	 }

	 public boolean WaveComplete(int CurrentWave){
		 int EventsLeft = 0;
		 for(DialogueEvent MyDialogueEvent: DialogueList){
			 if(MyDialogueEvent.Wave == CurrentWave && MyDialogueEvent.Done == false){
				 EventsLeft++;
			 }
		 }
		 if(EventsLeft > 0){
			 return false;
		 }
		 else{
			 return true;
		 }
	 }
	 
	 private void SequenceCheck(){
		 int SequencesLeft = 0;
		 for(DialogueEvent MyDialogueEvent: DialogueList){
			 if(MyDialogueEvent.Sequence < SequenceCount){
				 MyDialogueEvent.Done = true;
			 }
		 }
	 }
}
