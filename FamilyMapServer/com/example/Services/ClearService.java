package com.example.Services;

import com.example.Communicators.ResultHolders.ClearResult;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;

public class ClearService {
    public static ClearService SINGLETON = new ClearService();

    private ClearService(){};

    public ClearResult clearService(){
        StringBuilder stringBuilder = new StringBuilder();
        ClearResult clearResult = new ClearResult();

        if (!AuthDAO.SINGLETON.clearTokenTable()){
            stringBuilder.append("Couldn't delete data from the Tokens table.\n");
        }
        if(!EventDAO.SINGLETON.clearEventTable()){
            stringBuilder.append("Couldn't delete data from the Events table.\n");
        }
        if(!PersonDAO.SINGLETON.clearPersonTable()){
            stringBuilder.append("Couldn't delete data from the Persons table.\n");
        }
        if(!UserDAO.SINGLETON.clearUserTable()){
            stringBuilder.append("Couldn't delete data from the Users table.\n");
        }

        if (stringBuilder.length() != 0){
            clearResult.setError(true);
            clearResult.setMessage(stringBuilder.toString());
        }

        return clearResult;
    }
}
