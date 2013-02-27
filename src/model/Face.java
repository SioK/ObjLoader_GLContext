package model;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Face {

	private boolean isQuad;

	private Vector4f vertexIndices = new Vector4f();
	private Vector4f normalIndices = new Vector4f();
	private Vector4f textureIndices = new Vector4f();

	public Face(Vector3f vertexIndices, Vector3f textureIndices, Vector3f normalIndices) {
		this.vertexIndices = new Vector4f(vertexIndices.x, vertexIndices.y, vertexIndices.z,0);
		this.normalIndices = new Vector4f(normalIndices.x, normalIndices.y, normalIndices.z,0);
		this.textureIndices = new Vector4f(textureIndices.x, textureIndices.y, textureIndices.z,0);
	}

	public Face(Vector4f vertexIndices, Vector4f textureIndices, Vector4f normalIndices) {
		this.vertexIndices = vertexIndices;
		this.normalIndices = normalIndices;
		this.textureIndices = textureIndices;
		isQuad = true;
	}
	
	public Vector4f getVertexIndices() {
		return vertexIndices;
	}
	
	public Vector4f getTextureIndices() {
		return textureIndices;
	}
	
	public Vector4f getNormalIndices() {
		return normalIndices;
	}
	
	public boolean isQuad() {
		return isQuad;
	}

}
