package com.projeto.diariocal;

import android.app.Activity;
import android.content.Intent;
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
    private AlimentoAdapter listaAdapter;

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

        alimentos = new ArrayList<>();

        listaAdapter = new AlimentoAdapter(this, alimentos);
        listViewAlimento.setAdapter(listaAdapter);

    }

    public void adicionarAlimento(View view) {
        MainActivity.novoAlimento(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            String nome = bundle.getString(MainActivity.NOME);
            double quantidadeCal = Double.parseDouble(bundle.getString(MainActivity.QUANTIDADE_CAL));
            UnidadeMedida[] unidadeMedidas = UnidadeMedida.values();
            UnidadeMedida unidadeMedida = unidadeMedidas[bundle.getInt(MainActivity.UNIDADE_MEDIDA)];
            String categoria = bundle.getString(MainActivity.CATEGORIA);
            boolean alimentoFresco = bundle.getBoolean(MainActivity.ALIMENTO_FRESCO);


            Alimento alimento = new Alimento(nome, quantidadeCal, unidadeMedida,
                    categoria, alimentoFresco);

            alimentos.add(alimento);


            listaAdapter.notifyDataSetChanged();
        }
    }

    public void abrirSobre(View view){
        SobreActivity.sobre(this);
    }
}