package objects3D;

import org.lwjgl.opengl.GL11;
import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import java.math.*;

public class Cylinder {

	
	public Cylinder() {

	}
	
	// remember to use Math.PI isntead PI 
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	public void DrawCylinder(float radius, float height, int nSegments ) {

		GL11.glBegin(GL11.GL_TRIANGLES);
		// draw each segment
		for (float i = 0.0f; i < nSegments; i += 1.0) {

			// produce the angle corresponding to (x1, y1) and (x2, y2)
			float angle = (float) (Math.PI * i * 2.0 / nSegments);
			float nextAngle = (float) (Math.PI * (i + 1.0) * 2.0 / nSegments);

			// produce (x1, y1) and (x2, y2)
			float x1 = (float) (radius * Math.sin(angle)), y1 = (float) (radius * Math.cos(angle));
			float x2 = (float) (radius * Math.sin(nextAngle)), y2 = (float) (radius * Math.cos(nextAngle));

			// divide each segment rectangle to two triangle

			// draw the triangle above
			GL11.glNormal3f(x1, y1, 0);
			GL11.glVertex3f(x1, y1, 0);
			GL11.glNormal3f(x2, y2, height);
			GL11.glVertex3f(x2, y2, height);
			GL11.glNormal3f(x1, y1, height);
			GL11.glVertex3f(x1, y1, height);

			// draw the segment of the cylinder top
			GL11.glNormal3f(0.0f, 0.0f, height);
			GL11.glVertex3f(0.0f, 0.0f, height);
			GL11.glVertex3f(x1, y1, height);
			GL11.glVertex3f(x2, y2,height);

			// draw the triangle below
			GL11.glNormal3f(x1, y1, 0);
			GL11.glVertex3f(x1, y1, 0);
			GL11.glNormal3f(x2, y2, 0);
			GL11.glVertex3f(x2, y2, 0);
			GL11.glNormal3f(x2, y2, height);
			GL11.glVertex3f(x2, y2, height);

			// draw the segment of the cylinder bottom
			GL11.glNormal3f(0.0f, 0.0f, 0);
			GL11.glVertex3f(0.0f, 0.0f, 0);
			GL11.glVertex3f(x2, y2, 0);
			GL11.glVertex3f(x1, y1,0);
		}
		GL11.glEnd();
	}
}
