package cn.cl.love.manager.controller;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Advertisement;
import cn.cl.love.bean.User;
import cn.cl.love.manager.service.AdvertisementServlet;
import cn.cl.love.util.AjaxResult;
import cn.cl.love.util.Const;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 标题：广告管理器
 * 作者：何处是归程
 * 时间：2020/2/1 - 21:38
 */
@Controller
@RequestMapping("ad")
public class AdController {
    @Autowired
    AdvertisementServlet advertisementServlet;

    // 跳往广告首页
    @RequestMapping("/toIndex")
    public String toIndex() {
        return "ad/index";
    }

    // 跳往广告添加页
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "ad/add";
    }

    // 跳往广告修改页
    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        Advertisement ad = advertisementServlet.getAdById(id);
        model.addAttribute("ad", ad);
        return "ad/update";
    }

    // 查询全部广告
    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "begin", defaultValue = "1") Integer begin) {
        try {
            PageHelper.startPage(begin, 10);
            List<Advertisement> ads = advertisementServlet.getAdAll();
            PageInfo<Advertisement> info = new PageInfo<Advertisement>(ads, 10);
            return AjaxResult.success("info", info);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("获取广告信息失败");
        }
    }

    /**
     * @功能: 添加广告
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/add")
    public Object add(HttpServletRequest request, Advertisement ad, HttpSession session) {
        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
            MultipartFile mfile = mreq.getFile("img");
            String name = mfile.getOriginalFilename();//java.jpg
            String extname = name.substring(name.lastIndexOf(".")); // .jpg
            String iconpath = UUID.randomUUID().toString() + extname; //232243343.jpg
            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pics");
            String path = realpath + "\\adv\\" + iconpath;
            mfile.transferTo(new File(path));
            User user = (User) session.getAttribute(Const.LOGIN_USER);
            ad.setUserid(user.getId());
            ad.setStatus("1");
            ad.setIconpath(iconpath);
            advertisementServlet.saveAd(ad);
            return AjaxResult.success();
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.fail("添加广告失败");
        }
    }

    // 修改广告
    @ResponseBody
    @RequestMapping("update")
    public Object update(Advertisement ad) {
        try {
            advertisementServlet.updateAd(ad);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("修改广告失败");
        }
    }

    // 删除单个
    @ResponseBody
    @RequestMapping("del")
    public Object del(Integer id) {
        try {
            advertisementServlet.delAd(id);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("删除广告失败");
        }
    }

    // 批量删除
    @ResponseBody
    @RequestMapping("delBatchAd")
    public Object delBatchAd(Data data) {
        try {
            advertisementServlet.delBatchAd(data);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("删除广告失败");
        }
    }
}
