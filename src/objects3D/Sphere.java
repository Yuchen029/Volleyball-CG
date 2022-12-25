package objects3D;

import org.lwjgl.opengl.GL11;

public class Sphere {

	
	public Sphere() {

	}

	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	// 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works 
	public void DrawSphere(float radius,float nSlices,float nSegments) {
		// produce the angle increases in longitude and altitude
		float inctheta = (float) (2.0 * Math.PI / nSlices);
		float incphi = (float) (Math.PI / nSegments);

		GL11.glBegin(GL11.GL_TRIANGLES);
		// draw each piece of the sphere by iterating angles in longitude and altitude and produce the coordinates

		// iterating angles in longitude
		for (float theta = (float) -Math.PI; theta < (float) Math.PI; theta += inctheta) {
			// iterating angles in altitude
			for (float phi = (float) (-Math.PI/2); phi < (float) (Math.PI/2); phi += incphi) {

				// from anti-clockwise produce four vertices (x1, y1), (x2, y2), (x3, y3), (x4, y4)
				float x1 = (float) (radius * Math.cos(phi) * Math.cos(theta));
				float y1 = (float) (radius * Math.cos(phi) * Math.sin(theta));
				float z1 = (float) (radius * Math.sin(phi));

				float x2 = (float) (radius * Math.cos(phi) * Math.cos(theta + inctheta));
				float y2 = (float) (radius * Math.cos(phi) * Math.sin(theta + inctheta));
				float z2 = (float) (radius * Math.sin(phi));

				float x3 = (float) (radius * Math.cos(phi + incphi) * Math.cos(theta + inctheta));
				float y3 = (float) (radius * Math.cos(phi + incphi) * Math.sin(theta + inctheta));
				float z3 = (float) (radius * Math.sin(phi + incphi));

				float x4 = (float) (radius * Math.cos(phi + incphi) * Math.cos(theta));
				float y4 = (float) (radius * Math.cos(phi + incphi) * Math.sin(theta));
				float z4 = (float) (radius * Math.sin(phi + incphi));

				// draw the triangle above ((x1, y1), (x3, y3), (x4, y4))
				GL11.glNormal3f(x1, y1, z1);
				GL11.glVertex3f(x1, y1, z1);
				GL11.glNormal3f(x3, y3, z3);
				GL11.glVertex3f(x3, y3, z3);
				GL11.glNormal3f(x4, y4, z4);
				GL11.glVertex3f(x4, y4, z4);

				// draw the triangle below ((x1, y1), (x2, y2), (x3, y3))
				GL11.glNormal3f(x1, y1, z1);
				GL11.glVertex3f(x1, y1, z1);
				GL11.glNormal3f(x2, y2, z2);
				GL11.glVertex3f(x2, y2, z2);
				GL11.glNormal3f(x3, y3, z3);
				GL11.glVertex3f(x3, y3, z3);
			}
		}
		GL11.glEnd();
	}
}

 