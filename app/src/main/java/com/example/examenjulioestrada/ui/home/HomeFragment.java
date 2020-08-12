package com.example.examenjulioestrada.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.examenjulioestrada.ClassConnection;
import com.example.examenjulioestrada.R;
import com.example.examenjulioestrada.UsuarioModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ListView listview;
    private ArrayList<String> names;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        System.out.println("ENTRO AL FRAGMENT DE USUARIO :::");
        ClassConnection classConnection = new ClassConnection();
        try {
            String response = classConnection.execute("http://alpws.alpura.com:8080/examen/alpura/web-service/examen/users").get();
            // LEER FORMATO JSON
            JSONArray jsonArray = new JSONArray(response);
            listview = (ListView) root.findViewById(R.id.listView);
            Gson gson = new Gson();
            UsuarioModel[] userArray = gson.fromJson(jsonArray.toString(), UsuarioModel[] .class);
            names = new ArrayList<String>();
            for(UsuarioModel user : userArray) {
                System.out.println(user);
                names.add("Id usuario:" + user.getIdUser() + "\nNúmero nómina: " + user.getNumeroNomina()
                + "\nNombre: "+ user.getName() + "\nArea: " + user.getArea());
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1, names);
        listview.setAdapter(adapter);
        return root;
    }
}