package com.example.scoremanagement.Controller;

import com.example.scoremanagement.Entity.ScoreInsertRecord;
import com.example.scoremanagement.Entity.UserInsertRecord;
import com.example.scoremanagement.Entity.UserRecord;
import com.example.scoremanagement.Exception.UserNotFoundException;
import com.example.scoremanagement.Form.ScoreInsertForm;
import com.example.scoremanagement.Form.ScoreUpdateForm;
import com.example.scoremanagement.Form.UpdateForm;
import com.example.scoremanagement.Form.UserForm;
import com.example.scoremanagement.Service.IScoreService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class userManagementController {
    @Autowired
    private HttpSession session;
    @Autowired
    IUserService iUserService;
    @Autowired
    IScoreService iScoreService;


    private int insertNum = 0;

    private String[] successMesList={"","削除しました",""};

    private int successIndex = 0;


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
    public String insert(@ModelAttribute("ScoreInsertForm")ScoreInsertForm scoreInsertForm, Model model){

        return "insert";
    }
    @PostMapping("/insert")

        public String insert (@Validated @ModelAttribute("ScoreInsertForm")ScoreInsertForm scoreInsertForm, BindingResult bindingResult, Model model){
            if(bindingResult.hasErrors()) {
                return "/insert";
            }else {
                var user = (UserRecord)session.getAttribute("user");
                iScoreService.insert(new ScoreInsertRecord(user.login_id(),scoreInsertForm.getGrade(),scoreInsertForm.getScore(),scoreInsertForm.getSubject()
                ,scoreInsertForm.getType(),scoreInsertForm.getDeviation()));
                model.addAttribute("success","データを登録しました");
                insertNum ++;
                successMesList[2] = insertNum+"件追加しました";
                successIndex = 2;
                return "/insert";

            }
        }
    //データ一覧画面
    @GetMapping("/result")
    public String result(Model model){
//        model.addAttribute("dataList",iScoreService.findAll());
        var user = (UserRecord)session.getAttribute("user");
//        System.out.println(user);
        model.addAttribute("dataList",iScoreService.findMe(user.login_id()));
//        System.out.println(iScoreService.findMe(user.login_id()));
        model.addAttribute("success",successMesList[successIndex]);
        successIndex=0;
        insertNum =0;
        return "result";
    }
    //ログアウト
    @PostMapping("/logout")
    public String logout(@ModelAttribute("loginForm") UserForm userForm) {
        session.invalidate();
        return "login";
    }

    //詳細
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model){
        model.addAttribute("detail",iScoreService.findById(id));
        return "/detail";
    }

    //更新
    @GetMapping("/update/{id}")
    public String update(@ModelAttribute("UpdateForm") UpdateForm updateForm, @PathVariable("id") int id, Model model) {
        model.addAttribute("update", iScoreService.findById(id));
        return "/update";
    }
//    @PostMapping("/updateInput/{id}")
//    public String update (@PathVariable("id") int id, @Validated @ModelAttribute("ScoreUpdateForm") ScoreUpdateForm scoreUpdateForm, BindingResult bindingResult, Model model){
//        model.addAttribute("product",iScoreService.findById(id));
//        model.addAttribute("categoryList", iCategoryService.categoryFindAll());
//        if(bindingResult.hasErrors()) {
////            model.addAttribute("product",iUserManagementService.findById(id));
//            return "updateInput";
//        }else {
//            try{iUserManagementService.update(new ProductUpdate(
//                    id,updateForm.getProduct_id(),updateForm.getName(),updateForm.getPrice(),updateForm.getCategory_id(),updateForm.getDescription()));}
//            catch (DuplicateKeyException e){
//                model.addAttribute("error","商品IDが重複しています");
//                return "updateInput";
//            }
//
//            return "redirect:/menu";
//    }
    //削除
    @PostMapping("/detail/{id}")
    public String delete(@PathVariable("id") int id,Model model) {
        iScoreService.findById(id);
        iScoreService.delete(id);
        successIndex=1;
        return "redirect:/result";

    }
}



