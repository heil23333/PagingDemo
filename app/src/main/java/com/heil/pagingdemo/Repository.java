package com.heil.pagingdemo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.paging.DataSource;

public class Repository {
    private Context context;
    private StudentDao studentDao;
    private static Repository repository;

    private Repository(Context context) {
        this.context = context;
        this.studentDao = StudentDatabase.getInstance(context).getStudentDao();
    }

    public synchronized static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    public DataSource.Factory<Integer, Student> getDataSourceFactory() {
        return StudentDatabase.getInstance(context).getStudentDao().getAllStudent();
    }

    public void insertStudents(Student ...students) {
        new InsertAsyncTask(studentDao).execute(students);
    }

    public void clearStudents() {
        new ClearAsyncTask(studentDao).execute();
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
