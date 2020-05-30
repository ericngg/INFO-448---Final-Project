package com.ljchen17.myapplication.model

import com.ljchen17.myapplication.R

class GroceryDataProvider {
    companion object {
        fun getAllGroceries(): List<GroceryDetails> = mutableListOf<GroceryDetails>().apply {
            add(
                createGrocery(
                    "Apple",
                    4,
                    "2020/06/01",
                    "Fruit",
                    "Apple bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Broccoli",
                    2,
                    "2020/06/02",
                    "Vegetable",
                    "Broccoli bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Steak",
                    1,
                    "2020/06/05",
                    "Meat and Seafood",
                    "Steak bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Milk",
                    2,
                    "2020/07/02",
                    "Dairy and Eggs",
                    "Milk bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Egg",
                    1,
                    "2020/06/02",
                    "Dairy and Eggs",
                    "Egg bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Pizza",
                    1,
                    "2020/06/02",
                    "Frozen",
                    "Pizza bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Shrimp",
                    1,
                    "2020/06/02",
                    "Meat and Seafood",
                    "Shrimp bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "M&M",
                    3,
                    "2020/06/02",
                    "Snack",
                    "M&M bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Sausage",
                    2,
                    "2020/06/02",
                    "Deli",
                    "Sausage bought from Safeway"
                )
            )
            add(
                createGrocery(
                    "Bread",
                    3,
                    "2020/06/02",
                    "Grain",
                    "Bread bought from Safeway"
                )
            )
        }

        private fun createGrocery(
            itemName: String,
            quantity: Int,
            expiration: String,
            category: String,
            description: String
        ): GroceryDetails {
            return GroceryDetails(
                itemName,
                quantity,
                expiration,
                category,
                description
            )
        }

    }
}