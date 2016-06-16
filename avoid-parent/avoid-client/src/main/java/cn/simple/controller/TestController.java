package cn.simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.simple.annotation.AvoidDuplicateSubmission;

@Controller
@RequestMapping(path = "/test")
public class TestController {

	@Autowired
	HttpServletRequest request;

	@RequestMapping(path = "/form")
	@AvoidDuplicateSubmission(saveToken = true)
	public ModelAndView testForm() {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("test/form");
		return mv;
	}

	@RequestMapping(path = "/save")
	@AvoidDuplicateSubmission(removeToken = true)
	public ModelAndView formSave() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("client", request.getParameter("client"));
		mv.setViewName("test/save");
		return mv;
	}
}
