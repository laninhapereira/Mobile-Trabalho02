package br.ufc.quixada.dadm.entreg02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static int REQUEST_ADD = 1;
    public static int REQUEST_EDIT = 2;

    ListView myList;
    ArrayList<Livro> listLivros;
    ArrayAdapter adapter;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listLivros = new ArrayList<Livro>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listLivros);
        myList = findViewById(R.id.myList);
        myList.setAdapter(adapter);
    }

    public void addLivros(View view){
        Intent i = new Intent(MainActivity.this, Adicionar.class);
        startActivityForResult(i, REQUEST_ADD);
    }

    public void editarLivros(View view){
        //Livro livro = listLivros.get(0);

        Intent i = new Intent(MainActivity.this, Adicionar.class);

        EditText idUsuario = findViewById(R.id.editarPorId);
        id = Integer.parseInt(idUsuario.getText().toString());

        Livro livro = listLivros.get(id);

        i.putExtra("idLivro", livro.getId());
        i.putExtra("nome", livro.getNome());
        i.putExtra("genero", livro.getGenero());

        startActivityForResult(i, REQUEST_EDIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_ADD && resultCode == Adicionar.RESULT_ADD){
            String nome = (String) data.getExtras().get("nome");
            String genero = (String) data.getExtras().get("genero");
            Livro livro = new Livro(nome, genero);

            listLivros.add(livro);
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_EDIT && resultCode == Adicionar.RESULT_ADD){
            String nome = (String) data.getExtras().get("nome");
            String genero = (String) data.getExtras().get("genero");

            listLivros.get(id).setNome(nome);
            listLivros.get(id).setGenero(genero);

            adapter.notifyDataSetChanged();

        } else if(resultCode == Adicionar.RESULT_CANCEL){
            Toast.makeText(this, "Cancelado",
                    Toast.LENGTH_SHORT).show();
        }
    }
}