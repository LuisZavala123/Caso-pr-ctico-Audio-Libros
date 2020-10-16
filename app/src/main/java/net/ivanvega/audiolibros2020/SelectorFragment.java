package net.ivanvega.audiolibros2020;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectorFragment extends Fragment {

    RecyclerView recyclerView;
    AdaptadorLibros adaptadorLibros;
    GridLayoutManager layoudManager;
    Context context;

    MainActivity mainActivity;

    String[] menuContextual = new String[]{"compartir","agregar","eliminar"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.listaselector,null);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        layoudManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(layoudManager);

        adaptadorLibros = new AdaptadorLibros(getActivity(),Libro.ejemploLibros());

        adaptadorLibros.setOnclicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),
                        "Elemento seleccionado"+
                                recyclerView.getChildAdapterPosition(view),
                        Toast.LENGTH_LONG).show();

                mainActivity.mostrarDetalle(recyclerView.getChildAdapterPosition(view));
            }
        });

        adaptadorLibros.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View volc) {

                        Libro Libroselecionado= Libro.ejemploLibros().get(recyclerView.getChildAdapterPosition(volc));

                        AlertDialog.Builder Cuadrodedialogo = new AlertDialog.Builder(context);

                        Cuadrodedialogo.setTitle("Seleccione una opcion");
                        Cuadrodedialogo.setMessage("Informacion del mensaje");
                        Cuadrodedialogo.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(context,"Boton Ok Presionado",Toast.LENGTH_LONG).show();
                            }
                        });

                        Cuadrodedialogo.setItems(menuContextual,(dialogInterface,i)->
                        {
                            Toast.makeText(context,"se preciono el elemento:"+ i,Toast.LENGTH_LONG).show();

                            switch (i){
                                case 0:
                                    //compantor
                                    Intent intent =
                                            new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_SUBJECT,Libroselecionado.titulo);
                                    intent.putExtra(Intent.EXTRA_TEXT,Libroselecionado.urlAudio);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    //agregar
                                    Libro.ejemploLibros().add(Libroselecionado);
                                    adaptadorLibros.notifyDataSetChanged();
                                    break;
                                case 2:
                                    //eliminar
                                    Libro.ejemploLibros().remove(Libroselecionado);
                                    adaptadorLibros.notifyDataSetChanged();
                                    break;
                            }
                        });
                        Cuadrodedialogo.create().show();

                        return false;
                    }
                });

        recyclerView.setAdapter(adaptadorLibros);

        return
                v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context=context;

        if(context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }

}
