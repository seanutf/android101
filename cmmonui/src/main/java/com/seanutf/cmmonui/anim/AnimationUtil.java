package com.seanutf.cmmonui.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Auther: sean on 2019/2/1  14:31
 */


public class AnimationUtil {
    private AnimatorSet mDoubleLikeAnimatorSet, mSingleLikeAnimatorSet;
    private View currentView;

    private AnimationUtil() {
    }

    private static class InstancesImpl {
        private static final AnimationUtil mAnimoter = new AnimationUtil();
    }

    public static AnimationUtil getInstances() {
        return InstancesImpl.mAnimoter;
    }


    public void showDoubleLikeAnimetor(final View mView) {
        if (currentView == mView) {
            if (mDoubleLikeAnimatorSet == null) {
                mDoubleLikeAnimatorSet = new AnimatorSet();
            }
        } else {
            currentView = mView;
            mDoubleLikeAnimatorSet = new AnimatorSet();
        }

        if (mDoubleLikeAnimatorSet.isStarted()) {
            mDoubleLikeAnimatorSet.cancel();
            mDoubleLikeAnimatorSet.start();
            return;
        }

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mView, "scaleX", 0f, 1.5f);
        scaleX1.setDuration(200);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mView, "scaleY", 0f, 1.5f);
        scaleY.setDuration(200);
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mView, "alpha", 0f, 1f);
        alpha1.setDuration(200);

        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(mView, "scaleX", 1.5f, 1.4f);
        scaleX2.setDuration(100);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(mView, "scaleY", 1.5f, 1.4f);
        scaleY2.setDuration(100);


        ObjectAnimator alpha3 = ObjectAnimator.ofFloat(mView, "alpha", 1f, 0f);
        alpha3.setDuration(200);


        mDoubleLikeAnimatorSet.play(scaleX1).with(scaleY).with(alpha1);
        mDoubleLikeAnimatorSet.play(scaleX2).with(scaleY2).after(scaleX1);
        mDoubleLikeAnimatorSet.play(alpha3).after(scaleX2).after(1000);

        mDoubleLikeAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mDoubleLikeAnimatorSet.start();
    }


    public void showSingleLikeAnimetor(final View view) {
        if (mSingleLikeAnimatorSet == null) {
            mSingleLikeAnimatorSet = new AnimatorSet();
        }

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.8f);
        ObjectAnimator fade = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);

        mSingleLikeAnimatorSet.play(scaleY).with(scaleX).with(fade);
        mSingleLikeAnimatorSet.setDuration(400);

        mSingleLikeAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mSingleLikeAnimatorSet.start();
    }
}
