package com.robusoft.tars.bridge.animation;

import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class RunningManAnimation extends MoveAnimation {

	public static final String ANIMATION_NAME_RUNNING = "runningManAnimation";
	public static final String ANIMATION_NAME_DROP = "dropManAnimation";

	private AnimationUpdataListener runListener;

	public RunningManAnimation(String animationName) {
		super(animationName);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Interpolator interpolator = getInterpolator();
		if (interpolator != null) {
			interpolatedTime = interpolator.getInterpolation(interpolatedTime);
		}
		runListener.onRunListener(interpolatedTime);
	}

	public interface AnimationUpdataListener {
		void onRunListener(float rate);
	}

	public void setRunListener(AnimationUpdataListener listener) {
		this.runListener = listener;
	}

}