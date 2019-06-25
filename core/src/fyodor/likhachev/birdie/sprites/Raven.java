package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Raven {
// width = height
    public static final int SIZE = 400;
    private static final int GRAVITY = 12;
    private static final int MOVEMENT = -400;
    private static int frameCount = 6;
    private static float cycleTime = 1, maxFrameTime = cycleTime / frameCount;
    private static Animation animation = new Animation(new TextureRegion(new Texture("ravenAnimation.png")), frameCount, cycleTime, false, true);

    public boolean live = true;
    private Rectangle bounds;

    public Raven(int x, int y) {
        bounds = new Rectangle(x, y, animation.getFrameWidth(), animation.getFrameHeight());
    }

    public void update(float dt) {
        if (live){
            bounds.x += MOVEMENT*dt;
            animationUpdate(dt);
        }
        else
            bounds.y -= GRAVITY;

    }

    // Анимация внутри объекта ворона, чтобы не загружать к каждому текстуру - она общая
    private float curFrameTime;
    private int frame;

    private void animationUpdate(float dt) {
        curFrameTime += dt;
        if (curFrameTime > maxFrameTime){
            frame++;
            curFrameTime = 0;
        }
        if (frame >= frameCount)
            frame = 0;
    }

    public void dispose() {
        animation.dispose();
    }

    public TextureRegion getTexture() {
        return animation.getFrame(frame);
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
