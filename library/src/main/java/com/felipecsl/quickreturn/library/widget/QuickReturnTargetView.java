package com.felipecsl.quickreturn.library.widget;

import android.view.View;

public class QuickReturnTargetView {

    protected static final int STATE_ONSCREEN = 0;
    protected static final int STATE_OFFSCREEN = 1;
    protected static final int STATE_RETURNING = 2;

    protected int currentState = STATE_ONSCREEN;
    protected int minRawY;
    protected View quickReturnView;

    public QuickReturnTargetView(final View targetView) {
        quickReturnView = targetView;
    }

    protected void translateTo(final int translationY) {
        quickReturnView.setTranslationY(translationY);
    }

    public int determineState(int rawY, int quickReturnHeight) {
        int translationY = 0;

        switch (currentState) {
            case STATE_OFFSCREEN:
                if (rawY <= minRawY) {
                    minRawY = rawY;
                } else {
                    currentState = STATE_RETURNING;
                }

                translationY = rawY;
                break;

            case STATE_ONSCREEN:
                if (rawY < -quickReturnHeight) {
                    currentState = STATE_OFFSCREEN;
                    minRawY = rawY;
                }
                translationY = rawY;
                break;

            case STATE_RETURNING:
                translationY = (rawY - minRawY) - quickReturnHeight;
                if (translationY > 0) {
                    translationY = 0;
                    minRawY = rawY - quickReturnHeight;
                }

                if (rawY > 0) {
                    currentState = STATE_ONSCREEN;
                    translationY = rawY;
                }

                if (translationY < -quickReturnHeight) {
                    currentState = STATE_OFFSCREEN;
                    minRawY = rawY;
                }
                break;
        }
        return translationY;
    }

}
