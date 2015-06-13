import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Level {

	ArrayList<Entity> LevelEntityList = new ArrayList<>();
	ArrayList<Entity> LevelEntityRemoveList = new ArrayList<>();
	private Entity MyBack = new Entity(0, 0, -50, "black");
	private int TotalWaves;
	private int WaveNumber = 1;
	DialogueHandler MyDialog;
	private float FadeTimer = 0;
	int LevelStatus = 0;
	Texture LevelOpening;
	
	Vector3f BaseLightPos = new Vector3f(-30, 0, 200.0f);
	Vector4f LightDirection = new Vector4f(-30, 0, 200.0f, 0);
	
	Matrix4f LightMatrix = new Matrix4f();

	Texture LevelComplete;
	
	//backdrop
	boolean HasBackdrop = false;
	BackDrop MyBackDrop;
	
	int CameraState = 0;

	public Level(String LevelName) {
		
		MyBack.SetScale(new Vector3f(110,110,110));
		GameData.MaxBossHealth = 0;
		GameData.BossHealth = 0;

		try {
			LevelComplete = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("Res/Textures/levelcomplete.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MyDialog = new DialogueHandler();
		ModelHandler.AddModel("bullet", "black");
		//ModelHandler.AddModel("gear", "black");

		MyBack.MyModel.SetScale(new Vector3f(40, 40, 1));

		String LevelFileName = "Res/LevelData/" + LevelName + ".Dat";
		Scanner s = null;
		String TempString = null;
		Entity TempEnt;
		boolean CreatingNewEnt = false;
		Vector2f TempPosition = new Vector2f(0, 0);
		Vector3f TempOffset = new Vector3f(0, 0, 0);
		Vector3f TempScale = new Vector3f(1, 1, 1);
		float TempOrbit = 0;
		String TempName = null;
		String TempTexName = null;
		String TempStateName = "howdy";
		boolean CreatingPlayer = false;
		boolean CreatingPlanet = false;
		boolean CreatingEnemy = false;
		boolean CreatingDialogue = false;

		Vector3f TempVecA = new Vector3f(0, 0, 0);
		Vector3f TempVecB = new Vector3f(0, 0, 0);
		Vector3f TempVecC = new Vector3f(0, 0, 0);

		int TempWave = 0;
		int TempHealth = 27;
		float TempBulletScale = 0;
		
		float TempRotSpeed = 0;
		
		String CustomBulletModel = null;
		boolean CustomBullet = false;

		String TempBulletSpellType = null;
		float TempA = 0;
		float TempB = 0;
		float TempC = 0;
		float TempD = 0;
		float TempE = 0;
		Vector3f TempColour = new Vector3f(1, 1, 1);
		ArrayList<TemporarySpell> TempSpellList = new ArrayList<>();
		
		int TempDelay = 0;
		
		//secondary bullet spell effect
		TemporarySpell SecTempSpell = new TemporarySpell();
		String SecEffect = null;
		float SecRequire = 0;
		boolean SecSpell = false;
		
		float BulletBlur = 1.0f;

		DialogueEvent MyTempDialogue;
		
		//PARTCILE STUFF
		String TempParticleName = null;
		boolean Particles = false;
		
		//boss stuff
		boolean BossOn = false;
		int TempPhases = 0;
		int TempSpellPhase = 0;
		
		//bullet orient
		String TempOrient = "None";
		
		//spell delay
		int TempSpellDelay = 0;

		try {
			InputStream i = Level.class.getResourceAsStream(LevelFileName);
			s = new Scanner(new BufferedReader(new InputStreamReader(i)));
			//s = new Scanner(new BufferedReader(new FileReader(LevelFileName)));

			while (s.hasNext()) {

				TempString = s.next();

				if (TempString.equals("#Player")) {
					CreatingPlayer = true;
				}

				if (TempString.equals("#Planet")) {
					CreatingPlanet = true;
				}

				if (TempString.equals("#Entity")) {
					CreatingNewEnt = true;
				}

				if (TempString.equals("#Enemy")) {
					CreatingEnemy = true;
				}

				if (TempString.equals("#Dialogue")) {
					CreatingDialogue = true;
				}
				
				if (TempString.equals("#BackDrop")) {
					HasBackdrop = true;
					MyBackDrop = new BackDrop(s.next(), s.nextInt());
				}

				if (TempString.equals("#Opening")) {
					try {
						LevelOpening = TextureLoader.getTexture("PNG",
								ResourceLoader
										.getResourceAsStream("Res/Textures/"
												+ s.next() + ".png"));
						System.out.println("Opening Loaded");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				if (TempString.equals("Entity:")) {
					TempName = s.next();
				}

				if (TempString.equals("Texture:")) {
					TempTexName = s.next();
				}

				if (TempString.equals("Orbit:")) {
					TempOrbit = s.nextFloat();
				}
				
				if (TempString.equals("RotationSpeed:")) {
					TempRotSpeed = s.nextFloat();
				}

				if (TempString.equals("Position:")) {
					TempPosition = new Vector2f(s.nextFloat(), s.nextFloat());
				}

				if (TempString.equals("Offset:")) {
					TempOffset = new Vector3f(s.nextFloat(), s.nextFloat(),
							s.nextFloat());
				}

				if (TempString.equals("Scale:")) {
					TempScale = new Vector3f(s.nextFloat(), s.nextFloat(),
							s.nextFloat());
				}

				// /States + additional data
				if (TempString.equals("VectorA:")) {
					TempVecA = new Vector3f(s.nextFloat(), s.nextFloat(),
							s.nextFloat());
				}
				if (TempString.equals("VectorB:")) {
					TempVecB = new Vector3f(s.nextFloat(), s.nextFloat(),
							s.nextFloat());
				}
				if (TempString.equals("VectorC:")) {
					TempVecC = new Vector3f(s.nextFloat(), s.nextFloat(),
							s.nextFloat());
				}
				if (TempString.equals("State:")) {
					TempStateName = s.next();
				}

				// waves
				if (TempString.equals("Wave:")) {
					TempWave = s.nextInt();
				}

				if (TempString.equals("Health:")) {
					TempHealth = s.nextInt();
				}

				// TempSpellCreationg
				if (TempString.equals("BulletScale:")) {
					TempBulletScale = s.nextFloat();
				}
				
				if (TempString.equals("BulletBlur:")) {
					BulletBlur = s.nextFloat();
					System.out.println("bullet blur changed");
				}
				
				if (TempString.equals("Delay:")) {
					TempDelay = s.nextInt();
				}
				
				if (TempString.equals("BulletCustomModel:")) {
					CustomBulletModel = s.next();
					ModelHandler.AddModel(CustomBulletModel, "black");
					CustomBullet = true;
				}
				
				if (TempString.equals("SpellDelay:")) {
					TempSpellDelay = s.nextInt();
				}
				
				if (TempString.equals("BulletOrient:")) {
					TempOrient = s.next();
				}
				if (TempString.equals("BulletSpellPhase:")) {
					TempSpellPhase = s.nextInt();
				}
				
				//PARTICLE STUFF MAN
				if (TempString.equals("Particle:")) {
					TempParticleName = s.next();
					//System.out.println("Particles Added");
					ModelHandler.AddModel(TempParticleName, "black");
					Particles = true;
				}

				if (TempString.equals("BulletSpell:")) {
					TemporarySpell MyTempSpell = new TemporarySpell();
					MyTempSpell.TempString = s.next();
					MyTempSpell.DataA = s.nextFloat();
					MyTempSpell.DataB = s.nextFloat();
					MyTempSpell.DataC = s.nextFloat();
					MyTempSpell.DataD = s.nextFloat();
					MyTempSpell.DataE = s.nextFloat();
					MyTempSpell.Color.x = s.nextFloat();
					MyTempSpell.Color.y = s.nextFloat();
					MyTempSpell.Color.z = s.nextFloat();
					MyTempSpell.BulletScale = TempBulletScale;
					MyTempSpell.BlurScale = BulletBlur;
					MyTempSpell.BulletSpellDelay = TempSpellDelay;
					MyTempSpell.OrientMode = TempOrient;
					MyTempSpell.BulletSpellPhase = TempSpellPhase;
					if(SecSpell == true){
						MyTempSpell.SecondaryPhase = true;
						MyTempSpell.SecondPhaseRequirement = SecRequire;
						MyTempSpell.TempSecondPhase = SecEffect;
						MyTempSpell.SecondBulletSpell = SecTempSpell;
					}
					if(CustomBullet == true){
						MyTempSpell.CustomBulletName = CustomBulletModel;
						MyTempSpell.CustomBullet = true;
						System.out.println("Custom Bullet Added");
					}
					if(Particles == true){
						MyTempSpell.Particles = true;
						MyTempSpell.ParticleName = TempParticleName;
						System.out.println("Particles Added");
					}
					TempSpellList.add(MyTempSpell);
					System.out.println("BulletSpell Added");
					TempBulletScale = 0;
					SecTempSpell = null;
					SecSpell = false;
					CustomBulletModel = null;
					CustomBullet = false;
					BulletBlur = 1.0f;
					Particles = false;
					TempParticleName = null;
					TempSpellDelay = 0;
					TempOrient = "None";
					TempSpellPhase = 0;
				}
				
				//secondary bullet spell
				if (TempString.equals("SecondPhase:")) {
					SecEffect = s.next();
					SecRequire = s.nextFloat();
					SecSpell = true;
					
				}
				
				
				if (TempString.equals("SecondaryBulletSpell:")) {
					SecTempSpell = new TemporarySpell();
					SecTempSpell.TempString = s.next();
					SecTempSpell.DataA = s.nextFloat();
					SecTempSpell.DataB = s.nextFloat();
					SecTempSpell.DataC = s.nextFloat();
					SecTempSpell.DataD = s.nextFloat();
					SecTempSpell.DataE = s.nextFloat();
					SecTempSpell.Color.x = s.nextFloat();
					SecTempSpell.Color.y = s.nextFloat();
					SecTempSpell.Color.z = s.nextFloat();
					SecTempSpell.BulletScale = TempBulletScale;
					//TempSpellList.add(MyTempSpell);
					System.out.println("Secondary BulletSpell Added");
					TempBulletScale = 0;
				}
				
				
					//boss stuff
				if (TempString.equals("Boss:")) {
					BossOn = true;
					TempPhases = s.nextInt();
				}
				

				// //DIALOGUE
				if (TempString.equals("DialogueData:")) {
					MyTempDialogue = new DialogueEvent();
					MyTempDialogue.Wave = s.nextInt();
					MyTempDialogue.Sequence = s.nextInt();
					MyTempDialogue.CharacterLeft.x = s.nextFloat();
					MyTempDialogue.CharacterLeft.y = s.nextFloat();
					MyTempDialogue.CharacterRight.x = s.nextFloat();
					MyTempDialogue.CharacterRight.y = s.nextFloat();
					MyTempDialogue.CharacterNameLeft = s.next();
					MyTempDialogue.CharacterNameRight = s.next();
					MyTempDialogue.LeftState = s.nextInt();
					MyTempDialogue.RightState = s.nextInt();

					if (MyTempDialogue.Wave > TotalWaves) {
						TotalWaves = TempWave;
					}

					String MyTempString = s.next();
					while (MyTempString.contentEquals("#AddDialogue") == false) {
						if (MyTempString.equals("LeftLineOne:")) {
							MyTempDialogue.LeftLineOne = s.nextLine();
							// System.out.println(MyTempDialogue.LeftLineOne);
							// System.out.println("Line One Added");
						}
						if (MyTempString.equals("LeftLineTwo:")) {
							MyTempDialogue.LeftLineTwo = s.nextLine();
							// System.out.println(MyTempDialogue.LeftLineTwo);
							// System.out.println("Line Two Added");
						}
						if (MyTempString.equals("LeftLineThree:")) {
							MyTempDialogue.LeftLineThree = s.nextLine();
							// System.out.println(MyTempDialogue.LeftLineThree);
							// System.out.println("Line Three Added");
						}

						if (MyTempString.equals("RightLineOne:")) {
							MyTempDialogue.RightLineOne = s.nextLine();
							// System.out.println(MyTempDialogue.RightLineOne);
							// System.out.println("Line One Added");
						}
						if (MyTempString.equals("RightLineTwo:")) {
							MyTempDialogue.RightLineTwo = s.nextLine();
							// System.out.println(MyTempDialogue.RightLineTwo);
							// System.out.println("Line Two Added");
						}
						if (MyTempString.equals("RightLineThree:")) {
							MyTempDialogue.RightLineThree = s.nextLine();
							// System.out.println(MyTempDialogue.RightLineThree);
							// System.out.println("Line Three Added");
						}

						MyTempString = s.next();
					}
					MyDialog.AddDialogueEvent(MyTempDialogue);
					// System.out.println("Dialogue Added");
					MyTempString = null;
					MyTempDialogue = null;
				}

				if (TempString.equals("#AddEntity")) {
					TempEnt = new Entity(TempPosition.x, TempPosition.y, -10,
							TempName);
					//TempEnt.SetWaveNumber(TempWave);
					TempEnt.SetRotSpeed(TempRotSpeed);
					TempEnt.SetScale(TempScale);
					//System.out.println(TempWave);
					LevelEntityList.add(TempEnt);
					System.out.println("EntityMade");

					TempOrbit = 0;
					TempScale = new Vector3f(0, 0, 0);
					TempOffset = new Vector3f(0, 0, 0);
					TempPosition = new Vector2f(0, 0);
					CreatingNewEnt = false;
					TempPhases = 0;
				}

				if (TempString.equals("#AddEnemy")) {
					TempEnt = new Enemy(TempPosition.x, TempPosition.y, -35,TempName);
					TempEnt.SetVecA(TempVecA);
					TempEnt.SetVecB(TempVecB);
					TempEnt.SetVecC(TempVecC);
					TempEnt.SetState(TempStateName);
					TempEnt.SetWaveNumber(TempWave);
					TempEnt.SetHealth(TempHealth);
					TempEnt.SetDelay(TempDelay);
					TempEnt.SetRotSpeed(TempRotSpeed);
					TempEnt.SetPhaseNumber(TempPhases);
					if(BossOn == true){
						TempEnt.SetBoss();
						System.out.println("Enemy Entity Set As Boss");
					}
					// Adding BulletSpells
					for (TemporarySpell TheTempSpell : TempSpellList) {
						// System.out.println("BulletSpell Passed");
						TempEnt.AddBulletSpell(TheTempSpell);
					}
					TempSpellList.clear();
					LevelEntityList.add(TempEnt);
					System.out.println("EntityMade");
					TempRotSpeed = 0;
					TempDelay = 0;
					TempOrbit = 0;
					TempScale = new Vector3f(1, 1, 1);
					TempOffset = new Vector3f(0, 0, 0);
					TempPosition = new Vector2f(0, 0);
					TempHealth = 27;
					if (TempWave > TotalWaves) {
						TotalWaves = TempWave;
					}
					TempWave = 0;
					CreatingEnemy = false;
					BossOn = false;
					TempPhases = 0;
				}

				if (TempString.equals("#AddPlayer")) {
					TempEnt = new Player(-10, 0, -35, TempName);
					TempEnt.SetVecA(TempVecA);
					TempEnt.SetVecB(TempVecB);
					TempEnt.SetVecC(TempVecC);
					TempEnt.SetState(TempStateName);
					TempEnt.SetWaveNumber(TempWave);
					LevelEntityList.add(TempEnt);
					System.out.println("PlayerMade");

					TempOrbit = 0;
					TempScale = new Vector3f(1, 1, 1);
					TempOffset = new Vector3f(0, 0, 0);
					TempPosition = new Vector2f(0, 0);
					CreatingPlayer = false;
				}

				if (TempString.equals("#AddPlanet")) {
					TempEnt = new Planet(40, 0, -50, TempName, TempTexName);
					TempEnt.SetOffset(new Vector3f(TempOffset.x, TempOffset.y,
							TempOffset.z));
					TempEnt.SetScale(new Vector3f(TempScale.x, TempScale.y,
							TempScale.z));
					TempEnt.SetOrbit(TempOrbit);
					TempEnt.SetVecA(TempVecA);
					TempEnt.SetVecB(TempVecB);
					TempEnt.SetVecC(TempVecC);
					TempEnt.SetState(TempStateName);
					TempEnt.SetWaveNumber(TempWave);
					LevelEntityList.add(TempEnt);
					System.out.println("PlanetMade");

					TempOrbit = 0;
					TempScale = new Vector3f(1, 1, 1);
					TempOffset = new Vector3f(0, 0, 0);
					TempPosition = new Vector2f(0, 0);
					CreatingPlanet = false;
				}
				LoadScreen.ShowLoad();
			}

			// System.out.println(s.next());

		} finally {
			s.close();

		}

	}

	public void Update() {
		LightDirection = new Vector4f(BaseLightPos.x + GameData.CameraPosition.x, BaseLightPos.y + GameData.CameraPosition.y, BaseLightPos.z + GameData.CameraPosition.z, 0);
		//GameData.LightPosition = LightDirection;
		//GameData.CameraPosition = new Vector3f(26.0f, -17.0f, -63.0f);
		/*if(Input.GetW() == true){
			GameData.CameraPosition.y++;
		}
		if(Input.GetS() == true){
			GameData.CameraPosition.y--;
		}
		if(Input.GetD() == true){
			GameData.CameraPosition.x++;
		}
		if(Input.GetA() == true){
			GameData.CameraPosition.x--;
		}
		if(Input.GetQ() == true){
			GameData.CameraPosition.z--;
		}
		if(Input.GetE() == true){
			GameData.CameraPosition.z++;
		}*/
		if(Input.GetVReleased() == true){
			CameraState++;
			if(CameraState > 1){
				CameraState = 0;
			}
			GameData.CameraState = CameraState;
			
			if(CameraState == 0){
				GameData.CameraPosition = new Vector3f (0,0,0);
				GameData.CameraRotation = new Vector3f (0,0,0);
			}
			if(CameraState == 1){
				GameData.CameraPosition = new Vector3f (-5, 34, -27);
				GameData.CameraRotation = new Vector3f (0,45,90);
			}
			LightMatrix = new Matrix4f();
			LightMatrix.rotate(GameData.CameraRotation.z, new Vector3f(0, 0 ,1));
			LightMatrix.rotate(GameData.CameraRotation.x, new Vector3f(1, 0 ,0));
			LightMatrix.rotate(GameData.CameraRotation.y, new Vector3f(0, 1 ,0));
			
		}
		//System.out.println(LightDirection);
		Matrix4f.transform(LightMatrix, LightDirection, LightDirection);
		GameData.LightPosition = new Vector3f(LightDirection.x, LightDirection.y, LightDirection.z);
		//System.out.println(GameData.CameraPosition);
		//System.out.println(LightDirection);
		
		
		
		
		//backdrop stuff
		if(HasBackdrop == true){
			MyBackDrop.Update();
		}
		
		// level start / end stuff
		ParticleHandler.UpdateParticles();
		
		if (TotalWaves < WaveNumber || LevelStatus == 0) {
			if (FadeTimer < 1) {
				FadeTimer += 0.0055f;
			}
		} else {
			if (LevelStatus > 1) {
				FadeTimer = 0;
			} else if (LevelStatus == 1) {
				if (FadeTimer > 0) {
					FadeTimer -= 0.0055f;
				}
			}
		}
		if (LevelStatus == 0 && FadeTimer > 1) {
			LevelStatus = 1;
		}
		if (LevelStatus == 1 && FadeTimer < 0) {
			LevelStatus = 2;
		}

		if (LevelStatus == 2 && FadeTimer > 1) {
			if (Input.GetEnterReleased() == true) {
				LevelStatus = 4;
			}
		}

		Player TempPlayer = null;
		for (Entity MyPEnt : LevelEntityList) {
			if (MyPEnt instanceof Player) {
				TempPlayer = (Player) MyPEnt;
			}
		}

		// dialogue Update
		MyDialog.Update(WaveNumber);

		// entity update
		for (Entity MyEnt : LevelEntityList) {

			if (MyEnt.GetWaveNumber() <= WaveNumber) {
				MyEnt.Update();
				MyEnt.CheckHit(TempPlayer);
			}

			if (MyEnt.ReadyToRemove() == true) {
				LevelEntityRemoveList.add(MyEnt);
			}

		}
		for (Entity MyEnt : LevelEntityRemoveList) {
			LevelEntityList.remove(MyEnt);

			System.out.println("Entity Removed");
		}
		LevelEntityRemoveList.clear();
		CheckWave();
	}

	public void Draw() {

		ShaderHandler.ShipShader.Activate();

		MyBack.Draw();

		// entity update
		for (Entity MyEnt : LevelEntityList) {
			if (MyEnt.GetWaveNumber() <= WaveNumber) {
				MyEnt.Draw();
			}
		}
		ShaderHandler.ShipShader.DeActivate();

	}

	public void DrawBullets(float BlurScale) {

		ShaderHandler.BulletBase.Activate();
		
		
		for (Entity MyEnt : LevelEntityList) {

			MyEnt.BulletDraw(BlurScale);

		}

		ShaderHandler.BulletBase.DeActivate();

	}

	public void DrawLighting() {

		float lightPosition[] = { LightDirection.x, LightDirection.y,
				LightDirection.z, 0.0f };
		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());

		if (GameData.CurrentRenderState == RenderState.ShipLight) {
			ShaderHandler.ToonLighting.Activate();

			GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, (FloatBuffer) temp
					.asFloatBuffer().put(lightPosition).flip());

			// entity update
			for (Entity MyEnt : LevelEntityList) {
				if (MyEnt.OnlyEdge() == false) {
					if (MyEnt.GetWaveNumber() <= WaveNumber) {
						MyEnt.Draw();
					}
				}

			}
			ShaderHandler.ToonLighting.DeActivate();
		}

		if (GameData.CurrentRenderState == RenderState.PlanetLight) {
			
			if(HasBackdrop == true){
				ShaderHandler.SpecularShader.Activate();
					MyBackDrop.Draw();
				ShaderHandler.SpecularShader.DeActivate();
			}
			
			
			ShaderHandler.PlanetShader.Activate();

			GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, (FloatBuffer) temp
					.asFloatBuffer().put(lightPosition).flip());
			
			ShaderHandler.ParticleShader.Activate();
			ParticleHandler.DrawParticles();
			ShaderHandler.ParticleShader.DeActivate();
			
			ShaderHandler.PlanetShader.Activate();
			// entity update
			for (Entity MyEnt : LevelEntityList) {
				if (MyEnt instanceof Planet) {
					MyEnt.Draw();
				}

			}
			ShaderHandler.PlanetShader.DeActivate();
		}

	}

	public void DrawUI() {

		ShaderHandler.ShipShader.Activate();

		FontHandler.ElitePro.DisplayFont(1560, 1000, 0.8f, "Score: " + GameData.LevelScore);

		ShaderHandler.ShipShader.DeActivate();
		ShaderHandler.FlatColourShader.Activate();

		if (GameData.PlayerInvuln == false) {
			drawRectangle(20, 1050, 7f * GameData.PlayerHealth, 10,
					new Vector3f(1, 0.2f, 0.2f), new Vector3f(1, 0, 0));
			drawRectangle(20, 1040, 7f * GameData.PlayerHealth, 10,
					new Vector3f(1, 0, 0), new Vector3f(1, 0.2f, 0.2f));
		} else {
			drawRectangle(20, 1050, 7f * GameData.PlayerHealth, 10,
					new Vector3f(0.2f, 0.2f, 1f), new Vector3f(0, 0, 1));
			drawRectangle(20, 1040, 7f * GameData.PlayerHealth, 10,
					new Vector3f(0, 0, 1), new Vector3f(0.2f, 0.2f, 1f));
		}
		
		if(GameData.MaxBossHealth > 0){
			drawRectangle(1860, 512, 20, 0.5f * GameData.BossHealth,new Vector3f(0.0f, 0.0f, 1f), new Vector3f(1, 1, 1));
			//drawRectangle(1860, 12 + (1 * (GameData.MaxBossHealth - GameData.BossHealth))/2, 20, 0.5f * GameData.BossHealth,new Vector3f(1.0f, 1.0f, 1f), new Vector3f(0, 0, 1));
			drawRectangle(1860, 512, 20, -0.5f * GameData.BossHealth,new Vector3f(0.0f, 0.0f, 1f), new Vector3f(1, 1, 1));
		}
		//GameData.BossHealth--;
		
		
		// drawRectangle(20, 1000, 200, 10, new Vector3f(1,0,0));

		ShaderHandler.FlatColourShader.DeActivate();
		ShaderHandler.FadingTextShader.Activate();

		if (TotalWaves < WaveNumber) {
			GL11.glLoadIdentity();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelComplete.getTextureID());
			drawRectangle(704, 256, 512, 512, FadeTimer);
		}

		if (LevelStatus < 2) {
			GL11.glLoadIdentity();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, LevelOpening.getTextureID());
			drawRectangle(704, 256, 512, 512, FadeTimer);
		}
		ShaderHandler.FadingTextShader.DeActivate();

		MyDialog.DrawDialogue(WaveNumber);
	}

	public void DrawDepth(){
		
		//ShaderHandler.DepthShader.Activate();
		
		if(HasBackdrop == true){
			MyBackDrop.SetBoss();
		}
		
		for (Entity MyEnt : LevelEntityList) {
			
			MyEnt.BulletDraw(0.25f);
			MyEnt.BulletDraw(0.5f);
			MyEnt.BulletDraw(1);

		}
		
		for (Entity MyEnt : LevelEntityList) {
			if (MyEnt.OnlyEdge() == false) {
				if (MyEnt.GetWaveNumber() <= WaveNumber) {
						MyEnt.Draw();
				}
			}
		}
		
		//ShaderHandler.DepthShader.DeActivate();
		
	}
	
	public void CheckWave() {

		int MyEntLeft = 0;

		for (Entity MyEnt : LevelEntityList) {

			if (MyEnt.GetWaveNumber() == WaveNumber) {
				if (MyEnt.ReadyForNextWave() == true) {
					MyEntLeft++;
				}
			}

		}
		// System.out.println(MyEntLeft);
		if (MyEntLeft == 0 && MyDialog.WaveComplete(WaveNumber) == true
				&& WaveNumber <= TotalWaves) {
			WaveNumber++;
		}
		MyEntLeft = 0;

	}

	private void drawRectangle(float x, float y, float width, float height,
			Vector3f Color) {
		// MyFont.SwitchToOrth();
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(Color.x, Color.y, Color.z);
		GL11.glVertex2f(x, y);

		GL11.glColor3f(Color.x, Color.y, Color.z);
		GL11.glVertex2f(x + width, y);

		GL11.glColor3f(Color.x, Color.y, Color.z);
		GL11.glVertex2f(x + width, y + height);

		GL11.glColor3f(Color.x, Color.y, Color.z);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		// MyFont.SwitchToPerspective();
	}

	private void drawRectangle(float x, float y, float width, float height,
			Vector3f UpColor, Vector3f DownColor) {
		// MyFont.SwitchToOrth();
		GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(DownColor.x, DownColor.y, DownColor.z);
		GL11.glVertex2f(x, y);

		GL11.glColor3f(DownColor.x, DownColor.y, DownColor.z);
		GL11.glVertex2f(x + width, y);

		GL11.glColor3f(UpColor.x, UpColor.y, UpColor.z);
		GL11.glVertex2f(x + width, y + height);

		GL11.glColor3f(UpColor.x, UpColor.y, UpColor.z);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		// MyFont.SwitchToPerspective();
	}

	private void drawRectangle(float x, float y, float width, float height,
			float alpha) {
		// MyFont.SwitchToOrth();
		// GL11.glLoadIdentity();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glColor3f(alpha, 0, 0);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x, y);

		GL11.glColor3f(alpha, 0, 0);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + width, y);

		GL11.glColor3f(alpha, 0, 0);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + width, y + height);

		GL11.glColor3f(alpha, 0, 0);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x, y + height);
		GL11.glEnd();
		// MyFont.SwitchToPerspective();
	}

	public boolean LevelOver() {
		if (LevelStatus == 4) {
			return true;
		} else {
			return false;
		}
	}
}
