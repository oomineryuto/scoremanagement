package com.example.scoremanagement.Service;

import com.example.scoremanagement.Entity.UserInsertRecord;
import com.example.scoremanagement.Entity.UserRecord;


public interface IUserService {
    public UserRecord findByInfo(String login_id, String password);
    public int userInsert(UserInsertRecord userInsertRecord);
}

