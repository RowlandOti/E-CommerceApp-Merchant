package com.meetic.shuffle;

import android.view.View;

import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.dragueur.ExitViewAnimator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;

public class ShuffleViewAnimator extends ExitViewAnimator<CardDraggableView> {

    @Nullable
    protected Shuffle shuffle;

    boolean pushTopAnimateViewStackScaleUp = true;
    boolean pushBottomAnimateViewStackScaleUp = true;
    boolean pushLeftAnimateViewStackScaleUp = true;
    boolean pushRightAnimateViewStackScaleUp = false;

    public ShuffleViewAnimator() {
    }

    public void setShuffle(@NonNull Shuffle shuffle) {
        this.shuffle = shuffle;
    }

    @Override
    public boolean animateToOrigin(@NonNull CardDraggableView draggableView, int duration) {
        boolean animating = super.animateToOrigin(draggableView, duration);
        if (animating) {
            ViewCompat.animate(draggableView.getOverlayView()).withLayer().alpha(0f).setDuration(duration);
        }
        return animating;
    }

    public boolean animateViewStackFrom(@NonNull Listener listener, Direction direction) {
        if (shuffle != null) {
            if (shuffle.getShuffleSettings().isVertical()) {
                switch (direction) {
                    case TOP:
                        if (pushTopAnimateViewStackScaleUp) {
                            return animateViewStackScaleUp(direction, listener);
                        } else {
                            return animateViewStackGoBackBehind(direction, listener);
                        }
                    case BOTTOM:
                        if (pushBottomAnimateViewStackScaleUp) {
                            return animateViewStackScaleUp(direction, listener);
                        } else {
                            return animateViewStackGoBackBehind(direction, listener);
                        }
                }
            } else {
                switch (direction) {
                    case LEFT:
                        if (pushLeftAnimateViewStackScaleUp) {
                            return animateViewStackScaleUp(direction, listener);
                        } else {
                            return animateViewStackGoBackBehind(direction, listener);
                        }
                    case RIGHT:
                        if (pushRightAnimateViewStackScaleUp) {
                            return animateViewStackScaleUp(direction, listener);
                        } else {
                            return animateViewStackGoBackBehind(direction, listener);
                        }
                }
            }
        }
        return false;
    }

    public boolean animateRestartShuffling(@NonNull final RestartListener listener) {
        ViewCompat.animate(shuffle)
                .alpha(0f)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                        listener.animationStarted();
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        listener.animationMiddle();

                        ViewCompat.animate(shuffle)
                                .alpha(1f)
                                .withLayer()
                                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(View view) {
                                        super.onAnimationEnd(view);
                                        listener.animationEnd();
                                    }
                                });
                    }
                });

        return false;
    }

    public boolean animateRevert(int duration, @NonNull final RestartListener listener) {
        Direction lastExitDirection = getLastExitDirection();
        if (lastExitDirection == null) {
            return false;
        }
        final int position = 0;
        final DraggableView firstCard = shuffle.getFirstDraggableView();
        if (!firstCard.isAnimating()) {
            final ShuffleSettings shuffleSettings = shuffle.getShuffleSettings();

            { //reset
                float scale = shuffleSettings.getScaleForPosition(position);
                ViewCompat.setScaleX(firstCard, scale);
                ViewCompat.setScaleY(firstCard, scale);
                firstCard.reset();
                ViewCompat.setRotation(firstCard, 0);
            }

            {
                float translationX = 0f;
                float translationY = 0f;
                switch (getLastExitDirection()) {
                    case RIGHT:
                        translationX = firstCard.getParentWidth();
                        translationY = shuffleSettings.getTranslationYForPosition(position);
                        break;
                    case LEFT:
                        translationY = shuffleSettings.getTranslationYForPosition(position);
                        translationX = -firstCard.getParentWidth();
                        break;
                    case BOTTOM:
                        translationY = firstCard.getParentHeight() * 2;
                        translationX = 0f;
                        break;
                    case TOP:
                        translationY = -firstCard.getParentHeight() * 2;
                        translationX = 0f;
                        break;
                }

                if (shuffleSettings.isStackFromTop()) {
                    translationY *= -1;
                }

                ViewCompat.setTranslationY(firstCard, translationY);
                ViewCompat.setTranslationX(firstCard, translationX);
            }

            ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(firstCard)
                    .withLayer()
                    .setDuration(duration)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
                            super.onAnimationStart(view);
                            listener.animationStarted();
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            listener.animationEnd();
                            firstCard.setAnimating(false);
                        }
                    })
                    .setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(View view) {
                            notifyDraggableViewUpdated((CardDraggableView) firstCard);
                        }
                    });

            if (lastExitDirection == Direction.TOP || lastExitDirection == Direction.BOTTOM) {
                animatorCompat.translationY(0f);
            } else {
                animatorCompat.translationX(0f);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean animateViewStackScaleUp(@NonNull Direction direction, @NonNull final Listener listener) {
        if (shuffle != null) {

            final DraggableView lastCard = shuffle.getLastDraggableView();
            final ShuffleSettings shuffleSettings = shuffle.getShuffleSettings();

            { //reset
                ViewCompat.setRotation(lastCard, 0);
                ViewCompat.setTranslationX(lastCard, 0);
                lastCard.reset();
            }

            int numberOfCards = shuffleSettings.getNumberOfDisplayedCards();
            int position = numberOfCards - 1;
            float scale = shuffleSettings.getScaleForPosition(position);

            float translationY = shuffleSettings.getTranslationYForPosition(position);
            if (shuffleSettings.isStackFromTop()) {
                translationY *= -1;
            }

            ViewCompat.setScaleX(lastCard, 0.5f);
            ViewCompat.setScaleY(lastCard, 0.5f);
            ViewCompat.setTranslationY(lastCard, 0);

            float translationX = shuffleSettings.getTranslationXForPosition(position);
            ViewCompat.setTranslationX(lastCard, translationX);

            ViewCompat.animate(lastCard)
                    .withLayer()
                    .scaleX(scale)
                    .scaleY(scale)
                    .translationY(translationY)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(View view) {
                            listener.animationEnd();
                        }
                    })
                    .setStartDelay(100)
                    .setDuration(shuffleSettings.getAnimationReturnCardDuration());

            return true;
        } else {
            return false;
        }
    }

    public boolean animateViewStackGoBackBehind(@NonNull Direction direction, @NonNull final Listener listener) {
        if (shuffle != null) {
            final DraggableView lastCard = shuffle.getLastDraggableView();
            final ShuffleSettings shuffleSettings = shuffle.getShuffleSettings();

            final int numberOfCards = shuffleSettings.getNumberOfDisplayedCards();
            final int position = numberOfCards - 1;

            { //reset
                float scale = shuffleSettings.getScaleForPosition(position);
                ViewCompat.setScaleX(lastCard, scale);
                ViewCompat.setScaleY(lastCard, scale);
                lastCard.reset();
                ViewCompat.setRotation(lastCard, 0);
            }

            {
                float translationX = 0f;
                float translationY = 0f;
                switch (direction) {
                    case RIGHT:
                        translationX = lastCard.getParentWidth();
                        translationY = shuffleSettings.getTranslationYForPosition(position);
                        break;
                    case LEFT:
                        translationY = shuffleSettings.getTranslationYForPosition(position);
                        translationX = -lastCard.getParentWidth();
                        break;
                    case BOTTOM:
                        translationY = lastCard.getParentHeight() * 2;
                        translationX = 0f;
                        break;
                    case TOP:
                        translationY = -lastCard.getParentHeight() * 2;
                        translationX = 0f;
                        break;
                }

                if (shuffleSettings.isStackFromTop()) {
                    translationY *= -1;
                }

                listener.animationStarted();

                ViewCompat.setTranslationY(lastCard, translationY);
                ViewCompat.setTranslationX(lastCard, translationX);
            }

            ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(lastCard)
                    .withLayer()
                    .setDuration(shuffleSettings.getAnimationReturnCardDuration())
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(View view) {
                            listener.animationEnd();
                        }
                    });

            if (direction == Direction.TOP || direction == Direction.BOTTOM) {
                animatorCompat.translationY(0f);
            } else {
                animatorCompat.translationX(0f);
            }

            return true;
        } else {
            return false;
        }

    }

    public void updateViewsPositions(float percentX, float percentY) {
        if (shuffle != null) {
            final ShuffleSettings shuffleSettings = shuffle.getShuffleSettings();
            float percent = shuffleSettings.isVertical() ? percentY : percentX;
            int numberOfCards = shuffleSettings.getNumberOfDisplayedCards();
            for (int i = 1; i < numberOfCards; ++i) {
                DraggableView view = shuffle.getDraggableView(i);

                float myScale = shuffleSettings.getScaleForPosition(i);
                float nextScale = shuffleSettings.getScaleForPosition(i - 1);
                float percentAbs = myScale + (nextScale - myScale) * Math.abs(percent);
                ViewCompat.setScaleX(view, percentAbs);
                ViewCompat.setScaleY(view, percentAbs);

                float myTranslationY = shuffleSettings.getTranslationYForPosition(i);
                if (shuffleSettings.isStackFromTop()) {
                    myTranslationY *= -1;
                }

                float nextTranslationY = shuffleSettings.getTranslationYForPosition(i - 1);
                if (shuffleSettings.isStackFromTop()) {
                    nextTranslationY *= -1;
                }

                float translationY = myTranslationY - Math.abs(percent) * (myTranslationY - nextTranslationY);
                ViewCompat.setTranslationY(view, translationY);

                float myTranslationX = shuffleSettings.getTranslationXForPosition(i);
                float nextTranslationX = shuffleSettings.getTranslationXForPosition(i - 1);
                float translationX = myTranslationX - Math.abs(percent) * (myTranslationX - nextTranslationX);
                ViewCompat.setTranslationX(view, translationX);
            }
        }
    }

    @Override
    public void update(CardDraggableView draggableView, float percentX, float percentY) {
        super.update(draggableView, percentX, percentY);

        if (percentX < 0) {
            if (draggableView.getOldPercentX() >= 0) { //percent changed sign
                draggableView.getOverlayView().setBackgroundColor(draggableView.getColorLeft());
                draggableView.setOverlayLeftAlpha(1f);
                draggableView.setOverlayRightAlpha(0f);
            }
        } else {
            if (draggableView.getOldPercentX() <= 0) {
                //percent changed sign
                draggableView.getOverlayView().setBackgroundColor(draggableView.getColorRight());
                draggableView.setOverlayLeftAlpha(0f);
                draggableView.setOverlayRightAlpha(1f);
            }
        }

        ViewCompat.setAlpha(draggableView.getOverlayView(), Math.abs(percentX));
    }

    public boolean isPushTopAnimateViewStackScaleUp() {
        return pushTopAnimateViewStackScaleUp;
    }

    public ShuffleViewAnimator setPushTopAnimateViewStackScaleUp(boolean pushTopAnimateViewStackScaleUp) {
        this.pushTopAnimateViewStackScaleUp = pushTopAnimateViewStackScaleUp;
        return this;
    }

    public boolean isPushBottomAnimateViewStackScaleUp() {
        return pushBottomAnimateViewStackScaleUp;
    }

    public ShuffleViewAnimator setPushBottomAnimateViewStackScaleUp(boolean pushBottomAnimateViewStackScaleUp) {
        this.pushBottomAnimateViewStackScaleUp = pushBottomAnimateViewStackScaleUp;
        return this;
    }

    public boolean isPushLeftAnimateViewStackScaleUp() {
        return pushLeftAnimateViewStackScaleUp;
    }

    public ShuffleViewAnimator setPushLeftAnimateViewStackScaleUp(boolean pushLeftAnimateViewStackScaleUp) {
        this.pushLeftAnimateViewStackScaleUp = pushLeftAnimateViewStackScaleUp;
        return this;
    }

    public boolean isPushRightAnimateViewStackScaleUp() {
        return pushRightAnimateViewStackScaleUp;
    }

    public ShuffleViewAnimator setPushRightAnimateViewStackScaleUp(boolean pushRightAnimateViewStackScaleUp) {
        this.pushRightAnimateViewStackScaleUp = pushRightAnimateViewStackScaleUp;
        return this;
    }

    interface RestartListener {
        void animationStarted();

        void animationMiddle();

        void animationEnd();
    }
}
