package cursoandroid.com.pr10_agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class contactos extends AppCompatActivity {

    Button agregar,cancelar;
    ImageButton adv1,adv2;
    EditText name,email,tel;
    boolean userExist=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        agregar=(Button)findViewById(R.id.btnGuardar);
        cancelar=(Button)findViewById(R.id.btnCancelar);
        adv1=(ImageButton)findViewById(R.id.btnAdv1);
        adv2=(ImageButton)findViewById(R.id.btnAdv2);
        name=(EditText)findViewById(R.id.ed_nameC);
        tel=(EditText)findViewById(R.id.ed_telC);
        email=(EditText)findViewById(R.id.ed_emailC);


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrar();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar(name.getText().toString(),tel.getText().toString(),email.getText().toString());
            }
        });
    }

    private void agregar(String name, String tel, String mail) {
        limpiar();
        if(name.isEmpty()){
            adv1.setVisibility(View.VISIBLE);
        }else{
            if(tel.isEmpty()){
                adv2.setVisibility(View.VISIBLE);
            }else{

                for(int x=0;x<usuario.listaContactos.size();x++){
                    if(usuario.listaContactos.get(x).getPhone().equals(tel) && usuario.listaContactos.get(x).getName().equals(name)
                            && usuario.listaContactos.get(x).getEmail().equals(mail)){
                        Toast.makeText(this, "El contacto ya existe", Toast.LENGTH_SHORT).show();
                        userExist=true;
                        break;

                    }else{
                        userExist=false;
                    }
                }

                if(!userExist){
                    usuario.agregarUser(name,tel,mail);
                    Toast.makeText(this, "Contacto registrado de manera exitosa", Toast.LENGTH_SHORT).show();
                    cerrar();
                }
            }

        }


    }

    private void limpiar() {
        adv1.setVisibility(View.GONE);
        adv2.setVisibility(View.GONE);
    }


    private void cerrar() {
        this.finish();
    }
}
