package jp.ict.muffin.otasukejuru

import android.app.AlertDialog
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_list_todo.*
import kotlinx.android.synthetic.main.task_card_view.view.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textColor
import java.util.*


class ToDoListFragment : Fragment() {
    
    private var mTimer: Timer? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list_todo, container, false)
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setCardView()
    }
    
    override fun onResume() {
        super.onResume()
        val mHandler = Handler()
        mTimer = Timer()
        mTimer?.schedule(object : TimerTask() {
            override fun run() {
                mHandler.post {
                    setCardView()
                }
            }
        }, 5000, 5000)
    }
    
    override fun onStop() {
        super.onStop()
        mTimer?.cancel()
        mTimer = null
    }
    
    fun setCardView() {
        (0..6).forEach {
            when (it) {
                0 -> mostPriorityCardLinear
                1 -> highPriorityCardLinear1
                2 -> highPriorityCardLinear2
                3 -> middlePriorityCardLinear1
                4 -> middlePriorityCardLinear2
                5 -> lowPriorityCardLinear1
                else -> lowPriorityCardLinear2
            }.removeAllViews()
        }
        
        var highPriorityNum = 0
        var middlePriorityNum = 0
        var lowPriorityNum = 0
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.MONTH) * 100 + calendar.get(Calendar.DAY_OF_MONTH)
        
        GlobalValue.taskInformationArrayList.forEach {
            val diffDays = diffDayNum(today, it.limitDate, calendar.get(Calendar.YEAR))
            
            val inflater: LayoutInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val linearLayout: LinearLayout = inflater.inflate(R.layout.task_card_view, null) as LinearLayout
            
            linearLayout.apply {
                dateTextView.apply {
                    text = diffDays.toString()
                    if (it.priority == 0) {
                        textColor = context.resources.getColor(R.color.mostPriority)
                    }
                }
                cardView.apply {
                    tag = it.limitDate
                    setOnClickListener {
                        createDialog(tag.toString())
                    }
                }
                taskNameTextView.text = it.name
            }
            when (it.priority) {
                0 -> mostPriorityCardLinear
                1 -> {
                    highPriorityNum++
                    if (4 < highPriorityNum) {
                        highPriorityCardLinear1
                    } else {
                        highPriorityCardLinear2
                    }
                }
                2 -> {
                    middlePriorityNum++
                    if (4 < middlePriorityNum) {
                        middlePriorityCardLinear1
                    } else {
                        middlePriorityCardLinear2
                    }
                }
                else -> {
                    lowPriorityNum++
                    if (4 < lowPriorityNum) {
                        lowPriorityCardLinear1
                    } else {
                        lowPriorityCardLinear2
                    }
                }
            }.addView(linearLayout, 0)
        }
    }
    
    private fun createDialog(tagName: String) {
        val listDialog = arrayOfNulls<String>(3)
        listDialog[0] = "開始"
        listDialog[1] = "変更"
        listDialog[2] = "削除"
        val dialog = AlertDialog.Builder(context)
        dialog.setItems(listDialog, { _, i ->
            toast(when (i) {
                0 -> "Start"
                1 -> "Change"
                else -> "Delete"
            })
            
        })
        dialog.create().show()
    }
    
    private fun diffDayNum(beforeDate: Int, afterDate: Int, year: Int): Int {
        
        val totalDays = if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        } else {
            intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335)
        }
        
        val beforeDay = beforeDate % 100
        val beforeMonth = beforeDate / 100
        val afterDay = afterDate % 100
        val afterMonth = afterDate / 100
        return (totalDays[afterMonth] + afterDay - (totalDays[beforeMonth] + beforeDay))
    }
}