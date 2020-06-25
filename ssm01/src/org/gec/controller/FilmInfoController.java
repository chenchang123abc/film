package org.gec.controller;

import org.gec.pojo.Filminfo;
import org.gec.pojo.Filmtype;
import org.gec.service.FilmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

@Controller
public class FilmInfoController {

    @Autowired
    private FilmInfoService filmInfoService;

    public List<Filminfo> getResult() {
        return result;
    }

    //电影list
    List<Filminfo> result ;


    @RequestMapping("/findFilms")
    public ModelAndView findFilms(Filminfo filminfo, Integer typeid) throws Exception{
        //选了类型
        if (typeid != 0){
            Filmtype type = new Filmtype();
            type.setTypeid(typeid);

            filminfo.setFilmtype(type);
        }

        //电影条件
        //查询
        result = filmInfoService.findAllInfo(filminfo);

        for(Filminfo info:result){
            System.out.println(info.getFilmtype().getTypename());
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("result",result);
        mv.setViewName("page/result");
        return mv;
    }

    @RequestMapping("/FilmAddServlet")
    public String FilmAddServlet(Integer typeid,Filminfo filminfo, MultipartFile picname) throws Exception{
        //电影类型

         Filmtype filmtype = new Filmtype();
         filmtype.setTypeid(typeid);

         //上传的文件名
        String fileName = picname.getOriginalFilename();
         //文件存储路径
        File file = new File("D:\\code\\file\\",fileName);

        filminfo.setPic(fileName);

        //写到磁盘
        picname.transferTo(file);

         //查询
         filmInfoService.save(filminfo);


        return "redirect:/toAddFilm";
    }
    //批量删除
    @RequestMapping("/deleteFilms")
    public String deleteFilms(int[] filmIds){
        //调用方法删除  request.getParameter("filmsIds")-->空指针
        filmInfoService.deleteByIds(filmIds);

        //重定向
        return "redirect:/toCinema";
    }
}
