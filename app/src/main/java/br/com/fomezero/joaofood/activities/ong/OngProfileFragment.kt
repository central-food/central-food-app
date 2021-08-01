package br.com.fomezero.joaofood.activities.ong

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
import br.com.fomezero.joaofood.util.loadImage
import kotlinx.android.synthetic.main.fragment_ong_profile.accountExitButton
import kotlinx.android.synthetic.main.fragment_ong_profile.profileName
import kotlinx.android.synthetic.main.fragment_ong_profile.profilePicture


class OngProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ong_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        accountExitButton.setOnClickListener {
            ActiveUserData.signOut()
            val loginIntent = Intent(activity, LoginActivity::class.java)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(loginIntent)
            activity?.finish()
        }

        profileName.text = ActiveUserData.data?.getString("name") ?: "João"

        ActiveUserData.data?.getString("imageUrl")?.let { imageUrl ->
            context?.let{
                profilePicture.loadImage(imageUrl,  CircularProgressDrawable(context!!))
            }
        }

    }


}