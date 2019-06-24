package fyodor.likhachev.birdie.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fyodor.likhachev.birdie.MyGame;

public class GameOverState extends State{

    private Texture gameOverTable, deadBirdie, endBg;
    private int score;
    private BitmapFont scoreFont = new BitmapFont();
    private String res;

    GameOverState(GameStateManager gsm, int score) {
        super(gsm);
        camera.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        gameOverTable = new Texture("gameOverTable.png");
        deadBirdie = new Texture("deadBirdie.png");
        endBg = new Texture("endBg.png");
        this.score = score;
        scoreFont.setColor(Color.CYAN);
        scoreFont.getData().setScale(3);
        res = "YOUR SCORE: " + score;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            gsm.set(new MenuState(gsm));
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sp) {
        sp.setProjectionMatrix(camera.combined);
        sp.begin();
        sp.draw(endBg,0,0);
        scoreFont.draw(sp, res, MyGame.WIDTH/2 - res.length()*13, MyGame.HEIGHT - 200);
        sp.draw(gameOverTable, MyGame.WIDTH/2 - gameOverTable.getWidth()/2, MyGame.HEIGHT/2);
        sp.draw(deadBirdie, MyGame.WIDTH/2 - deadBirdie.getWidth()/2, 50);
        sp.end();
    }

    @Override
    public void dispose() {
        gameOverTable.dispose();
    }
}
