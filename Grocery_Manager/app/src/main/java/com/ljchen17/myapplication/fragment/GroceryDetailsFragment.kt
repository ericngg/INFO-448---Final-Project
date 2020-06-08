package com.ljchen17.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.activity.ComposeActivity
import com.ljchen17.myapplication.activity.EditActivity
import com.ljchen17.myapplication.data.GroceryViewModel
import com.ljchen17.myapplication.data.model.GroceryDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grocery_details.*
import java.io.File
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 */
class GroceryDetailsFragment : Fragment() {

    private var grocery: GroceryDetails? = null
    private val newGroceryActivityRequestCode = 1

    companion object {

        val TAG: String = GroceryDetailsFragment::class.java.simpleName
        const val ARG_GROCERY = "arg_grocery"
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

    private fun updateGroceryViews() {
        grocery?.let {
            itemName.text = grocery!!.itemName
            quantity.text = "Quantity: " + grocery!!.quantity.toString()
            category.text = "Category: " + grocery!!.category
            expirationDate?.text = "Expiration Date: " + grocery!!.expiration
            description?.text = "Note: \n" + grocery!!.description

            val imagePath = grocery!!.imagePath
            if (imagePath != null) {
                val file = File(imagePath)
                val uri = Uri.fromFile(file)
                Picasso.get().load(uri).into(cover)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.editmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        if (item.itemId == R.id.action_edit) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(EditActivity.ITEM_TO_EDIT, grocery)
            startActivityForResult(intent,newGroceryActivityRequestCode)
        }  else {
            super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newGroceryActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val grocery = data.getParcelableExtra(EditActivity.EXTRA_REPLY) as GroceryDetails
                // Get a new or existing ViewModel from the ViewModelProvider.
                val groceryViewModel = ViewModelProvider(context as ComposeActivity).get(
                    GroceryViewModel::class.java)

                if (grocery.iid == (-1).toLong()){
                    var random = kotlin.random.Random
                    grocery.iid = random.nextLong(Long.MAX_VALUE)
                    groceryViewModel.newItem(grocery)
                } else {
                    groceryViewModel.updateItem(grocery)
                }
                updateGrocery(grocery)

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
