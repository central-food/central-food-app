package br.com.fomezero.joaofood.activities.merchant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.util.loadImage
import br.com.fomezero.joaofood.model.OngData


class OngsRecycleViewAdapter(
    private var ongList: List<OngData>,
    private var context: Context,
    private val loadFragment: (Fragment) -> Unit
) : RecyclerView.Adapter<OngsRecycleViewAdapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        return ongList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ong = ongList[position]
        holder.name.text = ong.name
        holder.image.loadImage(ong.imageUrl, CircularProgressDrawable(context))

        holder.cardView.setOnClickListener {
            loadFragment(OngInfoFragment(ongList[position]))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.card_list, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var image: ImageView = view.findViewById(R.id.image)
        var cardView: CardView = view.findViewById(R.id.cardView)
    }
}
