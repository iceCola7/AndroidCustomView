package com.cxz.selectcity.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_city.*

class SelectCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        cityBtn.setOnClickListener {
            startActivity(Intent(this, CityPickerActivity::class.java))
        }
        contactBtn.setOnClickListener {
            startActivity(Intent(this, ContactListActivity::class.java))
        }
    }

}