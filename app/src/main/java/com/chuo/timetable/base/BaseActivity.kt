package com.chuo.timetable.base


import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.chuo.timetable.R
import com.chuo.timetable.di.Injector.clearComponent
import com.chuo.timetable.di.Injector.inject
import com.chuo.timetable.di.ScreenInjector
import com.chuo.timetable.lifecycle.ActivityLifecycleTask
import com.chuo.timetable.ui.ScreenNavigator
import com.chuo.timetable.ui.ScreenProvider
import java.util.*
import javax.inject.Inject


abstract class BaseActivity: AppCompatActivity(), ScreenProvider {
    @Inject lateinit var screenInjector: ScreenInjector
    @Inject lateinit var screenNavigator: ScreenNavigator
    @Inject lateinit var activityLifecycleTasks: Set<@JvmSuppressWildcards ActivityLifecycleTask>
    private var instanceId: String? = null
    private val INSTANCE_ID_KEY = "instance_id"


    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY)
        } else {
            instanceId = UUID.randomUUID().toString();
        }
       inject(this)
        super.onCreate(savedInstanceState)

        setContentView(layoutRes())

//        activityViewInterceptor.setContentView(this, layoutRes());
     val screenContainer = findViewById<ViewGroup>(R.id.screen_container)
            ?: throw NullPointerException("Activity must have a view with id: screen_container")

        for (task in activityLifecycleTasks) {
            task.onCreate(this)
        }

    }




    override fun onStart() {
        super.onStart()
        for (task in activityLifecycleTasks) {
            task.onStart(this)
        }
    }

    override fun onResume() {
        super.onResume()
        for (task in activityLifecycleTasks) {
            task.onResume(this)
        }
    }

    override fun onPause() {
        super.onPause()
        for (task in activityLifecycleTasks) {
            task.onPause(this)
        }
    }

    override fun onStop() {
        super.onStop()
        for (task in activityLifecycleTasks) {
            task.onStop(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_ID_KEY, instanceId)
    }
    open fun getInstanceId(): String? {
        return instanceId
    }

    override fun onBackPressed() {
        if (!screenNavigator.pop()) {
            super.onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            clearComponent(this)
        }
//        activityViewInterceptor.clear()
        for (task in activityLifecycleTasks) {
            task.onDestroy(this)
        }
    }
    open fun getInjector(): ScreenInjector? {
        return screenInjector
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

}
