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
import kotlinx.android.synthetic.main.fragment_ong_info.callButton
import kotlinx.android.synthetic.main.fragment_ong_info.description
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
            description.text = "Descrição $it"
        }

        phoneNumber.text = ongData.phoneNumber

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
                    val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.CALL_PHONE)
                    //Asking request Permissions
                    ActivityCompat.requestPermissions(activity!!, PERMISSIONS_STORAGE, 9)
                }
            }

        }

    }

    private fun phoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ongData.phoneNumber))
        startActivity(intent)
    }
}