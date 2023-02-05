package com.pasich.mynotes.utils.transition;

import android.graphics.Color;
import android.transition.Transition;
import android.view.View;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.transition.platform.MaterialArcMotion;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.pasich.mynotes.R;

public class TransitionUtil {

    /**
     * Transition to activity Notes
     *
     * @param container - coordinationLyout
     */
    public static Transition buildContainerTransform(View container) {
        MaterialContainerTransform materialContainerTransform = new MaterialContainerTransform();
        materialContainerTransform.addTarget(container)
                .setDuration(500)
                .setInterpolator(new FastOutSlowInInterpolator());
        materialContainerTransform.setAllContainerColors(MaterialColors.getColor(container, R.attr.colorSurface));
        materialContainerTransform.setScrimColor(Color.TRANSPARENT);
        materialContainerTransform.setPathMotion(new MaterialArcMotion());
        materialContainerTransform.setFadeProgressThresholds(new MaterialContainerTransform.ProgressThresholds(0f, 1f));
        materialContainerTransform.setFadeMode(MaterialContainerTransform.FADE_MODE_THROUGH);

        return materialContainerTransform;
    }
}
