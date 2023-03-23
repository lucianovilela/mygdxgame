package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	BitmapFont font;

	Matrix4 matrix;

	private OrthographicCamera camera;
	private Array<Rectangle> elements;

	private long lastDropTime;

	private int HEIGTH;
	private int WIDTH;


	private ShapeRenderer shapeRenderer;
	@Override
	public void create () {

		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 600, 800);

		font = new BitmapFont();

		batch = new SpriteBatch();
		matrix = new Matrix4();
		elements = new Array<Rectangle>();
		WIDTH=Gdx.graphics.getWidth();
		HEIGTH=Gdx.graphics.getHeight();
		//boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
		spawnRect();

	}
	private void spawnRect() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = 50;//MathUtils.random(0,WIDTH );
		raindrop.y = 20;//MathUtils.random(0,HEIGTH );
		raindrop.width = 64;
		raindrop.height = 64;
		elements.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}
	@Override
	public void render () {
		ScreenUtils.clear(Color.WHITE);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		float accelZ = Gdx.input.getAccelerometerZ();
		shapeRenderer.setProjectionMatrix(camera.combined);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		batch.begin();

		for(Rectangle rect: elements) {
			shapeRenderer.setColor(Color.RED);
			Vector2 accel = new Vector2(-accelX*3, 0);
			Vector2 newPosition = accel.add(new Vector2(rect.getX(), rect.getY()));
			rect.setPosition(newPosition);
			if(rect.x < 0) rect.x = 0;
			if(rect.x > WIDTH - 64) rect.x = WIDTH - 64;
			if(rect.y < 0) rect.y = 0;
			if(rect.y > HEIGTH - 64) rect.y = HEIGTH - 64;


			shapeRenderer.rect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}

		shapeRenderer.end();
		font.setColor(Color.BLACK);
		font.getData().setScale(5f);
		font.draw(batch, "x:"+Float.toString(accelX), 50, 600);
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		font.dispose();
		shapeRenderer.dispose();

	}


}
