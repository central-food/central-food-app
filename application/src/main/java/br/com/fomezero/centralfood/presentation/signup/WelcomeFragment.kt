package br.com.fomezero.centralfood.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import br.com.fomezero.centralfood.databinding.WelcomeFragmentBinding

class WelcomeFragment : Fragment() {

    private var _binding: WelcomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WelcomeFragmentBinding.inflate(inflater, container, false)

        binding.merchantButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(WelcomeFragmentDirections.merchantSignUpFragment())
        }

        binding.ngoButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(WelcomeFragmentDirections.ngoSignUpFragment())
        }

        return binding.root
    }

}