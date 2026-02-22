package com.example.ashu

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream

// Data class for the result
data class CactusResult(
    val genus: String,
    val isSucculent: Boolean,
    val description: String
)

class MainActivity : AppCompatActivity() {

    private lateinit var uploadButton: Button
    private lateinit var cameraButton: Button
    private lateinit var resultCardView: CardView
    private lateinit var uploadedImageView: ImageView
    private lateinit var genusTextView: TextView
    private lateinit var succulentTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var statusTextView: TextView

    // Gallery launcher
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let { handleImage(it) }
        }
    }

    // Camera launcher
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            imageBitmap?.let {
                uploadedImageView.setImageBitmap(it)
                uploadedImageView.isVisible = true

                statusTextView.text = "Identifying your cactus..."
                statusTextView.isVisible = true
                resultCardView.isVisible = false

                identifyCactus(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uploadButton = findViewById(R.id.upload_button)
        cameraButton = findViewById(R.id.camera_button)
        resultCardView = findViewById(R.id.result_card)
        uploadedImageView = findViewById(R.id.uploaded_image)
        genusTextView = findViewById(R.id.genus_name_text_view)
        succulentTextView = findViewById(R.id.succulent_status_text_view)
        descriptionTextView = findViewById(R.id.description_text_view)
        statusTextView = findViewById(R.id.status_text_view)

        uploadButton.setOnClickListener { startImagePicker() }
        cameraButton.setOnClickListener { startCamera() }
    }

    private fun startImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun handleImage(imageUri: Uri) {
        uploadedImageView.setImageURI(imageUri)
        uploadedImageView.isVisible = true

        statusTextView.text = "Identifying your cactus..."
        statusTextView.isVisible = true
        resultCardView.isVisible = false

        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        identifyCactus(bitmap)
    }

    // ✅ Updated identifyCactus function with safe JSON parsing
    private fun identifyCactus(bitmap: Bitmap) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
                val base64Image = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP)

                val json = JSONObject().apply {
                    put("images", arrayOf(base64Image))
                    put("modifiers", arrayOf("crops_fast", "similar_images"))
                    put("plant_language", "en")
                    put("plant_details", arrayOf("common_names", "wiki_description", "taxonomy"))
                }

                val requestBody = RequestBody.create(
                    "application/json; charset=utf-8".toMediaTypeOrNull(),
                    json.toString()
                )

                val request = Request.Builder()
                    .url("https://api.plant.id/v2/identify")
                    .addHeader("Api-Key", BuildConfig.PLANT_ID_API_KEY)
                    .post(requestBody)
                    .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()
                val responseString = response.body?.string()

                println("Plant.id API Response: $responseString") // Debug log

                if (responseString.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        statusTextView.text = "Empty response from API."
                    }
                    return@launch
                }

                val resultJson = try {
                    JSONObject(responseString)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        statusTextView.text = "Failed to parse API response."
                    }
                    return@launch
                }

                val result = parsePlantIdResponse(resultJson.toString())

                withContext(Dispatchers.Main) {
                    displayResult(result)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    statusTextView.text = "Failed to identify cactus: ${e.message}"
                }
            }
        }
    }

    private fun parsePlantIdResponse(response: String?): CactusResult {
        if (response.isNullOrEmpty()) {
            return CactusResult("Unknown", false, "No description available.")
        }

        val json = JSONObject(response)
        val suggestions = json.optJSONArray("suggestions")
        if (suggestions == null || suggestions.length() == 0) {
            return CactusResult("Unknown", false, "No description available.")
        }

        val first = suggestions.getJSONObject(0)
        val plant = first.getJSONObject("plant")

        val genus = plant.optString("genus", "Unknown")
        val descriptionObj = plant.optJSONObject("wiki_description")
        val description = descriptionObj?.optString("value") ?: "Description not available."
        val isSucculent = true // Simplified; API does not provide this directly

        return CactusResult(genus, isSucculent, description)
    }

    private fun displayResult(result: CactusResult) {
        statusTextView.isVisible = false
        resultCardView.isVisible = true

        genusTextView.text = "Genus: ${result.genus}"
        succulentTextView.text = "Succulent: ${if (result.isSucculent) "Yes" else "No"}"
        descriptionTextView.text = result.description
    }
}
