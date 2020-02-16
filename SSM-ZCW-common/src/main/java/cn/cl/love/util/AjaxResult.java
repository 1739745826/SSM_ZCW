package cn.cl.love.util;

import cn.cl.love.bean.User;
import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;


/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/28 - 16:39
 */
public class AjaxResult {
    private Boolean success;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();
    private PageInfo<User> pageInfo;

    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    public static AjaxResult success(String str, Object obj) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setData(str, obj);
        return ajaxResult;
    }

    public static AjaxResult fail(String msg) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(false);
        ajaxResult.setMessage(msg);
        return ajaxResult;
    }

    public PageInfo<User> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo<User> pageInfo) {
        this.pageInfo = pageInfo;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String str, Object obj) {
        this.data.put(str, obj);
    }
}
