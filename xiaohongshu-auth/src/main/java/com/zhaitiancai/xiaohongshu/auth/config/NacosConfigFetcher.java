package com.zhaitiancai.xiaohongshu.auth.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
public class NacosConfigFetcher {
    
    @Autowired
    private ConfigService configService;
    
    @Value("${nacos.data-id:application-config}")
    private String dataId;
    
    @Value("${nacos.group:DEFAULT_GROUP}")
    private String group;

    @Value("${nacos.config.server-addr}")
    private  String serverAddr;
    private String configContent;

    private Listener listener;
    
    @PostConstruct
    public void init() {
	    // 初始加载配置
	    loadConfig();
    }

    /**
     * 添加动态监听
     * @throws NacosException
     */
//    public  void addListener() throws NacosException {
//        // 添加监听器，实现配置自动刷新
//          listener = new Listener() {
//            @Override
//            public Executor getExecutor() {
//                return null;
//            }
//
//            @Override
//            public void receiveConfigInfo(String configInfo) {
//                // 配置变更时更新内容
//                configContent = configInfo;
//                System.out.println("Config updated: " + configInfo);
//            }
//        };
//        configService.addListener(dataId, group, listener);


//    }

    /**
     * 获取配置(并实现监听 合并addListener方法)
     */
    private void loadConfig() {
        try {
            configContent = configService.getConfig(dataId, group, 5000);
            listener= new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("recieve1:" + configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            };
            String content = configService.getConfigAndSignListener(dataId, group, 5000,listener );
            System.out.println(content);
            System.out.println("Initial config loaded: " + configContent);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
    //获取配置对外接口
    public String getConfigContent() {
        return configContent;
    }

    /**
     * 删除监听
     */
    public void removeListener() throws NacosException {

        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        configService.removeListener(dataId, group, listener);
    }
}
