package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.pickup_check_list.PickupCheckListDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parameters = "{\"senders\":[{\"id\":\"212758\",\"active\":\"true\",\"header\":\"רגע לפני שמתחילים יש לשים לב\",\"subtitle\":\"במקר של אי מסירה, יש לשים פתק DT על דלת המקבל המשלוח. יש לבקש מאיש הקשר באיסוף פתקים שישמשו אותך במיקרים אילו יש לבקש מאיש הקשר באיסוף פתקים שישמשויש לבקש מאיש הקשר באיסוף פתקים שישמשויש לבקש מאיש הקשר באיסוף פתקים שישמשו\",\"image\":\"https://getpackage.com/wp-content/uploads/2021/03/Group-1.png\",\"checkboxes\":[{\"checkbox\":\"ֿֿNote\"},{\"checkbox\":\"Another note\"}],\"button\":\"התחלת האיסוף\"}]}"
        PickupCheckListDialogFragment.newInstance(parameters).show(supportFragmentManager, PickupCheckListDialogFragment.TAG)

    }
}