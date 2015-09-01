package com.robusoft.tars.bridge.bean.man;

public class WaveDegrees {

	public static final float RIGHT_ANGLE_RADIANS = 90;

	public static final float RIGHT_ARM_DEGREES = 30;
	public static final float LEFT_ARM_DEGREES = 180 - RIGHT_ARM_DEGREES;

	public static final float RIGHT_LEG_DEGREES = RIGHT_ARM_DEGREES * 2;
	public static final float LEFT_LEG_DEGREES = 180 - RIGHT_LEG_DEGREES;

	private static final float MIDDLE_LINE_DEGREES = RIGHT_ANGLE_RADIANS;

	private static final float ARM_DEGREES_INC = 9;
	private static final float LEG_DEGREES_INC = ARM_DEGREES_INC
			* (90 - RIGHT_LEG_DEGREES) / (90 - RIGHT_ARM_DEGREES);

	private float leftArm = LEFT_ARM_DEGREES;
	private float leftArmInc = -ARM_DEGREES_INC;

	private float rightArm = RIGHT_ARM_DEGREES;
	private float rightArmInc = ARM_DEGREES_INC;

	private float leftLeg = LEFT_LEG_DEGREES;
	private float leftLegInc = -LEG_DEGREES_INC;

	private float rightLeg = RIGHT_LEG_DEGREES;
	private float rightLegInc = LEG_DEGREES_INC;

	public void updateDegrees() {

		updateLeftArm();

		updateRightArm();

		updateLeftLeg();

		updateRightLeg();
	}

	private void updateLeftArm() {
		if (leftArm < MIDDLE_LINE_DEGREES) {
			leftArmInc = ARM_DEGREES_INC;
		} else if (leftArm > LEFT_ARM_DEGREES) {
			leftArmInc = -ARM_DEGREES_INC;
		}
		leftArm += leftArmInc;
	}

	private void updateRightArm() {
		if (rightArm < RIGHT_ARM_DEGREES) {
			rightArmInc = ARM_DEGREES_INC;
		} else if (rightArm > MIDDLE_LINE_DEGREES) {
			rightArmInc = -ARM_DEGREES_INC;
		}
		rightArm += rightArmInc;
	}

	private void updateLeftLeg() {
		if (leftLeg < MIDDLE_LINE_DEGREES) {
			leftLegInc = LEG_DEGREES_INC;
		} else if (leftLeg > LEFT_LEG_DEGREES) {
			leftLegInc = -LEG_DEGREES_INC;
		}
		leftLeg += leftLegInc;
	}

	private void updateRightLeg() {
		if (rightLeg < RIGHT_LEG_DEGREES) {
			rightLegInc = LEG_DEGREES_INC;
		} else if (rightLeg > MIDDLE_LINE_DEGREES) {
			rightLegInc = -LEG_DEGREES_INC;
		}
		rightLeg += rightLegInc;
	}

	public float getLeftArm() {
		return leftArm;
	}

	public float getRightArm() {
		return rightArm;
	}

	public float getLeftLeg() {
		return leftLeg;
	}

	public float getRightLeg() {
		return rightLeg;
	}

}