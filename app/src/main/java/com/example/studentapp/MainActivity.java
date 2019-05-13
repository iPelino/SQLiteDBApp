package com.example.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName;
    Button btnLoad, btnAdd, btnUpdate, btnFind, btnDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        etId = findViewById(R.id.text_student_id);
        etName = findViewById(R.id.text_student_name);
        btnLoad = findViewById(R.id.button_load_data);
        btnAdd = findViewById(R.id.button_add);
        btnFind = findViewById(R.id.button_find);
        btnUpdate = findViewById(R.id.button_update);
        btnDelete = findViewById(R.id.button_delete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addStudent();
            }
        });

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentName = etName.getText().toString();

                MyDBHandler handler = new MyDBHandler(MainActivity.this,null,null,1);
                Student student =handler.findRecord(studentName);

                if (student != null){



                }else{
                    Toast.makeText(getApplicationContext(),"No Match found",Toast.LENGTH_LONG).show();
                    clearFields();
                }

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStudents();
            }
        });
    }

    private void clearFields(){
        etId.setText("");
        etName.setText("");
    }

    private void addStudent(){

        int studentId = Integer.parseInt(etId.getText().toString());
        String studentName = etName.getText().toString();

        MyDBHandler handler = new MyDBHandler(MainActivity.this,null,null,1);

        Student student = new Student(studentId,studentName);
        try {
            handler.addNewRecord(student);
            clearFields();


        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Adding Record Failed!",Toast.LENGTH_LONG).show();

        }


    }

    private void deleteStudent() {
        MyDBHandler handler = new MyDBHandler(this, null,
                null, 1);
        boolean result = handler.deleteRecord(Integer.parseInt(
                etId.getText().toString()));
        if (result) {
            etId.setText("");
            etName.setText("");
            Toast.makeText(getApplicationContext(),"Record Deleted!",Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(),"No Record deleted",Toast.LENGTH_LONG).show();
    }


    private void updateStudent() {
        MyDBHandler handler = new MyDBHandler(this, null,
                null, 1);
        boolean result = handler.updateRecord(Integer.parseInt(
                etId.getText().toString()), etName.getText().toString());
        if (result) {
            etId.setText("");
            etName.setText("");
            Toast.makeText(getApplicationContext(),"Record update!",Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(getApplicationContext(),"No Match found",Toast.LENGTH_LONG).show();

    }

    private void loadStudents() {
        MyDBHandler handler = new MyDBHandler(this, null, null, 1);
        etName.setText(handler.loadData());

    }
}
