package com.example.examenjulioestrada.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.examenjulioestrada.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button button = root.findViewById(R.id.button3);
        final TextView txtNombre = root.findViewById(R.id.editTextTextPersonName);
        final TextView txtNumeroNomina = root.findViewById(R.id.editTextTextPersonName2);
        final TextView txtArea = root.findViewById(R.id.editTextTextPersonName3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                System.out.println("VALOR DE NOMINA  ::: " + txtNumeroNomina.getText());
                System.out.println("VALOR DE NOMBRE :::: " + txtNombre.getText());
                System.out.println("VALOR DE AREA :::: " + txtArea.getText());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("numeroNomina", Integer.parseInt(txtNumeroNomina.getText().toString()));
                    jsonObject.put("name", txtNombre.getText().toString());
                    jsonObject.put("area", Integer.parseInt(txtArea.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "http://alpws.alpura.com:8080/examen/alpura/web-service/examen/users";
                JsonObjectRequest request = new JsonObjectRequest
                        (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // TODO Auto-generated method stub
                                System.out.println("Response ::: " + response);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });

                Volley.newRequestQueue(root.getContext()).add(request);
            }
        });

        return root;
    }
}