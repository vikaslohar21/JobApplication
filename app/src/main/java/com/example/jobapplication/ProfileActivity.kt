package com.example.jobapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private val REQUEST_CAMERA_CAPTURE = 2
    private val REQUEST_CAMERA_PERMISSION = 3

    private lateinit var profileImageView: ImageView
    private lateinit var captureFromCameraOrGalleryBtn: ImageButton

    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImageView = findViewById(R.id.profile)
        captureFromCameraOrGalleryBtn = findViewById(R.id.captureFromCameraOrGalleryBtn)

        // Initialize userData (replace with your logic to fetch user data)
        userData = UserData(
            profileImageUri = "", // initial value
            post = "Android Developer", // initial value
            username = "YourUsername" // initial value
        )
        // Set up click listener for the button
        captureFromCameraOrGalleryBtn.setOnClickListener {
            showImagePickerDialog()
        }

        if (savedInstanceState == null) {
            loadFragment(PersonalInfoFragment())
        }

        // Set click listeners for buttons
        val prInfoBtn : Button = findViewById(R.id.prInfoBtn)
        prInfoBtn.setOnClickListener {
            loadFragment(PersonalInfoFragment())
        }

        val expBtn : Button = findViewById(R.id.expBtn)
        expBtn.setOnClickListener {
            loadFragment(ExperienceFragment())
        }

        val topSkillsBtn : Button = findViewById(R.id.topSkillsBtn)
        topSkillsBtn.setOnClickListener {
            loadFragment(TopSkillsFragment())
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_1, fragment)
            .commit()
    }

    private fun showImagePickerDialog() {
        val items = arrayOf("Capture from Camera", "Select from Gallery")

        AlertDialog.Builder(this)
            .setTitle("Choose an action")
            .setItems(items) { _, which ->
                val REQUEST_READ_MEDIA_IMAGES_PERMISSION = 0
                when (which) {
                    0 -> checkAndExecuteAction(
                        Manifest.permission.CAMERA,
                        REQUEST_CAMERA_PERMISSION
                    ) {
                        openCamera()
                    }
                    1 -> checkAndExecuteAction(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        REQUEST_READ_MEDIA_IMAGES_PERMISSION
                    ) {
                        openGallery()
                    }
                }
            }
            .show()
    }

    private fun checkAndExecuteAction(
        permission: String,
        requestCode: Int,
        action: () -> Unit
    ) {
        if (checkPermission(permission)) {
            action()
        } else {
            requestPermission(permission, requestCode)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            requestCode
        )
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA_CAPTURE -> {
                    // Handle image captured from camera
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let {
                        val uri = getImageUriFromBitmap(it)
                        userData = userData.copy(profileImageUri = uri.toString())
                        updateProfileImage(uri)
                    }
                }
                PICK_IMAGE_REQUEST -> {
                    // Handle image selected from gallery
                    val selectedImageUri = data?.data
                    selectedImageUri?.let {
                        userData = userData.copy(profileImageUri = it.toString())
                        updateProfileImage(selectedImageUri)
                    }
                }
                // Handle other cases if needed
            }
        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "Profile_Image",
            null
        )
        return Uri.parse(path)
    }

    private fun updateProfileImage(uri: Uri?) {
        uri?.let {
            Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(profileImageView)
        }
    }
    // Method to handle edit action
    fun showEditDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Details")

        // Set up the input
        val input = EditText(this)
        // Add input fields based on your requirement
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK") { dialog, which ->
            // Get the entered text
            val newDetails = input.text.toString()
            // Update fragments with new details
            val personalInfoFragment = supportFragmentManager.findFragmentById(R.id.personal_info_fragment) as? PersonalInfoFragment
            personalInfoFragment?.updateDetails(newDetails)

            val experienceFragment = supportFragmentManager.findFragmentById(R.id.experience_fragment) as? ExperienceFragment
            experienceFragment?.updateDetails(newDetails)

            val topSkills = supportFragmentManager.findFragmentById(R.id.top_skills_fragment) as? TopSkillsFragment
            topSkills?.updateDetails(newDetails)
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        builder.show()
    }
}
