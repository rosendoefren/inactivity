package dev.echavez.inactividad.iu.second

import android.annotation.SuppressLint
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.echavez.inactividad.R
import dev.echavez.inactividad.databinding.FragmentSecondBinding
import dev.echavez.inactividad.iu.MainViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
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

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_MOVE) {
                // Removes the handler callbacks (if any)
                viewModel.stopHandler()

                // Runs the handler (for the specified time)
                // If any touch or motion is detected before
                // the specified time, this override function is again called
                start()
            }
            true
        }

        //Init
        viewModel.mHandler = Handler(Looper.getMainLooper())
        viewModel.mRunnable = Runnable {
            binding.viewTimer.stop()
            viewModel.sessionActive.value = false
        }
        start()
        observer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopHandler()
        _binding = null
    }

    private fun observer(){
        viewModel.sessionActive.observe(viewLifecycleOwner){
            if(!it){
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun start(){
        viewModel.startHandler()
        binding.viewTimer.isCountDown = true
        binding.viewTimer.base = SystemClock.elapsedRealtime() + viewModel.mTime
        binding.viewTimer.start()
    }
}