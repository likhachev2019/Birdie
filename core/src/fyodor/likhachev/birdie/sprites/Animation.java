package fyodor.likhachev.birdie.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import fyodor.likhachev.birdie.MyGame;

public class Animation {

//    Массив с кадрами анимации
    private Array<TextureRegion> frames;
//    Время отображения одного кадра
    private float maxFrameTime;
//    Время отображения текущего кадра
    private float curFrameTime;
//    Кол-во кадров анимации
    private int frameCount;
//    Отдельный кадр анимации
    private int frame;
    private int frameWidth, frameHeight;

    boolean end;

    /**
     * Конструктор
     * @param region регион текстур
     * @param frameCount кол-во кадров анимации
     * @param cycleTime длительность цикла анимации
     */
    Animation(TextureRegion region, int frameCount, float cycleTime, boolean leftStart, boolean rndFrame /*рандомный первый кадра*/) {
        frames = new Array<TextureRegion>();
        frameWidth = region.getRegionWidth()/frameCount;
        frameHeight = region.getRegionHeight();
//        Разбиваем регион текстур на кадры и заполняем ими массив
        if (leftStart)
            for (int i = 0; i < frameCount; i++)
                frames.add(new TextureRegion(region, i*frameWidth, 0, frameWidth, frameHeight));
        else
            for (int i = frameCount - 1; i >= 0; i--)
                frames.add(new TextureRegion(region, i*frameWidth, 0, frameWidth, frameHeight));
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = rndFrame ? MyGame.rnd.nextInt(frameCount) : 0;
    }
//        Изменяет текущее время анимации и меняет кадры. dt - дельта тайм
    public boolean update(float dt) {
        curFrameTime += dt;
        boolean res = false;
        if (curFrameTime > maxFrameTime){
            frame++;
            curFrameTime = 0;
            res = true;
        }
        if (frame >= frameCount){
            frame = 0;
            res = true;
            end = true;
        }
        return res;
    }

//       Метод получения текущего кадра анимации
    TextureRegion getCurFrame() {
        return frames.get(frame);
    }

    int getFrameWidth() {
        return frameWidth;
    }

    int getFrameHeight() {
        return frameHeight;
    }

    TextureRegion getSecondFrame() {
        return frames.get(1);
    }

    TextureRegion getFirstFrame() {
        return frames.get(0);
    }

    void dispose(){
        for (TextureRegion tr : frames)
            tr.getTexture().dispose();
    }
}
