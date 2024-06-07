package com.example.scoremanagement.Repository;

import com.example.scoremanagement.Entity.ScoreInsertRecord;
import com.example.scoremanagement.Entity.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreRepository implements IScoreRepository{
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public List<ScoreRecord> findAll() {
        return jdbcTemplate.query("SELECT id,grade,score,subject,type,deviation FROM score",
                new DataClassRowMapper<>(ScoreRecord.class));
    }
    @Override
    public ScoreRecord findById(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT id, grade,score,subject,type,deviation FROM score WHERE id = :id", param, new DataClassRowMapper<>(ScoreRecord.class));
        return list.isEmpty() ? null : list.get(0);

    }

    @Override
    public List<ScoreRecord> findMe(String userId) {
        var param=new MapSqlParameterSource();
        System.out.println(userId);
        param.addValue("user_id",userId);
        return jdbcTemplate.query("SELECT id,grade,score,subject,type,deviation FROM score where user_id = :user_id",param,
                new DataClassRowMapper<>(ScoreRecord.class));
    }

    @Override
    public int insert(ScoreInsertRecord scoreInsertRecordInsert) {
        var param = new MapSqlParameterSource();
        param.addValue("user_id", scoreInsertRecordInsert.user_id());
        param.addValue("grade", scoreInsertRecordInsert.grade());
        param.addValue("score",scoreInsertRecordInsert.score());
        param.addValue("subject",scoreInsertRecordInsert.subject());
        param.addValue("type",scoreInsertRecordInsert.type());
        param.addValue("deviation",scoreInsertRecordInsert.deviation());
        return jdbcTemplate.update("INSERT INTO score(user_id,grade,score,subject,type,deviation) VALUES(:user_id, :grade, :score, :subject, :type,:deviation)", param);
    }
    @Override
    public int delete(int id){
        var param = new MapSqlParameterSource();
        param.addValue("id",id);
        return jdbcTemplate.update("DELETE FROM score Where id = :id",param);

    }

    @Override
    public int update(ScoreRecord scoreRecord){
        var param = new MapSqlParameterSource();
        param.addValue("id",scoreRecord.id());
        param.addValue("grade", scoreRecord.grade());
        param.addValue("score",scoreRecord.score());
        param.addValue("subject",scoreRecord.subject());
        param.addValue("type",scoreRecord.type());
        param.addValue("deviation",scoreRecord.deviation());
        return jdbcTemplate.update("UPDATE score SET id = :id,grade = :grade, score = :score, subject = :subject,type=:type,deviation=:deviation WHERE id = :id", param);
    }
}
