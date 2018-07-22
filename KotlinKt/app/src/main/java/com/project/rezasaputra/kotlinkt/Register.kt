package com.project.rezasaputra.kotlinkt

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //memanggil fungsi registrasi
        registrasi.setOnClickListener {
            register()
        }
    }

    private fun register() {
        //ini buat declare editext jadi variable
        val etname = nama_user.text.toString()
        val etemail = email_user.text.toString()
        val etpassword = password_user.text.toString()
        val etnohp = nohp_user.text.toString()
        val etfb = idfb_user.text.toString()
        val etgplus = idgplus.text.toString()


        //ini untuk membuat request ke volley dengan parameter dan method post
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_REGISTER,
                Response.Listener<String> { response ->
                    try {
                        //ini ngambil responnya dari json
                        val obj = JSONObject(response)
                        val msg = obj.getString("msg")
                        if(msg == "User Added"){

                            val intent = Intent (this@Register, Utama::class.java)
                            startActivity(intent)

                            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
                        }
                        //val intent = Intent (this@Login, Utama::class.java)
                        //startActivity(intent)
                        //Toast.makeText(applicationContext, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError -> 
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val paramex = HashMap<String, String>()
                paramex.put("email", etemail)
                paramex.put("password", etpassword)
                paramex.put("id_fb", etfb)
                paramex.put("id_gplus", etgplus)
                paramex.put("phone", etnohp)
                paramex.put("nama", etname)
                return paramex
            }
        }
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}
