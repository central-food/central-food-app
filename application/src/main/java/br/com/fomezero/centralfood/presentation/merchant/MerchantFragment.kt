package br.com.fomezero.centralfood.presentation.merchant

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.fomezero.centralfood.R

class MerchantFragment : Fragment() {

    private lateinit var viewModel: MerchantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.merchant_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MerchantViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = MerchantFragment()
    }
}