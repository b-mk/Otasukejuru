package jp.ict.muffin.otasukejuru_peer.activity

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import jp.ict.muffin.otasukejuru_peer.R
import jp.ict.muffin.otasukejuru_peer.ui.TimeSetActivityUI
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView


class TimeSetActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val index = intent.getIntExtra("taskIndex", -1)
        
        TimeSetActivityUI(index).setContentView(this)
        
        find<ImageButton>(R.id.ankoBack).setOnClickListener {
            finish()
        }
    }
}