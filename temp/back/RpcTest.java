package com.daja.paymp;

import com.doris.das.model.RemoteAckPassThrough;
import com.doris.das.model.RemotePassThrough;
import com.doris.das.model.RemoteThroughResult;
import com.doris.das.model.TimeSwitchConfig;
import com.doris.das.service.RemoteService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-2
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */
public class RpcTest {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring-hsf-client.xml");
    RemoteService remoteDasService = (RemoteService)context.getBean("remoteDasService");

    @Test
    public void timeStep(){


        //透传
        RemotePassThrough cmd = new RemotePassThrough();
        cmd.setConcentratorID("000018");
        cmd.setCollectorID("006");
        cmd.setMeterID("002");

        TimeSwitchConfig config = new TimeSwitchConfig(TimeSwitchConfig.ConfigType.MASTER_WORK_TIME);
        config.addConfig("09:00", "12:00", (float) 1.1);
        config.addConfig("13:00", "17:00", (float) 1.2);
        cmd.addFunctions("2013", config);

        RemoteAckPassThrough object = (RemoteAckPassThrough) remoteDasService.ask(cmd, true);
        List<RemoteThroughResult> list = object.getResults();
        for(RemoteThroughResult result:list){
            result.getMeterID();
            result.getPhysicsCode();
            result.getResult();
            result.getType();
            System.out.println("MeterID="+result.getMeterID()+" Type:"+result.getType()+" Result:"+result.getResult());
        }

        System.out.println("=======透传======="+object);
    }

    @Test
    public void funTets(){
        RemotePassThrough cmd = new RemotePassThrough();
        cmd.setConcentratorID("000018");
        cmd.setCollectorID("006");
        cmd.setMeterID("001");

        //主正常用电，受分时、恶性等控制，剩余不够不断电
//			cmd.addFunctions("2016");
        //主继电器购电用电，受分时、恶性功率等控制，剩余不够则断电
//			cmd.addFunctions("2019");

        //主继电器供电时段设置
//			TimeSwitchConfig config = new TimeSwitchConfig(TimeSwitchConfig.ConfigType.MASTER_WORK_TIME);
//			config.addConfig("09:00", "12:00", (float) 11.1);
//			config.addConfig("13:00", "17:10", (float) 21.2);
//			cmd.addFunctions("2013", config);
        //(周末)主继电器供电时段设置
//			TimeSwitchConfig config = new TimeSwitchConfig(TimeSwitchConfig.ConfigType.MASTER_WORK_TIME);
//			config.addConfig("09:00", "12:00", (float) 11.1);
//			config.addConfig("13:00", "17:10", (float) 21.2);
//			cmd.addFunctions("2025", config);

        //辅继电器分时开关
//			TimeSwitchConfig config = new TimeSwitchConfig(TimeSwitchConfig.ConfigType.SLAVE_WORK_TIME);
//			config.addConfig("09:00", "12:00", (float) 11.1);
//			config.addConfig("13:00", "17:10", (float) 21.2);
//			cmd.addFunctions("2022", config);
        //(周末)辅继电器分时开关
//			TimeSwitchConfig config = new TimeSwitchConfig(TimeSwitchConfig.ConfigType.SLAVE_WORK_TIME);
//			config.addConfig("09:00", "12:00", (float) 11.1);
//			config.addConfig("13:00", "17:10", (float) 21.2);
//			cmd.addFunctions("2028", config);

        //最大负载功率
//			cmd.addFunctions("2034", (float) 1);
        //恶性负载起始功率
//			cmd.addFunctions("2037", (float) 0.40);
        //允许超负荷断电次数
//			cmd.addFunctions("2040", (int) 32);
        //允许恶性负载断电次数
//			cmd.addFunctions("2043", (int) 24);
        //清除超负荷次数次数
//			cmd.addFunctions("2046");
        //清除恶性负载次数
//			cmd.addFunctions("2049");

        RemoteAckPassThrough object = (RemoteAckPassThrough) remoteDasService.ask(cmd, true);
        List<RemoteThroughResult> list = object.getResults();
        for(RemoteThroughResult result:list){
            result.getMeterID();
            result.getPhysicsCode();
            result.getResult();
            result.getType();
            System.out.println("MeterID="+result.getMeterID()+" Type:"+result.getType()+" Result:"+result.getResult());
        }

        System.out.println("=======透传======="+object);
    }

}
