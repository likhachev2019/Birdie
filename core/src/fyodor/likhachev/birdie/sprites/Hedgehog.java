package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hedgehog extends BoxUnit {

    private static final float ANIME_TIME = 0.5f;

    public Hedgehog(int x, int y) {
        animation = new Animation(new TextureRegion(new Texture("hedgehogAnimation.png")), 2, ANIME_TIME, true, false);
        bounds = new Rectangle(x, y, animation.getFrameWidth(), animation.getFrameHeight());
        velocity = new Vector2(-100, 0);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        bounds.x += velocity.x*dt;
    }
}
