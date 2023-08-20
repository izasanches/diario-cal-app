package com.projeto.diariocal;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AlimentoActivity extends AppCompatActivity {

    private ListView listViewAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);

        listViewAlimento = findViewById(R.id.listViewAlimento);

       /* listViewAlimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String opcao = (String) listViewAlimento.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        opcao + " foi clicado!",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        popularLista();
    }

    private void popularLista() {

        String[] nomes = getResources().getStringArray(R.array.alimento_nomes);
        String[] calorias = getResources().getStringArray(R.array.calorias);
        String[] unidadeMedida = getResources().getStringArray(R.array.unidade_medida);
        String[] categoriaAlimento = getResources().getStringArray(R.array.categoria_alimento);
        String[] alimentoFresco = getResources().getStringArray(R.array.alimento_fresco);

        ArrayList<Alimento> alimentos = new ArrayList<>();

        for (int cont = 0; cont < nomes.length; cont++) {
            alimentos.add(new Alimento(nomes[cont],
                    Double.parseDouble(calorias[cont]),
                    unidadeMedida[cont],
                    categoriaAlimento[cont],
                    Boolean.parseBoolean(alimentoFresco[cont])));
        }

        ArrayAdapter<Alimento> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,alimentos);

        listViewAlimento.setAdapter(adapter);

    }
}