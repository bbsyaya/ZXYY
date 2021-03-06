/*
 * Copyright (c) 2016 Jacksgong(blog.dreamtobe.cn).
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
 */
package com.zhixinyisheng.user.view.blood;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import junit.framework.Assert;

import java.lang.ref.WeakReference;

/**
 * Created by Jacksgong on 2/2/16.
 * <p/>
 * handle the case of the internal of the percent between the current and the last is
 * too large to smooth for the target
 *处理这个案子的内部之间的百分比当前和过去的太大了,光滑的为目标
 * @see ISmoothTarget
 */
public class SmoothHandler extends Handler {
    final WeakReference<ISmoothTarget> targetWeakReference;

    private float aimPercent;
    private float minInternalPercent = 0.03f; // 3%
    private float smoothInternalPercent = 0.01f; // 1%
    private int smoothIncreaseDelayMillis = 1; // 1ms

    public float getMinInternalPercent() {
        return minInternalPercent;
    }

    /**
     * if the provider percent more than minInternalPercent, it will be split to the several smoothInternalPercent
     *如果提供者超过minInternalPercent百分比,它将被分割到几个smoothInternalPercent
     * @param minInternalPercent the min internal of the percent, default 0.03
     * @see #setSmoothInternalPercent(float)
     */
    public void setMinInternalPercent(float minInternalPercent) {
        Assert.assertTrue("the min internal percent must more than 0", minInternalPercent > 0);
        Assert.assertTrue("the min internal percent must less than 1", minInternalPercent <= 1);
        Assert.assertTrue("the min internal percent must more than the smooth internal percent",
                minInternalPercent > this.smoothInternalPercent);
        this.minInternalPercent = minInternalPercent;
    }

    public float getSmoothInternalPercent() {
        return smoothInternalPercent;
    }

    /**
     * if the provider percent more than minInternalPercent, it will be split to the several smoothInternalPercent
     *如果提供者超过minInternalPercent百分比,它将被分割到几个smoothInternalPercent
     * @param smoothInternalPercent the internal of the percent will provide the smooth effect, default 0.01
     * @see #setMinInternalPercent(float)
     */
    public void setSmoothInternalPercent(float smoothInternalPercent) {
        Assert.assertTrue("the smooth internal percent must more than 0", minInternalPercent > 0);
        Assert.assertTrue("the smooth internal percent must less than 0.5", minInternalPercent < 0.5);
        Assert.assertTrue("the smooth internal percent must less than the min internal percent",
                smoothInternalPercent < this.minInternalPercent);
        this.smoothInternalPercent = smoothInternalPercent;
    }

    public int getSmoothIncreaseDelayMillis() {
        return smoothIncreaseDelayMillis;
    }

    /**
     * @param smoothIncreaseDelayMillis the delay of increase duration, default 1ms
     */
    public void setSmoothIncreaseDelayMillis(int smoothIncreaseDelayMillis) {
        Assert.assertTrue("the delay of increase duration must more than 0", minInternalPercent > 0);
        this.smoothIncreaseDelayMillis = smoothIncreaseDelayMillis;
    }

    /**
     * generally use for the progress widget
     *通常使用的进步小部件
     * @param targetWeakReference the weak reference of the smooth target
     */
    public SmoothHandler(WeakReference<ISmoothTarget> targetWeakReference) {
        this(targetWeakReference, Looper.getMainLooper());
    }

    public SmoothHandler(WeakReference<ISmoothTarget> targetWeakReference, Looper looper) {
        super(looper);
        this.targetWeakReference = targetWeakReference;
        this.aimPercent = targetWeakReference.get().getPercent();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (this.targetWeakReference == null || this.targetWeakReference.get() == null) {
            return;
        }

        final ISmoothTarget target = targetWeakReference.get();

        setPercent2Target(Math.min(target.getPercent() + smoothInternalPercent, aimPercent));

        if (target.getPercent() >= this.aimPercent || target.getPercent() >= 1 ||
                (target.getPercent() == 0 && this.aimPercent == 0)) {
            clear();
            return;
        }

        sendEmptyMessageDelayed(0, smoothIncreaseDelayMillis);
    }

    private void clear() {
        this.ignoreCommit = false;
        removeMessages(0);
    }

    private boolean ignoreCommit = false;

    /**
     * Must be invoked by some method which will change the percent for monitor all changes
     * 必须调用一些方法将改变监视所有的百分比变化
     * about percent.
     *
     * @param percent the percent will be effect by the target.
     */
    public void commitPercent(float percent) {
        if (this.ignoreCommit) {
            this.ignoreCommit = false;
            return;
        }
        this.aimPercent = percent;
    }

    private void setPercent2Target(final float percent) {
        if (targetWeakReference == null || targetWeakReference.get() == null) {
            return;
        }

        this.ignoreCommit = true;
        targetWeakReference.get().setPercent(percent);
        this.ignoreCommit = false;
    }

    /**
     * if the provider percent(the aim percent) more than minInternalPercent, it will be split to the several smoothInternalPercent
     *如果提供者百分比(目标百分比)超过minInternalPercent,它将分成几个smoothInternalPercent
     * @param percent the aim percent
     */
    public void loopSmooth(float percent) {
        if (this.targetWeakReference == null || this.targetWeakReference.get() == null) {
            return;
        }

        final ISmoothTarget target = targetWeakReference.get();

        setPercent2Target(this.aimPercent);
        clear();

        this.aimPercent = percent;

        if (this.aimPercent - target.getPercent() > minInternalPercent) {
            sendEmptyMessage(0);
        } else {
            setPercent2Target(percent);
        }

    }
}