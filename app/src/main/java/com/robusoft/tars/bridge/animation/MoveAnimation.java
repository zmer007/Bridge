package com.robusoft.tars.bridge.animation;

import android.view.animation.Animation;

public abstract class MoveAnimation extends Animation {

	private String animName;

	public MoveAnimation(String name) {
		this.animName = name;
	}

	public String getAnimName() {
		return animName;
	}
	
}
