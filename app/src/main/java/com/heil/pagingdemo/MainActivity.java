package com.heil.pagingdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button insert, clear;
    LiveData<PagedList<Student>> studentPagedList;
    MyPageAdapter adapter;
    StudentDatabase database;
    StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        insert = findViewById(R.id.button_insert);
        clear = findViewById(R.id.button_clear);

        database = StudentDatabase.getInstance(this);
        studentDao = database.getStudentDao();
        adapter = new MyPageAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        studentPagedList = new LivePagedListBuilder<>(studentDao.getAllStudent(), 10).build();
        studentPagedList.observe(this, new Observer<PagedList<Student>>() {
            @Override
            public void onChanged(final PagedList<Student> students) {
                adapter.submitList(students);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student[] students = new Student[100];
                for (int i = 0; i <100 ; i ++) {
                    Student student = new Student();
                    student.setNumber(i);
                    students[i] = student;
                }
                new InsertAsyncTask(studentDao).execute(students);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ClearAsyncTask(studentDao).execute();
            }
        });
    }

    static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {
        StudentDao studentDao;

        public InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            studentDao.insert(students);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void, Void, Void> {
        StudentDao studentDao;

        public ClearAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentDao.deleteAllStudent();
            return null;
        }
    }
}
