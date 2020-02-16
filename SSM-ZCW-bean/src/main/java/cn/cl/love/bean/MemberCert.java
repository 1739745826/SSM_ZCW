package cn.cl.love.bean;

import org.springframework.web.multipart.MultipartFile;

public class MemberCert {
    private Integer id;

    private Integer memberid;

    private Integer certid;

    private String iconpath;

    public MultipartFile getFileImg() {
        return fileImg;
    }

    public void setFileImg(MultipartFile fileImg) {
        this.fileImg = fileImg;
    }

    private MultipartFile fileImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public Integer getCertid() {
        return certid;
    }

    public void setCertid(Integer certid) {
        this.certid = certid;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath == null ? null : iconpath.trim();
    }

    @Override
    public String toString() {
        return "MemberCert{" +
                "id=" + id +
                ", memberid=" + memberid +
                ", certid=" + certid +
                ", iconpath='" + iconpath + '\'' +
                ", fileImg=" + fileImg +
                '}';
    }
}