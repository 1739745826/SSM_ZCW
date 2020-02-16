package cn.cl.love.VO;

import cn.cl.love.bean.Member;
import cn.cl.love.bean.MemberCert;
import cn.cl.love.bean.Role;
import cn.cl.love.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题：VO
 * 作者：何处是归程
 * 时间：2020/1/30 - 11:45
 */
public class Data {
    private List<User> users = new ArrayList<User>();
    private List<Role> roles = new ArrayList<Role>();
    private List<Integer> ids = new ArrayList<Integer>();
    private List<MemberCert> certimgs = new ArrayList<MemberCert>();

    public Data() {
    }

    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public List<Integer> getIds() {
        return ids;
    }
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }



    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<MemberCert> getCertimgs() {
        return certimgs;
    }

    public void setCertimgs(List<MemberCert> certimgs) {
        this.certimgs = certimgs;
    }
}
