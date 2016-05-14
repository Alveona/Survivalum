package com.pomavau.crimson.Controller;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import javax.security.auth.callback.TextInputCallback;

/**
 * Created by Pomavau on 07.03.16.
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean animationFinishedAtLeastOnce = false;
    private boolean loop = true;


    public Animation(TextureRegion region, int frameCount, float cycleTime)
    {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++)
        {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime /frameCount;
        frame = 0;
    }
    public Animation(TextureRegion region, int frameCount, float cycleTime, boolean loop)
    {
        this.loop = loop;
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++)
        {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime /frameCount;
        frame = 0;
    }
    public void update(float delta)
    {
        currentFrameTime += delta;
        if ((currentFrameTime > maxFrameTime && loop == true) || (currentFrameTime > maxFrameTime && loop == false && animationFinishedAtLeastOnce == false))
        {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            if(loop == true){
                frame = 0;
                animationFinishedAtLeastOnce = true;
            }
            else
            {
                animationFinishedAtLeastOnce = true;
            }
        }


    }
    public TextureRegion getFrame()
    {
        return frames.get(frame);
    }

    public int getFrameCount(){return frameCount;}

    public int getCurrentFrame(){return frame;}

    public boolean isFinishedOnce()
    {
        return animationFinishedAtLeastOnce;
    }

    public void setFinishedOnce(boolean bool)
    {
        animationFinishedAtLeastOnce = bool;
    }



}
