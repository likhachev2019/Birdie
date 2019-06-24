package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Button {

    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;

    public Button(Texture texture, int x, int y) {
        this.texture = texture;

        position = new Vector2(x, y);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        texture.dispose();
    }

    public boolean isTouched(Vector3 coords) {
        return bounds.contains(coords.x, coords.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
