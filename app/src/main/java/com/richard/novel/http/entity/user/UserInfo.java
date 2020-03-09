package com.richard.novel.http.entity.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserInfo implements Serializable{
    /**
     * user : {"id":1,"name":"richae0x1.62e798p40","sex":0,"header":"http://oow561q5i.bkt.clouddn.com/703de9f4b2264a16b9710402308f5849.jpg"}
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX25hbWUiOiI5ODVkNTA3OTY2ZXciLCJjcmVhdGVfZGF0ZSI6MTUyNDMwNDQyNzc4MSwiZXhwIjoxNTI0OTA5MjI3fQ.9vET5TbfQC9NTjHOKdB-rF3OlK07455koD3XBOuN6s9Ulb_sqh-aM3LS9nZR1bf4o-Z8ZOr8KFE4G3t11oSRJg
     */
    public static final String USER_RULE_GENERAL = "ROLE_USER";
    public static final String USER_RULE_ADMIN = "ROLE_ADMIN";



    private UserBean user;
    private String token;
    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean implements Serializable{
        /**
         * id : 1
         * name : richae0x1.62e798p40
         * sex : 0
         * header : http://oow561q5i.bkt.clouddn.com/703de9f4b2264a16b9710402308f5849.jpg
         */

        private long id;
        private String name;
        private int sex;
        private String header;
        private int age;

        private String inviteCode;
        private String fromInviteCode;


        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getFromInviteCode() {
            return fromInviteCode;
        }

        public void setFromInviteCode(String fromInviteCode) {
            this.fromInviteCode = fromInviteCode;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserBean userBean = (UserBean) o;
            return id == userBean.id;
        }
    }
}
