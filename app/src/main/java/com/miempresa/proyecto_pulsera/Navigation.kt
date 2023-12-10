package com.miempresa.proyecto_pulsera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings


@Composable
fun Screen1(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF08FF00), Color(0xFF00F7FF))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Secure Child",
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Image(
            painter = painterResource(id = R.drawable.bracelet),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Button(
            onClick = {
                navController.navigate("pantalla2")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF)) // Degradado de verde a naranja
                    )
                )
        ) {
            Text(
                text = "Comenzar",
                color = Color.White
            )
        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen2(navController: NavHostController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf(Option.LogIn) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.portada2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OptionButton(
                text = "Log In",
                isSelected = selectedOption == Option.LogIn,
                onClick = { selectedOption = Option.LogIn }
            )
            OptionButton(
                text = "Sign Up",
                isSelected = selectedOption == Option.SignUp,
                onClick = { selectedOption = Option.SignUp }
            )
        }

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Ingrese correo o número de teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        if (showError) {
            Text(
                text = "Por favor, complete todos los campos.",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "Olvidó su contraseña",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Implementa la lógica aquí */ }
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                if (correo.isEmpty() || contrasena.isEmpty()) {
                    // Campos vacíos, mostrar mensaje de error
                    showError = true
                } else {
                    showError = false
                    navController.navigate("pantalla3/.")
                    when (selectedOption) {
                        Option.LogIn -> {
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, contrasena)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Autenticación exitosa
                                    } else {
                                        // Manejar error de autenticación
                                    }
                                }
                        }
                        Option.SignUp -> {
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contrasena)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // Cuenta creada exitosamente
                                    } else {
                                        // Manejar error de creación de cuenta
                                    }
                                }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                    )
                )
        ) {
            Text(
                text = when (selectedOption) {
                    Option.LogIn -> "INICIAR SESIÓN"
                    Option.SignUp -> "REGISTRARSE"
                },
                color = Color.White
            )
        }
    }
}

enum class Option {
    LogIn, SignUp
}

@Composable
fun OptionButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(if (isSelected) Color(0xFF00BFFF) else Color.White, CircleShape)
            .border(1.dp, Color.Gray, CircleShape)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}











@Composable
fun Screen3(navController: NavHostController, texto: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(id = R.drawable.telefono),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Mantente al tanto de tu ser querido desde tu celular",
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("pantalla2")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Prev",
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    navController.navigate("pantalla4")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Next",
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    navController.navigate("pantalla7")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Omitir",
                    color = Color.White
                )
            }
        }
    }
}








@Composable
fun Screen4(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(id = R.drawable.campana1),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Recibirás notificaciones de alerta",
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("pantalla3/.")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Prev",
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    navController.navigate("pantalla5")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Next",
                    color = Color.White
                )
            }
        }
    }
}







@Composable
fun Screen5(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(id = R.drawable.campana2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Te mantendremos alerta",
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    navController.navigate("pantalla4")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Prev",
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    navController.navigate("pantalla6")
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(60.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                        )
                    )
            ) {
                Text(
                    text = "Next",
                    color = Color.White
                )
            }
        }
    }
}






@Composable
fun Screen6(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "CONSEJO",
            color = Color(0xFF04D9FF),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Puedes usar esta aplicación para contactarte con nosotros en el caso de telefono en el caso de que tengas dudas",
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            // Círculo con icono de teléfono
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF04D9FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = {
                navController.navigate("pantalla7")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
                    )
                )
        ) {
            Text(
                text = "FINALIZAR",
                color = Color.White
            )
        }
    }
}







@Composable
fun Screen7(navController: NavHostController) {
    var isPowerOn by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF87CEEB), CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Color.White)
            }

            IconButton(
                onClick = {
                    navController.navigate("pantalla8")
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF87CEEB), CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(Color.Gray, RoundedCornerShape(16.dp))
                    .clickable {
                        isPowerOn = !isPowerOn
                    }
                    .padding(40.dp)
            ) {
                Image(
                    painter = painterResource(id = if (isPowerOn) R.drawable.power2 else R.drawable.power1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "00:00:00",
                color = Color.Black,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.audifonos),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "¿Tienes alguna duda?",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Puedes contactarte con nosotros aquí.",
                        color = Color.Gray,
                        maxLines = 2
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.phone),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .background(Color.White, CircleShape)
                .padding(bottom = 16.dp)
                .align(Alignment.End)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    navController.navigate("pantalla1")
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = {
                    navController.navigate("pantalla9")
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = { navController.navigate("pantalla10")},
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = {navController.navigate("pantalla11") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.Black)
            }
        }
    }
}






@Composable
fun Screen8(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate("pantalla7") },
                modifier = Modifier
                    .size(55.dp)
                    .background(Color.Transparent)
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = Color.Black)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .background(Color.Gray, RoundedCornerShape(16.dp))
                    .clickable { /* Implementa la lógica aquí */ }
                    .padding(40.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .drawBehind {
                            drawCircle(color = Color(0xFF87CEEB), center = center, radius = size.width / 2f)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(160.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            EditableField("Tu nombre", name, { name = it }, Color(0xFF87CEEB))
            EditableField("Correo", email, { email = it }, Color(0xFF87CEEB))
            EditableField("Número", number, { number = it }, Color(0xFF87CEEB))
        }
    }

    // Crear una instancia de FirebaseFirestore
    val db = FirebaseFirestore.getInstance()

    // En el lugar apropiado, por ejemplo, cuando el usuario hace clic en un botón para guardar datos
    db.collection("usuarios").add(
        mapOf(
            "nombre" to name,
            "correo" to email,
            "numero" to number
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableField(label: String, value: String, onValueChange: (String) -> Unit, borderColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label, color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF87CEEB),
                unfocusedBorderColor = borderColor
            )
        )
    }
}











@Composable
fun Screen9(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate("pantalla7") },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF87CEEB), CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Color.White)
            }

            IconButton(
                onClick = {navController.navigate("pantalla8") },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF87CEEB), CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(48.dp)
                .clickable { navController.navigate("pantalla7") }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Por el momento no tienes ninguna notificación ;)",
            color = Color.Gray.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 25.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate("pantalla7")},
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = {navController.navigate("pantalla9") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = { navController.navigate("pantalla10")},
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = {navController.navigate("pantalla11") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.Black)
            }
        }
    }
}






@Composable
fun Screen10(navController: NavHostController) {
    var uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.SATELLITE)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = properties,
            uiSettings = uiSettings
        )

        Switch(
            checked = uiSettings.zoomControlsEnabled,
            onCheckedChange = {
                uiSettings = uiSettings.copy(zoomControlsEnabled = it)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}











@Composable
fun Screen11(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate("pantalla7") },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF87CEEB), CircleShape)
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = Color.White)
            }

            IconButton(
                onClick = { navController.navigate("pantalla8") },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFF87CEEB), CircleShape)
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Estado",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.emoji),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF87CEEB), RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Alerta de Seguridad! \uD83D\uDE31 Tu ser querido se está alejando. Verifica su ubicación y asegúrate de que esté seguro. Si necesitas asistencia, toca aquí para obtener ayuda inmediata. ¡Priorizamos tu tranquilidad y conexión emocional! \uD83C\uDF1F",
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate("pantalla7") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = { navController.navigate("pantalla9") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = { navController.navigate("pantalla10") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.Black)
            }

            IconButton(
                onClick = { navController.navigate("pantalla11") },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.Black)
            }
        }
    }
}
