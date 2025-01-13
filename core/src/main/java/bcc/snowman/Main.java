package bcc.snowman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private List<Body> balls;
    private ShapeRenderer shapeRenderer;

    private float WIDTH = 9;// box2d units is 'meters'
    private float HEIGHT = 6;

    public void create() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer(); // Initialize ShapeRenderer

        // Set up the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        addBorders();
        balls = new ArrayList<>();

        

        // Add some balls
        for (int i = 0; i < 10; i++) {   
            addSnowman();
        }
    }

    public void addBorders(){
        createWall(WIDTH/2, 0, WIDTH, .01f);//BOTTOM
        createWall(WIDTH/2, HEIGHT, WIDTH, .01f);//TOP
        createWall(0, HEIGHT/2, .01f, HEIGHT);//LEFT
        createWall(WIDTH, HEIGHT/2, .01f, HEIGHT);//RIGHT


    }
    private void createWall(float centerX, float centerY, float width, float height) {
        // Create the body definition
        BodyDef wallBodyDef = new BodyDef();
        wallBodyDef.position.set(new Vector2(centerX,centerY));
    
        // Create the body in the world
        Body wallBody = world.createBody(wallBodyDef);
    
        // Create a polygon shape
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(width/2, height/2);
    
        // Create a fixture for the shape and attach it to the body
        wallBody.createFixture(wallShape, 0.0f);
    
        // Dispose of the shape
        wallShape.dispose();
    }
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        world.step(1 / 60f, 6, 2);

        camera.update();

        // Render the ground and physics bodies using the debug renderer
        debugRenderer.render(world, camera.combined);

        // Render the filled circles
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1); // Set color to white
        for (Body ball : balls) {
            Vector2 bodyPosition = ball.getPosition(); // Position of the body
            float bodyAngle = ball.getAngle(); // Rotation of the body
            for (Fixture fixture : ball.getFixtureList()) {
                if (fixture.getShape() instanceof CircleShape) {
                    CircleShape circleShape = (CircleShape) fixture.getShape();
        
                    // Get the local position of the circle shape
                    Vector2 localPosition = circleShape.getPosition();
                    localPosition.rotateRad(bodyAngle); // Apply body rotation
                    localPosition.add(bodyPosition); // Add body position to get world position
        
                    // Render the circle
                    shapeRenderer.circle(localPosition.x, localPosition.y, circleShape.getRadius(), 30);
                }
            }
        }
        shapeRenderer.end();
    }

    private float randomFloat(float lower, float upper){
        return lower + (float) Math.random() * (upper - lower);
    }

    private void addSnowman() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(randomFloat(0, WIDTH), randomFloat(0, HEIGHT));

        Body ball = world.createBody(bodyDef);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(.25f);

        FixtureDef bottomSnowball = new FixtureDef();
        bottomSnowball.shape = ballShape;
        bottomSnowball.density = 1f;
        bottomSnowball.restitution = 0.8f; // Make the ball bouncy

        ball.createFixture(bottomSnowball);

        //middle snowball
        ballShape.setRadius(.2f);
        ballShape.setPosition(new Vector2(0, .25f));
        FixtureDef middleSnowball = new FixtureDef();
        middleSnowball.shape = ballShape;
        middleSnowball.density = 1f;
        middleSnowball.restitution = 0.8f; 

        ball.createFixture(middleSnowball);

        //final snowball
        //ADD THE CODE HERE

        ballShape.dispose();
        ball.setLinearVelocity(new Vector2(randomFloat(-2,2), randomFloat(-2,2)));

        // Set initial angular velocity
        ball.setAngularVelocity(randomFloat(-2,2));
        balls.add(ball);
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        shapeRenderer.dispose(); // Dispose of ShapeRenderer
    }
}
