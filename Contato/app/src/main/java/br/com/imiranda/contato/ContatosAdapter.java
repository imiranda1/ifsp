package br.com.imiranda.contato;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.com.imiranda.contato.databinding.ViewContatoBinding;

public class ContatosAdapter extends ArrayAdapter<Contato> {
    public ContatosAdapter(Context context, int layout, ArrayList<Contato> contatosList){
        super(context,layout,contatosList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewContatoBinding viewContatoBinding;
        ContatoViewHolder contatoViewHolder;
        //verificar se é necessário inflar(*criar uma nova célula)
        if (convertView == null) {
            viewContatoBinding = ViewContatoBinding.inflate(LayoutInflater.from(getContext()));
            //atribui a nova célula a view será devolvida preenchida para o Listview
            convertView = viewContatoBinding.getRoot();

            //pegar e guardar referências view interna
            contatoViewHolder = new ContatoViewHolder();
            contatoViewHolder.nomeContatoTV = viewContatoBinding.nomeContatoTv;
            contatoViewHolder.emailContatoTV = viewContatoBinding.emailContatoTv;
            //associa view celular
            convertView.setTag(contatoViewHolder);
        }
        //pegar o HOlder associado a célula (nova ou reciclada)
        contatoViewHolder = (ContatoViewHolder) convertView.getTag();


        Contato contato = getItem(position);
        contatoViewHolder.nomeContatoTV.setText(contato.getNome());
        contatoViewHolder.emailContatoTV.setText(contato.getEmail());

        return convertView;
    }
    private class ContatoViewHolder{
        public TextView nomeContatoTV;
        public TextView emailContatoTV;
    }
}
