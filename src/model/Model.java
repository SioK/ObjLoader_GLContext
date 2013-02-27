package model;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Model {

	public List<Vector3f> vertices = new ArrayList<>();
	public List<Vector3f> normals = new ArrayList<>();
	public List<Vector2f> textures = new ArrayList<>();
	public List<Face> faces = new ArrayList<>();

	public static int vboNormalID;
	public static int vboVertexID;

	public void createVBO() {

		
		FloatBuffer vertexBuffer = reserveData(vertices.size() * 36);
		FloatBuffer normalBuffer = reserveData(normals.size() * 36);

		for (Face face : faces) {
			vertexBuffer.put(asFloats(vertices.get((int) face.getVertexIndices().x-1)));
			vertexBuffer.put(asFloats(vertices.get((int) face.getVertexIndices().y-1)));
			vertexBuffer.put(asFloats(vertices.get((int) face.getVertexIndices().z-1)));
			
			normalBuffer.put(asFloats(normals.get((int) face.getNormalIndices().x-1)));
			normalBuffer.put(asFloats(normals.get((int) face.getNormalIndices().y-1)));
			normalBuffer.put(asFloats(normals.get((int) face.getNormalIndices().z-1)));

		}
	
		vertexBuffer.flip();
		normalBuffer.flip();
		
		vboVertexID = GL15.glGenBuffers();
		vboNormalID = GL15.glGenBuffers();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // UNBIND
		
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboNormalID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // UNBIND
	
	}

	public void draw() {

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexID);
		GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboNormalID);
		GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
		
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, faces.size() * 3);

	}

	public FloatBuffer reserveData(int size) {
		FloatBuffer data = BufferUtils.createFloatBuffer(size);
		return data;
	}
	
	public float[] asFloats(Vector3f v) {
		return new float[]{v.x, v.y, v.z};
	}

}


