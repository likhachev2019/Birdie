package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

import fyodor.likhachev.birdie.util.Point;

public class Joystick {

    public static Texture joystickFraming = new Texture("joystickFraming.png");
    private static final int BIRDIE_SLOWER = 25;
    private  Texture joystick = new Texture("joystick.png");
    private Circle framingBounds, joystickBounds;
    public float framingX, framingY, joystickX, joystickY, framingRadius = joystickFraming.getWidth()/2f, joystickRadius = joystick.getHeight()/2f;

    public Joystick(int x, int y) {
        framingX = x;
        framingY = y;
        joystickX = x + joystickFraming.getWidth()/2f - joystick.getWidth()/2f;
        joystickY = y + joystickFraming.getHeight()/2f - joystick.getHeight()/2f;
        joystickBounds = new Circle(joystickX, joystickY, joystickRadius);
        // Считаем границы обрамы так, чтобы в любую её точку можно было поместить центр джойстика
        framingBounds = new Circle(x + framingRadius, y + framingRadius , framingRadius - joystickBounds.radius);
    }

    private Point centerShift = new Point();
    private Vector3 vect;

    public Vector3 update(float x, float y) {
        if (framingBounds.contains(x , y)){
            joystickX = x - joystickRadius;
            joystickY = y - joystickRadius;
            vect = new Vector3((x - framingBounds.x) /BIRDIE_SLOWER, (y-framingBounds.y)/BIRDIE_SLOWER, 0);
        }
        else {
            calcCentre(x,y);
        // Сдвиг рассчитывается относительная центра обрамы джостика, поэтому и прибавляем к нему сдвиги
            joystickX = framingBounds.x + centerShift.x - joystickRadius;
            joystickY = framingBounds.y + centerShift.y - joystickRadius;
            vect = new Vector3(centerShift.x/BIRDIE_SLOWER, centerShift.y/BIRDIE_SLOWER, 0);
        }
        return vect;
    }

    private void calcCentre(float x, float y) {
        float deltaY = y - framingBounds.y;
        float deltaX = x - framingBounds.x;
        double tangent = deltaY / deltaX;
        double _x = framingBounds.radius / Math.sqrt(1 + tangent*tangent); // модуль
        centerShift.x = (int) (deltaX < 0 ?  -_x : _x);
        centerShift.y = (int) (centerShift.x * tangent);
    }

    public boolean contains(float x, float y) {
        return framingBounds.contains(x, y);
    }

    public Texture getJoystick() {
        return joystick;
    }

    public void dispose() {
        joystick.dispose();
        joystickFraming.dispose();
    }
}
