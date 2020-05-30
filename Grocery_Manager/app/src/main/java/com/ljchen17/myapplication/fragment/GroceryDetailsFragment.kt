package com.ljchen17.myapplication.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.activity.ComposeActivity
import com.ljchen17.myapplication.activity.EditActivity
import com.ljchen17.myapplication.model.GroceryDetails
import kotlinx.android.synthetic.main.grocery_details.*
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class GroceryDetailsFragment : Fragment() {

    var randomNumber = Random.nextInt(1000, 10000)
    private var grocery: GroceryDetails? = null

    companion object {

        val TAG: String = GroceryDetailsFragment::class.java.simpleName
        const val ARG_GROCERY = "arg_grocery"
        const val PLAY_TIMES = "play_times"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true);

        arguments?.let { args ->
            val grocery = args.getParcelable<GroceryDetails>(ARG_GROCERY)
            if (grocery != null) {
                this.grocery = grocery
            }
        }
    }

    fun updateGrocery(grocery: GroceryDetails?) {
        this.grocery = grocery
        updateGroceryViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grocery_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateGroceryViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState?.run {
            putInt(PLAY_TIMES, randomNumber)
        }
        super.onSaveInstanceState(outState)
    }

    private fun updateGroceryViews() {
        grocery?.let {
            itemName.text = grocery!!.itemName
            quantity.text = "Quantity: " + grocery!!.quantity.toString()
            category.text = "Category: " + grocery!!.category
            expirationDate?.text = "Expiration Date: " + grocery!!.expiration
            description?.text = "Note: \n" + grocery!!.description
        }
    }

    override fun onCreateOptionsMenu(
        menu: Menu?, inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        if (item.itemId == R.id.action_edit) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(EditActivity.ITEM_TO_EDIT, grocery)
            startActivity(intent)
        }  else {
            super.onOptionsItemSelected(item)
        }
        return true
    }
}
