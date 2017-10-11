package com.kekland.enis.NISApi.Requests.Account;

import com.kekland.enis.NISApi.NISSchool;

/**
 * Created by Gulnar on 09.10.2017.
 */

public class NISCredentials {
    public String PIN;
    public String Password;
    public NISSchool School;

    public NISCredentials(String PIN, String password, NISSchool school) {
        this.PIN = PIN;
        Password = password;
        School = school;
    }
}
