package com.example.scoremanagement.Repository;


import com.example.scoremanagement.Entity.UserInsertRecord;
import com.example.scoremanagement.Entity.UserRecord;

public interface IUserRepository {
    public UserRecord findByInfo(String  login_id, String password);
    public int userInsert(UserInsertRecord userInsertRecord);
}


