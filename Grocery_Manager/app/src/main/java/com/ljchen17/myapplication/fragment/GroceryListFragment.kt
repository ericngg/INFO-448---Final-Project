package com.ljchen17.myapplication.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ljchen17.myapplication.GroceryApplication
import com.ljchen17.myapplication.GroceryListAdapter
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.SwipeToDeleteCallback
import com.ljchen17.myapplication.activity.EditActivity
import com.ljchen17.myapplication.data.GroceryViewModel
import com.ljchen17.myapplication.data.model.GroceryDetails
import kotlinx.android.synthetic.main.fragment_grocery_list.*
import java.util.*
import kotlin.random.Random.Default.nextLong

/**
 * A simple [Fragment] subclass.
 */
class GroceryListFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: GroceryListAdapter
    private var OnGroceryClickListener: OnGroceryClickListener? = null
    private lateinit var groceryViewModel: GroceryViewModel
    private val newGroceryActivityRequestCode = 1
    private lateinit var GApp: GroceryApplication

    companion object {
        val TAG: String = GroceryListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGroceryClickListener) {
            OnGroceryClickListener = context
        }
        GApp = context.applicationContext as GroceryApplication
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

        // Get a new or existing ViewModel from the ViewModelProvider.
        groceryViewModel = ViewModelProvider(this).get(GroceryViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        groceryViewModel.allGroceries.observe((context as AppCompatActivity), Observer { groceries ->
            // Update the cached copy of the words in the adapter.
            groceries?.let { adapter.setGroceries(it)
                             GApp.allGroceries = it
                             GApp.startNotify()}
        })

        adapter = GroceryListAdapter(groceryViewModel)

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvGrocery)

        addItemBtn.setOnClickListener {
           // Log.i("echee", GApp.allGroceries.toString())
//            GApp.test(GApp.allGroceries[0].expiration)
            resetSearch()
            val intent = Intent(context, EditActivity::class.java)
            startActivityForResult(intent,newGroceryActivityRequestCode)
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

        // startNotification
//        startNT()
    }

//    private fun startNT() {
//        GApp.startNotify()
//    }
    public fun resetSearch() {
        grocery_search.setQuery("", false);
        grocery_search.clearFocus();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newGroceryActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val grocery = data.getParcelableExtra(EditActivity.EXTRA_REPLY) as GroceryDetails

                if (grocery.iid == (-1).toLong()){
                    var random = kotlin.random.Random
                    grocery.iid = random.nextLong(Long.MAX_VALUE)
                    groceryViewModel.newItem(grocery)
                } else {
                    groceryViewModel.updateItem(grocery)
                }
                Unit
            }
        } else {
            Toast.makeText(
                context,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}

interface OnGroceryClickListener{
    fun onGroceryClicked(grocery: GroceryDetails)
}
