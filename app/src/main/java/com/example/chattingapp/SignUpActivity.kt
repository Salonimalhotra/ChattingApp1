
package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var edtName:EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    //firebase authentication
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtName=findViewById(R.id.edt_name)
        edtEmail=findViewById(R.id.edt_email)
        edtPassword=findViewById(R.id.edt_password)
        btnSignUp=findViewById(R.id.btn_signUp)
        mAuth=FirebaseAuth.getInstance()
       //hiding actionBar
        supportActionBar?.hide()


        btnSignUp.setOnClickListener {
            val name=edtName.text.toString()
            val password:String=edtPassword.text.toString()
            val email:String=edtEmail.text.toString()

            SignUp(name,email,password)
        }

    }
    private fun SignUp(name:String,email:String,password:String){
    //logic for creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                 //code for jumping home activity
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent= Intent(this@SignUpActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                 Toast.makeText(this@SignUpActivity,"Some error occurred",Toast.LENGTH_SHORT).show()

                }
            }


    }

    private fun addUserToDatabase(name:String,email:String,uid:String){
     mDbRef=FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email, uid))
    }
}