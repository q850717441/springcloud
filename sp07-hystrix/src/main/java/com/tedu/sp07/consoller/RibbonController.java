package com.tedu.sp07.consoller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tedu.sp01.pojo.Item;
import com.tedu.sp01.pojo.Order;
import com.tedu.sp01.pojo.User;
import com.tedu.web.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RibbonController {
    @Autowired
    private RestTemplate rt;

    @GetMapping("/item-service/{orderId}")
    //当请求后台服务失败,自动跳到降级方法执行降级代码
    @HystrixCommand(fallbackMethod = "getItemsFB")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId) {
        return rt.getForObject("http://item-service/{1}", JsonResult.class, orderId);
    }

    @PostMapping("/item-service/decreaseNumber")
    @HystrixCommand(fallbackMethod = "decreaseNumberFB")
    public JsonResult decreaseNumber(@RequestBody List<Item> list) {
        return rt.postForObject("http://item-service/decreaseNumber", list, JsonResult.class);
    }

    /////////////////////////////////////////

    @GetMapping("/user-service/{userId}")
    @HystrixCommand(fallbackMethod = "getUserFB")
    public JsonResult<User> getUser(@PathVariable Integer userId) {
        return rt.getForObject("http://user-service/{1}", JsonResult.class, userId);
    }

    @GetMapping("/user-service/{userId}/score")
    @HystrixCommand(fallbackMethod = "addScoreFB")
    public JsonResult addScore(
            @PathVariable Integer userId, Integer score) {
        return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
    }

    /////////////////////////////////////////

    @GetMapping("/order-service/{orderId}")
    @HystrixCommand(fallbackMethod = "getOrderFB")
    public JsonResult<Order> getOrder(@PathVariable String orderId) {
        return rt.getForObject("http://order-service/{1}", JsonResult.class, orderId);
    }

    @GetMapping("/order-service")
    @HystrixCommand(fallbackMethod = "addOrderFB")
    public JsonResult addOrder() {
        return rt.getForObject("http://order-service/", JsonResult.class);
    }


    public JsonResult<List<Item>> getItemsFB(String orderId) {
        return JsonResult.err("无法获取订单的商品列表");
    }

    public JsonResult decreaseNumberFB(List<Item> items) {
        return JsonResult.err("无法减少商品库存");
    }

    public JsonResult<User> getUserFB(Integer id) {
        return JsonResult.err("无法获取用户信息");
    }

    public JsonResult addScoreFB(Integer userId, Integer socre) {
        return JsonResult.err("无法增加用户积分");
    }

    public JsonResult<Order> getOrderFB(String orderId) {
        return JsonResult.err("无法获取订单信息");
    }

    public JsonResult addOrderFB() {
        return JsonResult.err("无法保存订单");
    }
}










