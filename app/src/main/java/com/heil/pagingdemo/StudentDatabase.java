package com.heil.pagingdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    private static StudentDatabase database;
    public synchronized static StudentDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, StudentDatabase.class, "student_database").build();
        }
        return database;
    }

    abstract StudentDao getStudentDao();
}
