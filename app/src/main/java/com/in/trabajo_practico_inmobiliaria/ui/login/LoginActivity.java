package com.in.trabajo_practico_inmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.in.trabajo_practico_inmobiliaria.MainActivity;
import com.in.trabajo_practico_inmobiliaria.R;
import com.in.trabajo_practico_inmobiliaria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding b;
private LoginViewModel mv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        setContentView(b.getRoot());
        b.btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = b.edemail.getText().toString();
                String clave = b.edclave.getText().toString();

                mv.recuperarDatos(email,clave);
                /*
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("DESTINO", "nav_inicio");
                startActivity(intent);
                finish();
                */

            }
        });
    }
}