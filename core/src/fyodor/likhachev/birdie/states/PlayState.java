package fyodor.likhachev.birdie.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import fyodor.likhachev.birdie.MyGame;
import fyodor.likhachev.birdie.sprites.Achivka;
import fyodor.likhachev.birdie.sprites.Box;
import fyodor.likhachev.birdie.sprites.BoxUnit;
import fyodor.likhachev.birdie.sprites.Cat;
import fyodor.likhachev.birdie.sprites.Raven;
import fyodor.likhachev.birdie.sprites.Hawk;
import fyodor.likhachev.birdie.sprites.Birdie;
import fyodor.likhachev.birdie.sprites.Bush;
import fyodor.likhachev.birdie.sprites.Button;
import fyodor.likhachev.birdie.sprites.Hedgehog;
import fyodor.likhachev.birdie.sprites.Insect;
import fyodor.likhachev.birdie.sprites.Insects;

// Игровой экран
public class PlayState extends State {

    private static final int WIDTH = MyGame.WIDTH*2;
    public static final int HEIGHT = MyGame.HEIGHT*2;
    private static int maxRndInsHeight;
    private static int maxRndRavHeight;
    private static Texture ground = new Texture("ground.png");
    private static final int GROUND_HEIGHT = ground.getHeight()*2;
    private static Rectangle boundsBuffer;
    private static int minFlyHeight;

    private Birdie birdie;
    private Texture bg;
    private Texture tree;
    private Vector2 groundPos1, groundPos2;
    private Button upButton, downButton, rightButton, leftButton;
    private Array<Bush> bushes;
    private Array<Insect> insects;
    private int score = 0;
    private BitmapFont scoreFont = new BitmapFont();
    private Array<Achivka> achivki = new Array<Achivka>();
    private Hawk hawk;
    private Box secretBox;
    private Array<BoxUnit> boxUnits = new Array<BoxUnit>();
    private Array<Raven> ravens = new Array<Raven>();

    public PlayState(GameStateManager gsm) {
        super(gsm);
        // Настраиваем ортографическую камеру, центрируя её
        camera.setToOrtho(false, WIDTH, HEIGHT);
        ground = new Texture("ground.png");
        birdie = new Birdie(250, 480);
        bg = new Texture("bg.png");
        tree = new Texture("tree.png");
        // Изначально камера в позиции по центру окна просмотра => отнимем половину его ширины и поулчим 0
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth/2, 0);
        // Вторая следует сразу за первой
        groundPos2 = new Vector2(camera.position.x - camera.viewportWidth/2 + WIDTH,0);
        Texture texture = new Texture("upButton.png");
        upButton = new Button(texture,WIDTH - texture.getWidth()*2 - 5, texture.getHeight() + 15);
        texture = new Texture("downButton.png");
        downButton = new Button(texture,WIDTH - texture.getWidth()*2 - 5,  5);
        texture = new Texture("rightButton.png");
        rightButton = new Button(texture, WIDTH - texture.getWidth() - 5, texture.getHeight()/2);
        texture = new Texture("leftButton.png");
        leftButton = new Button(texture, WIDTH - texture.getWidth()*3 - 10, texture.getHeight()/2);
        bushes = new Array<Bush>();
        insects = new Array<Insect>();
        Insects.init();
        minFlyHeight = PlayState.GROUND_HEIGHT + Bush.HEIGHT;;
        maxRndInsHeight = HEIGHT - minFlyHeight - Insects.height;
        maxRndRavHeight = HEIGHT - minFlyHeight - Raven.SIZE;
        scoreFont.setColor(Color.CORAL);
        scoreFont.getData().setScale(2);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()){
//            Конвертируем реальные координаты в коодринаты области просмотра, которую мы установили
            Vector3 viewpointCoords = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            float curUpButX =  camera.position.x - camera.viewportWidth/2 + upButton.getPosition().x;
            float curRButX = curUpButX + upButton.getBounds().width + 5;
            float curLButX = curUpButX - upButton.getBounds().width - 5;
            float coordXBuf;
            // Координату х проверяем отдельно, поэтому в векторе она всегда верная
            if (viewpointCoords.x >= curUpButX && viewpointCoords.x <= curUpButX + upButton.getBounds().width){
                coordXBuf = viewpointCoords.x;
                viewpointCoords.x = upButton.getPosition().x;
                if(upButton.isTouched(viewpointCoords))
                    birdie.up();
                else if (downButton.isTouched(viewpointCoords))
                    birdie.down();
                viewpointCoords.x = coordXBuf;
            }
            // Правая кнопка
            if(viewpointCoords.x >= curRButX && viewpointCoords.x <= curRButX + upButton.getBounds().width){
                viewpointCoords.x = rightButton.getPosition().x;
                if (rightButton.isTouched(viewpointCoords))
                    birdie.forward(camera.position.x + camera.viewportWidth/2);
            }
            // Левая кнопка
            else if(viewpointCoords.x >= curLButX && viewpointCoords.x <= curLButX + upButton.getBounds().width){
                viewpointCoords.x = leftButton.getPosition().x;
                if (leftButton.isTouched(viewpointCoords))
                    birdie.back(camera.position.x - camera.viewportWidth/2);
            }
        }
    }

    @Override
    public void update(float dt) {
        boundsBuffer = birdie.getBounds();
        if (boundsBuffer.y + boundsBuffer.height < 0)
            gsm.set(new GameOverState(gsm, score));
        if (birdie.live)
            handleInput();
        birdie.update(dt);
        groundUpdate();
        bushesUpdate();
        insectsUpdate();
        hawkUpdate(dt);
        ravenUpdate(dt);
        secretBoxUpdate();
        for (BoxUnit bu : boxUnits)
            bu.update(dt);
        for (Insect i : insects)
            i.update(dt);
        if (birdie.live){
            checkIntersection();
            camera.position.x += Birdie.MOVEMENT*dt;
            camera.update();
        }
    }

    private void ravenUpdate(float dt) {
        for (Raven r : ravens)
            r.update(dt);
        if (Math.random() < 0.008){
            Raven r = new Raven((int)(camera.position.x + camera.viewportWidth/2), minFlyHeight +  MyGame.rnd.nextInt(maxRndRavHeight));
            ravens.add(r);
        }
    }

    private void secretBoxUpdate() {
        if (secretBox == null){
            if (Math.random() < 0.008){
                float x = camera.position.x + camera.viewportWidth/2;
                if (bushes.size != 0){
                    boundsBuffer = bushes.get(bushes.size - 1).getBounds();
                    // если коробка слишком близко к кусту, сдвинем её вправо
                    if (boundsBuffer.x + boundsBuffer.width + Box.INDENT > x)
                        x = boundsBuffer.x + boundsBuffer.width + Box.INDENT;
                }
                secretBox = new Box((int)x, GROUND_HEIGHT);
            }
        }
        // если за границей экрана
        else if (secretBox.getRightX() < camera.position.x - camera.viewportWidth/2){
            secretBox.dispose();
            secretBox = null;
        }
    }

    private void hawkUpdate(float dt) {
        if (hawk == null){
            double n = Math.random();
            if( n < 0.08) {
                n = MyGame.rnd.nextInt(WIDTH);
                // Прибавляем к х - текущему началу экрана рандомное число, по величине не больше ширины экрана
                float x = camera.position.x - camera.viewportWidth/2 + (float) n;
                hawk = new Hawk(x, HEIGHT, n > WIDTH/2);
            }
        }
        else hawk.update(dt);
    }

    private void checkIntersection() {
        Rectangle birdieBounds = birdie.getBounds();
        if (hawk != null)
            boundsBuffer = hawk.getBounds();
        Rectangle bounds;
        for (Insect i : insects){
            bounds = i.getBounds();
            if (bounds.x + bounds.width < camera.position.x - camera.viewportWidth/2)
                insects.removeValue(i, false);
            else if (i.isInside(birdieBounds)) {
                Achivka ach = new Achivka(i);
                achivki.add(ach);
                score += Integer.valueOf(ach.getText());
                insects.removeValue(i, false);
            }
            else if (hawk != null && i.isInside(boundsBuffer))
                insects.removeValue(i, false);
        }
        for (Bush b : bushes) {
            bounds = b.getBounds();
            if (bounds.x + bounds.width < camera.position.x - camera.viewportWidth/2){
                b.dispose();
                bushes.removeValue(b, false);
            }
            else if (b.isIntersection(birdieBounds))
                birdie.minusHeart();
        }
        if (hawk != null)
            if (hawk.isIntersection(birdieBounds))
                birdie.minusHeart();
        if (secretBox != null && secretBox.isIntersection(birdieBounds))
            openSecretBox();
        for (BoxUnit bu : boxUnits)
            if (bu.isIntersection(birdieBounds))
                birdie.minusHeart();
        for (Raven r : ravens)
            if (r.live)
                if (hawk != null && hawk.isIntersection(r.getBounds()))
                    r.live = false;
                else if (r.isAttack(birdieBounds)){
                    birdie.minusHeart();
                    r.live = false;
                }
    }

    private void openSecretBox() {
        secretBox.dispose();
        double n = Math.random();
        if (n < 0.3){
            boxUnits.add(new Cat((int)secretBox.getLeftX(), GROUND_HEIGHT));
            achivki.add(new Achivka("CAT!", secretBox.getLeftX(), secretBox.getUpY()));
        }
        else if (n <= 0.6){
            boxUnits.add(new Hedgehog((int)secretBox.getLeftX(), GROUND_HEIGHT));
            achivki.add(new Achivka("HEDGEHOG!", secretBox.getLeftX(), secretBox.getUpY()));
        }
        else if (n <= 0.9){
            insectBonusCount = 100;
            achivki.add(new Achivka("100 INSECTS!", secretBox.getLeftX(), secretBox.getUpY()));
        }
        else
            achivki.add(new Achivka("EMPTY!", secretBox.getLeftX(), secretBox.getUpY()));
        secretBox = null;
    }

    private int insectBonusCount = 0;
    private double insectChance = 0.02;

    private void insectsUpdate() {
        if (insectBonusCount > 0 || Math.random() <= insectChance){
            if (insectBonusCount > 0)
                insectBonusCount--;
            Insect insect;
            int x = Math.round(camera.position.x + camera.viewportWidth);
            int y = minFlyHeight + MyGame.rnd.nextInt(maxRndInsHeight);
            if (insects.size == 0){
                insect = new Insect(x, y);
                insects.add(insect);
            }
            else {
                Rectangle pastBounds = insects.get(insects.size - 1).getBounds();
                // Если последнее наскомое не пересекается с новым, то добавляем
                if (!pastBounds.contains(x, y)){
                    insect = new Insect(x, y);
                    insects.add(insect);
                }
            }
        }
    }

    private void bushesUpdate() {
        if (Math.random() <= 0.002){
            Bush b;
            int x = Math.round(camera.position.x + camera.viewportWidth);
            // чтобы не залазил на коробку
            if (secretBox != null && secretBox.getRightX() + Box.INDENT > x)
                x = Math.round(secretBox.getRightX() + Box.INDENT*2);
            if (bushes.size == 0){
                b = new Bush(x, GROUND_HEIGHT);
                bushes.add(b);
            }
            else {
                Rectangle pastBounds = bushes.get(bushes.size - 1).getBounds();
                if (pastBounds.x + pastBounds.width < x){
                    b = new Bush(x, GROUND_HEIGHT );
                    bushes.add(b);
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        // Устанавливаем матрицу проекций для нашей камеры
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - camera.viewportWidth/2, 0, WIDTH, HEIGHT);
        bushRender(sb);
        sb.draw(ground, groundPos1.x, groundPos1.y, WIDTH, GROUND_HEIGHT);
        sb.draw(ground, groundPos2.x, groundPos2.y, WIDTH, GROUND_HEIGHT);
        if (secretBox != null){
            Rectangle b = secretBox.getBounds();
            sb.draw(secretBox.getTexture(), b.x, b.y);
        }
            boxUnitRender(sb);
        if (tree != null)
            treeRender(sb);
        insectRender(sb);
        ravenRender(sb);
        sb.draw(birdie.getBirdie(), birdie.getPosition().x, birdie.getPosition().y);
        achivkRender(sb);
        hawkRender(sb);
        sb.draw(upButton.getTexture(),camera.position.x - camera.viewportWidth/2 + upButton.getPosition().x, upButton.getPosition().y );
        sb.draw(downButton.getTexture(),camera.position.x - camera.viewportWidth/2 + downButton.getPosition().x, downButton.getPosition().y);
        sb.draw(rightButton.getTexture(), camera.position.x - camera.viewportWidth/2 + rightButton.getPosition().x, rightButton.getPosition().y);
        sb.draw(leftButton.getTexture(), camera.position.x - camera.viewportWidth/2 + leftButton.getPosition().x, leftButton.getPosition().y);
        heartRender(sb);
        scoreFont.draw(sb, "SCORE: " + score, camera.position.x + camera.viewportWidth/2 - 200, HEIGHT - 15);
        sb.end();
    }

    private void ravenRender(SpriteBatch sb) {
        for (Raven r : ravens) {
            boundsBuffer = r.getBounds();
            if (boundsBuffer.x + boundsBuffer.width < camera.position.x - camera.viewportWidth || boundsBuffer.y + boundsBuffer.height < 0){
                r.dispose();
                ravens.removeValue(r, false);
            }
            else sb.draw(r.getTexture(), boundsBuffer.x, boundsBuffer.y);
        }
    }

    private void boxUnitRender(SpriteBatch sb) {
        for (BoxUnit boxUnit: boxUnits) {
            boundsBuffer = boxUnit.getBounds();
            if (boundsBuffer.x + boundsBuffer.width < camera.position.x - camera.viewportWidth/2){
                boxUnit.dispose();
                boxUnits.removeValue(boxUnit, false);
            }
            else
                sb.draw(boxUnit.getTexture(), boundsBuffer.x, boundsBuffer.y);
        }

    }

    private void treeRender(SpriteBatch sb) {
        if (camera.position.x - camera.viewportWidth/2 > tree.getWidth()){ // Если дерево уже скрылось из виду, не будем зря рисовать его
            tree.dispose();
            tree = null;
        }
        else
            sb.draw(tree,0,0);
    }

    private void hawkRender(SpriteBatch sb) {
        if (hawk != null){
            boundsBuffer = hawk.getBounds();
            if (boundsBuffer.y < GROUND_HEIGHT){
                hawk.dispose();
                hawk = null;
            }
            else
                sb.draw(hawk.getTexture(), boundsBuffer.x, boundsBuffer.y);
        }
    }

    private void achivkRender(SpriteBatch sb) {
        for (Achivka ach : achivki){
            int times = ach.getAnimeTime();
            ach.getFont().draw(sb, ach.getText(), ach.getX(), ach.getY() + times);
            ach.incrAnimeTime();
            if (times == Achivka.animationFrame)   // Если ачивка отрсиовалась 10 раз, удаляем её
                achivki.removeValue(ach, false);
        }
    }

    private void insectRender(SpriteBatch sb) {
        for (Insect insect : insects)
            sb.draw(insect.getTexture(), insect.getBounds().x, insect.getBounds().y);
    }

    private void bushRender(SpriteBatch sb) {
        for (Bush b : bushes)
            sb.draw(b.getTexture(), b.getBounds().x, b.getBounds().y - Bush.DOWN_STEP);
    }

    private void heartRender(SpriteBatch sb) {
        TextureRegion[] hearts = birdie.getHurts();
        for (int i = 0; i < hearts.length; i++)
            sb.draw(hearts[i], camera.position.x - camera.viewportWidth/2 + 5*(i+1) /*gaps*/ + hearts[i].getRegionWidth()*i, HEIGHT - hearts[i].getRegionHeight() - 5);
    }

    @Override
    public void dispose() {
        bg.dispose();
        birdie.dispose();
        ground.dispose();
    }

    private void groundUpdate() {
        if (camera.position.x - camera.viewportWidth/2 > groundPos1.x + WIDTH)
            groundPos1.set(groundPos2.x + WIDTH, 0);
        if (camera.position.x - camera.viewportWidth/2 > groundPos2.x + WIDTH)
            groundPos2.set(groundPos1.x + WIDTH, 0);
    }

    public static int getGroundHeight() {
        return GROUND_HEIGHT;
    }

}
