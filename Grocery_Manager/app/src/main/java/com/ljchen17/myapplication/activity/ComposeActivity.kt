package com.ljchen17.myapplication.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.data.model.GroceryDetails
import com.ljchen17.myapplication.fragment.GroceryDetailsFragment
import com.ljchen17.myapplication.fragment.GroceryListFragment
import com.ljchen17.myapplication.fragment.OnGroceryClickListener


class ComposeActivity : AppCompatActivity(), OnGroceryClickListener {

    private var currentGrocery : GroceryDetails? = null

    companion object {
        val STATE_GROCERY = "currentgrocery"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.activity_compose))

        if (savedInstanceState != null) {

                with(savedInstanceState) {
                    currentGrocery = getParcelable(STATE_GROCERY)
                }

            val groceryDetailsFragment = getGroceryDetailFragment()

            if (groceryDetailsFragment != null) {

                supportActionBar?.setDisplayHomeAsUpEnabled(true)

            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)

            }

        } else {

            val argumentBundle = Bundle().apply {
              //  val groceryList = (application as GroceryApplication).databaseManager.getItems()
             //   putParcelableArrayList(GroceryListFragment.ARG_GROCERYLIST, ArrayList(groceryList))
            }

            val groceryListFragment = GroceryListFragment()
            groceryListFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, groceryListFragment,GroceryListFragment.TAG)
                .commit()

            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        supportFragmentManager.addOnBackStackChangedListener {

            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState?.run {
            putParcelable(STATE_GROCERY, currentGrocery)
        }
        super.onSaveInstanceState(outState)
    }

    private fun getGroceryDetailFragment() = supportFragmentManager.findFragmentByTag(GroceryDetailsFragment.TAG) as? GroceryDetailsFragment
    private fun getListFragment() = supportFragmentManager.findFragmentByTag(GroceryListFragment.TAG) as? GroceryListFragment

    override fun onSupportNavigateUp(): Boolean {

        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onGroceryClicked(grocery:GroceryDetails) {

        getListFragment()?.resetSearch()

        currentGrocery = grocery
        var groceryDetailsFragment = getGroceryDetailFragment()

        if (groceryDetailsFragment == null) {

            groceryDetailsFragment = GroceryDetailsFragment()
            val argumentBundle = Bundle().apply {
                putParcelable(GroceryDetailsFragment.ARG_GROCERY, currentGrocery)
            }

            groceryDetailsFragment.arguments = argumentBundle

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, groceryDetailsFragment, GroceryDetailsFragment.TAG)
                .addToBackStack(GroceryDetailsFragment.TAG)
                .commit()

        } else {
            groceryDetailsFragment.updateGrocery(currentGrocery)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}