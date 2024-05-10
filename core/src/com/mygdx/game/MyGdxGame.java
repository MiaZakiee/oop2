package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import jdk.tools.jlink.internal.Platform;

import static utils.Constants.PPM;

public class MyGdxGame extends ApplicationAdapter {
	private Box2DDebugRenderer b2dr;
	private World world;
	private Player player;
	private Body[] platform = new Body[10];
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont myFont;

	private Body ball;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / 2, h / 2);

		world = new World(new Vector2(0.0F, -9.8F), true);
		world.setContactListener(new Contact());
		b2dr = new Box2DDebugRenderer();

		player = new Player(new Texture("assets/idle_ball.png"),5.0F,2.0F,world);
		platform[0] = createBox(0, 0, 800, 1, true);
		platform[1] = createBox(0, 300, 800, 1, true);
		platform[3] = createBox(400, 150, 1, 300, true);
		platform[4] = createBox(-400, 150, 1, 300, true);
		ball = Ball.createBall(world,0,5,1);

		batch = new SpriteBatch();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/COMICSANS.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 14;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?: ";
		myFont = generator.generateFont(parameter);
		myFont.setColor(Color.WHITE);
		generator.dispose();
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		Gdx.gl.glClear(16384);
		b2dr.render(world, camera.combined.scl(PPM));

		batch.begin();
		myFont.draw(batch, String.format("X: %.2f Y: %.2f", player.getPosition().x, player.getPosition().y), 50, 450);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width / 2, height / 2);
	}

	@Override
	public void dispose() {
		world.dispose();
		b2dr.dispose();
		batch.dispose();
		myFont.dispose();
	}

	private void update(float delta) {
		world.step(0.016666668F, 6, 2);
		player.inputUpdate(delta);
		cameraUpdate(delta);
	}

	private void cameraUpdate(float delta) {
		// Set the camera's position to a fixed point
		camera.position.set(0, 250, 0); // Adjust the coordinates according to your desired fixed position

		// Zoom out the camera
		float zoomLevel = 2.5f; // Adjust the zoom level as needed
		camera.zoom = zoomLevel;

		camera.update();
	}

//	TODO: maybe add a parent class with this functions and others like getx gety for inheritance
	private Body createBox(int x, int y, int width, int height, boolean isStatic) {
		BodyDef def = new BodyDef();
		def.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		Body pBody = world.createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width / 2) / PPM, (height / 2) / PPM);
		pBody.createFixture(shape, 1.0F);
		shape.dispose();
		return pBody;
	}
}
