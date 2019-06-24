package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Hawk {

    private static final int MOVEMENT = 400;

    private Texture texture;
    private Rectangle bounds;
    private boolean rightHawk;
    private Rectangle damageBounds;

    public Hawk(float x, float y, boolean rightHawk) {
        this.rightHawk = rightHawk;
        if (rightHawk){
            texture = new Texture("rightHawk.png");
            bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
            damageBounds = new Rectangle(bounds.x, bounds.y, bounds.width/3, bounds.height/2);
        }
        else{
            texture = new Texture("leftHawk.png");
            bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
            damageBounds = new Rectangle(bounds.x + bounds.width/3*2, bounds.y, bounds.width/3, bounds.height/2);
        }
    }

    public void update(float dt) {
        damageBounds.y = bounds.y -= MOVEMENT * dt;
        damageBounds.x = bounds.x -= rightHawk ? MOVEMENT * dt : - MOVEMENT * dt;
    }

    public boolean isIntersection(Rectangle birdieBounds) {
        // Проверяем только касание птицы сверху. Размеры ястреба меньше => при касании обязательно его угол будет в границах птицы
        return birdieBounds.contains(damageBounds.x, damageBounds.y)
                || birdieBounds.contains(damageBounds.x + damageBounds.width, damageBounds.y);
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}