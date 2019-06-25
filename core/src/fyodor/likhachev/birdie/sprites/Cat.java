package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cat extends BoxUnit {

    private static final int ACCESS_INDENT = 30;
    private static final int JUMP_FRAME_COUNT = 4;
    private static final float JUMP_CYCLE_TIME = 1, JUMP_MAX_FRAME_TIME = JUMP_CYCLE_TIME / JUMP_FRAME_COUNT;
    private boolean jumpEnd;
    private int curJumpFrameCount = 1;
    private static final int WALK_FRAME_COUNT = 6;
    private static final float WALK_CYCLE_TIME = 2, WALK_MAX_FRAME_TIME = WALK_CYCLE_TIME / WALK_FRAME_COUNT;
    private static Animation jumpAnime = new Animation(new TextureRegion(new Texture("jumpCatAnimation.png")), JUMP_FRAME_COUNT, JUMP_CYCLE_TIME, true, false);
    private static Animation animation = new Animation(new TextureRegion(new Texture("catAnimation.png")), WALK_FRAME_COUNT, WALK_CYCLE_TIME, true, false);
    // Границы, которые могут наносить урон. В первой и второй половинах прыжка
    private Rectangle upDamageBounds, downDamageBounds;


    public Cat(int x, int y) {
        bounds = new Rectangle(x, y, animation.getFrameWidth(), animation.getFrameHeight());
        velocity = new Vector2(-50, 100);
        upDamageBounds = new Rectangle(bounds.x, bounds.y + bounds.height/2, bounds.width/2, bounds.height/2);
        downDamageBounds = new Rectangle(bounds.x, bounds.y, bounds.width/2, bounds.height/2);
    }

    @Override
    public void update(float dt) {
        // Если идёт анимация с прыжком, то меняет координаты по у
        if (!jumpEnd){
            if (animationUpdate(dt))
                curJumpFrameCount++;
            if (curJumpFrameCount <= JUMP_FRAME_COUNT /2)
                bounds.y += velocity.y*dt;
            else
                bounds.y -= velocity.y*dt;
        }
        else
            animationUpdate(dt);
        bounds.x += velocity.x*dt;
    }

    @Override
    boolean animationUpdate(float dt) {
        curFrameTime += dt;
        boolean res = false;
        if (!jumpEnd){
            if (curFrameTime > JUMP_MAX_FRAME_TIME){
                frame++;
                curFrameTime = 0;
                res = true;
            }
            if (frame >= JUMP_FRAME_COUNT){
                frame = 0;
                curFrameTime = 0;
                res = true;
                jumpEnd = true;
            }
        }
        else {
            if (curFrameTime > WALK_MAX_FRAME_TIME){
                frame++;
                curFrameTime = 0;
                res = true;
            }
            if (frame >= WALK_FRAME_COUNT){
                frame = 0;
                res = true;
            }
        }
        return res;
    }


    @Override
    public boolean isIntersection(Rectangle rect) {
        // Кот опасен только в прыжке
        if (!jumpEnd){
            if (curJumpFrameCount <= JUMP_FRAME_COUNT /2)
                return rect.contains(upDamageBounds.x + ACCESS_INDENT, upDamageBounds.y + upDamageBounds.height - ACCESS_INDENT)
                        || rect.contains(upDamageBounds.x + ACCESS_INDENT, upDamageBounds.y + ACCESS_INDENT);
            else
                return rect.contains(downDamageBounds.x + ACCESS_INDENT, downDamageBounds.y + ACCESS_INDENT)
                        || rect.contains(downDamageBounds.x + ACCESS_INDENT, downDamageBounds.y + downDamageBounds.height - ACCESS_INDENT);
        }
        return false;
    }

    @Override
    public TextureRegion getTexture() {
        if (jumpEnd)
            return animation.getFrame(frame);
        // Если анимация прыжка ещё не закончилась
        else
            return jumpAnime.getFrame(frame);
    }
}
