package com.robusoft.tars.bridge.animation;

import android.view.animation.Interpolator;
import android.view.animation.Transformation;

public class BridgeRotatingAnimation extends MoveAnimation{

	public static final String ANIMATION_NAME = "bridgeRotatingAnimation";

	private RotatingListener rotatingListener;
	
	public BridgeRotatingAnimation(){
		super(ANIMATION_NAME);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		Interpolator interpolator = getInterpolator();
		if (interpolator != null) {
			interpolatedTime = interpolator.getInterpolation(interpolatedTime);
		}
		rotatingListener.onRotatingListener(interpolatedTime);
	}

	public void setRotatingLisenter(RotatingListener listener) {
		rotatingListener = listener;
	}

	public interface RotatingListener {
		void onRotatingListener(float degrees);
	}
	
}
