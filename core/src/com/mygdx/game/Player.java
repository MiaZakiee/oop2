package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import jdk.internal.icu.util.OutputInt;

import static utils.Constants.PPM;
import static utils.Constants.*;

public class Player {
    private String name;
    private float health;
    private Sprite sprite;
    private Rectangle hitbox;
    private Body body;
    private boolean canMoveLeft;
    private boolean canMoveRight;

    public Player (Texture texture, Float x, Float y, World world) {
        sprite = new Sprite(texture);
        sprite.setPosition(x,y);
        body = createBox(x,y,sprite.getWidth(),sprite.getHeight(),false,world);

        hitbox = new Rectangle(x,y,sprite.getWidth(),sprite.getHeight());
//        hitbox = new Rectangle(x,y,sprite.getWidth(),sprite.getHeight());
    }


//    TODO: apply collision shit later
    public void inputUpdate(float delta) {
        int horizontalForce = 0;
//		removed can move left/right
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            --horizontalForce;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            ++horizontalForce;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            body.applyForceToCenter(0.0F, 1000.0F, false);
        }
        body.setLinearVelocity((float)(horizontalForce * 5), body.getLinearVelocity().y);
        sprite.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public Vector2 getPosition() {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        return new Vector2(x, y);
    }

    public Sprite getSprite () {
        return sprite;
    }

    private Body createBox(float x, float y, float width, float height, boolean isStatic, World world) {
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
