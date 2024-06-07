package com.example.scoremanagement.Service;

import com.example.scoremanagement.Entity.ScoreInsertRecord;
import com.example.scoremanagement.Entity.ScoreRecord;

import java.util.List;

public interface IScoreService {
    public List<ScoreRecord> findAll();
    public ScoreRecord findById(int id);

    public List<ScoreRecord> findMe(String userId);
    public int insert(ScoreInsertRecord scoreInsertRecord);
    public int delete(int id);
    public int update(ScoreRecord scoreRecord);
}
