package com.example.scoremanagement.Controller;

import com.example.scoremanagement.Entity.UserInsertRecord;
import com.example.scoremanagement.Entity.UserRecord;
import com.example.scoremanagement.Exception.UserNotFoundException;
import com.example.scoremanagement.Form.UserForm;
import com.example.scoremanagement.Service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class userManagementController {
    @Autowired
    private HttpSession session;
    @Autowired
    IUserService iUserService;


    //ログイン機能
    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") UserForm userform) {

        return "login";
    }
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") UserForm userForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "login";
        }else {
            try {
                session.setAttribute("user",iUserService.findByInfo(userForm.getLoginId(),userForm.getPassword()));
                return "redirect:/menu";
            } catch (UserNotFoundException e) {
                model.addAttribute("error","IDまたはパスワードが不正です");
                return "login";
            }
        }
    }

    //新規登録
    @GetMapping("/user")
    public String user(@ModelAttribute("UserForm") UserForm userform) {

        return "user";
    }
    @PostMapping("/user")
    public String userInsert (@Validated @ModelAttribute("UserForm") UserForm userForm, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "user";
        }
        else {
            try {
                iUserService.userInsert(new UserInsertRecord(userForm.getLoginId(), userForm.getPassword()));
                model.addAttribute("success","新規ユーザーを登録しました");
            } catch (DuplicateKeyException e) {
                model.addAttribute("error", "ユーザーが重複しています");
                return "user";
            }
        }
        return "user";
    }
    //メニュー画面
    @GetMapping("/menu")
    public String menu(Model model){
        return "menu";
    }

    //データ入力画面
    @GetMapping("/insert")
    public String insert(Model model){
        return "insert";
    }
    //データ一覧画面
    @GetMapping("/result")
    public String result(Model model){
        return "result";
    }
    //ログアウト
    @GetMapping("/login")

    public String logout(Model model){
        return "login";
    }
    @PostMapping("/logout")
    public String logout(@ModelAttribute("loginForm") UserForm userForm) {
        session.invalidate();
        return "logout";
    }
}