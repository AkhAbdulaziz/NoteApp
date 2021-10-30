package uz.xsoft.noteapp.ui.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.xsoft.noteapp.R
import uz.xsoft.noteapp.databinding.ScreenSplashBinding

class SplashScreen : Fragment() {
    private var _binding : ScreenSplashBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ScreenSplashBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            findNavController().navigate(R.id.action_splashScreen_to_mainScreen)
        },2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}