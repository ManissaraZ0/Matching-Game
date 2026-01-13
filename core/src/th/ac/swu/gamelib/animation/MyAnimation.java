package th.ac.swu.gamelib.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MyAnimation extends Action {

    private Image sprite;
    private TextureRegion[] subtextures;
    private float durationStep;
    private float time = 0;
    public boolean running = true;
    public boolean repeat = true;

    public MyAnimation(Image sprite, TextureRegion[] subtextures, float duration) {
    	this.sprite = sprite;
    	setTarget(sprite);
        this.subtextures = subtextures;
        this.durationStep = duration / subtextures.length;
        setSubTexture(0); // เริ่มต้นมาก็ set ภาพแรกก่อนเลย
	}
    private void setSubTexture(int index)
    {
        sprite.setDrawable(new TextureRegionDrawable(subtextures[index]));
    }


    @Override
    public void restart()
    {
        time = 0;
        setSubTexture(0);
    }
    public void pause() { running = false; }
    public void play() { running = true; }
    
    private void normalizeTime()
    {
        float duration = durationStep * subtextures.length;
        while (time >= duration)
            time -= duration;
    }

    @Override
	public boolean act(float deltaTime) {
        if (!running)
            return false;

        time += deltaTime;
        int i = (int)Math.floor(time / durationStep);
        if (i < subtextures.length)
            setSubTexture(i);
        else
        {
            if (!repeat)
            {
                setSubTexture(subtextures.length - 1); // set ซ้ำอีกที เผื่อว่า frame มันไวมากจนเรียกครั้งแรกก็เลยเวลาจบแล้ว
                running = false;
            }
            else
            {
                normalizeTime();
                int j = (int)Math.floor(time / durationStep);
                setSubTexture(j);
            }
        }
    	return false;
	}
}
