package br.com.fomezero.centralfood.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fomezero.centralfood.databinding.MerchantSignUpFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MerchantSignUpFragment : Fragment() {

    private var _binding: MerchantSignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NGOSignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MerchantSignUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}