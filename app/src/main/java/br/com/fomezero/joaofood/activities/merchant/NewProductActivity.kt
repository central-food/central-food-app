package br.com.fomezero.joaofood.activities.merchant

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.com.fomezero.joaofood.R
import br.com.fomezero.joaofood.activities.ActiveUserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_new_food.addFoodButton
import kotlinx.android.synthetic.main.activity_new_food.donationSwitch
import kotlinx.android.synthetic.main.activity_new_food.foodImage
import kotlinx.android.synthetic.main.activity_new_food.priceField
import kotlinx.android.synthetic.main.activity_new_food.priceInputLayout
import kotlinx.android.synthetic.main.activity_new_food.productNameField
import kotlinx.android.synthetic.main.activity_new_food.quantityField
import kotlinx.android.synthetic.main.activity_new_food.submitProductButton


class NewProductActivity : AppCompatActivity() {
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_food)
        donationSwitch.isChecked = true

        donationSwitch.setOnClickListener {
            if (donationSwitch.isChecked) {
                priceInputLayout.visibility = View.GONE
            } else {
                priceInputLayout.visibility = View.VISIBLE
            }
        }

        addFoodButton.setOnClickListener {
            if (Build.VERSION.SDK_INT < 23) {
                takePhoto()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    takePhoto()
                } else {
                    val permissionStorage = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //Asking request Permissions
                    ActivityCompat.requestPermissions(this, permissionStorage, 9)
                }
            }
        }

        submitProductButton.setOnClickListener {
            onSubmit()
        }
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Camera is unavailable", Toast.LENGTH_LONG).show()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intentHome = Intent(this, MerchantHomeActivity::class.java)
        startActivity(intentHome)
        finish()
    }

    private fun onSubmit() {
        val price = if (donationSwitch.isChecked) {
            "0.0"
        } else {
            priceField.text.toString()
        }

        val users = db.collection("users")
        val query = users.whereEqualTo("email", auth.currentUser?.email)
        query.get()
            .addOnSuccessListener { result ->
                if (result.isEmpty.not()) {
                    val document = result.first()
                    val productData = hashMapOf(
                        "name" to productNameField.text.toString(),
                        "amount" to quantityField.text.toString(),
                        "price" to price,
                        "user" to document.reference,
                    )
                    db.collection("products").add(productData)
                        .addOnSuccessListener { usersDocumentReference ->
                            Log.d(
                                TAG,
                                "DocumentSnapshot added with ID: ${usersDocumentReference.id}"
                            )
                            ActiveUserData.sendNotification(usersDocumentReference.id)
                            runOnUiThread {
                                val intentRegisterConfirmation = Intent(this, RegisterConfirmation::class.java)
                                startActivity(intentRegisterConfirmation)
                                finish()
                            }
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                            finish()
                        }
                }


            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap?
            foodImage.setImageBitmap(photo)
            foodImage.visibility = View.VISIBLE
            // TODO: Send photo do imgur and send url to database
//            val imageUri = photo?.let { getImageUri(this, it) }
        }
    }

    companion object {
        private const val TAG = "NewProductActivity"
        private const val CAMERA_REQUEST = 1888
    }
}