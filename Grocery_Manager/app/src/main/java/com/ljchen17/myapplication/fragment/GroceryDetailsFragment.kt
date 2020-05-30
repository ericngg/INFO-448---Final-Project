package com.ljchen17.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ljchen17.myapplication.R
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

}
