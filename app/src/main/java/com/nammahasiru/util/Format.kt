package com.nammahasiru.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

fun formatEpochDay(epochDay: Long): String =
    LocalDate.ofEpochDay(epochDay).format(formatter)

