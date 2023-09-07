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
import androidx.appcompat.app.AppCompatActivity;

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


    public static void novoAlimento(AppCompatActivity activity){
        Intent intent = new Intent(activity, AlimentoActivity.class);
        activity.startActivityForResult(intent, NOVO);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItemSalvar) {
            salvar();
            return true;
        }

        else if (itemId == R.id.menuItemCancelar) {
            cancelar();
            return true;
        }

        else {
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

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        cbAlimentoFresco = findViewById(R.id.checkBoxAliFresco);
        radioGroupUnidadeMedida = findViewById(R.id.rgUnidadeMedida);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);

        setTitle("Cadastro de alimento");
    }

    public void limparCampos(View view){
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

    public void salvar(View view){
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

        if(radioGroupUnidadeMedida.getCheckedRadioButtonId() == R.id.radioButtonGramas)
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
        intent.putExtra(NOME,  nome);
        intent.putExtra(QUANTIDADE_CAL, quantidade);
        intent.putExtra(UNIDADE_MEDIDA,  unidadeMedida);
        intent.putExtra(CATEGORIA, categoria);
        intent.putExtra(ALIMENTO_FRESCO, alimentoFresco);

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    public void salvar(){
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

        if(radioGroupUnidadeMedida.getCheckedRadioButtonId() == R.id.radioButtonGramas)
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
        intent.putExtra(NOME,  nome);
        intent.putExtra(QUANTIDADE_CAL, quantidade);
        intent.putExtra(UNIDADE_MEDIDA,  unidadeMedida);
        intent.putExtra(CATEGORIA, categoria);
        intent.putExtra(ALIMENTO_FRESCO, alimentoFresco);

        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    public void cancelar(View view){
        onBackPressed();
    }

    public void cancelar(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}