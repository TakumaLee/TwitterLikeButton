/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.kuma.library.twitterfavo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

/**
 * Display on/off states (ie: Favorite or Retweet action buttons) as an {@link ImageButton}.
 *
 *
 * By default the button will be toggled when clicked. This behaviour can be prevented by setting
 * the {@code twitter:toggleOnClick} attribute to false.
 *
 */
public class TwitterFavoImageButton extends ImageButton {
    private static final String TAG = TwitterFavoImageButton.class.getSimpleName();
    private static final int[] STATE_TOGGLED_ON = {R.attr.state_toggled_on};

    boolean isToggledOn;
    final boolean toggleOnClick;

    private OnLikeChangedListener onLikeChangedListener;

    public TwitterFavoImageButton(Context context) {
        this(context, null);
    }

    public TwitterFavoImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterFavoImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = null;
        try {
            a = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.TwitterFavoImageButton, defStyle, 0);

            toggleOnClick = a.getBoolean(R.styleable.TwitterFavoImageButton_toggleOnClick, false);

            setImageResource(R.drawable.tw__like_action);

            setNormalToggleOn(toggleOnClick);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
        setOnClickListener(clickListener);
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l == null ? clickListener : l);
    }

    public void setOnLikeChangedListener(OnLikeChangedListener onLikeChangedListener) {
        this.onLikeChangedListener = onLikeChangedListener;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (isToggledOn) {
            mergeDrawableStates(drawableState, STATE_TOGGLED_ON);
        }
        return drawableState;
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public void initToggleOn() {

    }

    @Deprecated
    public void setToggledOn(boolean isToggledOn) {
        setAnimationToggledOn(isToggledOn);
    }

    public void setNormalToggleOn(boolean isToggledOn) {
        this.isToggledOn = isToggledOn;
        if (isToggledOn) {
            setImageResource(R.drawable.tw__action_heart_on_default);
        } else {
            setImageResource(R.drawable.tw__action_heart_off_default);
        }
    }

    public void setAnimationToggledOn(boolean isToggledOn) {
        this.isToggledOn = isToggledOn;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setImageResource(R.drawable.tw__like_action);
            refreshDrawableState();
        } else {
            if (isToggledOn) {
                setImageResource(R.drawable.animation_list_favo);
                AnimationDrawable favoAnimation = (AnimationDrawable) getDrawable();
                favoAnimation.start();
            } else {
                setImageResource(R.drawable.tw__like_action);
            }
        }
        if (this.isToggledOn) {
            onLikeChangedListener.onLike();
        } else {
            onLikeChangedListener.onDislike();
        }
    }

    public void toggle() {
        setToggledOn(!isToggledOn);
    }

    public boolean isToggledOn() {
        return isToggledOn;
    }
}
