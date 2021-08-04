package br.com.fomezero.joaofood.activities.ong

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.util.loadImage
import br.com.fomezero.joaofood.model.Product
import com.google.android.material.button.MaterialButton

class ProductListAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val loadFragment: (Fragment) -> Unit
) : RecyclerView.Adapter<ProductListAdapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.name
        holder.price.text = context.getString(R.string.price_template, product.price)
        holder.amount.text = product.amount
        holder.image.loadImage(product.imageUrl, CircularProgressDrawable(context))
        holder.button.setOnClickListener {
            loadFragment(MerchantInfoFragment(productList[position]))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.food_card_list, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var price: TextView = view.findViewById(R.id.price)
        var amount: TextView = view.findViewById(R.id.amount)
        var image: ImageView = view.findViewById(R.id.image)
        var button: MaterialButton = view.findViewById(R.id.submitProductButton)
    }
}
