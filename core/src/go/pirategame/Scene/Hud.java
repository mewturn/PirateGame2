package go.pirategame.Scene;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import go.pirategame.PirateGame;
import go.pirategame.Sprites.Pirate;

/**
 * Created by Amy on 1/3/16.
 */
public class Hud implements Disposable {
    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    private static Integer score;

    private Pirate pirate;

    //Scene2D widgets
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label playerLabel;
    public static Label testLabel,testLabel2;
    private Label healthLabel;
    private Label healthValueLabel;


    public Hud(SpriteBatch sb,Pirate pirate){
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        this.pirate=pirate;

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(PirateGame.V_WIDTH *(PirateGame.FULL_WIDTH- PirateGame.MAP_WIDTH)/PirateGame.FULL_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel = new Label("Pirate 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel= new Label("HEALTH", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        healthValueLabel= new Label(String.format("%03d",pirate.getHealth()), new Label.LabelStyle(new BitmapFont(),Color.WHITE));


        testLabel= new Label("Testing", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        testLabel2= new Label("Testing", new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(playerLabel).center().padTop(10);
        table.row();
        table.add(timeLabel).left().padLeft(PirateGame.HUD_PAD);
        table.add(countdownLabel).right().padRight(PirateGame.HUD_PAD);
        table.row();
        table.add(healthLabel).left().padLeft(PirateGame.HUD_PAD);
        table.add(healthValueLabel).right().padRight(PirateGame.HUD_PAD);
        table.row();
        table.add(testLabel).padLeft(PirateGame.HUD_PAD);
        table.row();
        table.add(testLabel2).padLeft(PirateGame.HUD_PAD);

        //add our table to the stage
        stage.addActor(table);

    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        healthValueLabel.setText(String.format("%03d",pirate.getHealth()));
        testLabel.setText(String.format("%1.2f",pirate.b2body.getPosition().x));
        testLabel2.setText(String.format("%1.2f",pirate.b2body.getPosition().y));
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static void setTestMsg(String msg){
        testLabel.setText(msg);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() { return timeUp; }
}
