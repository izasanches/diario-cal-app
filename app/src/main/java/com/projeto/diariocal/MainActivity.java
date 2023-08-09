package com.projeto.diariocal;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextQuantidade;
    private CheckBox cbAlimentoFresco;
    private RadioGroup radioGroupUnidadeMedida;
    private Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        cbAlimentoFresco = findViewById(R.id.checkBoxAliFresco);
        radioGroupUnidadeMedida = findViewById(R.id.rgUnidadeMedida);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
    }

    private void popularSpinner() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add("Frutas");
        lista.add("Legumes");
        lista.add("Carne");
        lista.add("Laticínios");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                                            android.R.layout.simple_list_item_1,
                                                            lista);
        spinnerCategoria.setAdapter(adapter);
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

        if (spinnerCategoria.getSelectedItemPosition() < 0) {
            Toast.makeText(this, R.string.erro_categoria, Toast.LENGTH_SHORT).show();
            spinnerCategoria.requestFocus();
            return;
        }

    }
}