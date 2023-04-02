package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txPergunta;
    private RadioGroup rgOpcoes;
    private RadioButton rbOpcao1;
    private RadioButton rbOpcao2;
    private RadioButton rbOpcao3;
    private RadioButton rbOpcao4;
    private TriviaViewModel triviaViewModel;
    private int idxPergunta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txPergunta = findViewById(R.id.tx_pergunta);
        rgOpcoes = findViewById(R.id.rgOpcoes);
        rbOpcao1 = findViewById(R.id.rb_opcao1);
        rbOpcao2 = findViewById(R.id.rb_opcao2);
        rbOpcao3 = findViewById(R.id.rb_opcao3);
        rbOpcao4 = findViewById(R.id.rb_opcao4);

        triviaViewModel = new ViewModelProvider(this).get(TriviaViewModel.class);
        triviaViewModel.getQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                Log.d("MainActivity", "Número de perguntas: " + questions.size());
                carregarPergunta(idxPergunta);
            }
        });
    }

    public void btnEnviarClick(View view){
        RadioButton checkedRB= findViewById(rgOpcoes.getCheckedRadioButtonId());
        String respostaSelecionada = checkedRB.getText().toString();
        String respostaCorreta = triviaViewModel.getQuestions().getValue().get(idxPergunta).correctAnswer;
        if (respostaSelecionada.equals(respostaCorreta)){
            Log.d("Log", "resposta correta");
            Toast.makeText(this, "resposta correta", Toast.LENGTH_LONG).show();
        } else {
            Log.d("Log", "resposta incorreta");
            Toast.makeText(this, "resposta incorreta "+ respostaCorreta, Toast.LENGTH_LONG).show();
        }
        idxPergunta++;
        rgOpcoes.clearCheck();
        carregarPergunta(idxPergunta);
    }

    public void carregarPergunta(int idxPergunta){
        // se chegou ao fim da lista de perguntas recebidas, limpa e carrega novamente
        if (idxPergunta == triviaViewModel.getQuestions().getValue().size()) {
            triviaViewModel.loadQuestions();
            this.idxPergunta = 0;
            return;
        }

        Question q = triviaViewModel.getQuestions().getValue().get(idxPergunta);
        txPergunta.setText(q.question);
        String [] respostas = q.getAllAnswers();

        rbOpcao1.setText(respostas[0]);
        rbOpcao2.setText(respostas[1]);
        rbOpcao3.setText(respostas[2]);
        rbOpcao4.setText(respostas[3]);
    }
}