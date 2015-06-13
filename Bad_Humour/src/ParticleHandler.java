import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;


class ParticleHandler {
	
	
	static ArrayList<Particle> ParticleList = new ArrayList<>();
	static ArrayList<Particle> ParticleRemoveList = new ArrayList<>();
	
	public static void AddParticle(String ModelName, Vector3f ParticleColour, Vector3f MyPosition, float MyFadeTime){
		Particle TempParticle = new Particle(ModelName, ParticleColour, MyPosition, MyFadeTime);
		ParticleList.add(TempParticle);
		TempParticle = null;
	}
	
	
	public static void UpdateParticles(){
		
		for(Particle MyParticle: ParticleList){
			MyParticle.Update();
			if(MyParticle.CheckForRemove() == true){
				ParticleRemoveList.add(MyParticle);
			}
		}
		for(Particle MyParticle: ParticleRemoveList){
			ParticleList.remove(MyParticle);
		}
		ParticleRemoveList.clear();
		
	}
	
	public static void DrawParticles(){
		
		for(Particle MyParticle: ParticleList){
			MyParticle.Draw();
		}
	}
	
	
}
