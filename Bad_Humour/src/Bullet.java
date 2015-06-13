import java.util.ArrayList;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class Bullet {
	private Model BulletModel;
	public Vector3f Position = new Vector3f (0,0,0);
	public Vector3f StartPosition = new Vector3f (0,0,0);
	private Vector3f MovementVector = new Vector3f (0,0,0);
	private Vector3f Scale = new Vector3f (1,1,1);
	boolean SetVector = false;
	float BlurScale = 1;
	float MyRotation;
	
	boolean HasParticles = false;
	String ParticleName = null;
	
	public Vector3f Colour = new Vector3f (1,1,1);
	
	public String OrientMode = "None";
	
	Vector3f ColourTimer = new Vector3f(0,0,0);
	
	
	public Bullet(Vector3f StartPos){
		
		BulletModel = ModelHandler.FindModel("bullet", "black");
		StartPosition = new Vector3f(StartPos.x, StartPos.y, StartPos.z);
		Position = new Vector3f(StartPos.x, StartPos.y, StartPos.z);
	}
	
	public float GetScale(){
		return Scale.x;
	}
	
	public void Update(){
		
		//random changing colours
		if(Colour.x <= -10 && Colour.y <= -10 && Colour.z <= -10){
			ColourTimer.x = ColourTimer.x += 0.5f;
			ColourTimer.y = ColourTimer.y += 0.5f;
			ColourTimer.z = ColourTimer.z += 0.5f;
		}
		
		
		if(OrientMode.equals("Rotation")){
			MyRotation ++;
		}
		if(OrientMode.equals("Movement")){
			MyRotation = -MyMath.HeadingVectorToAngle(new Vector2f(MovementVector.x, MovementVector.y));
		}
		
		Position = new Vector3f(Position.x + MovementVector.x, Position.y + MovementVector.y, Position.z + MovementVector.z);
		
		if(HasParticles == true){
			//System.out.println("I Have Particles :3");
			ParticleHandler.AddParticle(ParticleName, Colour, Position, 0.1f);
		}
		//System.out.println(Position);
	}
	
	public boolean BoundaryCheck(){
		
		if (Position.y < -30){
			return true;
		}
		else if(Position.y > 30){
			return true;
		}
		else if(Position.x > 50){
			return true;
		}
		else if(Position.x < -100){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public void SetMovementVector(float MyX, float MyY, float MyZ){
		MovementVector = new Vector3f(MyX, MyY, MyZ);
		//System.out.println(MyX);
		//System.out.println(MovementVector);
	}
	
	public void Draw(float MyBlurScale){
		
		BulletModel.SetPosition(Position);
		BulletModel.SetColour(Colour);
		if(Colour.x <= -10 && Colour.y <= -10 && Colour.z <= -10){
			BulletModel.SetColour(new Vector3f((float)(Math.sin(ColourTimer.x / (180/Math.PI))),(float)(Math.sin(ColourTimer.y / (180/Math.PI))), (float)(Math.sin(ColourTimer.z / (180/Math.PI)))));
			//System.out.println("sine colour");
			if(BulletModel.Colour.x + BulletModel.Colour.y + BulletModel.Colour.z < 0){
				BulletModel.Colour.x = BulletModel.Colour.x * -1;
				BulletModel.Colour.y = BulletModel.Colour.y * -1;
				BulletModel.Colour.z = BulletModel.Colour.z * -1;
			}
		}
		//System.out.println(Colour);
		BulletModel.SetScale(Scale);
		BulletModel.SetZRotation(MyRotation);
		
		if(BlurScale == MyBlurScale){
			BulletModel.Draw();
		}
		
	}
	
	public void SetColour(Vector3f MyColour){
		
		Colour = new Vector3f(MyColour.x, MyColour.y, MyColour.z);
		if(MyColour.x < 0 && MyColour.x > -10){
			Colour.x = (float)Math.random();
		}
		if(MyColour.y < 0 && MyColour.y > -10){
			Colour.y = (float)Math.random();
		}
		if(MyColour.z < 0 && MyColour.z > -10){
			Colour.z = (float)Math.random();
		}
		if(MyColour.x <= -10 && MyColour.y <= -10 && MyColour.z <= -10){
			ColourTimer.x = (float) Math.random() * 360;
			ColourTimer.y = (float) Math.random() * 360;
			ColourTimer.z = (float) Math.random() * 360;
		}
		//System.out.println("Random Colour: " + Colour);
	}
	
	public Vector3f GetMovementVector(){
		return MovementVector;
	}
	
	public void SetScale(float MyScale){
		Scale = new Vector3f(MyScale, MyScale, MyScale);
	}
	
	public void MovementSet(){
		SetVector = true;
	}

	public void SetCustomBulletModel(String ModelName){
		BulletModel = ModelHandler.FindModel(ModelName, "black");
		//BlurScale = 0.5f;
	}

	public void SetBlurScale(float MyScale){
		BlurScale = MyScale;
	}

	public void SetOrientMode(String MyOrient){
		OrientMode = MyOrient;
	}
}
