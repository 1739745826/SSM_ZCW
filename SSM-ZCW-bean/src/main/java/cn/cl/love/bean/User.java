package cn.cl.love.bean;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;

public class User {
    private Integer id;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$", message = "账号的格式为：以字母开头，长度在6~16之间，只能包含字母、数字和下划线")
    private String loginacct;

    @Pattern(regexp = "^[a-zA-Z]\\w{6,16}$", message = "密码的格式为：以字母开头，长度在6~16之间，只能包含字母、数字和下划线")
    private String userpswd;

    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,5}$", message = "真实名称的格式为：2~5个汉字")
    private String username;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String createtime;

    public User(Integer id, String loginacct, String userpswd, String username, String email, String createtime) {
        this.id = id;
        this.loginacct = loginacct;
        this.userpswd = userpswd;
        this.username = username;
        this.email = email;
        this.createtime = createtime;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct == null ? null : loginacct.trim();
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd == null ? null : userpswd.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginacct='" + loginacct + '\'' +
                ", userpswd='" + userpswd + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}