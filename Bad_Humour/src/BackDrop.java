import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;


public class BackDrop extends Entity {
	
	Model WaterModel;
	Model WaterModelA;
	Model WaterModelB;
	Model WaterModelC;
	Model WaterModelD;
	boolean HasWater = false;
	
	Model LandModel;
	Model LandModelA;
	Model LandModelB;
	Model LandModelC;
	Model LandModelD;

	public BackDrop(String ModName, int water) {
		super(0, -5, -80, ModName);
		MyModel = null;
		if(water == 1){
			HasWater = true;
		}
		
		LandModel = new Model(ModName, ModName, false);
		LandModelA = new Model(ModName, ModName, false);
		LandModelB = new Model(ModName, ModName, false);
		LandModelC = new Model(ModName, ModName, false);
		LandModelD = new Model(ModName, ModName, false);
		LandModel.SetTopDown(true);
		LandModelA.SetTopDown(true);
		LandModelB.SetTopDown(true);
		LandModelC.SetTopDown(true);
		LandModelD.SetTopDown(true);
		
		
		WaterModel = new Model("water", "water", false);
		WaterModelA = new Model("water", "water", false);
		WaterModelB = new Model("water", "water", false);
		WaterModelC = new Model("water", "water", false);
		WaterModelD = new Model("water", "water", false);
		WaterModel.SetTopDown(true);
		WaterModelA.SetTopDown(true);
		WaterModelB.SetTopDown(true);
		WaterModelC.SetTopDown(true);
		WaterModelD.SetTopDown(true);
		
		Scale = new Vector3f(8,8,8);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Update(){
		Position.x -= 0.2f;
		if(Position.x <= -(8*Scale.x)){
			Position.x = 0;
		}
	}
	
	@Override
	public void Draw(){
		if(DontDraw() == false){
			LandModel.SetRotation(-90);
			LandModel.SetScale(Scale);
			LandModelA.SetRotation(-90);
			LandModelA.SetScale(Scale);
			LandModelB.SetRotation(-90);
			LandModelB.SetScale(Scale);
			LandModelC.SetRotation(-90);
			LandModelC.SetScale(Scale);
			LandModelD.SetRotation(-90);
			LandModelD.SetScale(Scale);
			
			int locSM = GL20.glGetUniformLocation(ShaderHandler.SpecularShader.shaderProgram, "Alpha");
			GL20.glUniform1f(locSM, 1);
			
			
			LandModel.SetPosition(new Vector3f(Position.x + 8*Scale.x, Position.y, Position.z));
			LandModel.Draw();
			LandModelA.SetPosition(new Vector3f(Position.x - 8*Scale.x, Position.y, Position.z));
			LandModelA.Draw();
			LandModelB.SetPosition(new Vector3f(Position.x + 16*Scale.x, Position.y, Position.z));
			LandModelB.Draw();
			LandModelD.SetPosition(new Vector3f(Position.x + 24*Scale.x, Position.y, Position.z));
			LandModelD.Draw();
			LandModelC.SetPosition(Position);
			LandModelC.Draw();
			
			if(HasWater == true){
			WaterModel.SetRotation(-90);
			WaterModel.SetScale(Scale);
			WaterModelA.SetRotation(-90);
			WaterModelA.SetScale(Scale);
			WaterModelB.SetRotation(-90);
			WaterModelB.SetScale(Scale);
			WaterModelC.SetRotation(-90);
			WaterModelC.SetScale(Scale);
			
			GL20.glUniform1f(locSM, 0.5f);
			
			WaterModel.SetPosition(new Vector3f(Position.x + 8*Scale.x, Position.y, Position.z));
			WaterModel.Draw();
			WaterModelA.SetPosition(new Vector3f(Position.x - 8*Scale.x, Position.y, Position.z));
			WaterModelA.Draw();
			WaterModelB.SetPosition(new Vector3f(Position.x + 16*Scale.x, Position.y, Position.z));
			WaterModelB.Draw();
			WaterModelC.SetPosition(Position);
			WaterModelC.Draw();
			WaterModelD.SetPosition(new Vector3f(Position.x + 24*Scale.x, Position.y, Position.z));
			WaterModelD.Draw();
			}
		}
	}
	
	@Override
	public void SetBoss(){
		if(DontDraw() == false){
			LandModel.SetRotation(-90);
			LandModel.SetScale(Scale);
			LandModelA.SetRotation(-90);
			LandModelA.SetScale(Scale);
			LandModelB.SetRotation(-90);
			LandModelB.SetScale(Scale);
			LandModelC.SetRotation(-90);
			LandModelC.SetScale(Scale);
			
			int locSM = GL20.glGetUniformLocation(ShaderHandler.SpecularShader.shaderProgram, "Alpha");
			GL20.glUniform1f(locSM, 1);
			
			
			LandModel.SetPosition(new Vector3f(Position.x + 8*Scale.x, Position.y, Position.z));
			LandModel.Draw();
			LandModelA.SetPosition(new Vector3f(Position.x - 8*Scale.x, Position.y, Position.z));
			LandModelA.Draw();
			LandModelB.SetPosition(new Vector3f(Position.x + 16*Scale.x, Position.y, Position.z));
			LandModelB.Draw();
			LandModelC.SetPosition(Position);
			LandModelC.Draw();
			
		}
	}

}
