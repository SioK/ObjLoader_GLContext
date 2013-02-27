package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class OBJLoader {

	public Model loadModel(File file) throws FileNotFoundException, IOException {
		Model model = new Model();
		BufferedReader br = new BufferedReader(new FileReader(file));

		String line;
		while((line = br.readLine()) != null) {
			if(line.startsWith("v ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				model.vertices.add(new Vector3f(x, y, z));
			}

			if(line.startsWith("vn ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				model.normals.add(new Vector3f(x, y, z));
			}

			if(line.startsWith("vt ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				model.textures.add(new Vector2f(x, y));
			}

			if(line.startsWith("f ")) {
				String[] faceCoord = line.split(" ");
				List<Float> tmp = new ArrayList<Float>();
				
				if(faceCoord.length == 5) {
					
					Float[] v_i = new Float[4];
					Float[] t_i = new Float[4];
					Float[] n_i = new Float[4];
					for(int i = 1; i <= 4; i++) {

						tmp.add(Float.valueOf(faceCoord[i].split("/")[0]));
						
						if(hasTextureCoord(faceCoord[i])) {
							tmp.add(Float.valueOf(faceCoord[i].split("/")[1]));
						} else {
							tmp.add(new Float(0));
						}
						
						if(hasNormalCoord(faceCoord[i])) {
							tmp.add(Float.valueOf(faceCoord[i].split("/")[2]));
						} else {
							tmp.add(new Float(0));
						}
					}

					for(int i = 0; i < tmp.size()/3; i++) {
						v_i[i] = tmp.get(i*3);
						t_i[i] = tmp.get(i*3+1);
						n_i[i] = tmp.get(i*3+2);						
					}
				
					model.faces.add(new Face(
							new Vector4f(v_i[0], v_i[1], v_i[2], v_i[3]), 
							new Vector4f(t_i[0], t_i[1], t_i[2], t_i[3]), 
							new Vector4f(n_i[0], n_i[1], n_i[2], n_i[3])));
					
				} else {
							
					Float[] v_i = new Float[3];
					Float[] t_i = new Float[3];
					Float[] n_i = new Float[3];
					
					for(int i = 1; i <= 3; i++) {

						tmp.add(Float.valueOf(faceCoord[i].split("/")[0]));
						
						if(hasTextureCoord(faceCoord[i])) {
							tmp.add(Float.valueOf(faceCoord[i].split("/")[1]));
						} else {
							tmp.add(new Float(0));
						}
						
						if(hasNormalCoord(faceCoord[i])) {
							tmp.add(Float.valueOf(faceCoord[i].split("/")[2]));
						} else {
							tmp.add(new Float(0));
						}
					}
					
					for(int i = 0; i < tmp.size()/3; i++) {
						v_i[i] = tmp.get(i*3);
						t_i[i] = tmp.get(i*3+1);
						n_i[i] = tmp.get(i*3+2);						
					}

					model.faces.add(new Face(
							new Vector3f(v_i[0], v_i[1], v_i[2]), 
							new Vector3f(t_i[0], t_i[1], t_i[2]), 
							new Vector3f(n_i[0], n_i[1], n_i[2])));
				}
					
			}

		}
		br.close();

		return model;
	}
	
	public boolean hasTextureCoord(String faceCoord) {
		return faceCoord.matches("\\w+/\\w+/\\w+|\\w+/\\w+");
	}
	
	public boolean hasNormalCoord(String faceCoord) {
		return faceCoord.matches("\\w+/\\w+/\\w+|\\w+//\\w+");
	}

}
