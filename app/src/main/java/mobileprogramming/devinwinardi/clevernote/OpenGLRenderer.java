package mobileprogramming.devinwinardi.clevernote;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private double redValue = 1f;
    private double greenValue = 1f;
    private double blueValue = 1f;
    private static final double FLASH_DURATION = 1000;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor((float) redValue, (float)  greenValue, (float) blueValue,1f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClearColor((float) redValue, (float)  greenValue, (float) blueValue,1f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        redValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.2) + 0.5));
        greenValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.4) + 0.5));
        blueValue = ((Math.sin(System.currentTimeMillis() * 2 * Math.PI / FLASH_DURATION * 0.7) + 0.5));
    }

    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
