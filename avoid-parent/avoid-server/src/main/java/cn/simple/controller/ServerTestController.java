package cn.simple.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.simple.annotation.AvoidDuplicateSubmission;

@Controller
@RequestMapping(path = "/servertest")
public class ServerTestController {
	@Autowired
	HttpServletRequest request;

	@RequestMapping("/form")
	@AvoidDuplicateSubmission(saveToken = true)
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView();
		System.out.println("this is server");
		mv.setViewName("test/form");
		return mv;
	}

	@RequestMapping(path = "/save")
	@AvoidDuplicateSubmission(removeToken = true)
	public ModelAndView save() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("server", request.getParameter("server"));
		mv.setViewName("test/save");
		return mv;
	}
}
