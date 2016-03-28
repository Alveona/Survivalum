package com.pomavau.crimson.View;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Pomavau on 09.03.16.
 */
public class AnimationLGDX extends Animation{
    public AnimationLGDX(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
    }
}
