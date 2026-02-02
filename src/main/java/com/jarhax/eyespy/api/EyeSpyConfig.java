package com.jarhax.eyespy.api;

import com.jarhax.eyespy.api.hud.LayoutMode;
import com.jarhax.eyespy.api.info.AnchorBuilder;

public interface EyeSpyConfig {

    AnchorBuilder position();

    LayoutMode layoutMode();

    boolean visible();

    boolean showContainers();

    boolean showProcessingTimes();

    boolean showInBackground();
}
