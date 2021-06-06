package com.example.findamate.manager;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.findamate.domain.Student;
import com.example.findamate.helper.Util;
import com.example.findamate.view.StudentView;

public class StudentViewManager {
    public static View getView(Activity activity, Student student, boolean mini) {
        int width = mini ? 30 : 70;
        int height = mini ? 30 : 120;

        return getView(activity, student, width, height);
    }

    private static View getView(Activity activity, Student student, int width, int height) {
        LinearLayout linearLayout = new LinearLayout(activity);
        StudentView studentProfile = new StudentView(activity, student);
        WindowManager windowManager = activity.getWindowManager();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Util.dpToPx(windowManager, width), Util.dpToPx(windowManager, height));
        linearLayout.addView(studentProfile, layoutParams);

        return linearLayout;
    }
}
