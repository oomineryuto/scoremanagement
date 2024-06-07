package com.example.scoremanagement.Service;

import com.example.scoremanagement.Entity.ScoreInsertRecord;
import com.example.scoremanagement.Entity.ScoreRecord;
import com.example.scoremanagement.Exception.UserNotFoundException;
import com.example.scoremanagement.Repository.IScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService implements IScoreService{
    @Autowired
    private IScoreRepository iScoreRepository;
    @Override
    public List<ScoreRecord> findAll(){
        return iScoreRepository.findAll();
    }
    @Override
    public ScoreRecord findById(int id){
        if(iScoreRepository.findById(id) == null){
            throw new UserNotFoundException();
        }else{
            return iScoreRepository.findById(id);
        }
    }
    @Override
    public List<ScoreRecord> findMe(String userId) {
        return iScoreRepository.findMe(userId);
    }

    @Override
    public int insert(ScoreInsertRecord scoreInsertRecord){

        return iScoreRepository.insert(scoreInsertRecord);
    }
    @Override
    public int delete(int id){
        return iScoreRepository.delete(id);
    }


    @Override
    public int update(ScoreRecord scoreRecord){

        return iScoreRepository.update(scoreRecord);
    }
}
