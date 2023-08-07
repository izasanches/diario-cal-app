package com.projeto.diariocal;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextQuantidade;
    private CheckBox cbAlimentoFresco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextQuantidade = findViewById(R.id.editTextQuantidade);
        cbAlimentoFresco = findViewById(R.id.checkBoxAliFresco);
    }

    public void limparCampos(View view){
        editTextNome.setText(null);
        editTextQuantidade.setText(null);
        cbAlimentoFresco.setChecked(false);

        editTextNome.requestFocus();

        Toast.makeText(this,
                              R.string.campos_limpos,
                              Toast.LENGTH_SHORT).show();
    }

    public void resumoAlimento(View view){
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

        Toast.makeText(this,
                          nome.trim() + " " + quantidade.trim(),
                            Toast.LENGTH_SHORT).show();

    }
}