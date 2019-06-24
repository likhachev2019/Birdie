package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class BoxUnit {

    protected Rectangle bounds;
    protected Vector2 velocity;
    protected Animation animation;

    public abstract void update(float dt);

    public boolean isIntersection(Rectangle rect) {
        return rect.contains(bounds.x, bounds.y + bounds.height)
                || rect.contains(bounds.x + bounds.width, bounds.y + bounds.height);
    }

    public void dispose() {
        animation.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public TextureRegion getTexture() {
        return animation.getCurFrame();
    }

}
