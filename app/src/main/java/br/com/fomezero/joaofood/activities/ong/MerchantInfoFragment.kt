package br.com.fomezero.joaofood.activities.ong

import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.fragment_merchant_info.*

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
        phoneNumber.text = getString(R.string.phone_number, product.merchantData.phoneNumber)



        // TODO: get address from database
        address.text = getString(R.string.address_text, PLACEHOLDER_ADDRESS)

        mapsButton.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=" + Uri.encode(PLACEHOLDER_ADDRESS))

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        activity?.let {
            profilePicture.loadImage(product.merchantData.imageUrl, CircularProgressDrawable(it))
        }
    }

    companion object {
        const val TAG = "MerchantInfoFragment"
        const val PLACEHOLDER_ADDRESS = "177A Bleecker Street, New York City, NY 10012-1406"
    }
}