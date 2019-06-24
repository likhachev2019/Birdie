package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import fyodor.likhachev.birdie.states.PlayState;

public class Birdie {

    public static final int MOVEMENT = 100;
    private static final int GRAVITY = 6;
    private static final int HEARTS = 3;
    private static final int HEART_ANIME_COUNT = 5;

    private int heartBalance;
    // позиция
    private Vector3 position;
    // границы
    private Rectangle bounds;
    private Animation birdieAnimation;
    private Animation heartAnimation;
    public boolean live;

    public Birdie(int x, int y) {
        // задаём направление движения
        position = new Vector3(x, y, 0);
        heartBalance = HEARTS;
        Texture texture = new Texture("heartAnimation.png");
        heartAnimation = new Animation(new TextureRegion(texture), 2, 3, true, false);
        texture = new Texture("birdieAnimation.png");
        birdieAnimation = new Animation(new TextureRegion(texture), 2, 0.5f, true, false);
        bounds = new Rectangle(x, y, birdieAnimation.getFrameWidth(), birdieAnimation.getFrameHeight());
        live = true;
    }

    private int curAnimeCount;

    public TextureRegion[] getHurts() {
        TextureRegion[] hs = new TextureRegion[HEARTS];
        for (int i = 0; i < HEARTS; i++){
            // Если это только что потеряное сердце, анимируем его
            if (i == heartBalance && curAnimeCount > 0){
                // Если кадр сменился, уменьшаем кол-во оставшихся анимаций
                if (heartAnimation.update(0.1f))
                    curAnimeCount--;
                hs[i] = heartAnimation.getCurFrame();
            }
            else if (i >= heartBalance)
                hs[i] = heartAnimation.getSecondFrame(); // Пустое сердце
            else
                hs[i] = heartAnimation.getFirstFrame(); // Целое сердце
        }
        return hs;
    }

    public void update(float dt) {
        if (live){
            birdieAnimation.update(dt);
            position.add(MOVEMENT * dt, 0,0);
            bounds.x = position.x;
        }
        else
            position.y -= GRAVITY;
        bounds.y = position.y;
    }

    public void up() {
        if (position.y + GRAVITY > PlayState.HEIGHT -  birdieAnimation.getFrameHeight())
            position.set(position.x, PlayState.HEIGHT -  birdieAnimation.getFrameHeight(), 0);
        else
            position.add(0, GRAVITY,0);
    }

    public void down() {
        if (position.y - GRAVITY < PlayState.getGroundHeight())
            position.set(position.x,PlayState.getGroundHeight(), 0);
        else
            position.add(0, -GRAVITY,0);
    }

    public void forward(float limit) {
        if (position.x - GRAVITY > limit - bounds.width)
            position.set(limit - bounds.width, position.y, 0);
        else
            position.add(GRAVITY, 0,0);
    }

    public void back(float limit) {
        if (position.x - GRAVITY < limit)
            position.set(limit, position.y, 0);
        else
            position.add(-GRAVITY, 0,0);
    }

    public void minusHeart() {
        if (curAnimeCount == 0){
            heartBalance--;
            if (heartBalance == 0)
                live = false;
            curAnimeCount = HEART_ANIME_COUNT;
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBirdie() {
        return birdieAnimation.getCurFrame();
    }

    public void dispose() {
        heartAnimation.dispose();
        birdieAnimation.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
