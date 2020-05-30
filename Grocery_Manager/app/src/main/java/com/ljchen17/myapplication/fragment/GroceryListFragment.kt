package com.ljchen17.myapplication.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ljchen17.myapplication.GroceryListAdapter
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.SwipeToDeleteCallback
import com.ljchen17.myapplication.activity.EditActivity
import com.ljchen17.myapplication.model.GroceryDetails
import kotlinx.android.synthetic.main.fragment_grocery_list.*

/**
 * A simple [Fragment] subclass.
 */
class GroceryListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: GroceryListAdapter
    private var OnGroceryClickListener: OnGroceryClickListener? = null
    private var allGroceries: ArrayList<GroceryDetails> = ArrayList<GroceryDetails>()

    companion object {
        val TAG: String = GroceryListFragment::class.java.simpleName
        const val ARG_GROCERYLIST = "arg_songlist"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val grocerylist = args.getParcelableArrayList<GroceryDetails>(ARG_GROCERYLIST)
            if (grocerylist != null) {
                this.allGroceries = grocerylist.toList() as ArrayList<GroceryDetails>
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGroceryClickListener) {
            OnGroceryClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grocery_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context)
        rvGrocery.layoutManager = linearLayoutManager
        adapter = GroceryListAdapter(allGroceries)

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvGrocery)

        addItemBtn.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            //intent.putExtra(SONG_IMAGE, currentSong.largeImageID)
            startActivity(intent)
        }

        grocery_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        adapter.onGroceryClickListener = { someGrocery: GroceryDetails ->
            OnGroceryClickListener?.onGroceryClicked(someGrocery)
        }

        rvGrocery.adapter = adapter
        rvGrocery.setHasFixedSize(true)
    }
}

interface OnGroceryClickListener{
    fun onGroceryClicked(grocery: GroceryDetails)
}
