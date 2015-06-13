import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class Player extends Entity {
	
	//left: -25
	//right: 25
	//top: 13
	//bottom: -13
	
	
	float basespeed = 0.4f;
	float focusspeed = 0.1f;
	float speed = basespeed;
	int bullettimer = 200;
	int Health = 50;
	boolean PlayerInv = true;
	int PlayerInvTimer = 120;
	Vector3f PlayerStart;
	
	boolean SlowSpeed = false;
	boolean XDown = false;
	boolean XReleased = false;
	
	ArrayList<Bullet> PlayerBulletList = new ArrayList<>();
	
	BulletSpell BombSpell = new BulletSpell();
	
	ArrayList<Bullet> PlayerBulletRemoveList = new ArrayList<>();
	Bullet TempBullet;

	int BombsLeft = 3;
	
	public Player(float startx, float starty, float startz, String ModelName) {
		super(startx, starty, startz, ModelName);
		MyModel.SetTopDown(true);
		
		GameData.PlayerPower = 0;
		PlayerStart = new Vector3f(startx, starty, startz);
		
	}
	
	
	@Override
	public void Update(){
		GameData.PlayerPosition = Position;
		GameData.PlayerHealth = Health;
		GameData.PlayerInvuln = PlayerInv;
		bullettimer++;
		
		if(PlayerInvTimer > 0){
			PlayerInvTimer--;
		}
		else{
			PlayerInv = false;
		}
		
		BulletUpdate();
		
		if(Input.GetUp()==true){
			if(GameData.CameraState == 0){
				if (Position.y < 13){
					if(GameData.CameraState == 0){
						Position.y+=speed;
					}
				}
			}
			if(GameData.CameraState == 1){
				if (Position.x < 25){
					Position.x+=speed;
				}
			}
		}
		if(Input.GetDown()==true){
			if(GameData.CameraState == 0){
				if (Position.y > -14){
					Position.y-=speed;
				}
			}
			if(GameData.CameraState == 1){
				if (Position.x > -25){
					Position.x-=speed;
				}
			}
		}
		if(Input.GetLeft()==true){			
			if(GameData.CameraState == 1){
				if (Position.y < 13){
					if(GameData.CameraState == 1){
						Position.y+=speed;
					}
				}
			}
			if(GameData.CameraState == 0){
				if (Position.x > -25){
					Position.x-=speed;
				}
			}
		}
		if(Input.GetRight()==true){

			if(GameData.CameraState == 1){
				if (Position.y > -14){
					Position.y-=speed;
				}
			}
			if(GameData.CameraState == 0){
				if (Position.x < 25){
					Position.x+=speed;
				}
			}
		}
		
		//BOMB STUFF
		if(Input.GetC() == true && GameData.BombOn == false && BombsLeft > 0){
			GameData.BombOn = true;
			GameData.BombPosition = new Vector3f(Position.x, Position.y, Position.z);
			GameData.BombRadius = 0;
			for(int i = 0; i < 30; i++){
			Vector2f TempHeading = MyMath.AngleToHeadingVector(360/30*i);
			
			TempBullet = new Bullet(Position);
			TempBullet.SetColour(new Vector3f(1.0f, 0.5f, 0.0f));
			TempBullet.SetScale(5);
			TempBullet.SetMovementVector(-TempHeading.x/10, -TempHeading.y/10, 0);
			BombSpell.BulletList.add(TempBullet);
			}
			BombsLeft--;
			GameData.LevelScore -= 200;
		}
		if(GameData.BombOn == true){
			GameData.BombRadius += 0.1f;
			if(GameData.BombRadius > 18){
				GameData.BombOn = false;
				BombSpell.BulletList.clear();
			}
		}
		
		if(Input.GetX() == true){
			XDown = true;
			XReleased = false;
		}
		else{
			XReleased = true;
		}
		if(XReleased == true && XDown == true){
			SlowSpeed = !SlowSpeed;
			XDown = false;
		}
		//System.out.println(SlowSpeed);
		if(SlowSpeed == true){
			speed = focusspeed;
		}
		else{
			speed = basespeed;
		}
		
		if(Input.GetZ()==true && bullettimer > 5){
			//System.out.println("Z");
			
			if(GameData.PlayerPower < 3){
				TempBullet = new Bullet(new Vector3f(Position.x, Position.y, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				//TempBullet.SetBlurScale(2.0f);
				PlayerBulletList.add(TempBullet);
				TempBullet = null;
			}
			if(GameData.PlayerPower > 2 && GameData.PlayerPower < 10){
				TempBullet = new Bullet(new Vector3f(Position.x, Position.y+0.2f, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				PlayerBulletList.add(TempBullet);
				TempBullet = new Bullet(new Vector3f(Position.x, Position.y-0.2f, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				PlayerBulletList.add(TempBullet);
				TempBullet = null;
			}
			if(GameData.PlayerPower > 9){
				TempBullet = new Bullet(new Vector3f(Position.x, Position.y+0.2f, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				PlayerBulletList.add(TempBullet);
				TempBullet = new Bullet(new Vector3f(Position.x, Position.y-0.2f, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				PlayerBulletList.add(TempBullet);
				TempBullet = new Bullet(new Vector3f(Position.x-0.15f, Position.y-0.5f, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				PlayerBulletList.add(TempBullet);
				TempBullet = new Bullet(new Vector3f(Position.x - 0.15f, Position.y+0.5f, Position.z));
				TempBullet.SetMovementVector(1f,0,0);
				TempBullet.SetColour(new Vector3f(1,1,0));
				PlayerBulletList.add(TempBullet);
				TempBullet = null;
			}
			
			
			
			bullettimer = 0;
		}
		
		
		if(Input.GetF2()==true){
			System.out.println("X: " + Position.x);
			System.out.println("Y: " + Position.y);
		}
		
		Health -= GameData.PlayerDamage;
		GameData.PlayerDamage = 0;
		
		if(Health < 1){
			PlayerReset();
		}
		
	}
	
	public void BulletUpdate(){
		
		BombSpell.PlayerBulletUpdate();
		
		for(Bullet MyBullet: PlayerBulletList){
			
			MyBullet.Update();

			if(MyBullet.BoundaryCheck()==true){
				PlayerBulletRemoveList.add(MyBullet);
			}
			
		}
		for(Bullet MyBullet: PlayerBulletRemoveList){
			PlayerBulletList.remove(MyBullet);
		}
		PlayerBulletRemoveList.clear();
		
	}
	
	@Override
	public void BulletDraw(float BlurScale){
		
		BombSpell.Draw(BlurScale);
		
		for(Bullet MyBullet: PlayerBulletList){
			
			MyBullet.Draw(BlurScale);
			
		}
		
	}
	
	@Override
	public boolean DontDraw(){
		if(Health > 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void PlayerReset(){
		
		Health = 50;
		PlayerInv = true;
		PlayerInvTimer = 240;
		Position = new Vector3f(PlayerStart.x,PlayerStart.y,PlayerStart.z);
		GameData.PlayerPower = 0;
		BombsLeft = 3;
		GameData.LevelScore -= 1000;
	
	}
	
	@Override
	public boolean OnlyEdge(){
		if(PlayerInv == true){
			return true;
		}
		else{
			return false;
		}
		
	}

}
