package com.example.quiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private PieChart pcResult;
    private TextView tvResultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pcResult = findViewById(R.id.pc_result);
        tvResultCount = findViewById(R.id.tv_result_count);

        findViewById(R.id.btn_test_list).setOnClickListener(this);

        checkTestAndShowResult();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_test_list) {
            finish();
        }
    }

    private void checkTestAndShowResult() {
        TestResult testResult = (TestResult) getIntent()
                .getSerializableExtra("TEST_RESULT");
        int correctAnswersCount = 0;
        int questionsCount = testResult.getQuestionList().size();

        for (int i = 0; i < questionsCount; i++) {
            int correctAnswerIndex = testResult.getQuestionList().get(i)
                    .getCorrectAnswerIndex();
            int userAnswerIndex = testResult.getUserAnswersIndexesList().get(i);
            if (correctAnswerIndex == userAnswerIndex) {
                correctAnswersCount++;
            }
        }

        int percentResult = Math.round((float) correctAnswersCount / questionsCount * 100);

        renderResultChart(percentResult);

        tvResultCount.setText("Правильных ответов: "
                + correctAnswersCount
                + "/"
                + questionsCount);
    }

    private void renderResultChart(int percentResult) {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieEntry correctEntry = new PieEntry(percentResult, 0);
        PieEntry incorrectEntry = new PieEntry(100 - percentResult, 1);

        correctEntry.setLabel("Правильно");
        incorrectEntry.setLabel("Неправильно");

        pieEntries.add(correctEntry);
        pieEntries.add(incorrectEntry);

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        PieData pieData = new PieData(pieDataSet);
        pcResult.setData(pieData);

        Description description = new Description();
        description.setText("Результаты викторины");
        pcResult.setDescription(description);

        pcResult.setCenterText("Результат, %");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
    }

}
