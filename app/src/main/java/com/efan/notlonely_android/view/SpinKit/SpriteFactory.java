package com.efan.notlonely_android.view.SpinKit;


import com.efan.notlonely_android.view.SpinKit.sprite.Sprite;
import com.efan.notlonely_android.view.SpinKit.style.ChasingDots;
import com.efan.notlonely_android.view.SpinKit.style.Circle;
import com.efan.notlonely_android.view.SpinKit.style.CubeGrid;
import com.efan.notlonely_android.view.SpinKit.style.DoubleBounce;
import com.efan.notlonely_android.view.SpinKit.style.FadingCircle;
import com.efan.notlonely_android.view.SpinKit.style.FoldingCube;
import com.efan.notlonely_android.view.SpinKit.style.Pulse;
import com.efan.notlonely_android.view.SpinKit.style.RotatingCircle;
import com.efan.notlonely_android.view.SpinKit.style.RotatingPlane;
import com.efan.notlonely_android.view.SpinKit.style.ThreeBounce;
import com.efan.notlonely_android.view.SpinKit.style.WanderingCubes;
import com.efan.notlonely_android.view.SpinKit.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            default:
                break;
        }
        return sprite;
    }
}
