package com.example.tdah.usuario.ajustes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tdah.BuildConfig;
import com.example.tdah.MainActivity;
import com.example.tdah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButton;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AjustesFragment extends Fragment {
    private AjustesViewModel AjustesViewModel;
    private Button btn_cerrar_sesion;
    private FirebaseAuth mAuth;

    private static final String ID_CLIENT_PAYPAL = "ATWfD62z3TUeMswLbKbXRRwC0tzFiIak2A0ptBlaSjL7LOcQuunPoibBONshrWXck4KcqIgPiXHHiQRr";

    private DatabaseReference databaseReference;
    FirebaseUser usuario_actual;

    private PaymentButton payPalButton;

    private boolean boolean_pago;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AjustesViewModel =
                new ViewModelProvider(this).get(AjustesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        inicializaFirebase();
        mAuth = FirebaseAuth.getInstance();
        btn_cerrar_sesion = (Button) root.findViewById(R.id.btn_cerrar_sesion);

        payPalButton = root.findViewById(R.id.payPalButton_usuario);
        configuraPaypal(root);

        payPalButton.setup(
                createOrderActions -> {
                    ArrayList purchaseUnits = new ArrayList<>();
                    purchaseUnits.add(
                            new PurchaseUnit.Builder()
                                    .amount(
                                            new Amount.Builder()
                                                    .currencyCode(CurrencyCode.USD)
                                                    .value("5.00")
                                                    .build()
                                    )
                                    .build()
                    );
                    Order order = new Order(OrderIntent.CAPTURE,
                            new AppContext.Builder()
                                    .userAction(UserAction.PAY_NOW)
                                    .build(),
                            purchaseUnits);

                    createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                },
                approval -> approval.getOrderActions().capture(result -> {
                    Toast.makeText(getContext(), "Compra exitosa", Toast.LENGTH_LONG).show();
                    boolean_pago = false;
                    try {
                        actualizaPago();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }),
                () -> Toast.makeText(getContext(), "Compra cancelada", Toast.LENGTH_LONG).show()
        );
        btn_cerrar_sesion.setOnClickListener(v -> {
            mAuth.signOut();
            ir_a_main(inflater,container);
        });

        return root;
    }
    private void inicializaFirebase() {

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();

        databaseReference = firebase_database.getReference();

        usuario_actual = mAuth.getCurrentUser();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void actualizaPago() throws ParseException {
        databaseReference.child("Usuario").child(usuario_actual.getUid()).child("string_fecha_pago").setValue(fecha_pago()[0]);
        databaseReference.child("Usuario").child(usuario_actual.getUid()).child("string_fecha_fin_suscripcion").setValue(fecha_pago()[1]);
        Toast.makeText(getContext(), "Cuenta actualizada", Toast.LENGTH_LONG).show();
    }

    /**
     * Este método asigna el monto a pagar por la suscripción
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configuraPaypal(View root) {
        CheckoutConfig config;
        config = new CheckoutConfig(
                getActivity().getApplication(),
                ID_CLIENT_PAYPAL,
                Environment.SANDBOX,
                String.format("%s://paypalpay", BuildConfig.APPLICATION_ID),
                CurrencyCode.MXN,
                UserAction.PAY_NOW,
                new SettingsConfig(
                        true,
                        false
                )
        );
        PayPalCheckout.setConfig(config);

    }
    /**
     * Regresa el tipo de cuenta
     *
     * @return string_cuenta
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String[] fecha_pago() throws ParseException {
        String[] strings_fecha = new String[2];
        DateTimeFormatter dateTimeFormatter_formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String string_fecha_pago = LocalDateTime.now().format(dateTimeFormatter_formato);
        String string_fecha_termino_suscripcion = LocalDateTime.now().plusDays(30).format(dateTimeFormatter_formato);
        if (!boolean_pago) {
            Toast.makeText(getContext(), "Cuenta pago", Toast.LENGTH_SHORT).show();
            strings_fecha[0] = string_fecha_pago;
            strings_fecha[1] = string_fecha_termino_suscripcion;
        } else {
            Toast.makeText(getContext(), "Cuenta gratuita", Toast.LENGTH_SHORT).show();
            strings_fecha[0] = "-1";
            strings_fecha[1] = "-1";
        }
        return strings_fecha;
    }


    private void ir_a_main(LayoutInflater inflater, ViewGroup container) {
        AjustesViewModel =
                new ViewModelProvider(this).get(AjustesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        startActivity(new Intent(root.getContext(),MainActivity.class));
        requireActivity().finish();
    }
}
