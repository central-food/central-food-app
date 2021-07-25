package br.com.fomezero.joaofood.activities.ong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fomezero.joaofood.R
import kotlinx.android.synthetic.main.fragment_notification.notificationRecycleView


class NotificationFragment : Fragment() {
    private var notificationList = arrayListOf(
        "João Carlos acabou de anunciar Laranja!",
        "João Carlos anunciou Banana!",
        "João Carlos anunciou Beterraba!"
    )
    private lateinit var notificationAdapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onStart() {
        super.onStart()

        notificationAdapter = NotificationAdapter(notificationList)

        notificationRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        notificationRecycleView.itemAnimator = DefaultItemAnimator()
        notificationRecycleView.adapter = notificationAdapter
    }

}