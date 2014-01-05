package com.daja.paymp.infrastructure.common.rpc.impl;


import com.daja.paymp.domain.model.SEMessage;
import com.daja.paymp.domain.model.meter.Meter;
import com.daja.paymp.infrastructure.common.control.protocol.ArgsBean;
import com.daja.paymp.infrastructure.common.control.protocol.Fun;
import com.daja.paymp.infrastructure.common.rpc.RpcDataService;
import com.doris.das.exception.ServiceException;
import com.doris.das.model.RemoteAckPassThrough;
import com.doris.das.model.RemotePassThrough;
import com.doris.das.service.RemoteService;
import com.doris.hsf.exception.HsfTimeoutException;
import org.apache.poi.hssf.record.FnGroupCountRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("RpcDataService")
public class RpcDataServiceImpl implements RpcDataService {
	                
	@Resource(name="remoteDasService")
    RemoteService remoteService;

	// 日志对象
	Logger logger = LoggerFactory.getLogger(RpcDataServiceImpl.class);
	
	private float offNum = 0.0f;
	private float onNum = 0.0f;
	private float errNum = 0.0f;
	private long sum;
	
	
	public SEMessage controlMeter(Meter m,String cmd){
		SEMessage info = new SEMessage();
		StringBuffer sbtime = new StringBuffer("");
		StringBuffer sberror = new StringBuffer("");
		String msg = "";
		String timeMeter = "", errMeter = "";
		// 表计物理id
		String meterid = m.getPno();
		// 表计逻辑id 1~6采集器号 7~9集中器（废弃了） 10~12表号
		String meterno = m.getNo();
		String code = "";
		if("1".equals(cmd)){
			code ="";
			msg = "开";
		}else{
			code = "";
			msg = "关";
		}
		
		try {
			long startTime = System.currentTimeMillis();
			Object obj = null;
			
			boolean flag = false;
			if(flag){
				info.setInfo("操作成功");
				info.setFlag(true);
				logger.info(meterid+"--- "+msg+"---操作成功 " + (System.currentTimeMillis() - startTime) + " ms");
			}else{
				info.setInfo("操作失败");
				info.setFlag(false);
				logger.info(meterid+"--- "+msg+"---操作失败 " + (System.currentTimeMillis() - startTime) + " ms");
			}
			
			
		} catch (IllegalStateException e) {
			logger.error("[das服务状态异常：" + e.getMessage() + "]", e);
			sberror.append(meterno);
		} catch (HsfTimeoutException timeout) {
			logger.error("[das超时：" + timeout.getMessage() + "]", timeout);
			sbtime.append(meterno);
		}catch (ServiceException e){
			logger.error("[das异常："+e.getMessage()+"]", e);
			info.setInfo("das异常");
			info.setFlag(false);
		}catch (Exception er){
			logger.error("[服务器异常："+er.getMessage()+"]", er);
			info.setInfo("服务器异常,请联系管理员");
			info.setFlag(false);
		}
		if (timeMeter.length() > 0) {
			timeMeter += " 表计操作超时,请重试";
			logger.info(timeMeter);
			info.setInfo(timeMeter);
			info.setFlag(false);
		}

		if (errMeter.length() > 0) {
			errMeter += " 表计操作异常,与das断开,请稍后重试";
			logger.info(errMeter);
			info.setInfo(errMeter);
			info.setFlag(false);
		}
		return info;
	}


    @Override
    public boolean payOff(Meter meter) {
        RemotePassThrough cmd = Fun.packObj(meter);
        cmd.addFunctions(Fun.OFF);
        RemoteAckPassThrough obj = (RemoteAckPassThrough)remoteService.ask(cmd, false);
        Map<String,Object> map = Fun.parseResult(obj);
        return (Boolean)map.get("result") ;
    }

    @Override
    public boolean argsConf(Meter meter, Map<String,Object> cmd) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean argsConfTime(Meter meter, Map<String,Object> cmd) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean argsConfClear(Meter meter, Map<String,Object> cmd) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean payON(Meter meter) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
