package net.ivanvega.audiolibros2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SelectorFragment selectorFragment
                = new SelectorFragment();

        if ( findViewById(R.id.contenedor_pequeno) != null &&
            getSupportFragmentManager().findFragmentById(R.id.detalle_fragment)==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_pequeno,
                            selectorFragment).commit();
        }

    }

    public void mostrarDetalle(int index){
        FragmentManager manager = getSupportFragmentManager();
        Fragment detalle = manager.findFragmentById(R.id.detalle_fragment);
        if(detalle!=null){
            ((DetalleFragment)detalle).ponInfoLibro(index);
        }else{
            DetalleFragment detalleFragment = new DetalleFragment();

            Bundle bundle = new Bundle();
            bundle.putInt(DetalleFragment.ARG_ID_LIBRO,index);
            detalleFragment.setArguments(bundle);

            manager.beginTransaction()
                    .replace(R.id.contenedor_pequeno,detalleFragment)
                    .addToBackStack(null).commit();
        }
    }
}