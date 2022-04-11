package com.noval.listfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update.*

class Update : AppCompatActivity() {

    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekAlamat: String? = null
    private var cekHp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        supportActionBar!!.title = "Update"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        data
        btn_update.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                cekNama = new_nama.getText().toString()
                cekAlamat = new_alamat.getText().toString()
                cekHp = new_no_hp.getText().toString()

                if (isEmpty(cekNama!!) || isEmpty(cekAlamat!!) || isEmpty(cekHp!!)) {
                    Toast.makeText(this@Update, "Data tidak boleh kosong",
                        Toast.LENGTH_SHORT).show()
                } else {
                    val setData = data_teman()
                    setData.nama = new_nama.getText().toString()
                    setData.alamat = new_alamat.getText().toString()
                    setData.no_hp = new_no_hp.getText().toString()
                    updateData(setData)
                }
            }
        })
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    private val data: Unit
        get() {
            val getNama = intent.extras!!.getString("dataNama")
            val getAlamat = intent.extras!!.getString("dataAlamat")
            val getHp = intent.extras!!.getString("dataHp")

            new_nama!!.setText(getNama)
            new_alamat!!.setText(getAlamat)
            new_no_hp!!.setText(getHp)
        }

    private fun updateData(teman: data_teman) {
        val userID = auth!!.uid
        val getKey = intent.extras!!.getString("getPrimaryKey")
        database!!.child("Admin")
            .child(userID!!)
            .child("DataTeman")
            .child(getKey!!)
            .setValue(teman)
            .addOnSuccessListener {
                new_nama!!.setText("")
                new_alamat!!.setText("")
                new_no_hp!!.setText("")
                Toast.makeText(this@Update, "Data telah berhasil diubah",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}