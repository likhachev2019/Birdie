package fyodor.likhachev.birdie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import fyodor.likhachev.birdie.states.GameStateManager;
import fyodor.likhachev.birdie.states.MenuState;

public class MyGame extends ApplicationAdapter {

	public static Random rnd = new Random();

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Birdie";
	public static int highScore; // лучший результат
	static Preferences prefs; // объект класса Preferences

	private GameStateManager gsm;
	// Хранит текстуры и координаты для их отрисовки
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		// Очищает экран
		Gdx.gl.glClearColor(1, 0, 0, 1);
		prefs = Gdx.app.getPreferences("My Preferences"); //получаем файл персональных данных
		highScore = prefs.getInteger("highscore"); //получаем текущий лучший результат
		System.out.println(highScore);

		// Создаёт новый экран меню и кладёт его в вершину стэка
		gsm.push(new MenuState(gsm, highScore));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Передаём время, прошедшее между последним и текущим кадром в секундах
		gsm.update(Gdx.graphics.getDeltaTime());
		// Отрисовываем верхний экран в стэке
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
