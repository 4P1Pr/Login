package com.project.rezasaputra.kotlinkt

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        //memanggil function login
        submit.setOnClickListener {
            login()
        }

        nav_regist.setOnClickListener {
            val pindah = Intent(this@Login, Register::class.java)
            startActivity(pindah)
        }

    }


    private fun login() {
        //ini buat declare editext jadi variable
        val etusername = username.text.toString()
        val etpassword = password.text.toString()

        //ini untuk membuat request ke volley dengan parameter dan method post
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_LOGIN,
                Response.Listener<String> { response ->
                    try {
                        //ini ngambil responnya dari json
                        val obj = JSONObject(response)
                        val message = obj.getString("msg")
                        if(message == "Login Success"){

                            val intent = Intent (this@Login, Utama::class.java)
                            startActivity(intent)

                            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                        }
                        //val intent = Intent (this@Login, Utama::class.java)
                        //startActivity(intent)
                        //Toast.makeText(applicationContext, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(volleyError: VolleyError) {
                        Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email", etusername)
                params.put("password", etpassword)
                return params
            }
        }
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}