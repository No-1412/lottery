package com.youge.yogee.interfaces.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Haipeng.Ren on 2017/4/11.
 * @author Haipeng.Ren
 * @version 1.0
 */
@Controller
@RequestMapping("${publicPath}")
public class DemoI18NInterface {
    public static final Logger logger = LoggerFactory.getLogger(DemoI18NInterface.class);

    @Autowired
    private CookieLocaleResolver resolver;

    //@Autowired
    //private SessionLocaleResolver resolver;

    /**
     * 国际化Demo
     * @param request
     * @return
     */
    @RequestMapping(value = "changeLanguage", method = RequestMethod.GET)
    public String changeLanguage(HttpServletRequest request,HttpServletResponse response,String language) {
        logger.info("web changeLanguage ----------Start--------");
        language=language.toLowerCase();
        if(language==null||language.equals("")){
            return "web/demo/i18n";
        }else{
            if(language.equals("zh_cn")){
                resolver.setLocale(request, response, Locale.CHINA );
            }else if(language.equals("en")){
                resolver.setLocale(request, response, Locale.ENGLISH );
            }else{
                resolver.setLocale(request, response, Locale.CHINA );
            }
        }
        return "web/demo/i18n";
    }

}
