package br.com.fomezero.joaofood.activities.merchant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.ActiveUserData
import br.com.fomezero.joaofood.model.MerchantData
import br.com.fomezero.joaofood.model.OngData
import br.com.fomezero.joaofood.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_merchant_home.addProductButton
import kotlinx.android.synthetic.main.fragment_merchant_home.productRecyclerView
import kotlinx.android.synthetic.main.fragment_merchant_home.recyclerView


class MerchantHomeFragment : Fragment() {
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private var ongList = arrayListOf<OngData>()
    private var productList = arrayListOf<Product>()
    private lateinit var ongAdapter: OngsRecycleViewAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onStart() {
        super.onStart()
        ongAdapter = OngsRecycleViewAdapter(ongList, activity!!) {
            loadFragment(it)
        }

        productAdapter = ProductAdapter(productList, activity!!)


        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = ongAdapter

        productRecyclerView

        productRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        productRecyclerView.itemAnimator = DefaultItemAnimator()
        productRecyclerView.adapter = productAdapter

        addProductButton.setOnClickListener {
            val newProductIntent = Intent(activity, NewProductActivity::class.java)
            startActivity(newProductIntent)
        }
        getProductList()

        ActiveUserData.getOngList(object: ActiveUserData.OngListCallback {
            override fun onSuccess(ongList: ArrayList<OngData>) {
                this@MerchantHomeFragment.ongList.clear()
                this@MerchantHomeFragment.ongList.addAll(ongList)
                ongAdapter.notifyDataSetChanged()
            }

            override fun onError(throwable: Throwable) { }

        })
    }

    private fun getProductList() {
        val users = db.collection("users")
        val query = users.whereEqualTo("email", auth.currentUser?.email)
        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty.not()) {
                    val document = result.first()
                    document.getDocumentReference("data")?.get()?.addOnSuccessListener { data ->
                        db.collection("products")
                            .whereEqualTo("user", document.reference)
                            .get()
                            .addOnSuccessListener { products ->
                                if (products.isEmpty.not()) {
                                    val merchantData = MerchantData(
                                        data.getString("name") ?: "",
                                        null,
                                        data.getString("phoneNumber") ?: "",
                                    )
                                    productList.clear()
                                    for (product in products) {
                                        val urlList: List<String>? = product.get("image") as List<String>?
                                        productList.add(
                                            Product(
                                                product.getString("name") ?: "",
                                                product.getString("amount") ?: "",
                                                product.getString("price") ?: "0",
                                                urlList?.first(),
                                                merchantData
                                            )
                                        )
                                        productAdapter.notifyDataSetChanged()
                                    }
                                }

                            }
                    }
                }
            }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_merchant_home, container, false)
    }
}