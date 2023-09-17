package com.projeto.diariocal.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.projeto.diariocal.Alimento;

@Database(entities = {Alimento.class}, version = 1)
public abstract class AlimentosDatabase extends RoomDatabase {

    public abstract AlimentoDao alimentoDao();

    private static AlimentosDatabase instance;

    public static AlimentosDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (AlimentosDatabase.class) {
                if (instance == null) {
                    RoomDatabase.Builder builder = Room.databaseBuilder(context,
                            AlimentosDatabase.class,
                            "alimentos.db");

                  /*  builder.addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull @androidx.annotation.NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    });*/

                    instance = (AlimentosDatabase) builder.build();
                }
            }
        }
        return instance;
    }
}
