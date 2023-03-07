package dev.echavez.inactividad.iu

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val sessionActive = MutableLiveData<Boolean>()
    lateinit var mHandler: Handler
    lateinit var mRunnable: Runnable
    var mTime: Long = 10000 // 10 seconds


    // start handler function
    fun startHandler(){
        mHandler.postDelayed(mRunnable, mTime)
    }

    // stop handler function
    fun stopHandler(){
        mHandler.removeCallbacks(mRunnable)
    }
}


