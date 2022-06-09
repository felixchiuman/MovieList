package com.felix.movielist.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felix.movielist.R
import com.felix.movielist.ui.theme.MovieListTheme
import com.felix.movielist.ui.theme.NavyBlue
import com.felix.movielist.ui.theme.Orange
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : ComponentActivity() {
    private val auth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        LoginScreen()
                        InputForm(auth)
                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .background(color = NavyBlue)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_rafiki),
            contentDescription = "Login Screen",
            modifier = Modifier.size(256.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "Process With Your",
            fontFamily = FontFamily.Companion.SansSerif,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp)
        )

        Text(
            text = "Sign In",
            fontFamily = FontFamily.Companion.SansSerif,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun InputForm(auth: FirebaseAuth) {

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val isEmailValid by derivedStateOf {
        Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .background(color = NavyBlue)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "emailIcon",
                    tint = Color.White
                )
            },
            onValueChange = { email = it },
            label = { Text(text = "Email", color = Color.White) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                textColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            isError = !isEmailValid,
            trailingIcon = {
                if (email.isNotBlank()) {
                    IconButton(onClick = { email = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear email"
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "emailIcon",
                    tint = Color.White
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.clearFocus() }
            ),
            onValueChange = { password = it },
            label = { Text(text = "Password", color = Color.White) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                textColor = Color.White
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "toggle pass visibility",
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .background(NavyBlue)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { 
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(context, "Email Sent !!", Toast.LENGTH_SHORT).show()
                    }
                }
        }) {
            Text(
                fontSize = 16.sp,
                text = "Forget Password?",
                color = Color.White,
                fontFamily = FontFamily.Companion.SansSerif,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .background(color = NavyBlue)
            .fillMaxSize()
            .background(NavyBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            context.startActivity(Intent(context,MainPageActivity::class.java))
                        } else {
                            Toast.makeText(context, "Sign In Error", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(Orange),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = "Sign In",
                fontFamily = FontFamily.Companion.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }
        TextButton(onClick = {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }, enabled = true) {
            Text(
                fontSize = 16.sp,
                text = "Don't have an account? Sign Up",
                color = Color.White,
                fontFamily = FontFamily.Companion.SansSerif
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    MovieListTheme {
//        Column {
//            LoginScreen()
//            InputForm(Firebase.auth)
//        }
//    }
//}