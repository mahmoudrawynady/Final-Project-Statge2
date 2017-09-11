/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.kashasha.theworld.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TaskDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "countriesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY, " +
                TaskContract.TaskEntry.NAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.CAPITAL + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.REGION + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.SUB_REGION + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.FLAG + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.LANGUAGE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.CURRENCY_NAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.CURRENCY_SYNBOL + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.TIME_ZONE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.CAIIING_CODE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.PUPULATION + " DOUBLE NOT NULL, " +
                TaskContract.TaskEntry.LAT + " DOUBLE NOT NULL, " +
                TaskContract.TaskEntry.LANG + " DOUBLE NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    /**
     * This method discards the old table of com.example.ph_data01221240053.todolist.data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}
