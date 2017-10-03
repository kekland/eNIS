package com.kekland.enis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kekland.enis.NIS.NISApi;
import com.kekland.enis.NIS.NISApiUtils;
import com.kekland.enis.NIS.NISData;
import com.kekland.enis.NIS.NISDiary;
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
import com.kekland.enis.Utilities.DebugLog;
import com.kekland.enis.Utilities.ProjectUtilities;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.net.URI;
import java.util.List;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class LoginActivity extends AppCompatActivity {

    boolean PINCorrect = false, PasswordCorrect = false;

    EditText PINEdit;
    TextInputLayout PINLayout;

    EditText PasswordEdit;
    TextInputLayout PasswordLayout;

    Button EnterButton;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NISApi.Init(getBaseContext());
        ProjectUtilities.Init(getBaseContext());

        database = FirebaseDatabase.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PINEdit = findViewById(R.id.activityLoginPINEditText);
        PINLayout = findViewById(R.id.activityLoginPINTextInputLayout);

        PasswordEdit = findViewById(R.id.activityLoginPasswordEditText);
        PasswordLayout = findViewById(R.id.activityLoginPasswordTextInputLayout);

        EnterButton = findViewById(R.id.activityLoginEnterButton);

        PINEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.length() != 12) {
                    PINCorrect = false;
                    EnterButton.setEnabled(false);
                    PINLayout.setError(ProjectUtilities.GetString(R.string.PINLengthError));
                }
                else {
                    for(int i = 0; i < text.length(); i++) {
                        Character character = text.charAt(i);
                        if(character.charValue() < 48 || character.charValue() > 57) {
                            PINCorrect = false;
                            EnterButton.setEnabled(false);
                            PINLayout.setError(ProjectUtilities.GetString(R.string.PINCharactersError));
                            return;
                        }
                    }
                    PINCorrect = true;
                    EnterButton.setEnabled(PINCorrect && PasswordCorrect);
                    PINLayout.setError("");
                }
            }
        });

        PasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                NISApi.GetPasswordStrength(text, new NISApiMisc.PassStrengthListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(int strength) {
                        if(strength < 3) {
                            PasswordCorrect = false;
                            EnterButton.setEnabled(false);
                            PasswordLayout.setError(ProjectUtilities.GetString(R.string.PasswordTooWeak));
                        }
                        else {
                            PasswordCorrect = true;
                            EnterButton.setEnabled(PINCorrect && PasswordCorrect);
                            PasswordLayout.setError("");
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        PasswordCorrect = false;
                        EnterButton.setEnabled(false);
                        PasswordLayout.setError(message);
                    }
                });
            }
        });

        EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PIN = ((EditText)findViewById(R.id.activityLoginPINEditText)).getText().toString();
                String Password = ((EditText)findViewById(R.id.activityLoginPasswordEditText)).getText().toString();

                int IDOfSchool = ((Spinner)findViewById(R.id.activityLoginSchoolSpinner)).getSelectedItemPosition();
                String School = NISApiUtils.URLs.get(IDOfSchool);

                Login(PIN, Password, School);
            }
        });

        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("Save", false)) {
            NISData.Load(this);
            SavedLogin();
        }
    }

    void SavedLogin() {
        NISApi.CheckCredentials(NISData.getPIN(), NISData.getPassword(), NISData.getSchool(),
                new NISApiAccount.LoginListener() {
            DialogPlus dialog;
                    @Override
                    public void onStart() {
                        dialog = DialogPlus.newDialog(LoginActivity.this)
                                .setCancelable(true)
                                .setGravity(Gravity.CENTER)
                                .setPadding(ProjectUtilities.DefaultPadding, ProjectUtilities.DefaultPadding,
                                        ProjectUtilities.DefaultPadding, ProjectUtilities.DefaultPadding)
                                .setContentHolder(new ViewHolder(R.layout.dialog_logging_in)).create();
                        dialog.show();

                    }

                    @Override
                    public void onSuccess() {
                        if(dialog.isShowing()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            supportFinishAfterTransition();

                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        if(dialog.isShowing()) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                    .edit().putBoolean("Save", false).apply();

                            Snackbar.make(findViewById(R.id.activityLogin), message, Snackbar.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    void Login(final String PIN, final String Pass, final String SchoolURL) {

        final DialogPlus dialog = DialogPlus.newDialog(LoginActivity.this)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setPadding(ProjectUtilities.DefaultPadding, ProjectUtilities.DefaultPadding,
                        ProjectUtilities.DefaultPadding, ProjectUtilities.DefaultPadding)
                .setContentHolder(new ViewHolder(R.layout.dialog_logging_in)).create();

        dialog.show();
        database.getReference().child("users-v2").child(NISApiUtils.ConvertURLToDatabaseURI(SchoolURL))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DebugLog.i("Got firebase data");
                        if(dataSnapshot.hasChild(PIN)) {
                            if(dataSnapshot.child(PIN).child("Password").getValue().toString().equals(Pass)) {
                                NISData.setSchool(SchoolURL);
                                DebugLog.i("Was in firebase - getting data from there");
                                //Success
                                NISData.GetFromDatabase(database, PIN, SchoolURL, new NISData.DatabaseListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess() {
                                        dialog.dismiss();
                                        boolean save = ((Switch)findViewById(R.id.activityLoginSaveSwitch))
                                                .isChecked();

                                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                                .edit().putBoolean("Save", save).apply();

                                        NISData.Save(getApplicationContext());
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        supportFinishAfterTransition();
                                    }

                                    @Override
                                    public void onFailure(DatabaseError error) {
                                        Snackbar.make(findViewById(R.id.activityLogin), error.getMessage(),
                                                Snackbar.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                            else {
                                Snackbar.make(findViewById(R.id.activityLogin),
                                        ProjectUtilities.GetString(R.string.IncorrectPINOrPassword),
                                        Snackbar.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                        else {
                            DebugLog.i("Was not in firebase - starting to login");
                            NISApi.Login(PIN, Pass, SchoolURL, new NISApiAccount.LoginListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess() {
                                    dialog.dismiss();
                                    ShowRegisterDialog(new RegisterCallback() {
                                        @Override
                                        public void onSuccess() {
                                            boolean save = ((Switch)findViewById(R.id.activityLoginSaveSwitch))
                                                    .isChecked();

                                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                                    .edit().putBoolean("Save", save).apply();

                                            NISData.Save(getApplicationContext());

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            supportFinishAfterTransition();
                                        }

                                        @Override
                                        public void onFailure(String message) {
                                            Snackbar.make(findViewById(R.id.activityLogin), message,
                                                    Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(String message) {
                                    Snackbar.make(findViewById(R.id.activityLogin), message,
                                            Snackbar.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Snackbar.make(findViewById(R.id.activityLogin), databaseError.getMessage(), Snackbar.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    interface RegisterCallback {
        void onSuccess();
        void onFailure(String message);
    }

    boolean NicknameCorrect = false, DiaryCorrect = false;
    void ShowRegisterDialog(final RegisterCallback callback) {
        DebugLog.i("Showing register dialog");
        DiaryCorrect = false;
        NicknameCorrect = false;
        final View registerView = getLayoutInflater()
                .inflate(R.layout.dialog_registration, null);

        ((EditText)registerView.findViewById(R.id.dialogRegistrationNicknameEditText)).addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        TextInputLayout layoutEdit =
                                registerView.findViewById(R.id.dialogRegistrationNicknameLayout);
                        View button = registerView.findViewById(R.id.dialogRegistrationSubmitButton);
                        String text = editable.toString();

                        if(text.length() < 5) {
                            NicknameCorrect = false;
                            layoutEdit.setError(ProjectUtilities.GetString(R.string.NicknameLengthError));
                            button.setEnabled(false);
                        }
                        else {
                            NicknameCorrect = true;
                            layoutEdit.setError("");
                            button.setEnabled(NicknameCorrect && DiaryCorrect);
                        }
                    }
                }
        );

        registerView.findViewById(R.id.dialogRegistrationIMKORadioButton)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View button = registerView.findViewById(R.id.dialogRegistrationSubmitButton);
                DiaryCorrect = true;
                button.setEnabled(NicknameCorrect);
            }
        });

        registerView.findViewById(R.id.dialogRegistrationJKORadioButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View button = registerView.findViewById(R.id.dialogRegistrationSubmitButton);
                        DiaryCorrect = true;
                        button.setEnabled(NicknameCorrect);
                    }
                });

        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(registerView)
                .setCancelable(false)
                .create();
        final AlertDialog dialog = builder.create();

        registerView.findViewById(R.id.dialogRegistrationSubmitButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String Nickname = ((EditText)registerView.findViewById(
                                R.id.dialogRegistrationNicknameEditText
                        )).getText().toString();
                        RadioButton IMKORadio = registerView.findViewById(R.id.dialogRegistrationIMKORadioButton);

                        NISDiary Diary;
                        if(IMKORadio.isChecked()) {
                            Diary = NISDiary.IMKO;
                        }
                        else {
                            Diary = NISDiary.JKO;
                        }

                        NISData.setNickname(Nickname);
                        NISData.setDiary(Diary);

                        NISData.SaveInDatabase(database, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    callback.onSuccess();
                                }
                                else {
                                    callback.onFailure("Error connecting to Firebase");
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                });

        DebugLog.i("Showing register dialog 2");
        dialog.show();
    }
}