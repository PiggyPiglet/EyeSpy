package com.jarhax.eyespy.api;

import com.jarhax.eyespy.api.ui.Anchor;
import com.jarhax.eyespy.api.ui.LayoutMode;

public interface EyeSpyConfig {

    Anchor position();

    LayoutMode layoutMode();

    boolean visible();

    boolean showContainers();

    boolean showProcessingTimes();

    boolean showInBackground();
}
