package com.example.diplom

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.diplom.databinding.ActivityAddHomeworkBinding

class AddHomeworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddHomeworkBinding
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHomeworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.btnSaveHomework.setOnClickListener {
            saveHomework()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.ivHomeworkPhoto.setImageBitmap(imageBitmap)
            binding.ivHomeworkPhoto.visibility = ImageView.VISIBLE
        }
    }

    private fun saveHomework() {
        val homeworkText = binding.etHomework.text.toString()
        // Save homeworkText and the photo (if any) to database or shared preferences
        // This example just returns to the previous activity with a result
        val resultIntent = Intent()
        resultIntent.putExtra("homework_text", homeworkText)
        // You might also want to save the image path or URI
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
