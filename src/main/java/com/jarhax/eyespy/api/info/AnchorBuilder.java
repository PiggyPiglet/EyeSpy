package com.jarhax.eyespy.api.info;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.server.core.ui.Anchor;
import com.hypixel.hytale.server.core.ui.Value;

import java.util.Objects;

public class AnchorBuilder {

    public static final BuilderCodec<AnchorBuilder> CODEC = BuilderCodec.builder(AnchorBuilder.class, AnchorBuilder::new)
            .append(new KeyedCodec<>("Left", Codec.INTEGER), (p, t) -> p.left = t, p -> p.left)
            .add()
            .append(new KeyedCodec<>("Right", Codec.INTEGER), (p, t) -> p.right = t, p -> p.right)
            .add()
            .append(new KeyedCodec<>("Top", Codec.INTEGER), (p, t) -> p.top = t, p -> p.top)
            .add()
            .append(new KeyedCodec<>("Bottom", Codec.INTEGER), (p, t) -> p.bottom = t, p -> p.bottom)
            .add()
            .append(new KeyedCodec<>("Height", Codec.INTEGER), (p, t) -> p.height = t, p -> p.height)
            .add()
            .append(new KeyedCodec<>("Full", Codec.INTEGER), (p, t) -> p.full = t, p -> p.full)
            .add()
            .append(new KeyedCodec<>("Horizontal", Codec.INTEGER), (p, t) -> p.horizontal = t, p -> p.horizontal)
            .add()
            .append(new KeyedCodec<>("Vertical", Codec.INTEGER), (p, t) -> p.vertical = t, p -> p.vertical)
            .add()
            .append(new KeyedCodec<>("Width", Codec.INTEGER), (p, t) -> p.width = t, p -> p.width)
            .add()
            .append(new KeyedCodec<>("MinWidth", Codec.INTEGER), (p, t) -> p.minWidth = t, p -> p.minWidth)
            .add()
            .append(new KeyedCodec<>("MaxWidth", Codec.INTEGER), (p, t) -> p.maxWidth = t, p -> p.maxWidth)
            .add()
            .build();

    private Integer left = null;
    private Integer right = null;
    private Integer top = null;
    private Integer bottom = null;
    private Integer height = null;
    private Integer full = null;
    private Integer horizontal = null;
    private Integer vertical = null;
    private Integer width = null;
    private Integer minWidth = null;
    private Integer maxWidth = null;

    public AnchorBuilder() {
    }

    public void addHeight(int height) {
        this.height = this.height == null ? height : this.height + height;
    }

    public void ensureHeight(int height) {
        if (this.height == null || this.height < height) {
            this.height = height;
        }
    }

    public AnchorBuilder setLeft(Integer left) {
        this.left = left;
        return this;
    }

    public AnchorBuilder setRight(Integer right) {
        this.right = right;
        return this;
    }

    public AnchorBuilder setTop(Integer top) {
        this.top = top;
        return this;
    }

    public AnchorBuilder setBottom(Integer bottom) {
        this.bottom = bottom;
        return this;
    }

    public AnchorBuilder setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public AnchorBuilder setFull(Integer full) {
        this.full = full;
        return this;
    }

    public AnchorBuilder setHorizontal(Integer horizontal) {
        this.horizontal = horizontal;
        return this;
    }

    public AnchorBuilder setVertical(Integer vertical) {
        this.vertical = vertical;
        return this;
    }

    public AnchorBuilder setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public AnchorBuilder setMinWidth(Integer minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public AnchorBuilder setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Integer left() {
        return left;
    }

    public Integer right() {
        return right;
    }

    public Integer top() {
        return top;
    }

    public Integer bottom() {
        return bottom;
    }

    public Integer height() {
        return height;
    }

    public Integer full() {
        return full;
    }

    public Integer horizontal() {
        return horizontal;
    }

    public Integer vertical() {
        return vertical;
    }

    public Integer width() {
        return width;
    }

    public Integer minWidth() {
        return minWidth;
    }

    public Integer maxWidth() {
        return maxWidth;
    }

    public Anchor build() {
        Anchor anchor = new Anchor();
        if (this.left != null) {
            anchor.setLeft(Value.of(this.left));
        }
        if (this.right != null) {
            anchor.setRight(Value.of(this.right));
        }
        if (this.top != null) {
            anchor.setTop(Value.of(this.top));
        }
        if (this.bottom != null) {
            anchor.setBottom(Value.of(this.bottom));
        }
        if (this.height != null) {
            anchor.setHeight(Value.of(this.height));
        }
        if (this.full != null) {
            anchor.setFull(Value.of(this.full));
        }
        if (this.horizontal != null) {
            anchor.setHorizontal(Value.of(this.horizontal));
        }
        if (this.vertical != null) {
            anchor.setVertical(Value.of(this.vertical));
        }
        if (this.width != null) {
            anchor.setWidth(Value.of(this.width));
        }
        if (this.minWidth != null) {
            anchor.setMinWidth(Value.of(this.minWidth));
        }
        if (this.maxWidth != null) {
            anchor.setMaxWidth(Value.of(this.maxWidth));
        }
        return anchor;
    }

    public void clear() {
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
        this.height = null;
        this.full = null;
        this.horizontal = null;
        this.vertical = null;
        this.width = null;
        this.minWidth = null;
        this.maxWidth = null;
    }

    @Override
    public AnchorBuilder clone() {
        AnchorBuilder anchorBuilder = new AnchorBuilder();
        anchorBuilder.left = this.left;
        anchorBuilder.right = this.right;
        anchorBuilder.top = this.top;
        anchorBuilder.bottom = this.bottom;
        anchorBuilder.height = this.height;
        anchorBuilder.full = this.full;
        anchorBuilder.horizontal = this.horizontal;
        anchorBuilder.vertical = this.vertical;
        anchorBuilder.width = this.width;
        anchorBuilder.minWidth = this.minWidth;
        anchorBuilder.maxWidth = this.maxWidth;
        return anchorBuilder;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AnchorBuilder that)) return false;

        return Objects.equals(left, that.left) && Objects.equals(right, that.right) && Objects.equals(top, that.top) && Objects.equals(bottom, that.bottom) && Objects.equals(height, that.height) && Objects.equals(full, that.full) && Objects.equals(horizontal, that.horizontal) && Objects.equals(vertical, that.vertical) && Objects.equals(width, that.width) && Objects.equals(minWidth, that.minWidth) && Objects.equals(maxWidth, that.maxWidth);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(left);
        result = 31 * result + Objects.hashCode(right);
        result = 31 * result + Objects.hashCode(top);
        result = 31 * result + Objects.hashCode(bottom);
        result = 31 * result + Objects.hashCode(height);
        result = 31 * result + Objects.hashCode(full);
        result = 31 * result + Objects.hashCode(horizontal);
        result = 31 * result + Objects.hashCode(vertical);
        result = 31 * result + Objects.hashCode(width);
        result = 31 * result + Objects.hashCode(minWidth);
        result = 31 * result + Objects.hashCode(maxWidth);
        return result;
    }
}
