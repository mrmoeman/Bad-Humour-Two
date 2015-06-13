import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;


public class Particle {
	
	Model ParticleModel;
	Vector4f Colour = new Vector4f(1,1,1,1);
	Vector3f Position = new Vector3f(0,0,0);
	float FadeTimer = 1;
	float FadeTime = 0;
	
	public Particle(String ModelName, Vector3f ParticleColour, Vector3f MyPosition, float MyFadeTime){
		ParticleModel = ModelHandler.FindModel(ModelName, "black");
		Position = new Vector3f(MyPosition.x, MyPosition.y, MyPosition.z);	
		FadeTime = MyFadeTime;
		Colour = new Vector4f(ParticleColour.x, ParticleColour.y, ParticleColour.z, Colour.w);
	}
	
	public void Update(){
		Colour.w = FadeTimer;
		FadeTimer -= FadeTime;
	}
	
	public void Draw(){
		
		ParticleModel.SetFourColour(Colour);
		//System.out.println(Colour);
		ParticleModel.SetPosition(Position);
		ParticleModel.SetScale(new Vector3f(1,1,1));
		ParticleModel.Draw();
		
	}
	
	public boolean CheckForRemove(){
		if(FadeTimer < 0){
			return true;
		}
		else{
			return false;
		}
		
	}

}
