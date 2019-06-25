package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import fyodor.likhachev.birdie.states.PlayState;
import fyodor.likhachev.birdie.util.Triangle;

public class FirTree extends Plant {

    public static final int DOWN_STEP = 25;
    private static final int ALLOWED_INDENT = 35;

    public FirTree(int x, int y) {
        texture = new Texture("firTree.png");
        bounds = new Triangle(x, y - DOWN_STEP, texture.getWidth(), texture.getHeight());
    }

    public boolean isIntersection(Rectangle rect) {
        // Касание либо центром низа, либо серединой справа\слева
        return bounds.contains(rect.x + rect.width/2, rect.y + ALLOWED_INDENT )// Y должен быть
                || bounds.contains(rect.x + rect.width/2 - ALLOWED_INDENT, rect.y + rect.height/2 - PlayState.getGroundHeight() + DOWN_STEP)
                || bounds.contains(rect.x + ALLOWED_INDENT, rect.y + rect.height/2 - PlayState.getGroundHeight() + DOWN_STEP);
    }
}
