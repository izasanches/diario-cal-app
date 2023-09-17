package com.projeto.diariocal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.projeto.diariocal.persistencia.AlimentosDatabase;

public class AlimentoActivity extends AppCompatActivity {

    private EditText editTextNome, editTextQuantidade;
    private CheckBox cbAlimentoFresco;
    private RadioGroup radioGroupUnidadeMedida;
    private Spinner spinnerCategoria;
    public static final String NOME = "NOME";
    public static final String QUANTIDADE_CAL = "QUANTIDADE_CAL";
    public static final String UNIDADE_MEDIDA = "UNIDADE_MEDIDA";
    public static final String CATEGORIA = "CATEGORIA";
    public static final String ALIMENTO_FRESCO = "ALIMENTO_FRESCO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    public static final String MODO = "MODO";
    public static final String ID = "ID";
    private static int id = 0;
    private int modo;
    private String categoriaOriginal;
    private int unidadeMedidaOriginal;
    private Alimento alimento;

    public static void novoAlimento(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AlimentoActivity.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarAlimento(AppCompatActivity activity, Alimento alimento) {
        Intent intent = new Intent(activity, AlimentoActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, alimento.getId());
        id = alimento.getId();

        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemSalvar) {
            salvar();
            return true;
        } else if (itemId == R.id.menuItemLimpar) {
            limparCampos();
            return true;
        } else if (itemId == android.R.id.home) {
            cancelar();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alimento_opcoes, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        cbAlimentoFresco = findViewById(R.id.checkBoxAliFresco);
        radioGroupUnidadeMedida = findViewById(R.id.rgUnidadeMedida);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle(getString(R.string.cadastro_alimento));
                alimento = new Alimento("", 0.0, 0, "", false);
            } else {

                setTitle(getString(R.string.edicao_alimento));

                int id = bundle.getInt(ID);

                AlimentosDatabase database = AlimentosDatabase.getDatabase(AlimentoActivity.this);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        alimento = database.alimentoDao().queryForId(id);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                editTextNome.setText(alimento.getNome());

                                editTextQuantidade.setText(alimento.getQuantidadeCal() + "");

                                unidadeMedidaOriginal = alimento.getUnidadeMedida();

                                if (unidadeMedidaOriginal == 0/*UnidadeMedida.GRAMA.value*/)
                                    radioGroupUnidadeMedida.check(R.id.radioButtonGramas);
                                else
                                    radioGroupUnidadeMedida.check(R.id.radioButtonLitros);

                                categoriaOriginal = alimento.getCategoria();
                                int posicaoSpinner = -1;

                                for (int i = 0; i < spinnerCategoria.getCount(); i++) {
                                    String categTexto = spinnerCategoria.getItemAtPosition(i).toString();

                                    if (categTexto.equals(categoriaOriginal)) {
                                        posicaoSpinner = i;
                                        break;
                                    }
                                }
                                spinnerCategoria.setSelection(posicaoSpinner);

                                cbAlimentoFresco.setChecked(alimento.isAlimentoFresco());
                            }
                        });
                    }
                }).start();
            }
        }
    }

    public void limparCampos() {
        editTextNome.setText(null);
        editTextQuantidade.setText(null);
        cbAlimentoFresco.setChecked(false);
        radioGroupUnidadeMedida.clearCheck();
        spinnerCategoria.setSelection(0);

        editTextNome.requestFocus();

        Toast.makeText(this,
                R.string.campos_limpos,
                Toast.LENGTH_SHORT).show();
    }

    public void salvar(View view) {
        String nome = editTextNome.getText().toString();
        String quantidade = editTextQuantidade.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_nome, Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return;
        }

        if (quantidade == null || quantidade.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_quantidade, Toast.LENGTH_SHORT).show();
            editTextQuantidade.requestFocus();
            return;
        }

        if (radioGroupUnidadeMedida.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.erro_unidade_medida, Toast.LENGTH_SHORT).show();
            radioGroupUnidadeMedida.requestFocus();
            return;
        }

        int unidadeMedida;

        if (radioGroupUnidadeMedida.getCheckedRadioButtonId() == R.id.radioButtonGramas)
            unidadeMedida = 0;
        else
            unidadeMedida = 1;

        String categoria = (String) spinnerCategoria.getSelectedItem();

        if (categoria == null) {
            Toast.makeText(this,
                    getString(R.string.erro_categoria),
                    Toast.LENGTH_SHORT).show();
            spinnerCategoria.requestFocus();
            return;
        }

        boolean alimentoFresco = cbAlimentoFresco.isChecked();

        Intent intent = new Intent();
        intent.putExtra(NOME, nome);
        intent.putExtra(QUANTIDADE_CAL, quantidade);
        intent.putExtra(UNIDADE_MEDIDA, unidadeMedida);
        intent.putExtra(CATEGORIA, categoria);
        intent.putExtra(ALIMENTO_FRESCO, alimentoFresco);

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    public void salvar() {
        String nome = editTextNome.getText().toString();
        String quantidade = editTextQuantidade.getText().toString();

        if (nome == null || nome.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_nome, Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return;
        }

        if (quantidade == null || quantidade.trim().isEmpty()) {
            Toast.makeText(this, R.string.erro_quantidade, Toast.LENGTH_SHORT).show();
            editTextQuantidade.requestFocus();
            return;
        }

        if (radioGroupUnidadeMedida.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.erro_unidade_medida, Toast.LENGTH_SHORT).show();
            radioGroupUnidadeMedida.requestFocus();
            return;
        }

        int unidadeMedida;

        if (radioGroupUnidadeMedida.getCheckedRadioButtonId() == R.id.radioButtonGramas)
            unidadeMedida = UnidadeMedida.GRAMA.value;
        else
            unidadeMedida = UnidadeMedida.MILILITRO.value;

        String categoria = (String) spinnerCategoria.getSelectedItem();

        if (categoria == null) {
            Toast.makeText(this,
                    getString(R.string.erro_categoria),
                    Toast.LENGTH_SHORT).show();
            spinnerCategoria.requestFocus();
            return;
        }

        boolean alimentoFresco = cbAlimentoFresco.isChecked();

        Intent intent = new Intent();
        intent.putExtra(NOME, nome);
        intent.putExtra(QUANTIDADE_CAL, quantidade);
        intent.putExtra(UNIDADE_MEDIDA, unidadeMedida);
        intent.putExtra(CATEGORIA, categoria);
        intent.putExtra(ALIMENTO_FRESCO, alimentoFresco);
        intent.putExtra(ID, id);

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    public void cancelar(View view) {
        onBackPressed();
    }

    public void cancelar() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}