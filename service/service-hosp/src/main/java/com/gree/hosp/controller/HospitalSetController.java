package com.gree.hosp.controller;


import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gree.commonUtil.response.HttpResponseResult;
import com.gree.commonUtil.utils.MD5;
import com.gree.hosp.service.HospitalSetService;
import com.gree.models.model.hosp.HospitalSet;
import com.gree.models.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    private final HospitalSetService hospitalSetService;
    public HospitalSetController(HospitalSetService hospitalSetService) {
        this.hospitalSetService = hospitalSetService;
    }

    //1 查询医院设置表所有信息
    @ApiOperation(value = "1.获取所有医院设置")
    @GetMapping("findAll")
    public HttpResponseResult findAllHospitalSet() {
        //调用service的方法
        List<HospitalSet> list = hospitalSetService.list();
        return HttpResponseResult.ok(list);
    }

    @ApiOperation(value = "2.逻辑删除医院设置")
    @DeleteMapping("{id}")
    public HttpResponseResult removeHospSet(@PathVariable Long id){
        boolean b = hospitalSetService.removeById(id);
        if (b){
            return HttpResponseResult.ok();
        }else{
            return HttpResponseResult.fail();
        }
    }

    @ApiOperation(value = "3.分页查询医院列表")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public HttpResponseResult findPageHospSet(@PathVariable Long current,
                                              @PathVariable Long limit,
                                              @RequestBody HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> pageData = new Page<>(current, limit);

        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())){
            wrapper.like("hostname",hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())){
            wrapper.eq("hascode",hospitalSetQueryVo.getHoscode());
        }
        Page<HospitalSet> page = hospitalSetService.page(pageData, wrapper);

        return HttpResponseResult.ok(page);
    }

    //4 添加医院设置
    @ApiOperation("4.添加医院设置")
    @PostMapping("saveHospitalSet")
    public HttpResponseResult saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if(save) {
            return HttpResponseResult.ok();
        } else {
            return HttpResponseResult.fail();
        }
    }


    @ApiOperation("5.根据id获取医院信息")
    @GetMapping("getHospSet/{id}")
    public HttpResponseResult getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return HttpResponseResult.ok(hospitalSet);
    }


    @ApiOperation("6.修改医院设置")
    @PostMapping("updateHospitalSet")
    public HttpResponseResult updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if(flag) {
            return HttpResponseResult.ok();
        } else {
            return HttpResponseResult.fail();
        }
    }


    @ApiOperation("7.批量删除医院设置")
    @DeleteMapping("batchRemove")
    public HttpResponseResult batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return HttpResponseResult.ok();
    }

    @ApiOperation("8.医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public HttpResponseResult lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return HttpResponseResult.ok();
    }

    @ApiOperation("9.发送签名秘钥")
    @PutMapping("sendKey/{id}")
    public HttpResponseResult lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return HttpResponseResult.ok();
    }


}
