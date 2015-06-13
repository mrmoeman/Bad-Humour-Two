import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;


public class Entity {
	
	protected Vector3f Position;
	private String ModelName;
	private String TextureName;
	public Model MyModel;
	private int WaveNumber = 0;
	protected int PhaseNumber = 0;
	
	float MyRot = 0;
	float RotSpeed = 0;
	protected Vector3f Offset = new Vector3f(0,0,0);
	protected Vector3f Scale = new Vector3f(1,1,1);
	
	protected Vector3f VecA = new Vector3f(0,0,0);
	protected Vector3f VecB = new Vector3f(0,0,0);
	protected Vector3f VecC = new Vector3f(0,0,0);
	
	protected float Orbit = 0;
	
	public Entity(float startx, float starty, float startz, String ModName){
		
		Position = new Vector3f(startx, starty, startz);
		ModelName = ModName;
		TextureName = ModName;
		//MyModel = new Model(ModName, false);
		
		ModelHandler.AddModel(ModName, TextureName);
		MyModel = ModelHandler.FindModel(ModName, TextureName);
		
		System.out.println("model loaded: " + ModName);
		
	}
	
	public Entity(float startx, float starty, float startz, String ModName, String TexName){
		
		Position = new Vector3f(startx, starty, startz);
		ModelName = ModName;
		TextureName = TexName;
		//MyModel = new Model(ModName, false);
		
		ModelHandler.AddModel(ModName, TextureName);
		MyModel = ModelHandler.FindModel(ModName, TextureName);
		
		System.out.println("model loaded: " + ModName);
		
	}
	
	
	
	public void Update(){
		
		MyRot += RotSpeed;
		
	}
	
	public void CheckHit(Player MyPlayer){
			
	}

	
	public int GetHealth(){
		
		return 1;
	}
	
	public void SetHealth(int NewHealth){
		
	}
	
	
	public void Draw(){
		if(DontDraw() == false){
			MyModel.SetPosition(Position);
			MyModel.SetRotation(MyRot);
			MyModel.SetScale(Scale);
			MyModel.Draw();
		}
		
	}
	
	public void SetState(String State){
	}
	
	public void BulletDraw(float BlurScale){
		
	}
	
	public void SetOffset(Vector3f MyOffset){
		Offset = MyOffset;
	}
	
	public void SetScale(Vector3f MyScale){
		Scale = MyScale;
	}
	
	public void SetOrbit(float MyOrbit){
		Orbit = MyOrbit;
	}
	
	public boolean ReadyToRemove(){
		return false;
	}
	
	public boolean DontDraw(){
		return false;
	}
	
	public boolean OnlyEdge(){
		return false;
	}
	
	public void SetVecA(Vector3f MyVec){
		VecA = new Vector3f(MyVec.x, MyVec.y, MyVec.z);
	}
	public void SetVecB(Vector3f MyVec){
		VecB = new Vector3f(MyVec.x, MyVec.y, MyVec.z);
	}
	public void SetVecC(Vector3f MyVec){
		VecC = new Vector3f(MyVec.x, MyVec.y, MyVec.z);
	}
	
	public void SetWaveNumber(int EntWave){
		WaveNumber = EntWave;
	}
	public int GetWaveNumber(){
		return WaveNumber;
	}
	
	public boolean ReadyForNextWave(){
		return true;
	}
	
	public void AddBulletSpell(TemporarySpell MyTempSpell){
		
	}
	
	public void SetDelay(int MyDelay){
		
	}

	public void SetRotSpeed(float MyRotSpeed){
		RotSpeed = MyRotSpeed;
	}

	public void SetBoss(){
		
	}

	public void SetPhaseNumber(int MyPhase){
		PhaseNumber = MyPhase;
	}
	
	public int GetPhase(){
		return PhaseNumber;
	}
	
}
