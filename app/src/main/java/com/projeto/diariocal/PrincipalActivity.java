package com.projeto.diariocal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    private ListView listViewDiario;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listViewDiario = findViewById(R.id.listViewDiario);

        listViewDiario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nome = (String) listViewDiario.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        nome + " foi clicado!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        popularLista();
    }

    private void popularLista() {
        ArrayList<String> opcoesMenu = new ArrayList<>();

        opcoesMenu.add(getString(R.string.cad_categoria_alimento));
        opcoesMenu.add(getString(R.string.cad_alimento));
        opcoesMenu.add(getString(R.string.cad_refeicao));
        opcoesMenu.add(getString(R.string.cad_diario));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        opcoesMenu);

        listViewDiario.setAdapter(adapter);



    }
}