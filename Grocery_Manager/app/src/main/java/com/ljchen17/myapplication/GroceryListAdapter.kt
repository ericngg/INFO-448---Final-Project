package com.ljchen17.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ljchen17.myapplication.model.GroceryDetails
import java.util.*
import kotlin.collections.ArrayList

class GroceryListAdapter(private var listOfGroceries: ArrayList<GroceryDetails>): RecyclerView.Adapter<GroceryListAdapter.GroceryViewHolder>(), Filterable{

    var groceryFilterList = ArrayList<GroceryDetails>()

    init {
        groceryFilterList = listOfGroceries
    }

    var onGroceryClickListener: ((grocery: GroceryDetails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {

        val itemViewFromALayout = LayoutInflater.from(parent.context).inflate(R.layout.item_grocery, parent, false)
        return GroceryViewHolder(itemViewFromALayout)
    }

    override fun getItemCount(): Int {
        return groceryFilterList.size
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val grocery = groceryFilterList[position]
        holder.bindView(grocery)
    }

    inner class GroceryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val groceryTitle by lazy {itemView.findViewById<TextView>(R.id.itemName)}
        private val groceryQuantity by lazy {itemView.findViewById<TextView>(R.id.quantity)}
        private val groceryExpiration by lazy {itemView.findViewById<TextView>(R.id.expirationDate)}
        private val groceryImage by lazy {itemView.findViewById<ImageView>(R.id.ivGroceryImage)}

        fun bindView (grocery: GroceryDetails) {

            groceryTitle.text = grocery.itemName
            groceryQuantity.text = grocery.quantity.toString()
            groceryExpiration.text = "Expires On: " + grocery.expiration
            // songImage.setImageResource(grocery.smallImageID)

            itemView.setOnClickListener {
                onGroceryClickListener?.invoke(grocery)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    groceryFilterList = listOfGroceries
                } else {
                    val resultList = ArrayList<GroceryDetails>()
                    for (row in listOfGroceries) {
                        if (row.itemName.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    groceryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = groceryFilterList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                groceryFilterList = results?.values as ArrayList<GroceryDetails>
                notifyDataSetChanged()
            }
        }
    }
    fun removeAt(position: Int) {
        listOfGroceries.removeAt(position)
        notifyItemRemoved(position)
    }
}

