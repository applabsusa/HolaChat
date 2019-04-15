package co.paulfran.paulfranco.holachat.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import co.paulfran.paulfranco.holachat.R
import co.paulfran.paulfranco.holachat.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (userId.isNullOrEmpty()) {
            finish()
        }
        progressLayout.setOnTouchListener { v, event -> true }

        populateInfo()
    }

    private fun populateInfo() {
        progressLayout.visibility = View.GONE
        firebaseDB.collection(DATA_USERS)
                .document(userId!!)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    nameET.setText(user?.name, TextView.BufferType.EDITABLE)
                    emailET.setText(user?.email, TextView.BufferType.EDITABLE)
                    phoneET.setText(user?.phone, TextView.BufferType.EDITABLE)
                    progressLayout.visibility = View.GONE
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    finish()
                }
    }

    fun onApply(v: View) {
        progressLayout.visibility = View.GONE
        val name = nameET.text.toString()
        val email = emailET.text.toString()
        val phone = phoneET.text.toString()
        val map = HashMap<String, Any> ()
        map[DATA_USER_NAME] = name
        map[DATA_USER_EMAIL] = email
        map[DATA_USER_PHONE] = phone
        firebaseDB.collection(DATA_USERS)
                .document(userId!!)
                .update(map)
                .addOnSuccessListener {
                    Toast.makeText(this, "Update Successful", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                    profileLayout.visibility = View.GONE
                }

    }

    fun onDelete(v: View) {
        progressLayout.visibility = View.GONE
        AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("This will delete your profile information. Are you sure?")
                .setPositiveButton("Yes") { dialog, which ->
                    Toast.makeText(this, "Profile Deleted", Toast.LENGTH_SHORT).show()
                    firebaseDB.collection(DATA_USERS).document(userId!!).delete()
                    finish()
                }
                .setNegativeButton("No") { dialog, which ->
                    progressLayout.visibility = View.GONE
                }
                .show()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, ProfileActivity::class.java)
    }
}
