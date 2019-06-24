package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Achivka {

    public static final int animationFrame = 50;

    private BitmapFont font;
    private String text;
    private float x, y;
    private int animeTime;

    public Achivka(String text, float x, float y) {
        this.text = text;
        font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.RED);
        this.x = x;
        this.y = y;
    }

    public Achivka(Insect ins) {
        text = "+";
        font = new BitmapFont();
        font.getData().setScale(2);
        int score = ins.getType() + 1;
        text += score;
        if (score == 1)
            font.setColor(Color.GREEN);
        else if (score == 2)
            font.setColor(Color.YELLOW);
        else if (score == 3)
            font.setColor(Color.RED);
        x = ins.getBounds().x;
        y = ins.getBounds().y;
    }

    public BitmapFont getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void incrAnimeTime() {
        animeTime++;
    }

    public int getAnimeTime() {
        return animeTime;
    }
}
