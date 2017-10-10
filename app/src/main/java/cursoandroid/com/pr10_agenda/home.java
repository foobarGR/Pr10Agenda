package cursoandroid.com.pr10_agenda;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    private final  int PHONE_CALL_CODE=100;
    private final  int PHONE_MSJ_SEND=200;


    ImageButton btnAgregar,btnBuscar;
    EditText txtBuscar;
    ListView lstContact;
    usuario obj= new usuario();
    boolean b=false;
    ArrayList<usuario> aux= new ArrayList<>();
    int post=0,pO=0;

    AlertDialog dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dialogBuilder=new AlertDialog.Builder(this).create();
        btnAgregar=(ImageButton)findViewById(R.id.btnAdd);
        btnBuscar=(ImageButton)findViewById(R.id.btnBuscar);
        txtBuscar=(EditText)findViewById(R.id.et_buscar);
        lstContact=(ListView)findViewById(R.id.lstContactos);



        mostrarContactos(obj.listaContactos);




        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b=true;
                buscar(txtBuscar);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar();
            }
        });

        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int j, long l) {
                mostrarMsj(j);
                return true;
            }
        });
    }

    private void mostrarContactos(ArrayList<usuario> lista) {

        ArrayAdapter<String> adaptador;
        adaptador= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,obj.mostrarUsers(lista));
        lstContact.setAdapter(adaptador);
    }

    private void buscar(EditText txtBuscar) {
        if(!txtBuscar.getText().toString().isEmpty()){
            String buscar=txtBuscar.getText().toString();
             aux= new ArrayList<>();

            for(int x=0;x<usuario.listaContactos.size();x++){
                if(usuario.listaContactos.get(x).getName().startsWith(txtBuscar.getText().toString())){
                    aux.add(usuario.listaContactos.get(x));
                }

            }
            mostrarContactos(aux);



        }else{
            b=false;
            mostrarContactos(usuario.listaContactos);
        }
    }



    private void agregar() {
        Intent cont= new Intent(this,contactos.class);
        startActivity(cont);
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtBuscar.setText("");
        b=false;
        mostrarContactos(obj.listaContactos);
        dialogBuilder.dismiss();


    }


    public void mostrarMsj(final int jj){

        pO=jj;
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_p,null);
        dialogBuilder.setView(dialogView);


        final TextView titulo = (TextView) dialogView.findViewById(R.id.ADtitulo);
        final ImageButton llamar = (ImageButton) dialogView.findViewById(R.id.btnllamarAD);
        final ImageButton msj = (ImageButton) dialogView.findViewById(R.id.btnmensajeAD);
        final ImageButton eliminar = (ImageButton) dialogView.findViewById(R.id.btneliminarAD);

        titulo.setText("SELECCIONA UNA OPCIÓN");

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                post=pO;
                verificarVersion(1,pO);

            }
        });

        msj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                post=pO;
                verificarVersion(2,pO);

            }
        });


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
                eliminar(pO);
                Toast.makeText(getApplication(), "Contacto eliminado de manera correcta", Toast.LENGTH_SHORT).show();

            }
        });





        dialogBuilder.create();
        dialogBuilder.show();
    }


    public void verificarVersion(int i,int pos){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            if(i==1){
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);
            }else{
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},PHONE_MSJ_SEND);
            }


            newVersion(i,pos);
        }else{

            oldVersion(i,pos);
        }

    }

    public void newVersion(int i,int pos){


    }


    public void oldVersion(int i, int pos){
        switch (i){
            case 1:
                llamar(pos);
                break;
            case 2:
                enviarMensaje(pos);
                break;
}
    }
    private void llamar(int i) {
        String num;
        if(b){
            num=aux.get(i).getPhone();
        }else{
            num=usuario.listaContactos.get(i).getPhone();
        }
        Intent call = new Intent(Intent.ACTION_CALL);
        call.setData(Uri.parse("tel:"+num));

        if(checarPermisos(Manifest.permission.CALL_PHONE)){
            startActivity(call);

        }else{
            Toast.makeText(this, "No hay permiso", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean checarPermisos(String permiso) {

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        return result== PackageManager.PERMISSION_GRANTED;
    }


    private void enviarMensaje(int i) {
        String phone,msj;
        if(b){
            phone=aux.get(i).getPhone();
            msj="Hola "+aux.get(i).getName()+" que tal.";
        }else{
            phone=usuario.listaContactos.get(i).getPhone();
            msj="Hola "+usuario.listaContactos.get(i).getName()+" que tal.";
        }

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("vnd.android-dir/mms-sms");
        sendIntent.putExtra("address", phone);
        sendIntent.putExtra("sms_body", msj);
        startActivity(sendIntent);


    }

    private void eliminar(int i) {
        ArrayList<usuario> lstAux= new ArrayList<>();
        if(b){
            lstAux=aux;
        }else{
            lstAux=usuario.listaContactos;
        }
        for(int x=0;x<usuario.listaContactos.size();x++){
            if(usuario.listaContactos.get(x).getName().equals(lstAux.get(i).getName())&&
                    usuario.listaContactos.get(x).getPhone().equals(lstAux.get(i).getPhone())&&
                    usuario.listaContactos.get(x).getEmail().equals(lstAux.get(i).getEmail())){


                usuario.listaContactos.remove(x);
                buscar(txtBuscar);
                break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<usuario> lstAux= new ArrayList<>();

        switch(requestCode){
            case PHONE_CALL_CODE:

                String permiso=permissions[0];
                int result=grantResults[0];

                //Este if permite asegurarnos que es el mismo permiso que solicito
                if(permiso.equals(Manifest.permission.CALL_PHONE)){
                    //En caso de que se ejecuten mas intents, comprobar que el permiso ya se ha concedido

                    if(result==PackageManager.PERMISSION_GRANTED){
                        //permiso concedido

                        if(b){
                            lstAux=aux;
                        }else{
                            lstAux=usuario.listaContactos;
                        }
                        String numPhone=lstAux.get(post).getPhone();
                        Intent intent=new Intent (Intent.ACTION_CALL,Uri.parse("tel:"+numPhone));

                        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                            return;
                        }

                        startActivity(intent);
                    }else{
                        Toast.makeText(this, "Permiso no concedido", Toast.LENGTH_SHORT).show();
                    }

                }


                break;
            case PHONE_MSJ_SEND:
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }


}
