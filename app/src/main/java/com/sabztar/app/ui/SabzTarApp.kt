package com.sabztar.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection

private val Green = Color(0xFF2E8B57)
private val LightGreen = Color(0xFFEAF6EE)
private val Bg = Color(0xFFF7FBF7)

data class Food(
    val name: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
)

private val foods = listOf(
    Food("برنج پخته", 130, 3, 28, 0),
    Food("سینه مرغ گریل", 165, 31, 0, 4),
    Food("تخم‌مرغ", 78, 6, 1, 5),
    Food("نان سنگک", 250, 8, 50, 2),
    Food("ماست کم‌چرب", 63, 5, 7, 2),
    Food("سیب", 52, 0, 14, 0),
    Food("موز", 89, 1, 23, 0),
    Food("عدسی", 116, 9, 20, 0),
    Food("قورمه‌سبزی", 180, 12, 10, 10),
    Food("کباب کوبیده", 220, 19, 2, 15)
)

@Composable
fun SabzTarApp() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        var tab by remember { mutableIntStateOf(0) }
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = Green,
                secondary = Green,
                background = Bg,
                surface = Color.White
            )
        ) {
            Scaffold(
                containerColor = Bg,
                bottomBar = {
                    NavigationBar(containerColor = Color.White) {
                        NavigationBarItem(
                            selected = tab == 0,
                            onClick = { tab = 0 },
                            icon = { Icon(Icons.Default.Home, null) },
                            label = { Text("خانه") }
                        )
                        NavigationBarItem(
                            selected = tab == 1,
                            onClick = { tab = 1 },
                            icon = { Icon(Icons.Default.Restaurant, null) },
                            label = { Text("غذا") }
                        )
                        NavigationBarItem(
                            selected = tab == 2,
                            onClick = { tab = 2 },
                            icon = { Icon(Icons.Default.MonitorWeight, null) },
                            label = { Text("وزن") }
                        )
                        NavigationBarItem(
                            selected = tab == 3,
                            onClick = { tab = 3 },
                            icon = { Icon(Icons.Default.Person, null) },
                            label = { Text("پروفایل") }
                        )
                    }
                }
            ) { padding ->
                Box(Modifier.padding(padding)) {
                    when (tab) {
                        0 -> HomeScreen()
                        1 -> FoodScreen()
                        2 -> WeightScreen()
                        3 -> ProfileScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun ScreenTitle(title: String, subtitle: String? = null) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp)) {
        Text(title, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F3A2C))
        if (subtitle != null) {
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
private fun HomeScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Bg),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { ScreenTitle("سبزتر", "تغذیه سالم، زندگی بهتر") }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("کالری امروز", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("۱۱۵۰", fontSize = 42.sp, color = Green, fontWeight = FontWeight.Black)
                    Text("از ۱۸۰۰ کالری", color = Color.Gray)
                    Spacer(Modifier.height(16.dp))
                    LinearProgressIndicator(
                        progress = { 0.64f },
                        modifier = Modifier.fillMaxWidth().height(12.dp).clip(RoundedCornerShape(20.dp)),
                        color = Green,
                        trackColor = LightGreen
                    )
                }
            }
        }
        item {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MacroCard("پروتئین", "۷۵ گرم", Modifier.weight(1f))
                MacroCard("کربوهیدرات", "۱۳۰ گرم", Modifier.weight(1f))
                MacroCard("چربی", "۴۰ گرم", Modifier.weight(1f))
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = LightGreen),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("آب امروز", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("۴ از ۸ لیوان", fontSize = 24.sp, color = Green, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    LinearProgressIndicator(progress = { 0.5f }, modifier = Modifier.fillMaxWidth())
                }
            }
        }
        item { SectionTitle("وعده‌های امروز") }
        items(listOf("صبحانه", "ناهار", "شام", "میان‌وعده")) { meal ->
            MealCard(meal)
        }
    }
}

@Composable
private fun MacroCard(title: String, value: String, modifier: Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(6.dp))
            Text(value, fontWeight = FontWeight.Bold, color = Color(0xFF1F3A2C))
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun MealCard(name: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.RestaurantMenu, null, tint = Green)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Text("هنوز چیزی ثبت نشده", color = Color.Gray, fontSize = 13.sp)
            }
            Icon(Icons.Default.AddCircle, null, tint = Green)
        }
    }
}

@Composable
private fun FoodScreen() {
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Food?>(null) }

    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenTitle("ثبت غذا", "غذا را جستجو و به وعده اضافه کن")
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            leadingIcon = { Icon(Icons.Default.Search, null) },
            placeholder = { Text("مثلاً برنج، مرغ، سیب...") },
            shape = RoundedCornerShape(18.dp)
        )
        Spacer(Modifier.height(10.dp))
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(bottom = 16.dp)) {
            items(foods.filter { it.name.contains(query, ignoreCase = true) }) { food ->
                Card(
                    onClick = { selected = food },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(food.name, fontWeight = FontWeight.Bold)
                            Text("${food.calories} کالری در ۱۰۰ گرم", color = Color.Gray, fontSize = 13.sp)
                        }
                        Icon(Icons.Default.ChevronLeft, null)
                    }
                }
            }
        }
    }

    if (selected != null) {
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(selected!!.name) },
            text = {
                Column {
                    Text("کالری: ${selected!!.calories}")
                    Text("پروتئین: ${selected!!.protein} گرم")
                    Text("کربوهیدرات: ${selected!!.carbs} گرم")
                    Text("چربی: ${selected!!.fat} گرم")
                }
            },
            confirmButton = {
                Button(onClick = { selected = null }) { Text("افزودن") }
            },
            dismissButton = {
                TextButton(onClick = { selected = null }) { Text("بستن") }
            }
        )
    }
}

@Composable
private fun WeightScreen() {
    var weight by remember { mutableStateOf("78.5") }
    val entries = remember { mutableStateListOf("۷۹.۸", "۷۹.۲", "۷۸.۹", "۷۸.۵") }

    LazyColumn(Modifier.fillMaxSize().background(Bg)) {
        item { ScreenTitle("ثبت وزن", "روند تغییرات وزنت را دنبال کن") }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("وزن (کیلوگرم)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = { if (weight.isNotBlank()) entries.add(weight) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ثبت وزن")
                    }
                }
            }
        }
        item { SectionTitle("آخرین ثبت‌ها") }
        items(entries.reversed()) { w ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MonitorWeight, null, tint = Green)
                    Spacer(Modifier.width(12.dp))
                    Text("$w کیلوگرم", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ProfileScreen() {
    var age by remember { mutableStateOf("۲۸") }
    var height by remember { mutableStateOf("۱۷۵") }
    var currentWeight by remember { mutableStateOf("۷۸.۵") }
    var targetWeight by remember { mutableStateOf("۷۲") }

    LazyColumn(Modifier.fillMaxSize().background(Bg)) {
        item { ScreenTitle("پروفایل", "تنظیمات هدف و اطلاعات بدنی") }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(22.dp)
            ) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ProfileField("سن", age) { age = it }
                    ProfileField("قد (سانتی‌متر)", height) { height = it }
                    ProfileField("وزن فعلی", currentWeight) { currentWeight = it }
                    ProfileField("وزن هدف", targetWeight) { targetWeight = it }
                    Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                        Text("ذخیره تغییرات")
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = LightGreen),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("هدف روزانه پیشنهادی", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("۱۸۰۰ کالری", fontSize = 28.sp, color = Green, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ProfileField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true
    )
}
