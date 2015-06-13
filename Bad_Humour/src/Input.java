import org.lwjgl.input.Keyboard;


class Input {
	
	static boolean EnterDown = false;
	static boolean EnterReleased = false;
	
	static boolean VDown = false;
	static boolean VReleased = false;
	
	static boolean GetEnter(){
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetEnterReleased(){
		if(GetEnter() == true){
			EnterDown = true;
			EnterReleased = false;
		}
		else{
			EnterReleased = true;
		}
		if(EnterReleased == true && EnterDown == true){
			EnterDown = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	static boolean GetV(){
		if(Keyboard.isKeyDown(Keyboard.KEY_V)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetVReleased(){
		if(GetV() == true){
			VDown = true;
			VReleased = false;
		}
		else{
			VReleased = true;
		}
		if(VReleased == true && VDown == true){
			VDown = false;
			return true;
		}
		else{
			return false;
		}
	}
	
	static boolean GetSpace(){
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetUp(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {  
            return true;
        }
		else{
			return false;
		}
	}
	
	
	static boolean GetDown(){
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetRight(){
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { 
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetLeft(){
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetZ(){
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetX(){
		if(Keyboard.isKeyDown(Keyboard.KEY_X)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetC(){
		if(Keyboard.isKeyDown(Keyboard.KEY_C)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	public static boolean GetW(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_W)) {       // Exit if Escape is pressed
	            return true;
	        }
		 else { return false;}
	
	}
	
	public static boolean GetS(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_S)) {       // Exit if Escape is pressed
	            return true;
	        }
		 else { return false;}
	
	}
	
	public static boolean GetD(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_D)) {       // Exit if Escape is pressed
	            return true;
	        }
		 else { return false;}
	
	}
	
	public static boolean GetA(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_A)) {       // Exit if Escape is pressed
	            return true;
	        }
		 else { return false;}
	
	}
	
	public static boolean GetQ(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {       // Exit if Escape is pressed
	            return true;
	        }
		 else { return false;}
	
	}
	
	public static boolean GetE(){
		
		 if(Keyboard.isKeyDown(Keyboard.KEY_E)) {       // Exit if Escape is pressed
	            return true;
	        }
		 else { return false;}
	
	}
	
	
	static boolean GetF2(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF3(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF4(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F4)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF5(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F5)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF6(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F6)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF7(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F7)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF8(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F8)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF9(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F9)) {   
            return true;
        }
		else{
			return false;
		}
	}
	
	static boolean GetF10(){
		if(Keyboard.isKeyDown(Keyboard.KEY_F10)) {   
            return true;
        }
		else{
			return false;
		}
	}

}
