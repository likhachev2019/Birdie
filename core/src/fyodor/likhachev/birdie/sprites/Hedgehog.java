package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hedgehog extends BoxUnit {


    private static final int FRAME_COUNT = 2;
    private static final float ANIME_TIME = 0.5f;
    private static final float MAX_FRAME_TIME = ANIME_TIME / FRAME_COUNT;

    private static Animation animation = new Animation(new TextureRegion(new Texture("hedgehogAnimation.png")), FRAME_COUNT , ANIME_TIME, true, false);

    public Hedgehog(int x, int y) {
        bounds = new Rectangle(x, y, animation.getFrameWidth(), animation.getFrameHeight());
        velocity = new Vector2(-100, 0);
    }

    @Override
    public void update(float dt) {
        animationUpdate(dt);
        bounds.x += velocity.x*dt;
    }

    @Override
    boolean animationUpdate(float dt) {
        curFrameTime += dt;
        if (curFrameTime > MAX_FRAME_TIME){
            frame++;
            curFrameTime = 0;
        }
        if (frame >= FRAME_COUNT)
            frame = 0;
        return false;
    }

    @Override
    public TextureRegion getTexture() {
        return animation.getFrame(frame);
    }
}
