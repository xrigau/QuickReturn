package com.felipecsl.quickreturn.library.widget;

import android.view.View;

import static com.felipecsl.quickreturn.library.widget.QuickReturnViewTranslater.QuickReturnState.OFFSCREEN;
import static com.felipecsl.quickreturn.library.widget.QuickReturnViewTranslater.QuickReturnState.ONSCREEN;
import static com.felipecsl.quickreturn.library.widget.QuickReturnViewTranslater.QuickReturnState.RETURNING;

public class QuickReturnViewTranslater {

    enum QuickReturnState {
        ONSCREEN, OFFSCREEN, RETURNING;
    }

    private QuickReturnState currentState = ONSCREEN;
    private int minRawY;
    private View quickReturnView;

    public QuickReturnViewTranslater(View targetView) {
        quickReturnView = targetView;
    }

    public void translateTo(int translationY) {
        quickReturnView.setTranslationY(translationY);
    }

    public int determineState(int rawY, int quickReturnHeight) {
        int translationY;

        switch (currentState) {
            case OFFSCREEN:
                if (rawY <= minRawY) {
                    minRawY = rawY;
                } else {
                    currentState = RETURNING;
                }

                translationY = rawY;
                break;

            case ONSCREEN:
                if (rawY < -quickReturnHeight) {
                    currentState = OFFSCREEN;
                    minRawY = rawY;
                }
                translationY = rawY;
                break;

            case RETURNING:
                translationY = (rawY - minRawY) - quickReturnHeight;
                if (translationY > 0) {
                    translationY = 0;
                    minRawY = rawY - quickReturnHeight;
                }

                if (rawY > 0) {
                    currentState = ONSCREEN;
                    translationY = rawY;
                }

                if (translationY < -quickReturnHeight) {
                    currentState = OFFSCREEN;
                    minRawY = rawY;
                }
                break;
            default:
                throw new IllegalStateException("State not handled properly: " + currentState.name());
        }

        return translationY;
    }

}
