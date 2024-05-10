package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class Contact implements ContactListener {

    @Override
    public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {
        System.out.println("Contact");
        Shape.Type fa = contact.getFixtureA().getType();
        Fixture fb = contact.getFixtureB();
        System.out.println(fa +" has hit "+ fb.getBody().getType());
    }

    @Override
    public void endContact(com.badlogic.gdx.physics.box2d.Contact contact) {
        Shape.Type fa = contact.getFixtureA().getType();
        Fixture fb = contact.getFixtureB();
        System.out.println(fa +" ended contact with "+ fb.getBody().getType());
    }

    @Override
    public void preSolve(com.badlogic.gdx.physics.box2d.Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(com.badlogic.gdx.physics.box2d.Contact contact, ContactImpulse impulse) {

    }
}
