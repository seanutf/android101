package com.seanutf.android.dagger;

import javax.inject.Singleton;

import dagger.Component;

// @Component makes Dagger create a graph of dependencies
//@Singleton
@MyCustomScope
@Component
public interface ApplicationGraph {
    // The return type  of functions inside the component interface is
    // what can be consumed from the graph
    UserRepository userRepository();
}
