package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class Joystick {

    private Texture joystickFraming, joystick;
    private Circle framingBounds, joystickBounds;

    public Joystick(int x, int y) {
        joystickFraming = new Texture("joystickFraming.png");
        joystick = new Texture("joystick.png");
        framingBounds = new Circle(x + joystickFraming.getWidth(), y + joystickFraming.getHeight(), joystickFraming.getHeight()/2);
        joystickBounds = new Circle(x + joystickFraming.getWidth(), y + joystickFraming.getHeight(), joystickFraming.getHeight()/2);
    }
}
