package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Test currentTest;

    private TestViewModel model;

    private TextView tvTestName;
    private TextView tvQuestionNumber;
    private TextView tvQuestionTitle;
    private TextView tvUserAnswer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvTestName = findViewById(R.id.tv_test_name);
        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvQuestionTitle = findViewById(R.id.tv_question_title);
        tvUserAnswer = findViewById(R.id.tv_user_answer);
        recyclerView = findViewById(R.id.rv_answer_list);

        findViewById(R.id.btn_prev_question).setOnClickListener(this);
        findViewById(R.id.btn_next_question).setOnClickListener(this);
        findViewById(R.id.btn_finish_test).setOnClickListener(this);

        model = ViewModelProviders.of(TestActivity.this).get(TestViewModel.class);
        subscribeToModelChanges();

        loadTest();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_prev_question:
                getPrevQuestion();
                break;
            case R.id.btn_next_question:
                getNextQuestion();
                break;
            case R.id.btn_finish_test:
                finishTest();
                break;
        }
    }

    private void getPrevQuestion() {
        int newQuestionIndex = model.getCurrentQuestion().getValue().getCurrentQuestionIndex() - 1;
        if (newQuestionIndex >= 0) {
            Question newCurrentQuestion = currentTest.getQuestionList().get(newQuestionIndex);
            model.setCurrentQuestion(newCurrentQuestion, newQuestionIndex);
        }
    }

    private void getNextQuestion() {
        int newQuestionIndex = model.getCurrentQuestion().getValue().getCurrentQuestionIndex() + 1;
        int questionsCount = currentTest.getQuestionList().size();
        if (newQuestionIndex < questionsCount) {
            Question newCurrentQuestion = currentTest.getQuestionList().get(newQuestionIndex);
            model.setCurrentQuestion(newCurrentQuestion, newQuestionIndex);
        }
    }

    private void finishTest() {
        TestResult testResult = new TestResult();
        testResult.setQuestionList(currentTest.getQuestionList());
        testResult.setUserAnswersIndexesList(model.getUserAnswersIndexesList());

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("TEST_RESULT", testResult);
        startActivity(intent);
        finish();
    }

    private void subscribeToModelChanges() {
        model.getCurrentQuestion().observe(this, currentQuestion -> {
            generateQuestionView(
                    currentQuestion.getCurrentQuestionIndex(),
                    currentQuestion.getTitle()
            );
            generateAnswerListView(currentQuestion.getAnswerList());
            generateUserAnswerView(currentQuestion);
        });
    }

    private void loadTest() {
        int testId = getIntent().getIntExtra("TEST_ID", 0);
        currentTest = TestLoader.getInstance(getApplication()).getTest(testId);
        int currentQuestionIndex = 0;
        Question currentQuestion = currentTest.getQuestionList().get(currentQuestionIndex);
        model.clearUserAnswersIndexesList(currentTest.getQuestionList().size());
        model.setCurrentQuestion(currentQuestion, currentQuestionIndex);
        tvTestName.setText(currentTest.getTitle());
    }

    private void generateQuestionView(int index, String title) {
        tvQuestionNumber.setText(
                "Вопрос "
                + (index + 1)
                + "/"
                + currentTest.getQuestionList().size());
        tvQuestionTitle.setText(title);
    }

    private void generateAnswerListView(List<String> answerList) {
        AnswerListAdapter adapter = new AnswerListAdapter(
                this,
                answerList,
                TestActivity.this,
                tvUserAnswer
        );
        recyclerView.setAdapter(adapter);
    }

    private void generateUserAnswerView(CurrentQuestion currentQuestion) {
        int questionIndex = currentQuestion.getCurrentQuestionIndex();
        List<Integer> answersIndexesList = model.getUserAnswersIndexesList();
        if (answersIndexesList.get(questionIndex) != -1) {
            tvUserAnswer.setText("Ваш ответ: ".concat(
                    (answersIndexesList.get(questionIndex) + 1) + ") "
                            + currentQuestion
                            .getAnswerList()
                            .get(
                            answersIndexesList.get(questionIndex)
                    )
            ));
        } else {
            tvUserAnswer.setText(R.string.your_answer);
        }
    }

}
