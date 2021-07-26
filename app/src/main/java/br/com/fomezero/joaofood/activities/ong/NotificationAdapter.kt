package br.com.fomezero.joaofood.activities.ong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fomezero.joaofood.R

class NotificationAdapter(
    private val notificationList: List<String>,
): RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {
    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.title.text = notification
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.notification_card_list, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
    }
}
