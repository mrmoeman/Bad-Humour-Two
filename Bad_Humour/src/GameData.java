import org.lwjgl.util.vector.Vector3f;


class GameData {
	
	static String LevelName = "TestLevel";
	static boolean LevelLoaded = false;
	static boolean BeginLoading = false;
	
	static RenderState CurrentRenderState = RenderState.ShipBase;
	
	static int PlayerDamage;
	static int PlayerHealth;
	static Vector3f PlayerPosition;
	static boolean PlayerInvuln;
	static int PlayerPower = 0;
	
	static Vector3f BombPosition;
	static boolean BombOn = false;
	static float BombRadius;
	
	static int LevelScore = 0;
	
	static int BossHealth = 0;
	static int MaxBossHealth = 0;
	
	static Vector3f LightPosition = new Vector3f(0,0,0);
	
	static Vector3f CameraPosition = new Vector3f(0,0,0);
	static Vector3f CameraRotation = new Vector3f(0,0,0);
	static int CameraState = 0;
	
	static Vector3f PreviousCameraPosition = new Vector3f(0,0,0);
	static Vector3f PreviousCameraRotation = new Vector3f(0,0,0);
	
	static int SHADOWTEXTURE;

}
