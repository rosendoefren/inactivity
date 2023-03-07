package dev.echavez.inactividad.iu

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val sessionActive = MutableLiveData<Boolean>()
    val rTime = MutableLiveData<Long>()
    private var mHandler: Handler? = null
    private var mRunnable: Runnable? = null
    var mTime: Long = 10000 // 10 seconds


    // start handler function
    fun startHandler(){
        if(mHandler == null){
            mHandler = Handler(Looper.getMainLooper())
            mRunnable = Runnable {
                stopHandler()
                sessionActive.value = false
                mHandler = null
                mRunnable = null
            }
            mHandler?.postDelayed(mRunnable!!, mTime)
            rTime.value = SystemClock.elapsedRealtime() + mTime
        }
    }

    // stop handler function
    fun stopHandler(){
        mHandler!!.removeCallbacks(mRunnable!!)
    }

    fun updateTimer(){
        rTime.value = SystemClock.elapsedRealtime() + mTime
        mHandler?.postDelayed(mRunnable!!, mTime)
    }
}


