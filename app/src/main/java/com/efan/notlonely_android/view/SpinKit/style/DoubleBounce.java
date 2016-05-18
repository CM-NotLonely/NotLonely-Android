package com.efan.notlonely_android.view.SpinKit.style;

import android.animation.ValueAnimator;

import com.efan.notlonely_android.view.SpinKit.animation.SpriteAnimatorBuilder;
import com.efan.notlonely_android.view.SpinKit.sprite.CircleSprite;
import com.efan.notlonely_android.view.SpinKit.sprite.Sprite;
import com.efan.notlonely_android.view.SpinKit.sprite.SpriteGroup;


/**
 * Created by ybq.
 */
public class DoubleBounce extends SpriteGroup {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Bounce(), new Bounce()
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        sprites[1].setAnimationDelay(-1000);
    }

    class Bounce extends CircleSprite {

        public Bounce() {
            setAlpha(153);
            setScale(0f);
        }

        @Override
        public ValueAnimator getAnimation() {
            float fractions[] = new float[]{0f, 0.5f, 1f};
            return new SpriteAnimatorBuilder(this).scale(fractions, 0f, 1f, 0f).
                    duration(2000).
                    easeInOut(fractions)
                    .build();
        }
    }
}
