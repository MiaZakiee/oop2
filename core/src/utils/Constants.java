package utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Constants {
    public static final float PPM = 32.0F;

    private Body createBox(int x, int y, int width, int height, boolean isStatic, World world) {
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
