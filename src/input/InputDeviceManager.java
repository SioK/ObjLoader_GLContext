package input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputDeviceManager {

	private float rotX, rotY, rotZ, transX, transY, transZ, scaleFactor, zoom = 0; 

	public void keyboardEvents() {

		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			rotY += 0.5;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			rotY -= 0.5;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			rotX -= 0.5;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			rotX += 0.5;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
			transZ += 0.5;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
			transZ -= 0.5;
		}

		while(Keyboard.next()) {
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				transY += 0.5;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				transY -= 0.5;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				transX += 0.5;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				transX -= 0.5;
			}
		}


	}

	public void mouseEvents() {
		if(Mouse.isButtonDown(0)) {
			System.out.println("left clicked");
		}

		if(Mouse.isButtonDown(1)) {
			System.out.println("right clicked");
		}
	}

	public float rotateX() {
		return this.rotX;
	}

	public float rotateY() {
		return this.rotY;
	}

	public float rotateZ() {
		return this.rotZ;
	}

	public float translateX() {
		return this.transX;
	}

	public float translateY() {
		return this.transY;
	}

	public float translateZ() {
		return this.transZ;
	}

	public float scaleFactor() {
		return this.scaleFactor;
	}

	public float zoomFactor() {
		return this.zoom;
	}
}
