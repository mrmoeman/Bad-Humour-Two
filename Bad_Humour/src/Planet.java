import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Planet extends Entity {
	
	private Texture MyAlpha;
	private float MyOrbit;

	public Planet(float startx, float starty, float startz, String ModelName, String TextureName) {
		super(startx, starty, startz, ModelName, TextureName);
		
		try {
			MyAlpha = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("Res/Textures/" + TextureName +"Alpha.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Override
	public void Update(){
		MyRot++;
		MyOrbit+= Orbit;
		Position.x -= 0.01f;
	}
	
	@Override
	public void Draw(){
		if(GameData.CurrentRenderState == RenderState.PlanetLight || GameData.CurrentRenderState == RenderState.Depth){
			
			MyModel.SetAlpha(MyAlpha);
			MyModel.SetScale(new Vector3f(Scale.x * 2, Scale.y * 2, Scale.z * 2));
			MyModel.SetPosition(Position);
			MyModel.SetRotation(MyRot);
			MyModel.SetOffset(new Vector3f(Offset.x , Offset.y, Offset.z));
			MyModel.SetOrbit(MyOrbit);
			MyModel.Draw();
		}
		
	}
	
	
}
