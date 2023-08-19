package com.projeto.diariocal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ListView listViewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listViewMenu = findViewById(R.id.listViewMenu);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String opcao = (String) listViewMenu.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        opcao + " foi clicado!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        popularLista();
    }

    private void popularLista() {
        ArrayList<String> opcoesMenu = new ArrayList<>();
        opcoesMenu.add(getString(R.string.cadastrar_categoria_alimento));
        opcoesMenu.add(getString(R.string.cadastrar_alimento));
        opcoesMenu.add(getString(R.string.cadastrar_refeicao));
        opcoesMenu.add(getString(R.string.cadastrar_diario));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,opcoesMenu);

        listViewMenu.setAdapter(adapter);

    }
}