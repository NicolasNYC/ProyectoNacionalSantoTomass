package cl.ProyectoNacionalSantoTomas.proyectonacionalsantotomas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EnviarMensaje extends AppCompatActivity {

    //DECLARACIÓN DE VARIABLES
    private Mqtt mqttManager;
    EditText texto, nombre, apellido;
    Button btnEnviar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_mensaje);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        texto = findViewById(R.id.txtMensaje);
        nombre = findViewById(R.id.txtNombre);
        apellido = findViewById(R.id.txtApellido);
        btnEnviar = findViewById(R.id.btnEnviarMensaje);

        inicializarFireBase();

        mqttManager = new Mqtt(getApplicationContext());
        mqttManager.connectToMqttBroker();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mqttManager.publishMessage("Mensaje: " + texto.getText().toString() + "\nNombre: " + nombre.getText().toString() + "\nApellido: " + apellido.getText().toString());
                databaseReference.child("Mensaje").setValue(texto.getText().toString());
                databaseReference.child("Nombre").setValue(nombre.getText().toString());
                databaseReference.child("Apellido").setValue(apellido.getText().toString());
                Toast.makeText(getApplicationContext(), "Mensaje enviado exitosamente", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Consulta tu servidor MQTT y la base de datos de Firebase", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}