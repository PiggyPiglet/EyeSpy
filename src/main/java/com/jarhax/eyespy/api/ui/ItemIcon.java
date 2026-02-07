package com.jarhax.eyespy.api.ui;

import com.jarhax.eyespy.api.util.Identifier;

public class ItemIcon extends UIElement {

    private final String itemId;
    private final int width;
    private final int height;

    public ItemIcon(Identifier id, String itemId) {
        this(id, 64, 64, itemId);
    }

    public ItemIcon(Identifier id, int size, String itemId) {
        this(id, size, size, itemId);
    }

    public ItemIcon(Identifier id, int width, int height, String itemId) {
        super(id);
        this.width = width;
        this.height = height;
        this.itemId = itemId;
    }

    @Override
    public String build() {
        return """
                ItemIcon #%s {
                    Anchor: (Width: %d, Height: %d);
                    ItemId: "%s";
                }
                """.formatted(this.id.ui(), this.width, this.height, this.itemId);
    }

    @Override
    public int height() {
        return this.height;
    }
}
