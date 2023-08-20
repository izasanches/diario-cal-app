package com.projeto.diariocal;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AlimentoActivity extends AppCompatActivity {

    private ListView listViewAlimento;
    private ArrayList<Alimento> alimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);

        listViewAlimento = findViewById(R.id.listViewAlimento);

        listViewAlimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Alimento alimento = (Alimento) listViewAlimento.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        getString(R.string.alimento_nome)
                                + alimento.getNome() + getString(R.string.foi_clicado),
                        Toast.LENGTH_SHORT).show();
            }
        });

        popularLista();
    }

    private void popularLista() {

        String[] nomes = getResources().getStringArray(R.array.alimento_nomes);
        String[] calorias = getResources().getStringArray(R.array.calorias);
        int[] unidadeMedida = getResources().getIntArray(R.array.unidade_medida);
        String[] categoriaAlimento = getResources().getStringArray(R.array.categoria_alimento);
        int[] alimentoFresco = getResources().getIntArray(R.array.alimento_fresco);

        alimentos = new ArrayList<>();
        UnidadeMedida[] unidadeMedidas = UnidadeMedida.values();

        for (int cont = 0; cont < nomes.length; cont++) {
            alimentos.add(new Alimento(nomes[cont],
                    Double.parseDouble(calorias[cont]),
                    unidadeMedidas[unidadeMedida[cont]],
                    categoriaAlimento[cont],
                    alimentoFresco[cont] == 0 ? false : true));
        }

        AlimentoAdapter alimentoAdapter = new AlimentoAdapter(this, alimentos);
        listViewAlimento.setAdapter(alimentoAdapter);
    }
}