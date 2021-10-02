package br.com.fomezero.centralfood.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import br.com.fomezero.centralfood.R
import br.com.fomezero.centralfood.databinding.LoginFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.listener = viewModel

        binding.signUpButton.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(LoginFragmentDirections.welcomeFragment())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.invalidEmailOrPassword.observe(viewLifecycleOwner, { invalidEmailOrPassword() })
        viewModel.emptyField.observe(viewLifecycleOwner, { emptyField() })
        viewModel.unexpectedError.observe(viewLifecycleOwner, { unexpectedError() })
        viewModel.loginSuccessAction.observe(viewLifecycleOwner, { loginSuccessAction(it) })
        viewModel.loading.observe(viewLifecycleOwner, { loadingObserver(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadingObserver(value: Boolean) {
        if (value) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun loginSuccessAction(action: NavDirections) {
        Navigation.findNavController(requireView()).apply{
            navigate(action)
        }
    }

    private fun emptyField() {
        Toast.makeText(context, getString(R.string.empty_field_toast), Toast.LENGTH_LONG).show()
    }

    private fun invalidEmailOrPassword() {
        Toast.makeText(context, getString(R.string.invalid_login_toast), Toast.LENGTH_LONG).show()
    }

    private fun unexpectedError() {
        Toast.makeText(context, getString(R.string.unexpected_error_toast), Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}