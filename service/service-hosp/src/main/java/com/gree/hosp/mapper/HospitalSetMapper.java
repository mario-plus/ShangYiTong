package com.gree.hosp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gree.models.model.hosp.HospitalSet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HospitalSetMapper extends BaseMapper<HospitalSet> {
}
