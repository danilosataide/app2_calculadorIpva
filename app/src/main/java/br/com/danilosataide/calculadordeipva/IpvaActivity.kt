package br.com.danilosataide.calculadordeipva

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ipva.*
import java.text.DecimalFormat

class IpvaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipva)

        val estados = arrayOf<String>(
            "Acre",
            "Alagoas",
            "Amapá",
            "Amazonas",
            "Bahia",
            "Ceará",
            "Distrito Federal",
            "Espírito Santo",
            "Goiás",
            "Maranhão",
            "Mato Grosso",
            "Mato Grosso do Sul",
            "Minas Gerais",
            "Pará",
            "Paraíba",
            "Paraná",
            "Pernambuco",
            "Piauí",
            "Rio de Janeiro",
            "Rio Grande do Norte",
            "Rio Grande do Sul",
            "Rondônia",
            "Roraima",
            "Santa Catarina",
            "São Paulo",
            "Sergipe",
            "Tocantins"
        )

        var aliquotas  = arrayOf(
            2,
            2.5,
            3,
            3,
            2.5,
            2.5,
            3,
            2,
            2.5,
            2.5,
            3,
            2.5,
            4,
            2.5,
            2,
            3.5,
            2.5,
            2.5,
            4,
            2.5,
            3,
            3,
            3,
            2,
            3,
            2,
            2
        )

        //Recuperando o ID passado por meio da Intent
        val id = intent.getStringExtra("INTENT_ID")

        //Acessar o arquivo de preferências compartilhadas
        val sharedPrefs = getSharedPreferences(
            "REGISTRO_$id",
            Context.MODE_PRIVATE
        )

        //Recuperar dados no arquivo de preferencias compartilhadas
        //val id_recuperado = sharedPrefs.getString("ID", "")
        val modelo = sharedPrefs.getString("MODELO", "")
        val valor = sharedPrefs.getString("VALOR", "")
        val estado = sharedPrefs.getString("ESTADO", "")

        //Exibir os dados recuperados na tela
        txtModelo.text = "$modelo"
        txtValor.text = "R$ $valor"
        txtEstado.text = "$estado"

        //cálculo do ipva
        for (i in estados.indices)
        {
            if(estados[i] == estado){
                var ipva = valor?.toDouble()?.let { calculator(aliquotas[i].toDouble(), it) };
                txtValorIpva.text = "R$ $ipva";
                break
            }
        }

        btnVoltarMain.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java)
            startActivity(mIntent)
            finishAffinity()
        }

    }
}

fun calculator(a: Double, b: Double):String{
    val df = DecimalFormat("0.00")
    return df.format(((a*b)/100)).toString();
}

