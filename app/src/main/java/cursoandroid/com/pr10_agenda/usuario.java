package cursoandroid.com.pr10_agenda;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by juamp on 04/10/2017.
 */

public class usuario extends Application{
    String name,phone,email;
    public static ArrayList<usuario> listaContactos= new ArrayList<>();

    public usuario(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public usuario() {
    }

    public ArrayList<String> mostrarUsers(ArrayList<usuario> lista){


        ArrayList<String> lstAux= new ArrayList<>();


        int x=0;
        for(x=0;x<lista.size();x++){
            lstAux.add(lista.get(x).getName());
        }


        return lstAux;

    }

    public static void agregarUser(String nom,String tel, String email){
        if(email.isEmpty()){
            email="NA";
        }
        usuario user= new usuario(nom,tel,email);
        listaContactos.add(user);

    }
}
