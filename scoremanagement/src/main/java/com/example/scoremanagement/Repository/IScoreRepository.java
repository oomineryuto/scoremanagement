package com.example.scoremanagement.Repository;

import com.example.scoremanagement.Entity.ScoreInsertRecord;
import com.example.scoremanagement.Entity.ScoreRecord;

import java.util.List;

public interface IScoreRepository {
    public List<ScoreRecord> findAll();
    public ScoreRecord findById(int id);

    public List<ScoreRecord> findMe(String userId);
    public int insert(ScoreInsertRecord scoreInsertRecordInsert);
    public int delete(int id);
    public int update(ScoreRecord scoreRecord);
}
