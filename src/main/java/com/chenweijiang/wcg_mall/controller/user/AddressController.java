package com.chenweijiang.wcg_mall.controller.user;

import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.context.BaseContext;
import com.chenweijiang.wcg_mall.pojo.AddressBook;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户地址相关接口
 * @author 枳枳
 */
@RestController
@RequestMapping("/user/address")
@Slf4j
@Tag(name = "用户地址相关接口")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;


    @GetMapping
    @Operation(summary = "获取用户地址数据")
    public Result<List<AddressBook>> listByUserId() {
        log.info("获取用户地址数据");
        Long userId  = BaseContext.getCurrentId();
        List<AddressBook> address = addressService.listByUserId(userId);
        return Result.success(address);
    }

    @PostMapping
    @Operation(summary = "添加用户地址数据")
    public Result<String> addAddress(@RequestBody AddressBook addressBook) {
        log.info("添加用户地址数据");
        addressBook.setUserId(BaseContext.getCurrentId());
        addressService.add(addressBook);
        return Result.success(MessageConstant.ADD_ADDRESS_SUCCESS);
    }

    @PutMapping
    @Operation(summary = "修改用户地址数据")
    public Result<String> editAddress(@RequestBody AddressBook addressBook) {
        log.info("修改用户地址数据{}",addressBook);
        addressService.update(addressBook);
        return Result.success(MessageConstant.SUCCESS_EDIT_ADDRESS_BOOK);
    }

    @DeleteMapping
    @Operation(summary = "删除用户地址数据")
    public Result<String> deleteAddress(Long id) {
        log.info("删除用户地址数据{}",id);
        addressService.deleteById(id);
        return Result.success(MessageConstant.ADDRESS_DELETE_SUCCESS);
    }
}
