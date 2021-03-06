package jp.ict.muffin.otasukejuru_peer.`object`


object GlobalValue {
    var displayWidth: Int = 0
    var displayHeight: Int = 0
    var taskInfoArrayList: ArrayList<TaskInfo> = ArrayList()
    var scheduleInfoArrayList: ArrayList<ScheduleInfo> = ArrayList()
    var everyInfoArrayList: ArrayList<EveryInfo> = ArrayList()
    var SERVER_URL: String = ""
    var focusTimeG: Long = 1
    var intervalTimeG: Long = 0
    val monthDays = arrayListOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
}