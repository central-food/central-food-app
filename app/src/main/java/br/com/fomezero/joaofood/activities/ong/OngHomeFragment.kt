package br.com.fomezero.joaofood.activities.ong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.ActiveUserData
import br.com.fomezero.joaofood.model.Product
import kotlinx.android.synthetic.main.fragment_ong_home.foodListRecycleView

class OngHomeFragment : Fragment() {
    private var productList = arrayListOf<Product>()
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ong_home, container, false)
    }

    override fun onStart() {
        super.onStart()

        productListAdapter = ProductListAdapter(activity!!, productList) {
            loadFragment(it)
        }

        foodListRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        foodListRecycleView.itemAnimator = DefaultItemAnimator()
        foodListRecycleView.adapter = productListAdapter

        ActiveUserData.getProductList(object : ActiveUserData.ProductListCallBack {
            override fun onSuccess(productList: ArrayList<Product>)
            {
                activity?.runOnUiThread {
                    this@OngHomeFragment.productList.clear()
                    this@OngHomeFragment.productList.addAll(productList)
                    productListAdapter.notifyDataSetChanged()
                }
            }

            override fun onError(throwable: Throwable) { }

        })
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    companion object {
        private const val TAG = "OngHomeFragment"
    }
}