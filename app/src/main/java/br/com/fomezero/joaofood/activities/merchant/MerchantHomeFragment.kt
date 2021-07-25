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
import br.com.fomezero.joaofood.model.OngData
import kotlinx.android.synthetic.main.fragment_merchant_home.addProductButton
import kotlinx.android.synthetic.main.fragment_merchant_home.recyclerView


class MerchantHomeFragment : Fragment() {

    private var list = listOf<OngData>(OngData("Feliz", "1234", "https://s2.glbimg.com/TcOlk_66J_D0a_U34wR81bwawa8=/607x426/smart/e.glbimg.com/og/ed/f/original/2019/10/15/73294822_1403047876514847_2297516901577785344_o.jpg"), OngData("Comida", "4321"))
    private lateinit var adapter: OngsRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        adapter = OngsRecycleViewAdapter(list, activity!!)


        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter

        addProductButton.setOnClickListener {
            val newProductIntent = Intent(activity, NewFoodActivity::class.java)
            startActivity(newProductIntent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_merchant_home, container, false)
    }
}