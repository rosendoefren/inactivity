package dev.echavez.inactividad.iu.first

import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dev.echavez.inactividad.R
import dev.echavez.inactividad.databinding.FragmentFirstBinding
import dev.echavez.inactividad.iu.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentFirstBinding? = null

    //private val timer = Timer()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        listener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observer(){
        viewModel.sessionActive.observe(viewLifecycleOwner){
            if(!it){
                Snackbar.make(binding.buttonFirst,
                        "Inactive for ${viewModel.mTime/1000} seconds!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun listener(){
        binding.buttonFirst.setOnClickListener {
            viewModel.sessionActive.value = true
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}