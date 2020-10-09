package br.com.danilosataide.calculadordeipva

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Criando uma lista de opções para o Spinner de UFs
        val listaEstados = arrayListOf(
            "Selecione a UF",
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

        //Criando um adaptador para o Spinner
        val estadosAdapter = ArrayAdapter (
            this, //Contexto
            android.R.layout.simple_spinner_dropdown_item, //Layout
            listaEstados //Dados
        )

        //Plugar o adaptador no Spinner
        spnEstados.adapter = estadosAdapter


        //Executando o clique do botão Cadastrar
        btnCalcular.setOnClickListener {
            //Capturar os dados digitados
            val modelo = edtModelo.text.toString().trim();
            val valor = edtValorCarro.text
            val estado = spnEstados.selectedItem.toString()

            //Validação dos campos
            if(modelo.isEmpty() || valor.isEmpty()  || estado == listaEstados[0]) {
                if(modelo.isEmpty() && valor.isEmpty() && estado == listaEstados[0]){
                    //Apresentando uma mensagem de erro ao usuário
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                }else if(modelo.isEmpty() && !valor.isEmpty() && estado != listaEstados[0]){
                        Toast.makeText(this, "Informe o modelo do veículo!", Toast.LENGTH_SHORT).show()
                }else if(!modelo.isEmpty() && valor.isEmpty() && estado != listaEstados[0]){
                    Toast.makeText(this, "Informe o valor do veículo!", Toast.LENGTH_SHORT).show()
                }else if(!modelo.isEmpty() && !valor.isEmpty() && estado == listaEstados[0]){
                    Toast.makeText(this, "Selecione uma Unidade Federativa!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Existem campos não preenchidos!", Toast.LENGTH_SHORT).show()
                }
            } else {
                //Todos os campos foram preenchidos

                //Criando ou acessando o arquivo de preferências compartilhadas
                val sharedPrefs = getSharedPreferences("veiculo_$modelo", Context.MODE_PRIVATE)

                //Criando um editor par o arquivo
                val editPrefs = sharedPrefs.edit()

                //Preparando os dados para serem salvos
                editPrefs.putString("MODELO", modelo)
                editPrefs.putString("VALOR", valor.toString())
                editPrefs.putString("ESTADO", estado)

                //Salvando os dados no arquivo Shared Preferences
                editPrefs.apply()

                //Abrindo a MainActivity
                val mIntent = Intent(this, IpvaActivity::class.java)

                //Passando informações através da Intent
                mIntent.putExtra("INTENT_MODELO+ESTADO", modelo+estado)
                startActivity(mIntent)

                //Tirando todas as telas do empilhamento
                finishAffinity()
            }
        }
    }
}