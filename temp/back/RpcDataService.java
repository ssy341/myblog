package com.daja.paymp.infrastructure.common.rpc;

import com.daja.paymp.domain.model.meter.Meter;

import java.util.Map;

public interface RpcDataService {

   /**
     * 关
     */
    public boolean payOff(Meter meter);


    /**
     * 对控制器进行参数设置
     * @param meter
     * @return
     */
    public boolean argsConf(Meter meter,Map<String,Object> cmd);

    /**
     * 对控制器进行参数（时间段）设置[单个]
     * @param meter
     * @return
     */
    public boolean argsConfTime(Meter meter,Map<String,Object> cmd);


    /**
     * 对控制器进行参数（清除）设置[单个]
     * @param meter
     * @return
     */
    public boolean argsConfClear(Meter meter,Map<String,Object> cmd);


    /**
     * 通电
     * @param meter
     */
    public boolean payON(Meter meter);
	
}
