package com.kekland.enis.NISApi.Requests.Account;

import com.kekland.enis.NISApi.NISBaseInterface;

/**
 * Created by Gulnar on 09.10.2017.
 */

public class NISApiAccount {
    public static void Login(NISCredentials credentials, LoginListener listener) {

    }

    public interface LoginListener extends NISBaseInterface {
        void onSuccess(NISCredentials credentials, NISRole role,);
    }
}
