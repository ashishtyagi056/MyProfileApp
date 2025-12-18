package com.example.myprofileapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofileapp.ui.theme.MyProfileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioApp() {
    var darkTheme by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Ashish Tyagi") },
                    actions = {
                        IconButton(onClick = { darkTheme = !darkTheme }) {
                            Icon(
                                imageVector = if (darkTheme)
                                    Icons.Filled.LightMode
                                else
                                    Icons.Filled.DarkMode,
                                contentDescription = "Toggle theme"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen == Screen.Home,
                        onClick = { currentScreen = Screen.Home },
                        icon = { Icon(Icons.Filled.Home, null) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screen.Experience,
                        onClick = { currentScreen = Screen.Experience },
                        icon = { Icon(Icons.Filled.Work, null) },
                        label = { Text("Experience") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screen.Resume,
                        onClick = { currentScreen = Screen.Resume },
                        icon = { Icon(Icons.Filled.School, null) },
                        label = { Text("Resume") }
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (currentScreen) {
                    Screen.Home -> HomeScreen()
                    Screen.Experience -> ExperienceScreen()
                    Screen.Resume -> ResumeScreen()
                }
            }
        }
    }
}



// ---------------- HOME ----------------
@Composable
fun HomeScreen() {
    val scale by rememberInfiniteTransition().animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.image),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop, // 👈 VERY IMPORTANT
                    modifier = Modifier
                        .size(140.dp)
                        .scale(scale)
                        .clip(CircleShape)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text("Senior Technical Lead", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("8+ Years • Fintech • Jetpack Compose", color = MaterialTheme.colorScheme.primary)

            Spacer(Modifier.height(12.dp))

            Text(
                "Android Application Engineer with experience of building scalable, high performance fintech apps." +
                        "Expert in Jetpack Compose, Kotlin, Coroutines, and modular architecture. Strong leader who remains deeply technical," +
                        "driving UI migrations, performance optimization, and crash reduction.",
                fontSize = 14.sp
            )

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(Icons.Default.Email, null, modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:tyagiashish056@gmail.com")))
                })
                Text("LinkedIn", modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/ashish-tyagi-9a875292")))
                })
                Text("GitHub", modifier = Modifier.clickable {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com")))
                })
            }

            Spacer(Modifier.height(24.dp))
            Text("Skills", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
        }

        item {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                skillList.forEach { skill ->
                    AssistChip(
                        onClick = {},
                        label = { Text(skill) }
                    )
                }
            }
        }
    }
}



// ---------------- RESUME ----------------
@Composable
fun ResumeScreen() {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Education", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            ResumeItem("PG Diploma in Mobile Computing", "CDAC, Bengaluru • 2016")
            ResumeItem("B.Tech Computer Science", "Bharat Institute of Technology, Meerut • 2015")

            Spacer(Modifier.height(16.dp))
            Text("Awards & Honors", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            ResumeItem("Rockstar", "Paytm Money • 2021, 2024")
            ResumeItem("Hall of Fame", "Paytm Money • 2024")
            ResumeItem("Best Associate IT Consultant", "ITC Infotech • 2018")
        }
    }
}

@Composable
fun ResumeItem(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold)
        Text(subtitle, fontSize = 14.sp, color = Color.Gray)
    }
}

// ---------------- EXPERIENCE ----------------


@Composable
fun ExperienceScreen() {
    var expandedId by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(experienceList) { experience ->
            ExperienceCard(
                experience = experience,
                isExpanded = expandedId == experience.id,
                onClick = {
                    expandedId =
                        if (expandedId == experience.id) null
                        else experience.id
                }
            )
        }
    }
}

@Composable
fun ExperienceCard(
    experience: Experience,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize()
        ) {

            Text(
                text = experience.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = "${experience.company} • ${experience.duration}",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(12.dp))

            val visibleItems =
                if (isExpanded)
                    experience.responsibilities
                else
                    experience.responsibilities.take(3)

            visibleItems.forEach {
                Text(
                    text = "• $it",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (isExpanded) "Show less" else "Show more",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

sealed class Screen {
    object Home : Screen()
    object Experience : Screen()
    object Resume : Screen()
}

// ---------------- DATA ----------------
data class Experience(
    val id: Int,
    val title: String,
    val company: String,
    val duration: String,
    val responsibilities: List<String>
)

val experienceList = listOf(
    Experience(
        id = 1001,
        title = "Senior Technical Lead",
        company = "Paytm / Paytm Money",
        duration = "Feb 2020 – Apr 2025",
        responsibilities = listOf(
            "Led end-to-end migration of Paytm Money app to Jetpack Compose, reducing UI development time by 30%.",
            "Designed scalable Compose UI patterns and reusable components across trading modules.",
            "Built and optimized WebSocket infrastructure for real-time market feeds, reducing latency by 30%.",
            "Improved app stability by reducing crash rate by 35–45% using Crashlytics-driven RCA.",
            "Mentored and led a team of 5+ Android engineers, improving team velocity by 25%.",
            "Collaborated with product, backend, QA, and compliance teams to ship secure fintech features.",
            "Introduced AI-assisted development workflows using GitHub Copilot and LLM tools."
        )
    ),
    Experience(
        id = 1002,
        title = "Associate IT Consultant",
        company = "ITC Infotech",
        duration = "Feb 2020 – Apr 2025",
        responsibilities = listOf(
            "Designed and developed Android applications across publishing and digital advisory domains.",
            "Delivered apps like Glassboxx, uLibrary, and Bayer Digital Farming supporting global users.",
            "Refactored legacy codebases into modular architectures, reducing development time by 20–25%.",
            "Built reusable Android libraries (Weather, Field Profile, Cropping Calendar, P&L modules).",
            "Led and mentored a team of two developers, enabling independent delivery.",
            "Collaborated with product teams to reduce requirement ambiguity by 30%.",
            "Improved API reliability by working closely with backend microservices teams.",
            "Performed production RCA and debugging, reducing defect resolution time by ~35%."
        )
    )
)

val skillList = listOf(
    "AI Assisted Dev", "Kotlin", "Jetpack Compose", "Coroutines", "Flow", "MVVM", "WebSockets", "Clean Architecture", "Performance Optimization", "Crash Analytics", "CI/CD"
)



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyProfileAppTheme {
        PortfolioApp()
    }
}