package com.terry.iat.service.common.content;


import com.terry.iat.dao.entity.UserEntity;


public class WebContext {
    private static final ThreadLocal<ThreadLocalContext> WEBCONTEXT_LOCAL = new ThreadLocal<ThreadLocalContext>();




    public static  void setCurrentUser(UserEntity user){
        WEBCONTEXT_LOCAL.get().setUserEntity(user);
    }

    public static UserEntity getCurrentUser(){
        return WEBCONTEXT_LOCAL.get().getUserEntity();
    }



    public static ThreadLocalContext getContext() {
        ThreadLocalContext localContext = WEBCONTEXT_LOCAL.get();
        return localContext;
    }


    public static void registry(ThreadLocalContext threadLocalContext) {
        WEBCONTEXT_LOCAL.set(threadLocalContext);
    }

    public static void release() {
        WEBCONTEXT_LOCAL.remove();
    }
}
