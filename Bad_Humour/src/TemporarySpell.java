import org.lwjgl.util.vector.Vector3f;


public class TemporarySpell {
	float DataA;
	float DataB;
	float DataC;
	float DataD;
	float DataE;
	String TempString;
	float BulletScale = 0;
	Vector3f Color = new Vector3f(0,0,0);
	String CustomBulletName = null;
	boolean CustomBullet = false;
	int BulletSpellPhase = 0;	
	boolean SecondaryPhase = false;
	String TempSecondPhase = null;
	float SecondPhaseRequirement = 0;
	TemporarySpell SecondBulletSpell;
	
	boolean Particles = false;
	String ParticleName = null;
	
	int BulletSpellDelay = 0;
	
	float BlurScale = 1.0f;
	
	String OrientMode = "None";
			
	public TemporarySpell(){
		
	}
}
