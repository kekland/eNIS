package com.kekland.enis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kekland.enis.NIS.NISApi;
import com.kekland.enis.NIS.Requests.NISApiAccount;
import com.kekland.enis.NIS.Requests.NISApiMisc;
import com.kekland.enis.NIS.Requests.NISApiSubjects;
import com.kekland.enis.NIS.Subject.GoalStatus;
import com.kekland.enis.NIS.Subject.GoalsData;
import com.kekland.enis.NIS.Subject.Homework;
import com.kekland.enis.NIS.Subject.IMKOGoal;
import com.kekland.enis.NIS.Subject.IMKOLesson;
import com.kekland.enis.NIS.Subject.JKOGoal;
import com.kekland.enis.NIS.Subject.JKOLesson;
import com.kekland.enis.NIS.Subject.SubjectData;

import java.net.URI;
import java.util.List;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}