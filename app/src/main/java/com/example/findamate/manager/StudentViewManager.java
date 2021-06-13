package com.example.findamate.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.findamate.R;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.example.findamate.helper.Util;
import com.example.findamate.view.StudentView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StudentViewManager {
    public static final int NORMAL_WIDTH = 56;
    public static final int NORMAL_HEIGHT = 96;
    public static final int MINI_WIDTH = 45;
    public static final int MINI_HEIGHT = 77;

    public static View newView(Activity activity, Student student, boolean mini) {
        int width = mini ? MINI_WIDTH : NORMAL_WIDTH;
        int height = mini ? MINI_HEIGHT : NORMAL_HEIGHT;

        return newView(activity, student, width, height);
    }

    private static View newView(Activity activity, Student student, int width, int height) {
        LinearLayout linearLayout = new LinearLayout(activity);
        StudentView studentView = new StudentView(activity, student);
        WindowManager windowManager = activity.getWindowManager();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Util.dpToPx(windowManager, width), Util.dpToPx(windowManager, height));
        linearLayout.addView(studentView, layoutParams);
        studentView.setTag(student.getId());
        return studentView;
    }

    public static void startBounceAnimation(Context context, FrameLayout container) {
        Random random = new Random();

        for(int index = 0; index < container.getChildCount(); index++) {
            startAnimation(context, container.getChildAt(index), R.anim.bounce, 1000 + random.nextInt(100), true);
        }
    }

    public static void startWaveAnimation(Context context, FrameLayout container) {
        for(int index = 0; index < container.getChildCount(); index++) {
            startWaveAnimation(context, container, container.getChildAt(index));
        }
    }

    public static void startWaveAnimation(Context context, FrameLayout container, View view) {
        Random random = new Random();

        startAnimation(context, view,
                random.nextBoolean() ? R.anim.wave_horizontal : R.anim.wave_vertical,
                random.nextInt(1000), true);
    }

    public static void startMatchingAnimation(Context context, View view1, View view2, View versus, View happiness) {
        startAnimation(context, view1, R.anim.move_left, 500, false);
        startAnimation(context, view2, R.anim.move_right, 500, false);
        startAnimation(context, versus, R.anim.fade_in, 900, false);
        startAnimation(context, (View)versus.getParent(), R.anim.move_up, 1500, false);
        startAnimation(context, happiness, R.anim.move_up, 1500, false);
    }

    private static void startAnimation(Context context, View view, int animationId, long startOffset, boolean infinite) {
        Animation animation = AnimationUtils.loadAnimation(context, animationId);

        if(startOffset > 0) animation.setStartOffset(startOffset);

        if(infinite) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    animation.reset();
                    animation.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }

        view.startAnimation(animation);
    }

    public static void stopAnimation(View view) {
        view.clearAnimation();
    }

    public static List<Rect> randomPositions(Activity activity, FrameLayout container) {
        List<Rect> positions = new ArrayList<>();
        addButtonPosition(activity, positions);

        for(int index = 0; index < container.getChildCount(); index++) {
            View view = container.getChildAt(index);
            randomPosition(activity, container, view, positions, false);
        }

        return positions;
    }

    public static void randomPosition(Activity activity, FrameLayout container, View view, List<Rect> positions) {
        randomPosition(activity, container, view, positions, false);
    }

    private static void randomPosition(Activity activity, FrameLayout container, View targetView, List<Rect> positions, boolean mini) {
        Random random = new Random();
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)container.getLayoutParams();
        int left = 0;
        int top = 0;
        int width = Util.dpToPx(windowManager, mini ? MINI_WIDTH : NORMAL_WIDTH);
        int height = Util.dpToPx(windowManager, mini ? MINI_HEIGHT : NORMAL_HEIGHT);
        int containerWidth = metrics.widthPixels - width;
        int containerHeight = metrics.heightPixels - layoutParams.topMargin - layoutParams.bottomMargin - height;

        for(int count = 0; count < 1000; count++) {
            left = random.nextInt(containerWidth);
            top = random.nextInt(containerHeight);

            if(!isOverlapped(left, top, width, height, positions)) break;
        }

        positions.add(new Rect(left, top, left + width, top + height));

        targetView.setX(left);
        targetView.setY(top);
    }

    private static void addButtonPosition(Activity activity, List<Rect> positions) {
        View button = activity.findViewById(R.id.studentSettingButton);

        if(button != null) {
            WindowManager windowManager = activity.getWindowManager();
            int left = 0;
            int top = Util.dpToPx(windowManager, 100);
            int width = Util.dpToPx(windowManager, 50);
            int height = Util.dpToPx(windowManager, 50);
            positions.add(new Rect(left, top, left + width, top + height));
        }
    }

    private static boolean isOverlapped(int left, int top, int width, int height, List<Rect> positions) {
        for(int i = 0; i < positions.size(); i++) {
            if(isOverlapped(left, top, left + width, top + height, positions.get(i))) return true;
        }

        return false;
    }

    private static boolean isOverlapped(int left, int top, int right, int bottom, Rect position) {
        return left < position.right && right > position.left && top < position.bottom && bottom > position.top;
    }
}