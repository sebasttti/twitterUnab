package com.sebastianjoya.twitunab.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sebastianjoya.twitunab.R
import com.sebastianjoya.twitunab.databinding.TwitDetailBinding
import com.sebastianjoya.twitunab.model.entity.Twit

class TwitAdapter(private var twits:ArrayList<Twit>):RecyclerView.Adapter<TwitAdapter.TwitViewHolder>() {

    var onItemClickListener:((Twit)->Unit)?=null
    var onItemLongClickListener:((Twit)->Unit)?=null

    fun refresh(theseTwits: ArrayList<Twit>){
        twits=theseTwits
        notifyDataSetChanged()
    }

    class TwitViewHolder(private val binding: TwitDetailBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(
            myTwit:Twit,
            onItemClickListener: ((Twit) -> Unit)?,
            onItemLongClickListener: ((Twit) -> Unit)?
        ){
            binding.twit = myTwit

            binding.root.setOnClickListener{

                onItemClickListener?.let{
                    it(myTwit)
                }
            }

            binding.root.setOnLongClickListener{
                onItemLongClickListener?.let{
                    it (myTwit)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding: TwitDetailBinding = DataBindingUtil.inflate(
            inflate,
            R.layout.twit_detail,
            parent,
            false
        )

        return TwitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TwitViewHolder, position: Int) {
        holder.bind(twits[position],onItemClickListener,onItemLongClickListener)
    }

    override fun getItemCount(): Int = twits.size
}