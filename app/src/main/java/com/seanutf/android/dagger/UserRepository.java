package com.seanutf.android.dagger;

import javax.inject.Inject;
import javax.inject.Singleton;

/***
 * @Singleton 表示本类为
 *
 */
//@Singleton
@MyCustomScope
public class UserRepository {

    private final UserLocalDataSource userLocalDataSource;
    private final UserRemoteDataSource userRemoteDataSource;

    // @Inject lets Dagger know how to create instances of this object
    @Inject
    public UserRepository(UserLocalDataSource userLocalDataSource, UserRemoteDataSource userRemoteDataSource) {
        this.userLocalDataSource = userLocalDataSource;
        this.userRemoteDataSource = userRemoteDataSource;
    }
}
