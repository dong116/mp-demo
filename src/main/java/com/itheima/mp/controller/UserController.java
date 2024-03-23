package com.itheima.mp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.BaseResponse;
import com.itheima.mp.domain.po.Allbloguser;
import com.itheima.mp.domain.query.PageQuery;
import com.itheima.mp.domain.vo.PageVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/page")
    public PageVO<UserVO> queryUserByPage(PageQuery query){
        return userService.queryUserByPage(query);
    }

    @GetMapping("/select")
    public BaseResponse<Page<Allbloguser>> select(long currentPage, String title, Long userId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            return ResultUtils.success(allbloguserService.testselect(currentPage, title, null));
        } else {
            return ResultUtils.success(allbloguserService.testselect(currentPage, title, userId));
        }
    }
}
