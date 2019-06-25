package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import fyodor.likhachev.birdie.util.Insects;

public class Insect {

    private TextureRegion texture;
    private Rectangle bounds;
    private int type;

    public Insect(int x, int y) {
        texture = Insects.getRndTexture();
        type = Insects.getType();
        bounds = new Rectangle(x, y, Insects.width, Insects.height);
    }

    public void update(float dt) {
        bounds.x -= Insects.MOVEMENT * dt;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isInside(Rectangle rect) {
        return rect.contains(bounds);
    }

    public int getType() {
        return type;
    }
}
