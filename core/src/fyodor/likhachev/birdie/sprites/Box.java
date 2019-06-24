package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Box {

    public static final int INDENT = 50;

    private Rectangle bounds;
    private Texture texture;

    public Box(int x, int y) {
        texture = new Texture("secretBox.png");
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public float getRightX() {
        return bounds.x + bounds.width;
    }

    public float getLeftX() {
        return bounds.x;
    }

    public float getUpY() {
        return bounds.y + bounds.height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        texture.dispose();
    }

    public boolean isIntersection(Rectangle birdieBounds) {
        // Проверяем находится какой-либо верхний угол коробки внутри птицы
        return birdieBounds.contains(bounds.x, bounds.y + bounds.height)
                || birdieBounds.contains(bounds.x + bounds.height, bounds.y + bounds.height);
    }
}
