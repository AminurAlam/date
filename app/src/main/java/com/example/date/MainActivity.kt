package com.example.date

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.date.ui.theme.MyComposeApplicationTheme
import java.time.*
import androidx.compose.foundation.isSystemInDarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    month()
                }
            }
        }
    }
}

@Composable
fun month() {
    val selectedMonth = remember { mutableStateOf(YearMonth.now()) }
    val today: LocalDate = LocalDate.now()
    val daysInMonth: Int = selectedMonth.value.lengthOfMonth()
    var selectedDay: Int = 1
    var count: Int
    var pad =
        selectedMonth.value
            .atDay(1)
            .dayOfWeek
            .getValue()
    var cellmod =
        Modifier
            .height(50.dp)
            .width(50.dp)
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    var weekmod =
        Modifier
            .height(36.dp)
            .width(50.dp)
            .padding(4.dp)
            .wrapContentSize(Alignment.Center)

    Column {
        Header(selectedMonth)
        Divider()

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth(),
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                Text(
                    text = day,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = weekmod,
                    color = if (day.startsWith("S")) {
                        Color(0xFFFF6E67)
                    } else if (isSystemInDarkTheme()) {
                        Color.White
                    } else {
                        Color.Black
                    },
                )
            }
        }

        while (true) {
            count = 0
            if (selectedDay > daysInMonth) break
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth(),
            ) {
                while (--pad > 0) {
                    ++count
                    Text(text = "", cellmod)
                }
                while (true) {
                    if (selectedDay > daysInMonth) break
                    if (++count > 7) break
                    Text(
                        text = "${selectedDay++}",
                        fontSize = 26.sp,
                        modifier = cellmod,
                        color = if (count > 5) {
                            Color(0xFFFF6E67)
                        } else if (isSystemInDarkTheme()) {
                            Color.White
                        } else {
                            Color.Black
                        },
                    )
                }
                while (++count <= 7) Text(text = "", cellmod)
            }
        }

        Divider()
    }
}

@Composable
fun Header(selectedMonth: MutableState<YearMonth>) {
    var year = selectedMonth.value.year
    var mon = selectedMonth.value.month.name
    Row(
        modifier = Modifier.padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "$year $mon",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier
                    .height(48.dp)
                    .width(300.dp)
                    .wrapContentSize(Alignment.CenterStart),
        )
        IconButton(onClick = {
            selectedMonth.value = selectedMonth.value.minusMonths(1L)
        }) {
            Icon(Icons.Default.KeyboardArrowLeft, "prev")
        }
        IconButton(onClick = {
            selectedMonth.value = selectedMonth.value.plusMonths(1L)
        }) {
            Icon(Icons.Default.KeyboardArrowRight, "next")
        }
    }
}
