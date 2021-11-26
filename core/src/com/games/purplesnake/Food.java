package com.games.purplesnake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public class Food {
    private final GridPoint2 point;
    private final Texture texture;

    public Food( Texture texture) {
        this.point = new GridPoint2();
        this.texture = texture;
    }

    public void draw(Batch batch) {
        batch.draw(texture, point.x, point.y);
    }

    public void generateRandomPoint() {
        int numberOfXPoints = Gdx.graphics.getWidth() / texture.getWidth();
        int numberOfYPoints = Gdx.graphics.getHeight() / texture.getHeight();

        this.point.set(
                (int) (Math.random() * numberOfXPoints) * texture.getWidth(),
                (int) (Math.random() * numberOfYPoints) * texture.getHeight()
        );
    }

    public GridPoint2 getPoint() {
        return point;
    }
}
