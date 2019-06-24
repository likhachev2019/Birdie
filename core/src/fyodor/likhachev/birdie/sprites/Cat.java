package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cat extends BoxUnit {

    private static final int ACCESS_INDENT = 30;
    private static final int JUMP_FRAME_COUNT = 4;

    private int curJumpFrameCount = 1;
    private Animation jumpAnime;
    // Границы, которые могут наносить урон. В первой и второй половинах прыжка
    private Rectangle upDamageBounds, downDamageBounds;

    public Cat(int x, int y) {
        animation = new Animation(new TextureRegion(new Texture("catAnimation.png")), 6, 2, true, false);
        bounds = new Rectangle(x, y, animation.getFrameWidth(), animation.getFrameHeight());
        jumpAnime = new Animation(new TextureRegion(new Texture("jumpCatAnimation.png")), JUMP_FRAME_COUNT, 1, true, false);
        velocity = new Vector2(-50, 100);
        upDamageBounds = new Rectangle(bounds.x, bounds.y + bounds.height/2, bounds.width/2, bounds.height/2);
        downDamageBounds = new Rectangle(bounds.x, bounds.y, bounds.width/2, bounds.height/2);
    }

    @Override
    public void update(float dt) {
        // Если идёт анимация с прыжком, то меняет координаты по у
        if (!jumpAnime.end){
            if (jumpAnime.update(dt))
                curJumpFrameCount++;
            if (curJumpFrameCount <= JUMP_FRAME_COUNT /2)
                bounds.y += velocity.y*dt;
            else
                bounds.y -= velocity.y*dt;
        }
        else
            animation.update(dt);
        bounds.x += velocity.x*dt;
    }

    @Override
    public boolean isIntersection(Rectangle rect) {
        // Кот опасен только в прыжке
        if (!jumpAnime.end){
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
        if (jumpAnime.end)
            return animation.getCurFrame();
        // Если анимация прыжка ещё не закончилась
        else
            return jumpAnime.getCurFrame();
    }
}
