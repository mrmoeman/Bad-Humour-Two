import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Menu {
	
	Model MyModel;
	Texture ModelAlpha;
	Texture ModelSpecular;
	
	float ModelRot = 0;
	Vector3f LightDirection = new Vector3f(-30, 0, 200.0f);
	
	public Menu(){
		
		MyModel = new Model("spacestation", "spacestation", false);
		try {
			ModelAlpha = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/spacestationalpha.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ModelSpecular = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/spacestationspecular.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MyModel.SetPosition(new Vector3f(0,0,-50));

		System.out.println("Menu Loaded");
	}
	
	public void Update(){
		ModelRot+=0.5f;
		MyModel.SetRotation(ModelRot);
		
		//TheFont.DisplayFont(0, 0, 1, "AaBbCcDdZz :;$");
	}
	
	public boolean Done(){
		if(Input.GetEnterReleased() == true){
			MyModel = null;
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	public void drawBaseMenu(){
		ShaderHandler.WhiteShader.Activate();
		MyModel.Draw();
		ShaderHandler.WhiteShader.DeActivate();
		
	}
	
	public void drawLightingMenu(){
		
		float lightPosition[] = { LightDirection.x, LightDirection.y,
				LightDirection.z, 0.0f };
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, (FloatBuffer) temp
				.asFloatBuffer().put(lightPosition).flip());
		
		ShaderHandler.SpaceStationShader.Activate();
		

		MyModel.SetMyAlpha(ModelAlpha);
		MyModel.SetMySpecular(ModelSpecular);
		MyModel.Draw();
		ShaderHandler.SpaceStationShader.DeActivate();
		
	}
	
	public void drawUI(){
		ShaderHandler.ShipShader.Activate();
		FontHandler.ElitePro.DisplayFont(1400, 900, 1.5f, "Bad Humour");
		ShaderHandler.ShipShader.DeActivate();	
	}

}
