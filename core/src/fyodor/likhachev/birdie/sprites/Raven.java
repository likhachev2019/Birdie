package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Raven {
// width = height
    public static final int SIZE = 400;
    private static final int GRAVITY = 12;
    private static final int MOVEMENT = -400;

    private Rectangle bounds;
    private Animation animation;
    public boolean live = true;

    public Raven(int x, int y) {
        animation = new Animation(new TextureRegion(new Texture("ravenAnimation.png")), 6, 1, false, true);
        bounds = new Rectangle(x, y, animation.getFrameWidth(), animation.getFrameHeight());
    }

    public void update(float dt) {
        if (live){
            bounds.x += MOVEMENT*dt;
            animation.update(dt);
        }
        else
            bounds.y -= GRAVITY;

    }

    public void dispose() {
        animation.dispose();
    }

    public TextureRegion getTexture() {
        return animation.getCurFrame();
    }

    public Rectangle getBounds() {
        return bounds;
    }


    public boolean isAttack(Rectangle birdieBounds) {
        // FIXME: 24.06.2019 (200 = SIZE/2)
        // Если центр перед ворона по центру врезался
        return birdieBounds.contains(bounds.x + 100, bounds.y + 200);
    }
}
