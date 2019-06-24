package fyodor.likhachev.birdie.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

//    Массив игровых состояний (экранов)
    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state) {
        states.push(state);
    }

    public State peek() {
        return states.peek();
    }

//    Извлекает элемент, удаляя его из стэка
    public void pop() {
        states.pop().dispose();
    }

    public void set(State state) {
        pop();
        push(state);
    }

    /**
     * Обновляет в игре состояние, которое находится на вершине стэка
     *
     * @param dt дельта обновления
     */
    public void update(float dt) {
        states.peek().update(dt);
    }

    /**
     * Достаёт состояние (кадр) и отрисовывает его
     *
     * @param sb объект для отрисовки
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
