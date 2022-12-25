package objects3D;

import GraphicsObjects.Utils;
import org.newdawn.slick.opengl.Texture;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Camera {

    static float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };

    public Camera() {}

    public void DrawCamera(Texture texture_camera) {

        SimpleTexCube camera_body = new SimpleTexCube();
        Sphere camera_len = new Sphere();

        GL11.glColor3f(grey[0], grey[1], grey[2]);
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
            GL11.glScalef(3, 3, 6);
            GL11.glTexParameteri(
                    GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                    GL11.GL_CLAMP);
            Color.white.bind();
            texture_camera.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            camera_body.DrawSimpleTexCube(1);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0.0f, 0.0f, 0.4f);
                GL11.glScalef(3, 3, 3);
                GL11.glTexParameteri(
                        GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
                        GL11.GL_CLAMP);
                Color.white.bind();
//            texture_jeans.bind();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                camera_len.DrawSphere(0.3f, 100, 100);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }
}
