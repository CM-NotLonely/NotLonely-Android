package com.efan.notlonely_android.view.SpinKit.style;

import android.animation.ValueAnimator;

import com.efan.notlonely_android.view.SpinKit.animation.SpriteAnimatorBuilder;
import com.efan.notlonely_android.view.SpinKit.sprite.CircleSprite;


/**
 * Created by ybq.
 */
public class Pulse extends CircleSprite {

    public Pulse() {
        setScale(0f);
    }

    @Override
    public ValueAnimator getAnimation() {
        float fractions[] = new float[]{0f, 1f};
        return new SpriteAnimatorBuilder(this).
                scale(fractions, 0f, 1f).
                alpha(fractions, 255, 0).
                duration(1000).
                easeInOut(fractions)
                .build();
    }
}
