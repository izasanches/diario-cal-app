package com.projeto.diariocal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.projeto.diariocal.persistencia.AlimentosDatabase;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private static final String ARQUIVO = "com.projeto.diariocal.PREFERENCIAS_CONFIGURACOES";
    private static final String TEMA = "TEMA";
    private int opcaoTema = AppCompatDelegate.MODE_NIGHT_NO;
    private ListView listViewAlimento;
    private AlimentoAdapter listaAdapter;
    private View viewSelecionada;
    private ActionMode actionMode;
    private ConstraintLayout layout;
    private int posicaoSelecionada = -1;
    private int id = 0;

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

            Alimento alimento = (Alimento) listViewAlimento.getItemAtPosition(posicaoSelecionada);
            id = alimento.getId();
            int itemId = item.getItemId();

            if (itemId == R.id.menuItemAlterar) {
                alterarAlimento(alimento);
                mode.finish();
                return true;
            } else if (itemId == R.id.menuItemExcluir) {
                excluirAlimento(alimento);
                mode.finish();
                return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode = null;
            viewSelecionada = null;
            listViewAlimento.setEnabled(true);
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemAdicionar) {
            adicionarAlimento();
            return true;
        } else if (itemId == R.id.menuItemSobre) {
            abrirSobre();
            return true;
        } else if (itemId == R.id.menuItemClaro) {
            item.setChecked(true);
            salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        } else if (itemId == R.id.menuItemEscuro) {
            item.setChecked(true);
            salvarPreferenciaTema(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        } else {
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

                Alimento alimento = (Alimento) parent.getItemAtPosition(position);

                alterarAlimento(alimento);
            }
        });

        listViewAlimento.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewAlimento.setOnItemLongClickListener((parent, view, position, id) -> {
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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AlimentosDatabase database = AlimentosDatabase.getDatabase(MenuActivity.this);
                List<Alimento> lista = database.alimentoDao().queryForAll();

                MenuActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listaAdapter = new AlimentoAdapter(MenuActivity.this, lista);

                        listViewAlimento.setAdapter(listaAdapter);
                    }
                });

            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            String nome = bundle.getString(AlimentoActivity.NOME);
            double quantidadeCal = Double.parseDouble(bundle.getString(AlimentoActivity.QUANTIDADE_CAL));
            int unidadeMedida = bundle.getInt(AlimentoActivity.UNIDADE_MEDIDA);
            String categoria = bundle.getString(AlimentoActivity.CATEGORIA);
            boolean alimentoFresco = bundle.getBoolean(AlimentoActivity.ALIMENTO_FRESCO);
            int id = bundle.getInt(AlimentoActivity.ID);

            AlimentosDatabase database = AlimentosDatabase.getDatabase(this);

            if (requestCode == AlimentoActivity.ALTERAR) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Alimento alimento = database.alimentoDao().queryForId(id);
                        alimento.setNome(nome);
                        alimento.setQuantidadeCal(quantidadeCal);
                        alimento.setUnidadeMedida(unidadeMedida);
                        alimento.setCategoria(categoria);
                        alimento.setAlimentoFresco(alimentoFresco);

                        database.alimentoDao().update(alimento);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        popularLista();
                    }
                }.execute();


            } else {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        Alimento alimento = new Alimento(nome, quantidadeCal, unidadeMedida, categoria, alimentoFresco);

                        database.alimentoDao().insert(alimento);
                        listaAdapter.notifyDataSetChanged();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {

                        popularLista();
                    }
                }.execute();

            }

            listaAdapter.notifyDataSetChanged();
        }
    }

    private void excluirAlimento(@NonNull final Alimento alimento) {

        String mensagem = getString(R.string.deseja_realmente_apagar) + "\n" + alimento.getNome();

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:


                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                AlimentosDatabase database = AlimentosDatabase.getDatabase(MenuActivity.this);

                                database.alimentoDao().delete(alimento);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {

                                popularLista();
                            }
                        }.execute();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        confirmaAcao(this, mensagem, listener);
    }

    public static void confirmaAcao(Context contexto, String mensagem, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.confirmacao);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton(R.string.sim, listener);
        builder.setNegativeButton(R.string.nao, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void alterarAlimento(Alimento alimento) {
        AlimentoActivity.alterarAlimento(this, alimento);
    }

    public void abrirSobre() {
        SobreActivity.sobre(this);
    }
}