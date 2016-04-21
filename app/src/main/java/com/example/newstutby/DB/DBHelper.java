package com.example.newstutby.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rssfeedtutby.Constants;
import com.example.rssfeedtutby.models.DBNew;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Сергей on 19.04.2016.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private Dao<DBNew, Integer> dao;

    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DBNew.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, DBNew.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<DBNew, Integer> getDao() throws SQLException {
        if(dao == null) {
            dao = getDao(DBNew.class);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        dao = null;
    }
}
