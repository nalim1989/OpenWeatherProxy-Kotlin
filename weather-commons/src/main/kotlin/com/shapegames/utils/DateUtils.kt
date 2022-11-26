package com.shapegames.utils

import java.util.*


fun getTomorrowStartOfADay():Date{
    val calendar: Calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    calendar.add(Calendar.DAY_OF_MONTH,1)

    return calendar.time
}
 fun getTomorrowEndOfADay():Date{
     val calendar: Calendar = Calendar.getInstance()
     calendar[Calendar.HOUR_OF_DAY] = 23
     calendar[Calendar.MINUTE] = 59
     calendar[Calendar.SECOND] = 59
     calendar[Calendar.MILLISECOND] = 9999
     calendar.add(Calendar.DAY_OF_MONTH,1)

     return calendar.time
 }

fun get5DaysStartOfADay():Date{
    val calendar: Calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    calendar.add(Calendar.DAY_OF_MONTH,5)

    return calendar.time
}

fun get24HoursFromNowTime(time:Date):Date{
    val calendar: Calendar = Calendar.getInstance()
    calendar.time=time
    calendar.add(Calendar.DAY_OF_MONTH,1)

    return calendar.time
}

