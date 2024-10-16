package com.example.simulador_carro;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputPreco;
    private TextView txtAduaneiro, txtPortuaisELocais, txtTotal;

    private CheckBox checkBoxTurismo, checkBoxPickup, checkBoxAutocarro, checkBoxCaminhao, checkBoxMotociclo;
    private CheckBox checkBoxDollar, checkBoxEuro, checkBoxRand;

    private double valorEmMetical = 0.0;


    private double taxaServicoAduaneiro = 1000;
    private double mcnet = 1540;
    private double kudumba = 2700;
    private double agenteNavegacao = 9800;
    private double inatter = 11510;
    private double registroAutomovel = 2600;
    private double chapaMatricula = 5000;
    private double parqueamento = 20200;

    private double dolarParaMetical = 64.51;
    private double euroParaMetical = 72.15;
    private double randParaMetical = 3.77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputPreco = findViewById(R.id.inputPreco);
        txtAduaneiro = findViewById(R.id.txtAduaneiro);
        txtPortuaisELocais = findViewById(R.id.txtPortuaisELocais);
        txtTotal = findViewById(R.id.txtTotal);

        checkBoxTurismo = findViewById(R.id.checkBox1);
        checkBoxPickup = findViewById(R.id.checkBox2);
        checkBoxAutocarro = findViewById(R.id.checkBox3);
        checkBoxCaminhao = findViewById(R.id.checkBox4);
        checkBoxMotociclo = findViewById(R.id.checkBox5);

        checkBoxDollar = findViewById(R.id.checkBoxDollar);
        checkBoxEuro = findViewById(R.id.checkBoxEUR);
        checkBoxRand = findViewById(R.id.checkBoxRand);

        inputPreco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    double precoInput = Double.parseDouble(s.toString());
                    calcularDespesa(precoInput);
                } else {

                    txtAduaneiro.setText("0.00 MT");
                    txtPortuaisELocais.setText("0.00 MT");
                    txtTotal.setText("0.00 MT");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        configureCheckboxesTipoVeiculo();
        configureCheckboxesMoeda();
    }

    private void calcularDespesa(double precoInput) {
        if (checkBoxDollar.isChecked()) {
            valorEmMetical = precoInput * dolarParaMetical;
        } else if (checkBoxEuro.isChecked()) {
            valorEmMetical = precoInput * euroParaMetical;
        } else if (checkBoxRand.isChecked()) {
            valorEmMetical = precoInput * randParaMetical;
        } else {
            Toast.makeText(this, "Por favor, selecione uma moeda.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (valorEmMetical == 0.0) {
            txtAduaneiro.setText("0.00 MT");
            txtPortuaisELocais.setText("0.00 MT");
            txtTotal.setText("0.00 MT");
            return;
        }

        double direitosAduaneiros = valorEmMetical * 0.20;
        double ice = direitosAduaneiros;
        double iva = 0.16 * (valorEmMetical + direitosAduaneiros + ice);

        double despesaAduaneira = direitosAduaneiros + ice + iva + taxaServicoAduaneiro + mcnet + kudumba;

        double despesaPortuariaELocais = agenteNavegacao + inatter + registroAutomovel + chapaMatricula + parqueamento;

        double precoTotal = valorEmMetical + despesaAduaneira + despesaPortuariaELocais;

        txtAduaneiro.setText(String.format("%.2f MT", despesaAduaneira));
        txtPortuaisELocais.setText(String.format("%.2f MT", despesaPortuariaELocais));
        txtTotal.setText(String.format("%.2f MT", precoTotal));
    }

    private void configureCheckboxesTipoVeiculo() {
        View.OnClickListener veiculoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desmarcarTodosVeiculos();
                ((CheckBox) v).setChecked(true);
            }
        };

        checkBoxTurismo.setOnClickListener(veiculoListener);
        checkBoxPickup.setOnClickListener(veiculoListener);
        checkBoxAutocarro.setOnClickListener(veiculoListener);
        checkBoxCaminhao.setOnClickListener(veiculoListener);
        checkBoxMotociclo.setOnClickListener(veiculoListener);
    }

    private void desmarcarTodosVeiculos() {
        checkBoxTurismo.setChecked(false);
        checkBoxPickup.setChecked(false);
        checkBoxAutocarro.setChecked(false);
        checkBoxCaminhao.setChecked(false);
        checkBoxMotociclo.setChecked(false);
    }

    private void configureCheckboxesMoeda() {
        View.OnClickListener moedaListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desmarcarTodasMoedas();
                ((CheckBox) v).setChecked(true);

                if (!inputPreco.getText().toString().isEmpty()) {
                    double precoInput = Double.parseDouble(inputPreco.getText().toString());
                    calcularDespesa(precoInput);
                } else {

                    txtAduaneiro.setText("0.00 MT");
                    txtPortuaisELocais.setText("0.00 MT");
                    txtTotal.setText("0.00 MT");
                }
            }
        };

        checkBoxDollar.setOnClickListener(moedaListener);
        checkBoxEuro.setOnClickListener(moedaListener);
        checkBoxRand.setOnClickListener(moedaListener);
    }

    private void desmarcarTodasMoedas() {
        checkBoxDollar.setChecked(false);
        checkBoxEuro.setChecked(false);
        checkBoxRand.setChecked(false);
    }
}
