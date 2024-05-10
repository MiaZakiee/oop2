package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
    public static Body createBall(World world, float x, float y, float radius) {
        // Create the body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Make it dynamic (movable)
        bodyDef.position.set(x, y);

        // Create the body in the world using the definition
        Body body = world.createBody(bodyDef);

        // Define a circle shape
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        // Define the fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f; // Bounciness 0.6f

        // Create the fixture on the body
        body.createFixture(fixtureDef);

        // Clean up
        circle.dispose();

        return body;
    }
}
