/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.igniselit.bottomsheetbuilder.adapter;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;


class BottomSheetMenuItem implements BottomSheetItem {

    @ColorInt
    private int mTextColor;

    @ColorInt
    private int mTintColor;

    private Drawable mIcon;
    private String mTitle;
    private int mId;
    private MenuItem mMenuItem;

    @DrawableRes
    private int mBackground;

    public BottomSheetMenuItem(MenuItem item, @ColorInt int textColor, @DrawableRes int background,
                               @ColorInt int tintColor) {
        mMenuItem = item;
        mIcon = item.getIcon();
        mId = item.getItemId();
        mTitle = item.getTitle().toString();
        mTextColor = textColor;
        mBackground = background;
        mTintColor = tintColor;

        // Check if we have a tint to be applied to an icon
        if (mTintColor != -1 && mIcon != null) {
            mIcon = DrawableCompat.wrap(mIcon);
            DrawableCompat.setTint(mIcon, mTintColor);
        }
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public MenuItem getMenuItem() {
        return mMenuItem;
    }

    @DrawableRes
    public int getBackground() {
        return mBackground;
    }

    public int getId() {
        return mId;
    }

    @ColorInt
    public int getTextColor() {
        return mTextColor;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }
}
