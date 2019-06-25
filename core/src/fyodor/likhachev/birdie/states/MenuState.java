package fyodor.likhachev.birdie.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import fyodor.likhachev.birdie.MyGame;
import fyodor.likhachev.birdie.sprites.Button;

public class MenuState extends State {

    private Texture background, alpha;
    private Button playBtn;
    private BitmapFont scoreFont = new BitmapFont();
    private String res;

    public MenuState(GameStateManager gsm, int highScore) {
        super(gsm);
        background = new Texture("bg.png");
        Texture plBt = new Texture("playButton.png");
        playBtn = new Button(plBt,MyGame.WIDTH/2 - plBt.getWidth()/2, MyGame.HEIGHT/2);
        camera.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        scoreFont.setColor(Color.PURPLE);
        scoreFont.getData().setScale(3);
        res = "RECORD: "+ highScore;
        alpha = new Texture("alpha.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
//            Конвертируем реальные координаты в коодринаты области просмотра, которую мы установили
            if (playBtn.isTouched(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0))))
//            Устанавливаем игровой экран на вершину стэка, убирая оттуда меню
                gsm.set(new StartState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

//  Отрисовывает экран игры
    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, MyGame.WIDTH, MyGame.HEIGHT);
        sb.draw(playBtn.getTexture(), playBtn.getPosition().x, playBtn.getPosition().y);
        scoreFont.draw(sb, res, MyGame.WIDTH/2 - res.length()*13, MyGame.HEIGHT - 200);
        sb.draw(alpha, MyGame.WIDTH/2 - alpha.getWidth()/2, alpha.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
    }
}
