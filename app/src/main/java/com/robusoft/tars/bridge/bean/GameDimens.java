package com.robusoft.tars.bridge.bean;

public class GameDimens {

	public static int RIVER_WIDTH = 540;

	public static int BRIDGE_WIDTH = 4;

	public static int PIER_HEIGHT = 200;

	public static int MINIMAL_DISTANCE = 20;
	public static int[][] TOTAL_PIER_WIDTH = getTotalPierWidth();
	public static int[] DISTANCE_LEVEL = getDistanceLevel();

	public static int ARM_LENGTH = 10;
	public static int LEG_LENGTH = 15;
	public static int TRUNK_LENGTH = 10;

	public static int HEAD_RADIUS = 6;

	public static int ANCHOR_SITE = 80;
	public static int PEOPLE_STANDING_SITE = (int) (ANCHOR_SITE * 0.8f);

	public static int ROTATING_DURATION = (int) (.65f * 1000);

	public static long MOVING_ANIMATION_DURATION = 500;

	public static int GOING_PIERWIDTH = 60;
	public static int GOING_PIERSITE = 200;
	public static int COMING_PIERWIDTH = 50;

	public static int BRIDGE_GROW_RATE = 2;

	public static int MAN_PAINT_STROKE_WIDTH = 2;

	public static int SCORE_PAINT_TEXT_WIDTH = 50;

	private static final float HTC_DENSITY = 1.5f;

	public static void initDimens(float density) {
		density /= HTC_DENSITY;
		RIVER_WIDTH *= density;

		BRIDGE_WIDTH *= density;

		PIER_HEIGHT *= density;

		MINIMAL_DISTANCE *= density;
		TOTAL_PIER_WIDTH = getTotalPierWidth(density);
		DISTANCE_LEVEL = getDistanceLevel(density);

		ARM_LENGTH *= density;
		LEG_LENGTH *= density;
		TRUNK_LENGTH *= density;

		HEAD_RADIUS *= density;

		ANCHOR_SITE *= density;
		PEOPLE_STANDING_SITE *= density;

		GOING_PIERWIDTH *= density;
		GOING_PIERSITE *= density;
		COMING_PIERWIDTH *= density;

		BRIDGE_GROW_RATE *= density;

		MAN_PAINT_STROKE_WIDTH *= density;

		SCORE_PAINT_TEXT_WIDTH *= density;

	}

	private static int[] getDistanceLevel() {
		return getDistanceLevel(1);
	}

	private static int[] getDistanceLevel(float density) {
		return new int[] { (int) (100 * density), (int) (150 * density),
				(int) (200 * density) };
	}

	private static int[][] getTotalPierWidth() {
		return getTotalPierWidth(1);
	}

	private static int[][] getTotalPierWidth(float density) {
		return new int[][] {
				{ (int) (60 * density), (int) (50 * density),
						(int) (45 * density), (int) (43 * density),
						(int) (40 * density) },
				{ (int) (40 * density), (int) (35 * density),
						(int) (26 * density), (int) (23 * density),
						(int) (20 * density) },
				{ (int) (20 * density), (int) (18 * density),
						(int) (16 * density), ((int) (14 * density)),
						(int) (7 * density) } };
	}

}
