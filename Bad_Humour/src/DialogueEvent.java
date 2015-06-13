import org.lwjgl.util.vector.Vector2f;


public class DialogueEvent {
	
	int Wave;
	int Sequence;
	Vector2f CharacterLeft = new Vector2f(0,0);
	Vector2f CharacterRight = new Vector2f(0,0);
	String CharacterNameLeft = " ";
	String CharacterNameRight = " ";
	int LeftState;
	int RightState;
	String LeftLineOne = " ";
	String LeftLineTwo = " ";
	String LeftLineThree = " ";
	String RightLineOne = " ";
	String RightLineTwo = " ";
	String RightLineThree = " ";
	boolean Done = false;
	
	public DialogueEvent(){
		
		
		
	}

}
