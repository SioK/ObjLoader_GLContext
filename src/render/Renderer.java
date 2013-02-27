package render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Renderer implements IRenderer {

	@Override
	public void render() {
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glColor3f(1, 0, 0);
			GL11.glVertex3f(-1.0f, 1.0f, 0.0f);              // Top Left
			GL11.glVertex3f( 1.0f, 1.0f, 0.0f);              // Top Right
			GL11.glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
			GL11.glVertex3f(-1.0f,-1.0f, 0.0f); 			 // Bottom Left
		GL11.glEnd();
	}

}
