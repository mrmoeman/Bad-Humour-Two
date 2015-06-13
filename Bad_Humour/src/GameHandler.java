
public class GameHandler {
	
	private GameState CurrentState = GameState.Main_Menu;
	private Level MyLevel;
	private LevelSelect MySelect;
	private Menu MyMenu;
	
	public void SetUpMenus(){
		MyMenu = new Menu();
		MySelect = new LevelSelect();
	}
	
	public void Update(){
	
		
		/*
		if (GameData.LevelName.isEmpty() == false && GameData.LevelLoaded == false){
			//CurrentState = GameState.Level_Running;
			MyLevel = new Level(GameData.LevelName);
			GameData.LevelLoaded = true;
		}*/
		if(GameData.BeginLoading == true){
			CurrentState= GameState.Level_Loading;
			MyLevel = new Level(GameData.LevelName);
			GameData.LevelLoaded = true;
			GameData.BeginLoading = false;
			GameData.LevelScore = 0;
			CurrentState= GameState.Level_Running;
		}
		
		
		if(CurrentState == GameState.Level_Running && GameData.LevelLoaded == true){
			LevelUpdate();
		}
		if(CurrentState == GameState.Level_Select){
			MySelect.Update();
		}
		
		if(CurrentState == GameState.Main_Menu){
			MyMenu.Update();
			
			if(MyMenu.Done()==true){
				CurrentState = GameState.Level_Select;
			}
			
		}
		
	}
	
	public void LevelUpdate(){
		MyLevel.Update();
		
		if(MyLevel.LevelOver() == true){
			CurrentState = GameState.Level_Select;
			MyLevel = null;
			ModelHandler.ClearModels();
			System.out.println("Model Handler Wiped");
		}
	}

	public void Draw(){
		
		
		if(CurrentState == GameState.Level_Running && GameData.LevelLoaded == true){
			MyLevel.Draw();
		}
		
		
		if(CurrentState == GameState.Main_Menu){
			MyMenu.drawBaseMenu();
		}
	}
	
	public void DrawBullets(float BlurScale){
		
		if(CurrentState == GameState.Level_Running && GameData.LevelLoaded == true){
			MyLevel.DrawBullets(BlurScale);
		}
		
	}
	
	public void DrawLighting(){
		
		
		if(CurrentState == GameState.Level_Running && GameData.LevelLoaded == true){
			MyLevel.DrawLighting();
			
		}
		
		if(CurrentState == GameState.Main_Menu){
			MyMenu.drawLightingMenu();
		}
	}
	
	public void DrawUI(){
	
		if(CurrentState == GameState.Level_Running && GameData.LevelLoaded == true){
			MyLevel.DrawUI();
		}
		if(CurrentState == GameState.Level_Select){
			MySelect.DrawLevelSelect();
		}
		
		if(CurrentState == GameState.Main_Menu){
			MyMenu.drawUI();
		}
	
	}

	public void DrawDepth(){
		if(CurrentState == GameState.Level_Running  && GameData.LevelLoaded == true){
			MyLevel.DrawDepth();
		}
	}
	
}
