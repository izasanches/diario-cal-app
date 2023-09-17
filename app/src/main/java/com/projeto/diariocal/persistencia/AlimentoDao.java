package com.projeto.diariocal.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.projeto.diariocal.Alimento;

import java.util.List;

@Dao
public interface AlimentoDao {

    @Insert
    long insert(Alimento alimento);

    @Delete
    void delete(Alimento alimento);

    @Update
    void update(Alimento alimento);

    @Query("SELECT * FROM alimentos WHERE id = :id")
    Alimento queryForId(long id);

    @Query("SELECT * FROM alimentos ORDER BY nome ASC")
    List<Alimento> queryForAll();
}
