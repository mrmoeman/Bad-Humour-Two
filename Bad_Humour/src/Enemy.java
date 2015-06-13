import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;


public class Enemy extends Entity {
	
	ArrayList<BulletSpell> EntityBulletSpellList = new ArrayList<>();
	boolean MovementState = false;
	
	EnemyState MyState = EnemyState.idle;
	
	int Health = 27;
	int MaxHealth = 27;
	Vector3f speed = new Vector3f(0,0,0);
	boolean PowerUpProvided = false;
	int Delay = 0;
	int DelayTimer = 0;
	
	int AliveTimer = 0;
	
	boolean IsBoss = false;
	boolean BossDataUpdated = false;
	
	boolean HitByBomb = false;
	
	int CurrentPhase = 0;

	public Enemy(float startx, float starty, float startz, String ModName) {
		super(startx, starty, startz, ModName);
		
		MyRot = 180;
		//BulletSpell MySpell = new BulletSpell();
		//MySpell.SetBulletVector(new Vector3f(-0.2f, 0, 0));
		//MySpell.SetBulletColour(new Vector3f(0,0.5f,1));
		//EntityBulletSpellList.add(MySpell);
		//MySpell = null;
		//System.out.println(Health);
	}
	
	@Override
	public void Update(){
		if(DelayTimer >= Delay){
			super.Update();
			
			AliveTimer++;
			
		//BOSS STUFF
		if(IsBoss == true){
			if(BossDataUpdated == false){
				GameData.MaxBossHealth = Health * (1000/MaxHealth);
				BossDataUpdated = true;
			}
			GameData.BossHealth = Health* (1000/MaxHealth);
			if(GameData.BossHealth < 0){
				GameData.BossHealth = 0;
			}
			//System.out.println(CurrentPhase + " " + PhaseNumber);
			if(Health <= 0 && CurrentPhase < PhaseNumber){
				Health = MaxHealth;
				GameData.PlayerPower+=3;
				CurrentPhase++;
			}
		}
			
			
		
		if(MyState == EnemyState.PointMovement){
			PointMovement(240);
		}
		if(MyState == EnemyState.MoveToo){
			MoveToo(240);
		}
		
		if(Health <= 0 && PowerUpProvided == false){
			GameData.PlayerPower++;
			PowerUpProvided = true;
			GameData.LevelScore += 500;
			if(AliveTimer < 360){
				GameData.LevelScore += 300;
			}
		}
		
		
		//bulletspell update
		for(BulletSpell MySpell: EntityBulletSpellList){
			//System.out.println("updated BulletSpell");
			MySpell.Update(Position, Health, CurrentPhase);
			/*System.out.println(MySpell.MyType);
			System.out.println(MySpell.DataA);
			System.out.println(MySpell.DataB);
			System.out.println(MySpell.DataC);
			System.out.println(MySpell.DataD);
			System.out.println(MySpell.DataE);*/
			if (Health > 0){
				//MySpell.AddBullet(new Vector3f(Position.x, Position.y, Position.z), 15);	
				//MySpell.BulletSpray(Position, 23, 15);
			}
		}
		
		}
		else{
			DelayTimer++;
		}
	}
	
	@Override
	public void Draw(){
		if(DelayTimer >= Delay){
		if (Health > 0){
			MyModel.SetPosition(Position);
			MyModel.SetRotation(MyRot);
			MyModel.SetTopDown(true);
			MyModel.Draw();
		}
		}
	}
	
	@Override
	public void BulletDraw(float BlurScale){
		
		//bulletspell draw
		for(BulletSpell MySpell: EntityBulletSpellList){
									
			MySpell.Draw(BlurScale);
									
		}
		
	}
	
	@Override
	public void SetState(String State){
		if (State.equals("Idle")){
			MyState = EnemyState.idle;
		}
		if (State.equals("PointMovement")){
			MyState = EnemyState.PointMovement;
		}
		if (State.equals("LineMovement")){
			MyState = EnemyState.LineMovement;
		}
		if (State.equals("MoveToo")){
			MyState = EnemyState.MoveToo;
		}
		if (State.equals("Dead")){
			MyState = EnemyState.Dead;
		}
	}
	
	@Override
	public int GetHealth(){
		return Health;
	}
	
	@Override
	public void SetHealth(int NewHealth){
		Health = NewHealth;
		MaxHealth = NewHealth;
	}
	
	@Override
	public void CheckHit(Player MyPlayer){
		if(Health > 0){
		for(Bullet MyBullet: MyPlayer.PlayerBulletList){
			
			if(MyMath.Length(MyBullet.Position, Position) < 1.5f){
				MyPlayer.PlayerBulletRemoveList.add(MyBullet);
				Health--;
			}
			
		}
		
		if(GameData.BombOn == true && HitByBomb == false){
			if(MyMath.Length(Position, GameData.BombPosition) < GameData.BombRadius){
				Health -= 50;
				HitByBomb = true;
			}
		}
		if(GameData.BombOn == false){
			HitByBomb = false;
		}
		
		}
	}
	
	@Override
	public boolean ReadyToRemove(){
		int NoBullets = 0;
		for(BulletSpell MySpell: EntityBulletSpellList){
			
			NoBullets += MySpell.BulletList.size();
			//System.out.println(MySpell.BulletList.size());
		}
		//System.out.println("Health: " + Health);
		//System.out.println("bullets left: " + NoBullets);
		//System.out.println("Bullets Counted");
		if (Health < 1 && NoBullets < 1){
			//System.out.println("Enemy Removed");
			//System.out.println("bullets left: " + NoBullets);
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public boolean ReadyForNextWave(){
		if(Health > 0){
			return true;//NOT READY
		}
		else{
			if(IsBoss == true){
				GameData.BossHealth = Health* (1000/MaxHealth);
			}
			
			//System.out.println(CurrentPhase +" " + PhaseNumber);
			if(CurrentPhase == PhaseNumber){
				return false;
			}
			else{
				return true;
			}
			
		}
	}
	
	public void PointMovement(int time){
		if(speed.x + speed.y + speed.z == 0){
			speed = new Vector3f(MyMath.VecBetween(VecA, VecB).x/time, MyMath.VecBetween(VecA, VecB).y/time, MyMath.VecBetween(VecA, VecB).z/time);
		}
		
	
		if(MovementState == false){
			if(MyMath.Length(new Vector3f(Position.x, Position.y, Position.z), new Vector3f(VecB.x, VecB.y, Position.z))< 1){
				MovementState = true;
			}
			else{
				Position = new Vector3f(Position.x - speed.x, Position.y - speed.y, Position.z);
			}
		}
		
		
		if(MovementState == true){
			if(MyMath.Length(new Vector3f(Position.x, Position.y, Position.z), new Vector3f(VecA.x, VecA.y, Position.z))< 1){
				MovementState = false;
			}
			else{
				Position = new Vector3f(Position.x + speed.x, Position.y + speed.y, Position.z);
			}
		}
		
	}
	
	public void MoveToo(int time){
		if(speed.x + speed.y + speed.z == 0){
			speed = new Vector3f(MyMath.VecBetween(Position, VecA).x/time, MyMath.VecBetween(Position, VecA).y/time, MyMath.VecBetween(Position, VecA).z/time);
		}
		
		if(MovementState == false){
			if(MyMath.Length(new Vector3f(Position.x, Position.y, Position.z), new Vector3f(VecA.x, VecA.y, Position.z))< 1){
				MovementState = true;
			}
			else{
				Position = new Vector3f(Position.x - speed.x, Position.y - speed.y, Position.z);
			}
		}
		
	}

	@Override
	public void AddBulletSpell(TemporarySpell MyTempSpell){
		//System.out.println("bulletspell caught " + MyTempSpell.DataB);
		BulletSpell TempBulletSpell = new BulletSpell();
		TempBulletSpell.SetBulletSpell(MyTempSpell.TempString, MyTempSpell.DataA, MyTempSpell.DataB, MyTempSpell.DataC, MyTempSpell.DataD, MyTempSpell.DataE, MyTempSpell.Color);
		TempBulletSpell.BlurScale = MyTempSpell.BlurScale;
		TempBulletSpell.BulletSpellDelay = MyTempSpell.BulletSpellDelay;
		TempBulletSpell.BulletOrient = MyTempSpell.OrientMode;
		TempBulletSpell.BulletSpellPhase = MyTempSpell.BulletSpellPhase;
		//System.out.println(MyTempSpell.BlurScale  + " howdy");
		if(MyTempSpell.BulletScale > 0){
			TempBulletSpell.SetScale(MyTempSpell.BulletScale);
		}
		if(MyTempSpell.SecondaryPhase == true){
			//System.out.println(MyTempSpell.SecondBulletSpell.DataA + "grrr");
			TempBulletSpell.SetSecondaryEffect(MyTempSpell.TempSecondPhase, MyTempSpell.SecondPhaseRequirement, MyTempSpell.SecondBulletSpell);
		}
		if(MyTempSpell.CustomBullet == true){
			TempBulletSpell.SetCustomBullet(MyTempSpell.CustomBulletName);
		}
		if(MyTempSpell.Particles == true){
			TempBulletSpell.HasParticles = true;
			TempBulletSpell.ParticleName = MyTempSpell.ParticleName;
			//System.out.println(TempBulletSpell.ParticleName);
		}
		
		EntityBulletSpellList.add(TempBulletSpell);
	}
	
	@Override
	public void SetDelay(int MyDelay){
		Delay = MyDelay;
	}

	@Override
	public void SetBoss(){
		IsBoss = true;
	}

}
