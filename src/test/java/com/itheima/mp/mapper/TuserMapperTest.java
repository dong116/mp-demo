package com.itheima.mp.mapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.UserInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.itheima.mp.enums.UserStatus;

import com.itheima.mp.domain.po.User;
import com.itheima.mp.service.IUserService;
import com.itheima.mp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(args = "--mpw.key=1743c7a470670bb8")
class TuserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TuserMapper tuserMapper;

    @Autowired
    private IUserService iUserService;

    @Test
    void  testInsert(){
        User user = new User();
        user.setId(6L);
        user.setUsername("Lucy98");
        user.setPassword("123");
        user.setPhone("456");
        user.setStatus(UserStatus.NORMAL);
        user.setBalance(20000);
        user.setInfo(new UserInfo());
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        tuserMapper.insert(user);


    }

    @Test
    void selectById(){
        User user = userMapper.selectById(5L);
        System.out.println("user=" + user);
    }

    @Test
    void selectByIds(){
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L, 5L));
        users.forEach(System.out::println);
    }

    @Test
    void UpdateById(){
        User user = new User();
        user.setId(5L);
        user.setBalance(50000);
        userMapper.updateById(user);
    }

    @Test
    void delete(){
        userMapper.deleteById(5L);
    }

    @Test
    void qtestQuery(){
        List<User> users = userMapper.TqueryById(1L);
        System.out.println("user=" + users);
    }

    @Test
    void testQuery(){
        List<User> users = tuserMapper.TqueryById(1L);
        System.out.println("user=" + users);
    }

    @Test
    void QueryWrapper(){
        //构造查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id","username","info","balance")
                .like("username","o")
                .ge("balance",300);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void updateByQueryWrapper(){
        QueryWrapper<User> wrapper  = new QueryWrapper<User>()
                .eq("username","Jack");
        User user = new User();
        user.setBalance(2000);
        userMapper.update(user,wrapper);

    }

    @Test
    void qupdateByQueryWrapper(){
        List<Long> ids = List.of(1l, 2l, 3l);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id",ids);
        tuserMapper.update(null,wrapper);

    }

    @Test
    void LambdaQueryWrapper(){
        //构造查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(User::getUsername,"o")
                .ge(User::getBalance,100);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void Custom(){
        List<Long> ids = List.of(1l, 2l, 3l);
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("id",ids);

        userMapper.deductBalanceByIds(200,wrapper);
    }


    @Test
    void CusJoin(){
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .in("u.id",List.of(1l,2l,4l))
                .eq("a.city","北京");
        List<User> users = userMapper.queryUsersByWrapper(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void tService(){
        List<User> list = iUserService.list();
        list.forEach(System.out::println);
    }

    @Test
    void Save(){
        long b = System.currentTimeMillis();
        for (int i = 1; i < 1000; i++) {
            iUserService.save(buildUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user2_"+i);
        user.setPassword("123");
        user.setPhone(""+(1868844066L)+i);
        user.setInfo(new UserInfo());
        user.setBalance(20000);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;

    }

    @Test
    void SaveBatch(){
        ArrayList<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            list.add(buildUser(i));
            //
            if (i%1000 == 0){
                iUserService.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    @Test
    void LambdaQuery(){
        User rose = iUserService.lambdaQuery()
                .eq(User::getUsername, "Rose")
                .one();
        System.out.println("rose=" + rose);

        List<User> users = iUserService.lambdaQuery()
                .like(User::getUsername, "o")
                .list();
        users.forEach(System.out::println);

        //count统计
        Long count = iUserService.lambdaQuery()
                .like(User::getUsername, "user")
                .count();
        System.out.println("count = " + count);

    }
    
    @Test
    void QueryUser(){
        List<User> users = queryUser("test", 1, null, null);

        users.forEach(System.out::println);
        System.out.println(users.stream().count());
    }

    private List<User> queryUser(String username, Integer status,Integer minBalance, Integer maxBalance) {
        return iUserService.lambdaQuery()
                .like(username != null,User::getUsername,username)
                .eq(status != null,User::getStatus,status)
                .ge(minBalance!= null,User::getBalance,minBalance)
                .le(maxBalance!= null,User::getBalance,maxBalance)
                .list();
    }

    @Test
    void DbGet(){
        User user = Db.getById(1l, User.class);
        System.out.println(user);
    }

    @Test
    void Delete(){
        // 构造条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("id", 1000); // id 大于 1000

        // 执行删除操作
        int deletedCount = userMapper.delete(queryWrapper);
        System.out.println("删除了 " + deletedCount + " 条记录");

    }

    @Test
    void PageQuery(){
        Page<User> p = iUserService.page(new Page<>(2, 100));
        // 2.总条数
        System.out.println("total = " + p.getTotal());
        // 3.总页数
        System.out.println("pages = " + p.getPages());
        List<User> records = p.getRecords();
        records.forEach(System.out::println);
        System.out.println("===============");
        int pageNO=1,pageSize=5;
        Page<User> page = Page.of(1, 5);
        page.addOrder(new OrderItem("balance",false));

        iUserService.page(page);
    }



}