package com.rkya.weather.utils.loader;

import androidx.annotation.NonNull;

public enum LoaderId {
    FORECAST(0);

    private final int value;

    LoaderId(int value) {
        this.value = value;
    }

    @NonNull
    public static LoaderId getViewType(int value) {
        for (LoaderId viewType : LoaderId.values()) {
            if (viewType.value == value) {
                return viewType;
            }
        }

        throw new IllegalArgumentException("Invalid loader type, value = " + value);
    }

    public int getValue() {
        return value;
    }
}
