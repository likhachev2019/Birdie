package fyodor.likhachev.birdie.util;

import com.badlogic.gdx.math.Rectangle;

import fyodor.likhachev.birdie.states.PlayState;

import static fyodor.likhachev.birdie.sprites.FirTree.DOWN_STEP;

//  Наследуемся т.к. ёлка подкласс растений и её метод границ возвращает прямоугольник
public class Triangle extends Rectangle {

    public int x, y, width, height;
    private double tangent;
    private float relativeY;
    public Triangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        tangent = (double)(height) / width * 2;
    }

    @Override
    public boolean contains(float x, float y) {
        // высота птицы в координатах с OY в нижней точке ёлки
        relativeY = y - PlayState.getGroundHeight() + DOWN_STEP;
        return y <= this.y + height && x >= this.x + relativeY/tangent && x <= this.x + width - relativeY/tangent;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
