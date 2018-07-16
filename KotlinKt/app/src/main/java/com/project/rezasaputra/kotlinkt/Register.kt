package com.project.rezasaputra.kotlinkt

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
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
        val stringRequestRegister = object : StringRequest(Request.Method.POST, EndPoints.URL_REGISTER,
                Response.Listener<String> { response ->
                    try {
                        //ini ngambil responnya dari json
                        val objek = JSONObject(response)
                        /*val intent = Intent (this@Register, Login::class.java)
                        startActivity(intent)*/
                        Toast.makeText(applicationContext, objek.getString("msg"), Toast.LENGTH_LONG).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(Error: VolleyError) {
                        Toast.makeText(applicationContext, Error.message, Toast.LENGTH_LONG).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val paramex = HashMap<String, String>()
                paramex.put("nama", etname)
                paramex.put("email", etemail)
                paramex.put("password", etpassword)
                paramex.put("nohp", etnohp)
                paramex.put("id_fb", etfb)
                paramex.put("id_gplus", etgplus)
                return paramex
            }
        }
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequestRegister)
    }
}
