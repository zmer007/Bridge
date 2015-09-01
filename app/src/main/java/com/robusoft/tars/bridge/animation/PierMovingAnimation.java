package com.robusoft.tars.bridge.animation;

import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class PierMovingAnimation extends MoveAnimation {
    public static final String ANIMATION_NAME = "pierMovingAnimation";

    private MovingListener movingListener;

    public PierMovingAnimation() {
        super(ANIMATION_NAME);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Interpolator interpolator = getInterpolator();
        if (interpolator != null) {
            interpolatedTime = interpolator.getInterpolation(interpolatedTime);
        }
        movingListener.onMovingListener(interpolatedTime);
    }

    public void setMovingListener(MovingListener onMovingListener) {
        movingListener = onMovingListener;
    }

    public interface MovingListener {
        void onMovingListener(float movingProgressRate);
    }

}
