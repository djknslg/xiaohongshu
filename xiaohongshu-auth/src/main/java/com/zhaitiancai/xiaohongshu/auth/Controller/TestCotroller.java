package com.zhaitiancai.xiaohongshu.auth.Controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zhaitiancai.xiaohongshu.auth.alarm.AlarmInterface;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestCotroller {

@Resource
private AlarmInterface alarm;
	@NacosValue(value = "${rate-limit.api.limit}", autoRefreshed = true)
	private Integer aaa;
	@GetMapping("/limit")
	public String limit(){
		return "当前限流阈值为: " + aaa;
	}
@GetMapping("/alarm")
public String sendAlarm() {
	alarm.send("系统出错啦，犬小哈这个月绩效没了，速度上线解决问题！");
	return "alarm success";
}

}
