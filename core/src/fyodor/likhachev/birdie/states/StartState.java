package fyodor.likhachev.birdie.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import fyodor.likhachev.birdie.MyGame;
import fyodor.likhachev.birdie.sprites.Birdie;
import fyodor.likhachev.birdie.sprites.Button;
import fyodor.likhachev.birdie.sprites.Tree;

public class StartState extends State{
    private static final int WIDTH = MyGame.WIDTH*2;
    private static final int HEIGHT = MyGame.HEIGHT*2;
    private static int GROUND_HEIGHT;

    private Array<Tree> nests;
    private Birdie birdie;
    private Texture bg;
    private Texture tree;
    private Texture ground;
    private Button upButton, downButton, rightButton, leftButton;

    public StartState(GameStateManager gsm) {
        super(gsm);
        // Настраиваем ортографическую камеру, центрируя её
        camera.setToOrtho(false, WIDTH, HEIGHT);
        birdie = new Birdie(250, 480);
        bg = new Texture("bg.png");
        tree = new Texture("tree.png");
        ground = new Texture("ground.png");
        GROUND_HEIGHT = ground.getHeight()*2;
        Texture texture = new Texture("upButton.png");
        upButton = new Button(texture,WIDTH - texture.getWidth()*2 - 5, texture.getHeight() + 15);
        texture = new Texture("downButton.png");
        downButton = new Button(texture,WIDTH - texture.getWidth()*2 - 5,  5);
        texture = new Texture("rightButton.png");
        rightButton = new Button(texture, WIDTH - texture.getWidth() - 5, texture.getHeight()/2);
        texture = new Texture("leftButton.png");
        leftButton = new Button(texture, WIDTH - texture.getWidth()*3 - 10, texture.getHeight()/2);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            gsm.set(new PlayState(gsm));
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        // Устанавливаем матрицу проекций для нашей камеры
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, 0, 0, WIDTH, HEIGHT);
        sb.draw(ground, 0, 0, WIDTH, GROUND_HEIGHT);
        sb.draw(tree, 0,0);
        sb.draw(birdie.getBirdie(), birdie.getPosition().x, birdie.getPosition().y);
        sb.draw(upButton.getTexture(),camera.position.x - camera.viewportWidth/2 + upButton.getPosition().x, upButton.getPosition().y );
        sb.draw(downButton.getTexture(),camera.position.x - camera.viewportWidth/2 + downButton.getPosition().x, downButton.getPosition().y);
        sb.draw(rightButton.getTexture(), camera.position.x - camera.viewportWidth/2 + rightButton.getPosition().x, rightButton.getPosition().y);
        sb.draw(leftButton.getTexture(), camera.position.x - camera.viewportWidth/2 + leftButton.getPosition().x, leftButton.getPosition().y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        birdie.dispose();
        ground.dispose();
        tree.dispose();
    }
}
