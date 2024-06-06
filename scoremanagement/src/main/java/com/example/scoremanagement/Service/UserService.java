package com.example.scoremanagement.Service;

import com.example.scoremanagement.Entity.UserInsertRecord;
import com.example.scoremanagement.Entity.UserRecord;
import com.example.scoremanagement.Exception.UserNotFoundException;
import com.example.scoremanagement.Repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public UserRecord findByInfo(String login_id, String password) {
        if (iUserRepository.findByInfo(login_id, password) == null ) {
            throw new UserNotFoundException();
        }
        return iUserRepository.findByInfo(login_id,password);
    }

    @Override
    public int userInsert(UserInsertRecord userInsertRecord){

        return iUserRepository.userInsert(userInsertRecord);
    }



}