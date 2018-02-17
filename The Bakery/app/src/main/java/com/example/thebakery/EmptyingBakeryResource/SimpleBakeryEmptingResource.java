package com.example.thebakery.EmptyingBakeryResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleBakeryEmptingResource implements IdlingResource {

    private AtomicBoolean is_it_idle = new AtomicBoolean(true);
    @Nullable
    private volatile ResourceCallback myCallback;

    public void setIdleState(boolean isIdleNow) {
        is_it_idle.set(isIdleNow);
        if (isIdleNow && myCallback != null) {
            myCallback.onTransitionToIdle();
        }
    }

    @Override
    public boolean isIdleNow() {
        return is_it_idle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        myCallback = callback;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

}