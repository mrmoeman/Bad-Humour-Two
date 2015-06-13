
class ShaderHandler {
	
	
	static ShaderProgram ShipShader;
	
	static ShaderProgram FinalShader;
	
	static ShaderProgram EdgeDetection;
	
	static ShaderProgram ToonLighting;
	
	static ShaderProgram BulletBase;
	
	static ShaderProgram XBlur; 
	
	static ShaderProgram YBlur; 
	
	static ShaderProgram PlanetShader; 
	
	static ShaderProgram FlatColourShader; 
	
	static ShaderProgram FadingTextShader; 
	
	static ShaderProgram BulletFixerShader; 
	
	static ShaderProgram ParticleShader; 
	
	static ShaderProgram SpecularShader;
	
	static ShaderProgram DepthShader;
	
	static ShaderProgram SpaceStationShader;
	
	static ShaderProgram WhiteShader;
	
	static void ShaderSetup(){
		
		ShipShader = new ShaderProgram("Res/Shaders/shipshader.vert", "Res/Shaders/shipshader.frag");
		System.out.println("shipshader done");
		FinalShader = new ShaderProgram("Res/Shaders/finalshader.vert", "Res/Shaders/finalshader.frag");
		System.out.println("finalshader done");
		EdgeDetection = new ShaderProgram("Res/Shaders/edgedetection.vert", "Res/Shaders/edgedetection.frag");
		System.out.println("edgeshader done");
		ToonLighting = new ShaderProgram("Res/Shaders/toonshader.vert", "Res/Shaders/toonshader.frag");
		System.out.println("toonshader done");
		BulletBase = new ShaderProgram("Res/Shaders/bulletbaseshader.vert", "Res/Shaders/bulletbaseshader.frag");
		System.out.println("bbshader done");
		XBlur = new ShaderProgram("Res/Shaders/glowblurxshader.vert", "Res/Shaders/glowblurxshader.frag");
		System.out.println("xblurshader done");
		YBlur = new ShaderProgram("Res/Shaders/glowbluryshader.vert", "Res/Shaders/glowbluryshader.frag");
		System.out.println("yblurshader done");
		PlanetShader = new ShaderProgram("Res/Shaders/PlanetShader.vert", "Res/Shaders/PlanetShader.frag");
		System.out.println("PlanetShader done");
		FlatColourShader = new ShaderProgram("Res/Shaders/FlatColour.vert", "Res/Shaders/FlatColour.frag");
		System.out.println("FlatColourShader done");
		FadingTextShader = new ShaderProgram("Res/Shaders/FadingTextShader.vert", "Res/Shaders/FadingTextShader.frag");
		System.out.println("FadingTextShader done");
		BulletFixerShader = new ShaderProgram("Res/Shaders/BulletFixer.vert", "Res/Shaders/BulletFixer.frag");
		System.out.println("BulletFixerShader done");
		ParticleShader = new ShaderProgram("Res/Shaders/ParticleShader.vert", "Res/Shaders/ParticleShader.frag");
		System.out.println("ParticleShader done");
		SpecularShader = new ShaderProgram("Res/Shaders/specularshader.vert", "Res/Shaders/specularshader.frag");
		System.out.println("SpecularShader done");
		DepthShader = new ShaderProgram("Res/Shaders/DepthShader.vert", "Res/Shaders/DepthShader.frag");
		System.out.println("DepthShader done");
		SpaceStationShader = new ShaderProgram("Res/Shaders/SpaceStationShader.vert", "Res/Shaders/SpaceStationShader.frag");
		System.out.println("SpaceStationShader done");
		WhiteShader = new ShaderProgram("Res/Shaders/WhiteShader.vert", "Res/Shaders/WhiteShader.frag");
		System.out.println("WhiteShader done");
	}

}
