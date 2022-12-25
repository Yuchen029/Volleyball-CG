package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class TexCube {

	
	public TexCube() {

	}
	
	// Implement using notes  and looking at TexSphere
	public void DrawTexCube(float length) {

		// 8 vertices of the cube
		Point4f vertices[] = { 	new Point4f(-1.0f * length, -1.0f * length, -1.0f * length,0.0f),
								new Point4f(-1.0f * length, -1.0f * length, 1.0f * length,0.0f),
								new Point4f(-1.0f * length, 1.0f * length, -1.0f * length,0.0f),
								new Point4f(-1.0f * length, 1.0f * length, 1.0f * length,0.0f),
								new Point4f(1.0f * length, -1.0f * length, -1.0f * length,0.0f),
								new Point4f(1.0f * length, -1.0f * length, 1.0f * length,0.0f),
								new Point4f(1.0f * length, 1.0f * length, -1.0f * length,0.0f),
								new Point4f(1.0f * length, 1.0f * length, 1.0f * length,0.0f) };

		GL11.glBegin(GL11.GL_QUADS);

		/*
			the faces of the cube
					-----
				    | 4 |
			-----------------
			| 5	| 2 | 0 | 3 |
			-----------------
					| 1 |
					-----

		 */

			// face 0
			// produce two vectors in each face by using three vertices of the face
			Vector4f v1 = vertices[2].MinusPoint(vertices[0]);
			Vector4f w1 = vertices[4].MinusPoint(vertices[0]);
			// using two vectors to produce the normal vector of each face
			Vector4f normal1 = v1.cross(w1).Normal();
			// set the direction of each face and produce the shade
			GL11.glNormal3f(normal1.x, normal1.y, normal1.z);
			// draw the face by using four vertices of the face
			GL11.glTexCoord2f(0.5f, 0.25f);
			GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
			GL11.glTexCoord2f(0.5f, 0);
			GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);
			GL11.glTexCoord2f(0.75f, 0);
			GL11.glVertex3f(vertices[6].x, vertices[6].y, vertices[6].z);
			GL11.glTexCoord2f(0.75f, 0.25f);
			GL11.glVertex3f(vertices[4].x, vertices[4].y, vertices[4].z);

			// face 1
			// produce two vectors in each face by using three vertices of the face
			Vector4f v2 = vertices[0].MinusPoint(vertices[1]);
			Vector4f w2 = vertices[5].MinusPoint(vertices[1]);
			// using two vectors to produce the normal vector of each face
			Vector4f normal2 = v2.cross(w2).Normal();
			// set the direction of each face and produce the shade
			GL11.glNormal3f(normal2.x, normal2.y, normal2.z);
			// draw the face by using four vertices of the face
			GL11.glTexCoord2f(0.5f, 0.5f);
			GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);
			GL11.glTexCoord2f(0.5f, 0.25f);
			GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
			GL11.glTexCoord2f(0.75f, 0.25f);
			GL11.glVertex3f(vertices[4].x, vertices[4].y, vertices[4].z);
			GL11.glTexCoord2f(0.75f, 0.5f);
			GL11.glVertex3f(vertices[5].x, vertices[5].y, vertices[5].z);

			// face 2
			// produce two vectors in each face by using three vertices of the face
			Vector4f v3 = vertices[2].MinusPoint(vertices[3]);
			Vector4f w3 = vertices[1].MinusPoint(vertices[3]);
			// using two vectors to produce the normal vector of each face
			Vector4f normal3 = v3.cross(w3).Normal();
			// set the direction of each face and produce the shade
			GL11.glNormal3f(normal3.x, normal3.y, normal3.z);
			// draw the face by using four vertices of the face
			GL11.glTexCoord2f(0.25f, 0.5f);
			GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);
			GL11.glTexCoord2f(0.25f, 0.25f);
			GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);
			GL11.glTexCoord2f(0.5f, 0.25f);
			GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
			GL11.glTexCoord2f(0.5f, 0.5f);
			GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);

			// face 3
			// produce two vectors in each face by using three vertices of the face
			Vector4f v4 = vertices[4].MinusPoint(vertices[5]);
			Vector4f w4 = vertices[7].MinusPoint(vertices[5]);
			// using two vectors to produce the normal vector of each face
			Vector4f normal4 = v4.cross(w4).Normal();
			// set the direction of each face and produce the shade
			GL11.glNormal3f(normal4.x, normal4.y, normal4.z);
			// draw the face by using four vertices of the face
			GL11.glTexCoord2f(0.75f, 0.5f);
			GL11.glVertex3f(vertices[5].x, vertices[5].y, vertices[5].z);
			GL11.glTexCoord2f(0.75f, 0.25f);
			GL11.glVertex3f(vertices[4].x, vertices[4].y, vertices[4].z);
			GL11.glTexCoord2f(1, 0.25f);
			GL11.glVertex3f(vertices[6].x, vertices[6].y, vertices[6].z);
			GL11.glTexCoord2f(1, 0.5f);
			GL11.glVertex3f(vertices[7].x, vertices[7].y, vertices[7].z);

			// face 4
			// produce two vectors in each face by using three vertices of the face
			Vector4f v5 = vertices[1].MinusPoint(vertices[3]);
			Vector4f w5 = vertices[7].MinusPoint(vertices[3]);
			// using two vectors to produce the normal vector of each face
			Vector4f normal5 = v5.cross(w5).Normal();
			// set the direction of each face and produce the shade
			GL11.glNormal3f(normal5.x, normal5.y, normal5.z);
			// draw the face by using four vertices of the face
			GL11.glTexCoord2f(0.5f, 0.75f);
			GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);
			GL11.glTexCoord2f(0.5f, 0.5f);
			GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);
			GL11.glTexCoord2f(0.75f, 0.5f);
			GL11.glVertex3f(vertices[5].x, vertices[5].y, vertices[5].z);
			GL11.glTexCoord2f(0.75f, 0.75f);
			GL11.glVertex3f(vertices[7].x, vertices[7].y, vertices[7].z);

			// face 5
			// produce two vectors in each face by using three vertices of the face
			Vector4f v6 = vertices[6].MinusPoint(vertices[7]);
			Vector4f w6 = vertices[3].MinusPoint(vertices[7]);
			// using two vectors to produce the normal vector of each face
			Vector4f normal6 = v6.cross(w6).Normal();
			// set the direction of each face and produce the shade
			GL11.glNormal3f(normal6.x, normal6.y, normal6.z);
			// draw the face by using four vertices of the face
			GL11.glTexCoord2f(0, 0.5f);
			GL11.glVertex3f(vertices[7].x, vertices[7].y, vertices[7].z);
			GL11.glTexCoord2f(0, 0.25f);
			GL11.glVertex3f(vertices[6].x, vertices[6].y, vertices[6].z);
			GL11.glTexCoord2f(0.25f, 0.25f);
			GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);
			GL11.glTexCoord2f(0.25f, 0.5f);
			GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);

		GL11.glEnd();
	}
}