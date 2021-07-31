package br.com.fomezero.joaofood.activities.merchant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.loadImage
import br.com.fomezero.joaofood.model.OngData
import kotlinx.android.synthetic.main.fragment_ong_info.address
import kotlinx.android.synthetic.main.fragment_ong_info.callButton
import kotlinx.android.synthetic.main.fragment_ong_info.description
import kotlinx.android.synthetic.main.fragment_ong_info.mapsButton
import kotlinx.android.synthetic.main.fragment_ong_info.peopleHelped
import kotlinx.android.synthetic.main.fragment_ong_info.phoneNumber
import kotlinx.android.synthetic.main.fragment_ong_info.profileName
import kotlinx.android.synthetic.main.fragment_ong_info.profilePicture


class OngInfoFragment(val ongData: OngData) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ong_info, container, false)
    }

    override fun onStart() {
        super.onStart()

        profileName.text = ongData.name
        profilePicture.loadImage(ongData.imageUrl, CircularProgressDrawable(activity!!))
        ongData.description?.let {
            description.text = getString(R.string.about_text, it)
        }

        // TODO: get address from database
        address.text = getString(R.string.address_text, PLACEHOLDER_ADDRESS)

        phoneNumber.text = getString(R.string.phone_number, ongData.phoneNumber)

        // TODO: get helped number from database
        peopleHelped.text = getString(R.string.people_helped, 75000)

        mapsButton.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=" + Uri.encode(PLACEHOLDER_ADDRESS))

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        callButton.setOnClickListener {
            if (Build.VERSION.SDK_INT < 23) {
                phoneCall()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.CALL_PHONE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    phoneCall()
                } else {
                    val permissionStorage = arrayOf<String>(Manifest.permission.CALL_PHONE)
                    //Asking request Permissions
                    ActivityCompat.requestPermissions(activity!!, permissionStorage, 9)
                }
            }

        }

    }

    private fun phoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ongData.phoneNumber))
        startActivity(intent)
    }

    companion object {
        const val PLACEHOLDER_ADDRESS = "177A Bleecker Street, New York City, NY 10012-1406"
    }
}