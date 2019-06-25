package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BoxUnit {

    protected Rectangle bounds;
    protected Vector2 velocity;

    public abstract void update(float dt);

    // Анимация внутри объекта ворона, чтобы не загружать к каждому текстуру - она общая
    protected float curFrameTime;
    protected int frame;

    abstract boolean animationUpdate(float dt);

    public boolean isIntersection(Rectangle rect) {
        return rect.contains(bounds.x, bounds.y + bounds.height)
                || rect.contains(bounds.x + bounds.width, bounds.y + bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public abstract TextureRegion getTexture();

}
