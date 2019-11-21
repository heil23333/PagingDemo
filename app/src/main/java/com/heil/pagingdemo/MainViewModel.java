package com.heil.pagingdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class MainViewModel extends AndroidViewModel {
    private LiveData<PagedList<Student>> studentPagedList;
    private StudentDatabase database;
    private Repository repository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        database = StudentDatabase.getInstance(application);
        repository = Repository.getInstance(application);
        studentPagedList = new LivePagedListBuilder<>(Repository.getInstance(application).getDataSourceFactory(), 10).build();
    }

    public LiveData<PagedList<Student>> getStudentPagedList() {
        return studentPagedList;
    }

    public void insertStudent(Student ...students) {
        repository.insertStudents(students);
    }

    public void clearStudents() {
        repository.clearStudents();
    }
}
