package br.com.fomezero.joaofood.activities.ong

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.loadImage
import br.com.fomezero.joaofood.model.Product
import kotlinx.android.synthetic.main.fragment_merchant_info.phoneNumber
import kotlinx.android.synthetic.main.fragment_merchant_info.profileName
import kotlinx.android.synthetic.main.fragment_merchant_info.profilePicture

class MerchantInfoFragment(private val product: Product) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_merchant_info, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: $product")
        profileName.text = product.merchantData.name
        phoneNumber.text = "Celular: ${product.merchantData.phoneNumber}"
        activity?.let {
            profilePicture.loadImage(product.merchantData.imageUrl, CircularProgressDrawable(it))
        }
    }

    companion object {
        const val TAG = "MerchantInfoFragment"
    }
}