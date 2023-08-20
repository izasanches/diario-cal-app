package com.projeto.diariocal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AlimentoAdapter extends BaseAdapter {

    private Context context;
    private List<Alimento> alimentos;
    private NumberFormat numberFormat;

    private static class AlimentoHolder {
        public TextView textViewValorNome;
        public TextView textViewValorQuantidadeCal;
        public TextView textViewValorUnidadeMedida;
        public TextView textViewValorCategoria;
        public TextView textViewValorFresco;

    }

    public AlimentoAdapter(Context context, List<Alimento> alimentos) {
        this.context = context;
        this.alimentos = alimentos;
        numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
    }

    @Override
    public int getCount() {
        return alimentos.size();
    }

    @Override
    public Object getItem(int i) {
        return alimentos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlimentoHolder holder;

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_lista_alimentos, viewGroup, false);

            holder = new AlimentoHolder();

            holder.textViewValorNome = view.findViewById(R.id.textViewValorNome);
            holder.textViewValorQuantidadeCal = view.findViewById(R.id.textViewValorQuantidadeCal);
            holder.textViewValorUnidadeMedida = view.findViewById(R.id.textViewValorUnidadeMedida);
            holder.textViewValorCategoria = view.findViewById(R.id.textViewValorCategoria);
            holder.textViewValorFresco = view.findViewById(R.id.textViewValorFresco);

            view.setTag(holder);

        } else {
            holder = (AlimentoHolder) view.getTag();
        }

        holder.textViewValorNome.setText(alimentos.get(i).getNome());

        String quantidadeCal = numberFormat.format(alimentos.get(i).getQuantidadeCal());

        holder.textViewValorQuantidadeCal.setText(quantidadeCal);

        switch(alimentos.get(i).getUnidadeMedida()){
            case GRAMA:
                holder.textViewValorUnidadeMedida.setText(R.string.gramas);
                break;
            case MILILITRO:
                holder.textViewValorUnidadeMedida.setText(R.string.ml);
                break;
        }

        if (alimentos.get(i).isAlimentoFresco()) {
            holder.textViewValorFresco.setText(context.getResources().getString(R.string.alimento_fresco));
        } else {
            holder.textViewValorFresco.setText(context.getResources().getString(R.string.alimento_processado));
        }

        return view;
    }
}
