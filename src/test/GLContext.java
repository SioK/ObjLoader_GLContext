package test;

import input.InputDeviceManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import model.Face;
import model.Model;
import model.OBJLoader;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import render.Renderer;


public class GLContext {

	private static final String TITLE = "Y4D3";
	long lastFPS;
	int fps;

	Model m;

	public static void main (String[] args) {
		GLContext ctx = new GLContext();
		ctx.start();
	}

	public void start() {

		OBJLoader loader = new OBJLoader();
		try {
			m = loader.loadModel(new File("C:/Users/SioK/workspace/GLTest/ak47_triangles.obj"));
			System.out.println("Model Loading Complete");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Display.setTitle(TITLE);
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setResizable(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		//init openGL
		initializeGL();

		//Renderer renderer = new Renderer();

		InputDeviceManager deviceManager = new InputDeviceManager();

		m.createVBO();
		int shaderProgram = createShaderProgram();
		GL20.glUseProgram(shaderProgram);


		while (!Display.isCloseRequested()) {

			// human inputs

			deviceManager.keyboardEvents();
			deviceManager.mouseEvents();

			float rotX = deviceManager.rotateX();
			float rotY = deviceManager.rotateY();

			float transZ = deviceManager.translateZ();
			float transY = deviceManager.translateY();
			float transX = deviceManager.translateX();



			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);


			GL11.glClearColor(0, 0, 0, 1);
			GL11.glLoadIdentity();
			GLU.gluLookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);

			GL11.glTranslatef(0, 0, transZ);
			GL11.glTranslatef(0, transY, 0);
			GL11.glTranslatef(transX, 0, 0);


			GL11.glRotatef(rotX, 1, 0, 0);
			GL11.glRotatef(rotY, 0, 1, 0);


			GL11.glColor3f(0.4f, 0.27f, 0.17f);
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 128.0f);

			m.draw();


			// render code here
			//renderer.render();
			resizeGL();
			Display.update();
			Display.sync(60);
			updateFPS();
		}

		GL20.glDeleteProgram(shaderProgram);
		Display.destroy();
	}

	/*
	 * initialize GL
	 */
	public void initializeGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective (60, (float)Display.getWidth() / (float)Display.getHeight(), 1.0f, 10000.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// Z Buffer Clipping 
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		// Lighting

		FloatBuffer lightColor = BufferUtils.createFloatBuffer(GL11.GL_FLOAT * 4);
		lightColor.put(new float[]{1.5f,1.5f,1.5f,1});
		lightColor.flip();

		FloatBuffer ambientColor = BufferUtils.createFloatBuffer(GL11.GL_FLOAT * 4);
		ambientColor.put(new float[]{0.5f,0.5f,0.5f,1});
		ambientColor.flip();


		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, ambientColor);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightColor);

		// Backface Culling
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);


		GL11.glEnable(GL11.GL_SMOOTH);

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE);

	}

	public void resizeGL() {
		GL11.glViewport (0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity ();
		GLU.gluPerspective (60, (float)Display.getWidth() / (float)Display.getHeight(), 1.0f, 10000.0f);
		GL11.glMatrixMode (GL11.GL_MODELVIEW);
	}

	public int createShaderProgram() {
		int shaderProgram = GL20.glCreateProgram();
		int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		StringBuilder vertexShaderSource = loadShaderSource("src/ressources/shader.vert");
		StringBuilder fragmentShaderSource = loadShaderSource("src/ressources/shader.frag");

		GL20.glShaderSource(vertexShader, vertexShaderSource);
		GL20.glCompileShader(vertexShader);

		if(GL20.glGetShader(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Compile error in Vertex Shader");
		}

		GL20.glShaderSource(fragmentShader, fragmentShaderSource);
		GL20.glCompileShader(fragmentShader);

		if(GL20.glGetShader(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Compile error in Fragment Shader");
		}

		GL20.glAttachShader(shaderProgram, vertexShader);
		GL20.glAttachShader(shaderProgram, fragmentShader);

		GL20.glLinkProgram(shaderProgram);
		GL20.glValidateProgram(shaderProgram);

		return shaderProgram;

	}

	public StringBuilder loadShaderSource(String path) {
		StringBuilder vertexShaderSource = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while((line = reader.readLine()) != null) {
				vertexShaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Unable to load Shader: " + path);
			System.exit(1);
			e.printStackTrace();
		}

		return vertexShaderSource;
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void updateFPS() {

		if (getTime() - lastFPS > 1000) {
			Display.setTitle(TITLE + " - FPS: " /*+ fps*/);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
}
