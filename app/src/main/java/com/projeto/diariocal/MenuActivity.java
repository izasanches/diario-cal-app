package com.projeto.diariocal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ListView listViewAlimento;
    private ArrayList<Alimento> alimentos;
    private AlimentoAdapter listaAdapter;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemAdicionar) {
            adicionarAlimento();
            return true;
        }

        else if (itemId == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
        AlimentoActivity.novoAlimento(this);
    }

    public void adicionarAlimento() {
        AlimentoActivity.novoAlimento(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            String nome = bundle.getString(AlimentoActivity.NOME);
            double quantidadeCal = Double.parseDouble(bundle.getString(AlimentoActivity.QUANTIDADE_CAL));
            UnidadeMedida[] unidadeMedidas = UnidadeMedida.values();
            UnidadeMedida unidadeMedida = unidadeMedidas[bundle.getInt(AlimentoActivity.UNIDADE_MEDIDA)];
            String categoria = bundle.getString(AlimentoActivity.CATEGORIA);
            boolean alimentoFresco = bundle.getBoolean(AlimentoActivity.ALIMENTO_FRESCO);


            Alimento alimento = new Alimento(nome, quantidadeCal, unidadeMedida,
                    categoria, alimentoFresco);

            alimentos.add(alimento);


            listaAdapter.notifyDataSetChanged();
        }
    }

    public void abrirSobre(View view){
        SobreActivity.sobre(this);
    }

    public void abrirSobre(){
        SobreActivity.sobre(this);
    }
}