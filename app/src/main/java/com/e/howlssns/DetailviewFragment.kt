package com.e.howlssns

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater //
import android.view.View //
import android.view.ViewGroup //
import android.widget.ImageView //

import androidx.fragment.app.Fragment //
import androidx.recyclerview.widget.LinearLayoutManager //
import androidx.recyclerview.widget.RecyclerView //
import kotlinx.android.synthetic.main.fragment_detail.view.*


/*private var View.layoutManager: LinearLayoutManager
    get() {}
    set() {}
private var View.adapter: DetailviewFragment.DetailRecyclerviewAdapter
    get() {}
    set() {}*/

class DetailviewFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(inflater.context).inflate(R.layout.fragment_detail,container,false)
        view.detailviewfragment_recyclerview.adapter = DetailRecyclerviewAdapter()

        view.detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity)


}
        return view
    }

    inner class DetailRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var imageView = LayoutInflater.from(parent.context).inflate(R.layout.item_detail,parent,false)
            return CustomViewHolder(imageView)
        }

        inner class CustomViewHolder(imageView: View?) : RecyclerView.ViewHolder()

        override fun getItemCount(): Int {
            return  3

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}