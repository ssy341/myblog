package com.daja.paymp.infrastructure.common.control.protocol;

import com.daja.paymp.domain.model.meter.Meter;
import com.doris.das.model.RemoteAckPassThrough;
import com.doris.das.model.RemotePassThrough;
import com.doris.das.model.RemoteThroughResult;
import com.doris.das.model.TimeSwitchConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-4
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class Fun {



    /**
     * 工作日供电时段
     */
    public final static String WORK_DATE = "2013";

    /**
     * 工作日供电时段（副）
     */
    public final static String WORK_DATE_S = "2022";

    /**
     * 休息日供电时段
     */
    public final static String REST_DATE = "2025";

    /**
     * 休息日供电时段（副）
     */
    public final static String REST_DATE_S = "2028";

    /**
     *  最大负载功率
     */
    public final static String MAX_POWER = "2034";

    /**
     *  恶性负载起始功率
     */
    public  final static String M_POWER = "2037";

    /**
     * 超负荷断电次数
     */
    public final static String S_OUTAGE_NUM = "2040";

    /**
     * 恶性负载断电次数
     */
    public final static String M_OUTAGE_NUM = "2043";

    /**
     * 剩余不够不断电  （开）
     */
    public final static String ON = "2016";

    /**
     * 断电
     */
    public final static String OFF = "2019";

    /**
     * 清除超负荷次数次数
     */
    public final static String CLEAR_S = "2046";

    /**
     * 清除恶性负载次数
     */
    public final static String CLEAR_M = "2049";


    public static RemotePassThrough packObj(Meter m){
        String meterNo = m.getNo();
        String ConcentratorID = meterNo.substring(0, 6);
        String CollectorID = meterNo.substring(6, 9);
        String MeterID = meterNo.substring(9, 12);
        RemotePassThrough cmd = new RemotePassThrough();
        cmd.setConcentratorID(ConcentratorID);
        cmd.setCollectorID(CollectorID);
        cmd.setMeterID(MeterID);
        return cmd;
    }


    public static TimeSwitchConfig packTime(Map<String,Object> map){
        String startTime = (String)map.get("startTime");
        String endTime = (String)map.get("endTime");
        float power = (Float)map.get("power");
        TimeSwitchConfig.ConfigType configType =  (TimeSwitchConfig.ConfigType)map.get("configType");
        TimeSwitchConfig config = new TimeSwitchConfig(configType);
        config.addConfig(startTime, endTime, power);
        return config;
    }

    public static Map<String,Object> parseResult(RemoteAckPassThrough object){
        Map<String,Object> map = new HashMap<String, Object>();
        List<RemoteThroughResult> list = object.getResults();
        for(RemoteThroughResult result:list){
            map.put("meterId", result.getMeterID());
            map.put("pno", result.getPhysicsCode());
            map.put("result", result.getResult());
            map.put("type", result.getType());
        }
        return map;
    }

    public static Object parseResult(Object obj){
         if (obj instanceof String)
            return (String)obj;
        else if (obj instanceof Boolean)
            return (Boolean)obj;
        else
            return null;
    }

}
