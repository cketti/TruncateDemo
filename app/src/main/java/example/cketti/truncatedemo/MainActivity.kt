package example.cketti.truncatedemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private val createDocumentLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument(),
        ::onDocumentCreated
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.save_button).setOnClickListener {
            createDocumentLauncher.launch("truncate-test.txt")
        }
    }

    private fun onDocumentCreated(uri: Uri?) {
        val contentUri = uri ?: return

        try {
            // Open Uri using truncate mode to make sure existing files will only contain the data we write to it.
            contentResolver.openOutputStream(contentUri, "wt")?.use { outputStream ->
                // Write a single byte
                outputStream.write('A'.code)
            }
        } catch (e: Exception) {
            e.printStackTrace()

            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
