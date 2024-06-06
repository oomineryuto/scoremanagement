package com.example.scoremanagement.Repository;

import com.example.scoremanagement.Entity.UserInsertRecord;
import com.example.scoremanagement.Entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Override
    public UserRecord findByInfo(String login_id, String password) {
        var param = new MapSqlParameterSource();
        param.addValue("login_id", login_id);
        param.addValue("password",password);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :login_id AND password = :password", param, new DataClassRowMapper<>(UserRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public int userInsert(UserInsertRecord userInsertRecord) {
        var param = new MapSqlParameterSource();
        param.addValue("login_id", userInsertRecord.login_id());
        param.addValue("password", userInsertRecord.password());

        return jdbcTemplate.update("INSERT INTO users(login_id,password) VALUES(:login_id,:password)", param);
    }

}
