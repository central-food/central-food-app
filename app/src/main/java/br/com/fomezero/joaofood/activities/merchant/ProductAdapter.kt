package br.com.fomezero.joaofood.activities.merchant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.util.loadImage
import br.com.fomezero.joaofood.model.Product


class ProductAdapter(
    private var productList: List<Product>,
    private var context: Context
) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.name
        holder.image.loadImage(product.imageUrl, CircularProgressDrawable(context))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.card_list, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var image: ImageView = view.findViewById(R.id.image)

    }
}
