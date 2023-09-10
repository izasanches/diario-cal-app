package com.projeto.diariocal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private static final String ARQUIVO = "com.projeto.diariocal.PREFERENCIAS_CONFIGURACOES";
    private static final String TEMA = "TEMA";

    private int opcaoTema = AppCompatDelegate.MODE_NIGHT_NO;

    private ListView listViewAlimento;
    private ArrayList<Alimento> alimentos;
    private AlimentoAdapter listaAdapter;
    private int posicaoSelecionada = -1;
    private View viewSelecionada;
    private ActionMode actionMode;

    private ConstraintLayout layout;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.principal_alimento_selecionado, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int itemId = item.getItemId();

            if (itemId == R.id.menuItemAlterar) {
                alterarAlimento();
                mode.finish();
                return true;
            } else if (itemId == R.id.menuItemExcluir) {
                excluirAlimento();
                mode.finish();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode         = null;
            viewSelecionada    = null;
            listViewAlimento.setEnabled(true);
        }
    };

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

        else if (itemId == R.id.menuItemClaro){
            item.setChecked(true);
            salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        }

        else if (itemId == R.id.menuItemEscuro){
            item.setChecked(true);
            salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item;

        if (opcaoTema == AppCompatDelegate.MODE_NIGHT_NO) {
            item = menu.findItem(R.id.menuItemClaro);
            item.setChecked(true);
            return true;
        } else if (opcaoTema == AppCompatDelegate.MODE_NIGHT_YES) {
            item = menu.findItem(R.id.menuItemEscuro);
            item.setChecked(true);
            return true;
        }

        return false;
    }

    public void lerPreferenciaTema() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        opcaoTema = shared.getInt(TEMA, opcaoTema);

        mudarTema();
    }

    private void mudarTema() {
        AppCompatDelegate.setDefaultNightMode(opcaoTema);
    }

    private void salvarPreferenciaTema(int tema) {
        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();

        editor.putInt(TEMA, tema);

        editor.commit();

        opcaoTema = tema;

        mudarTema();

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listViewAlimento = findViewById(R.id.listViewAlimento);

        listViewAlimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicaoSelecionada = position;
                alterarAlimento();
            }
        });

        listViewAlimento.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewAlimento.setOnItemLongClickListener(
                (parent, view, position, id) -> {
                    if (actionMode != null) {
                        return false;
                    }
                    posicaoSelecionada = position;

                    view.setBackgroundColor(Color.LTGRAY);

                    viewSelecionada = view;

                    listViewAlimento.setEnabled(false);

                    actionMode = startSupportActionMode(mActionModeCallback);

                    return true;
                }

        );
        popularLista();

        layout = findViewById(R.id.layoutPrincipal);
        lerPreferenciaTema();
    }

    private void popularLista() {
        alimentos = new ArrayList<>();

        listaAdapter = new AlimentoAdapter(this, alimentos);
        listViewAlimento.setAdapter(listaAdapter);
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

            if (requestCode == AlimentoActivity.ALTERAR) {
                Alimento alimento = alimentos.get(posicaoSelecionada);
                alimento.setNome(nome);
                alimento.setQuantidadeCal(quantidadeCal);
                alimento.setUnidadeMedida(unidadeMedida);
                alimento.setCategoria(categoria);
                alimento.setAlimentoFresco(alimentoFresco);

                posicaoSelecionada = -1;
            } else {
                Alimento alimento = new Alimento(nome, quantidadeCal, unidadeMedida,
                        categoria, alimentoFresco);

                alimentos.add(alimento);
            }

            listaAdapter.notifyDataSetChanged();
        }
    }

    private void excluirAlimento(){

        alimentos.remove(posicaoSelecionada);
        listaAdapter.notifyDataSetChanged();
    }

    private void alterarAlimento(){

        Alimento alimento = alimentos.get(posicaoSelecionada);

        AlimentoActivity.alterarAlimento(this, alimento);
    }

    public void abrirSobre(){
        SobreActivity.sobre(this);
    }
}