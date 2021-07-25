package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.ActiveUserData
import br.com.fomezero.joaofood.activities.LoginActivity
import br.com.fomezero.joaofood.loadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_merchant_perfil.accountExitButton
import kotlinx.android.synthetic.main.fragment_merchant_perfil.profileName
import kotlinx.android.synthetic.main.fragment_merchant_perfil.profilePicture


class MerchantProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_merchant_perfil, container, false)
    }

    override fun onStart() {
        super.onStart()
        accountExitButton.setOnClickListener {
            logout()
        }
        val merchantData = ActiveUserData.data
        profileName.text = merchantData?.getString("name")
        profilePicture.loadImage(merchantData?.getString("imageUrl"), CircularProgressDrawable(activity!!))
    }

    private fun logout() {
        ActiveUserData.signOut()
        val loginIntent = Intent(activity, LoginActivity::class.java)
        startActivity(loginIntent)
        activity?.finish()
    }
}