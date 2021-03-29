package ch.dc.shipment_tracking_app;

import android.os.Bundle;

public class QuestionsAnswersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_answers);

        setTitle(getString(R.string.questions_answers_nav_title));

        attachNavigationMenu();
    }

}
