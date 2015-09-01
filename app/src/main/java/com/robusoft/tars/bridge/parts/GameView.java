package com.robusoft.tars.bridge.parts;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.robusoft.tars.bridge.animation.BridgeRotatingAnimation;
import com.robusoft.tars.bridge.animation.MoveAnimation;
import com.robusoft.tars.bridge.animation.PierMovingAnimation;
import com.robusoft.tars.bridge.animation.RunningManAnimation;
import com.robusoft.tars.bridge.bean.AgorithmUtils;
import com.robusoft.tars.bridge.bean.GameDimens;
import com.robusoft.tars.bridge.bean.bridge.Bridge;
import com.robusoft.tars.bridge.bean.bridge.Pier;
import com.robusoft.tars.bridge.bean.man.LeftArm;
import com.robusoft.tars.bridge.bean.man.LeftLeg;
import com.robusoft.tars.bridge.bean.man.Limbs;
import com.robusoft.tars.bridge.bean.man.RightArm;
import com.robusoft.tars.bridge.bean.man.RightLeg;
import com.robusoft.tars.bridge.bean.man.TheMan;
import com.robusoft.tars.bridge.bean.man.Trunk;
import com.robusoft.tars.bridge.bean.man.WaveDegrees;

public class GameView extends View {

	private static final String BEST_SCORE_STOR = "bestScoreStor";

	private int score;
	private int bestScore;
	private int gameLevel;
	private boolean gameOver;
	private boolean forecastGameOver;
	private SharedPreferences bestScoreStor;

	private int distanceGoing;

	private int goingPierWidth = GameDimens.GOING_PIERWIDTH;
	private int goingPierSite = GameDimens.GOING_PIERSITE;

	private int distanceComing;

	private int comingPierWidth = GameDimens.COMING_PIERWIDTH;
	private int comingPierSite = GameDimens.RIVER_WIDTH + comingPierWidth;

	private Paint manPaint;
	private Paint headPaint;
	private Paint pierPaint;
	private Paint bridgePaint;
	private Paint scorePaint;

	private Pier disappearing;
	private Pier standing;
	private Pier going;
	private Pier coming;

	private TheMan theMan;
	private Trunk trunk;
	private Limbs leftLeg;
	private Limbs leftArm;
	private Limbs rightArm;
	private Limbs rightLeg;
	private WaveDegrees waveLimbs;

	private boolean isMoving;
	private MoveAnimation currentAnim;
	private PierMovingAnimation movingPiers;
	private RunningManAnimation runningAnim;
	private RunningManAnimation dropAnim;
	private BridgeRotatingAnimation rotatingAnim;

	public GameView(Context context) {
		this(context, null);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initComponent();
	}

	private void initComponent() {

		score = 0;
		gameLevel = 0;
		isMoving = false;
		gameOver = false;
		forecastGameOver = false;
		bestScoreStor = getContext().getSharedPreferences(BEST_SCORE_STOR,
				Context.MODE_PRIVATE);
		bestScore = bestScoreStor.getInt(BEST_SCORE_STOR, 0);

		initMan();

		initPierBridge();

		initMovingAnimation();
	}

	private void initMan() {
		manPaint = new Paint();
		manPaint.setStyle(Style.STROKE);
		manPaint.setStrokeWidth(GameDimens.MAN_PAINT_STROKE_WIDTH);
		manPaint.setAntiAlias(true);
		manPaint.setColor(Color.BLACK);

		scorePaint = new Paint(manPaint);
		scorePaint.setStyle(Style.FILL);
		scorePaint.setTextSize(GameDimens.SCORE_PAINT_TEXT_WIDTH);

		headPaint = new Paint();
		headPaint.setStyle(Style.FILL);
		headPaint.setAntiAlias(true);
		headPaint.setColor(Color.BLACK);

		theMan = new TheMan(GameDimens.PEOPLE_STANDING_SITE,
				GameDimens.PIER_HEIGHT * 2 - GameDimens.HEAD_RADIUS
						- GameDimens.LEG_LENGTH - GameDimens.TRUNK_LENGTH + 1);

		waveLimbs = new WaveDegrees();

		leftArm = new LeftArm(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS, GameDimens.ARM_LENGTH);
		rightArm = new RightArm(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS, GameDimens.ARM_LENGTH);
		leftLeg = new LeftLeg(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS + GameDimens.TRUNK_LENGTH,
				GameDimens.LEG_LENGTH);
		rightLeg = new RightLeg(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS + GameDimens.TRUNK_LENGTH,
				GameDimens.LEG_LENGTH);
		trunk = new Trunk(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS, theMan.getManCoorX(),
				theMan.getManCoorY() + GameDimens.HEAD_RADIUS
						+ GameDimens.TRUNK_LENGTH);

	}

	private void initPierBridge() {
		bridgePaint = new Paint();
		bridgePaint.setStyle(Style.STROKE);
		bridgePaint.setStrokeWidth(GameDimens.BRIDGE_WIDTH);
		bridgePaint.setAntiAlias(true);
		bridgePaint.setColor(Color.BLACK);

		pierPaint = new Paint();
		pierPaint.setStyle(Style.FILL);
		pierPaint.setAntiAlias(true);
		pierPaint.setColor(Color.BLACK);

		disappearing = new Pier(GameDimens.ANCHOR_SITE, GameDimens.ANCHOR_SITE);
		standing = new Pier(GameDimens.ANCHOR_SITE, GameDimens.ANCHOR_SITE);
		going = new Pier(goingPierSite, goingPierWidth);
		coming = new Pier(comingPierSite, comingPierWidth);

		distanceGoing = goingPierSite - GameDimens.ANCHOR_SITE;
		distanceComing = comingPierSite - goingPierSite + comingPierWidth;
	}

	private void initMovingAnimation() {

		movingPiers = new PierMovingAnimation();
		movingPiers.setDuration(GameDimens.MOVING_ANIMATION_DURATION);
		movingPiers.setInterpolator(new LinearInterpolator());
		movingPiers.setMovingListener(new PierMovingAnimation.MovingListener() {
			@Override
			public void onMovingListener(float movingProgressRate) {

				int deltaGoing = (int) (-distanceGoing * movingProgressRate);
				int deltaComing = (int) (-distanceComing * movingProgressRate);

				disappearing.updatePierSite(deltaGoing);
				standing.updatePierSite(deltaGoing);
				disappearing.getBridge().rebuildBridgeSite(
						standing.getPierSite());
				standing.getBridge().rebuildBridgeSite(standing.getPierSite());
				going.updatePierSite(deltaGoing);
				coming.updatePierSite(deltaComing);
				updateManSite(deltaGoing);
				invalidate();
			}
		});

		runningAnim = new RunningManAnimation(
				RunningManAnimation.ANIMATION_NAME_RUNNING);
		runningAnim.setDuration(GameDimens.MOVING_ANIMATION_DURATION * 2);
		runningAnim.setInterpolator(new LinearInterpolator());
		runningAnim.setRunListener(new RunningManAnimation.AnimationUpdataListener() {

			@Override
			public void onRunListener(float rate) {
				int deltaRuning = (int) (distanceGoing * rate);
				updateManSite(deltaRuning);
				invalidate();
			}
		});

		dropAnim = new RunningManAnimation(
				RunningManAnimation.ANIMATION_NAME_DROP);
		dropAnim.setDuration(GameDimens.MOVING_ANIMATION_DURATION);
		dropAnim.setInterpolator(new AccelerateInterpolator());
		dropAnim.setRunListener(new RunningManAnimation.AnimationUpdataListener() {
			@Override
			public void onRunListener(float rate) {
				int deltaDrop = (int) (GameDimens.PIER_HEIGHT * rate);
				updateDropSite(deltaDrop);
				invalidate();
			}
		});

		rotatingAnim = new BridgeRotatingAnimation();
		rotatingAnim.setDuration(GameDimens.ROTATING_DURATION);
		rotatingAnim.setInterpolator(new LinearInterpolator());
		rotatingAnim.setRotatingLisenter(new BridgeRotatingAnimation.RotatingListener() {
			@Override
			public void onRotatingListener(float rate) {
				float degrees = Bridge.ROTATE_DEGREES * rate;
				standing.getBridge().rotateBridge(degrees);
				invalidate();
			}

		});

	}

	private void updateManSite(int deltaRuning) {
		theMan.updateManSite(deltaRuning);
		updateMan();
	}

	private void updateDropSite(int deltaDrop) {
		theMan.updateDropSite(deltaDrop);
		updateMan();
	}

	private void updateMan() {
		trunk.updateSite(theMan.getManCoorX(), theMan.getManCoorY()
						+ GameDimens.HEAD_RADIUS, theMan.getManCoorX(),
				theMan.getManCoorY() + GameDimens.HEAD_RADIUS
						+ GameDimens.TRUNK_LENGTH);
		leftArm.updateSite(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS, GameDimens.ARM_LENGTH);
		rightArm.updateSite(theMan.getManCoorX(), theMan.getManCoorY()
				+ GameDimens.HEAD_RADIUS, GameDimens.ARM_LENGTH);
		leftLeg.updateSite(theMan.getManCoorX(), theMan.getManCoorY()
						+ GameDimens.HEAD_RADIUS + GameDimens.TRUNK_LENGTH,
				GameDimens.LEG_LENGTH);
		rightLeg.updateSite(theMan.getManCoorX(), theMan.getManCoorY()
						+ GameDimens.HEAD_RADIUS + GameDimens.TRUNK_LENGTH,
				GameDimens.LEG_LENGTH);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawScore(canvas);
		drawMan(canvas);
		translateCanvas(canvas);
		drawPiers(canvas);
	}

	private void drawScore(Canvas canvas) {
		hiddenTask();
		canvas.drawText("最高：" + bestScore, 30, GameDimens.PIER_HEIGHT / 2,
				scorePaint);
		if (gameOver) {
			canvas.drawText("结束：" + score, 30, GameDimens.PIER_HEIGHT,
					scorePaint);
		} else {
			canvas.drawText("得分：" + score, 30, GameDimens.PIER_HEIGHT,
					scorePaint);
		}
	}

	private void drawMan(Canvas canvas) {
		if (theMan.isQuiet()) {
			drawQuietMan(canvas);
		} else {
			drawRunningMan(canvas, waveLimbs);
			waveLimbs.updateDegrees();
			invalidate();
		}
	}

	private void drawRunningMan(Canvas canvas, WaveDegrees degrees) {
		drawHead(canvas);
		drawTrunk(canvas);
		drawLimbs(canvas, degrees);
	}

	private void drawQuietMan(Canvas canvas) {
		drawHead(canvas);
		drawTrunk(canvas);
		drawLimbs(canvas);
	}

	private void drawHead(Canvas canvas) {
		canvas.drawCircle(theMan.getManCoorX(), theMan.getManCoorY(),
				GameDimens.HEAD_RADIUS, headPaint);
	}

	private void drawTrunk(Canvas canvas) {
		trunk.draw(canvas, manPaint);
	}

	private void drawLimbs(Canvas canvas) {
		drawLeftArm(canvas, WaveDegrees.LEFT_ARM_DEGREES);
		drawRightArm(canvas, WaveDegrees.RIGHT_ARM_DEGREES);
		drawLeftLeg(canvas, WaveDegrees.LEFT_LEG_DEGREES);
		drawRightLeg(canvas, WaveDegrees.RIGHT_LEG_DEGREES);

	}

	private void drawLimbs(Canvas canvas, WaveDegrees degrees) {
		drawLeftArm(canvas, degrees.getLeftArm());
		drawRightArm(canvas, degrees.getRightArm());
		drawLeftLeg(canvas, degrees.getLeftLeg());
		drawRightLeg(canvas, degrees.getRightLeg());
	}

	private void drawLeftArm(Canvas canvas, float degrees) {
		leftArm.buildStopCoorBasisAngle(degrees);
		leftArm.draw(canvas, manPaint);
	}

	private void drawRightArm(Canvas canvas, float degrees) {
		rightArm.buildStopCoorBasisAngle(degrees);
		rightArm.draw(canvas, manPaint);
	}

	private void drawLeftLeg(Canvas canvas, float degrees) {
		leftLeg.buildStopCoorBasisAngle(degrees);
		leftLeg.draw(canvas, manPaint);
	}

	private void drawRightLeg(Canvas canvas, float degrees) {
		rightLeg.buildStopCoorBasisAngle(degrees);
		rightLeg.draw(canvas, manPaint);
	}

	private void translateCanvas(Canvas canvas) {
		canvas.save();
		canvas.translate(0, GameDimens.BRIDGE_WIDTH / 2);
		drawBridge(canvas);
		canvas.restore();
	}

	private void drawBridge(Canvas canvas) {
		drawPierBridge(canvas, disappearing);
		drawPierBridge(canvas, standing);
	}

	private void drawPierBridge(Canvas canvas, Pier pier) {
		canvas.drawLine(pier.getBridge().getStartX(), pier.getBridge()
				.getStartY(), pier.getBridge().getStopX(), pier.getBridge()
				.getStopY(), bridgePaint);
	}

	private void drawPiers(Canvas canvas) {
		canvas.save();
		canvas.translate(0, GameDimens.PIER_HEIGHT * 2);

		drawPier(canvas, disappearing);
		drawPier(canvas, standing);
		drawPier(canvas, going);
		drawPier(canvas, coming);

		canvas.restore();
	}

	private void drawPier(Canvas canvas, Pier pier) {
		canvas.drawRect(pier.getRect(), pierPaint);
	}

	@Override
	public boolean onTouchEvent(@NonNull MotionEvent event) {
		if (isMoving() || gameOver) {
			return true;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isOnPress = true;
			bridgeTask.post(task);
			break;
		case MotionEvent.ACTION_UP:
			isOnPress = false;
			performClick();
			break;
		default:
			break;
		}
		return true;
	}

	private boolean isMoving() {
		return isMoving;
	}

	private void setMoving(boolean moving) {
		isMoving = moving;
	}

	@Override
	public boolean performClick() {
		startMoveAnimation(rotatingAnim);
		return super.performClick();
	}

	private void startMoveAnimation(MoveAnimation anim) {
		setMoving(true);
		startAnimation(anim);
		setCurrenAnimation(anim);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(GameDimens.RIVER_WIDTH, GameDimens.PIER_HEIGHT * 3);
	}

	@Override
	protected void onAnimationEnd() {
		super.onAnimationEnd();
		MoveAnimation anim = getCurrentAnimation();
		switch (anim.getAnimName()){
			case BridgeRotatingAnimation.ANIMATION_NAME:
				startMoveAnimation(runningAnim);
				theMan.setQuiet(false);
				theMan.refreshOldCoorX();
				int bridgeLength = standing.getBridge().getBridgeLength();
				if (bridgeLength > distanceGoing
						|| bridgeLength < distanceGoing - going.getPierWidth()) {
					distanceGoing = bridgeLength;
					forecastGameOver = true;
				}
				break;
			case RunningManAnimation.ANIMATION_NAME_RUNNING:
				theMan.setQuiet(true);
				theMan.refreshOldCoorX();
				if (forecastGameOver) {
					startMoveAnimation(dropAnim);
				} else {
					startMoveAnimation(movingPiers);
				}
				break;
			case RunningManAnimation.ANIMATION_NAME_DROP:
				gameOver = true;
				invalidate();
				break;
			case PierMovingAnimation.ANIMATION_NAME:
				refreshPiersSite();
				refreshPiersSlidingDistance();
				setMoving(false);
				if (!gameOver) {
					score++;
					if (bestScore < score) {
						bestScore = score;
						bestScoreStor.edit().putInt(BEST_SCORE_STOR, bestScore)
								.apply();
					}
					gameRuler();
				}
				invalidate();
				break;
		}
	}

	private void gameRuler() {
		if (score < 3) {
			gameLevel = 0;
		} else if (score < 9) {
			gameLevel = Math.random() > .5 ? 0 : 1;
		} else if (score < 15) {
			gameLevel = Math.random() > .5 ? 1 : 2;
		} else {
			gameLevel = 2;
		}
	}

	private void refreshPiersSite() {
		disappearing.rebuildPier(standing.getPierSite(),
				standing.getPierWidth());
		disappearing.setBridge(standing.getBridge());

		standing.rebuildPier(going.getPierSite(), going.getPierWidth());
		standing.setBridge(new Bridge(standing.getPierSite()));

		going.rebuildPier(coming.getPierSite(), coming.getPierWidth());

		int[] siteAndwidth = AgorithmUtils.getComingSiteAndWidth(gameLevel);
		coming.rebuildPier(siteAndwidth[0], siteAndwidth[1]);
	}

	private void refreshPiersSlidingDistance() {
		distanceGoing = AgorithmUtils.getDeltaGoing(going.getPierSite());
		distanceComing = AgorithmUtils.getDeltaComing(gameLevel);
	}

	private void setCurrenAnimation(Animation anim) {
		currentAnim = (MoveAnimation) anim;
	}

	private MoveAnimation getCurrentAnimation() {
		return currentAnim;
	}

	public void resetGame() {
		initComponent();
		invalidate();
	}

	private boolean isOnPress = false;
	private Handler bridgeTask = new Handler();
	private static final long CIRCLE_LENGTH = 18;
	private Runnable task = new Runnable() {

		@Override
		public void run() {
			if (isOnPress) {
				standing.getBridge().rebuildBridgeLength(
						GameDimens.BRIDGE_GROW_RATE);
				bridgeTask.postDelayed(task, CIRCLE_LENGTH);
				invalidate();
			}
		}
	};

	private void hiddenTask() {
		if (bestScore > 20) {
			scorePaint.setColor(Color.BLUE);
		}
	}
}
