package com.example.validaciones_clase3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    @BindView(R.id.ed_usuario)
    @Required(order = 1, message = "Debe Ingresar el Usuario")
    EditText ed_usuario;

    @BindView(R.id.ed_clave)
    @Required(order = 2, message = "Debe Ingresar la Clave")
    @TextRule(order = 3, minLength = 6, maxLength = 10, message = "La contraseña debe contener entre 6 y 10 digitos.")
    EditText ed_clave;

    Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        validator = new Validator(this);
        validator.setValidationListener(this);


    }

    @OnClick(R.id.btn_ingresar)
    public void autenticar(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {

        String usuario = ed_usuario.getText().toString();
        String clave = ed_clave.getText().toString();

        if(usuario.equals("dflores") && clave.equals("123456")){
            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this,"Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

        final String failureMessage = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            EditText failed = (EditText) failedView;
            failed.requestFocus();
            failed.setError(failureMessage);
        } else {
            Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_SHORT).show();
        }


    }
}
