package com.rius.coinunion.injector

import android.app.Activity
import android.app.Application
import android.os.Bundle

abstract class ActivityCreatedAdapter : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
    }
}