package dev.echavez.inactividad.iu.second

import android.annotation.SuppressLint
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.HandlerCompat.hasCallbacks
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.echavez.inactividad.R
import dev.echavez.inactividad.databinding.FragmentSecondBinding
import dev.echavez.inactividad.iu.MainViewModel
import kotlinx.coroutines.android.asCoroutineDispatcher

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("ClickableViewAccessibility")
class SecondFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateChronometer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        observer()
        listener()
        viewModel.startHandler()
    }

    private fun observer(){
        viewModel.sessionActive.observe(viewLifecycleOwner){
            if(!it) findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        viewModel.rTime.observe(viewLifecycleOwner){
            if(it != null) updateChronometer()
        }
    }

    private fun listener(){
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                // Removes the handler callbacks (if any)
                viewModel.stopHandler()
                // Runs the handler (for the specified time)
                // If any touch or motion is detected before
                // the specified time, this override function is again called
                viewModel.startHandler()
                viewModel.updateTimer()
            }
            true
        }
    }

    private fun updateChronometer(){
        if(viewModel.rTime.value!! > SystemClock.elapsedRealtime()){
            binding.viewTimer.isCountDown = true
            binding.viewTimer.base = SystemClock.elapsedRealtime() + (viewModel.rTime.value!! - SystemClock.elapsedRealtime())
            binding.viewTimer.start()
        }
    }





    /*@RequiresApi(Build.VERSION_CODES.N)
    private fun start(){
        viewModel.startHandler()
        binding.viewTimer.isCountDown = true
        binding.viewTimer.base = SystemClock.elapsedRealtime() + viewModel.mTime
        binding.viewTimer.start()
    }*/
}