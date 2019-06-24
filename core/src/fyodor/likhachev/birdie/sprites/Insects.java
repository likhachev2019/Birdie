package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import fyodor.likhachev.birdie.states.PlayState;

public class Insects {



    public static int width, height = 50;
    static final int MOVEMENT = 50;
    private static final int COUNT = 3;
    private static Array<TextureRegion> insects = new Array<TextureRegion>();

    public static void init() {
        TextureRegion region = new TextureRegion(new Texture("insects.png"));
        width = region.getRegionWidth()/COUNT;
        height = region.getRegionHeight();
        for (int i = 0; i < COUNT; i++)
            insects.add(new TextureRegion(region, i*width, 0, width, height));
    }

    // Индекс региона текстуры, которая былы возвращена последней
    private static int type = -1;

    static TextureRegion getRndTexture() {
        double n = Math.random();
        if (n <= 0.45){
            type = 0;
            return insects.get(0);
        }
        if (n <= 0.8){
            type = 1;
            return insects.get(1);
        }
        else {
            type = 2;
            return insects.get(COUNT - 1);
        }
    }

    static int getType() {
        return type;
    }
}
