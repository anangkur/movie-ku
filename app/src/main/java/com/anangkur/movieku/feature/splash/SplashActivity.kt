package com.anangkur.movieku.feature.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anangkur.movieku.R
import com.anangkur.movieku.feature.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.fillMaxWidth().fillMaxSize()) {
                Column(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(R.drawable.ic_movie_active),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.app_name),
                        fontSize = 28.sp,
                        fontFamily = FontFamily(fonts = listOf(Font(R.font.comfortaa_bold)))
                    )
                }
            }
        }

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}