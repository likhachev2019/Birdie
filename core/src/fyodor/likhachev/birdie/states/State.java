package fyodor.likhachev.birdie.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    //    Переменные классов:
//    Ортографическая камера - окно в наш игровой мир
    protected OrthographicCamera camera;
//    Управляет окнами (состаянимами) игры
    protected GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera();
    }

//    Обрабатывает ввод пользователя (нажатия клавиш, экрана)
    protected abstract void handleInput();

    /**
     * Обновляет картинки через определённые промежутки времени
     *
     * @param dt промежуток времени для обновления
     */
    public abstract void update(float dt);

    /**
     * Рисует экран
     *
     * @param sp объект с текстурой и координатами для её отрисовки
     */
    public abstract void render(SpriteBatch sp);
//    Уничтожает экземпляр класса, когда он уже не нужен
    public abstract void dispose();
}
