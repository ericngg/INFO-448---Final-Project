package com.ljchen17.myapplication.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.data.model.GroceryDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.item_grocery.view.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import android.preference.PreferenceManager
import com.ljchen17.myapplication.GroceryApplication

class EditActivity : AppCompatActivity() {

    var textViewDate: TextView? = null
    var cal = Calendar.getInstance()
    var existingItemId:Long? = null
    var imagePath:String? = null

    var editContainer: ConstraintLayout? = null

    private val TAKE_PHOTO_REQUEST = 101
    private var mCurrentPhotoPath: String = ""

    lateinit var GApp: GroceryApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        theme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        fab_capture.setOnClickListener { validatePermissions()}

        // get the references from layout file
        textViewDate = this.expirationDate as TextView?

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        textViewDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@EditActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })

        // access the items of the list
        val categories = resources.getStringArray(R.array.category)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.category)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, categories)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
            val selectedItemText = parent.getItemAtPosition(position);
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        val grocery = intent.getParcelableExtra<GroceryDetails>(ITEM_TO_EDIT)
        if (grocery != null) {
            existingItemId = grocery.iid
            itemName.text = Editable.Factory.getInstance().newEditable(grocery.itemName)
            quantity.text = Editable.Factory.getInstance().newEditable(grocery.quantity.toString())
            expirationDate.text = grocery.expiration
            //category.text = grocery!!.category


            imagePath = grocery.imagePath

            if (imagePath != null) {
                val file = File(imagePath)
                val uri = Uri.fromFile(file)
                Picasso.get().load(uri).into(cover)
            }
            spinner.setSelection(categories.indexOf(grocery.category))
            description?.text = Editable.Factory.getInstance().newEditable(grocery.description)
        }
    }

    // Camera code
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PHOTO_REQUEST) {
            processCapturedPhoto()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun validatePermissions() {
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    launchCamera()
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?,
                                                                token: PermissionToken?) {
                    AlertDialog.Builder(this@EditActivity)
                        .setTitle(R.string.storage_permission_rationale_title)
                        .setMessage(R.string.storage_permition_rationale_message)
                        .setNegativeButton(android.R.string.cancel,
                            { dialog, _ ->
                                dialog.dismiss()
                                token?.cancelPermissionRequest()
                            })
                        .setPositiveButton(android.R.string.ok,
                            { dialog, _ ->
                                dialog.dismiss()
                                token?.continuePermissionRequest()
                            })
                        .setOnDismissListener({ token?.cancelPermissionRequest() })
                        .show()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Snackbar.make(editContainer!!,
                        R.string.storage_permission_denied_message,
                        Snackbar.LENGTH_LONG)
                        .show()
                }
            })
            .check()
    }

    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }

    }

    private fun processCapturedPhoto() {
        val cursor = contentResolver.query(Uri.parse(mCurrentPhotoPath),
            Array(1) {android.provider.MediaStore.Images.ImageColumns.DATA},
            null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
        }
        val photoPath = cursor?.getString(0)
        if (cursor != null) {
            cursor.close()
        }
        imagePath = photoPath
            val file = File(photoPath)
        val uri = Uri.fromFile(file)
        Picasso.get().load(uri).into(cover)

    }


    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textViewDate!!.text = sdf.format(cal.getTime())
    }

    companion object {
        val TAG: String = EditActivity::class.java.simpleName
        const val ITEM_TO_EDIT = "item_to_edit"
        const val EXTRA_REPLY = "item_edited"
    }

    fun submitData(view: View) {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(itemName.text) || TextUtils.isEmpty(expirationDate.text)) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            var id:Long = -1
            if (existingItemId != null) {
                id = existingItemId as Long
            }

            var itemName = itemName.text.toString()
            var quantity = quantity.text.toString()
            var expiration = expirationDate.text.toString()
            val spinner = findViewById<Spinner>(R.id.category)
            var category = spinner.selectedItem.toString()
            var description = description.text.toString()

            val item = GroceryDetails(
                id,
                imagePath,
                itemName,
                quantity.toInt(),
                expiration,
                category,
                description
            )

            replyIntent.putExtra(EXTRA_REPLY, item)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        GApp.notifiable = true
        finish()
    }

    private fun theme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val name = sharedPreferences.getBoolean("theme",  true)
        if (name) {
            setTheme(R.style.dark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}

