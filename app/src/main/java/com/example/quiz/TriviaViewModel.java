package com.example.quiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TriviaViewModel extends ViewModel {
    private MutableLiveData<List<Question>> questions;

    public LiveData<List<Question>> getQuestions() {
        if (questions == null) {
            questions = new MutableLiveData<>();
            loadQuestions();
        }

        return questions;
    }

    public void loadQuestions() {
        // Crie um cliente HTTP usando OkHttp
        OkHttpClient client = new OkHttpClient();

        // Defina a URL da API
        String url = "https://the-trivia-api.com/api/questions/";

        // Crie uma solicitação GET usando a URL
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Envie a solicitação e obtenha a resposta
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Se ocorrer um erro de conexão, lance uma exceção ou faça algo apropriado
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    Gson gson = new Gson();
                    Question[] questionArray = gson.fromJson(responseData, Question[].class);
                    List<Question> questionList = Arrays.asList(questionArray);
                    questions.postValue(questionList);
                } else {
                    // Se a resposta não for bem sucedida, lance uma exceção ou faça algo apropriado
                }
            }
        });
    }


}
