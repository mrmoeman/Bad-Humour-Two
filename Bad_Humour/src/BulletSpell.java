import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class BulletSpell {
	
	ArrayList<Bullet> BulletList = new ArrayList<>();
	ArrayList<Bullet> BulletRemoveList = new ArrayList<>();
	Vector3f Colour = new Vector3f (1,1,1);
	Vector3f NewColour = new Vector3f (1,1,1);
	Bullet TempBullet;
	Vector3f BulletVector = new Vector3f(0,0,0);
	BulletSpellType MyType = BulletSpellType.MovementVector;
	float DataA;
	float DataB;
	float DataC;
	float DataD;
	float DataE;
	int timer = 0;
	float ticktimer = 0;
	float BulletScale = 1;
	int BulletSpellDelay = 0;
	int BulletSpellTimer = 0;
	int BulletSpellPhase = 0;
	
	String BulletOrient = "None";
	
	//PARTICLES
	boolean HasParticles = false;
	String ParticleName = null;
	
	float BlurScale = 1;
	
	boolean CustomBullet = false;
	String CustomBulletName = null;
	
	TemporarySpell MySecSpell = new TemporarySpell();
	float SecRequire = 0;
	boolean SecondarySpell = false;
	BulletSpellSecondaryRequirements SecondaryRequire = BulletSpellSecondaryRequirements.RadiusLimit;
	
	public void Update(Vector3f Position, int Health, int Phase){
		
		if(BulletSpellTimer >= BulletSpellDelay){
			
		if(timer == 0){
			NewColour = new Vector3f((float)(Math.random()),(float)(Math.random()),(float)(Math.random()));
		}
		
		timer ++;
		ticktimer += DataC;
		if (Health > 0 && BulletSpellPhase == Phase){
			if(MyType == BulletSpellType.MovementVector){
				BulletVector = new Vector3f(DataA, DataB, DataC);
				AddBullet(Position, (int)DataD);
				//System.out.println("Added Bullet");
			}
			if(MyType == BulletSpellType.BulletSpray){
				AddBulletSpray(Position, (int)DataA, (int)DataB);
				//System.out.println("Added Bullet");
			}
			if(MyType == BulletSpellType.RotatingBulletSpray){
				AddRotatingBulletSpray(Position, (int)DataA, (int)DataB);
				//System.out.println(ticktimer);
			}
			if(MyType == BulletSpellType.RandomBulletSpray){
				AddRandomBulletSpray(Position, (int)DataA, (int)DataB);
				//System.out.println(ticktimer);
			}
			if(MyType == BulletSpellType.RandomBulletRain){
				AddRandomBulletRain(Position, (int)DataA, (int)DataB);
				//System.out.println(ticktimer);
			}
			if(MyType == BulletSpellType.ConalBulletSpray){
				AddConalBulletSpray(Position, (int)DataA, (int)DataB, (int)DataC);
				//System.out.println(ticktimer);
			}
			if(MyType == BulletSpellType.AimedLine){
				AddAimedLine(Position, (int)DataA, DataB);
				//System.out.println("aimed line go");
			}
		}
		
		if(SecondarySpell == true){
			SecondarySpellFunctions(MySecSpell.TempString);
		}
		
		for(Bullet MyBullet: BulletList){
			
			MyBullet.Update();
			
			if(GameData.PlayerHealth > 0){
				if(MyMath.Length(MyBullet.Position, GameData.PlayerPosition) < 0.6 + 0.1*MyBullet.GetScale()){
					if(GameData.PlayerInvuln == false){
						GameData.PlayerDamage++;
						GameData.LevelScore -= 10;
					}
					BulletRemoveList.add(MyBullet);
				}
				else{
					if(MyMath.Length(MyBullet.Position, GameData.PlayerPosition) < 1 + 0.1*MyBullet.GetScale() && MyMath.Length(MyBullet.Position, GameData.PlayerPosition) > 0.8f + 0.1*MyBullet.GetScale()){
						GameData.LevelScore +=5;
					}
				}
			}
			if(GameData.BombOn == true){
				if(MyMath.Length(MyBullet.Position, GameData.BombPosition) < GameData.BombRadius){
					BulletRemoveList.add(MyBullet);
					//System.out.println("bullet kill by bomb");
				}
			}
			
			if(MyBullet.BoundaryCheck()==true){
				BulletRemoveList.add(MyBullet);
			}
			
		}
		for(Bullet MyBullet: BulletRemoveList){
			BulletList.remove(MyBullet);
		}
		BulletRemoveList.clear();
		}
		else{
			BulletSpellTimer++;
		}
	}
	
	public void PlayerBulletUpdate(){
		for(Bullet MyBullet: BulletList){
			
			MyBullet.Update();
		
			if(MyBullet.BoundaryCheck()==true){
				BulletRemoveList.add(MyBullet);
			}
			
		}
		for(Bullet MyBullet: BulletRemoveList){
			BulletList.remove(MyBullet);
		}
		BulletRemoveList.clear();
	}
	
	public void SetBulletVector(Vector3f MovementVector){
		BulletVector = MovementVector;
	}
	
	public void SetBulletColour(Vector3f BulletColour){
		Colour = BulletColour;

	}
	
	public void AddBullet(Vector3f Position, int time){
		if (timer > time){
		TempBullet = new Bullet(Position);
		TempBullet.SetColour(Colour);
		TempBullet.SetScale(BulletScale);
		TempBullet.SetBlurScale(BlurScale);
		TempBullet.SetOrientMode(BulletOrient);
		if(CustomBullet == true){
			TempBullet.SetCustomBulletModel(CustomBulletName);
		}
		if(HasParticles == true){
			TempBullet.HasParticles = true;
			TempBullet.ParticleName = ParticleName;
		}
		TempBullet.SetMovementVector(BulletVector.x, BulletVector.y, BulletVector.z);
		BulletList.add(TempBullet);
		timer = 0;
		}
	}
	
	public void AddBulletSpray(Vector3f Position, float SprayNumber,int time){
		if (timer > time){
		for (int i = 0; i < SprayNumber; i++){
			
			Vector2f TempHeading = MyMath.AngleToHeadingVector(360/SprayNumber*i);

				TempBullet = new Bullet(Position);
				TempBullet.SetColour(Colour);
				TempBullet.SetScale(BulletScale);
				TempBullet.SetBlurScale(BlurScale);
				TempBullet.SetOrientMode(BulletOrient);
				if(CustomBullet == true){
					TempBullet.SetCustomBulletModel(CustomBulletName);
				}
				if(HasParticles == true){
					TempBullet.HasParticles = true;
					TempBullet.ParticleName = ParticleName;
				}
				//System.out.println(TempHeading);
				TempBullet.SetMovementVector(-TempHeading.x/5, -TempHeading.y/5, 0);
				BulletList.add(TempBullet);
		}
		timer = 0;
		}
	}
	
	public void AddConalBulletSpray(Vector3f Position, float SprayNumber,int time, int DegreeLimit){
		if (timer > time){
		for (int i = 0; i < SprayNumber+1; i++){
			
			Vector2f TempHeading = MyMath.AngleToHeadingVector(DegreeLimit - (DegreeLimit*2)/SprayNumber*i);
			
			
				TempBullet = new Bullet(Position);
				TempBullet.SetColour(Colour);
				TempBullet.SetScale(BulletScale);
				TempBullet.SetBlurScale(BlurScale);
				TempBullet.SetOrientMode(BulletOrient);
				//System.out.println(TempHeading);
				if(CustomBullet == true){
					TempBullet.SetCustomBulletModel(CustomBulletName);
				}
				if(HasParticles == true){
					TempBullet.HasParticles = true;
					TempBullet.ParticleName = ParticleName;
				}
				TempBullet.SetMovementVector(-TempHeading.x/5, -TempHeading.y/5, 0);
				BulletList.add(TempBullet);
		}
		timer = 0;
		}
	}
	
	
	public void AddRotatingBulletSpray(Vector3f Position, float SprayNumber,int time){
		if (timer > time){
		for (int i = 0; i < SprayNumber; i++){
			
			Vector2f TempHeading = MyMath.AngleToHeadingVector((360/SprayNumber*i) + ticktimer);
			
				TempBullet = new Bullet(Position);
				ColourBullet(TempBullet);
				TempBullet.SetScale(BulletScale);
				TempBullet.SetBlurScale(BlurScale);
				TempBullet.SetOrientMode(BulletOrient);
				if(CustomBullet == true){
					TempBullet.SetCustomBulletModel(CustomBulletName);
				}
				if(HasParticles == true){
					TempBullet.HasParticles = true;
					TempBullet.ParticleName = ParticleName;
				}
				//System.out.println(TempHeading);
				TempBullet.SetMovementVector(-TempHeading.x/5, -TempHeading.y/5, 0);
				BulletList.add(TempBullet);
		}
		timer = 0;
		}
		
	}
	
	public void AddRandomBulletSpray(Vector3f Position, float SprayNumber,int time){
		if (timer > time){
		for (int i = 0; i < SprayNumber; i++){
			
			Vector2f TempHeading = MyMath.AngleToHeadingVector((float)(Math.random()*360));

				TempBullet = new Bullet(Position);
				TempBullet.SetColour(Colour);
				TempBullet.SetScale(BulletScale);
				TempBullet.SetBlurScale(BlurScale);
				TempBullet.SetOrientMode(BulletOrient);
				if(CustomBullet == true){
					TempBullet.SetCustomBulletModel(CustomBulletName);
				}
				if(HasParticles == true){
					TempBullet.HasParticles = true;
					TempBullet.ParticleName = ParticleName;
				}
				//System.out.println(TempHeading);
				TempBullet.SetMovementVector(-TempHeading.x/5, -TempHeading.y/5, 0);
				BulletList.add(TempBullet);
		}
		timer = 0;
		}
	}
	
	public void AddRandomBulletRain(Vector3f Position, float SprayNumber,int time){
		if (timer > time){
		for (int i = 0; i < SprayNumber; i++){
			
			Vector2f TempHeading = MyMath.AngleToHeadingVector((float)(Math.random()*360));

				TempBullet = new Bullet(Position);
				TempBullet.SetColour(Colour);
				TempBullet.SetScale(BulletScale);
				TempBullet.SetBlurScale(BlurScale);
				TempBullet.SetOrientMode(BulletOrient);
				//System.out.println(TempHeading);
				if(CustomBullet == true){
					TempBullet.SetCustomBulletModel(CustomBulletName);
				}
				TempBullet.SetMovementVector(-TempHeading.x/5, -TempHeading.y/5, 0);
				if(TempBullet.GetMovementVector().x > 0){
					TempBullet.SetMovementVector(TempBullet.GetMovementVector().x * -1, TempBullet.GetMovementVector().y, 0);
				}
				BulletList.add(TempBullet);
		}
		timer = 0;
		}
	}
	
	public void AddAimedLine(Vector3f Position,int time, float speed){
		if (timer > time){

			
			Vector2f TempHeading = new Vector2f(MyMath.VecBetween(Position, GameData.PlayerPosition).x, MyMath.VecBetween(Position, GameData.PlayerPosition).y);
				TempBullet = new Bullet(Position);
				TempBullet.SetColour(Colour);
				TempBullet.SetScale(BulletScale);
				TempBullet.SetBlurScale(BlurScale);
				TempBullet.SetOrientMode(BulletOrient);
				//System.out.println(TempHeading);
				if(CustomBullet == true){
					TempBullet.SetCustomBulletModel(CustomBulletName);
				}
				TempBullet.SetMovementVector(-TempHeading.x/(1/speed), -TempHeading.y/(1/speed), 0);
				//if(TempBullet.GetMovementVector().x > 0){
					//TempBullet.SetMovementVector(TempBullet.GetMovementVector().x * -1, TempBullet.GetMovementVector().y, 0);
				//}
				//System.out.println(TempBullet.GetMovementVector());
				BulletList.add(TempBullet);
		
		timer = 0;
		}
	}
	
	public void Draw(float MyBlurScale){
		if(BlurScale == MyBlurScale){
		for(Bullet MyBullet: BulletList){
			
			MyBullet.Draw(MyBlurScale);
			
		}
		}
	}
	
	public void SetBulletSpell(String BulletSpellTy, float DA, float DB, float DC, float DD, float DE, Vector3f Color){
		DataA = DA;
		DataB = DB;
		DataC = DC;
		DataD = DD;
		DataE = DE;
		Colour = new Vector3f(Color.x, Color.y, Color.z);
		
		if(BulletSpellTy.equals("MovementVector")){
			MyType = BulletSpellType.MovementVector;
			//System.out.println("MV BS Added");
		}
		if(BulletSpellTy.equals("BulletSpray")){
			MyType = BulletSpellType.BulletSpray;
		}
		if(BulletSpellTy.equals("RotatingBulletSpray")){
			MyType = BulletSpellType.RotatingBulletSpray;
		}
		if(BulletSpellTy.equals("RandomBulletSpray")){
			MyType = BulletSpellType.RandomBulletSpray;
		}
		if(BulletSpellTy.equals("ConalBulletSpray")){
			MyType = BulletSpellType.ConalBulletSpray;
		}
		if(BulletSpellTy.equals("RandomBulletRain")){
			MyType = BulletSpellType.RandomBulletRain;
		}
		if(BulletSpellTy.equals("AimedLine")){
			MyType = BulletSpellType.AimedLine;
		}
		
	}
	
	public void SetScale(float Scale){
		BulletScale = Scale;
	}
	
	public void SetCustomBullet(String MyModelName){
		CustomBulletName = MyModelName;
		CustomBullet = true;
	}
	
	public void SetBlurScale(float MyBlurScale){
		BlurScale = MyBlurScale;
	}
	
	///SECONDARY BULLET SPELLS
	public void SetSecondaryEffect(String Effect, float require, TemporarySpell MyTempSpell){
		MySecSpell.Color = MyTempSpell.Color;
		MySecSpell.BulletScale = MyTempSpell.BulletScale;
		MySecSpell.DataA = MyTempSpell.DataA;
		//System.out.println(BlurScale);
		MySecSpell.DataB = MyTempSpell.DataB;
		MySecSpell.DataC = MyTempSpell.DataC;
		MySecSpell.DataD = MyTempSpell.DataD;
		MySecSpell.DataE = MyTempSpell.DataE;
		MySecSpell.TempString = MyTempSpell.TempString;
		SecRequire = require;
		
		if(Effect.equals("RadiusLimit")){
			SecondaryRequire = BulletSpellSecondaryRequirements.RadiusLimit;
		}
		if(Effect.equals("RadiusPlayer")){
			SecondaryRequire = BulletSpellSecondaryRequirements.RadiusLimit;
		}
		if(Effect.equals("None")){
			SecondaryRequire = BulletSpellSecondaryRequirements.RadiusLimit;
		}
		if(Effect.equals("XLimit")){
			SecondaryRequire = BulletSpellSecondaryRequirements.XLimit;
		}
		
		
		SecondarySpell = true;
		//System.out.println("Sec Spell Added to Spell");
	}

	public void SecondarySpellFunctions(String SecFunction){
		
		//System.out.println(SecFunction);
		
		if(SecFunction.equals("MovementVector")){
			for(Bullet MyBullet: BulletList){
				
				if(SecondaryRequire == BulletSpellSecondaryRequirements.RadiusLimit){
					
					if(MyMath.Length(MyBullet.Position, MyBullet.StartPosition) >= SecRequire){
						MyBullet.SetMovementVector(MySecSpell.DataA, MySecSpell.DataB, MySecSpell.DataC);
						//System.out.println(MySecSpell.DataA);
						if(MySecSpell.Color.x >= 0 && MySecSpell.Color.y >= 0 && MySecSpell.Color.z >= 0){
							MyBullet.SetColour(MySecSpell.Color);
						}
						MyBullet.SetScale(MySecSpell.BulletScale);
					}
				}
				
				
				if(SecondaryRequire == BulletSpellSecondaryRequirements.XLimit){
					if(MyBullet.Position.x < SecRequire){
						MyBullet.SetMovementVector(MySecSpell.DataA, MySecSpell.DataB, MySecSpell.DataC);
						//System.out.println(MySecSpell.DataA);
						if(MySecSpell.Color.x >= 0 && MySecSpell.Color.y >= 0 && MySecSpell.Color.z >= 0){
							MyBullet.SetColour(MySecSpell.Color);
						}
						MyBullet.SetScale(MySecSpell.BulletScale);
					}
				}
				
			}
		}
		
		
		if(SecFunction.equals("Homing")){
			//System.out.println("Bad Game");
			for(Bullet MyBullet: BulletList){
				
				if(SecondaryRequire == BulletSpellSecondaryRequirements.RadiusLimit){
					
					if(MyMath.Length(MyBullet.Position, MyBullet.StartPosition) >= SecRequire && MyBullet.SetVector == false){
						
						Vector3f DistanceToPlayer = MyMath.VecBetween(GameData.PlayerPosition, MyBullet.Position);
						
						MyBullet.SetMovementVector(DistanceToPlayer.x / MySecSpell.DataA, DistanceToPlayer.y / MySecSpell.DataA, DistanceToPlayer.z / MySecSpell.DataA);
						MyBullet.MovementSet();
						if(MySecSpell.Color.x >= 0 && MySecSpell.Color.y >= 0 && MySecSpell.Color.z >= 0){
							MyBullet.SetColour(MySecSpell.Color);
						}
						MyBullet.SetScale(MySecSpell.BulletScale);
					}
				}
				
				
				if(SecondaryRequire == BulletSpellSecondaryRequirements.XLimit){
					if(MyBullet.Position.x < SecRequire){
						MyBullet.SetMovementVector(MySecSpell.DataA, MySecSpell.DataB, MySecSpell.DataC);
						//System.out.println(MySecSpell.DataA);
						if(MySecSpell.Color.x >= 0 && MySecSpell.Color.y >= 0 && MySecSpell.Color.z >= 0){
							MyBullet.SetColour(MySecSpell.Color);
						}
						MyBullet.SetScale(MySecSpell.BulletScale);
					}
				}
				
				
			}
		}
		
	}



	//BULLET FUNCTIONS
	private void ColourBullet(Bullet TempBullet){
		TempBullet.SetColour(Colour);
		if(Colour.x == -50 && Colour.y == -50 && Colour.z == -50){
			TempBullet.SetColour(NewColour);
		}
	}
	
}
